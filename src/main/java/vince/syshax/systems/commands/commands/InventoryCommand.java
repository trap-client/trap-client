/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vince.syshax.systems.commands.Command;
import vince.syshax.systems.commands.arguments.PlayerArgumentType;
import vince.syshax.utils.Utils;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class InventoryCommand extends Command {
    public InventoryCommand() {
        super("inventory", "Allows you to see parts of another player's inventory.", "inv", "invsee");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("player", PlayerArgumentType.create()).executes(context -> {
            Utils.screenToOpen = new InventoryScreen(PlayerArgumentType.get(context));
            return SINGLE_SUCCESS;
        }));
    }
}
