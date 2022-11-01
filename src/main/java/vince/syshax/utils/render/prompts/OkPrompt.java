/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.render.prompts;

import com.mojang.blaze3d.systems.RenderSystem;
import vince.syshax.gui.GuiTheme;
import vince.syshax.gui.GuiThemes;
import vince.syshax.gui.WindowScreen;
import vince.syshax.gui.widgets.containers.WHorizontalList;
import vince.syshax.gui.widgets.pressable.WButton;
import vince.syshax.gui.widgets.pressable.WCheckbox;
import vince.syshax.systems.config.Config;
import net.minecraft.client.gui.screen.Screen;
import vince.syshax.SYSHax;

import java.util.ArrayList;
import java.util.List;

public class OkPrompt {
    private final GuiTheme theme;
    private final Screen parent;

    private String title = "";
    private final List<String> messages = new ArrayList<>();
    private String id = null;

    private Runnable onOk = () -> {};

    private OkPrompt() {
        this(GuiThemes.get(), SYSHax.mc.currentScreen);
    }

    private OkPrompt(GuiTheme theme, Screen parent) {
        this.theme = theme;
        this.parent = parent;
    }

    public static OkPrompt create() {
        return new OkPrompt();
    }

    public static OkPrompt create(GuiTheme theme, Screen parent) {
        return new OkPrompt(theme, parent);
    }

    public OkPrompt title(String title) {
        this.title = title;
        return this;
    }

    public OkPrompt message(String message) {
        this.messages.add(message);
        return this;
    }

    public OkPrompt message(String message, Object... args) {
        this.messages.add(String.format(message, args));
        return this;
    }

    public OkPrompt id(String from) {
        this.id = from;
        return this;
    }

    public OkPrompt onOk(Runnable action) {
        this.onOk = action;
        return this;
    }

    public void show() {
        if (id == null) this.id(this.title);
        if (Config.get().dontShowAgainPrompts.contains(id)) return;

        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> SYSHax.mc.setScreen(new PromptScreen(theme)));
        }
        else {
            SYSHax.mc.setScreen(new PromptScreen(theme));
        }
    }

    private class PromptScreen extends WindowScreen {
        public PromptScreen(GuiTheme theme) {
            super(theme, OkPrompt.this.title);

            this.parent = OkPrompt.this.parent;
        }

        @Override
        public void initWidgets() {
            for (String line : messages) add(theme.label(line)).expandX();
            add(theme.horizontalSeparator()).expandX();

            WHorizontalList checkboxContainer = add(theme.horizontalList()).expandX().widget();
            WCheckbox dontShowAgainCheckbox = checkboxContainer.add(theme.checkbox(false)).widget();
            checkboxContainer.add(theme.label("Don't show this again.")).expandX();

            WHorizontalList list = add(theme.horizontalList()).expandX().widget();
            WButton okButton = list.add(theme.button("Ok")).expandX().widget();
            okButton.action = () -> {
                if (dontShowAgainCheckbox.checked) Config.get().dontShowAgainPrompts.add(id);
                onOk.run();
                close();
            };
        }
    }
}
