package net.got.plate;

import net.got.GotMod;
import net.minecraft.resources.Identifier;

/**
 * The five modular plate pieces. Each one lives in its own extra slot (a synced data attachment, like the
 * brigandine slot), so they can all be worn together and layer over the gambeson / chestplate / brigandine slots.
 * Slot positions are in inventory-menu coordinates (survival inventory screen).
 */
public enum PlatePiece {
    //            id             armor  tough  slotX slotY  deform (outermost layer; brigandine is 0.75)
    GORGET     ("gorget",       1.0,   0.0,   26,    8,    1.05F),
    SPAULDERS  ("spaulders",    2.0,   0.0,   26,   26,    1.05F),
    VAMBRACES  ("vambraces",    1.0,   0.0,   26,   44,    0.95F),
    BREASTPLATE("breastplate",  5.0,   0.5,   44,   62,    0.95F),
    CUISSES    ("cuisses",      3.0,   0.0,   62,   62,    0.95F);

    public final String id;
    public final double armor;
    public final double toughness;
    public final int slotX;
    public final int slotY;
    /** Inflate of the inner texture layer; the outer layer is this + 0.25. Pieces that share a body part differ slightly to avoid z-fighting. */
    public final float deform;

    PlatePiece(String id, double armor, double toughness, int slotX, int slotY, float deform) {
        this.id = id;
        this.armor = armor;
        this.toughness = toughness;
        this.slotX = slotX;
        this.slotY = slotY;
        this.deform = deform;
    }

    /** Faint outline shown in the slot while it is empty. */
    public Identifier emptyIcon() {
        return Identifier.fromNamespaceAndPath(GotMod.MODID, "container/slot/plate_" + id);
    }

    /** Armor texture: textures/entity/equipment/humanoid/<id>.png */
    public Identifier texture() {
        return Identifier.fromNamespaceAndPath(GotMod.MODID, "textures/entity/equipment/humanoid/" + id + ".png");
    }
}
