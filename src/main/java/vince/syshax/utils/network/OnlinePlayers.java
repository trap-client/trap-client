/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.network;

public class OnlinePlayers {
    private static long lastPingTime;

    public static void update() {
        long time = System.currentTimeMillis();

        if (time - lastPingTime > 5 * 60 * 1000) {
            MeteorExecutor.execute(() -> Http.post("https://meteorclient.com/api/online/ping").send());

            lastPingTime = time;
        }
    }

    public static void leave() {
        MeteorExecutor.execute(() -> Http.post("https://meteorclient.com/api/online/leave").send());
    }
}
