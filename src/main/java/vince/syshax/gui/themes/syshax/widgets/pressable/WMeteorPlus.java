/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.syshax.SYSHaxGuiTheme;
import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.pressable.WPlus;

public class WMeteorPlus extends WPlus implements SYSHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        SYSHaxGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
