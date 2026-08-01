package net.got.event.entity.npc.data;

import net.got.item.GotCoin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collections;
import java.util.List;

/**
 * Buy and sell offer lists for every NPC occupation.
 *
 * <p><b>Buy offers</b> — the NPC sells goods to the player.
 * The player pays {@code coinCost} coins of {@code coinType} and receives
 * {@code payCount} of {@code payItem}.
 *
 * <p><b>Sell offers</b> — the NPC buys goods from the player.
 * The player gives {@code costCount} of {@code costItem} and receives
 * {@code coinPay} coins of {@code coinType}.
 *
 * <p>Prices are in {@link GotCoin} denominations — edit the lists below
 * to change prices.  Groat = 8 halfpennies; Star = 16; Stag = 112, etc.
 */
public final class GotNpcTrades {

    private GotNpcTrades() {}

    // ── Public accessors ─────────────────────────────────────────────────────

    public static List<BuyOffer> getBuyOffers(GotNpcOccupation occ) {
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

    public static List<SellOffer> getSellOffers(GotNpcOccupation occ) {
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

    // ── Smith ────────────────────────────────────────────────────────────────

    private static final List<BuyOffer> SMITH_BUY = List.of(
        new BuyOffer(GotCoin.GROAT,  4, Items.IRON_INGOT,      2),
        new BuyOffer(GotCoin.STAG,   1, Items.IRON_SWORD,      1),
        new BuyOffer(GotCoin.STAG,   2, Items.IRON_CHESTPLATE, 1),
        new BuyOffer(GotCoin.MOON,   1, Items.DIAMOND_SWORD,   1)
    );
    private static final List<SellOffer> SMITH_SELL = List.of(
        new SellOffer(Items.IRON_INGOT,  3, GotCoin.GROAT,  2),
        new SellOffer(Items.GOLD_INGOT,  2, GotCoin.STAR,   3),
        new SellOffer(Items.COAL,       16, GotCoin.GROAT,  1)
    );

    // ── Farmer ───────────────────────────────────────────────────────────────

    private static final List<BuyOffer> FARMER_BUY = List.of(
        new BuyOffer(GotCoin.PENNY,  3, Items.WHEAT,  8),
        new BuyOffer(GotCoin.PENNY,  2, Items.CARROT, 6),
        new BuyOffer(GotCoin.PENNY,  2, Items.POTATO, 6),
        new BuyOffer(GotCoin.GROAT,  1, Items.BREAD,  4)
    );
    private static final List<SellOffer> FARMER_SELL = List.of(
        new SellOffer(Items.WHEAT,  20, GotCoin.PENNY, 3),
        new SellOffer(Items.CARROT, 12, GotCoin.PENNY, 2),
        new SellOffer(Items.POTATO, 12, GotCoin.PENNY, 2)
    );

    // ── Farmhand ─────────────────────────────────────────────────────────────

    private static final List<BuyOffer> FARMHAND_BUY = List.of(
        new BuyOffer(GotCoin.PENNY, 2, Items.WHEAT_SEEDS, 8),
        new BuyOffer(GotCoin.PENNY, 1, Items.CARROT,      4),
        new BuyOffer(GotCoin.PENNY, 1, Items.BEETROOT,    6)
    );
    private static final List<SellOffer> FARMHAND_SELL = List.of(
        new SellOffer(Items.WHEAT,    12, GotCoin.PENNY, 2),
        new SellOffer(Items.BEETROOT, 16, GotCoin.PENNY, 1)
    );

    // ── Barkeep ──────────────────────────────────────────────────────────────

    private static final List<BuyOffer> BARKEEP_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKED_BEEF,   2),
        new BuyOffer(GotCoin.PENNY, 3, Items.BREAD,          3),
        new BuyOffer(GotCoin.GROAT, 2, Items.HONEY_BOTTLE,   2),
        new BuyOffer(GotCoin.GROAT, 1, Items.MUSHROOM_STEW,  1)
    );
    private static final List<SellOffer> BARKEEP_SELL = List.of(
        new SellOffer(Items.WHEAT,      10, GotCoin.PENNY, 2),
        new SellOffer(Items.SUGAR_CANE,  8, GotCoin.PENNY, 1)
    );

