package net.got.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class GotMainMenuScreen extends Screen {

    public GotMainMenuScreen() {
        super(Component.literal("Game of Thrones"));
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int cy = height / 2;

        addRenderableWidget(
                Button.builder(
                        Component.literal("Map"),
                        b -> minecraft.setScreen(new GotMapScreen())
                ).bounds(cx - 60, cy - 10, 120, 20).build()
        );

        addRenderableWidget(
                Button.builder(
                        Component.literal("Close"),
                        b -> minecraft.setScreen(null)
                ).bounds(cx - 60, cy + 20, 120, 20).build()
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
