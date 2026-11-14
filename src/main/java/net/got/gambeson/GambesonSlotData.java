package net.got.gambeson;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/** Read / write the gambeson slot of any living entity, keeping its armor bonus in step. */
public final class GambesonSlotData {

    private static final Identifier ARMOR_MODIFIER =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "gambeson_slot_armor");

    /** Never null; ItemStack.EMPTY when nothing is worn in the slot. */
    public static ItemStack get(LivingEntity entity) {
        ItemStack stack = entity.getData(GambesonAttachments.GAMBESON_SLOT.get());
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** Sets the slot (this also re-syncs to clients) and refreshes the armor bonus. */
    public static void set(LivingEntity entity, ItemStack stack) {
        entity.setData(GambesonAttachments.GAMBESON_SLOT.get(), stack.isEmpty() ? ItemStack.EMPTY : stack);
        applyArmor(entity, stack);
    }

    /** Gambeson armor points only exist server-side; clients just read the synced stack. */
    public static void applyArmor(LivingEntity entity, ItemStack stack) {
        if (entity.level().isClientSide()) return;
        AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor == null) return;
        armor.removeModifier(ARMOR_MODIFIER);
        if (!stack.isEmpty() && stack.getItem() instanceof GambesonItem) {
            armor.addTransientModifier(new AttributeModifier(
                    ARMOR_MODIFIER, GambesonItem.ARMOR_POINTS, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private GambesonSlotData() {}
}
