package net.got.client.gui.overlay;

import net.got.faction.FactionData;
import net.got.faction.Factions;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

/**
 * The "waking up" intro: full black screen, a few seconds of eyes-fluttering-open
 * flicker, then a handful of lines fading in one at a time — closing on the
 * player's chosen house words. No camera lock, no cutscene actors; the player
 * is already standing at their real spawn point underneath this, so when it
 * fades out they're simply looking at the world.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class IntroSequenceOverlay implements GuiLayer {

    public static final IntroSequenceOverlay INSTANCE = new IntroSequenceOverlay();
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "intro_sequence");

    // -- timing (ms) --
    private static final long BLINK_DURATION      = 1400;
    private static final long LINE_APPEAR_INTERVAL= 1900;
    private static final long LINE_FADE_MS        = 500;
    private static final long HOLD_AFTER_ALL      = 1800;
    private static final long FADE_OUT_MS         = 1200;

    private static final String[] LINES = {
            "You felt the realm move.",
            "The King is dead. The war has already begun.",
            "You are not who you were on the Kingsroad.",
            "Open your eyes."
    };

    private static volatile boolean active = false;
    private static volatile long startTimeMillis = 0L;
    private static volatile String factionWords = null;
    private static volatile int factionColor = 0xFFFFFFFF;

    public static void start(String factionId) {
        FactionData faction = Factions.BY_ID.get(factionId);
        factionWords = (faction != null) ? faction.words() : null;
        factionColor = (faction != null) ? (0xFF000000 | faction.primaryColour()) : 0xFFFFFFFF;
        startTimeMillis = System.currentTimeMillis();
        active = true;
    }

    public static boolean isActive() {
        return active;
    }

    private static long totalDuration() {
        return BLINK_DURATION + LINES.length * LINE_APPEAR_INTERVAL + HOLD_AFTER_ALL + FADE_OUT_MS;
    }

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

    @Override
    public void render(GuiGraphics gfx, DeltaTracker delta) {
        if (!active) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) { active = false; return; }

        long elapsed = System.currentTimeMillis() - startTimeMillis;
        long total = totalDuration();
        if (elapsed >= total) { active = false; return; }

        int screenW = gfx.guiWidth();
        int screenH = gfx.guiHeight();

        long fadeOutStart = total - FADE_OUT_MS;
        float fadeOut = (elapsed >= fadeOutStart)
                ? 1f - clamp01((float) (elapsed - fadeOutStart) / FADE_OUT_MS)
                : 1f;

        // -- background: flicker during the blink, solid black after --
        int bg;
        if (elapsed < BLINK_DURATION) {
            float t = (float) elapsed / BLINK_DURATION;
            float flutter = (float) Math.abs(Math.sin(t * 3.0 * Math.PI)) * (1f - t);
            int g = (int) (flutter * 40f);
            bg = argb(fadeOut, g, g, g);
        } else {
            bg = argb(fadeOut, 0, 0, 0);
        }
        gfx.fill(0, 0, screenW, screenH, bg);

        if (elapsed < BLINK_DURATION) return;

        // -- lines, stacked and fading in one at a time --
        int baseY = screenH / 2 - 70;
        for (int i = 0; i < LINES.length; i++) {
            long lineStart = BLINK_DURATION + i * LINE_APPEAR_INTERVAL;
            if (elapsed < lineStart) break;

            float lineAlpha = clamp01((float) (elapsed - lineStart) / LINE_FADE_MS) * fadeOut;
            int color = argb(lineAlpha, 1f, 1f, 1f);
            int y = baseY + i * 22;
            gfx.drawCenteredString(mc.font, LINES[i], screenW / 2, y, color);
        }

        // -- closing line: the player's house words, in house colour --
        if (factionWords != null && !factionWords.isEmpty()) {
            long mottoStart = BLINK_DURATION + LINES.length * LINE_APPEAR_INTERVAL;
            if (elapsed >= mottoStart) {
                float mottoAlpha = clamp01((float) (elapsed - mottoStart) / LINE_FADE_MS) * fadeOut;
                int a = (int) (clamp01(mottoAlpha) * 255f);
                int color = (a << 24) | (factionColor & 0x00FFFFFF);
                int y = baseY + LINES.length * 22 + 20;
                gfx.drawCenteredString(mc.font, "\"" + factionWords + "\"", screenW / 2, y, color);
            }
        }
    }

    private static int argb(float a, float r, float g, float b) {
        return ((int) (clamp01(a) * 255f) << 24)
                | ((int) (clamp01(r) * 255f) << 16)
                | ((int) (clamp01(g) * 255f) << 8)
                | (int) (clamp01(b) * 255f);
    }

    private static int argb(float a, int r, int g, int b) {
        return ((int) (clamp01(a) * 255f) << 24) | (clampByte(r) << 16) | (clampByte(g) << 8) | clampByte(b);
    }

    private static int clampByte(int v) {
        return Math.max(0, Math.min(255, v));
    }

    private static float clamp01(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private IntroSequenceOverlay() {}
}
