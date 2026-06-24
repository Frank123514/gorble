package net.got.client.animation;

import net.got.init.GotModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Maps a held {@link ItemStack} to the correct {@link GotArmPose} for animation.
 *
 * <p>Extend the {@link #of(ItemStack)} switch as new weapon types are added
 * to the mod.
 */
public final class GotWeaponPoseClassifier {

    private GotWeaponPoseClassifier() {}

    /**
     * Returns the {@link GotArmPose} for the given stack, or
     * {@link GotArmPose#NONE} if the item does not need a custom animation.
     */
    public static GotArmPose of(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return GotArmPose.NONE;
        Item item = stack.getItem();

        // ── One-handed swords ─────────────────────────────────────────────────
        if (item == GotModItems.COPPER_SWORD.get()
                || item == GotModItems.BRONZE_SWORD.get()
                || item == GotModItems.STEEL_SWORD.get()
                || item == GotModItems.IRON_SHORTSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.IRON_LONGSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.IRON_FALCHION_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_SHORTSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_LONGSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_FALCHION_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_SHORTSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_LONGSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_FALCHION_CROSSGUARD_POMMEL.get()) {
            return GotArmPose.SWORD;
        }

        // ── Longswords / shortswords (smithy-crafted blades as finished items) ─
        // These are currently registered as plain Items (blade components).
        // When a finished longsword / shortsword item is added, slot it here.
        // For now we classify the blade items as one-handed swords too so
        // testers can see the animation in action.
        if (item == GotModItems.IRON_LONGSWORD_BLADE.get()
                || item == GotModItems.BRONZE_LONGSWORD_BLADE.get()
                || item == GotModItems.STEEL_LONGSWORD_BLADE.get()
                || item == GotModItems.IRON_SHORTSWORD_BLADE.get()
                || item == GotModItems.BRONZE_SHORTSWORD_BLADE.get()
                || item == GotModItems.STEEL_SHORTSWORD_BLADE.get()) {
            return GotArmPose.SWORD;
        }

        // ── Greatswords / bastard swords ──────────────────────────────────────
        if (item == GotModItems.IRON_GREATSWORD_BLADE.get()
                || item == GotModItems.BRONZE_GREATSWORD_BLADE.get()
                || item == GotModItems.STEEL_GREATSWORD_BLADE.get()
                || item == GotModItems.IRON_BASTARD_SWORD_BLADE.get()
                || item == GotModItems.BRONZE_BASTARD_SWORD_BLADE.get()
                || item == GotModItems.STEEL_BASTARD_SWORD_BLADE.get()
                // ── Assembled greatsword variants ─────────────────────────────
                || item == GotModItems.IRON_GREATSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_GREATSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_GREATSWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.IRON_BASTARD_SWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.IRON_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_BASTARD_SWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.BRONZE_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_BASTARD_SWORD_CROSSGUARD_POMMEL.get()
                || item == GotModItems.STEEL_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get()) {
            return GotArmPose.GREATSWORD;
        }

        // ── Axes ─────────────────────────────────────────────────────────────
        if (item == GotModItems.COPPER_AXE.get()
                || item == GotModItems.BRONZE_AXE.get()
                || item == GotModItems.STEEL_AXE.get()
                || item == GotModItems.IRON_SHORT_AXE_HEAD.get()
                || item == GotModItems.IRON_LONG_AXE_HEAD.get()
                || item == GotModItems.BRONZE_SHORT_AXE_HEAD.get()
                || item == GotModItems.BRONZE_LONG_AXE_HEAD.get()
                || item == GotModItems.STEEL_SHORT_AXE_HEAD.get()
                || item == GotModItems.STEEL_LONG_AXE_HEAD.get()) {
            return GotArmPose.AXE;
        }

        // ── Spears ────────────────────────────────────────────────────────────
        if (item == GotModItems.IRON_SPEAR_HEAD.get()
                || item == GotModItems.BRONZE_SPEAR_HEAD.get()
                || item == GotModItems.STEEL_SPEAR_HEAD.get()) {
            return GotArmPose.SPEAR;
        }

        return GotArmPose.NONE;
    }
}