/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.utils.render;

import com.google.gson.Gson;
import vince.syshax.systems.accounts.ProfileResponse;
import vince.syshax.systems.accounts.TexturesJson;
import vince.syshax.systems.accounts.UuidToProfileResponse;
import vince.syshax.utils.PostInit;
import vince.syshax.utils.network.Http;

import java.util.Base64;

public class PlayerHeadUtils {
    private static PlayerHeadTexture STEVE_HEAD;

    @PostInit
    public static void init() {
        STEVE_HEAD = new PlayerHeadTexture();
    }

    public static PlayerHeadTexture fetchHead(String username) {
        String url = getSkinUrl(username);
        if (url == null) return STEVE_HEAD;

        return new PlayerHeadTexture(url);
    }

    public static String getSkinUrl(String username) {
        ProfileResponse res = Http.get("https://api.mojang.com/users/profiles/minecraft/" + username).sendJson(ProfileResponse.class);
        if (res == null) return null;

        UuidToProfileResponse res2 = Http.get("https://sessionserver.mojang.com/session/minecraft/profile/" + res.id).sendJson(UuidToProfileResponse.class);
        if (res2 == null) return null;

        String base64Textures = res2.getPropertyValue("textures");
        if (base64Textures == null) return null;

        TexturesJson textures = new Gson().fromJson(new String(Base64.getDecoder().decode(base64Textures)), TexturesJson.class);
        if (textures.textures.SKIN == null) return null;

        return textures.textures.SKIN.url;
    }
}
