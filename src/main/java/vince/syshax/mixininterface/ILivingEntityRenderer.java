/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixininterface;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public interface ILivingEntityRenderer {
    void setupTransformsInterface(LivingEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta);
    void scaleInterface(LivingEntity entity, MatrixStack matrices, float amount);
    boolean isVisibleInterface(LivingEntity entity);
    float getAnimationCounterInterface(LivingEntity entity, float tickDelta);
}
