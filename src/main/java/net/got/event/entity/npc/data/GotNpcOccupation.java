package net.got.event.entity.npc.data;

import net.minecraft.util.StringRepresentable;

/**
 * Every possible job a Smallfolk NPC can hold.
 * Jobs are only assigned to adult, non-military Smallfolk.
 * Some jobs are male-only (heavy labour trades).
 */
public enum GotNpcOccupation implements StringRepresentable {

    NONE      ("none",       "None",      false),
    SMITH     ("smith",      "Smith",     true),   // male only – heavy forge work
    FARMER    ("farmer",     "Farmer",    true),   // male only – field labour
    FARMHAND  ("farmhand",   "Farmhand",  false),
    BARKEEP   ("barkeep",    "Barkeep",   false),
    MINER     ("miner",      "Miner",     true),   // male only
    FORESTER  ("forester",   "Forester",  true),   // male only
    MASON     ("mason",      "Mason",     true),   // male only
    BREWER    ("brewer",     "Brewer",    false),
    FLORIST   ("florist",    "Florist",   false),
    BUTCHER   ("butcher",    "Butcher",   false),
    BAKER     ("baker",      "Baker",     false),
    FISHERMAN ("fisherman",  "Fisherman", false);

    public final String  id;
    public final String  label;
    /** True means this job cannot be assigned to a female NPC. */
    public final boolean maleOnly;

    GotNpcOccupation(String id, String label, boolean maleOnly) {
        this.id       = id;
        this.label    = label;
        this.maleOnly = maleOnly;
    }

    @Override public String getSerializedName() { return id; }

    public static GotNpcOccupation fromString(String s) {
        for (GotNpcOccupation o : values()) if (o.id.equals(s)) return o;
        return NONE;
    }

    public boolean isEmployed() { return this != NONE; }

    /** All hireable jobs (excludes NONE). */
    public static final GotNpcOccupation[] HIREABLE = {
        SMITH, FARMER, FARMHAND, BARKEEP, MINER,
        FORESTER, MASON, BREWER, FLORIST, BUTCHER, BAKER, FISHERMAN
    };

    /** Jobs available to female NPCs only. */
    public static final GotNpcOccupation[] HIREABLE_FEMALE = java.util.Arrays.stream(HIREABLE)
            .filter(o -> !o.maleOnly)
            .toArray(GotNpcOccupation[]::new);

    public static final String NBT_KEY = "Occupation";
}
