package net.got.gambeson;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Padded undergarment worn in the dedicated gambeson slot (under the chestplate slot), so it can be
 * layered beneath a hauberk or brigandine. It can no longer be worn in the chestplate slot itself.
 */
public class GambesonItem extends Item {

    /** Armor points granted while worn in the gambeson slot (matches the old chest-slot value). */
    public static final double ARMOR_POINTS = 3.0;

    public GambesonItem(Properties properties) {
        super(properties);
    }

    /** Keeps it out of the chestplate slot; the gambeson slot has its own rules. */
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return false;
    }

    /** Right-click equips into the gambeson slot (swapping with whatever is there). */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack worn = GambesonSlotData.get(player);

        GambesonSlotData.set(player, held.copyWithCount(1));
        if (held.getCount() > 1) {
            held.shrink(1);
            if (!worn.isEmpty() && !player.getInventory().add(worn)) player.drop(worn, false);
        } else {
            player.setItemInHand(hand, worn.isEmpty() ? ItemStack.EMPTY : worn);
        }
        level.playSound(null, player, SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }
}
