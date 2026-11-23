package net.got.brigandine;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** The extra inventory slot that holds the brigandine (outermost layer). Added to the player's InventoryMenu by a mixin. */
public class BrigandineSlot extends Slot {

    /** Faint chestplate-style outline shown while the slot is empty. */
    public static final Identifier EMPTY_ICON =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/brigandine");
    /** 18x18 frame drawn behind the slot (the vanilla inventory texture has none at this spot). */
    public static final Identifier FRAME_SPRITE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/brigandine_frame");

    public BrigandineSlot(LivingEntity owner, int x, int y) {
        super(new BrigandineContainer(owner), 0, x, y);
        this.setBackground(EMPTY_ICON);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof BrigandineItem;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    /** Hidden together with the armor panel (e.g. while the recipe book is open). */
    @Override
    public boolean isActive() {
        return !net.got.armorpanel.ArmorPanelLayout.hidden;
    }
}
