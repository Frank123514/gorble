package net.got.client.animation.player;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
    /**
     * Two-handed greatswords/claymores: {@link SwordItem}s classified by
     * registry name rather than a distinct Java class, since this mod's
     * greatswords are just {@code SwordItem}s registered with heavier
     * damage/speed stats — there's no {@code GreatswordItem} type to check
     * {@code instanceof} against, so {@link #fromItem} matches on the
     * item's own registry path instead. Bigger wind-up, slower overhead
     * arc than a one-handed {@link #SWORD}.
     */
    GREATSWORD,
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
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            String path = id.getPath();
            // Greatswords/claymores share SwordItem with regular swords,
            // distinguished only by name (and matching heavier stats) —
            // see the GREATSWORD enum doc.
            if (path.contains("greatsword") || path.contains("claymore")) {
                return GREATSWORD;
            }
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
