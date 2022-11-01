/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.events.entity;

import net.minecraft.entity.Entity;

public class EntityDestroyEvent {
    private static final EntityDestroyEvent INSTANCE = new EntityDestroyEvent();

    public Entity entity;

    public static EntityDestroyEvent get(Entity entity) {
        INSTANCE.entity = entity;
        return INSTANCE;
    }
}
