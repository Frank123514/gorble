package net.got.event.entity.npc.data;

import net.minecraft.util.StringRepresentable;

public enum NpcOccupation implements StringRepresentable {

    NONE      ("none",       "None",      false),
    SMITH     ("smith",      "Smith",     true),
    FARMER    ("farmer",     "Farmer",    true),
    FARMHAND  ("farmhand",   "Farmhand",  false),
    BARKEEP   ("barkeep",    "Barkeep",   false),
    MINER     ("miner",      "Miner",     true),
    FORESTER  ("forester",   "Forester",  true),
    MASON     ("mason",      "Mason",     true),
    BREWER    ("brewer",     "Brewer",    false),
    FLORIST   ("florist",    "Florist",   false),
    BUTCHER   ("butcher",    "Butcher",   false),
    BAKER     ("baker",      "Baker",     false),
    FISHERMAN ("fisherman",  "Fisherman", false);

    public final String  id;
    public final String  label;
    
    public final boolean maleOnly;

    NpcOccupation(String id, String label, boolean maleOnly) {
        this.id       = id;
        this.label    = label;
        this.maleOnly = maleOnly;
    }

    @Override public String getSerializedName() { return id; }

    public static NpcOccupation fromString(String s) {
        for (NpcOccupation o : values()) if (o.id.equals(s)) return o;
        return NONE;
    }

    public boolean isEmployed() { return this != NONE; }

    public static final NpcOccupation[] HIREABLE = {
        SMITH, FARMER, FARMHAND, BARKEEP, MINER,
        FORESTER, MASON, BREWER, FLORIST, BUTCHER, BAKER, FISHERMAN
    };

    public static final NpcOccupation[] HIREABLE_FEMALE = java.util.Arrays.stream(HIREABLE)
            .filter(o -> !o.maleOnly)
            .toArray(NpcOccupation[]::new);

    public static final String NBT_KEY = "Occupation";
}
