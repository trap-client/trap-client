/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.tabs.screens.settings;

import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.widgets.WWidget;
import vince.syshax.mixin.IdentifierAccessor;
import vince.syshax.settings.BlockListSetting;
import vince.syshax.settings.Setting;
import vince.syshax.utils.misc.Names;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Predicate;

public class BlockListSettingScreen extends LeftRightListSettingScreen<Block> {
    private static final Identifier ID = new Identifier("minecraft", "");

    public BlockListSettingScreen(GuiTheme theme, Setting<List<Block>> setting) {
        super(theme, "Select Blocks", setting, setting.get(), Registry.BLOCK);
    }

    @Override
    protected boolean includeValue(Block value) {
        Predicate<Block> filter = ((BlockListSetting) setting).filter;

        if (filter == null) return value != Blocks.AIR;
        return filter.test(value);
    }

    @Override
    protected WWidget getValueWidget(Block value) {
        return theme.itemWithLabel(value.asItem().getDefaultStack(), getValueName(value));
    }

    @Override
    protected String getValueName(Block value) {
        return Names.get(value);
    }

    @Override
    protected boolean skipValue(Block value) {
        return Registry.BLOCK.getId(value).getPath().endsWith("_wall_banner");
    }

    @Override
    protected Block getAdditionalValue(Block value) {
        String path = Registry.BLOCK.getId(value).getPath();
        if (!path.endsWith("_banner")) return null;

        ((IdentifierAccessor) ID).setPath(path.substring(0, path.length() - 6) + "wall_banner");
        return Registry.BLOCK.get(ID);
    }
}
