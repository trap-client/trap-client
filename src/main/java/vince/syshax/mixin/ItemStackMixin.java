/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.mixin;

import vince.syshax.SYSHax;
import vince.syshax.events.entity.player.FinishUsingItemEvent;
import vince.syshax.events.entity.player.StoppedUsingItemEvent;
import vince.syshax.events.game.ItemStackTooltipEvent;
import vince.syshax.systems.modules.Modules;
import vince.syshax.systems.modules.render.BetterTooltips;
import vince.syshax.utils.Utils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "getTooltip", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onGetTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> info, List<Text> list) {
        if (Utils.canUpdate()) {
            SYSHax.EVENT_BUS.post(ItemStackTooltipEvent.get((ItemStack) (Object) this, list));
        }
    }

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void onFinishUsing(World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
        if (user == SYSHax.mc.player) {
            SYSHax.EVENT_BUS.post(FinishUsingItemEvent.get((ItemStack) (Object) this));
        }
    }

    @Inject(method = "onStoppedUsing", at = @At("HEAD"))
    private void onStoppedUsing(World world, LivingEntity user, int remainingUseTicks, CallbackInfo info) {
        if (user == SYSHax.mc.player) {
            SYSHax.EVENT_BUS.post(StoppedUsingItemEvent.get((ItemStack) (Object) this));
        }
    }

    @Inject(method = "getHideFlags", at = @At("HEAD"), cancellable = true)
    private void onGetHideFlags(CallbackInfoReturnable<Integer> cir) {
        if (Modules.get().get(BetterTooltips.class).alwaysShow()) {
            cir.setReturnValue(0);
        }
    }
}
