/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.systems.modules.world;

import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;

public class AntiCactus extends Module {
    public AntiCactus() {
        super(Categories.World, "anti-cactus", "Prevents you from taking damage from cacti.");
    }
}
