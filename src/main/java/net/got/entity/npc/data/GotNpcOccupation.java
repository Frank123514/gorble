package net.got.entity.npc.data;

import net.minecraft.util.StringRepresentable;

/**
 * Every possible job a Smallfolk NPC can hold.
 *
 * <p>Jobs are assigned by the player via the Hire screen (sneak + right-click
 * any unemployed NPC). There are no longer dedicated workstation blocks for
 * job assignment — the hire screen lists all available occupations directly.
 *
 * <p>To add a new job: add an entry here with a unique {@code id} and a
 * human-readable {@code label}, then add trade lists for it in
 * {@link GotNpcTrades}.
 */
public enum GotNpcOccupation implements StringRepresentable {

    NONE      ("none",       "None"),
    SMITH     ("smith",      "Smith"),
    FARMER    ("farmer",     "Farmer"),
    FARMHAND  ("farmhand",   "Farmhand"),
    BARKEEP   ("barkeep",    "Barkeep"),
    MINER     ("miner",      "Miner"),
    FORESTER  ("forester",   "Forester"),
    MASON     ("mason",      "Mason"),
    BREWER    ("brewer",     "Brewer"),
    FLORIST   ("florist",    "Florist"),
    BUTCHER   ("butcher",    "Butcher"),
    BAKER     ("baker",      "Baker"),
    FISHERMAN ("fisherman",  "Fisherman");

    /** Serialisation key — used in NBT and network packets. */
    public final String id;

    /**
     * Human-readable label shown in the hire screen and nameplate.
     * Matches the {@code got.occupation.<id>} lang key value for convenience.
     */
    public final String label;

    GotNpcOccupation(String id, String label) {
        this.id    = id;
        this.label = label;
    }

    @Override
    public String getSerializedName() {
        return id;
    }

    public static GotNpcOccupation fromString(String s) {
        for (GotNpcOccupation o : values()) {
            if (o.id.equals(s)) return o;
        }
        return NONE;
    }

    /** Whether this NPC currently has a job. */
    public boolean isEmployed() {
        return this != NONE;
    }

    /**
     * All occupations that can be assigned to a civilian NPC, in display order.
     * {@link #NONE} is excluded — it is the unassigned state, not a hireable job.
     */
    public static final GotNpcOccupation[] HIREABLE = {
        SMITH, FARMER, FARMHAND, BARKEEP, MINER,
        FORESTER, MASON, BREWER, FLORIST, BUTCHER, BAKER, FISHERMAN
    };

    /** NBT key used when saving/loading occupation. */
    public static final String NBT_KEY = "Occupation";
}
