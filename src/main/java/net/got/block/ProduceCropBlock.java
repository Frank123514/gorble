package net.got.block;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ProduceCropBlock extends CropBlock {

    private final Supplier<Item> produceSupplier;

    public ProduceCropBlock(Supplier<Item> produceSupplier, Properties properties) {
        super(properties);
        this.produceSupplier = produceSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return produceSupplier.get();
    }
}
