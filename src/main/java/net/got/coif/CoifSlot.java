package net.got.coif;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** The extra inventory slot that holds the mail coif. Added to the player's InventoryMenu by a mixin. */
public class CoifSlot extends Slot {

    /** Faint coif outline shown while the slot is empty. */
    public static final Identifier EMPTY_ICON =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/coif");
    /** 18x18 frame drawn behind the slot (the vanilla inventory texture has none at this spot). */
    public static final Identifier FRAME_SPRITE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/coif_frame");

    public CoifSlot(LivingEntity owner, int x, int y) {
        super(new CoifContainer(owner), 0, x, y);
        this.setBackground(EMPTY_ICON);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof CoifItem;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
