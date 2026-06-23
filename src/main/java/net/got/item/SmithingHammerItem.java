package net.got.item;

import net.minecraft.world.item.Item;

/**
 * SmithingHammerItem — the only tool that can work a heated ingot on the
 * Smithing Anvil.
 * <p>
 * Holding this and left-clicking (attacking) a Smithing Anvil that has an
 * ingot in its input slot and a recipe selected will, after three solid
 * hits, craft the selected {@link net.got.recipe.SmithyRecipe}. All of the
 * actual hit-counting and crafting logic lives on
 * {@link net.got.block.SmithingAnvilBlock#attack} /
 * {@link net.got.block.SmithingAnvilBlockEntity#hitWithHammer} — this class
 * just exists so the block can identify "the player is holding the hammer".
 */
public class SmithingHammerItem extends Item {
    public SmithingHammerItem(Properties properties) {
        super(properties);
    }
}