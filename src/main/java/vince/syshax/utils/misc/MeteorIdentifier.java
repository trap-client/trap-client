/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.misc;

import vince.syshax.SYSHax;
import net.minecraft.util.Identifier;

public class MeteorIdentifier extends Identifier {
    public MeteorIdentifier(String path) {
        super(SYSHax.MOD_ID, path);
    }
}
