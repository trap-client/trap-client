/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.addons;

import vince.syshax.SYSHax;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    public static final List<MeteorAddon> ADDONS = new ArrayList<>();

    public static void init() {
        // Meteor pseudo addon
        {
            SYSHax.ADDON = new MeteorAddon() {
                @Override
                public void onInitialize() {}

                @Override
                public String getPackage() {
                    return "vince.syshax";
                }

                @Override
                public String getWebsite() {
                    return "https://vincedev.se";
                }

                @Override
                public GithubRepo getRepo() {
                    return new GithubRepo("vincelmfao", "syshax");
                }

                @Override
                public String getCommit() {
                    String commit = SYSHax.MOD_META.getCustomValue(SYSHax.MOD_ID + ":commit").getAsString();
                    return commit.isEmpty() ? null : commit;
                }
            };

            ModMetadata metadata = FabricLoader.getInstance().getModContainer(SYSHax.MOD_ID).get().getMetadata();

            SYSHax.ADDON.name = metadata.getName();
            SYSHax.ADDON.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue(SYSHax.MOD_ID + ":color")) {
                SYSHax.ADDON.color.parse(metadata.getCustomValue(SYSHax.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                SYSHax.ADDON.authors[i++] = author.getName();
            }
        }

        // Addons
        for (EntrypointContainer<MeteorAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("meteor", MeteorAddon.class)) {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            MeteorAddon addon = entrypoint.getEntrypoint();

            addon.name = metadata.getName();
            addon.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue(SYSHax.MOD_ID + ":color")) {
                addon.color.parse(metadata.getCustomValue(SYSHax.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                addon.authors[i++] = author.getName();
            }

            ADDONS.add(addon);
        }
    }
}
