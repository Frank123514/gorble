package net.got.client.gui;

import net.minecraft.client.renderer.RenderPipelines;
import net.got.client.ClientSkillCache;
import net.got.network.UnlockPerkPayload;
import net.got.skill.Skill;
import net.got.skill.SkillCategory;
import net.got.skill.SkillPerks;
import net.got.skill.SkillPerk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class SkillsScreen extends Screen {

    private static final Identifier BOOK_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/book/book_background.png");
    private static final Identifier WIDGETS_TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/gui/map/widgets.png");

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

    private int pageMarginX, pageMarginTop, pageMarginBottom;
    private int pageW;

    private int bookX, bookY;
    private int leftPageX, rightPageX, pageY, pageH;
    private int listX, listY, listW, listBottom;
    private int rightX, rightY, rightW, rightBottom;
    private int btnX, btnY;

    private int lastLayoutWidth = -1;
    private int lastLayoutHeight = -1;

    private Skill selectedSkill = Skill.COMBAT;

    private final Map<Skill, int[]> skillRowBounds = new EnumMap<>(Skill.class);

    private int listContentHeight;
    
    private int listScroll = 0;

    private final List<List<PerkRow>> perkPages = new ArrayList<>();
    private int perkPageIndex = 0;
    private PageButton forwardButton;
    private PageButton backButton;

    private record PerkRow(SkillPerk perk, int x, int y, int w, int h, List<FormattedCharSequence> descLines) {}

    public SkillsScreen() {
        super(Component.literal("Skills"));
    }

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

        listX = leftPageX;
        listW = pageW;
        listY = pageY + 8 + font.lineHeight + 4 + 6;
        listBottom = pageY + pageH;

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
        int y = listY;
        SkillCategory lastCategory = null;

        for (Skill skill : Skill.values()) {
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

    private void rebuildPerkRows() {
        perkPages.clear();
        perkPageIndex = 0;
        if (font == null) return;

        int contentTop = rightY + 22;
        int rowW = rightW;
        int descWidth = rowW - 12;

        List<PerkRow> page = new ArrayList<>();
        int y = contentTop;
        for (SkillPerk perk : SkillPerks.forSkill(selectedSkill)) {
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
        perkPages.add(page);

        if (forwardButton != null) updateArrowVisibility();
    }

    private void selectSkill(Skill skill) {
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

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        
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

        gfx.blit(RenderPipelines.GUI_TEXTURED, BOOK_TEXTURE,
                bookX, bookY, 0f, 0f,
                BOOK_TEX_W, BOOK_TEX_H,
                BOOK_TEX_W, BOOK_TEX_H);

        String heading = "Skills";
        int headX = leftPageX + (pageW - font.width(heading)) / 2;
        gfx.drawString(font, heading, headX, pageY + 8, COL_TITLE, false);
        gfx.hLine(leftPageX, leftPageX + pageW - 1, pageY + 8 + font.lineHeight + 4, 0xFF8A7048);

        renderSkillList(gfx, mouseX, mouseY);
        renderPerkTree(gfx, mouseX, mouseY);

        for (var renderable : this.renderables) {
            renderable.render(gfx, mouseX, mouseY, partialTick);
        }

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

    private void renderSkillList(GuiGraphics gfx, int mouseX, int mouseY) {
        boolean mouseInList = mouseX >= listX && mouseX < listX + listW
                && mouseY >= listY && mouseY < listBottom;
        int docMouseY = mouseY + listScroll;

        gfx.enableScissor(listX, listY, listX + listW, listBottom);
        gfx.pose().pushMatrix();
        gfx.pose().translate(0, -listScroll);

        SkillCategory lastCategory = null;
        for (Skill skill : Skill.values()) {
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

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (__event.button() == 0) {
            if (isOver(__event.x(), __event.y(), btnX, btnY, BUTTON_W, BUTTON_H)) {
                Minecraft.getInstance().setScreen(new MainMenuScreen());
                return true;
            }

            if (isOver(__event.x(), __event.y(), listX, listY, listW, listBottom - listY)) {
                double docMouseY = __event.y() + listScroll;
                for (Map.Entry<Skill, int[]> entry : skillRowBounds.entrySet()) {
                    int[] b = entry.getValue();
                    if (isOver(__event.x(), docMouseY, b[0] - 2, b[1] - 1, b[2] + 2, b[3])) {
                        selectSkill(entry.getKey());
                        return true;
                    }
                }
            }

            if (!perkPages.isEmpty()) {
                for (PerkRow row : perkPages.get(perkPageIndex)) {
                    if (isOver(__event.x(), __event.y(), row.x, row.y, row.w, row.h)) {
                        if (ClientSkillCache.canUnlock(row.perk)) {
                            ClientPacketDistributor.sendToServer(new UnlockPerkPayload(row.perk.id()));
                        }
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(__event, __doubleClick);
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