package net.got.client;

import net.got.client.gui.GotMainMenuScreen;
import net.got.client.input.GotKeybinds;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class GotClient {

    private static boolean wasDown = false;

    public static void init() {
        NeoForge.EVENT_BUS.addListener(GotClient::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            wasDown = false;
            return;
        }

        boolean down = GotKeybinds.OPEN_MAP.isDown();

        if (down && !wasDown) {
            // FORCE MENU — NOT MAP
            mc.setScreen(new GotMainMenuScreen());
        }

        wasDown = down;
    }
}
