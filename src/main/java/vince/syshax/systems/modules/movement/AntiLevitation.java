/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.movement;

import vince.syshax.settings.BoolSetting;
import vince.syshax.settings.Setting;
import vince.syshax.settings.SettingGroup;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;

public class AntiLevitation extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> applyGravity = sgGeneral.add(new BoolSetting.Builder()
            .name("gravity")
            .description("Applies gravity.")
            .defaultValue(false)
            .build()
    );

    public AntiLevitation() {
        super(Categories.Movement, "anti-levitation", "Prevents the levitation effect from working.");
    }

    public boolean isApplyGravity() {
        return applyGravity.get();
    }
}
