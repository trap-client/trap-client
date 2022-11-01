/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.gui.widgets;

import vince.syshax.gui.renderer.GuiRenderer;
import vince.syshax.gui.widgets.containers.WContainer;

public abstract class WTooltip extends WContainer implements WRoot {
    private boolean valid;

    protected String text;

    public WTooltip(String text) {
        this.text = text;
    }

    @Override
    public void init() {
        add(theme.label(text)).pad(4);
    }

    @Override
    public void invalidate() {
        valid = false;
    }

    @Override
    public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!valid) {
            calculateSize();
            calculateWidgetPositions();

            valid = true;
        }

        return super.render(renderer, mouseX, mouseY, delta);
    }
}
