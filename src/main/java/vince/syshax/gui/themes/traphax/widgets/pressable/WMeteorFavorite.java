/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.traphax.widgets.pressable;


import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.pressable.WFavorite;
import vince.syshax.utils.render.color.Color;

public class WMeteorFavorite extends WFavorite implements TrapHaxWidget {
    public WMeteorFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
