/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import vince.syshax.systems.modules.Modules;
import vince.syshax.systems.modules.misc.InventoryTweaks;
import vince.syshax.utils.render.ContainerButtonWidget;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin extends HandledScreen<ShulkerBoxScreenHandler> {
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        InventoryTweaks invTweaks = Modules.get().get(InventoryTweaks.class);

        if (invTweaks.isActive() && invTweaks.showButtons()) {
            addDrawableChild(new ContainerButtonWidget(
                x + backgroundWidth - 88,
                y + 3,
                40,
                12,
                Text.literal("Steal"),
                button -> invTweaks.steal(handler))
            );

            addDrawableChild(new ContainerButtonWidget(
                x + backgroundWidth - 46,
                y + 3,
                40,
                12,
                Text.literal("Dump"),
                button -> invTweaks.dump(handler))
            );
        }

        if (invTweaks.autoSteal()) invTweaks.steal(handler);
        if (invTweaks.autoDump()) invTweaks.dump(handler);
    }
}
