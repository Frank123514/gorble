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

    private static final int CANVAS_BORDER_COLOR = 0xFFFF0000;
    private static final int CANVAS_BG_COLOR     = 0xFF000000;
    private static final int BORDER_THICKNESS    = 2;

    /**
     * Compass size as a fraction of the canvas height so it stays
     * proportional at every GUI scale.  0.12 = 12% of canvas height.
     */
    private static final float COMPASS_FRACTION = 0.12f;
    private static final float COMPASS_MARGIN_FRACTION = 0.015f;

    /**
     * Six discrete zoom levels as multipliers of minZoom.
     *   0 — world overview
     *   1 — continental
     *   2 — regional
     *   3 — local
     *   4 — close-up
     *   5 — street-level
     */
    private static final double[] ZOOM_MULTIPLIERS = { 1.0, 2.0, 4.0, 7.0, 12.0, 20.0 };

    /**
     * Exponential-decay speed for zoom/pan animation.
     * 4.0 ≈ 1.5-second ease per zoom step.
     */
    private static final float ANIM_SPEED = 4.0f;

    private static final double ZOOM_SNAP_EPSILON = 0.0001;
    private static final double PAN_SNAP_EPSILON  = 0.05;

    private static final float BLOCKS_PER_PIXEL    = 56.0f;
    private static final float WORLD_WIDTH_BLOCKS  = 112000f;
    private static final float WORLD_HEIGHT_BLOCKS = 112000f;

    /** Minimum milliseconds between zoom level changes. */
    private static final long ZOOM_COOLDOWN_MS = 400L;

    /* ============================================================= */
    /* ========================== STATE ============================ */
    /* ============================================================= */

    private final ResourceLocation mapTexture;
    private final int textureWidth;
    private final int textureHeight;

    // Rendered state (animated toward target each frame)
    private double zoom;
    private double panX;
    private double panY;

    // Destination state
    private double targetZoom;
    private double targetPanX;
    private double targetPanY;

    private int zoomIndex = 0;

    private boolean dragging       = false;
    private long    lastNanoTime   = -1L;
    private long    lastZoomTimeMs = 0L;

    /* ============================================================= */
    /* ======================== CONSTRUCTOR ======================== */
    /* ============================================================= */

    public GotMapWidget(
            int x, int y, int width, int height,
            ResourceLocation texture,
            int textureWidth, int textureHeight
    ) {
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
    /* ==================== ZOOM LEVEL HELPERS ===================== */
    /* ============================================================= */

    private double getMinZoom() {
        return Math.max(
                (double) width  / textureWidth,
                (double) height / textureHeight
        );
    }

    private double zoomForLevel(int level) {
        return getMinZoom() * ZOOM_MULTIPLIERS[level];
    }

    /* ============================================================= */
    /* ====================== PAN HELPERS ========================== */
    /* ============================================================= */

    private void snapPanToPlayer() {
        Minecraft mc = Minecraft.getInstance();
        double centerMapX = textureWidth  / 2.0;
        double centerMapZ = textureHeight / 2.0;

        if (mc.player instanceof AbstractClientPlayer player) {
            centerMapX = (player.getX() + WORLD_WIDTH_BLOCKS  / 2.0) / BLOCKS_PER_PIXEL;
            centerMapZ = (player.getZ() + WORLD_HEIGHT_BLOCKS / 2.0) / BLOCKS_PER_PIXEL;
        }

        targetPanX = centerMapX * targetZoom - width  / 2.0;
        targetPanY = centerMapZ * targetZoom - height / 2.0;
        clampTargetPan();
    }

    private void zoomAroundCanvasCentre(double newZoom) {
        double centreMapX = (panX + width  / 2.0) / zoom;
        double centreMapZ = (panY + height / 2.0) / zoom;

        targetPanX = centreMapX * newZoom - width  / 2.0;
        targetPanY = centreMapZ * newZoom - height / 2.0;
        targetZoom = newZoom;

        clampTargetPan();
    }

    /* ============================================================= */
    /* ========================== RENDER =========================== */
    /* ============================================================= */

    @Override
    public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // -- Tick animation -----------------------------------------------
        long now = System.nanoTime();
        if (lastNanoTime < 0) lastNanoTime = now;
        float dt = Math.min((now - lastNanoTime) / 1_000_000_000f, 0.1f);
        lastNanoTime = now;
        tickAnimation(dt);

        // -- Canvas background --------------------------------------------
        gfx.fill(getX(), getY(), getX() + width, getY() + height, CANVAS_BG_COLOR);

        // -- Scissor clip — GuiGraphics.enableScissor takes scaled GUI
        //    coordinates, same space as all other draw calls.
        gfx.enableScissor(getX(), getY(), getX() + width, getY() + height);

        // -- Map texture --------------------------------------------------
        int zoomedW = (int) (textureWidth  * zoom);
        int zoomedH = (int) (textureHeight * zoom);
        int drawX   = (int) (getX() - panX);
        int drawY   = (int) (getY() - panY);

        gfx.blit(
                RenderType::guiTextured,
                mapTexture,
                drawX, drawY,
                0, 0,
                zoomedW, zoomedH,
                zoomedW, zoomedH
        );

        // -- Player marker ------------------------------------------------
        drawPlayerMarker(gfx);

        // -- Compass rose — size proportional to canvas height ------------
        int compassSize   = Math.max(16, (int) (height * COMPASS_FRACTION));
        int compassMargin = Math.max(4,  (int) (height * COMPASS_MARGIN_FRACTION));
        int compassX = getX() + compassMargin;
        int compassY = getY() + height - compassSize - compassMargin;

        gfx.blit(
                RenderType::guiTextured,
                COMPASS_TEXTURE,
                compassX, compassY,
                0, 0,
                compassSize, compassSize,
                compassSize, compassSize
        );

        // -- End scissor, draw border + HUD overlays on top ---------------
        gfx.disableScissor();
        drawCanvasBorder(gfx);
        drawZoomLabel(gfx);
    }

    /* ------------------------------------------------------------ */
    /* Animation tick                                                */
    /* ------------------------------------------------------------ */

    private void tickAnimation(float dt) {
        float factor = 1f - (float) Math.exp(-ANIM_SPEED * dt);

        zoom = Mth.lerp(factor, zoom, targetZoom);
        panX = Mth.lerp(factor, panX, targetPanX);
        panY = Mth.lerp(factor, panY, targetPanY);

        if (Math.abs(zoom - targetZoom) < ZOOM_SNAP_EPSILON) zoom = targetZoom;
        if (Math.abs(panX - targetPanX) < PAN_SNAP_EPSILON)  panX = targetPanX;
        if (Math.abs(panY - targetPanY) < PAN_SNAP_EPSILON)  panY = targetPanY;
    }

    /* ------------------------------------------------------------ */
    /* Drawing helpers                                               */
    /* ------------------------------------------------------------ */

    private void drawCanvasBorder(GuiGraphics gfx) {
        int x1 = getX(),         y1 = getY();
        int x2 = getX() + width, y2 = getY() + height;
        gfx.fill(x1,                     y1, x2,                    y1 + BORDER_THICKNESS, CANVAS_BORDER_COLOR);
        gfx.fill(x1,  y2 - BORDER_THICKNESS, x2,                                       y2, CANVAS_BORDER_COLOR);
        gfx.fill(x1,                     y1, x1 + BORDER_THICKNESS,                    y2, CANVAS_BORDER_COLOR);
        gfx.fill(x2 - BORDER_THICKNESS,  y1, x2,                                       y2, CANVAS_BORDER_COLOR);
    }

    /**
     * Draws the player skin head marker.
     * Fixed 8×8 GUI-coordinate size — naturally larger relative to the map
     * when zoomed out, smaller when zoomed in.
     * Clamped flush to the canvas border when the player is off-screen.
     */
    private void drawPlayerMarker(GuiGraphics gfx) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof AbstractClientPlayer player)) return;

        double worldX = player.getX() + WORLD_WIDTH_BLOCKS  / 2.0;
        double worldZ = player.getZ() + WORLD_HEIGHT_BLOCKS / 2.0;

        double pixelX = (worldX / BLOCKS_PER_PIXEL) * zoom;
        double pixelZ = (worldZ / BLOCKS_PER_PIXEL) * zoom;

        int texX = (int) (getX() - panX);
        int texY = (int) (getY() - panY);

        int rawScreenX = (int) (texX + pixelX);
        int rawScreenY = (int) (texY + pixelZ);

        int cx1 = getX()         + BORDER_THICKNESS;
        int cy1 = getY()         + BORDER_THICKNESS;
        int cx2 = getX() + width  - BORDER_THICKNESS;
        int cy2 = getY() + height - BORDER_THICKNESS;

        int drawX = Mth.clamp(rawScreenX - 4, cx1, cx2 - 8);
        int drawY = Mth.clamp(rawScreenY - 4, cy1, cy2 - 8);

        ResourceLocation skin = player.getSkin().texture();
        gfx.blit(
                RenderType::guiTextured,
                skin,
                drawX, drawY,
                8, 8,
                8, 8,
                64, 64
        );
    }

    /**
     * Draws the zoom level label — plain white text, top-right of canvas.
     */
    private void drawZoomLabel(GuiGraphics gfx) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.font == null) return;

        String label  = "Zoom Level: " + (zoomIndex + 1) + "/" + ZOOM_MULTIPLIERS.length;
        int    margin = 6;
        int    labelX = getX() + width - mc.font.width(label) - margin;
        int    labelY = getY() + margin;

        gfx.drawString(mc.font, label, labelX, labelY, 0xFFFFFF, false);
    }

    /* ============================================================= */
    /* ========================== INPUT ============================ */
    /* ============================================================= */

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        long now = System.currentTimeMillis();
        if (now - lastZoomTimeMs < ZOOM_COOLDOWN_MS) return true;

        int prevIndex = zoomIndex;
        if (deltaY > 0) {
            zoomIndex = Math.min(zoomIndex + 1, ZOOM_MULTIPLIERS.length - 1);
        } else {
            zoomIndex = Math.max(zoomIndex - 1, 0);
        }

        if (zoomIndex != prevIndex) {
            zoomAroundCanvasCentre(zoomForLevel(zoomIndex));
            lastZoomTimeMs = now;
        }

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        if (button == 0) {
            dragging = true;
            return true;
        }

        if (button == 1) {
            teleportTo(mouseX, mouseY);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (!dragging) return false;

        panX       -= dx;
        panY       -= dy;
        targetPanX -= dx;
        targetPanY -= dy;

        clampPan();
        clampTargetPan();
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return false;
    }

    /* ============================================================= */
    /* ==================== COORDINATE QUERY ======================= */
    /* ============================================================= */

    @Nullable
    public BlockPos getHoveredWorldPos(double mouseX, double mouseY) {
        if (!isMouseOver(mouseX, mouseY)) return null;

        double localX = mouseX - getX();
        double localY = mouseY - getY();
        double mapX   = (localX + panX) / zoom;
        double mapY   = (localY + panY) / zoom;

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
        int    blockX = (int) (localX * BLOCKS_PER_PIXEL - WORLD_WIDTH_BLOCKS  / 2.0);
        int    blockZ = (int) (localY * BLOCKS_PER_PIXEL - WORLD_HEIGHT_BLOCKS / 2.0);

        if (mc.getConnection() != null) {
            mc.getConnection().send(new MapTeleportPayload(blockX, blockZ));
        }
    }

    /* ============================================================= */
    /* ========================== CLAMP ============================ */
    /* ============================================================= */

    private void clampPan() {
        int zoomedW = (int) (textureWidth  * zoom);
        int zoomedH = (int) (textureHeight * zoom);
        panX = Mth.clamp(panX, 0, Math.max(0, zoomedW - width));
        panY = Mth.clamp(panY, 0, Math.max(0, zoomedH - height));
    }

    private void clampTargetPan() {
        int zoomedW = (int) (textureWidth  * targetZoom);
        int zoomedH = (int) (textureHeight * targetZoom);
        targetPanX = Mth.clamp(targetPanX, 0, Math.max(0, zoomedW - width));
        targetPanY = Mth.clamp(targetPanY, 0, Math.max(0, zoomedH - height));
    }

    /* ============================================================= */

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narration) {}
}