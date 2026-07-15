package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * Stairs cut from {@link net.minecraft.world.level.block.Blocks#DIRT_PATH}.
 *
 * <p>Same trodden-down quirk as {@link DirtPathSlabBlock}: vanilla dirt path is
 * one pixel shorter than a full block, so its model (see
 * models/block/dirt_path_stairs*.json) shaves a pixel off the "up" face. For
 * {@code half=bottom} stairs that pixel comes off the very top (y=15 instead of
 * 16); for {@code half=top} (upside-down) stairs the whole shape is mirrored, so
 * the trodden face is on the bottom instead (y=1 instead of 0). This clips the
 * plain vanilla stairs shape by that same pixel on whichever side is "up" for
 * the current half, so the hitbox matches the model exactly.
 */
public class DirtPathStairsBlock extends StairBlock {
    // Generous horizontal bounds (only the vertical clip matters) so this cleanly
    // intersects every stair/inner/outer sub-shape without touching X/Z.
    private static final VoxelShape CLIP_BOTTOM = Block.box(-16.0D, 0.0D, -16.0D, 32.0D, 15.0D, 32.0D);
    private static final VoxelShape CLIP_TOP    = Block.box(-16.0D, 1.0D, -16.0D, 32.0D, 16.0D, 32.0D);

    public DirtPathStairsBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape shape = super.getShape(state, level, pos, context);
        VoxelShape clip = state.getValue(HALF) == Half.TOP ? CLIP_TOP : CLIP_BOTTOM;
        return Shapes.join(shape, clip, BooleanOp.AND);
    }
}