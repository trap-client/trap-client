/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.gui.widgets;

import vince.syshax.SYSHax;
import vince.syshax.gui.WidgetScreen;
import vince.syshax.gui.widgets.containers.WHorizontalList;
import vince.syshax.gui.widgets.pressable.WButton;
import vince.syshax.gui.widgets.pressable.WMinus;
import vince.syshax.systems.accounts.Account;
import vince.syshax.systems.accounts.Accounts;
import vince.syshax.utils.network.MeteorExecutor;
import vince.syshax.utils.render.color.Color;

public abstract class WAccount extends WHorizontalList {
    public Runnable refreshScreenAction;

    private final WidgetScreen screen;
    private final Account<?> account;

    public WAccount(WidgetScreen screen, Account<?> account) {
        this.screen = screen;
        this.account = account;
    }

    protected abstract Color loggedInColor();
    protected abstract Color accountTypeColor();

    @Override
    public void init() {
        // Head
        add(theme.texture(32, 32, account.getCache().getHeadTexture().needsRotate() ? 90 : 0, account.getCache().getHeadTexture()));

        // Name
        WLabel name = add(theme.label(account.getUsername())).widget();
        if (SYSHax.mc.getSession().getUsername().equalsIgnoreCase(account.getUsername())) name.color = loggedInColor();

        // Type
        WLabel label = add(theme.label("(" + account.getType() + ")")).expandCellX().right().widget();
        label.color = accountTypeColor();

        // Login
        WButton login = add(theme.button("Login")).widget();
        login.action = () -> {
            login.minWidth = login.width;
            login.set("...");
            screen.locked = true;

            MeteorExecutor.execute(() -> {
                if (account.login()) {
                    name.set(account.getUsername());

                    Accounts.get().save();

                    screen.taskAfterRender = refreshScreenAction;
                }

                login.minWidth = 0;
                login.set("Login");
                screen.locked = false;
            });
        };

        // Remove
        WMinus remove = add(theme.minus()).widget();
        remove.action = () -> {
            Accounts.get().remove(account);
            if (refreshScreenAction != null) refreshScreenAction.run();
        };
    }
}
