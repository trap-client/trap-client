/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.tabs.builtin;

import vince.syshax.SYSHax;
import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.GuiThemes;
import vince.syshax.gui.tabs.Tab;
import vince.syshax.gui.tabs.TabScreen;
import vince.syshax.gui.tabs.WindowTabScreen;

import vince.syshax.gui.widgets.containers.WTable;
import vince.syshax.gui.widgets.input.WDropdown;
import vince.syshax.utils.misc.NbtUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.NbtCompound;

public class GuiTab extends Tab {
    public GuiTab() {
        super("GUI");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new GuiScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof GuiScreen;
    }

    private static class GuiScreen extends WindowTabScreen {
        public GuiScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            theme.settings.onActivated();
        }

        @Override
        public void initWidgets() {
            WTable table = add(theme.table()).expandX().widget();

            table.add(theme.label("Theme:"));
            WDropdown<String> themeW = table.add(theme.dropdown(GuiThemes.getNames(), GuiThemes.get().name)).widget();

            themeW.action = () -> {
                GuiThemes.select(themeW.get());



                SYSHax.mc.setScreen(null);
                tab.openScreen(GuiThemes.get());
            };



        }


        @Override
        public boolean toClipboard() {
            return NbtUtils.toClipboard(theme.name + " GUI Theme", theme.toTag());
        }

        @Override
        public boolean fromClipboard() {
            NbtCompound clipboard = NbtUtils.fromClipboard(theme.toTag());

            if (clipboard != null) {
                theme.fromTag(clipboard);
                return true;
            }

            return false;
        }


    }
}
