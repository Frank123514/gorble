package net.got.plate;

import net.got.GotMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** An extra inventory slot that holds one plate piece. Added to the player's InventoryMenu by a mixin. */
public class PlateSlot extends Slot {

    /** 18x18 frame drawn behind the slot (the vanilla inventory texture has none at these spots). */
    public static final Identifier FRAME_SPRITE =
            Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/plate_frame");

    private final PlatePiece piece;

    public PlateSlot(LivingEntity owner, PlatePiece piece) {
        super(new PlateContainer(owner, piece), 0, net.got.armorpanel.ArmorPanelLayout.plateX(piece), net.got.armorpanel.ArmorPanelLayout.plateY(piece));
        this.piece = piece;
        this.setBackground(piece.emptyIcon());
    }

    public PlatePiece getPiece() { return piece; }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof PlateItem item && item.getPiece() == piece;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    /** Hidden together with the armor panel (e.g. while the recipe book is open). */
    @Override
    public boolean isActive() {
        return !net.got.armorpanel.ArmorPanelLayout.hidden;
    }
}
