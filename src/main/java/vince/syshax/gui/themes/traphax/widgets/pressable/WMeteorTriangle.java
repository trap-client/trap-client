/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.themes.traphax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.pressable.WTriangle;

public class WMeteorTriangle extends WTriangle implements TrapHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(x, y, width, height, rotation, GuiRenderer.TRIANGLE, theme().backgroundColor.get(pressed, mouseOver));
    }
}
