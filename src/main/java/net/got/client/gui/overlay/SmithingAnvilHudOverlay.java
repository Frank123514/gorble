package net.got.client.gui.overlay;

import net.got.block.SmithingAnvilBlockEntity;
import net.got.network.SmithingAnvilStatePayload;
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
 * Full-screen HUD overlay that shows the smithing timing bar while the player
 * is looking at an active Smithing Anvil (recipe + ingot selected, GUI closed).
 *
 * State is pushed from the server each tick via {@link SmithingAnvilStatePayload}.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class SmithingAnvilHudOverlay implements GuiLayer {

    public static final SmithingAnvilHudOverlay INSTANCE = new SmithingAnvilHudOverlay();
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "smithing_anvil_hud");

    // ── Client-side state (updated by network packet) ──────────────────────
    private static volatile boolean active       = false;
    private static volatile int     markerPos    = 0;
    private static volatile int     hitCount     = 0;
    private static volatile int     hitsRequired = 3;
    private static volatile int     zoneCenter   = 50;
    private static volatile int     zoneHalf     = 12;
    private static volatile int     lastQuality  = SmithingAnvilBlockEntity.HIT_QUALITY_NONE;

    // Flash feedback
    private static int lastQualitySeen = SmithingAnvilBlockEntity.HIT_QUALITY_NONE;
    private static int flashTimer      = 0;
    private static final int FLASH_TICKS = 15;

    public static void onStatePacket(SmithingAnvilStatePayload p) {
        active       = p.active();
        markerPos    = p.markerPos();
        hitCount     = p.hitCount();
        hitsRequired = p.hitsRequired();
        zoneCenter   = p.zoneCenter();
        zoneHalf     = p.zoneHalf();

        int q = p.lastHitQuality();
        if (q != SmithingAnvilBlockEntity.HIT_QUALITY_NONE && q != lastQualitySeen) {
            lastQualitySeen = q;
            flashTimer = FLASH_TICKS;
        }
        lastQuality = q;
    }

    // ── Layout ─────────────────────────────────────────────────────────────
    private static final int BAR_W      = 80;
    private static final int BAR_H      = 8;
    private static final int MARKER_W   = 4;
    private static final int MARKER_H   = 14;

    // Colors
    private static final int C_BAR_BG       = 0xCC_1A1A1A;
    private static final int C_ZONE_GOOD    = 0xFF_1FA01F;
    private static final int C_ZONE_PERFECT = 0xFF_59D459;
    private static final int C_MARKER       = 0xFF_FFFFFF;
    private static final int C_BORDER       = 0xFF_404040;
    private static final int C_TEXT         = 0xFF_EEEEEE;
    private static final int C_MISS         = 0xFF_DD2222;
    private static final int C_GOOD         = 0xFF_22BB22;
    private static final int C_PERFECT      = 0xFF_FFFF44;
    private static final int C_SHADOW       = 0x88_000000;

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ID, INSTANCE);
    }

    @Override
    public void render(GuiGraphics g, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;
        if (mc.screen != null) return; // don't show while any GUI is open
        if (!active) return;

        if (flashTimer > 0) flashTimer--;

        int sw = g.guiWidth();
        int sh = g.guiHeight();

        // Position: centered horizontally, 30% from bottom
        int bx = (sw - BAR_W) / 2;
        int by = sh * 2 / 3;

        // ── Label: "Hits Left: X" ──────────────────────────────────────────
        int hitsLeft = Math.max(0, hitsRequired - hitCount);
        String label = "Hits Left: " + hitsLeft;
        int labelW = mc.font.width(label);
        // shadow
        g.drawString(mc.font, label, (sw - labelW) / 2 + 1, by - 13, C_SHADOW, false);
        g.drawString(mc.font, label, (sw - labelW) / 2,     by - 13, C_TEXT,   false);

        // ── Bar background ──────────────────────────────────────────────────
        // outer shadow
        g.fill(bx - 1, by - 1, bx + BAR_W + 1, by + BAR_H + 1, C_SHADOW);
        g.fill(bx, by, bx + BAR_W, by + BAR_H, C_BAR_BG);

        // ── Green zone ──────────────────────────────────────────────────────
        int goodLeft  = bx + (zoneCenter - zoneHalf) * BAR_W / 100;
        int goodRight = bx + (zoneCenter + zoneHalf) * BAR_W / 100;
        g.fill(goodLeft, by, goodRight, by + BAR_H, C_ZONE_GOOD);

        // ── Perfect inner zone ───────────────────────────────────────────────
        int perfLeft  = bx + (zoneCenter - zoneHalf / 2) * BAR_W / 100;
        int perfRight = bx + (zoneCenter + zoneHalf / 2) * BAR_W / 100;
        g.fill(perfLeft, by, perfRight, by + BAR_H, C_ZONE_PERFECT);

        // ── Marker ───────────────────────────────────────────────────────────
        int mx = bx + markerPos * BAR_W / 100 - MARKER_W / 2;
        int markerTop = by - (MARKER_H - BAR_H) / 2;
        g.fill(mx, markerTop, mx + MARKER_W, markerTop + MARKER_H, C_MARKER);

        // ── Border ───────────────────────────────────────────────────────────
        g.fill(bx - 1, by - 1,        bx + BAR_W + 1, by,              C_BORDER);
        g.fill(bx - 1, by + BAR_H,    bx + BAR_W + 1, by + BAR_H + 1, C_BORDER);
        g.fill(bx - 1, by - 1,        bx,              by + BAR_H + 1, C_BORDER);
        g.fill(bx + BAR_W, by - 1,    bx + BAR_W + 1, by + BAR_H + 1, C_BORDER);

        // ── Hit flash ────────────────────────────────────────────────────────
        if (flashTimer > 0) {
            int flashColor = switch (lastQualitySeen) {
                case SmithingAnvilBlockEntity.HIT_QUALITY_MISS    -> C_MISS;
                case SmithingAnvilBlockEntity.HIT_QUALITY_GOOD    -> C_GOOD;
                case SmithingAnvilBlockEntity.HIT_QUALITY_PERFECT -> C_PERFECT;
                default -> 0;
            };
            if (flashColor != 0) {
                String hitLabel = switch (lastQualitySeen) {
                    case SmithingAnvilBlockEntity.HIT_QUALITY_MISS    -> "Miss!";
                    case SmithingAnvilBlockEntity.HIT_QUALITY_GOOD    -> "Good!";
                    case SmithingAnvilBlockEntity.HIT_QUALITY_PERFECT -> "Perfect!";
                    default -> "";
                };
                int alpha = (flashTimer * 0xFF / FLASH_TICKS) << 24;
                int col   = (flashColor & 0x00FFFFFF) | alpha;
                int lw    = mc.font.width(hitLabel);
                g.drawString(mc.font, hitLabel, (sw - lw) / 2, by + BAR_H + 6, col, false);
            }
        }
    }

    private SmithingAnvilHudOverlay() {}
}