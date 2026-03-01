package net.got.client.gui.widget;

import net.got.network.MapTeleportPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GotMapWidget extends AbstractWidget {

    /* ============================================================= */
    /* ======================== CONSTANTS ========================== */
    /* ============================================================= */

    private static final ResourceLocation COMPASS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/compass_rose.png");

    private static final int CANVAS_BG_COLOR = 0xFF000000;

    /**
     * Iron border — 5 concentric 1-pixel rings drawn outside-in.
     *
     * Each entry is { top/left colour, bottom/right colour }.
     * Light comes from top-left (classic UI bevel convention) so:
     *   - outer rings use a dark forge-shadow
     *   - ring 2 has the main iron highlight on top/left, deep shadow on bottom/right
     *   - inner rings taper back to dark for depth
     */
    private static final int[][] BORDER_LAYERS = {
            // { top+left,   bottom+right }
            { 0xFF0E0C0A, 0xFF0E0C0A },   // 0 — outer void / forge edge
            { 0xFF3A3634, 0xFF252220 },   // 1 — dark iron body
            { 0xFFB8B4B0, 0xFF5A5653 },   // 2 — main bevel: highlight / shadow
            { 0xFF6E6A67, 0xFF2E2B29 },   // 3 — inner iron drop
            { 0xFF1A1714, 0xFF1A1714 },   // 4 — inner void
    };
    private static final int BORDER_THICKNESS = BORDER_LAYERS.length; // 5

    private static final float COMPASS_FRACTION        = 0.12f;
    private static final float COMPASS_MARGIN_FRACTION = 0.015f;

    private static final double[] ZOOM_MULTIPLIERS = { 1.0, 2.0, 4.0, 7.0, 12.0, 20.0 };
    private static final float    ANIM_SPEED        = 4.0f;
    private static final double   ZOOM_SNAP_EPSILON = 0.0001;
    private static final double   PAN_SNAP_EPSILON  = 0.05;
    private static final float    BLOCKS_PER_PIXEL  = 56.0f;
    private static final float    WORLD_WIDTH_BLOCKS  = 112000f;
    private static final float    WORLD_HEIGHT_BLOCKS = 112000f;
    private static final long     ZOOM_COOLDOWN_MS    = 400L;

    /* ============================================================= */
    /* ========================== STATE ============================ */
    /* ============================================================= */

    private final ResourceLocation mapTexture;
    private final int textureWidth;
    private final int textureHeight;

    private double zoom, panX, panY;
    private double targetZoom, targetPanX, targetPanY;
    private int    zoomIndex = 0;

    private boolean dragging     = false;
    private long lastNanoTime    = -1L;
    private long lastZoomTimeMs  = 0L;

    /* ============================================================= */
    /* ======================== CONSTRUCTOR ======================== */
    /* ============================================================= */

    public GotMapWidget(int x, int y, int width, int height,
                        ResourceLocation texture,
                        int textureWidth, int textureHeight) {
        super(x, y, width, height, Component.empty());
        this.mapTexture    = texture;
        this.textureWidth  = textureWidth;
        this.textureHeight = textureHeight;

        this.targetZoom = zoomForLevel(0);
        this.zoom       = targetZoom;

        snapPanToPlayer();
        this.panX = targetPanX;
        this.panY = targetPanY;
    }

    /* ============================================================= */
    /* ==================== ZOOM / PAN HELPERS ==================== */
    /* ============================================================= */

    private double getMinZoom() {
        return Math.max((double) width / textureWidth, (double) height / textureHeight);
    }

    private double zoomForLevel(int level) {
        return getMinZoom() * ZOOM_MULTIPLIERS[level];
    }

    private void snapPanToPlayer() {
        Minecraft mc = Minecraft.getInstance();
        double cx = textureWidth  / 2.0;
        double cz = textureHeight / 2.0;
        if (mc.player instanceof AbstractClientPlayer p) {
            cx = (p.getX() + WORLD_WIDTH_BLOCKS  / 2.0) / BLOCKS_PER_PIXEL;
            cz = (p.getZ() + WORLD_HEIGHT_BLOCKS / 2.0) / BLOCKS_PER_PIXEL;
        }
        targetPanX = cx * targetZoom - width  / 2.0;
        targetPanY = cz * targetZoom - height / 2.0;
        clampTargetPan();
    }

    private void zoomAroundCanvasCentre(double newZoom) {
        double cx = (panX + width  / 2.0) / zoom;
        double cz = (panY + height / 2.0) / zoom;
        targetPanX = cx * newZoom - width  / 2.0;
        targetPanY = cz * newZoom - height / 2.0;
        targetZoom = newZoom;
        clampTargetPan();
    }

    /* ============================================================= */
    /* ========================== RENDER =========================== */
    /* ============================================================= */

    @Override
    public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // Tick animation
        long now = System.nanoTime();
        if (lastNanoTime < 0) lastNanoTime = now;
        float dt = Math.min((now - lastNanoTime) / 1_000_000_000f, 0.1f);
        lastNanoTime = now;
        tickAnimation(dt);

        // Canvas background
        gfx.fill(getX(), getY(), getX() + width, getY() + height, CANVAS_BG_COLOR);

        // Scissor-clip to canvas interior
        gfx.enableScissor(getX(), getY(), getX() + width, getY() + height);

        // Map texture
        int zoomedW = (int) (textureWidth  * zoom);
        int zoomedH = (int) (textureHeight * zoom);
        int drawX   = (int) (getX() - panX);
        int drawY   = (int) (getY() - panY);
        gfx.blit(RenderType::guiTextured, mapTexture,
                drawX, drawY, 0, 0,
                zoomedW, zoomedH,
                zoomedW, zoomedH);

        // Player marker
        drawPlayerMarker(gfx);

        // Compass rose
        int compassSize   = Math.max(16, (int) (height * COMPASS_FRACTION));
        int compassMargin = Math.max(4,  (int) (height * COMPASS_MARGIN_FRACTION));
        gfx.blit(RenderType::guiTextured, COMPASS_TEXTURE,
                getX() + compassMargin,
                getY() + height - compassSize - compassMargin,
                0, 0, compassSize, compassSize, compassSize, compassSize);

        gfx.disableScissor();

        // Iron border (drawn outside scissor so it overlaps the canvas edge cleanly)
        drawIronBorder(gfx);

        drawZoomLabel(gfx);
    }

    /* ------------------------------------------------------------ */
    /* Animation                                                     */
    /* ------------------------------------------------------------ */

    private void tickAnimation(float dt) {
        float f = 1f - (float) Math.exp(-ANIM_SPEED * dt);
        zoom = Mth.lerp(f, zoom, targetZoom);
        panX = Mth.lerp(f, panX, targetPanX);
        panY = Mth.lerp(f, panY, targetPanY);
        if (Math.abs(zoom - targetZoom) < ZOOM_SNAP_EPSILON) zoom = targetZoom;
        if (Math.abs(panX - targetPanX) < PAN_SNAP_EPSILON)  panX = targetPanX;
        if (Math.abs(panY - targetPanY) < PAN_SNAP_EPSILON)  panY = targetPanY;
    }

    /* ------------------------------------------------------------ */
    /* Iron border                                                   */
    /* ------------------------------------------------------------ */

    /**
     * Draws 5 concentric 1-pixel rectangular outlines around the canvas,
     * each split into top+left (highlight side) and bottom+right (shadow side)
     * to simulate a bevelled cast-iron frame.
     */
    private void drawIronBorder(GuiGraphics gfx) {
        for (int i = 0; i < BORDER_LAYERS.length; i++) {
            int x1 = getX()         - (BORDER_THICKNESS - i);
            int y1 = getY()         - (BORDER_THICKNESS - i);
            int x2 = getX() + width  + (BORDER_THICKNESS - i) - 1;
            int y2 = getY() + height + (BORDER_THICKNESS - i) - 1;

            int colTL = BORDER_LAYERS[i][0];
            int colBR = BORDER_LAYERS[i][1];

            // Top edge
            gfx.fill(x1, y1, x2 + 1, y1 + 1, colTL);
            // Left edge
            gfx.fill(x1, y1, x1 + 1, y2 + 1, colTL);
            // Bottom edge
            gfx.fill(x1, y2, x2 + 1, y2 + 1, colBR);
            // Right edge
            gfx.fill(x2, y1, x2 + 1, y2 + 1, colBR);
        }
    }

    /* ------------------------------------------------------------ */
    /* Player marker                                                 */
    /* ------------------------------------------------------------ */

    private void drawPlayerMarker(GuiGraphics gfx) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof AbstractClientPlayer player)) return;

        double worldX = player.getX() + WORLD_WIDTH_BLOCKS  / 2.0;
        double worldZ = player.getZ() + WORLD_HEIGHT_BLOCKS / 2.0;

        int rawScreenX = (int) (getX() - panX + (worldX / BLOCKS_PER_PIXEL) * zoom);
        int rawScreenY = (int) (getY() - panY + (worldZ / BLOCKS_PER_PIXEL) * zoom);

        int cx1 = getX() + BORDER_THICKNESS;
        int cy1 = getY() + BORDER_THICKNESS;
        int cx2 = getX() + width  - BORDER_THICKNESS;
        int cy2 = getY() + height - BORDER_THICKNESS;

        int drawX = Mth.clamp(rawScreenX - 4, cx1, cx2 - 8);
        int drawY = Mth.clamp(rawScreenY - 4, cy1, cy2 - 8);

        gfx.blit(RenderType::guiTextured, player.getSkin().texture(),
                drawX, drawY, 8, 8, 8, 8, 64, 64);
    }

    /* ------------------------------------------------------------ */
    /* Zoom label                                                    */
    /* ------------------------------------------------------------ */

    private void drawZoomLabel(GuiGraphics gfx) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.font == null) return;
        String label  = "Zoom Level: " + (zoomIndex + 1) + "/" + ZOOM_MULTIPLIERS.length;
        int    margin = 6;
        int    lx     = getX() + width - mc.font.width(label) - margin;
        int    ly     = getY() + margin;
        gfx.drawString(mc.font, label, lx, ly, 0xFFFFFF, false);
    }

    /* ============================================================= */
    /* ========================== INPUT ============================ */
    /* ============================================================= */

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseOver(mouseX, mouseY)) return false;
        long now = System.currentTimeMillis();
        if (now - lastZoomTimeMs < ZOOM_COOLDOWN_MS) return true;
        int prev = zoomIndex;
        if (deltaY > 0) zoomIndex = Math.min(zoomIndex + 1, ZOOM_MULTIPLIERS.length - 1);
        else            zoomIndex = Math.max(zoomIndex - 1, 0);
        if (zoomIndex != prev) { zoomAroundCanvasCentre(zoomForLevel(zoomIndex)); lastZoomTimeMs = now; }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) return false;
        if (button == 0) { dragging = true; return true; }
        if (button == 1) { teleportTo(mouseX, mouseY); return true; }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (!dragging) return false;
        panX -= dx; panY -= dy;
        targetPanX -= dx; targetPanY -= dy;
        clampPan(); clampTargetPan();
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false; return false;
    }

    /* ============================================================= */
    /* ==================== COORDINATE QUERY ======================= */
    /* ============================================================= */

    @Nullable
    public BlockPos getHoveredWorldPos(double mouseX, double mouseY) {
        if (!isMouseOver(mouseX, mouseY)) return null;
        double mapX = (mouseX - getX() + panX) / zoom;
        double mapY = (mouseY - getY() + panY) / zoom;
        if (mapX < 0 || mapY < 0 || mapX >= textureWidth || mapY >= textureHeight) return null;
        int worldX = (int) (mapX * BLOCKS_PER_PIXEL - WORLD_WIDTH_BLOCKS  / 2.0);
        int worldZ = (int) (mapY * BLOCKS_PER_PIXEL - WORLD_HEIGHT_BLOCKS / 2.0);
        return new BlockPos(worldX, 0, worldZ);
    }

    /* ============================================================= */
    /* ======================= TELEPORT ============================ */
    /* ============================================================= */

    private void teleportTo(double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.hasPermissions(2)) return;
        double localX = (mouseX - getX() + panX) / zoom;
        double localY = (mouseY - getY() + panY) / zoom;
        int blockX = (int) (localX * BLOCKS_PER_PIXEL - WORLD_WIDTH_BLOCKS  / 2.0);
        int blockZ = (int) (localY * BLOCKS_PER_PIXEL - WORLD_HEIGHT_BLOCKS / 2.0);
        if (mc.getConnection() != null)
            mc.getConnection().send(new MapTeleportPayload(blockX, blockZ));
    }

    /* ============================================================= */
    /* ========================== CLAMP ============================ */
    /* ============================================================= */

    private void clampPan() {
        int zW = (int) (textureWidth  * zoom);
        int zH = (int) (textureHeight * zoom);
        panX = Mth.clamp(panX, 0, Math.max(0, zW - width));
        panY = Mth.clamp(panY, 0, Math.max(0, zH - height));
    }

    private void clampTargetPan() {
        int zW = (int) (textureWidth  * targetZoom);
        int zH = (int) (textureHeight * targetZoom);
        targetPanX = Mth.clamp(targetPanX, 0, Math.max(0, zW - width));
        targetPanY = Mth.clamp(targetPanY, 0, Math.max(0, zH - height));
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput n) {}
}