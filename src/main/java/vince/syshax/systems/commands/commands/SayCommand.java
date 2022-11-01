/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vince.syshax.mixin.ClientPlayerEntityAccessor;
import vince.syshax.systems.commands.Command;
import vince.syshax.utils.misc.MeteorStarscript;
import meteordevelopment.starscript.Script;
import net.minecraft.command.CommandSource;
import net.minecraft.network.message.DecoratedContents;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.message.MessageMetadata;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class SayCommand extends Command {
    public SayCommand() {
        super("say", "Sends messages in chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("message", StringArgumentType.greedyString()).executes(context -> {
            String msg = context.getArgument("message", String.class);
            Script script = MeteorStarscript.compile(msg);

            if (script != null) {
                String message = MeteorStarscript.run(script);

                if (message != null) {
                    MessageMetadata metadata = MessageMetadata.of(mc.player.getUuid());
                    DecoratedContents contents = new DecoratedContents(message);

                    LastSeenMessageList.Acknowledgment acknowledgment = mc.getNetworkHandler().consumeAcknowledgment();
                    MessageSignatureData messageSignatureData = ((ClientPlayerEntityAccessor) mc.player)._signChatMessage(metadata, contents, acknowledgment.lastSeen());
                    mc.getNetworkHandler().sendPacket(new ChatMessageC2SPacket(contents.plain(), metadata.timestamp(), metadata.salt(), messageSignatureData, contents.isDecorated(), acknowledgment));

                }
            }

            return SINGLE_SUCCESS;
        }));
    }
}
