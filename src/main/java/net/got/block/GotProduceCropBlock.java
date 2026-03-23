package net.got.block;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Root / head crop (carrot, parsnip, onion, turnip, peas, cabbage, garlic).
 *
 * <ul>
 *   <li>Planted with the produce item itself (no separate seed).</li>
 *   <li>7 growth ages (0-7), matching vanilla carrots/potatoes.</li>
 *   <li>Loot table controls drops: age < 7 → 1 produce, age 7 → 1-3 produce.</li>
 * </ul>
 *
 * The seed item IS the produce item — exactly like vanilla carrots.
 */
public class GotProduceCropBlock extends CropBlock {

    private final Supplier<Item> produceSupplier;

    public GotProduceCropBlock(Supplier<Item> produceSupplier, Properties properties) {
        super(properties);
        this.produceSupplier = produceSupplier;
    }

    /** Planting uses the produce item (carrot plants a carrot, etc.). */
    @Override
    protected Item getBaseSeedId() {
        return produceSupplier.get();
    }
}
