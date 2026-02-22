package net.got.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.got.client.gui.widget.GotMapWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GotMapScreen extends Screen {

    private static final float MAP_SCALE = 0.875f;

    private static final ResourceLocation MAP_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");

    private static final ResourceLocation MAP_BACKGROUND =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/map_background.png");

    // Must match PNG resolution exactly
    private static final int MAP_PIXEL_WIDTH = 3520;
    private static final int MAP_PIXEL_HEIGHT = 3520;

    private GotMapWidget mapWidget;
    private Button backButton;

    public GotMapScreen() {
        super(Component.literal("Map"));
    }

    /* ============================================================= */
    /* =========================== INIT ============================ */
    /* ============================================================= */

    @Override
    protected void init() {
        super.init();
        createOrUpdateWidget();
        createBackButton();
    }

    private void createOrUpdateWidget() {
        int canvasWidth = (int) (this.width * MAP_SCALE);
        int canvasHeight = (int) (this.height * MAP_SCALE);

        int x = (this.width - canvasWidth) / 2;
        int y = (this.height - canvasHeight) / 2;

        if (mapWidget == null) {
            mapWidget = new GotMapWidget(
                    x,
                    y,
                    canvasWidth,
                    canvasHeight,
                    MAP_TEXTURE,
                    MAP_PIXEL_WIDTH,
                    MAP_PIXEL_HEIGHT
            );
            addRenderableWidget(mapWidget);
        } else {
            mapWidget.setX(x);
            mapWidget.setY(y);
            mapWidget.setWidth(canvasWidth);
            mapWidget.setHeight(canvasHeight);
        }
    }

    private void createBackButton() {
        backButton = Button.builder(
                        Component.literal("← Menu"),
                        btn -> Minecraft.getInstance().setScreen(new GotMainMenuScreen())
                ).bounds(10, 10, 120, 20)
                .build();

        addRenderableWidget(backButton);
    }

    /* ============================================================= */
    /* ========================== RESIZE =========================== */
    /* ============================================================= */

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);

        if (mapWidget != null) {
            createOrUpdateWidget();
        }

        if (backButton != null) {
            backButton.setX(10);
            backButton.setY(10);
        }
    }

    /* ============================================================= */
    /* ========================== RENDER =========================== */
    /* ============================================================= */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

// === TILED BACKGROUND (NO BLUR, 1.21 SAFE) ===
        for (int x = 0; x < width; x += 256) {
            for (int y = 0; y < height; y += 256) {
                gfx.blit(
                        RenderType::guiTextured,
                        MAP_BACKGROUND,
                        x,
                        y,
                        0,
                        0,
                        256,
                        256,
                        256,
                        256
                );
            }
        }

        // === MAP TITLE ===
        String title = "The Lands of Ice and Fire";
        int titleWidth = font.width(title);
        int titleX = (width - titleWidth) / 2;
        int titleY = 10;

        gfx.fill(
                titleX - 8,
                titleY - 4,
                titleX + titleWidth + 8,
                titleY + font.lineHeight + 4,
                0xAA000000
        );

        gfx.drawString(font, title, titleX, titleY, 0xFFFFFF, true);

        // Render widgets (map, buttons)
        super.render(gfx, mouseX, mouseY, partialTick);

        // === COORDINATE DISPLAY ===
        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text = "X: " + pos.getX() + "  Z: " + pos.getZ();

                int textWidth = font.width(text);
                int x = (width - textWidth) / 2;
                int y = height - 18;

                gfx.fill(
                        x - 6,
                        y - 4,
                        x + textWidth + 6,
                        y + font.lineHeight + 4,
                        0xAA000000
                );

                gfx.drawString(font, text, x, y, 0xFFFFFF, true);
            }
        }
    }

    /* ============================================================= */
    /* ========================== INPUT ============================ */
    /* ============================================================= */

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (super.mouseDragged(mouseX, mouseY, button, dx, dy)) {
            return true;
        }
        return mapWidget != null
                && mapWidget.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (super.mouseScrolled(mouseX, mouseY, deltaX, deltaY)) {
            return true;
        }
        return mapWidget != null
                && mapWidget.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // 1️⃣ Let vanilla widgets (buttons) handle the click FIRST
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        // 2️⃣ Only if no button handled it, forward to the map
        return mapWidget != null
                && mapWidget.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean handled = super.keyPressed(keyCode, scanCode, modifiers);
        if (handled) return true;

        return mapWidget != null
                && mapWidget.keyPressed(keyCode, scanCode, modifiers);
    }

    /* ============================================================= */

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
