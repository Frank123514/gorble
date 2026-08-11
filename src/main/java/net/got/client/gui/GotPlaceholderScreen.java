package net.got.client.gui;

import net.got.client.ClientFactionCache;
import net.got.client.gui.widget.GotPlaceholderWidget;
import net.got.faction.GotFactionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

/**
 * Screen shown for the Skills / Magic / Culture tabs of {@link GotMainMenuScreen}.
 * Built on the open-book GUI texture instead of the map's torn-parchment
 * panel — the book is drawn at its native 271x180 size, letting Minecraft's
 * GUI Scale setting do all the magnification (same as every other texture
 * in the game), and content is laid out across its two facing pages. A
 * "Menu" button above the book returns to {@link GotMainMenuScreen}, same
 * convention as {@link GotMapScreen}.
 */
public final class GotPlaceholderScreen extends Screen {

    private static final Identifier BOOK_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/book/book_background.png");
    private static final Identifier WIDGETS_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    /** Native texture size. Drawn 1:1 — no extra scale factor. Minecraft's
     *  own GUI Scale setting already scales this whole screen uniformly,
     *  same as it does for every other texture in the game (buttons,
     *  inventory, etc.). Stacking a second manual scale on top of that
     *  was pointless complexity and the only thing different about this
     *  texture's draw call vs. everything else on the screen. */
    private static final int BOOK_TEX_W = 271;
    private static final int BOOK_TEX_H = 180;

    private static final int bookW = BOOK_TEX_W;
    private static final int bookH = BOOK_TEX_H;

    /** Margin inside each page's curved/bound edges before content starts. */
    private int pageMarginX, pageMarginTop, pageMarginBottom;

    /** Half of the book (one page), inset from the outer margins above. */
    private int pageW;

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

    // Book page-turning, vanilla-style (same PageButton widget BookViewScreen
    // uses, same forward/back arrow sprites). The right page's body text is
    // split into "pages" per tab; flipping just swaps which page's text the
    // GotPlaceholderWidget shows.
    private String[] pages;
    private int pageIndex;
    private PageButton forwardButton;
    private PageButton backButton;

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

        pageMarginX = 22;
        pageMarginTop = 18;
        pageMarginBottom = 16;
        pageW = bookW / 2 - pageMarginX - 6;

        bookX = (width  - bookW) / 2;
        bookY = (height - bookH) / 2 + (BUTTON_H / 2);

        pageY = bookY + pageMarginTop;
        pageH = bookH - pageMarginTop - pageMarginBottom;

        leftPageX  = bookX + pageMarginX;
        rightPageX = bookX + bookW / 2 + 6;

        // "Menu" button sits above the book, like a bookmark tab
        btnX = bookX;
        btnY = bookY - BUTTON_H - 4;

        resetBtnVisible = tab == GotMenuTab.CULTURE;
        if (resetBtnVisible) {
            resetBtnX = rightPageX + (pageW - RESET_BTN_W) / 2;
            resetBtnY = pageY + pageH - RESET_BTN_H - 4;
        }

        pages = bodyPages();
        if (pageIndex >= pages.length) pageIndex = 0;

        // Left page always shows the tab's title as a heading; right page
        // gets the "Coming Soon" body widget, keeping the same widget every
        // other tab already used for its written content.
        addRenderableWidget(new GotPlaceholderWidget(
                rightPageX, pageY, pageW,
                resetBtnVisible ? pageH - RESET_BTN_H - 8 : pageH,
                bodyTitle(), pages[pageIndex]));

