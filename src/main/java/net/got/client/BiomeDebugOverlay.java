package net.got.client;

import net.got.climate.SeasonManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class BiomeDebugOverlay {

    private BiomeDebugOverlay() {}

    @SubscribeEvent
    public static void onDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        Minecraft mc = Minecraft.getInstance();
        if (!mc.getDebugOverlay().showDebugScreen()) return;

        event.getRight().add("");
        event.getRight().add("[GoT] Season: " + SeasonManager.getCurrentSeason().name());
    }
}