package net.got.client.animation;

/**
 * Identifies which combat animation set to play on the player's arm/item layer.
 *
 * <p>NONE         — vanilla behaviour, no override.
 * <p>SWORD        — one-handed sword slash (attack 1).
 * <p>SWORD_COMBO_2 — second hit in a sword combo chain (CPA sword_attack_2).
 * <p>GREATSWORD   — two-handed overhead swing.
 * <p>AXE          — chopping arc.
 * <p>SPEAR        — forward lunge / thrust.
 * <p>BLOCK        — raised-guard idle when player holds right-click with a sword/shield.
 */
public enum GotArmPose {
    NONE,
    SWORD,
    SWORD_COMBO_2,
    GREATSWORD,
    AXE,
    SPEAR,
    BLOCK
}
