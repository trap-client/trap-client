/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.themes.traphax.widgets;

import vince.syshax.gui.WidgetScreen;
import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.WAccount;
import vince.syshax.systems.accounts.Account;
import vince.syshax.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements TrapHaxWidget {
    public WMeteorAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}