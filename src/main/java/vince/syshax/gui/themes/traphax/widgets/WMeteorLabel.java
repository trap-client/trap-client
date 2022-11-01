/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.traphax.widgets;

import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.gui.themes.traphax.TrapHaxWidget;;
import vince.syshax.gui.widgets.WLabel;

public class WMeteorLabel extends WLabel implements TrapHaxWidget {
    public WMeteorLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
