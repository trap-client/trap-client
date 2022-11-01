/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;


import vince.syshax.gui.themes.syshax.SYSHaxGuiTheme;
import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.pressable.WCheckbox;
import vince.syshax.utils.Utils;

public class WMeteorCheckbox extends WCheckbox implements SYSHaxWidget {
    private double animProgress;

    public WMeteorCheckbox(boolean checked) {
        super(checked);
        animProgress = checked ? 1 : 0;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        SYSHaxGuiTheme theme = theme();

        animProgress += (checked ? 1 : -1) * delta * 14;
        animProgress = Utils.clamp(animProgress, 0, 1);

        renderBackground(renderer, this, pressed, mouseOver);

        if (animProgress > 0) {
            double cs = (width - theme.scale(2)) / 1.75 * animProgress;
            renderer.quad(x + (width - cs) / 2, y + (height - cs) / 2, cs, cs, theme.checkboxColor.get());
        }
    }
}
