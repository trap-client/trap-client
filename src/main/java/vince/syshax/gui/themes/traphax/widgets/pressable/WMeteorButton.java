/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.themes.traphax.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.gui.renderer.packer.GuiTexture;


import vince.syshax.gui.themes.traphax.TrapGuiTheme;
import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.pressable.WButton;

public class WMeteorButton extends WButton implements TrapHaxWidget {
    public WMeteorButton(String text, GuiTexture texture) {
        super(text, texture);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        TrapGuiTheme theme = theme();
        double pad = pad();

        renderBackground(renderer, this, pressed, mouseOver);

        if (text != null) {
            renderer.text(text, x + width / 2 - textWidth / 2, y + pad, theme.textColor.get(), false);
        }
        else {
            double ts = theme.textHeight();
            renderer.quad(x + width / 2 - ts / 2, y + pad, ts, ts, texture, theme.textColor.get());
        }
    }
}
