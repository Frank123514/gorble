package net.got.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.got.climate.PlayerTemperatureSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

/**
 * HUD overlay for body temperature and thirst.
 *
 * <h3>Thirst</h3>
 * 10 water-droplet icons on the right side of the screen, one row above the
 * vanilla hunger bar. All 10 are always visible — filled icons use the mod
 * sprite at {@code got:textures/gui/thirst_droplet.png}; empty icons are the
 * same sprite rendered with a dark tint. Half-fill intermediate states give
 * 20 visual phases across the 10 droplets.
 * Droplets fill/empty left-to-right: the leftmost droplet empties first as
 * the player gets thirstier.
 *
 * <h3>Temperature</h3>
 * Small °F readout positioned above the health bar row (bottom-left),
 * only shown when outside the comfortable warm band.
 *
 * <h3>Screen overlays</h3>
 * <ul>
 *   <li><b>Cold</b>: vanilla {@code powder_snow_outline.png} full-screen.</li>
 *   <li><b>Heat</b>: orange-red vignette at the screen edges.</li>
 * </ul>
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class TemperatureHudOverlay implements GuiLayer {

    public static final TemperatureHudOverlay INSTANCE = new TemperatureHudOverlay();
    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "vitals_hud");

    private static final ResourceLocation POWDER_SNOW_RL =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/powder_snow_outline.png");

    /** Thirst droplet sprite — got:textures/gui/thirst_droplet.png (9×10 px). */
    private static final ResourceLocation THIRST_DROPLET_RL =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/thirst_droplet.png");

    // ── Client-side cache ─────────────────────────────────────────────────────
    private static volatile float clientBodyTemp = 0.5f;
    private static volatile float clientThirst   = 1.0f;

    public static void setClientVitals(float bodyTemp, float thirst) {
        clientBodyTemp = clamp(bodyTemp, 0f, 1f);
        clientThirst   = clamp(thirst,   0f, 1f);
    }

    public static void setClientTemperature(float value) {
        clientBodyTemp = clamp(value, 0f, 1f);
    }

    // ── Thirst icon layout ────────────────────────────────────────────────────
    private static final int THIRST_RIGHT_EDGE   = 91;
    private static final int THIRST_ICON_SPACING = 8;
    private static final int THIRST_Y_OFFSET     = 61;
    /** Sprite dimensions in pixels. */
    private static final int DROPLET_W = 9;
    private static final int DROPLET_H = 10;

    // ── Temperature display ───────────────────────────────────────────────────
    /**
     * Y distance from screen bottom to the temperature text baseline.
     * Sits one text-line above the thirst droplet row (screenH - THIRST_Y_OFFSET),
     * stacking neatly: temp → thirst → health/hunger → hotbar.
     */
    private static final int TEMP_MARGIN_Y = 59;

    private static float toFahrenheit(float bodyTemp) {
        return 85.0f + bodyTemp * 27.2f;
    }

    // ── Overlay thresholds ────────────────────────────────────────────────────
    private static final float COLD_OVERLAY_START = PlayerTemperatureSystem.BODY_COLD;
    private static final float HEAT_OVERLAY_START = PlayerTemperatureSystem.BODY_WARM;

    // ── Registration ──────────────────────────────────────────────────────────
    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

    // ── Render ────────────────────────────────────────────────────────────────
    @Override
    public void render(GuiGraphics gfx, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isSpectator() || mc.player.isCreative()) return;
        if (mc.options.hideGui) return;
        if (mc.getDebugOverlay().showDebugScreen()) return;

        float bodyTemp = clientBodyTemp;
        float thirst   = clientThirst;
        int   screenW  = gfx.guiWidth();
        int   screenH  = gfx.guiHeight();

        renderColdOverlay(gfx, screenW, screenH, bodyTemp);
        renderHeatOverlay(gfx, screenW, screenH, bodyTemp);
        renderThirstDroplets(gfx, screenW, screenH, thirst);

        PlayerTemperatureSystem.TempBand band = tempBand(bodyTemp);
        if (band != PlayerTemperatureSystem.TempBand.WARM) {
            renderTempText(gfx, mc, screenW, screenH, bodyTemp, band);
        }
    }

    // ── Thirst droplets ───────────────────────────────────────────────────────

    /**
     * Renders 10 droplet icons with 20 half-step phases.
     * Fill direction: rightmost icons fill first; empties from left as thirst drops.
     */
    private static void renderThirstDroplets(GuiGraphics gfx, int screenW, int screenH,
                                             float thirst) {
        int filled20     = Math.max(0, Math.min(20, Math.round(thirst * 20f)));
        int fullDroplets = filled20 / 2;
        boolean hasHalf  = (filled20 % 2) == 1;

        int y       = screenH - THIRST_Y_OFFSET;
        int rowLeft = screenW / 2 + THIRST_RIGHT_EDGE - 9 * THIRST_ICON_SPACING - DROPLET_W;

        int startFull = 10 - fullDroplets;

        for (int i = 0; i < 10; i++) {
            FillState state;
            if (i >= startFull) {
                state = FillState.FULL;
            } else if (hasHalf && i == startFull - 1) {
                state = FillState.HALF;
            } else {
                state = FillState.EMPTY;
            }
            drawDroplet(gfx, rowLeft + i * THIRST_ICON_SPACING, y, state);
        }
    }

    private enum FillState { FULL, HALF, EMPTY }

    /**
     * Draws one droplet icon using the mod texture.
     * <ul>
     *   <li>FULL  — sprite at natural colour.</li>
     *   <li>EMPTY — sprite with a dark blue-grey tint.</li>
     *   <li>HALF  — bottom 5 rows at natural colour, top 5 rows dark-tinted.</li>
     * </ul>
     */
    private static void drawDroplet(GuiGraphics gfx, int x, int y, FillState state) {
        if (state == FillState.FULL) {
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            gfx.blit(RenderType::guiTextured, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H);

        } else if (state == FillState.EMPTY) {
            RenderSystem.setShaderColor(0.18f, 0.22f, 0.30f, 1f);
            gfx.blit(RenderType::guiTextured, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H);

        } else { // HALF — top half dark, bottom half full colour
            // Dark tint over entire sprite first
            RenderSystem.setShaderColor(0.18f, 0.22f, 0.30f, 1f);
            gfx.blit(RenderType::guiTextured, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H);
            // Overwrite bottom 5 rows at full colour
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            gfx.blit(RenderType::guiTextured, THIRST_DROPLET_RL,
                    x, y + 5, 0f, 5f, DROPLET_W, 5, DROPLET_W, DROPLET_H);
        }

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    // ── Temperature text ──────────────────────────────────────────────────────

    private static void renderTempText(GuiGraphics gfx, Minecraft mc,
                                       int screenW, int screenH, float bodyTemp,
                                       PlayerTemperatureSystem.TempBand band) {
        float  tempF = toFahrenheit(bodyTemp);
        String icon  = (bodyTemp < 0.5f) ? "\u2744" : "\u2605";
        String label = String.format("%s %.1f\u00b0F", icon, tempF);
        // Align with the left edge of the vanilla health bar (screenW/2 - 91)
        int x = screenW / 2 - 91;
        gfx.drawString(mc.font, label, x, screenH - TEMP_MARGIN_Y, band.color, false);
    }

    // ── Screen overlays ───────────────────────────────────────────────────────

    private static void renderColdOverlay(GuiGraphics gfx, int screenW, int screenH,
                                          float bodyTemp) {
        if (bodyTemp >= COLD_OVERLAY_START) return;
        float intensity = clamp((COLD_OVERLAY_START - bodyTemp) / COLD_OVERLAY_START, 0f, 1f);
        RenderSystem.setShaderColor(1f, 1f, 1f, intensity);
        gfx.blit(RenderType::guiTextured, POWDER_SNOW_RL,
                0, 0, 0f, 0f, screenW, screenH, screenW, screenH);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    private static void renderHeatOverlay(GuiGraphics gfx, int screenW, int screenH,
                                          float bodyTemp) {
        if (bodyTemp <= HEAT_OVERLAY_START) return;
        float intensity = clamp((bodyTemp - HEAT_OVERLAY_START) / (1f - HEAT_OVERLAY_START), 0f, 1f);
        int baseAlpha = (int)(intensity * 55);
        gfx.fill(0, 0, screenW, screenH, (baseAlpha << 24) | 0xFF5500);
        int steps = 32;
        for (int i = 0; i < steps; i++) {
            float t   = (float)(steps - i) / steps;
            int   a   = (int)(intensity * 175 * t * t);
            if (a < 2) continue;
            int   col = (a << 24) | 0xFF4400;
            int   p   = i * 2;
            gfx.fill(p,               p,               screenW - p,     p + 2,           col);
            gfx.fill(p,               screenH - p - 2, screenW - p,     screenH - p,     col);
            gfx.fill(p,               p,               p + 2,           screenH - p,     col);
            gfx.fill(screenW - p - 2, p,               screenW - p,     screenH - p,     col);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

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