/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.pressable.WTriangle;

public class WMeteorTriangle extends WTriangle implements SYSHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(x, y, width, height, rotation, GuiRenderer.TRIANGLE, theme().backgroundColor.get(pressed, mouseOver));
    }
}
