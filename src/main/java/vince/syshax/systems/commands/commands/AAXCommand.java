/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;


import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import vince.syshax.systems.commands.Command;

public class AAXCommand extends Command {
    public AAXCommand() {
        super("AntiAntiXray", "Remove ghost blocks & bypass AntiXray", "aax", "anti-anti-xray");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(ctx -> {
            execute(4);
            return SINGLE_SUCCESS;
        });
        builder.then(argument("radius", IntegerArgumentType.integer(1)).executes(ctx -> {
            int radius = IntegerArgumentType.getInteger(ctx, "radius");
            execute(radius);
            return SINGLE_SUCCESS;
        }));
    }

    private void execute(int radius) {
        ClientPlayNetworkHandler conn = mc.getNetworkHandler();
        if (conn == null)
            return;
        BlockPos pos = mc.player.getBlockPos();
        for (int dx = -radius; dx <= radius; dx++)
            for (int dy = -radius; dy <= radius; dy++)
                for (int dz = -radius; dz <= radius; dz++) {
                    PlayerActionC2SPacket packet = new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK,
                            new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz), Direction.UP);
                    conn.sendPacket(packet);
                }
    }
}
