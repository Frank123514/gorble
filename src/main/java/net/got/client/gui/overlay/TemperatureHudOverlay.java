package net.got.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.got.climate.PlayerTemperatureSystem;
import net.got.climate.PlayerThirstSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

/**
 * HUD overlay for body temperature and thirst.
 *
 * <h3>Thirst</h3>
 * 10 water-droplet icons on the right side of the screen, one row above the
 * vanilla hunger bar. All 10 are always visible — filled icons are blue,
 * empty icons are dark grey. Mirrors the vanilla hunger/hearts aesthetic.
 *
 * <h3>Temperature</h3>
 * Small °F readout in the bottom-left corner, only shown when outside the
 * comfortable warm band (i.e. hidden at normal body temperature).
 * Internal [0,1] scale maps so that 0.5 = 98.6 °F (normal human body temp).
 *
 * <h3>Screen overlays</h3>
 * <ul>
 *   <li><b>Cold</b>: vanilla {@code powder_snow_outline.png} texture rendered
 *       full-screen at increasing opacity as the player gets colder.</li>
 *   <li><b>Heat</b>: orange-red vignette at the screen edges, styled
 *       symmetrically to the frozen effect, growing with overheating severity.</li>
 * </ul>
 * No mob-effect particles or status icons are used for climate feedback.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class TemperatureHudOverlay implements LayeredDraw.Layer {

    public static final TemperatureHudOverlay INSTANCE = new TemperatureHudOverlay();
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "vitals_hud");

    /** Vanilla powder-snow frozen-screen texture (same one the game uses in powder snow blocks). */
    private static final ResourceLocation POWDER_SNOW_RL =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/powder_snow_outline.png");

    // ── Client-side cache (written by the network handler) ────────────────────
    private static volatile float clientBodyTemp = 0.5f;
    private static volatile float clientThirst   = 1.0f;

    public static void setClientVitals(float bodyTemp, float thirst) {
        clientBodyTemp = clamp(bodyTemp, 0f, 1f);
        clientThirst   = clamp(thirst,   0f, 1f);
    }

    /** Legacy compat for callers that only sync temperature. */
    public static void setClientTemperature(float value) {
        clientBodyTemp = clamp(value, 0f, 1f);
    }

    // ── Thirst icon layout ────────────────────────────────────────────────────
    /**
     * Right-hand edge of the icon strip, measured from the screen horizontal
     * centre — matches vanilla hearts/hunger right edge.
     */
    private static final int THIRST_RIGHT_EDGE   = 91;
    /** Pixels between icon left-edges (same cadence as vanilla hearts). */
    private static final int THIRST_ICON_SPACING = 8;
    /** Distance from the bottom of the screen to the icon row's top edge. */
    private static final int THIRST_Y_OFFSET     = 61; // one row above vanilla hunger at -49

    // Droplet colours
    private static final int DROP_FULL    = 0xFF44AAFF; // bright water-blue
    private static final int DROP_FULL_HL = 0xFFAADDFF; // lighter highlight
    private static final int DROP_EMPTY   = 0xFF223344; // dark blue-grey

    // ── Temperature display ───────────────────────────────────────────────────
    private static final int TEMP_MARGIN_X = 10;
    private static final int TEMP_MARGIN_Y = 50;

    /**
     * Converts the internal body-temp value [0, 1] to Fahrenheit.
     * Mapping: 0.5 → 98.6 °F (normal human body temperature).
     * Range: 0.0 → ~85 °F (severe hypothermia), 1.0 → ~112 °F (heat stroke).
     */
    private static float toFahrenheit(float bodyTemp) {
        return 85.0f + bodyTemp * 27.2f;
    }

    // ── Overlay start thresholds ──────────────────────────────────────────────
    // Cold: only kicks in at BODY_COLD (0.20) — well below the chilly band so
    // it doesn't bleed into biomes whose base temp lands naturally in the 0.30s.
    private static final float COLD_OVERLAY_START = PlayerTemperatureSystem.BODY_COLD;  // 0.20
    private static final float HEAT_OVERLAY_START = PlayerTemperatureSystem.BODY_WARM;  // 0.60

    // ── Registration ──────────────────────────────────────────────────────────
    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

    // ── Render ────────────────────────────────────────────────────────────────
    @Override
    public void render(GuiGraphics gfx, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isSpectator()) return;
        if (mc.getDebugOverlay().showDebugScreen()) return;

        float bodyTemp = clientBodyTemp;
        float thirst   = clientThirst;
        int   screenW  = gfx.guiWidth();
        int   screenH  = gfx.guiHeight();

        // 1. Full-screen climate overlays (behind HUD icons)
        renderColdOverlay(gfx, screenW, screenH, bodyTemp);
        renderHeatOverlay(gfx, screenW, screenH, bodyTemp);

        // 2. Thirst droplets — always visible, mirrors vanilla hunger position
        renderThirstDroplets(gfx, screenW, screenH, thirst);

        // 3. Temperature °F readout — bottom-left, hidden when comfortably warm
        PlayerTemperatureSystem.TempBand band = tempBand(bodyTemp);
        if (band != PlayerTemperatureSystem.TempBand.WARM) {
            renderTempText(gfx, mc, screenH, bodyTemp, band);
        }
    }

    // ── Thirst droplets ───────────────────────────────────────────────────────

    private static void renderThirstDroplets(GuiGraphics gfx, int screenW, int screenH,
                                             float thirst) {
        int filled = Math.max(0, Math.min(10, Math.round(thirst * 10f)));

        int y       = screenH - THIRST_Y_OFFSET;
        // Left edge of the first (leftmost) icon, right-aligned like vanilla hunger
        int rowLeft = screenW / 2 + THIRST_RIGHT_EDGE - 9 * THIRST_ICON_SPACING - 9;

        for (int i = 0; i < 10; i++) {
            drawDroplet(gfx, rowLeft + i * THIRST_ICON_SPACING, y, i < filled);
        }
    }

    /**
     * Draws a 9×9 pixel water-droplet icon (teardrop silhouette) at (x, y).
     * Filled droplets are blue with a small highlight; empty ones are dark.
     */
    private static void drawDroplet(GuiGraphics gfx, int x, int y, boolean filled) {
        int fg = filled ? DROP_FULL  : DROP_EMPTY;
        int hl = filled ? DROP_FULL_HL : DROP_EMPTY;

        // Per-row widths of the teardrop — narrow tip at top, widest near bottom,
        // then tapers to a rounded bottom cap.
        int[] widths = { 1, 3, 5, 7, 9, 9, 7, 5, 3 };

        for (int row = 0; row < widths.length; row++) {
            int w  = widths[row];
            int lx = x + (9 - w) / 2; // centre each row horizontally
            gfx.fill(lx, y + row, lx + w, y + row + 1, fg);
        }

        // Two-pixel highlight on the top-left shoulder of filled drops
        if (filled) {
            gfx.fill(x + 2, y + 2, x + 3, y + 4, hl);
        }
    }

    // ── Temperature text ──────────────────────────────────────────────────────

    private static void renderTempText(GuiGraphics gfx, Minecraft mc,
                                       int screenH, float bodyTemp,
                                       PlayerTemperatureSystem.TempBand band) {
        float  tempF = toFahrenheit(bodyTemp);
        // ❄ for cold side, ★ for warm side
        String icon  = (bodyTemp < 0.5f) ? "\u2744" : "\u2605";
        String label = String.format("%s %.1f\u00b0F", icon, tempF);
        gfx.drawString(mc.font, label, TEMP_MARGIN_X, screenH - TEMP_MARGIN_Y, band.color, false);
    }

    // ── Screen overlays ───────────────────────────────────────────────────────

    /**
     * Vanilla {@code powder_snow_outline.png} stretched full-screen.
     * Opacity ramps from 0 at BODY_CHILLY (0.40) to full at body temp 0.
     */
    private static void renderColdOverlay(GuiGraphics gfx, int screenW, int screenH,
                                          float bodyTemp) {
        if (bodyTemp >= COLD_OVERLAY_START) return;

        float intensity = clamp((COLD_OVERLAY_START - bodyTemp) / COLD_OVERLAY_START, 0f, 1f);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, intensity);
        gfx.blit(RenderType::guiTextured, POWDER_SNOW_RL,
                0, 0, 0f, 0f, screenW, screenH, screenW, screenH);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }

    /**
     * Heat overlay: orange-red vignette that bleeds in from the screen edges,
     * structurally mirroring the frozen powder-snow effect.
     * Opacity ramps from 0 at BODY_WARM (0.60) to full at body temp 1.0.
     */
    private static void renderHeatOverlay(GuiGraphics gfx, int screenW, int screenH,
                                          float bodyTemp) {
        if (bodyTemp <= HEAT_OVERLAY_START) return;

        float intensity = clamp((bodyTemp - HEAT_OVERLAY_START) / (1f - HEAT_OVERLAY_START), 0f, 1f);

        // Faint base warmth tint across the whole screen
        int baseAlpha = (int)(intensity * 55);
        gfx.fill(0, 0, screenW, screenH, (baseAlpha << 24) | 0xFF5500);

        // Edge vignette — same quadratic falloff toward centre as vanilla frozen tex
        int steps = 32;
        for (int i = 0; i < steps; i++) {
            float t   = (float)(steps - i) / steps;
            int   a   = (int)(intensity * 175 * t * t);
            if (a < 2) continue;
            int   col = (a << 24) | 0xFF4400;
            int   p   = i * 2;
            // Top / bottom / left / right border strips
            gfx.fill(p,               p,               screenW - p,     p + 2,           col);
            gfx.fill(p,               screenH - p - 2, screenW - p,     screenH - p,     col);
            gfx.fill(p,               p,               p + 2,           screenH - p,     col);
            gfx.fill(screenW - p - 2, p,               screenW - p,     screenH - p,     col);
        }
    }

    // ── Band helper ───────────────────────────────────────────────────────────

    private static PlayerTemperatureSystem.TempBand tempBand(float t) {
        if (t >= PlayerTemperatureSystem.BODY_OVERHEAT) return PlayerTemperatureSystem.TempBand.OVERHEATED;
        if (t >= PlayerTemperatureSystem.BODY_WARM)     return PlayerTemperatureSystem.TempBand.WARM;
        if (t >= PlayerTemperatureSystem.BODY_CHILLY)   return PlayerTemperatureSystem.TempBand.CHILLY;
        if (t >= PlayerTemperatureSystem.BODY_COLD)     return PlayerTemperatureSystem.TempBand.COLD;
        return PlayerTemperatureSystem.TempBand.FREEZING;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private TemperatureHudOverlay() {}
}