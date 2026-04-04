package net.got.entity.npc;

/**
 * Biological sex / presentation used by all GOT NPC entities.
 *
 * <p>Stored in NBT as a byte under the key {@code "Gender"}:
 * <ul>
 *   <li>0 → MALE</li>
 *   <li>1 → FEMALE</li>
 * </ul>
 *
 * <p>Gender is assigned once at spawn inside {@code finalizeSpawn()} and is
 * never changed afterwards.  It affects:
 * <ul>
 *   <li>Which skin texture(s) the renderer selects.</li>
 *   <li>The variant sub-range used for appearance variety within a gender.</li>
 *   <li>(Future) dialogue lines, name pools, equipment biases, etc.</li>
 * </ul>
 */
public enum NpcGender {
    MALE,
    FEMALE;

    /** Number of genders — used for random selection. */
    public static final int COUNT = values().length;

    /** NBT key used when saving/loading gender. */
    public static final String NBT_KEY = "Gender";

    /**
     * Converts the byte stored in NBT back to an {@link NpcGender}.
     * Falls back to {@link #MALE} for any unrecognised value so that
     * pre-gender saves don't break.
     */
    public static NpcGender fromByte(byte b) {
        return (b >= 0 && b < values().length) ? values()[b] : MALE;
    }

    /** Ordinal cast to byte, ready to write into a {@code CompoundTag}. */
    public byte toByte() {
        return (byte) this.ordinal();
    }
}