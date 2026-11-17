package net.got.plate;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/** Read / write the plate slots of any living entity, keeping each piece's armor bonus in step. */
public final class PlateSlotData {

    private static Identifier armorId(PlatePiece p) {
        return Identifier.fromNamespaceAndPath(GotMod.MODID, "plate_" + p.id + "_armor");
    }

    private static Identifier toughnessId(PlatePiece p) {
        return Identifier.fromNamespaceAndPath(GotMod.MODID, "plate_" + p.id + "_toughness");
    }

    /** Never null; ItemStack.EMPTY when nothing is worn in that slot. */
    public static ItemStack get(LivingEntity entity, PlatePiece piece) {
        ItemStack stack = entity.getData(PlateAttachments.SLOTS.get(piece).get());
        return stack == null ? ItemStack.EMPTY : stack;
    }

    /** Sets the slot (this also re-syncs to clients) and refreshes that piece's armor bonus. */
    public static void set(LivingEntity entity, PlatePiece piece, ItemStack stack) {
        entity.setData(PlateAttachments.SLOTS.get(piece).get(), stack.isEmpty() ? ItemStack.EMPTY : stack);
        applyArmor(entity, piece, stack);
    }

    /** Armor points only exist server-side; clients just read the synced stack. */
    public static void applyArmor(LivingEntity entity, PlatePiece piece, ItemStack stack) {
        if (entity.level().isClientSide()) return;
        AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor == null) return;
        armor.removeModifier(armorId(piece));
        AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughness != null) toughness.removeModifier(toughnessId(piece));
        if (!stack.isEmpty() && stack.getItem() instanceof PlateItem worn && worn.getPiece() == piece) {
            armor.addTransientModifier(new AttributeModifier(
                    armorId(piece), piece.armor, AttributeModifier.Operation.ADD_VALUE));
            if (toughness != null && piece.toughness != 0.0) toughness.addTransientModifier(new AttributeModifier(
                    toughnessId(piece), piece.toughness, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private PlateSlotData() {}
}
