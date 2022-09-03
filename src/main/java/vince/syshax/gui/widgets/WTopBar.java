/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.widgets;

import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.gui.tabs.Tab;
import vince.syshax.gui.tabs.TabScreen;
import vince.syshax.gui.tabs.Tabs;
import vince.syshax.gui.widgets.containers.WHorizontalList;
import vince.syshax.gui.widgets.pressable.WPressable;
import vince.syshax.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import vince.syshax.SYSHax;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public abstract class WTopBar extends WHorizontalList {
    protected abstract Color getButtonColor(boolean pressed, boolean hovered);

    protected abstract Color getNameColor();

    public WTopBar() {
        spacing = 0;
    }

    @Override
    public void init() {
        for (Tab tab : Tabs.get()) {
            add(new WTopBarButton(tab));
        }
    }

    protected class WTopBarButton extends WPressable {
        private final Tab tab;

        public WTopBarButton(Tab tab) {
            this.tab = tab;
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();

            width = pad + theme.textWidth(tab.name) + pad;
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onPressed(int button) {
            Screen screen = SYSHax.mc.currentScreen;

            if (!(screen instanceof TabScreen) || ((TabScreen) screen).tab != tab) {
                double mouseX = SYSHax.mc.mouse.getX();
                double mouseY = SYSHax.mc.mouse.getY();

                tab.openScreen(theme);
                glfwSetCursorPos(SYSHax.mc.getWindow().getHandle(), mouseX, mouseY);
            }
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            double pad = pad();
            Color color = getButtonColor(pressed || (SYSHax.mc.currentScreen instanceof TabScreen && ((TabScreen) SYSHax.mc.currentScreen).tab == tab), mouseOver);

            renderer.quad(x, y, width, height, color);
            renderer.text(tab.name, x + pad, y + pad, getNameColor(), false);
        }
    }
}
