package net.got.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

/**
 * Seed-type crop with 4 growth stages (age 0–3) instead of vanilla's 8 (0–7).
 *
 * IMPORTANT: createBlockStateDefinition() MUST be overridden here (not just
 * getAgeProperty/getMaxAge) because CropBlock's constructor calls
 * registerDefaultState() using getAgeProperty() before any subclass field
 * initialisation runs. Overriding createBlockStateDefinition() ensures the
 * custom AGE property is added to the state container first, so
 * registerDefaultState() finds it without throwing.
 */
public class GotShortSeedCropBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    private final Supplier<Item> seedSupplier;

    public GotShortSeedCropBlock(Supplier<Item> seedSupplier, Properties properties) {
        super(properties);
        this.seedSupplier = seedSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    /** Registers our 0-3 AGE property instead of the vanilla 0-7 one. */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(AGE);
    }
}
