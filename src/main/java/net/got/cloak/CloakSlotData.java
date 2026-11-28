package net.got.cloak;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/** Read / write the cloak slot of any living entity. Cloaks are cosmetic, so there is no armor bonus to keep in step. */
public final class CloakSlotData {

    /** Never null; ItemStack.EMPTY when nothing is worn in the slot. */
    public static ItemStack get(LivingEntity entity) {
        ItemStack stack = entity.getData(CloakAttachments.CLOAK_SLOT.get());
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** Sets the slot (this also re-syncs to clients). */
    public static void set(LivingEntity entity, ItemStack stack) {
        entity.setData(CloakAttachments.CLOAK_SLOT.get(), stack.isEmpty() ? ItemStack.EMPTY : stack);
    }

    private CloakSlotData() {}
}
