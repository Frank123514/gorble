package net.got.client.animation.player;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

/**
 * Picks which swing arc {@link GotAnimMath} should use for the currently
 * swinging arm, based on what's in that hand. This is what makes "punch"
 * and "custom attack swings" feel different from each other instead of both
 * reusing vanilla's single generic arm-swing curve.
 *
 * <p>Purely a classifier — add cases here as new weapon-ish items need their
 * own feel (e.g. a spear mod item could get its own {@code THRUST} style).
 */
public enum GotSwingStyle {

    /** Empty hand: quick, short jab. */
    PUNCH,
    /** Sword-type items: wide horizontal slash. */
    SWORD,
    /** Axe-type items: big overhead chop. */
    AXE,
    /** Trident: overhead thrust/throw motion, similar arc family to axe but sharper. */
    TRIDENT,
    /** Pickaxes, shovels, hoes and other {@link DiggerItem}s: straight downward/forward strike. */
    TOOL,
    /** Anything else being held (blocks, food, misc items): a restrained, tool-agnostic swing. */
    GENERIC;

    public static GotSwingStyle fromItem(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return PUNCH;
        }
        Item item = stack.getItem();
        if (item instanceof SwordItem) {
            return SWORD;
        }
        if (item instanceof TridentItem) {
            return TRIDENT;
        }
        if (item instanceof AxeItem) {
            return AXE;
        }
        if (item instanceof DiggerItem) {
            return TOOL;
        }
        return GENERIC;
    }
}
