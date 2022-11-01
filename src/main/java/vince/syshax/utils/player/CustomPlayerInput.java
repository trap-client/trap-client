/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.player;

import net.minecraft.client.input.Input;

public class CustomPlayerInput extends Input {
    @Override
    public void tick(boolean slowDown, float f) {
        movementForward = pressingForward == pressingBack ? 0.0F : (pressingForward ? 1.0F : -1.0F);
        movementSideways = pressingLeft == pressingRight ? 0.0F : (pressingLeft ? 1.0F : -1.0F);

        if (sneaking) {
            movementForward *= 0.3;
            movementSideways *= 0.3;
        }
    }

    public void stop() {
        pressingForward = false;
        pressingBack = false;
        pressingRight = false;
        pressingLeft = false;
        jumping = false;
        sneaking = false;
    }
}
