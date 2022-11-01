/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.render.postprocess;

import vince.syshax.systems.modules.Modules;
import vince.syshax.systems.modules.render.ESP;
import net.minecraft.entity.Entity;
import vince.syshax.SYSHax;

public class EntityOutlineShader extends EntityShader {
    private static ESP esp;

    public EntityOutlineShader() {
        init("outline");
    }

    @Override
    protected boolean shouldDraw() {
        if (esp == null) esp = Modules.get().get(ESP.class);
        return esp.isShader();
    }

    @Override
    public boolean shouldDraw(Entity entity) {
        if (!shouldDraw()) return false;
        return esp.getOutlineColor(entity) != null && (entity != SYSHax.mc.player || !esp.ignoreSelf.get());
    }

    @Override
    protected void setUniforms() {
        shader.set("u_Width", esp.outlineWidth.get());
        shader.set("u_FillOpacity", esp.fillOpacity.get() / 255.0);
        shader.set("u_ShapeMode", esp.shapeMode.get().ordinal());
    }
}
