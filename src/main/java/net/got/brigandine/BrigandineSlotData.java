package net.got.brigandine;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/** Read / write the brigandine slot of any living entity, keeping its armor bonus in step. */
public final class BrigandineSlotData {

    private static final Identifier ARMOR_MODIFIER =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "brigandine_slot_armor");
    private static final Identifier TOUGHNESS_MODIFIER =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "brigandine_slot_toughness");

    /** Never null; ItemStack.EMPTY when nothing is worn in the slot. */
    public static ItemStack get(LivingEntity entity) {
        ItemStack stack = entity.getData(BrigandineAttachments.BRIGANDINE_SLOT.get());
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** Sets the slot (this also re-syncs to clients) and refreshes the armor bonus. */
    public static void set(LivingEntity entity, ItemStack stack) {
        entity.setData(BrigandineAttachments.BRIGANDINE_SLOT.get(), stack.isEmpty() ? ItemStack.EMPTY : stack);
        applyArmor(entity, stack);
    }

    /** Brigandine armor points only exist server-side; clients just read the synced stack. */
    public static void applyArmor(LivingEntity entity, ItemStack stack) {
        if (entity.level().isClientSide()) return;
        AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor == null) return;
        armor.removeModifier(ARMOR_MODIFIER);
        AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughness != null) toughness.removeModifier(TOUGHNESS_MODIFIER);
        if (!stack.isEmpty() && stack.getItem() instanceof BrigandineItem worn) {
            armor.addTransientModifier(new AttributeModifier(
                    ARMOR_MODIFIER, worn.getArmorPoints(), AttributeModifier.Operation.ADD_VALUE));
            if (toughness != null && worn.getArmorToughness() != 0.0) toughness.addTransientModifier(new AttributeModifier(
                    TOUGHNESS_MODIFIER, worn.getArmorToughness(), AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private BrigandineSlotData() {}
}
