/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import vince.syshax.systems.modules.Modules;
import vince.syshax.systems.modules.render.NoRender;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlockEntityRenderer.class)
public class EnchantingTableBlockEntityRendererMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BookModel;renderBook(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void onRenderBookModelRenderProxy(BookModel model, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        if (!Modules.get().get(NoRender.class).noEnchTableBook()) model.renderBook(matrixStack, vertexConsumer, i, j, f, g, h, k);
    }
}
