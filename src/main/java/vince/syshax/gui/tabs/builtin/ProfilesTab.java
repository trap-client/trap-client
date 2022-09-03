/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.tabs.builtin;

import vince.syshax.SYSHax;
import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.WindowScreen;
import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.gui.tabs.Tab;
import vince.syshax.gui.tabs.TabScreen;
import vince.syshax.gui.tabs.WindowTabScreen;
import vince.syshax.gui.widgets.containers.WContainer;
import vince.syshax.gui.widgets.containers.WTable;
import vince.syshax.gui.widgets.pressable.WButton;
import vince.syshax.gui.widgets.pressable.WMinus;
import vince.syshax.systems.profiles.Profile;
import vince.syshax.systems.profiles.Profiles;
import vince.syshax.utils.Utils;
import vince.syshax.utils.misc.NbtUtils;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class ProfilesTab extends Tab {
    public ProfilesTab() {
        super("Profiles");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new ProfilesScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof ProfilesScreen;
    }

    private static class ProfilesScreen extends WindowTabScreen {
        public ProfilesScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().minWidth(400).widget();
            initTable(table);

            add(theme.horizontalSeparator()).expandX();

            // Create
            WButton create = add(theme.button("Create")).expandX().widget();
            create.action = () -> SYSHax.mc.setScreen(new EditProfileScreen(theme, null, this::reload));
        }

        private void initTable(WTable table) {
            table.clear();
            if (Profiles.get().isEmpty()) return;

            for (Profile profile : Profiles.get()) {
                table.add(theme.label(profile.name.get())).expandCellX();

                WButton save = table.add(theme.button("Save")).widget();
                save.action = profile::save;

                WButton load = table.add(theme.button("Load")).widget();
                load.action = profile::load;

                WButton edit = table.add(theme.button(GuiRenderer.EDIT)).widget();
                edit.action = () -> SYSHax.mc.setScreen(new EditProfileScreen(theme, profile, this::reload));

                WMinus remove = table.add(theme.minus()).widget();
                remove.action = () -> {
                    Profiles.get().remove(profile);
                    reload();
                };

                table.row();
            }
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Profiles.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Profiles.get());
        }
    }

    private static class EditProfileScreen extends WindowScreen {
        private WContainer settingsContainer;
        private final Profile profile;
        private final boolean isNew;
        private final Runnable action;

        public EditProfileScreen(GuiTheme theme, Profile profile, Runnable action) {
            super(theme, profile == null ? "New Profile" : "Edit Profile");

            this.isNew = profile == null;
            this.profile = isNew ? new Profile() : profile;
            this.action = action;
        }

        @Override
        public void initWidgets() {
            settingsContainer = add(theme.verticalList()).expandX().minWidth(400).widget();
            settingsContainer.add(theme.settings(profile.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();

            WButton save = add(theme.button(isNew ? "Create" : "Save")).expandX().widget();
            save.action = () -> {
                if (profile.name.get().isEmpty()) return;

                if (isNew) {
                    for (Profile p : Profiles.get()) {
                        if (profile.equals(p)) return;
                    }
                }

                List<String> valid = new ArrayList<>();
                for (String address : profile.loadOnJoin.get()) {
                    if (Utils.resolveAddress(address)) valid.add(address);
                }

                profile.loadOnJoin.set(valid);

                if (isNew) Profiles.get().add(profile);
                else Profiles.get().save();

                close();
            };

            enterAction = save.action;
        }

        @Override
        public void tick() {
            profile.settings.tick(settingsContainer, theme);
        }

        @Override
        protected void onClosed() {
            if (action != null) action.run();
        }
    }
}
