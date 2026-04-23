package net.got.menu;

import net.got.entity.npc.data.GotNpcOccupation;
import net.got.entity.npc.data.GotNpcTrades;
import net.got.init.GotModMenus;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Container menu for the NPC trade screen.
 *
 * Slot layout (imageHeight = 220):
 *   0–26  player main inventory (y = 138, 156, 174)
 *   27–35 player hotbar          (y = 196)
 *   36    sell-input slot        (x = 8,  y = 88)
 */
public class NpcTradeMenu extends AbstractContainerMenu {

    // ── Public slot index ─────────────────────────────────────────────────────
    public static final int SELL_SLOT_INDEX = 36;

    // ── Sell slot position (relative to GUI origin) used by NpcTradeScreen ───
    public static final int SELL_SLOT_X = 8;
    public static final int SELL_SLOT_Y = 88;

    // ── Fields ────────────────────────────────────────────────────────────────
    private final SimpleContainer               sellInput  = new SimpleContainer(1);
    private final List<GotNpcTrades.BuyOffer>  buyOffers;
    private final List<GotNpcTrades.SellOffer> sellOffers;
    private final GotNpcOccupation              occupation;
    private final String                        npcName;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Client-side factory (MenuType). */
    public NpcTradeMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, GotNpcOccupation.NONE, "");
    }

    /** Server-side constructor. */
    public NpcTradeMenu(int id, Inventory playerInventory,
                        GotNpcOccupation occupation, String npcName) {
        super(GotModMenus.NPC_TRADE.get(), id);
        this.occupation = occupation;
        this.npcName    = npcName;
        this.buyOffers  = GotNpcTrades.getBuyOffers(occupation);
        this.sellOffers = GotNpcTrades.getSellOffers(occupation);

        // Player main inventory (rows 1–3)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18, 138 + row * 18));

        // Hotbar
        for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 196));

        // Sell-input slot (slot 36)
        addSlot(new Slot(sellInput, 0, SELL_SLOT_X, SELL_SLOT_Y));
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public List<GotNpcTrades.BuyOffer>  getBuyOffers()     { return buyOffers;  }
    public List<GotNpcTrades.SellOffer> getSellOffers()    { return sellOffers; }
    public GotNpcOccupation             getOccupation()    { return occupation; }
    public String                       getNpcName()       { return npcName;    }
    public SimpleContainer              getSellInputSlot() { return sellInput;  }

    // ── AbstractContainerMenu ─────────────────────────────────────────────────

    @Override public boolean stillValid(Player player) { return true; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        var stack = slot.getItem();
        var orig  = stack.copy();

        if (index == SELL_SLOT_INDEX) {
            if (!moveItemStackTo(stack, 0, 36, false)) return ItemStack.EMPTY;
        } else if (index < 27) {
            if (!moveItemStackTo(stack, 27, 36, false))
                if (!moveItemStackTo(stack, SELL_SLOT_INDEX, SELL_SLOT_INDEX + 1, false))
                    return ItemStack.EMPTY;
        } else if (index < 36) {
            if (!moveItemStackTo(stack, 0, 27, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        return orig;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            var leftover = sellInput.getItem(0);
            if (!leftover.isEmpty()) {
                player.getInventory().add(leftover);
                sellInput.setItem(0, ItemStack.EMPTY);
            }
        }
    }
}
