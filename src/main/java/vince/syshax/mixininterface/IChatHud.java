/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.mixininterface;

import net.minecraft.text.Text;

public interface IChatHud {
    void add(Text message, int id);
}
