/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.syshax.SYSHaxGuiTheme;
import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.WVerticalSeparator;
import vince.syshax.utils.render.color.Color;

public class WMeteorVerticalSeparator extends WVerticalSeparator implements SYSHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        SYSHaxGuiTheme theme = theme();
        Color colorEdges = theme.separatorEdges.get();
        Color colorCenter = theme.separatorCenter.get();

        double s = theme.scale(1);
        double offsetX = Math.round(width / 2.0);

        renderer.quad(x + offsetX, y, s, height / 2, colorEdges, colorEdges, colorCenter, colorCenter);
        renderer.quad(x + offsetX, y + height / 2, s, height / 2, colorCenter, colorCenter, colorEdges, colorEdges);
    }
}
