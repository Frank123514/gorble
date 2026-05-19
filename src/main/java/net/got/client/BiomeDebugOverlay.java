package net.got.client;

import net.got.climate.SeasonManager;
import net.got.climate.WinterBiomeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;

/**
 * Appends GoT climate debug info to the F3 overlay.
 *
 * <p>Shows the current biome's live temperature (post-mutation if winter is
 * applied), its precipitation flag, and whether WinterBiomeManager has
 * applied its mutations.  Use this to verify that {@link WinterBiomeManager}
 * is actually changing the biome temperature at runtime.
 *
 * <p>Lines shown (F3 screen, right-hand column):
 * <pre>
 *   [GoT] Season: Winter | Winter applied: true
 *   [GoT] Biome temp: 0.000 | precip: true | SNOW expected
 * </pre>
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public final class BiomeDebugOverlay {

    private BiomeDebugOverlay() {}

    @SubscribeEvent
    public static void onDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        Minecraft mc = Minecraft.getInstance();

        // Only show when F3 is open
        if (!mc.getDebugOverlay().showDebugScreen()) return;
        if (mc.level == null || mc.player == null) return;

        BlockPos pos = mc.player.blockPosition();
        Holder<Biome> biomeHolder = mc.level.getBiome(pos);
        Biome biome = biomeHolder.value();

        float temp = biome.getBaseTemperature();
        boolean hasPrecip = biome.hasPrecipitation();

        // Vanilla snow threshold: temp < 0.15 at ground level (no altitude modifier at y=64)
        // At the player's actual Y, getTemperature() applies the altitude modifier.
        float tempAtPos = biome.getTemperature(pos, pos.getY());
        boolean expectSnow = hasPrecip && tempAtPos < 0.15f;

        String seasonLine = String.format("[GoT] Season: %s  |  Winter mutations: %s",
                SeasonManager.getCurrentSeason().name(),
                WinterBiomeManager.isWinterApplied() ? "APPLIED" : "not applied");

        String biomeLine = String.format("[GoT] Biome base temp: %.3f  |  temp@pos: %.3f  |  precip: %s  |  %s",
                temp,
                tempAtPos,
                hasPrecip ? "yes" : "no",
                expectSnow ? "SNOW expected" : "RAIN (too warm for snow)");

        // Append to the right-hand side of the F3 screen
        event.getRight().add("");  // spacer
        event.getRight().add(seasonLine);
        event.getRight().add(biomeLine);
    }
}