/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import vince.syshax.renderer.GL;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.ByteBuffer;

@Mixin(VertexBuffer.class)
public class VertexBufferMixin {
    @Shadow private int indexBufferId;

    @Inject(method = "configureIndexBuffer", at = @At("RETURN"))
    private void onConfigureIndexBuffer(BufferBuilder.DrawArrayParameters parameters, ByteBuffer data, CallbackInfoReturnable<RenderSystem.IndexBuffer> info) {
        if (info.getReturnValue() == null) GL.CURRENT_IBO = this.indexBufferId;
        else GL.CURRENT_IBO = ((IndexBufferAccessor) (Object) info.getReturnValue()).getId();
    }
}
