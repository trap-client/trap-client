/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.settings;

import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.WidgetScreen;
import vince.syshax.utils.misc.IChangeable;
import vince.syshax.utils.misc.ICopyable;
import vince.syshax.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
