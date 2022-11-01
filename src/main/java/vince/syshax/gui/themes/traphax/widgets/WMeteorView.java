/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.themes.traphax.widgets;

import vince.syshax.gui.renderer.GuiRenderer;

import vince.syshax.gui.themes.traphax.TrapHaxWidget;
import vince.syshax.gui.widgets.containers.WView;

public class WMeteorView extends WView implements TrapHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (canScroll && hasScrollBar) {
            renderer.quad(handleX(), handleY(), handleWidth(), handleHeight(), theme().scrollbarColor.get(handlePressed, handleMouseOver));
        }
    }
}
