package net.got.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

/**
 * Produce-type crop with 4 growth stages (age 0–3) instead of vanilla's 8 (0–7).
 *
 * <ul>
 *   <li>Planted with the produce item itself (no separate seed), like {@link GotProduceCropBlock}.</li>
 *   <li>4 growth ages (0-3), like {@link GotShortSeedCropBlock}.</li>
 * </ul>
 *
 * IMPORTANT: createBlockStateDefinition() MUST be overridden here (not just
 * getAgeProperty/getMaxAge) because CropBlock's constructor calls
 * registerDefaultState() using getAgeProperty() before any subclass field
 * initialisation runs. Overriding createBlockStateDefinition() ensures the
 * custom AGE property is added to the state container first, so
 * registerDefaultState() finds it without throwing.
 */
public class GotShortProduceCropBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    private final Supplier<Item> produceSupplier;

    public GotShortProduceCropBlock(Supplier<Item> produceSupplier, Properties properties) {
        super(properties);
        this.produceSupplier = produceSupplier;
    }

    /** Planting uses the produce item (bean plants a bean, cucumber plants a cucumber, etc.). */
    @Override
    protected Item getBaseSeedId() {
        return produceSupplier.get();
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
