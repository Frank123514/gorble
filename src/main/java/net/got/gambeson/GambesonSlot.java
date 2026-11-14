package net.got.gambeson;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** The extra inventory slot that holds the gambeson. Added to the player's InventoryMenu by a mixin. */
public class GambesonSlot extends Slot {

    /** Faint chestplate-style outline shown while the slot is empty. */
    public static final Identifier EMPTY_ICON =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/gambeson");
    /** 18x18 frame drawn behind the slot (the vanilla inventory texture has none at this spot). */
    public static final Identifier FRAME_SPRITE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/gambeson_frame");

    public GambesonSlot(LivingEntity owner, int x, int y) {
        super(new GambesonContainer(owner), 0, x, y);
        this.setBackground(EMPTY_ICON);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof GambesonItem;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
