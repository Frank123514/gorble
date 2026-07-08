package net.got.client.gui;

import net.got.client.ClientFactionCache;
import net.got.client.gui.widget.GotPlaceholderWidget;
import net.got.faction.GotFactionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Screen shown for the Skills / Magic / Culture tabs of {@link GotMainMenuScreen}.
 * Built on the open-book GUI texture instead of the map's torn-parchment
 * panel — the book is drawn at its native size (scaled up to a comfortable
 * on-screen size, no stretch-distortion), and content is laid out across its
 * two facing pages. A "Menu" button above the book returns to
 * {@link GotMainMenuScreen}, same convention as {@link GotMapScreen}.
 */
public final class GotPlaceholderScreen extends Screen {

    private static final ResourceLocation BOOK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/book/book_background.png");
    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    /** Native texture size. */
    private static final int BOOK_TEX_W = 271;
    private static final int BOOK_TEX_H = 180;

    /** On-screen book size — texture scaled up 2x for a comfortable window. */
    private static final int SCALE  = 2;
    private static final int BOOK_W = BOOK_TEX_W * SCALE;
    private static final int BOOK_H = BOOK_TEX_H * SCALE;

    /** Margin inside each page's curved/bound edges before content starts. */
    private static final int PAGE_MARGIN_X = 22 * SCALE;
    private static final int PAGE_MARGIN_TOP = 18 * SCALE;
    private static final int PAGE_MARGIN_BOTTOM = 16 * SCALE;

    /** Half of the book (one page), inset from the outer margins above. */
    private static final int PAGE_W = BOOK_W / 2 - PAGE_MARGIN_X - (6 * SCALE);

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;
    private static final int V_NORM = 0;
    private static final int V_HOV  = 22;

    private static final int RESET_BTN_W = 160;
    private static final int RESET_BTN_H = 20;

    private final GotMenuTab tab;

    private int bookX, bookY;

    // Left/right page content bounds
    private int leftPageX, rightPageX, pageY, pageH;

    // "Menu" button
    private int btnX, btnY;

    // Reset Affiliation button (Culture tab only) — drawn/hit-tested
    // manually, same as the Menu button, rather than through a widget.
    private int resetBtnX, resetBtnY;
    private boolean resetBtnVisible = false;

    public GotPlaceholderScreen(GotMenuTab tab) {
        super(Component.literal(tab.label));
        this.tab = tab;
    }

    /* ------------------------------------------------------------------ */
    /* Init                                                                 */
    /* ------------------------------------------------------------------ */

    @Override
    protected void init() {
        rebuildLayout();
    }

    private void rebuildLayout() {
        clearWidgets();

        bookX = (width  - BOOK_W) / 2;
        bookY = (height - BOOK_H) / 2 + (BUTTON_H / 2);

        pageY = bookY + PAGE_MARGIN_TOP;
        pageH = BOOK_H - PAGE_MARGIN_TOP - PAGE_MARGIN_BOTTOM;

        leftPageX  = bookX + PAGE_MARGIN_X;
        rightPageX = bookX + BOOK_W / 2 + (6 * SCALE);

        // "Menu" button sits above the book, like a bookmark tab
        btnX = bookX;
        btnY = bookY - BUTTON_H - 4;

        resetBtnVisible = tab == GotMenuTab.CULTURE;
        if (resetBtnVisible) {
            resetBtnX = rightPageX + (PAGE_W - RESET_BTN_W) / 2;
            resetBtnY = pageY + pageH - RESET_BTN_H - (4 * SCALE);
        }

        // Left page always shows the tab's title as a heading; right page
        // gets the "Coming Soon" body widget, keeping the same widget every
        // other tab already used for its written content.
        addRenderableWidget(new GotPlaceholderWidget(
                rightPageX, pageY, PAGE_W,
                resetBtnVisible ? pageH - RESET_BTN_H - (8 * SCALE) : pageH,
                bodyTitle(), bodyText()));
    }

    private String bodyTitle() {
        return switch (tab) {
            case SKILLS -> "Skills";
            case MAGIC -> "Magic";
            case CULTURE -> "Culture";
            default -> "";
        };
    }

    private String bodyText() {
        return switch (tab) {
            case SKILLS -> "Your skills and abilities will appear here.";
            case MAGIC -> "The magics of this world will appear here.";
            case CULTURE -> cultureBodyText();
            default -> "";
        };
    }

