/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.mixin;

import net.minecraft.client.font.TextHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextHandler.class)
public interface TextHandlerAccessor {
    @Accessor("widthRetriever")
    TextHandler.WidthRetriever getWidthRetriever();
}
