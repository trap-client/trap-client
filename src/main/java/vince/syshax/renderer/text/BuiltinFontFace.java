/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.renderer.text;

import vince.syshax.utils.render.FontUtils;

import java.io.InputStream;

public class BuiltinFontFace extends FontFace {
    private final String name;

    public BuiltinFontFace(FontInfo info, String name) {
        super(info);

        this.name = name;
    }

    @Override
    public InputStream toStream() {
        InputStream in = FontUtils.stream(name);
        if (in == null) throw new RuntimeException("Failed to load builtin font " + name + ".");
        return in;
    }

    @Override
    public String toString() {
        return super.toString() + " (builtin)";
    }
}
