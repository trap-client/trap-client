/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.accounts.types;

import vince.syshax.systems.accounts.Account;
import vince.syshax.systems.accounts.AccountType;
import vince.syshax.systems.accounts.MicrosoftLogin;
import net.minecraft.client.util.Session;

import java.util.Optional;

public class MicrosoftAccount extends Account<MicrosoftAccount> {
    public MicrosoftAccount(String refreshToken) {
        super(AccountType.Microsoft, refreshToken);
    }

    @Override
    public boolean fetchInfo() {
        return auth() != null;
    }

    @Override
    public boolean login() {
        super.login();

        String token = auth();
        if (token == null) return false;

        cache.loadHead();

        setSession(new Session(cache.username, cache.uuid, token, Optional.empty(), Optional.empty(), Session.AccountType.MSA));
        return true;
    }

    private String auth() {
        MicrosoftLogin.LoginData data = MicrosoftLogin.login(name);
        if (!data.isGood()) return null;

        name = data.newRefreshToken;
        cache.username = data.username;
        cache.uuid = data.uuid;

        return data.mcToken;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MicrosoftAccount)) return false;
        return ((MicrosoftAccount) o).name.equals(this.name);
    }
}
