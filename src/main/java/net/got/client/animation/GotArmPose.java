package net.got.client.animation;

/**
 * Identifies which combat animation set to play on the player's arm/item layer.
 *
 * <p>NONE       — vanilla behaviour, no override.
 * <p>SWORD      — one-handed sword swing (longsword, shortsword, copper/bronze/steel swords).
 * <p>GREATSWORD — two-handed overhead swing (bastard sword, greatsword).
 * <p>AXE        — chopping arc (short and long axes).
 * <p>SPEAR      — forward lunge / thrust.
 * <p>BLOCK      — raised-guard idle when player holds right-click with a sword/shield.
 */
public enum GotArmPose {
    NONE,
    SWORD,
    GREATSWORD,
    AXE,
    SPEAR,
    BLOCK
}
