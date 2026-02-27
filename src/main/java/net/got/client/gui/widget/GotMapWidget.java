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

    private static final int COMPASS_SIZE = 64;   // pixels
    private static final int COMPASS_MARGIN = 8;  // pixels from canvas edge

    private static final int CANVAS_BORDER_COLOR = 0xFFFF0000; // solid red
    private static final int CANVAS_BG_COLOR = 0xFF000000;     // black background
    private static final int BORDER_THICKNESS = 2;

    private static final float ZOOM_FACTOR = 1.1f;
    private static final double MIN_ZOOM = 0.25; // zoom WAY out
    private static final double MAX_ZOOM = 10.0;

    // 1 pixel = 20 blocks (matching world generation scale)
    private static final float BLOCKS_PER_PIXEL = 56.0f;
    private static final float WORLD_WIDTH_BLOCKS = 112000;  // 2000 * 28
    private static final float WORLD_HEIGHT_BLOCKS = 112000; // 2000 * 28

    /* ============================================================= */

    private final ResourceLocation mapTexture;
    private final int textureWidth;
    private final int textureHeight;

    private double zoom;
    private double panX;
    private double panY;

    private boolean dragging;

    public GotMapWidget(
            int x,
            int y,
            int width,
            int height,
            ResourceLocation texture,
            int textureWidth,
            int textureHeight
    ) {
        super(x, y, width, height, Component.empty());
        this.mapTexture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        // Default zoom fits map to screen
        this.zoom = Math.max(
                (double) width / textureWidth,
                (double) height / textureHeight
        );

        centerOnWorldOrigin();
    }

    /* ============================================================= */
    /* ========================== RENDER =========================== */
    /* ============================================================= */

    @Override
    public void renderWidget(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // === DRAW CANVAS BACKGROUND ===
        gfx.fill(
                getX(),
                getY(),
                getX() + width,
                getY() + height,
                CANVAS_BG_COLOR
        );

        // === ENABLE SCISSOR (CLIP TO CANVAS) ===
        gfx.enableScissor(
                getX(),
                getY(),
                getX() + width,
                getY() + height
        );

        // === DRAW MAP TEXTURE INSIDE CANVAS ===
        int zoomedW = (int) (textureWidth * zoom);
        int zoomedH = (int) (textureHeight * zoom);

        int drawX = (int) (getX() - panX);
        int drawY = (int) (getY() - panY);

        gfx.blit(
                RenderType::guiTextured,
                mapTexture,
                drawX,
                drawY,
                0,
                0,
                zoomedW,
                zoomedH,
                zoomedW,
                zoomedH
        );

        // === PLAYER MARKER (ALSO CLIPPED) ===
        drawPlayerMarker(gfx, drawX, drawY);

// === COMPASS ROSE (BOTTOM-LEFT OF CANVAS) ===
        int compassX = getX() + COMPASS_MARGIN;
        int compassY = getY() + height - COMPASS_SIZE - COMPASS_MARGIN;

        gfx.blit(
                RenderType::guiTextured,
                COMPASS_TEXTURE,
                compassX,
                compassY,
                0,
                0,
                COMPASS_SIZE,
                COMPASS_SIZE,
                COMPASS_SIZE,
                COMPASS_SIZE
        );

        // === DISABLE SCISSOR ===
        gfx.disableScissor();

        // === DRAW CANVAS BORDER (ON TOP) ===
        drawCanvasBorder(gfx);
    }

    private void drawCanvasBorder(GuiGraphics gfx) {
        int x1 = getX();
        int y1 = getY();
        int x2 = getX() + width;
        int y2 = getY() + height;

        // Top
        gfx.fill(x1, y1, x2, y1 + BORDER_THICKNESS, CANVAS_BORDER_COLOR);
        // Bottom
        gfx.fill(x1, y2 - BORDER_THICKNESS, x2, y2, CANVAS_BORDER_COLOR);
        // Left
        gfx.fill(x1, y1, x1 + BORDER_THICKNESS, y2, CANVAS_BORDER_COLOR);
        // Right
        gfx.fill(x2 - BORDER_THICKNESS, y1, x2, y2, CANVAS_BORDER_COLOR);
    }

    private void drawPlayerMarker(GuiGraphics gfx, int texX, int texY) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof AbstractClientPlayer player)) return;

        // Convert world coordinates to map pixel coordinates
        // World origin (0,0) maps to center of the map
        double worldX = player.getX() + WORLD_WIDTH_BLOCKS / 2.0;
        double worldZ = player.getZ() + WORLD_HEIGHT_BLOCKS / 2.0;

        double pixelX = (worldX / BLOCKS_PER_PIXEL) * zoom;
        double pixelZ = (worldZ / BLOCKS_PER_PIXEL) * zoom;

        int screenX = (int) (texX + pixelX);
        int screenY = (int) (texY + pixelZ);

        ResourceLocation skin = player.getSkin().texture();

        gfx.blit(
                RenderType::guiTextured,
                skin,
                screenX - 4,
                screenY - 4,
                8, 8,
                8, 8,
                64, 64
        );
    }

    /* ============================================================= */
    /* ========================== INPUT ============================ */
    /* ============================================================= */

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (!isMouseOver(mouseX, mouseY)) return false;

        double oldZoom = zoom;
        zoom = Mth.clamp(
                zoom * (deltaY > 0 ? ZOOM_FACTOR : 1.0 / ZOOM_FACTOR),
                MIN_ZOOM,
                MAX_ZOOM
        );

        // Zoom around cursor
        double relX = mouseX - getX();
        double relY = mouseY - getY();

        panX = (panX + relX) * (zoom / oldZoom) - relX;
        panY = (panY + relY) * (zoom / oldZoom) - relY;

        clampPan();
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

        panX -= dx;
        panY -= dy;
        clampPan();
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return false;
    }

    @Nullable
    public BlockPos getHoveredWorldPos(double mouseX, double mouseY) {
        if (!isMouseOver(mouseX, mouseY)) {
            return null;
        }

        // Mouse position relative to canvas
        double localX = mouseX - getX();
        double localY = mouseY - getY();

        // Apply pan and zoom to get pixel coordinates in the map texture
        double mapX = (localX + panX) / zoom;
        double mapY = (localY + panY) / zoom;

        // Outside texture bounds
        if (mapX < 0 || mapY < 0 || mapX >= textureWidth || mapY >= textureHeight) {
            return null;
        }

        // Convert pixel coordinates to world coordinates
        // Pixel (0,0) = world (-WORLD_WIDTH_BLOCKS/2, -WORLD_HEIGHT_BLOCKS/2)
        int worldX = (int) ((mapX * BLOCKS_PER_PIXEL) - WORLD_WIDTH_BLOCKS / 2);
        int worldZ = (int) ((mapY * BLOCKS_PER_PIXEL) - WORLD_HEIGHT_BLOCKS / 2);

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

        // Convert pixel coordinates to world coordinates
        // Pixel (0,0) = world (-WORLD_WIDTH/2, -WORLD_HEIGHT/2)
        int blockX = (int) ((localX * BLOCKS_PER_PIXEL) - WORLD_WIDTH_BLOCKS / 2);
        int blockZ = (int) ((localY * BLOCKS_PER_PIXEL) - WORLD_HEIGHT_BLOCKS / 2);

        if (mc.getConnection() != null) {
            mc.getConnection().send(new MapTeleportPayload(blockX, blockZ));
        }
    }

    /* ============================================================= */
    /* ========================== UTILS ============================ */
    /* ============================================================= */

    private void clampPan() {
        int zoomedW = (int) (textureWidth * zoom);
        int zoomedH = (int) (textureHeight * zoom);

        panX = Mth.clamp(panX, 0, Math.max(0, zoomedW - width));
        panY = Mth.clamp(panY, 0, Math.max(0, zoomedH - height));
    }

    private void centerOnWorldOrigin() {
        // Center on the middle of the rectangular map
        double centerX = (textureWidth / 2.0) * zoom;
        double centerY = (textureHeight / 2.0) * zoom;

        panX = centerX - width / 2.0;
        panY = centerY - height / 2.0;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narration) {}
}