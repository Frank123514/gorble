package net.got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * The tab launcher screen - opens first when the player presses M. Just the
 * row of four tabs (Map, Skills, Magic, Culture) plus a close button, drawn
 * directly over the world with nothing else on screen. Clicking a tab opens
 * that tab's own screen - {@link GotMapScreen} for Map,
 * {@link GotPlaceholderScreen} for the rest.
 *
 * <p>Widget sheet layout (all buttons 120x20 px), reused for every tab plaque
 * and the small close button (cropped to 20x20):
 *   v=  0  Back  - normal
 *   v= 22  Back  - hovered
 *   v= 44  Map   - normal
 *   v= 66  Map   - hovered
 *   v= 88  Close - normal
 *   v=110  Close - hovered
 */
public final class GotMainMenuScreen extends Screen {

    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    private static final int TAB_W   = 108;
    private static final int TAB_H   = 20;
    private static final int TAB_GAP = 6;

    private static final int CLOSE_W = 20;
    private static final int CLOSE_H = 20;

    private static final int V_NORM = 0;
    private static final int V_HOV  = 22;

    // Tab row bounds
    private int tabRowY;
    private final int[] tabX = new int[GotMenuTab.values().length];

    // Close button bounds
    private int closeBtnX, closeBtnY;

    // Width/height the cached layout was last computed for. Checked every
    // frame in render() so a GUI scale change or window resize always
    // triggers a relayout instead of leaving tabs/buttons drifted out of place.
    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    public GotMainMenuScreen() {
        super(Component.literal("Game of Thrones"));
    }

    /* ------------------------------------------------------------------ */
    /* Init                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void init() {
        rebuildLayout();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
    }

    private void rebuildLayout() {
        clearWidgets();

        // Tab row centered in the middle of the screen - the only thing
        // this screen shows.
        tabRowY = (height - TAB_H) / 2;
        int totalTabsW = GotMenuTab.values().length * TAB_W + (GotMenuTab.values().length - 1) * TAB_GAP;
        int startX = (width - totalTabsW) / 2;
        for (int i = 0; i < GotMenuTab.values().length; i++) {
            tabX[i] = startX + i * (TAB_W + TAB_GAP);
        }

        closeBtnX = startX + totalTabsW + TAB_GAP;
        closeBtnY = tabRowY;
    }

    /** Opens the screen for the given tab. */
    private void openTab(GotMenuTab tab) {
        Minecraft mc = Minecraft.getInstance();
        if (tab == GotMenuTab.MAP) {
            mc.setScreen(new GotMapScreen());
        } else {
            mc.setScreen(new GotPlaceholderScreen(tab));
        }
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        if (width != lastLayoutWidth || height != lastLayoutHeight) {
            rebuildLayout();
            lastLayoutWidth = width;
            lastLayoutHeight = height;
        }

        // Nothing else drawn here - just the tab row and close button,
        // directly over the world.

        // Tab bar
        for (int i = 0; i < GotMenuTab.values().length; i++) {
            GotMenuTab tab = GotMenuTab.values()[i];
            boolean hovered = isOver(mouseX, mouseY, tabX[i], tabRowY, TAB_W, TAB_H);
            drawPlaque(gfx, tabX[i], tabRowY, TAB_W, TAB_H, hovered, tab.label);
        }

        // Close button
        boolean closeHov = isOver(mouseX, mouseY, closeBtnX, closeBtnY, CLOSE_W, CLOSE_H);
        int closeV = closeHov ? V_HOV : V_NORM;
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
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
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                bx, by,
                0, v,
                w, h,
                256, 256);
        int colour = hovered ? 0xFFFFFFFF : 0xFFE8D8A0;
        int lx = bx + (w - font.width(label)) / 2;
        int ly = by + (h - font.lineHeight) / 2;
        gfx.drawString(font, label, lx, ly, colour, hovered);
    }

    /* ------------------------------------------------------------------ */
    /* Input                                                                */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int i = 0; i < GotMenuTab.values().length; i++) {
                if (isOver(mouseX, mouseY, tabX[i], tabRowY, TAB_W, TAB_H)) {
                    openTab(GotMenuTab.values()[i]);
                    return true;
                }
            }
            if (isOver(mouseX, mouseY, closeBtnX, closeBtnY, CLOSE_W, CLOSE_H)) {
                Minecraft.getInstance().setScreen(null);
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isOver(double mx, double my, int bx, int by, int w, int h) {
        return mx >= bx && mx < bx + w && my >= by && my < by + h;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
