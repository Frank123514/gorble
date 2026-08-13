package net.got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class MainMenuScreen extends Screen {

    private static final Identifier WIDGETS_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int TAB_W   = 108;
    private static final int TAB_H   = 20;
    private static final int TAB_GAP = 6;

    private static final int CLOSE_W = 20;
    private static final int CLOSE_H = 20;

    private static final int V_NORM = 0;
    private static final int V_HOV  = 22;

    private int tabRowY;
    private final int[] tabX = new int[MenuTab.values().length];

    private int closeBtnX, closeBtnY;

    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    public MainMenuScreen() {
        super(Component.literal("Game of Thrones"));
    }

    @Override
    protected void init() {
        rebuildLayout();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
    }

    private void rebuildLayout() {
        clearWidgets();

        tabRowY = (height - TAB_H) / 2;
        int totalTabsW = MenuTab.values().length * TAB_W + (MenuTab.values().length - 1) * TAB_GAP;
        int startX = (width - totalTabsW) / 2;
        for (int i = 0; i < MenuTab.values().length; i++) {
            tabX[i] = startX + i * (TAB_W + TAB_GAP);
        }

        closeBtnX = startX + totalTabsW + TAB_GAP;
        closeBtnY = tabRowY;
    }

    private void openTab(MenuTab tab) {
        Minecraft mc = Minecraft.getInstance();
        switch (tab) {
            case MAP -> mc.setScreen(new MapScreen());
            case SKILLS -> mc.setScreen(new SkillsScreen());
            default -> mc.setScreen(new PlaceholderScreen(tab));
        }
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        if (width != lastLayoutWidth || height != lastLayoutHeight) {
            rebuildLayout();
            lastLayoutWidth = width;
            lastLayoutHeight = height;
        }

        for (int i = 0; i < MenuTab.values().length; i++) {
            MenuTab tab = MenuTab.values()[i];
            boolean hovered = isOver(mouseX, mouseY, tabX[i], tabRowY, TAB_W, TAB_H);
            drawPlaque(gfx, tabX[i], tabRowY, TAB_W, TAB_H, hovered, tab.label);
        }

        boolean closeHov = isOver(mouseX, mouseY, closeBtnX, closeBtnY, CLOSE_W, CLOSE_H);
        int closeV = closeHov ? V_HOV : V_NORM;
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
                closeBtnX, closeBtnY,
                0, closeV,
                CLOSE_W, CLOSE_H,
                256, 256);
        String x = "X";
        gfx.drawString(font, x,
                closeBtnX + (CLOSE_W - font.width(x)) / 2,
                closeBtnY + (CLOSE_H - font.lineHeight) / 2,
                closeHov ? 0xFFFFFFFF : 0xFFE8D8A0, closeHov);
    }

    private void drawPlaque(GuiGraphics gfx, int bx, int by, int w, int h,
                            boolean hovered, String label) {
        int v = hovered ? V_HOV : V_NORM;
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
                bx, by,
                0, v,
                w, h,
                256, 256);
        int colour = hovered ? 0xFFFFFFFF : 0xFFE8D8A0;
        int lx = bx + (w - font.width(label)) / 2;
        int ly = by + (h - font.lineHeight) / 2;
        gfx.drawString(font, label, lx, ly, colour, hovered);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (__event.button() == 0) {
            for (int i = 0; i < MenuTab.values().length; i++) {
                if (isOver(__event.x(), __event.y(), tabX[i], tabRowY, TAB_W, TAB_H)) {
                    openTab(MenuTab.values()[i]);
                    return true;
                }
            }
            if (isOver(__event.x(), __event.y(), closeBtnX, closeBtnY, CLOSE_W, CLOSE_H)) {
                Minecraft.getInstance().setScreen(null);
                return true;
            }
        }

        return super.mouseClicked(__event, __doubleClick);
    }

    private boolean isOver(double mx, double my, int bx, int by, int w, int h) {
        return mx >= bx && mx < bx + w && my >= by && my < by + h;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
