package net.got.client.gui.overlay;

import net.got.climate.PlayerTemperatureSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class TemperatureHudOverlay implements GuiLayer {

    public static final TemperatureHudOverlay INSTANCE = new TemperatureHudOverlay();
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "vitals_hud");

    private static final Identifier POWDER_SNOW_RL =
            Identifier.fromNamespaceAndPath("minecraft", "textures/misc/powder_snow_outline.png");

    private static final Identifier THIRST_DROPLET_RL =
            Identifier.fromNamespaceAndPath("got", "textures/gui/thirst_droplet.png");

    private static volatile float clientBodyTemp = 0.5f;
    private static volatile float clientThirst   = 1.0f;

    public static void setClientVitals(float bodyTemp, float thirst) {
        clientBodyTemp = clamp(bodyTemp, 0f, 1f);
        clientThirst   = clamp(thirst,   0f, 1f);
    }

    public static void setClientTemperature(float value) {
        clientBodyTemp = clamp(value, 0f, 1f);
    }

    private static final int THIRST_RIGHT_EDGE   = 91;
    private static final int THIRST_ICON_SPACING = 8;
    private static final int THIRST_Y_OFFSET     = 61;
    
    private static final int DROPLET_W = 9;
    private static final int DROPLET_H = 10;

    private static final int TEMP_MARGIN_Y = 59;

    private static float toFahrenheit(float bodyTemp) {
        return 85.0f + bodyTemp * 27.2f;
    }

    private static final float COLD_OVERLAY_START = PlayerTemperatureSystem.BODY_COLD;
    private static final float HEAT_OVERLAY_START = PlayerTemperatureSystem.BODY_WARM;

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

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

    private static void drawDroplet(GuiGraphics gfx, int x, int y, FillState state) {
        if (state == FillState.FULL) {
            gfx.blit(RenderPipelines.GUI_TEXTURED, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H, WHITE);

        } else if (state == FillState.EMPTY) {
            gfx.blit(RenderPipelines.GUI_TEXTURED, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H, DARK_TINT);

        } else {
            
            gfx.blit(RenderPipelines.GUI_TEXTURED, THIRST_DROPLET_RL,
                    x, y, 0f, 0f, DROPLET_W, DROPLET_H, DROPLET_W, DROPLET_H, DARK_TINT);
            
            gfx.blit(RenderPipelines.GUI_TEXTURED, THIRST_DROPLET_RL,
                    x, y + 5, 0f, 5f, DROPLET_W, 5, DROPLET_W, DROPLET_H, WHITE);
        }
    }

    private static int argb(float a, float r, float g, float b) {
        return ((int) (clamp(a, 0f, 1f) * 255f) << 24)
                | ((int) (clamp(r, 0f, 1f) * 255f) << 16)
                | ((int) (clamp(g, 0f, 1f) * 255f) << 8)
                | (int) (clamp(b, 0f, 1f) * 255f);
    }

    private static final int WHITE     = argb(1f, 1f, 1f, 1f);
    private static final int DARK_TINT = argb(1f, 0.18f, 0.22f, 0.30f);

    private static void renderTempText(GuiGraphics gfx, Minecraft mc,
                                       int screenW, int screenH, float bodyTemp,
                                       PlayerTemperatureSystem.TempBand band) {
        float  tempF = toFahrenheit(bodyTemp);
        String icon  = (bodyTemp < 0.5f) ? "\u2744" : "\u2605";
        String label = String.format("%s %.1f\u00b0F", icon, tempF);
        
        int x = screenW / 2 - 91;
        gfx.drawString(mc.font, label, x, screenH - TEMP_MARGIN_Y, band.color, false);
    }

    private static void renderColdOverlay(GuiGraphics gfx, int screenW, int screenH,
                                          float bodyTemp) {
        if (bodyTemp >= COLD_OVERLAY_START) return;
        float intensity = clamp((COLD_OVERLAY_START - bodyTemp) / COLD_OVERLAY_START, 0f, 1f);
        gfx.blit(RenderPipelines.GUI_TEXTURED, POWDER_SNOW_RL,
                0, 0, 0f, 0f, screenW, screenH, screenW, screenH, argb(intensity, 1f, 1f, 1f));
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