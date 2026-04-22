package net.got.item;

import net.got.init.GotModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * All eight currency denominations, ordered smallest → largest.
 *
 * <p>Base unit is the halfpenny (value = 1).  Every other coin's
 * {@code value} is its worth in halfpennies.
 *
 * <pre>
 *  Halfpenny  =     1 hp     (copper)
 *  Penny      =     2 hp     (copper)
 *  Halfgroat  =     4 hp     (copper)
 *  Groat      =     8 hp     (copper)
 *  Star       =    16 hp     (copper)
 *  Stag       =   112 hp     (silver)
 *  Moon       =   784 hp     (silver)
 *  Dragon     = 23520 hp     (gold)
 * </pre>
 *
 * <p>Adjacent-denomination ratios:
 * <pre>
 *  Penny      = 2  Halfpennies
 *  Halfgroat  = 2  Pennies
 *  Groat      = 2  Halfgroats
 *  Star       = 2  Groats
 *  Stag       = 7  Stars
 *  Moon       = 7  Stags
 *  Dragon     = 30 Moons
 * </pre>
 */
public enum GotCoin {

    HALFPENNY ("halfpenny",  1,     null),
    PENNY     ("penny",      2,     HALFPENNY),
    HALFGROAT ("halfgroat",  4,     PENNY),
    GROAT     ("groat",      8,     HALFGROAT),
    STAR      ("star",       16,    GROAT),
    STAG      ("stag",       112,   STAR),
    MOON      ("moon",       784,   STAG),
    DRAGON    ("dragon",     23520, MOON);

    /** Serialisation id — matches item registry name. */
    public final String id;

    /** Worth in halfpennies. */
    public final int value;

    /** The next-smaller denomination, or {@code null} for {@link #HALFPENNY}. */
    public final GotCoin smaller;

    GotCoin(String id, int value, GotCoin smaller) {
        this.id      = id;
        this.value   = value;
        this.smaller = smaller;
    }

    /** How many of {@link #smaller} equal one of this coin. */
    public int ratio() {
        if (smaller == null) return 1;
        return value / smaller.value;
    }

    /** The item registered for this coin in {@link GotModItems}. */
    public Item item() {
        return switch (this) {
            case HALFPENNY -> GotModItems.COIN_HALFPENNY.get();
            case PENNY     -> GotModItems.COIN_PENNY.get();
            case HALFGROAT -> GotModItems.COIN_HALFGROAT.get();
            case GROAT     -> GotModItems.COIN_GROAT.get();
            case STAR      -> GotModItems.COIN_STAR.get();
            case STAG      -> GotModItems.COIN_STAG.get();
            case MOON      -> GotModItems.COIN_MOON.get();
            case DRAGON    -> GotModItems.COIN_DRAGON.get();
        };
    }

    public ItemStack stack(int count) {
        return new ItemStack(item(), count);
    }

    /** Total value of a player's entire inventory in halfpennies. */
    public static long totalWealth(net.minecraft.world.entity.player.Inventory inv) {
        long total = 0;
        for (GotCoin coin : values()) {
            int count = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack s = inv.getItem(i);
                if (s.is(coin.item())) count += s.getCount();
            }
            total += (long) count * coin.value;
        }
        return total;
    }

    /** Count how many of this coin the player has in their inventory. */
    public int countIn(net.minecraft.world.entity.player.Inventory inv) {
        int n = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.is(item())) n += s.getCount();
        }
        return n;
    }

    /** Remove {@code amount} of this coin from the inventory. Returns false if not enough. */
    public boolean removeFrom(net.minecraft.world.entity.player.Inventory inv, int amount) {
        if (countIn(inv) < amount) return false;
        int remaining = amount;
        for (int i = 0; i < inv.getContainerSize() && remaining > 0; i++) {
            ItemStack s = inv.getItem(i);
            if (s.is(item())) {
                int take = Math.min(remaining, s.getCount());
                s.shrink(take);
                remaining -= take;
            }
        }
        return true;
    }

    public static GotCoin fromId(String id) {
        for (GotCoin c : values()) if (c.id.equals(id)) return c;
        return HALFPENNY;
    }
}
