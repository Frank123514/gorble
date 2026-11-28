package net.got.cloak;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** The extra inventory slot that holds a cape or fur cloak. Added to the player's InventoryMenu by a mixin. */
public class CloakSlot extends Slot {

    /** Faint cloak outline shown while the slot is empty. */
    public static final Identifier EMPTY_ICON =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/cloak");
    /** 18x18 frame drawn behind the slot (the vanilla inventory texture has none at this spot). */
    public static final Identifier FRAME_SPRITE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/cloak_frame");

    public CloakSlot(LivingEntity owner, int x, int y) {
        super(new CloakContainer(owner), 0, x, y);
        this.setBackground(EMPTY_ICON);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof CloakItem;
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
