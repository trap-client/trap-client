/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.tabs.screens;

import vince.syshax.SYSHax;
import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.WindowScreen;
import vince.syshax.gui.widgets.WAccount;
import vince.syshax.gui.widgets.containers.WContainer;
import vince.syshax.gui.widgets.containers.WHorizontalList;
import vince.syshax.gui.widgets.pressable.WButton;
import vince.syshax.systems.accounts.Account;
import vince.syshax.systems.accounts.Accounts;
import vince.syshax.systems.accounts.MicrosoftLogin;
import vince.syshax.systems.accounts.types.MicrosoftAccount;
import vince.syshax.utils.misc.NbtUtils;
import vince.syshax.utils.network.MeteorExecutor;
import org.jetbrains.annotations.Nullable;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen(GuiTheme theme) {
        super(theme, "Accounts");
    }

    @Override
    public void initWidgets() {
        // Accounts
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = add(theme.account(this, account)).expandX().widget();
            wAccount.refreshScreenAction = this::reload;
        }

        // Add account
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        addButton(l, "Cracked", () -> SYSHax.mc.setScreen(new AddCrackedAccountScreen(theme, this)));
        addButton(l, "Altening", () -> SYSHax.mc.setScreen(new AddAlteningAccountScreen(theme, this)));
        addButton(l, "Microsoft", () -> {
            locked = true;

            MicrosoftLogin.getRefreshToken(refreshToken -> {
                locked = false;

                if (refreshToken != null) {
                    MicrosoftAccount account = new MicrosoftAccount(refreshToken);
                    addAccount(null, this, account);
                }
            });
        });
    }

    private void addButton(WContainer c, String text, Runnable action) {
        WButton button = c.add(theme.button(text)).expandX().widget();
        button.action = action;
    }

    public static void addAccount(@Nullable AddAccountScreen screen, AccountsScreen parent, Account<?> account) {
        if (screen != null) screen.locked = true;

        MeteorExecutor.execute(() -> {
            if (account.fetchInfo()) {
                account.getCache().loadHead();

                Accounts.get().add(account);
                if (account.login()) Accounts.get().save();

                if (screen != null) {
                    screen.locked = false;
                    screen.close();
                }

                parent.reload();

                return;
            }

            if (screen != null) screen.locked = false;
        });
    }

    @Override
    public boolean toClipboard() {
        return NbtUtils.toClipboard(Accounts.get());
    }

    @Override
    public boolean fromClipboard() {
        return NbtUtils.fromClipboard(Accounts.get());
    }
}
