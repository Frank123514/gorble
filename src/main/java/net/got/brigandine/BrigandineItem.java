package net.got.brigandine;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
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
 * Outer body armor worn in the dedicated "outer layer" slot (the brigandine slot): it goes over the
 * gambeson slot and over whatever is in the chestplate slot (hauberk, vanilla chestplate...). Used by
 * the brigandine and the boiled leather jerkin; each item carries its own stats and equip sound.
 * It can't be worn in the chestplate slot itself.
 */
public class BrigandineItem extends Item {

    private final double armorPoints;
    private final double armorToughness;
    private final Holder<SoundEvent> equipSound;

    public BrigandineItem(Properties properties, double armorPoints, double armorToughness,
                          Holder<SoundEvent> equipSound) {
        super(properties);
        this.armorPoints = armorPoints;
        this.armorToughness = armorToughness;
        this.equipSound = equipSound;
    }

    /** Armor points granted while worn in the slot (same as the old chest-slot value). */
    public double getArmorPoints() { return armorPoints; }

    /** Armor toughness granted while worn in the slot. */
    public double getArmorToughness() { return armorToughness; }

    /** Keeps it out of the chestplate slot; the brigandine slot has its own rules. */
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return false;
    }

    /** Right-click equips into the brigandine slot (swapping with whatever is there). */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack worn = BrigandineSlotData.get(player);

        BrigandineSlotData.set(player, held.copyWithCount(1));
        if (held.getCount() > 1) {
            held.shrink(1);
            if (!worn.isEmpty() && !player.getInventory().add(worn)) player.drop(worn, false);
        } else {
            player.setItemInHand(hand, worn.isEmpty() ? ItemStack.EMPTY : worn);
        }
        level.playSound(null, player, equipSound.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }
}
