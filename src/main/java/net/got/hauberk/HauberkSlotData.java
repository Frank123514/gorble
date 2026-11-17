package net.got.hauberk;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/** Read / write the hauberk slot of any living entity, keeping its armor bonus in step. */
public final class HauberkSlotData {

    private static final Identifier ARMOR_MODIFIER =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "hauberk_slot_armor");
    private static final Identifier TOUGHNESS_MODIFIER =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "hauberk_slot_toughness");

    /** Never null; ItemStack.EMPTY when nothing is worn in the slot. */
    public static ItemStack get(LivingEntity entity) {
        ItemStack stack = entity.getData(HauberkAttachments.HAUBERK_SLOT.get());
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** Sets the slot (this also re-syncs to clients) and refreshes the armor bonus. */
    public static void set(LivingEntity entity, ItemStack stack) {
        entity.setData(HauberkAttachments.HAUBERK_SLOT.get(), stack.isEmpty() ? ItemStack.EMPTY : stack);
        applyArmor(entity, stack);
    }

    /** Hauberk armor points only exist server-side; clients just read the synced stack. */
    public static void applyArmor(LivingEntity entity, ItemStack stack) {
        if (entity.level().isClientSide()) return;
        AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor == null) return;
        armor.removeModifier(ARMOR_MODIFIER);
        AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughness != null) toughness.removeModifier(TOUGHNESS_MODIFIER);
        if (!stack.isEmpty() && stack.getItem() instanceof HauberkItem) {
            armor.addTransientModifier(new AttributeModifier(
                    ARMOR_MODIFIER, HauberkItem.ARMOR_POINTS, AttributeModifier.Operation.ADD_VALUE));
            if (toughness != null) toughness.addTransientModifier(new AttributeModifier(
                    TOUGHNESS_MODIFIER, HauberkItem.ARMOR_TOUGHNESS, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private HauberkSlotData() {}
}
