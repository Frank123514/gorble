package net.got.client.gui.overlay;

import net.got.climate.GotSeason;
import net.got.climate.PlayerTemperatureSystem;
import net.got.climate.SeasonManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.minecraft.resources.ResourceLocation;

/**
 * HUD overlay that shows the player's current temperature during Winter.
 *
 * <h3>Visual design</h3>
 * Rendered in the bottom-left corner, just above the hotbar area, so it
 * doesn't compete with vanilla status bars on the right.
 *
 * <pre>
 *   ❄  Temperature  [████████░░]  Cold  (42%)
 * </pre>
 *
 * The bar fills left-to-right and transitions through four colors:
 * <ul>
 *   <li>Freezing (&lt;25%) — deep blue</li>
 *   <li>Cold (25–50%)     — light blue</li>
 *   <li>Chilly (50–75%)   — pale cyan</li>
 *   <li>Warm (&ge;75%)    — white (hidden outside Winter)</li>
 * </ul>
 *
 * The overlay is completely hidden outside Winter so it never clutters the
 * screen during summer play.
 *
 * <p>The displayed temperature is the last value received from the server via
 * {@link net.got.network.PlayerTemperaturePayload}; it is updated once per
 * second by {@link net.got.event.GotPlayerEvents#onPlayerTick}.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class TemperatureHudOverlay implements LayeredDraw.Layer {

    // ── Singleton ─────────────────────────────────────────────────────────────
    public static final TemperatureHudOverlay INSTANCE = new TemperatureHudOverlay();

    /** Overlay ID used when registering with NeoForge's GUI layer system. */
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "temperature_hud");

    // ── Client-side temperature cache (written by network handler) ────────────
    private static volatile float clientTemperature = 1.0f;

    /** Called from the {@link net.got.network.GotNetwork} client handler. */
    public static void setClientTemperature(float value) {
        clientTemperature = Math.max(0f, Math.min(1f, value));
    }

    public static float getClientTemperature() {
        return clientTemperature;
    }

    // ── Layout constants ──────────────────────────────────────────────────────
    private static final int MARGIN_LEFT   = 10;
    private static final int MARGIN_BOTTOM = 50; // above hotbar
    private static final int BAR_WIDTH     = 60;
    private static final int BAR_HEIGHT    = 5;
    private static final int ICON_SIZE     = 9;   // font height

    // ── Color palette ─────────────────────────────────────────────────────────
    private static final int COLOR_FREEZING = 0xFF4466EE; // deep blue
    private static final int COLOR_COLD     = 0xFF88AAFF; // light blue
    private static final int COLOR_CHILLY   = 0xFFBBDDFF; // pale cyan
    private static final int COLOR_WARM     = 0xFFFFFFFF; // white
    private static final int COLOR_BAR_BG   = 0x88000000; // translucent black
    private static final int COLOR_TEXT     = 0xFFFFFFFF;

    // ── Registration ──────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        // Render below the vanilla crosshair layer so it sits cleanly behind
        // any vanilla elements stacked above the hotbar.
        event.registerAboveAll(ID, INSTANCE);
    }

    // ── LayeredDraw.Layer impl ────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics gfx, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();

        // Only show during Winter
        if (!SeasonManager.getCurrentSeason().isWinter()) return;
        // Don't show in spectator / F3 debug mode
        if (mc.player == null) return;
        if (mc.player.isSpectator()) return;
        if (mc.getDebugOverlay().showDebugScreen()) return;

        float temp = clientTemperature;
        PlayerTemperatureSystem.TemperatureBand band = bandFromTemp(temp);

        int screenH = gfx.guiHeight();
        int y       = screenH - MARGIN_BOTTOM;
        int x       = MARGIN_LEFT;

        // ── Snowflake icon ────────────────────────────────────────────────────
        gfx.drawString(mc.font, "\u2744", x, y, bandColor(band), false);
        x += ICON_SIZE + 3;

        // ── Label ─────────────────────────────────────────────────────────────
        String label = band.displayName;
        gfx.drawString(mc.font, label, x, y, bandColor(band), false);
        x += mc.font.width(label) + 4;

        // ── Bar background ────────────────────────────────────────────────────
        gfx.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, COLOR_BAR_BG);

        // ── Bar fill ──────────────────────────────────────────────────────────
        int fillW = Math.round(temp * BAR_WIDTH);
        if (fillW > 0) {
            gfx.fill(x, y, x + fillW, y + BAR_HEIGHT, bandColor(band));
        }

        // ── Percentage text (right of bar) ────────────────────────────────────
        x += BAR_WIDTH + 4;
        String pct = Math.round(temp * 100) + "%";
        gfx.drawString(mc.font, pct, x, y, COLOR_TEXT, false);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static PlayerTemperatureSystem.TemperatureBand bandFromTemp(float t) {
        if (t >= PlayerTemperatureSystem.TEMP_MAX * 0.75f)
            return PlayerTemperatureSystem.TemperatureBand.WARM;
        if (t >= PlayerTemperatureSystem.TEMP_MAX * 0.50f)
            return PlayerTemperatureSystem.TemperatureBand.CHILLY;
        if (t >= PlayerTemperatureSystem.TEMP_MAX * 0.25f)
            return PlayerTemperatureSystem.TemperatureBand.COLD;
        return PlayerTemperatureSystem.TemperatureBand.FREEZING;
    }

    private static int bandColor(PlayerTemperatureSystem.TemperatureBand band) {
        return switch (band) {
            case FREEZING -> COLOR_FREEZING;
            case COLD     -> COLOR_COLD;
            case CHILLY   -> COLOR_CHILLY;
            case WARM     -> COLOR_WARM;
        };
    }

    private TemperatureHudOverlay() {}
}