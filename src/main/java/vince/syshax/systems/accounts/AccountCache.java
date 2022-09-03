/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.systems.accounts;

import vince.syshax.utils.misc.ISerializable;
import vince.syshax.utils.misc.NbtException;
import vince.syshax.utils.render.PlayerHeadTexture;
import vince.syshax.utils.render.PlayerHeadUtils;
import net.minecraft.nbt.NbtCompound;

public class AccountCache implements ISerializable<AccountCache> {
    private PlayerHeadTexture headTexture;
    public String username = "";
    public String uuid = "";

    public PlayerHeadTexture getHeadTexture() {
        return headTexture;
    }

    public void loadHead() {
        headTexture = PlayerHeadUtils.fetchHead(username);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("username", username);
        tag.putString("uuid", uuid);

        return tag;
    }

    @Override
    public AccountCache fromTag(NbtCompound tag) {
        if (!tag.contains("username") || !tag.contains("uuid")) throw new NbtException();

        username = tag.getString("username");
        uuid = tag.getString("uuid");
        loadHead();

        return this;
    }
}
