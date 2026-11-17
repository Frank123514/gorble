package net.got.hauberk;

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
 * Mail shirt worn in the dedicated hauberk slot (over the gambeson slot, under the brigandine slot and
 * under any plate armor in the vanilla armor slots). It can no longer be worn in the chestplate slot itself.
 */
public class HauberkItem extends Item {

    /** Armor points granted while worn in the hauberk slot (matches the old chest-slot value). */
    public static final double ARMOR_POINTS = 5.0;
    /** Armor toughness granted while worn in the hauberk slot (matches the old chest-slot value). */
    public static final double ARMOR_TOUGHNESS = 0.5;

    public HauberkItem(Properties properties) {
        super(properties);
    }

    /** Keeps it out of the chestplate slot; the hauberk slot has its own rules. */
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return false;
    }

    /** Right-click equips into the hauberk slot (swapping with whatever is there). */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack worn = HauberkSlotData.get(player);

        HauberkSlotData.set(player, held.copyWithCount(1));
        if (held.getCount() > 1) {
            held.shrink(1);
            if (!worn.isEmpty() && !player.getInventory().add(worn)) player.drop(worn, false);
        } else {
            player.setItemInHand(hand, worn.isEmpty() ? ItemStack.EMPTY : worn);
        }
        level.playSound(null, player, SoundEvents.ARMOR_EQUIP_CHAIN.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }
}