        // Vanilla's own book page-turn arrows (same PageButton widget and
        // sprites as BookViewScreen), sitting in the margin band below the
        // page text, straddling the spine like every vanilla book/lectern.
        int centerX = bookX + bookW / 2;
        int arrowY = bookY + bookH - pageMarginBottom + 3;
        backButton = new PageButton(centerX - 27, arrowY, false, b -> pageBack(), true);
        forwardButton = new PageButton(centerX + 4, arrowY, true, b -> pageForward(), true);
        addRenderableWidget(backButton);
        addRenderableWidget(forwardButton);
        updateArrowVisibility();
    }

    private String bodyTitle() {
        return switch (tab) {
            case SKILLS -> "Skills";
            case MAGIC -> "Magic";
            case CULTURE -> "Culture";
            default -> "";
        };
    }

    /** The text shown on the right page, split across however many pages this tab currently has. */
    private String[] bodyPages() {
        return switch (tab) {
            case SKILLS -> new String[] { "Your skills and abilities will appear here." };
            case MAGIC -> new String[] {
                    "The magics of this world will appear here.",
                    "Warging, greensight, and the old religions of Westeros are still being written."
            };
            case CULTURE -> new String[] {
                    cultureBodyText(),
                    "House allegiances, titles, and cultural bonuses are still being written."
            };
            default -> new String[] { "" };
        };
    }

    private void pageBack() {
        if (pageIndex > 0) {
            pageIndex--;
            onPageTurned();
        }
    }

    private void pageForward() {
        if (pageIndex < pages.length - 1) {
            pageIndex++;
            onPageTurned();
        }
    }

    private void onPageTurned() {
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        rebuildLayout();
    }

    private void updateArrowVisibility() {
        backButton.visible = pageIndex > 0;
        forwardButton.visible = pageIndex < pages.length - 1;
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
    public void renderBackground(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // Vanilla Screen#renderBackground blurs the 3D world behind the GUI
        // via gameRenderer.processBlurEffect() ("Menu Background Blurriness").
        // That's what was making the whole scene look "out of focus" —
        // never the book texture. We skip that blur and use vanilla's own
        // renderTransparentBackground() instead (same one BookEditScreen
        // uses) — the real, unmodified vanilla dark overlay.
        renderTransparentBackground(gfx);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {

        // Draw the (non-blurred) background exactly once, before anything
        // else. Screen#render() normally calls renderBackground() itself —
        // but we draw the book texture before calling super.render() below,
        // and super.render() would call renderBackground() again on its way
        // in, painting the dark overlay a second time *on top of* the
        // already-drawn book and heading. Calling it here ourselves, up
        // front, and swapping the later super.render() call for a direct
        // renderables loop (see below) means it only ever runs once, before
        // the book is drawn — never over it.
        renderBackground(gfx, mouseX, mouseY, partialTick);

        // Book background — drawn at native 271x180 size, no scaling at all.
        // Minecraft's GUI Scale setting handles all magnification uniformly
        // for the whole screen, same as every other texture here.
        gfx.blit(RenderPipelines.GUI_TEXTURED, BOOK_TEXTURE,
                bookX, bookY, 0f, 0f,
                BOOK_TEX_W, BOOK_TEX_H,
                BOOK_TEX_W, BOOK_TEX_H);

        // Left page: chapter heading for this tab, illuminated-manuscript style
        String heading = tab.label;
        int headX = leftPageX + (pageW - font.width(heading)) / 2;
        gfx.drawString(font, heading, headX, pageY + 8, 0xFF3A2818, false);
        gfx.hLine(leftPageX, leftPageX + pageW - 1, pageY + 8 + font.lineHeight + 4, 0xFF8A7048);

        // Right page: the actual body widget (title/body/"Coming Soon").
        // NOT super.render(gfx, ...) — that would call renderBackground()
        // again, redrawing the dark overlay on top of the book we just
        // drew above. This is the rest of what Screen#render() does besides
        // that call: just run the registered renderables.
        for (Renderable renderable : this.renderables) {
            renderable.render(gfx, mouseX, mouseY, partialTick);
        }

        // "Menu" button
        boolean btnHov = isOver(mouseX, mouseY, btnX, btnY, BUTTON_W, BUTTON_H);
        int btnV = btnHov ? V_HOV : V_NORM;
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
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
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (__event.button() == 0) {
            if (isOver(__event.x(), __event.y(), btnX, btnY, BUTTON_W, BUTTON_H)) {
                Minecraft.getInstance().setScreen(new GotMainMenuScreen());
                return true;
            }
            if (resetBtnVisible && isOver(__event.x(), __event.y(), resetBtnX, resetBtnY, RESET_BTN_W, RESET_BTN_H)) {
                onResetAffiliation();
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