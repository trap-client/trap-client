/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.movement;

import vince.syshax.events.world.TickEvent;
import vince.syshax.settings.DoubleSetting;
import vince.syshax.settings.Setting;
import vince.syshax.settings.SettingGroup;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public class FastClimb extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
            .name("climb-speed")
            .description("Your climb speed.")
            .defaultValue(0.2872)
            .min(0.0)
            .build()
    );

    public FastClimb() {
        super(Categories.Movement, "fast-climb", "Allows you to climb faster.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.isClimbing() || !mc.player.horizontalCollision) return;
        if (mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) return;

        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
    }
}
