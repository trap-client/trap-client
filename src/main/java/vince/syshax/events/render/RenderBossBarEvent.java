/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.events.render;

import vince.syshax.events.Cancellable;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.text.Text;

import java.util.Iterator;

public class RenderBossBarEvent extends Cancellable {
    public static class BossText {
        private static final BossText INSTANCE = new BossText();

        public ClientBossBar bossBar;
        public Text name;

        public static BossText get(ClientBossBar bossBar, Text name) {
            INSTANCE.bossBar = bossBar;
            INSTANCE.name = name;
            return INSTANCE;
        }
    }

    public static class BossSpacing {
        private static final BossSpacing INSTANCE = new BossSpacing();

        public int spacing;

        public static BossSpacing get(int spacing) {
            INSTANCE.spacing = spacing;
            return INSTANCE;
        }
    }

    public static class BossIterator {
        private static final BossIterator INSTANCE = new BossIterator();

        public Iterator<ClientBossBar> iterator;

        public static BossIterator get(Iterator<ClientBossBar> iterator) {
            INSTANCE.iterator = iterator;
            return INSTANCE;
        }
    }
}