    // ── Miner ────────────────────────────────────────────────────────────────

    private static final List<BuyOffer> MINER_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 1, Items.COAL,        8),
        new BuyOffer(GotCoin.STAR,  2, Items.IRON_ORE,    2),
        new BuyOffer(GotCoin.STAG,  1, Items.GOLD_INGOT,  1),
        new BuyOffer(GotCoin.GROAT, 2, Items.COPPER_INGOT,4)
    );
    private static final List<SellOffer> MINER_SELL = List.of(
        new SellOffer(Items.COAL,         16, GotCoin.GROAT, 1),
        new SellOffer(Items.IRON_INGOT,    4, GotCoin.STAR,  1),
        new SellOffer(Items.COPPER_INGOT,  8, GotCoin.GROAT, 1)
    );

    // ── Forester ─────────────────────────────────────────────────────────────

    private static final List<BuyOffer> FORESTER_BUY = List.of(
        new BuyOffer(GotCoin.PENNY, 2, Items.OAK_LOG,  8),
        new BuyOffer(GotCoin.GROAT, 1, Items.STICK,   16),
        new BuyOffer(GotCoin.GROAT, 1, Items.CHARCOAL, 4)
    );
    private static final List<SellOffer> FORESTER_SELL = List.of(
        new SellOffer(Items.OAK_LOG,   12, GotCoin.PENNY, 3),
        new SellOffer(Items.BIRCH_LOG, 12, GotCoin.PENNY, 3),
        new SellOffer(Items.STICK,     24, GotCoin.PENNY, 1)
    );

    // ── Mason ────────────────────────────────────────────────────────────────

    private static final List<BuyOffer> MASON_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 1, Items.STONE_BRICKS,          8),
        new BuyOffer(GotCoin.PENNY, 3, Items.COBBLESTONE,           12),
        new BuyOffer(GotCoin.GROAT, 2, Items.CHISELED_STONE_BRICKS,  4),
        new BuyOffer(GotCoin.GROAT, 2, Items.POLISHED_ANDESITE,      8)
    );
    private static final List<SellOffer> MASON_SELL = List.of(
        new SellOffer(Items.STONE,      16, GotCoin.PENNY, 3),
        new SellOffer(Items.COBBLESTONE,20, GotCoin.PENNY, 2),
        new SellOffer(Items.GRAVEL,     16, GotCoin.PENNY, 1)
    );

    // ── Brewer ───────────────────────────────────────────────────────────────

    private static final List<BuyOffer> BREWER_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 2, Items.HONEY_BOTTLE,         2),
        new BuyOffer(GotCoin.PENNY, 3, Items.GLASS_BOTTLE,          4),
        new BuyOffer(GotCoin.STAR,  1, Items.FERMENTED_SPIDER_EYE,  1),
        new BuyOffer(GotCoin.STAR,  2, Items.BLAZE_POWDER,           2)
    );
    private static final List<SellOffer> BREWER_SELL = List.of(
        new SellOffer(Items.SUGAR,        8, GotCoin.PENNY, 2),
        new SellOffer(Items.NETHER_WART,  4, GotCoin.GROAT, 1),
        new SellOffer(Items.GLASS_BOTTLE, 8, GotCoin.PENNY, 2)
    );

    // ── Florist ──────────────────────────────────────────────────────────────

    private static final List<BuyOffer> FLORIST_BUY = List.of(
        new BuyOffer(GotCoin.HALFGROAT, 3, Items.DANDELION,   4),
        new BuyOffer(GotCoin.HALFGROAT, 3, Items.POPPY,       4),
        new BuyOffer(GotCoin.PENNY,     3, Items.SUNFLOWER,   2),
        new BuyOffer(GotCoin.GROAT,     1, Items.OXEYE_DAISY, 4),
        new BuyOffer(GotCoin.PENNY,     2, Items.ALLIUM,       3)
    );
    private static final List<SellOffer> FLORIST_SELL = List.of(
        new SellOffer(Items.DANDELION, 8, GotCoin.HALFGROAT, 3),
        new SellOffer(Items.POPPY,     8, GotCoin.HALFGROAT, 3),
        new SellOffer(Items.SUNFLOWER, 4, GotCoin.PENNY,     2)
    );

    // ── Butcher ──────────────────────────────────────────────────────────────

    private static final List<BuyOffer> BUTCHER_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKED_BEEF,     4),
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKED_PORKCHOP, 4),
        new BuyOffer(GotCoin.STAR,  1, Items.COOKED_CHICKEN,  4),
        new BuyOffer(GotCoin.STAR,  2, Items.RABBIT_STEW,      1)
    );
    private static final List<SellOffer> BUTCHER_SELL = List.of(
        new SellOffer(Items.BEEF,      10, GotCoin.GROAT, 1),
        new SellOffer(Items.PORKCHOP,  10, GotCoin.GROAT, 1),
        new SellOffer(Items.CHICKEN,   12, GotCoin.PENNY, 3),
        new SellOffer(Items.LEATHER,    8, GotCoin.PENNY, 2)
    );

    // ── Baker ────────────────────────────────────────────────────────────────

    private static final List<BuyOffer> BAKER_BUY = List.of(
        new BuyOffer(GotCoin.PENNY, 2, Items.BREAD,       4),
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKIE,      8),
        new BuyOffer(GotCoin.STAG,  1, Items.CAKE,        1),
        new BuyOffer(GotCoin.GROAT, 1, Items.PUMPKIN_PIE, 2)
    );
    private static final List<SellOffer> BAKER_SELL = List.of(
        new SellOffer(Items.WHEAT, 15, GotCoin.PENNY, 3),
        new SellOffer(Items.EGG,    8, GotCoin.PENNY, 1),
        new SellOffer(Items.SUGAR,  8, GotCoin.PENNY, 1)
    );

    // ── Fisherman ────────────────────────────────────────────────────────────

    private static final List<BuyOffer> FISHERMAN_BUY = List.of(
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKED_COD,    4),
        new BuyOffer(GotCoin.GROAT, 1, Items.COOKED_SALMON, 3),
        new BuyOffer(GotCoin.STAG,  1, Items.FISHING_ROD,   1),
        new BuyOffer(GotCoin.GROAT, 2, Items.TROPICAL_FISH, 2)
    );
    private static final List<SellOffer> FISHERMAN_SELL = List.of(
        new SellOffer(Items.COD,    10, GotCoin.GROAT, 1),
        new SellOffer(Items.SALMON,  8, GotCoin.GROAT, 1),
        new SellOffer(Items.STRING,  8, GotCoin.PENNY, 2)
    );

    // ── Trade offer records ───────────────────────────────────────────────────

    /**
     * The NPC sells {@code payCount} × {@code payItem} to the player
     * for {@code coinCost} coins of {@code coinType}.
     */
    public record BuyOffer(GotCoin coinType, int coinCost, Item payItem, int payCount) {
        public ItemStack coinStack()   { return coinType.stack(coinCost); }
        public ItemStack payStack()    { return new ItemStack(payItem, payCount); }
    }

    /**
     * The NPC buys {@code costCount} × {@code costItem} from the player
     * and pays {@code coinPay} coins of {@code coinType}.
     */
    public record SellOffer(Item costItem, int costCount, GotCoin coinType, int coinPay) {
        public ItemStack costStack()   { return new ItemStack(costItem, costCount); }
        public ItemStack coinStack()   { return coinType.stack(coinPay); }
    }
}
