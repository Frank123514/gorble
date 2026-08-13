package net.got.menu;

import net.got.event.entity.npc.data.NpcOccupation;
import net.got.event.entity.npc.data.NpcTrades;
import net.got.init.ModMenus;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class NpcTradeMenu extends AbstractContainerMenu {

    public static final int SELL_SLOT_INDEX = 36;

    public static final int SELL_SLOT_X = 8;
    public static final int SELL_SLOT_Y = 88;

    private final SimpleContainer               sellInput  = new SimpleContainer(1);
    private final List<NpcTrades.BuyOffer>  buyOffers;
    private final List<NpcTrades.SellOffer> sellOffers;
    private final NpcOccupation              occupation;
    private final String                        npcName;

    public NpcTradeMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, NpcOccupation.NONE, "");
    }

    public NpcTradeMenu(int id, Inventory playerInventory,
                        NpcOccupation occupation, String npcName) {
        super(ModMenus.NPC_TRADE.get(), id);
        this.occupation = occupation;
        this.npcName    = npcName;
        this.buyOffers  = NpcTrades.getBuyOffers(occupation);
        this.sellOffers = NpcTrades.getSellOffers(occupation);

        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18, 138 + row * 18));

        for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 196));

        addSlot(new Slot(sellInput, 0, SELL_SLOT_X, SELL_SLOT_Y));
    }

    public List<NpcTrades.BuyOffer>  getBuyOffers()     { return buyOffers;  }
    public List<NpcTrades.SellOffer> getSellOffers()    { return sellOffers; }
    public NpcOccupation             getOccupation()    { return occupation; }
    public String                       getNpcName()       { return npcName;    }
    public SimpleContainer              getSellInputSlot() { return sellInput;  }

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
        if (!player.level().isClientSide()) {
            var leftover = sellInput.getItem(0);
            if (!leftover.isEmpty()) {
                player.getInventory().add(leftover);
                sellInput.setItem(0, ItemStack.EMPTY);
            }
        }
    }
}
