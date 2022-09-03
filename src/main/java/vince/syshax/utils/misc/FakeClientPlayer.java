/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.utils.misc;

import vince.syshax.SYSHax;
import vince.syshax.events.game.GameJoinedEvent;
import vince.syshax.utils.PreInit;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.text.Text;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

public class FakeClientPlayer {
    private static ClientWorld world;
    private static PlayerEntity player;
    private static PlayerListEntry playerListEntry;

    private static String lastId;
    private static boolean needsNewEntry;

    @PreInit
    public static void init() {
        SYSHax.EVENT_BUS.subscribe(FakeClientPlayer.class);
    }

    @EventHandler
    private static void onGameJoined(GameJoinedEvent event) {
    }

    public static PlayerEntity getPlayer() {
        String id = SYSHax.mc.getSession().getUuid();

        if (player == null || (!id.equals(lastId))) {
            if (world == null) {
                world = new ClientWorld(new ClientPlayNetworkHandler(SYSHax.mc, null, new ClientConnection(NetworkSide.CLIENTBOUND), SYSHax.mc.getSession().getProfile(), null), new ClientWorld.Properties(Difficulty.NORMAL, false, false), World.OVERWORLD, BuiltinRegistries.DIMENSION_TYPE.entryOf(DimensionTypes.OVERWORLD), 1, 1, SYSHax.mc::getProfiler, null, false, 0);
            }

            player = new OtherClientPlayerEntity(world, SYSHax.mc.getSession().getProfile(), null);

            lastId = id;
            needsNewEntry = true;
        }

        return player;
    }

    public static PlayerListEntry getPlayerListEntry() {
        if (playerListEntry == null || needsNewEntry) {
            playerListEntry = new PlayerListEntry(PlayerListEntryFactory.create(SYSHax.mc.getSession().getProfile(), 0, GameMode.SURVIVAL, Text.of(SYSHax.mc.getSession().getProfile().getName()), null), null, false);
            needsNewEntry = false;
        }

        return playerListEntry;
    }
}
