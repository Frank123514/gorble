package net.got.event.entity.npc;

public enum NpcGender {
    MALE,
    FEMALE;

    public static final int COUNT = values().length;

    public static final String NBT_KEY = "Gender";

    public static NpcGender fromByte(byte b) {
        return (b >= 0 && b < values().length) ? values()[b] : MALE;
    }

    public byte toByte() {
        return (byte) this.ordinal();
    }
}