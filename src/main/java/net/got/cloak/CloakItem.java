package net.got.cloak;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;

/**
 * A cape or fur cloak, worn in the dedicated cloak slot. It hangs over every other armor layer (gambeson,
 * hauberk, brigandine, plate), so it is purely a cosmetic outer garment. It cannot be worn in a vanilla armor slot.
 * Dyeable: both items are in the minecraft:dyeable tag, so dyes go on in the crafting grid and wash off in a cauldron.
 */
public class CloakItem extends Item {

    /**
     * Which model / texture a cloak item is drawn with (see CloakLayer), and the colour it shows undyed.
     * The default colours must match the "default" tint in assets/got/items/cape.json and fur_cloak.json.
     */
    public enum Kind {
        CAPE(0x4F5D75),
        FUR_CLOAK(0x9D7554);

        private final int defaultColor;

        Kind(int defaultColor) {
            this.defaultColor = defaultColor;
        }

        public int defaultColor() {
            return defaultColor;
        }
    }

    private final Kind kind;

    public CloakItem(Properties properties, Kind kind) {
        super(properties);
        this.kind = kind;
    }

    public Kind getKind() {
        return kind;
    }

    /** RGB (no alpha) the cloak is drawn with: its dye, or the default colour when it was never dyed. */
    public static int getColor(ItemStack stack, Kind kind) {
        DyedItemColor dyed = stack.get(DataComponents.DYED_COLOR);
        return dyed != null ? dyed.rgb() & 0xFFFFFF : kind.defaultColor();
    }

    /** Keeps it out of the vanilla armor slots; the cloak slot has its own rules. */
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return false;
    }

    /** Right-click equips into the cloak slot (swapping with whatever is there). */
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        ItemStack held = player.getItemInHand(hand);
        ItemStack worn = CloakSlotData.get(player);

        CloakSlotData.set(player, held.copyWithCount(1));
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