    /** Builds the summary text shown on the Culture tab above the reset button. */
    private String cultureBodyText() {
        String factionId = ClientFactionCache.getFactionId();
        if (factionId.isEmpty()) {
            return "You have not sworn allegiance to any culture yet.";
        }
        GotFactionData faction = ClientFactionCache.getFaction();
        String houseName = faction != null ? faction.greatHouse() : factionId;
        String currentTitle = ClientFactionCache.getTitle();
        return currentTitle.isEmpty()
                ? "Sworn to " + houseName + "."
                : currentTitle + " of " + houseName + ".";
    }

    /** Closes this screen and opens the faction selection screen so the player can reset their affiliation. */
    private void onResetAffiliation() {
        Minecraft.getInstance().setScreen(new FactionSelectionScreen());
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // Book background — drawn at its native 271x180 texel size, then
        // scaled up 2x via a pose transform. (Blitting straight at
        // BOOK_W x BOOK_H would have asked this blit overload to *sample*
        // a 542x360 region from a 271x180 texture — since width/height here
        // double as both the on-screen size AND the source-rect size, that
        // just wraps the UVs around and tiles the texture instead of
        // scaling it.)
        gfx.pose().pushPose();
        gfx.pose().translate(bookX, bookY, 0);
        gfx.pose().scale(SCALE, SCALE, 1f);
        gfx.blit(RenderType::guiTextured, BOOK_TEXTURE,
                0, 0, 0f, 0f,
                BOOK_TEX_W, BOOK_TEX_H,
                BOOK_TEX_W, BOOK_TEX_H);
        gfx.pose().popPose();

        // Left page: chapter heading for this tab, illuminated-manuscript style
        String heading = tab.label;
        int headX = leftPageX + (PAGE_W - font.width(heading)) / 2;
        gfx.drawString(font, heading, headX, pageY + (8 * SCALE), 0xFF3A2818, false);
        gfx.hLine(leftPageX, leftPageX + PAGE_W - 1, pageY + (8 * SCALE) + font.lineHeight + 4, 0xFF8A7048);

        // Right page: the actual body widget (title/body/"Coming Soon")
        super.render(gfx, mouseX, mouseY, partialTick);

        // "Menu" button
        boolean btnHov = isOver(mouseX, mouseY, btnX, btnY, BUTTON_W, BUTTON_H);
        int btnV = btnHov ? V_HOV : V_NORM;
        gfx.blit(RenderType::guiTextured, WIDGETS_TEXTURE,
                btnX, btnY,
                0, btnV,
                BUTTON_W, BUTTON_H,
                256, 256);
        String btnLabel = "Menu";
        gfx.drawString(font, btnLabel,
                btnX + (BUTTON_W - font.width(btnLabel)) / 2,
                btnY + (BUTTON_H - font.lineHeight) / 2,
                btnHov ? 0xFFFFFFFF : 0xFFE8D8A0, btnHov);

        // Reset Affiliation button (Culture tab only)
        if (resetBtnVisible) {
            boolean resetHov = isOver(mouseX, mouseY, resetBtnX, resetBtnY, RESET_BTN_W, RESET_BTN_H);
            int bg = resetHov ? 0xFF5A3A2A : 0xFF3A2818;
            int border = resetHov ? 0xFFE8C060 : 0xFF887733;
            gfx.fill(resetBtnX, resetBtnY, resetBtnX + RESET_BTN_W, resetBtnY + RESET_BTN_H, bg);
            gfx.hLine(resetBtnX, resetBtnX + RESET_BTN_W - 1, resetBtnY, border);
            gfx.hLine(resetBtnX, resetBtnX + RESET_BTN_W - 1, resetBtnY + RESET_BTN_H - 1, border);
            gfx.vLine(resetBtnX, resetBtnY, resetBtnY + RESET_BTN_H - 1, border);
            gfx.vLine(resetBtnX + RESET_BTN_W - 1, resetBtnY, resetBtnY + RESET_BTN_H - 1, border);

            String label = "Reset Affiliation";
            gfx.drawString(font, label,
                    resetBtnX + (RESET_BTN_W - font.width(label)) / 2,
                    resetBtnY + (RESET_BTN_H - font.lineHeight) / 2,
                    resetHov ? 0xFFFFFFFF : 0xFFE8D8A0, false);
        }
    }

    /* ------------------------------------------------------------------ */
    /* Input                                                                */
    /* ------------------------------------------------------------------ */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isOver(mouseX, mouseY, btnX, btnY, BUTTON_W, BUTTON_H)) {
                Minecraft.getInstance().setScreen(new GotMainMenuScreen());
                return true;
            }
            if (resetBtnVisible && isOver(mouseX, mouseY, resetBtnX, resetBtnY, RESET_BTN_W, RESET_BTN_H)) {
                onResetAffiliation();
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
