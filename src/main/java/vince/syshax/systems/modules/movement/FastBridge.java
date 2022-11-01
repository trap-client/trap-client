/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */


package vince.syshax.systems.modules.movement;


import meteordevelopment.orbit.EventHandler;
import vince.syshax.events.world.TickEvent;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;


public class FastBridge extends Module {
    public FastBridge() {
        super(Categories.Movement, "Fast bridge", "Automatically sneaks at block edge (idea by kokqi).");
    }

    boolean turn = true;
    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.world.getBlockState(mc.player.getSteppingPos()).isAir()) {
            if (!mc.player.isOnGround()) return;
            turn = true;
            mc.options.sneakKey.setPressed(true);
        } else if (turn) {
            turn = false;
            mc.options.sneakKey.setPressed(false);
        }
    }
}
