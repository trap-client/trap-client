/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.traphax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.pressable.WMinus;

public class WMeteorMinus extends WMinus implements TrapHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme().minusColor.get());
    }
}
