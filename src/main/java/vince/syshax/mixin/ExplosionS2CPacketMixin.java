/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import vince.syshax.mixininterface.IExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ExplosionS2CPacket.class)
public class ExplosionS2CPacketMixin implements IExplosionS2CPacket {
    @Shadow @Final @Mutable private float playerVelocityX;

    @Shadow @Final @Mutable private float playerVelocityY;

    @Shadow @Final @Mutable private float playerVelocityZ;

    @Override
    public void setVelocityX(float velocity) {
        playerVelocityX = velocity;
    }

    @Override
    public void setVelocityY(float velocity) {
        playerVelocityY = velocity;
    }

    @Override
    public void setVelocityZ(float velocity) {
        playerVelocityZ = velocity;
    }
}
