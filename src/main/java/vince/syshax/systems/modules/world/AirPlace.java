/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.world;

import vince.syshax.events.render.Render3DEvent;
import vince.syshax.events.world.TickEvent;
import vince.syshax.renderer.ShapeMode;
import vince.syshax.settings.*;
import vince.syshax.settings.*;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;
import vince.syshax.utils.render.color.SettingColor;
import vince.syshax.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AirPlace extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRange = settings.createGroup("Range");

    // General

    private final Setting<Boolean> render = sgGeneral.add(new BoolSetting.Builder()
        .name("render")
        .description("Renders a block overlay where the obsidian will be placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgGeneral.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgGeneral.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The color of the sides of the blocks being rendered.")
        .defaultValue(new SettingColor(204, 0, 0, 10))
        .build()
    );

    private final Setting<SettingColor> lineColor = sgGeneral.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The color of the lines of the blocks being rendered.")
        .defaultValue(new SettingColor(204, 0, 0, 255))
        .build()
    );

    // Range

    private final Setting<Boolean> customRange = sgRange.add(new BoolSetting.Builder()
        .name("custom-range")
        .description("Use custom range for air place.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> range = sgRange.add(new DoubleSetting.Builder()
        .name("range")
        .description("Custom range to place at.")
        .visible(customRange::get)
        .defaultValue(5)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private HitResult hitResult;

    public AirPlace() {
        super(Categories.Player, "air-place", "Places a block where your crosshair is pointing at.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        double r = customRange.get() ? range.get() : mc.interactionManager.getReachDistance();
        hitResult = mc.getCameraEntity().raycast(r, 0, false);

        if (!(hitResult instanceof BlockHitResult) || !(mc.player.getMainHandStack().getItem() instanceof BlockItem)) return;

        if (mc.options.useKey.isPressed()) {
            BlockUtils.place(((BlockHitResult) hitResult).getBlockPos(), Hand.MAIN_HAND, mc.player.getInventory().selectedSlot, false, 0, true, true, false);
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (!(hitResult instanceof BlockHitResult)
            || !mc.world.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getMaterial().isReplaceable()
            || !(mc.player.getMainHandStack().getItem() instanceof BlockItem)
            || !render.get()) return;

        event.renderer.box(((BlockHitResult) hitResult).getBlockPos(), sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }
}
