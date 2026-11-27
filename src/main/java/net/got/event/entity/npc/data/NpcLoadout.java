package net.got.event.entity.npc.data;

import net.got.brigandine.BrigandineSlotData;
import net.got.coif.CoifSlotData;
import net.got.gambeson.GambesonSlotData;
import net.got.hauberk.HauberkSlotData;
import net.got.init.ModItems;
import net.got.plate.PlatePiece;
import net.got.plate.PlateSlotData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * What an NPC wears for its rank (and region). Weapons are handled by the entity itself.
 *
 * <ul>
 *   <li>Peasant: nothing.</li>
 *   <li>Levy (every region): gambeson + padded coif.</li>
 *   <li>Man-at-arms and skilled, NORTH ONLY for now: gambeson, hauberk, boiled leather jerkin, wool breeches,
 *       leather boots, and a head variation (bascinet / mail coif + bascinet / mail coif alone).
 *       North heavy cavalry also gets spaulders, vambraces and a gorget on top of that.</li>
 *   <li>Man-at-arms and skilled in any other region: placeholder (gambeson only) until that region's
 *       loadout is written.</li>
 *   <li>Free Folk (every clan): NO gambeson and no body armour at all, their fur and hides are in the skin.
 *       Only the odd conical cap / skull cap / half helm, or the odd pair of spaulders; both together is rare.</li>
 * </ul>
 */
public final class NpcLoadout {

    public static final String NORTH = "north";

    // ---- Free Folk gear odds (one roll per NPC; each slice is exclusive, the rest wear nothing) ----
    private static final float FREEFOLK_HELM_ONLY      = 0.17F;   // cap / skull cap / half helm
    private static final float FREEFOLK_SPAULDERS_ONLY = 0.12F;   // spaulders
    private static final float FREEFOLK_BOTH           = 0.03F;   // rare: helmet AND spaulders

    /** Every freefolk clan: the generic freefolk region plus the clans that use their own region id. */
    private static boolean isFreefolk(String region) {
        return "freefolk".equals(region) || "thenn".equals(region) || "skagos".equals(region);
    }

    public static void apply(LivingEntity e, NpcRank rank, String region, RandomSource rand) {
        if (isFreefolk(region)) {          // before the tier switch so they never get the gambeson placeholder
            freefolk(e, rand);
            return;
        }
        switch (rank.tier) {
            case CIVILIAN -> { }
            case LEVY -> {
                gambeson(e);
                e.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.PADDED_COIF.get()));
            }
            case MAN_AT_ARMS, SKILLED -> {
                if (NORTH.equals(region)) {
                    northFighter(e, rank, rand);
                } else {
                    gambeson(e);   // TODO: write this region's man-at-arms / skilled loadout
                }
            }
        }
    }

    private static void northFighter(LivingEntity e, NpcRank rank, RandomSource rand) {
        gambeson(e);
        HauberkSlotData.set(e, new ItemStack(ModItems.HAUBERK.get()));
        BrigandineSlotData.set(e, new ItemStack(ModItems.BOILED_LEATHER_JERKIN.get()));   // jerkin lives in the outer-layer slot
        e.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ModItems.WOOL_BREECHES.get()));
        e.setItemSlot(EquipmentSlot.FEET, new ItemStack(ModItems.LEATHER_BOOTS.get()));

        // Head variation: bascinet alone, mail coif under a bascinet, or just a mail coif.
        float r = rand.nextFloat();
        if (r < 0.40F) {
            e.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.BASCINET.get()));
        } else if (r < 0.75F) {
            CoifSlotData.set(e, new ItemStack(ModItems.MAIL_COIF.get()));
            e.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.BASCINET.get()));
        } else {
            CoifSlotData.set(e, new ItemStack(ModItems.MAIL_COIF.get()));
        }

        if (rank == NpcRank.HEAVY_CAVALRY) {
            PlateSlotData.set(e, PlatePiece.SPAULDERS, new ItemStack(ModItems.SPAULDERS.get()));
            PlateSlotData.set(e, PlatePiece.VAMBRACES, new ItemStack(ModItems.VAMBRACES.get()));
            PlateSlotData.set(e, PlatePiece.GORGET,    new ItemStack(ModItems.GORGET.get()));
        }
    }

    /** No gambeson, no body armour. Occasionally a light helmet and/or spaulders, both together only rarely. */
    private static void freefolk(LivingEntity e, RandomSource rand) {
        float r = rand.nextFloat();
        boolean helm      = r < FREEFOLK_HELM_ONLY
                         || (r >= FREEFOLK_HELM_ONLY + FREEFOLK_SPAULDERS_ONLY
                             && r < FREEFOLK_HELM_ONLY + FREEFOLK_SPAULDERS_ONLY + FREEFOLK_BOTH);
        boolean spaulders = (r >= FREEFOLK_HELM_ONLY && r < FREEFOLK_HELM_ONLY + FREEFOLK_SPAULDERS_ONLY + FREEFOLK_BOTH);

        if (helm) {
            float h = rand.nextFloat();
            var item = h < 0.40F ? ModItems.CONICAL_CAP.get()
                     : h < 0.75F ? ModItems.SKULL_CAP.get()
                     :             ModItems.HALFHELM.get();
            e.setItemSlot(EquipmentSlot.HEAD, new ItemStack(item));
        }
        if (spaulders) {
            PlateSlotData.set(e, PlatePiece.SPAULDERS, new ItemStack(ModItems.SPAULDERS.get()));
        }
    }

    private static void gambeson(LivingEntity e) {
        GambesonSlotData.set(e, new ItemStack(ModItems.GAMBESON.get()));
    }

    private NpcLoadout() {}
}
