/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.events.render;

public class GetFovEvent {
    private static final GetFovEvent INSTANCE = new GetFovEvent();

    public double fov;

    public static GetFovEvent get(double fov) {
        INSTANCE.fov = fov;
        return INSTANCE;
    }
}
