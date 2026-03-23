package net.got.block;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Grain / fibre crop (wheat, oat, rye, barley, beetroot, cotton).
 *
 * <ul>
 *   <li>Planted with the seed item.</li>
 *   <li>7 growth ages (0-7), matching vanilla wheat.</li>
 *   <li>Loot table controls drops: age < 7 → only seeds, age 7 → crop + seeds.</li>
 * </ul>
 *
 * Extends vanilla {@link CropBlock} which supplies all the growth,
 * bone-meal, age-state, and farmland logic automatically.
 */
public class GotSeedCropBlock extends CropBlock {

    private final Supplier<Item> seedSupplier;

    public GotSeedCropBlock(Supplier<Item> seedSupplier, Properties properties) {
        super(properties);
        this.seedSupplier = seedSupplier;
    }

    /** Returns the seed item used to plant this crop. */
    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }
}
