/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.utils;

import vince.syshax.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;

public class WindowConfig implements ISerializable<WindowConfig> {
    public boolean expanded = true;
    public double x = -1;
    public double y = -1;

    // Saving

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putBoolean("expanded", expanded);
        tag.putDouble("x", x);
        tag.putDouble("y", y);

        return tag;
    }

    @Override
    public WindowConfig fromTag(NbtCompound tag) {
        expanded = tag.getBoolean("expanded");
        x = tag.getDouble("x");
        y = tag.getDouble("y");

        return this;
    }
}
