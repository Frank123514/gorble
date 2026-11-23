package net.got.plate;

import net.got.GotMod;
import net.minecraft.resources.Identifier;

/**
 * The five modular plate pieces. Each one lives in its own extra slot (a synced data attachment, like the
 * brigandine slot), so they can all be worn together and layer over the gambeson / chestplate / brigandine slots.
 * Slot positions live in {@link net.got.armorpanel.ArmorPanelLayout}.
 */
public enum PlatePiece {
    //            id             armor  tough  deform (inner layer; outer = +0.25, so outer lands on the brigandine's 0.75)
    GORGET     ("gorget",       1.0,   0.0,   0.80F),
    SPAULDERS  ("spaulders",    2.0,   0.0,   0.58F),
    VAMBRACES  ("vambraces",    1.0,   0.0,   0.52F),
    BREASTPLATE("breastplate",  5.0,   0.5,   0.52F),
    CUISSES    ("cuisses",      3.0,   0.0,   0.52F);

    public final String id;
    public final double armor;
    public final double toughness;
    /** Inflate of the inner texture layer; the outer layer is this + 0.25. Outer layer is ~brigandine size (0.75); the tiny +0.02 offsets keep plate from z-fighting with the brigandine and with each other (gorget over breastplate, spaulders over vambraces). */
    public final float deform;

    PlatePiece(String id, double armor, double toughness, float deform) {
        this.id = id;
        this.armor = armor;
        this.toughness = toughness;
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