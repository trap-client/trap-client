/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.addons;

public record GithubRepo(String owner, String name, String branch) {
    public GithubRepo(String owner, String name) {
        this(owner, name, "master");
    }

    public String getOwnerName() {
        return owner + "/" + name;
    }
}
