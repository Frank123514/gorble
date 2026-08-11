package net.got.client;

import net.got.climate.SeasonManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

// TODO (1.21.11): net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText no longer
// exists (or was at least renamed) in this NeoForge version - I could not find documentation
// confirming its replacement, and guessing at a hook name here risked silently doing nothing.
// Whole handler is disabled for now so the rest of the mod compiles; check NeoForge's own
// changelog/source for whichever event now lets you append lines to the F3 debug screen (the
// vanilla debug-text rendering path itself was also reworked as part of 1.21.11's Gizmos/
// DebugRenderer overhaul, so this may need more than a simple rename).
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class BiomeDebugOverlay {

    private BiomeDebugOverlay() {}

    /*
    @SubscribeEvent
    public static void onDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        Minecraft mc = Minecraft.getInstance();
        if (!mc.getDebugOverlay().showDebugScreen()) return;

        event.getRight().add("");
        event.getRight().add("[GoT] Season: " + SeasonManager.getCurrentSeason().name());
    }
    */
}
