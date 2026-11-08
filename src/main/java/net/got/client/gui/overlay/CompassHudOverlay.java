package net.got.client.gui.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

/**
 * Skyrim-style compass bar at the top center of the screen.
 * Shows N / NE / E / SE / S / SW / W / NW sliding along a strip as the player turns.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class CompassHudOverlay implements GuiLayer {

    public static final CompassHudOverlay INSTANCE = new CompassHudOverlay();
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "compass_hud");

    // ---- layout (tweak these) ----
    private static final int   BAR_W        = 240;   // width of the strip in GUI pixels
    private static final int   BAR_H        = 15;    // height of the strip
    private static final int   BAR_TOP      = 4;     // distance from the top of the screen
    private static final float VISIBLE_DEG  = 120f;  // how many degrees fit across the strip
    private static final float PX_PER_DEG   = BAR_W / VISIBLE_DEG;

    // ---- colors (RGB, alpha is applied per-pixel for the edge fade) ----
    private static final int RGB_BG         = 0x101010;
    private static final int RGB_LINE       = 0xB8B8B8;
    private static final int RGB_CARDINAL   = 0xFFFFFF;
    private static final int RGB_NORTH      = 0xFF6A5A;
    private static final int RGB_INTERMED   = 0xC8C8C8;
    private static final int RGB_TICK       = 0x9A9A9A;
    private static final int RGB_POINTER    = 0xFFFFFF;

    // index = bearing / 45
    private static final String[] NAMES = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

    @Override
    public void render(GuiGraphics gfx, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        if (mc.screen != null) return;

        float partial = delta.getGameTimeDeltaPartialTick(true);
        float yaw = Mth.rotLerp(partial, mc.player.yRotO, mc.player.getYRot());

        // Minecraft yaw: 0 = south, 90 = west, 180 = north, -90 = east.
        // Compass bearing: 0 = north, 90 = east, clockwise.
        float bearing = Mth.wrapDegrees(yaw + 180f);
        if (bearing < 0f) bearing += 360f;

        int screenW = gfx.guiWidth();
        int left = (screenW - BAR_W) / 2;
        int top  = BAR_TOP;
        int cx   = screenW / 2;
        float half = BAR_W / 2f;

        // ---- background strip, fading out toward both ends ----
        for (int i = 0; i < BAR_W; i++) {
            float dx = Math.abs((left + i + 0.5f) - cx);
            float fade = edgeFade(dx / half);
            int bgA = (int) (0x78 * fade);
            int lnA = (int) (0xB0 * fade);
            if (bgA > 0) gfx.fill(left + i, top, left + i + 1, top + BAR_H, argb(bgA, RGB_BG));
            if (lnA > 0) {
                gfx.fill(left + i, top, left + i + 1, top + 1, argb(lnA, RGB_LINE));
                gfx.fill(left + i, top + BAR_H - 1, left + i + 1, top + BAR_H, argb(lnA, RGB_LINE));
            }
        }

        // ---- ticks every 15 degrees (skipping the 45-degree label spots) ----
        for (int deg = 0; deg < 360; deg += 15) {
            if (deg % 45 == 0) continue;
            float d = Mth.wrapDegrees(deg - bearing);
            if (Math.abs(d) > VISIBLE_DEG / 2f) continue;
            float dx = d * PX_PER_DEG;
            int a = (int) (0xC0 * edgeFade(Math.abs(dx) / half));
            if (a <= 0) continue;
            int x = Math.round(cx + dx);
            gfx.fill(x, top + 5, x + 1, top + BAR_H - 5, argb(a, RGB_TICK));
        }

        // ---- direction labels every 45 degrees ----
        for (int i = 0; i < 8; i++) {
            int deg = i * 45;
            float d = Mth.wrapDegrees(deg - bearing);
            if (Math.abs(d) > VISIBLE_DEG / 2f) continue;
            float dx = d * PX_PER_DEG;
            float fade = edgeFade(Math.abs(dx) / half);
            int a = (int) (0xFF * fade);
            if (a < 12) continue;

            String label = NAMES[i];
            boolean cardinal = (i % 2 == 0);
            int rgb = cardinal ? (i == 0 ? RGB_NORTH : RGB_CARDINAL) : RGB_INTERMED;
            int w = mc.font.width(label);
            int x = Math.round(cx + dx) - w / 2;
            int y = top + (BAR_H - 8) / 2;

            gfx.drawString(mc.font, label, x, y, argb(a, rgb), false);
            if (cardinal) {
                // cheap bold so N / E / S / W stand out from NE / SE / SW / NW
                gfx.drawString(mc.font, label, x + 1, y, argb(a, rgb), false);
            }
        }

        // ---- center pointer under the strip ----
        int p = argb(0xFF, RGB_POINTER);
        gfx.fill(cx - 2, top + BAR_H,     cx + 3, top + BAR_H + 1, p);
        gfx.fill(cx - 1, top + BAR_H + 1, cx + 2, top + BAR_H + 2, p);
        gfx.fill(cx,     top + BAR_H + 2, cx + 1, top + BAR_H + 3, p);
    }

    /** 1.0 in the middle of the strip, easing to 0.0 at the very ends. t = distance from center / half width. */
    private static float edgeFade(float t) {
        if (t >= 1f) return 0f;
        if (t <= 0.55f) return 1f;
        float u = (t - 0.55f) / 0.45f;
        return 1f - u * u;
    }

    private static int argb(int alpha, int rgb) {
        return (Mth.clamp(alpha, 0, 255) << 24) | (rgb & 0xFFFFFF);
    }
}