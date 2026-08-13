package net.got.item;

import net.got.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public enum Coin {

    HALFPENNY ("halfpenny",  1,     null),
    PENNY     ("penny",      2,     HALFPENNY),
    HALFGROAT ("halfgroat",  4,     PENNY),
    GROAT     ("groat",      8,     HALFGROAT),
    STAR      ("star",       16,    GROAT),
    STAG      ("stag",       112,   STAR),
    MOON      ("moon",       784,   STAG),
    DRAGON    ("dragon",     23520, MOON);

    public final String id;

    public final int value;

    public final Coin smaller;

    Coin(String id, int value, Coin smaller) {
        this.id      = id;
        this.value   = value;
        this.smaller = smaller;
    }

    public int ratio() {
        if (smaller == null) return 1;
        return value / smaller.value;
    }

    public Item item() {
        return switch (this) {
            case HALFPENNY -> ModItems.COIN_HALFPENNY.get();
            case PENNY     -> ModItems.COIN_PENNY.get();
            case HALFGROAT -> ModItems.COIN_HALFGROAT.get();
            case GROAT     -> ModItems.COIN_GROAT.get();
            case STAR      -> ModItems.COIN_STAR.get();
            case STAG      -> ModItems.COIN_STAG.get();
            case MOON      -> ModItems.COIN_MOON.get();
            case DRAGON    -> ModItems.COIN_DRAGON.get();
        };
    }

    public ItemStack stack(int count) {
        return new ItemStack(item(), count);
    }

    public static long totalWealth(net.minecraft.world.entity.player.Inventory inv) {
        long total = 0;
        for (Coin coin : values()) {
            int count = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack s = inv.getItem(i);
                if (s.is(coin.item())) count += s.getCount();
            }
            total += (long) count * coin.value;
        }
        return total;
    }

    public int countIn(net.minecraft.world.entity.player.Inventory inv) {
        int n = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.is(item())) n += s.getCount();
        }
        return n;
    }

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

    public static Coin fromId(String id) {
        for (Coin c : values()) if (c.id.equals(id)) return c;
        return HALFPENNY;
    }
}
