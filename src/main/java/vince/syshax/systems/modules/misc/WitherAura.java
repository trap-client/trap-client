/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.systems.modules.misc;

import java.util.HashMap;
import vince.syshax.events.game.GameLeftEvent;
import vince.syshax.events.game.OpenScreenEvent;
import vince.syshax.events.world.TickEvent;
import vince.syshax.settings.*;
import vince.syshax.settings.*;
import vince.syshax.systems.modules.Categories;
import vince.syshax.systems.modules.Module;
import vince.syshax.utils.Utils;
import vince.syshax.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import vince.syshax.events.render.Render3DEvent;
import vince.syshax.renderer.ShapeMode;
import vince.syshax.utils.player.FindItemResult;
import vince.syshax.utils.player.InvUtils;
import vince.syshax.utils.player.PlayerUtils;
import vince.syshax.utils.render.color.SettingColor;
import vince.syshax.utils.world.BlockUtils;

public class WitherAura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
            .name("range")
            .description("How far the wither will be placed around you.")
            .defaultValue(3)
            .min(1)
            .sliderRange(1, 7)
            .build()
    );
    
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
            .name("delay")
            .description("Block place delay")
            .defaultValue(2)
            .min(0)
            .sliderRange(0, 20)
            .build()
    );
    
    private final Setting<Boolean> lockmove = sgGeneral.add(new BoolSetting.Builder()
            .name("lock-move")
            .description("Locks your movement.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> turnOff = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-toggle")
            .description("Toggles off after spawn all withers")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
            .name("rotate")
            .description("Forces you to rotate downwards when placing blocks.")
            .defaultValue(true)
            .build()
    );
    
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
            .name("shape-mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
            .name("side-color")
            .description("The color of the sides of the blocks being rendered.")
            .defaultValue(new SettingColor(204, 0, 0, 10))
            .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
            .name("line-color")
            .description("The color of the lines of the blocks being rendered.")
            .defaultValue(new SettingColor(204, 0, 0, 255))
            .build()
    );

    private final HashMap<BlockPos, FindItemResult> blocks = new HashMap<>();
    private int crrDelay;
    
    public WitherAura() {
        super(Categories.Misc, "wither-aura", "Automatically spawn withers around you.");
    }
    
    @Override
    public void onActivate() {
        blocks.clear();
        queueBlocks();
        crrDelay = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        
        if(crrDelay >= delay.get() && !blocks.isEmpty()) {
            BlockPos pos = (BlockPos) blocks.keySet().toArray()[0];
            FindItemResult block = blocks.get(pos);
            
            if(!block.found() || block.count() < 1) return;
            
            blocks.remove(pos);
            BlockUtils.place(pos, block, rotate.get(), 0, false);
            crrDelay = 0;
        } else
        if(crrDelay >= delay.get() && blocks.isEmpty()) {
            if (turnOff.get()) {toggle();}
        }
        if (lockmove.get()) PlayerUtils.centerPlayer();
        crrDelay++;
    }
    
    private void queueBlocks() {
        Wither();
    }
    
    private void Wither() {
        FindItemResult skull = InvUtils.findInHotbar(Items.WITHER_SKELETON_SKULL);
        FindItemResult sand = InvUtils.findInHotbar(Items.SOUL_SAND);
        
        int x = range.get();
        
        // X Axys
        // Wither 1
        blocks.put(calcPos(x, 0, 0), sand);
        blocks.put(calcPos(x - 1, 0, 0), sand);
        blocks.put(calcPos(x - 1, 0, -1), sand);
        blocks.put(calcPos(x - 1, 0, 1), sand);
        
        blocks.put(calcPos(x - 2, 0, 0), skull);
        blocks.put(calcPos(x - 2, 0, -1), skull);
        blocks.put(calcPos(x - 2, 0, 1), skull);
        
        // Wither 2
        blocks.put(calcPos(-x, 0, 0), sand);
        blocks.put(calcPos(-x + 1, 0, 0), sand);
        blocks.put(calcPos(-x + 1, 0, -1), sand);
        blocks.put(calcPos(-x + 1, 0, 1), sand);
        
        blocks.put(calcPos(-x + 2, 0, 0), skull);
        blocks.put(calcPos(-x + 2, 0, -1), skull);
        blocks.put(calcPos(-x + 2, 0, 1), skull);
        
        // Z Axys
        // Wither 3
        blocks.put(calcPos(0, 0, x), sand);
        blocks.put(calcPos(0, 0, x - 1), sand);
        blocks.put(calcPos(-1, 0, x - 1), sand);
        blocks.put(calcPos(1, 0, x - 1), sand);
        
        blocks.put(calcPos(0, 0, x - 2), skull);
        blocks.put(calcPos(-1, 0, x - 2), skull);
        blocks.put(calcPos(1, 0, x - 2), skull);
        
        // Wither 4
        blocks.put(calcPos(0, 0, -x), sand);
        blocks.put(calcPos(0, 0, -x + 1), sand);
        blocks.put(calcPos(-1, 0, -x + 1), sand);
        blocks.put(calcPos(1, 0, -x + 1), sand);
        
        blocks.put(calcPos(0, 0, -x + 2), skull);
        blocks.put(calcPos(-1, 0, -x + 2), skull);
        blocks.put(calcPos(1, 0, -x + 2), skull);
    }
    

    private BlockPos calcPos(int x, int y, int z) {
        return mc.player.getBlockPos().add(x, y, z);
    }
    
    @EventHandler
    private void onRender(Render3DEvent event) {
        if(blocks.isEmpty()) return;
        for(BlockPos pos : blocks.keySet()) {
            event.renderer.box(pos, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
        }
    }
}
