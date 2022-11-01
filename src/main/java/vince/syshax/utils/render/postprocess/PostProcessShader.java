/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.render.postprocess;

import vince.syshax.SYSHax;
import vince.syshax.renderer.GL;
import vince.syshax.renderer.PostProcessRenderer;
import vince.syshax.renderer.Shader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.entity.Entity;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public abstract class PostProcessShader {
    public OutlineVertexConsumerProvider vertexConsumerProvider;
    public Framebuffer framebuffer;
    protected Shader shader;

    public void init(String frag) {
        vertexConsumerProvider = new OutlineVertexConsumerProvider(SYSHax.mc.getBufferBuilders().getEntityVertexConsumers());
        framebuffer = new SimpleFramebuffer(SYSHax.mc.getWindow().getFramebufferWidth(), SYSHax.mc.getWindow().getFramebufferHeight(), false, MinecraftClient.IS_SYSTEM_MAC);
        shader = new Shader("post-process/base.vert", "post-process/" + frag + ".frag");
    }

    protected abstract boolean shouldDraw();
    public abstract boolean shouldDraw(Entity entity);

    protected void preDraw() {}
    protected void postDraw() {}

    protected abstract void setUniforms();

    public void beginRender() {
        if (!shouldDraw()) return;

        framebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
        SYSHax.mc.getFramebuffer().beginWrite(false);
    }

    public void endRender(Runnable draw) {
        if (!shouldDraw()) return;

        preDraw();
        draw.run();
        postDraw();

        SYSHax.mc.getFramebuffer().beginWrite(false);

        GL.bindTexture(framebuffer.getColorAttachment(), 0);

        shader.bind();

        shader.set("u_Size", SYSHax.mc.getWindow().getFramebufferWidth(), SYSHax.mc.getWindow().getFramebufferHeight());
        shader.set("u_Texture", 0);
        shader.set("u_Time", glfwGetTime());
        setUniforms();

        PostProcessRenderer.render();
    }

    public void onResized(int width, int height) {
        if (framebuffer == null) return;
        framebuffer.resize(width, height, MinecraftClient.IS_SYSTEM_MAC);
    }
}
