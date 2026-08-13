package net.got.client;

import net.got.client.gui.MainMenuScreen;
import net.got.client.input.Keybinds;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class Client {

    private static boolean wasDown = false;

    public static void init() {
        NeoForge.EVENT_BUS.addListener(Client::onClientTick);
        RiverCurrentParticles.init();
        RiverCurrentClientPush.init();
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            wasDown = false;
            return;
        }

        boolean down = Keybinds.OPEN_MAP.isDown();

        if (down && !wasDown) {
            
            mc.setScreen(new MainMenuScreen());
        }

        wasDown = down;
    }
}