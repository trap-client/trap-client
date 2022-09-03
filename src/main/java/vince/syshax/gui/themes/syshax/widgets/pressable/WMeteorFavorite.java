/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.themes.syshax.widgets.pressable;


import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.pressable.WFavorite;
import vince.syshax.utils.render.color.Color;

public class WMeteorFavorite extends WFavorite implements SYSHaxWidget {
    public WMeteorFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
