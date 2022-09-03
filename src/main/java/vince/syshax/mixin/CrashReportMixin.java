/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package vince.syshax.mixin;

import vince.syshax.SYSHax;
import vince.syshax.systems.modules.Category;
import vince.syshax.systems.modules.Module;
import vince.syshax.systems.modules.Modules;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrashReport.class)
public class CrashReportMixin {
    @Inject(method = "addStackTrace", at = @At("TAIL"))
    private void onAddStackTrace(StringBuilder sb, CallbackInfo info) {
        if (Modules.get() != null) {
            sb.append("\n\n");
            sb.append("-- Meteor Client --\n");
            sb.append("Version: ").append(SYSHax.VERSION).append("\n");

            if (!SYSHax.DEV_BUILD.isEmpty()) {
                sb.append("Dev Build: ").append(SYSHax.DEV_BUILD).append("\n");
            }

            for (Category category : Modules.loopCategories()) {
                List<Module> modules = Modules.get().getGroup(category);
                boolean active = false;

                for (Module module : modules) {
                    if (module != null && module.isActive()) {
                        active = true;
                        break;
                    }
                }

                if (active) {
                    sb.append("\n");
                    sb.append("[").append(category).append("]:").append("\n");

                    for (Module module : modules) {
                        if (module != null && module.isActive()) {
                            sb.append(module.name).append("\n");
                        }
                    }
                }
            }
        }
    }
}
