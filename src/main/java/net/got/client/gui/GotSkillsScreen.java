package net.got.client.gui;

import net.minecraft.client.renderer.RenderPipelines;
import net.got.client.ClientSkillCache;
import net.got.network.UnlockPerkPayload;
import net.got.skill.GotSkill;
import net.got.skill.GotSkillCategory;
import net.got.skill.GotSkillPerks;
import net.got.skill.SkillPerk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The real Skills tab, opened from {@link GotMainMenuScreen} in place of the
 * generic {@link GotPlaceholderScreen} the Skills tab used to show. Reads
 * live data from {@link ClientSkillCache} (kept in sync by the server - see
 * {@code SkillXpService#syncToClient}) and never touches server state
 * directly: clicking an unlockable perk just fires off an
 * {@link UnlockPerkPayload} and waits for the next sync to confirm it, same
 * as every other client→server request in this mod.
 *
 * <p>Built on the same open-book GUI (native 271x180, no manual scale factor)
 * as {@link GotPlaceholderScreen}, laid out across its two facing pages:
 * <ul>
 *   <li><b>Left page</b> - every {@link GotSkill}, grouped by {@link GotSkillCategory},
 *       each row showing level and a live XP progress bar. Clicking a row
 *       selects that skill. The full list usually runs taller than the page,
 *       so it scrolls (mouse wheel) rather than paginating - it's a menu you
 *       pick from, not something you'd flip pages to read.</li>
 *   <li><b>Right page</b> - the selected skill's three-tier perk chain (see
 *       {@link GotSkillPerks#forSkill}), each node showing its name,
 *       description, requirement and unlock status. Clicking a node that's
 *       actually unlockable (see {@link ClientSkillCache#canUnlock}) sends
 *       the unlock request. When a skill's perks don't fit on one page, the
 *       tree itself flips across pages using the same vanilla page-turn
 *       arrows ({@link PageButton}) {@link GotPlaceholderScreen} uses.</li>
 * </ul>
 */
public final class GotSkillsScreen extends Screen {

    private static final ResourceLocation BOOK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/book/book_background.png");
    private static final ResourceLocation WIDGETS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

    /** Native texture size — see {@link GotPlaceholderScreen} for why this is
     *  drawn 1:1 with no manual scale factor. */
    private static final int BOOK_TEX_W = 271;
    private static final int BOOK_TEX_H = 180;
    private static final int bookW = BOOK_TEX_W;
    private static final int bookH = BOOK_TEX_H;

    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 20;
    private static final int V_NORM = 0;
    private static final int V_HOV  = 22;

    private static final int COL_TITLE  = 0xFF3A2818;
    private static final int COL_TEXT   = 0xFF3A2818;
    private static final int COL_DIM    = 0xFF6A5838;
    private static final int COL_LOCKED = 0xFF7A7A7A;
    private static final int COL_READY  = 0xFF8A5A18;
    private static final int COL_DONE   = 0xFF3A6A28;

    private static final int HEADER_H = 10;
    private static final int SKILL_ROW_H = 16;
    private static final int SKILL_ROW_GAP = 1;
    private static final int CATEGORY_GAP = 4;

    /** Margin inside each page's curved/bound edges before content starts. */
    private int pageMarginX, pageMarginTop, pageMarginBottom;
    private int pageW;

    // Book position (recomputed on layout)
    private int bookX, bookY;
    private int leftPageX, rightPageX, pageY, pageH;
    private int listX, listY, listW, listBottom;
    private int rightX, rightY, rightW, rightBottom;
    private int btnX, btnY;

    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    private GotSkill selectedSkill = GotSkill.COMBAT;

    /** Left-column hit boxes: skill -> {x, y, w, h}, in unscrolled document space. */
    private final Map<GotSkill, int[]> skillRowBounds = new EnumMap<>(GotSkill.class);

    /** Total height of the skill list content, for clamping {@link #listScroll}. */
    private int listContentHeight;
    /** Left page (skill list) scroll offset — the list is taller than the book page, so it scrolls
     *  rather than paginating, since it's a menu you pick from rather than something you read. */
    private int listScroll = 0;

    /** Right-page perk rows for the currently selected skill, grouped into book pages. */
    private final List<List<PerkRow>> perkPages = new ArrayList<>();
    private int perkPageIndex = 0;
    private PageButton forwardButton;
    private PageButton backButton;

    private record PerkRow(SkillPerk perk, int x, int y, int w, int h, List<FormattedCharSequence> descLines) {}

    public GotSkillsScreen() {
        super(Component.literal("Skills"));
    }

    /* ------------------------------------------------------------------ */
    /* Layout                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    protected void init() {
        rebuildLayout();
        lastLayoutWidth = width;
        lastLayoutHeight = height;
    }

    private void rebuildLayout() {
        clearWidgets();

        pageMarginX = 22;
        pageMarginTop = 18;
        // Extra bottom margin (vs. GotPlaceholderScreen's 16) to leave room
        // for the page-turn arrows sitting below the perk tree.
        pageMarginBottom = 20;
        pageW = bookW / 2 - pageMarginX - 6;

        bookX = (width  - bookW) / 2;
        bookY = (height - bookH) / 2 + (BUTTON_H / 2);

        pageY = bookY + pageMarginTop;
        pageH = bookH - pageMarginTop - pageMarginBottom;

        leftPageX  = bookX + pageMarginX;
        rightPageX = bookX + bookW / 2 + 6;

        btnX = bookX;
        btnY = bookY - BUTTON_H - 4;

        // Left page: "Skills" heading (drawn in render(), same as
        // GotPlaceholderScreen's chapter heading) followed by the
        // scrollable skill list.
        listX = leftPageX;
        listW = pageW;
        listY = pageY + 8 + font.lineHeight + 4 + 6;
        listBottom = pageY + pageH;

        // Right page: selected skill's perk tree, paginated across book pages.
        rightX = rightPageX;
        rightY = pageY;
        rightW = pageW;
        rightBottom = pageY + pageH;

        rebuildSkillRows();
        rebuildPerkRows();

        int centerX = bookX + bookW / 2;
        int arrowY = bookY + bookH - pageMarginBottom + 3;
        backButton = new PageButton(centerX - 27, arrowY, false, b -> perkPageBack(), true);
        forwardButton = new PageButton(centerX + 4, arrowY, true, b -> perkPageForward(), true);
        addRenderableWidget(backButton);
        addRenderableWidget(forwardButton);
        updateArrowVisibility();
    }

    private void rebuildSkillRows() {
        skillRowBounds.clear();
        int y = listY; // document space; shifted by -listScroll at render/hit-test time
        GotSkillCategory lastCategory = null;

        for (GotSkill skill : GotSkill.values()) {
            if (skill.category != lastCategory) {
                if (lastCategory != null) y += CATEGORY_GAP;
                y += HEADER_H;
                lastCategory = skill.category;
            }
            skillRowBounds.put(skill, new int[]{listX, y, listW, SKILL_ROW_H});
            y += SKILL_ROW_H + SKILL_ROW_GAP;
        }

        listContentHeight = y - listY;
        int maxScroll = Math.max(0, listContentHeight - (listBottom - listY));
        listScroll = Math.max(0, Math.min(listScroll, maxScroll));
    }

    /** Recomputes the perk-node rows for {@link #selectedSkill}, wrapped and grouped into book pages. */
    private void rebuildPerkRows() {
        perkPages.clear();
        perkPageIndex = 0;
        if (font == null) return; // called once before init() populates it - guard just in case

        int contentTop = rightY + 22; // room for the skill header line(s) above the tree
        int rowW = rightW;
        int descWidth = rowW - 12;

        List<PerkRow> page = new ArrayList<>();
        int y = contentTop;
        for (SkillPerk perk : GotSkillPerks.forSkill(selectedSkill)) {
            List<FormattedCharSequence> lines = font.split(Component.literal(perk.description()), descWidth);
            int h = 4 + font.lineHeight + 1 + lines.size() * font.lineHeight + 1 + font.lineHeight + 4;
            if (!page.isEmpty() && y + h > rightBottom) {
                perkPages.add(page);
                page = new ArrayList<>();
                y = contentTop;
            }
            page.add(new PerkRow(perk, rightX, y, rowW, h, lines));
            y += h + 4;
        }
        perkPages.add(page); // always at least one page, even if empty

        if (forwardButton != null) updateArrowVisibility();
    }

    private void selectSkill(GotSkill skill) {
        if (skill == selectedSkill) return;
        selectedSkill = skill;
        rebuildPerkRows();
    }

    private void perkPageBack() {
        if (perkPageIndex > 0) {
            perkPageIndex--;
            onPerkPageTurned();
        }
    }

    private void perkPageForward() {
        if (perkPageIndex < perkPages.size() - 1) {
            perkPageIndex++;
            onPerkPageTurned();
        }
    }

    private void onPerkPageTurned() {
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
        updateArrowVisibility();
    }

    private void updateArrowVisibility() {
        backButton.visible = perkPageIndex > 0;
        forwardButton.visible = perkPageIndex < perkPages.size() - 1;
    }

    private int visibleListHeight() {
        return listBottom - listY;
    }

    /* ------------------------------------------------------------------ */
    /* Render                                                               */
    /* ------------------------------------------------------------------ */

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        // Same reasoning as GotPlaceholderScreen: skip vanilla's blur and use
        // the plain dark overlay instead, so the book stays in focus.
        renderTransparentBackground(gfx);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (width != lastLayoutWidth || height != lastLayoutHeight) {
            rebuildLayout();
            lastLayoutWidth = width;
            lastLayoutHeight = height;
        }

        renderBackground(gfx, mouseX, mouseY, partialTick);

        // Book background — native 271x180 size, same convention as
        // GotPlaceholderScreen; Minecraft's GUI Scale setting handles all
        // magnification uniformly for the whole screen.
        gfx.blit(RenderPipelines.GUI_TEXTURED, BOOK_TEXTURE,
                bookX, bookY, 0f, 0f,
                BOOK_TEX_W, BOOK_TEX_H,
                BOOK_TEX_W, BOOK_TEX_H);

        // Left page: "Skills" chapter heading, same illuminated-manuscript
        // style as GotPlaceholderScreen's tab headings.
        String heading = "Skills";
        int headX = leftPageX + (pageW - font.width(heading)) / 2;
        gfx.drawString(font, heading, headX, pageY + 8, COL_TITLE, false);
        gfx.hLine(leftPageX, leftPageX + pageW - 1, pageY + 8 + font.lineHeight + 4, 0xFF8A7048);

        renderSkillList(gfx, mouseX, mouseY);
        renderPerkTree(gfx, mouseX, mouseY);

        for (var renderable : this.renderables) {
            renderable.render(gfx, mouseX, mouseY, partialTick);
        }

        // "Menu" button
        boolean btnHov = isOver(mouseX, mouseY, btnX, btnY, BUTTON_W, BUTTON_H);
        int btnV = btnHov ? V_HOV : V_NORM;
        gfx.blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE,
                btnX, btnY, 0, btnV, BUTTON_W, BUTTON_H, 256, 256);
        String btnLabel = "Menu";
        gfx.drawString(font, btnLabel,
                btnX + (BUTTON_W - font.width(btnLabel)) / 2,
                btnY + (BUTTON_H - font.lineHeight) / 2,
                btnHov ? 0xFFFFFFFF : 0xFFE8D8A0, btnHov);
    }

    /** Left page: skill list, clipped to the page and scrollable (mouse wheel) since it
     *  routinely runs taller than the book page — it's a menu you pick from, not something
     *  you'd want to flip pages to read, so it scrolls rather than paginating. */
    private void renderSkillList(GuiGraphics gfx, int mouseX, int mouseY) {
        boolean mouseInList = mouseX >= listX && mouseX < listX + listW
                && mouseY >= listY && mouseY < listBottom;
        int docMouseY = mouseY + listScroll;

        gfx.enableScissor(listX, listY, listX + listW, listBottom);
        gfx.pose().pushMatrix();
        gfx.pose().translate(0, -listScroll);

        GotSkillCategory lastCategory = null;
        for (GotSkill skill : GotSkill.values()) {
            int[] b = skillRowBounds.get(skill);
            int rowY = b[1];

            if (skill.category != lastCategory) {
                int headY = rowY - HEADER_H - (lastCategory != null ? CATEGORY_GAP : 0);
                gfx.drawString(font, skill.category.label, listX, headY, skill.category.colour, false);
                lastCategory = skill.category;
            }

            boolean selected = skill == selectedSkill;
            boolean hovered = mouseInList && isOver(mouseX, docMouseY, b[0], b[1], b[2], b[3]);
            if (selected || hovered) {
                gfx.fill(b[0] - 2, b[1] - 1, b[0] + b[2], b[1] + b[3] - 1,
                        selected ? 0x552A2210 : 0x33000000);
            }

            int level = ClientSkillCache.getLevel(skill);
            String name = skill.displayName;
            String lvl = "Lv " + level;
            gfx.drawString(font, name, b[0], b[1], COL_TEXT, false);
            gfx.drawString(font, lvl, b[0] + b[2] - font.width(lvl), b[1], COL_DIM, false);

            int barY = b[1] + font.lineHeight + 1;
            int barW = b[2];
            gfx.fill(b[0], barY, b[0] + barW, barY + 3, 0x33000000);
            float frac = ClientSkillCache.getProgressFraction(skill);
            int fillW = Math.round(barW * frac);
            if (fillW > 0) {
                gfx.fill(b[0], barY, b[0] + fillW, barY + 3, skill.category.colour | 0xFF000000);
            }

            if (ClientSkillCache.getAvailablePerkPoints(skill) > 0) {
                gfx.drawString(font, "*", b[0] + b[2] - font.width(lvl) - 8, b[1], COL_READY, false);
            }
        }

        gfx.pose().popMatrix();
        gfx.disableScissor();
    }

    /** Right page: perk tree for {@link #selectedSkill}, flipped through via {@link #forwardButton}/
     *  {@link #backButton} whenever it takes more than one page. */
    private void renderPerkTree(GuiGraphics gfx, int mouseX, int mouseY) {
        int level = ClientSkillCache.getLevel(selectedSkill);
        int available = ClientSkillCache.getAvailablePerkPoints(selectedSkill);

        String header = selectedSkill.displayName + " — Level " + level;
        gfx.drawString(font, header, rightX, rightY, selectedSkill.category.colour | 0xFF000000, false);

        String points = available > 0
                ? available + " perk point" + (available == 1 ? "" : "s") + " available"
                : "No perk points available";
        gfx.drawString(font, points, rightX, rightY + font.lineHeight + 2,
                available > 0 ? COL_READY : COL_DIM, false);

        List<PerkRow> page = perkPages.isEmpty() ? List.of() : perkPages.get(perkPageIndex);

        for (PerkRow row : page) {
            boolean unlocked = ClientSkillCache.hasPerk(row.perk.id());
            boolean unlockable = !unlocked && ClientSkillCache.canUnlock(row.perk);
            boolean hovered = !unlocked && isOver(mouseX, mouseY, row.x, row.y, row.w, row.h);

            int statusColour = unlocked ? COL_DONE : unlockable ? COL_READY : COL_LOCKED;
            int bg = unlocked ? 0x332A4A2A : unlockable ? (hovered ? 0x33C8A030 : 0x1A000000) : 0x14000000;
            gfx.fill(row.x, row.y, row.x + row.w, row.y + row.h, bg);
            gfx.vLine(row.x, row.y, row.y + row.h - 1, statusColour);

            int tx = row.x + 6;
            int ty = row.y + 4;

            gfx.fill(tx, ty + 1, tx + 4, ty + 5, statusColour);
            gfx.drawString(font, "Tier " + row.perk.tier() + ": " + row.perk.name(),
                    tx + 8, ty, unlocked || unlockable ? COL_TEXT : COL_DIM, false);
            ty += font.lineHeight + 1;

            for (FormattedCharSequence line : row.descLines) {
                gfx.drawString(font, line, tx, ty, COL_DIM, false);
                ty += font.lineHeight;
            }
            ty += 1;

            String status;
            if (unlocked) {
                status = "Unlocked";
            } else if (level < row.perk.levelRequirement()) {
                status = "Requires " + selectedSkill.displayName + " level " + row.perk.levelRequirement();
            } else {
                status = unlockable
                        ? "Click to unlock (" + row.perk.pointCost() + " point" + (row.perk.pointCost() == 1 ? "" : "s") + ")"
                        : "Requires the previous tier";
            }
            gfx.drawString(font, status, tx, ty, statusColour, false);
        }

        if (page.isEmpty()) {
            gfx.drawString(font, "No perks defined for this skill.", rightX, rightY + 24, COL_DIM, false);
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

            if (isOver(mouseX, mouseY, listX, listY, listW, listBottom - listY)) {
                double docMouseY = mouseY + listScroll;
                for (Map.Entry<GotSkill, int[]> entry : skillRowBounds.entrySet()) {
                    int[] b = entry.getValue();
                    if (isOver(mouseX, docMouseY, b[0] - 2, b[1] - 1, b[2] + 2, b[3])) {
                        selectSkill(entry.getKey());
                        return true;
                    }
                }
            }

            if (!perkPages.isEmpty()) {
                for (PerkRow row : perkPages.get(perkPageIndex)) {
                    if (isOver(mouseX, mouseY, row.x, row.y, row.w, row.h)) {
                        if (ClientSkillCache.canUnlock(row.perk)) {
                            ClientPacketDistributor.sendToServer(new UnlockPerkPayload(row.perk.id()));
                        }
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (isOver(mouseX, mouseY, listX, listY, listW, listBottom - listY)) {
            int maxScroll = Math.max(0, listContentHeight - visibleListHeight());
            listScroll = Math.max(0, Math.min(maxScroll, listScroll - (int) Math.round(scrollY * SKILL_ROW_H)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private boolean isOver(double mx, double my, int bx, int by, int w, int h) {
        return mx >= bx && mx < bx + w && my >= by && my < by + h;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}