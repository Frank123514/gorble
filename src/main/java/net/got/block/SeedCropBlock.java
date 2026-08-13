package net.got.block;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SeedCropBlock extends CropBlock {

    private final Supplier<Item> seedSupplier;

    public SeedCropBlock(Supplier<Item> seedSupplier, Properties properties) {
        super(properties);
        this.seedSupplier = seedSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }
}
