/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.world;

import vince.syshax.events.game.OpenScreenEvent;
import vince.syshax.events.packets.PacketEvent;
import vince.syshax.mixin.SignEditScreenAccessor;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;

public class AutoSign extends Module {
    private String[] text;

    public AutoSign() {
        super(Categories.World, "auto-sign", "Automatically writes signs. The first sign's text will be used.");
    }

    @Override
    public void onDeactivate() {
        text = null;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof UpdateSignC2SPacket)) return;

        text = ((UpdateSignC2SPacket) event.packet).getText();
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        if (!(event.screen instanceof SignEditScreen) || text == null) return;

        SignBlockEntity sign = ((SignEditScreenAccessor) event.screen).getSign();

        mc.player.networkHandler.sendPacket(new UpdateSignC2SPacket(sign.getPos(), text[0], text[1], text[2], text[3]));

        event.cancel();
    }
}
