/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.widgets.pressable;

import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.utils.render.color.Color;

public abstract class WFavorite extends WPressable {
    public boolean checked;

    public WFavorite(boolean checked) {
        this.checked = checked;
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();
        double s = theme.textHeight();

        width = pad + s + pad;
        height = pad + s + pad;
    }

    @Override
    protected void onPressed(int button) {
        checked = !checked;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.textHeight();

        renderer.quad(x + pad, y + pad, s, s, checked ? GuiRenderer.FAVORITE_YES : GuiRenderer.FAVORITE_NO, getColor());
    }

    protected abstract Color getColor();
}
