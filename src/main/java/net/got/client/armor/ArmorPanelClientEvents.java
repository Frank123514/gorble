package net.got.client.armor;

import net.got.GotMod;
import net.got.armorpanel.ArmorPanelLayout;
import net.got.brigandine.BrigandineSlot;
import net.got.coif.CoifSlot;
import net.got.gambeson.GambesonSlot;
import net.got.hauberk.HauberkSlot;
import net.got.mixin.CreativeSlotWrapperAccessor;
import net.got.plate.PlateSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

/**
 * Draws the armor panel (the plain vanilla-style box behind every custom armor slot) on the left of the
 * inventory screens. Runs first so the per-slot frames/icons draw on top of it. While the recipe book is
 * open (it occupies the same spot) the panel and its slots are hidden.
 */
@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
public final class ArmorPanelClientEvents {

    private static final String TITLE = "Armor";

    private static boolean isCustom(Slot slot) {
        Slot s = slot;
        if (slot instanceof CreativeSlotWrapperAccessor w) s = w.got$getTarget();
        return s instanceof GambesonSlot || s instanceof HauberkSlot || s instanceof BrigandineSlot
                || s instanceof CoifSlot || s instanceof PlateSlot;
    }

    private static boolean recipeBookOpen() {
        var player = Minecraft.getInstance().player;
        return player != null && player.getRecipeBook().isOpen(RecipeBookType.CRAFTING);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void drawPanel(ScreenEvent.Render.Background event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) {
            ArmorPanelLayout.shown = false;
            ArmorPanelLayout.hidden = false;
            return;
        }
        boolean hasCustom = false;
        for (Slot slot : screen.getMenu().slots) {
            if (isCustom(slot)) { hasCustom = true; break; }
        }
        if (!hasCustom) {
            ArmorPanelLayout.shown = false;
            ArmorPanelLayout.hidden = false;
            return;
        }

        boolean hide = screen instanceof InventoryScreen && recipeBookOpen();
        ArmorPanelLayout.hidden = hide;
        ArmorPanelLayout.shown = !hide;
        if (hide) return;

        GuiGraphics g = event.getGuiGraphics();
        int x0 = screen.getGuiLeft() + ArmorPanelLayout.PANEL_LEFT;
        int y0 = screen.getGuiTop() + ArmorPanelLayout.PANEL_TOP;
        int x1 = screen.getGuiLeft() + ArmorPanelLayout.PANEL_RIGHT;
        int y1 = screen.getGuiTop() + ArmorPanelLayout.PANEL_BOTTOM;

        g.fill(x0, y0, x1, y1, 0xFF000000);                     // outline
        g.fill(x0 + 1, y0 + 1, x1 - 1, y1 - 1, 0xFFC6C6C6);     // body
        g.fill(x1 - 3, y0 + 1, x1 - 1, y1 - 1, 0xFF555555);     // right shadow
        g.fill(x0 + 1, y1 - 3, x1 - 1, y1 - 1, 0xFF555555);     // bottom shadow
        g.fill(x0 + 1, y0 + 1, x1 - 1, y0 + 3, 0xFFFFFFFF);     // top highlight
        g.fill(x0 + 1, y0 + 1, x0 + 3, y1 - 1, 0xFFFFFFFF);     // left highlight

        var font = Minecraft.getInstance().font;
        g.drawString(font, TITLE, x0 + (x1 - x0 - font.width(TITLE)) / 2, y0 + 5, 0xFF404040, false);
    }

    private ArmorPanelClientEvents() {}
}
