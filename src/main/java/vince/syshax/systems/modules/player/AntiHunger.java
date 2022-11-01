/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.player;

import vince.syshax.events.packets.PacketEvent;
import vince.syshax.events.world.TickEvent;
import vince.syshax.mixin.PlayerMoveC2SPacketAccessor;
import vince.syshax.settings.BoolSetting;
import vince.syshax.settings.Setting;
import vince.syshax.settings.SettingGroup;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AntiHunger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> sprint = sgGeneral.add(new BoolSetting.Builder()
            .name("sprint")
            .description("Spoofs sprinting packets.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> onGround = sgGeneral.add(new BoolSetting.Builder()
            .name("on-ground")
            .description("Spoofs the onGround flag.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> waterCheck = sgGeneral.add(new BoolSetting.Builder()
            .name("water-check")
            .description("Pauses the module if you are in water")
            .defaultValue(true)
            .build());

    private boolean lastOnGround;
    private boolean sendOnGroundTruePacket;
    private boolean ignorePacket;

    public AntiHunger() {
        super(Categories.Player, "anti-hunger", "Reduces (does NOT remove) hunger consumption.");
    }

    @Override
    public void onActivate() {
        lastOnGround = mc.player.isOnGround();
        sendOnGroundTruePacket = true;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (ignorePacket) return;

        if (event.packet instanceof ClientCommandC2SPacket && sprint.get()) {
            ClientCommandC2SPacket.Mode mode = ((ClientCommandC2SPacket) event.packet).getMode();

            if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING || mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                event.cancel();
            }
        }

        if (event.packet instanceof PlayerMoveC2SPacket && onGround.get() && mc.player.isOnGround() && mc.player.fallDistance <= 0.0 && !mc.interactionManager.isBreakingBlock()) {
            ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (waterCheck.get() && mc.player.isTouchingWater()) {
            ignorePacket = true;
            return;
        }
        if (mc.player.isOnGround() && !lastOnGround && !sendOnGroundTruePacket) sendOnGroundTruePacket = true;

        if (mc.player.isOnGround() && sendOnGroundTruePacket && onGround.get()) {
            ignorePacket = true;
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            ignorePacket = false;

            sendOnGroundTruePacket = false;
        }

        lastOnGround = mc.player.isOnGround();
    }
}
