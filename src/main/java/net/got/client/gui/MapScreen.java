package net.got.client.gui;

import net.got.client.gui.widget.MapWidget;
import net.got.faction.WaypointRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class MapScreen extends Screen {

    private static final Identifier MAP_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/known_world.png");

    private static final Identifier WIDGETS_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;

    private static final int BUTTON_V_NORMAL  = 0;
    private static final int BUTTON_V_HOVERED = 22;

    private static final int MAP_PIXEL_WIDTH  = 4207;
    private static final int MAP_PIXEL_HEIGHT = 3277;

    private static final int BACKDROP_COLOUR = 0xFF111111;

    private MapWidget mapWidget;

    private int btnX, btnY;
    private boolean btnHovered = false;

    public MapScreen() {
        super(Component.literal("Map"));
    }

    @Override
    protected void init() {
        mapWidget = null;
        rebuildLayout();
    }

    private void rebuildLayout() {
        clearWidgets();

        int canvasX = 0;
        int canvasY = 0;
        int canvasW = width;
        int canvasH = height;

        btnX = 6;
        btnY = 6;

        mapWidget = new MapWidget(
                canvasX, canvasY,
                canvasW, canvasH,
                MAP_TEXTURE,
                MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT
        );
        
        mapWidget.setWaypoints(WaypointRegistry.ALL, -1);
        addRenderableWidget(mapWidget);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        gfx.fill(0, 0, width, height, BACKDROP_COLOUR);

        super.render(gfx, mouseX, mouseY, partialTick);

        btnHovered = mouseX >= btnX && mouseX < btnX + BUTTON_W
                && mouseY >= btnY && mouseY < btnY + BUTTON_H;
        int btnV = btnHovered ? BUTTON_V_HOVERED : BUTTON_V_NORMAL;

        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
                btnX, btnY,
                0, btnV,
                BUTTON_W, BUTTON_H,
                256, 256);

        String btnLabel = "Menu";
        int lblX = btnX + (BUTTON_W - font.width(btnLabel)) / 2;
        int lblY = btnY + (BUTTON_H - font.lineHeight)       / 2;
        gfx.drawString(font, btnLabel, lblX, lblY,
                btnHovered ? 0xFFFFFFFF : 0xFFE8D8A0, btnHovered);

        if (mapWidget != null) {
            BlockPos pos = mapWidget.getHoveredWorldPos(mouseX, mouseY);
            if (pos != null) {
                String text = "x: " + pos.getX() + "  z: " + pos.getZ();
                int    tx   = (width - font.width(text)) / 2;
                int    ty   = height - font.lineHeight - 6;
                gfx.drawString(font, text, tx, ty, 0xFFE8D8A0, false);
            }
        }
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (__event.button() == 0 && __event.x() >= btnX && __event.x() < btnX + BUTTON_W
                && __event.y() >= btnY && __event.y() < btnY + BUTTON_H) {
            Minecraft.getInstance().setScreen(new MainMenuScreen());
            return true;
        }
        if (super.mouseClicked(__event, __doubleClick)) return true;
        return mapWidget != null && mapWidget.mouseClicked(__event, __doubleClick);
    }

    @Override
    public boolean mouseDragged(net.minecraft.client.input.MouseButtonEvent __event, double dx, double dy){
        if (super.mouseDragged(__event, dx, dy)) return true;
        return mapWidget != null && mapWidget.mouseDragged(__event, dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        if (super.mouseScrolled(mouseX, mouseY, deltaX, deltaY)) return true;
        return mapWidget != null && mapWidget.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(net.minecraft.client.input.KeyEvent __event){
        if (super.keyPressed(__event)) return true;
        return mapWidget != null && mapWidget.keyPressed(__event);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
