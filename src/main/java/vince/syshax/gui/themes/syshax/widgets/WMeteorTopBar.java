/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets;

import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.WTopBar;
import vince.syshax.utils.render.color.Color;

public class WMeteorTopBar extends WTopBar implements SYSHaxWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
