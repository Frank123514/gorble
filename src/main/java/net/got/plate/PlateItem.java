package net.got.plate;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
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
 * One modular plate piece (gorget, spaulders, vambraces, breastplate or cuisses). Worn in its own dedicated
 * slot, outside the vanilla armor slots, so every piece can be combined with the others and with the
 * gambeson / hauberk / brigandine layers. Cannot be worn in a vanilla armor slot.
 */
public class PlateItem extends Item {

    private final PlatePiece piece;
    private final Holder<SoundEvent> equipSound;

    public PlateItem(Properties properties, PlatePiece piece) {
        this(properties, piece, SoundEvents.ARMOR_EQUIP_IRON);
    }

    public PlateItem(Properties properties, PlatePiece piece, Holder<SoundEvent> equipSound) {
        super(properties);
        this.piece = piece;
        this.equipSound = equipSound;
    }

    public PlatePiece getPiece() { return piece; }

    /** Keeps it out of the vanilla armor slots; the plate slots have their own rules. */
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return false;
    }

    /** Right-click equips into this piece's slot (swapping with whatever is there). */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack worn = PlateSlotData.get(player, piece);

        PlateSlotData.set(player, piece, held.copyWithCount(1));
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
