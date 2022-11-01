/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.events.entity.player;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class InteractItemEvent {
    private static final InteractItemEvent INSTANCE = new InteractItemEvent();

    public Hand hand;
    public ActionResult toReturn;

    public static InteractItemEvent get(Hand hand) {
        INSTANCE.hand = hand;
        INSTANCE.toReturn = null;

        return INSTANCE;
    }
}
