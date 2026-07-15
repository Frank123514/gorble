package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * Slab cut from {@link net.minecraft.world.level.block.Blocks#DIRT_PATH}.
 *
 * <p>Vanilla dirt path is one pixel shorter than a full block (its top sits at
 * y=15, not y=16) to sell the trodden-down look. A plain {@link SlabBlock}
 * shape would be a full 8/16 px tall and stick out above its own model by a
 * pixel, so this overrides the collision/selection shape to match: bottom half
 * runs 0-7, top half runs 8-15, and the double slab runs 0-15.
 *
 * <p>Ported from the "Grass Slabs" mod's {@code DirtPathSlabBlock}.
 */
public class DirtPathSlabBlock extends SlabBlock {
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    protected static final VoxelShape TOP_AABB    = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape DOUBLE_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public DirtPathSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                         @NotNull BlockPos pos, @NotNull CollisionContext context) {
        SlabType type = state.getValue(TYPE);
        return switch (type) {
            case DOUBLE -> DOUBLE_AABB;
            case TOP -> TOP_AABB;
            default -> BOTTOM_AABB;
        };
    }
}
