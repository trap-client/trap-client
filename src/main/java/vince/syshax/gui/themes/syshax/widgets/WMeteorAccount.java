/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.syshax.widgets;

import vince.syshax.gui.WidgetScreen;

import vince.syshax.gui.themes.syshax.SYSHaxWidget;
import vince.syshax.gui.widgets.WAccount;
import vince.syshax.systems.accounts.Account;
import vince.syshax.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements SYSHaxWidget {
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
