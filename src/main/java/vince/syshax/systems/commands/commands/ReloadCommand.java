/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import vince.syshax.renderer.Fonts;
import vince.syshax.systems.Systems;
import vince.syshax.systems.commands.Command;
import vince.syshax.utils.network.Capes;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, macros, accounts, capes and fonts.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Systems.load();
            Capes.init();
            Fonts.refresh();

            return SINGLE_SUCCESS;
        });
    }
}
