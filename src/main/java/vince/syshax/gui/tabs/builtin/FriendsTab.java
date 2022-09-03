/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.tabs.builtin;

import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.tabs.Tab;
import vince.syshax.gui.tabs.TabScreen;
import vince.syshax.gui.tabs.WindowTabScreen;
import vince.syshax.gui.widgets.containers.WHorizontalList;
import vince.syshax.gui.widgets.containers.WTable;
import vince.syshax.gui.widgets.input.WTextBox;
import vince.syshax.gui.widgets.pressable.WMinus;
import vince.syshax.gui.widgets.pressable.WPlus;
import vince.syshax.systems.friends.Friend;
import vince.syshax.systems.friends.Friends;
import vince.syshax.utils.misc.NbtUtils;
import vince.syshax.utils.network.MeteorExecutor;
import net.minecraft.client.gui.screen.Screen;

public class FriendsTab extends Tab {
    public FriendsTab() {
        super("Friends");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new FriendsScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof FriendsScreen;
    }

    private static class FriendsScreen extends WindowTabScreen {
        public FriendsScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().minWidth(400).widget();
            initTable(table);

            add(theme.horizontalSeparator()).expandX();

            // New
            WHorizontalList list = add(theme.horizontalList()).expandX().widget();

            WTextBox nameW = list.add(theme.textBox("", (text, c) -> c != ' ')).expandX().widget();
            nameW.setFocused(true);

            WPlus add = list.add(theme.plus()).widget();
            add.action = () -> {
                String name = nameW.get().trim();
                Friend friend = new Friend(name);

                if (Friends.get().add(friend)) {
                    MeteorExecutor.execute(() -> {
                        friend.updateInfo();
                        nameW.set("");
                        reload();
                    });
                }
            };

            enterAction = add.action;
        }

        private void initTable(WTable table) {
            table.clear();
            if (Friends.get().isEmpty()) return;

            for (Friend friend : Friends.get()) {
                table.add(theme.texture(32, 32, friend.headTexture.needsRotate() ? 90 : 0, friend.headTexture));
                table.add(theme.label(friend.name));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    Friends.get().remove(friend);
                    reload();
                };

                table.row();
            }
        }

        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(Friends.get());
        }

        @Override
        public boolean fromClipboard() {
            return NbtUtils.fromClipboard(Friends.get());
        }
    }
}
