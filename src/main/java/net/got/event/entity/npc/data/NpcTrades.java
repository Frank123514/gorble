package net.got.event.entity.npc.data;

import net.got.item.Coin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collections;
import java.util.List;

public final class NpcTrades {

    private NpcTrades() {}

    public static List<BuyOffer> getBuyOffers(NpcOccupation occ) {
        return switch (occ) {
            case SMITH     -> SMITH_BUY;
            case FARMER    -> FARMER_BUY;
            case FARMHAND  -> FARMHAND_BUY;
            case BARKEEP   -> BARKEEP_BUY;
            case MINER     -> MINER_BUY;
            case FORESTER  -> FORESTER_BUY;
            case MASON     -> MASON_BUY;
            case BREWER    -> BREWER_BUY;
            case FLORIST   -> FLORIST_BUY;
            case BUTCHER   -> BUTCHER_BUY;
            case BAKER     -> BAKER_BUY;
            case FISHERMAN -> FISHERMAN_BUY;
            default        -> Collections.emptyList();
        };
    }

    public static List<SellOffer> getSellOffers(NpcOccupation occ) {
        return switch (occ) {
            case SMITH     -> SMITH_SELL;
            case FARMER    -> FARMER_SELL;
            case FARMHAND  -> FARMHAND_SELL;
            case BARKEEP   -> BARKEEP_SELL;
            case MINER     -> MINER_SELL;
            case FORESTER  -> FORESTER_SELL;
            case MASON     -> MASON_SELL;
            case BREWER    -> BREWER_SELL;
            case FLORIST   -> FLORIST_SELL;
            case BUTCHER   -> BUTCHER_SELL;
            case BAKER     -> BAKER_SELL;
            case FISHERMAN -> FISHERMAN_SELL;
            default        -> Collections.emptyList();
        };
    }

    private static final List<BuyOffer> SMITH_BUY = List.of(
        new BuyOffer(Coin.GROAT,  4, Items.IRON_INGOT,      2),
        new BuyOffer(Coin.STAG,   1, Items.IRON_SWORD,      1),
        new BuyOffer(Coin.STAG,   2, Items.IRON_CHESTPLATE, 1),
        new BuyOffer(Coin.MOON,   1, Items.DIAMOND_SWORD,   1)
    );
    private static final List<SellOffer> SMITH_SELL = List.of(
        new SellOffer(Items.IRON_INGOT,  3, Coin.GROAT,  2),
        new SellOffer(Items.GOLD_INGOT,  2, Coin.STAR,   3),
        new SellOffer(Items.COAL,       16, Coin.GROAT,  1)
    );

    private static final List<BuyOffer> FARMER_BUY = List.of(
        new BuyOffer(Coin.PENNY,  3, Items.WHEAT,  8),
        new BuyOffer(Coin.PENNY,  2, Items.CARROT, 6),
        new BuyOffer(Coin.PENNY,  2, Items.POTATO, 6),
        new BuyOffer(Coin.GROAT,  1, Items.BREAD,  4)
    );
    private static final List<SellOffer> FARMER_SELL = List.of(
        new SellOffer(Items.WHEAT,  20, Coin.PENNY, 3),
        new SellOffer(Items.CARROT, 12, Coin.PENNY, 2),
        new SellOffer(Items.POTATO, 12, Coin.PENNY, 2)
    );

    private static final List<BuyOffer> FARMHAND_BUY = List.of(
        new BuyOffer(Coin.PENNY, 2, Items.WHEAT_SEEDS, 8),
        new BuyOffer(Coin.PENNY, 1, Items.CARROT,      4),
        new BuyOffer(Coin.PENNY, 1, Items.BEETROOT,    6)
    );
    private static final List<SellOffer> FARMHAND_SELL = List.of(
        new SellOffer(Items.WHEAT,    12, Coin.PENNY, 2),
        new SellOffer(Items.BEETROOT, 16, Coin.PENNY, 1)
    );

    private static final List<BuyOffer> BARKEEP_BUY = List.of(
        new BuyOffer(Coin.GROAT, 1, Items.COOKED_BEEF,   2),
        new BuyOffer(Coin.PENNY, 3, Items.BREAD,          3),
        new BuyOffer(Coin.GROAT, 2, Items.HONEY_BOTTLE,   2),
        new BuyOffer(Coin.GROAT, 1, Items.MUSHROOM_STEW,  1)
    );
    private static final List<SellOffer> BARKEEP_SELL = List.of(
        new SellOffer(Items.WHEAT,      10, Coin.PENNY, 2),
        new SellOffer(Items.SUGAR_CANE,  8, Coin.PENNY, 1)
    );

    private static final List<BuyOffer> MINER_BUY = List.of(
        new BuyOffer(Coin.GROAT, 1, Items.COAL,        8),
        new BuyOffer(Coin.STAR,  2, Items.IRON_ORE,    2),
        new BuyOffer(Coin.STAG,  1, Items.GOLD_INGOT,  1),
        new BuyOffer(Coin.GROAT, 2, Items.COPPER_INGOT,4)
    );
    private static final List<SellOffer> MINER_SELL = List.of(
        new SellOffer(Items.COAL,         16, Coin.GROAT, 1),
        new SellOffer(Items.IRON_INGOT,    4, Coin.STAR,  1),
        new SellOffer(Items.COPPER_INGOT,  8, Coin.GROAT, 1)
    );

    private static final List<BuyOffer> FORESTER_BUY = List.of(
        new BuyOffer(Coin.PENNY, 2, Items.OAK_LOG,  8),
        new BuyOffer(Coin.GROAT, 1, Items.STICK,   16),
        new BuyOffer(Coin.GROAT, 1, Items.CHARCOAL, 4)
    );
    private static final List<SellOffer> FORESTER_SELL = List.of(
        new SellOffer(Items.OAK_LOG,   12, Coin.PENNY, 3),
        new SellOffer(Items.BIRCH_LOG, 12, Coin.PENNY, 3),
        new SellOffer(Items.STICK,     24, Coin.PENNY, 1)
    );

