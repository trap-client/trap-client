/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.hud;

import java.util.Objects;

public class HudGroup {
    public final String title;

    public HudGroup(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HudGroup hudGroup = (HudGroup) o;
        return Objects.equals(title, hudGroup.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