    private static final List<BuyOffer> MASON_BUY = List.of(
        new BuyOffer(Coin.GROAT, 1, Items.STONE_BRICKS,          8),
        new BuyOffer(Coin.PENNY, 3, Items.COBBLESTONE,           12),
        new BuyOffer(Coin.GROAT, 2, Items.CHISELED_STONE_BRICKS,  4),
        new BuyOffer(Coin.GROAT, 2, Items.POLISHED_ANDESITE,      8)
    );
    private static final List<SellOffer> MASON_SELL = List.of(
        new SellOffer(Items.STONE,      16, Coin.PENNY, 3),
        new SellOffer(Items.COBBLESTONE,20, Coin.PENNY, 2),
        new SellOffer(Items.GRAVEL,     16, Coin.PENNY, 1)
    );

    private static final List<BuyOffer> BREWER_BUY = List.of(
        new BuyOffer(Coin.GROAT, 2, Items.HONEY_BOTTLE,         2),
        new BuyOffer(Coin.PENNY, 3, Items.GLASS_BOTTLE,          4),
        new BuyOffer(Coin.STAR,  1, Items.FERMENTED_SPIDER_EYE,  1),
        new BuyOffer(Coin.STAR,  2, Items.BLAZE_POWDER,           2)
    );
    private static final List<SellOffer> BREWER_SELL = List.of(
        new SellOffer(Items.SUGAR,        8, Coin.PENNY, 2),
        new SellOffer(Items.NETHER_WART,  4, Coin.GROAT, 1),
        new SellOffer(Items.GLASS_BOTTLE, 8, Coin.PENNY, 2)
    );

    private static final List<BuyOffer> FLORIST_BUY = List.of(
        new BuyOffer(Coin.HALFGROAT, 3, Items.DANDELION,   4),
        new BuyOffer(Coin.HALFGROAT, 3, Items.POPPY,       4),
        new BuyOffer(Coin.PENNY,     3, Items.SUNFLOWER,   2),
        new BuyOffer(Coin.GROAT,     1, Items.OXEYE_DAISY, 4),
        new BuyOffer(Coin.PENNY,     2, Items.ALLIUM,       3)
    );
    private static final List<SellOffer> FLORIST_SELL = List.of(
        new SellOffer(Items.DANDELION, 8, Coin.HALFGROAT, 3),
        new SellOffer(Items.POPPY,     8, Coin.HALFGROAT, 3),
        new SellOffer(Items.SUNFLOWER, 4, Coin.PENNY,     2)
    );

    private static final List<BuyOffer> BUTCHER_BUY = List.of(
        new BuyOffer(Coin.GROAT, 1, Items.COOKED_BEEF,     4),
        new BuyOffer(Coin.GROAT, 1, Items.COOKED_PORKCHOP, 4),
        new BuyOffer(Coin.STAR,  1, Items.COOKED_CHICKEN,  4),
        new BuyOffer(Coin.STAR,  2, Items.RABBIT_STEW,      1)
    );
    private static final List<SellOffer> BUTCHER_SELL = List.of(
        new SellOffer(Items.BEEF,      10, Coin.GROAT, 1),
        new SellOffer(Items.PORKCHOP,  10, Coin.GROAT, 1),
        new SellOffer(Items.CHICKEN,   12, Coin.PENNY, 3),
        new SellOffer(Items.LEATHER,    8, Coin.PENNY, 2)
    );

    private static final List<BuyOffer> BAKER_BUY = List.of(
        new BuyOffer(Coin.PENNY, 2, Items.BREAD,       4),
        new BuyOffer(Coin.GROAT, 1, Items.COOKIE,      8),
        new BuyOffer(Coin.STAG,  1, Items.CAKE,        1),
        new BuyOffer(Coin.GROAT, 1, Items.PUMPKIN_PIE, 2)
    );
    private static final List<SellOffer> BAKER_SELL = List.of(
        new SellOffer(Items.WHEAT, 15, Coin.PENNY, 3),
        new SellOffer(Items.EGG,    8, Coin.PENNY, 1),
        new SellOffer(Items.SUGAR,  8, Coin.PENNY, 1)
    );

    private static final List<BuyOffer> FISHERMAN_BUY = List.of(
        new BuyOffer(Coin.GROAT, 1, Items.COOKED_COD,    4),
        new BuyOffer(Coin.GROAT, 1, Items.COOKED_SALMON, 3),
        new BuyOffer(Coin.STAG,  1, Items.FISHING_ROD,   1),
        new BuyOffer(Coin.GROAT, 2, Items.TROPICAL_FISH, 2)
    );
    private static final List<SellOffer> FISHERMAN_SELL = List.of(
        new SellOffer(Items.COD,    10, Coin.GROAT, 1),
        new SellOffer(Items.SALMON,  8, Coin.GROAT, 1),
        new SellOffer(Items.STRING,  8, Coin.PENNY, 2)
    );

    public record BuyOffer(Coin coinType, int coinCost, Item payItem, int payCount) {
        public ItemStack coinStack()   { return coinType.stack(coinCost); }
        public ItemStack payStack()    { return new ItemStack(payItem, payCount); }
    }

    public record SellOffer(Item costItem, int costCount, Coin coinType, int coinPay) {
        public ItemStack costStack()   { return new ItemStack(costItem, costCount); }
        public ItemStack coinStack()   { return coinType.stack(coinPay); }
    }
}
