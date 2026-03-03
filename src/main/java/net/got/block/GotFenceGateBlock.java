package net.got.block;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/** Generic fence gate for all GOT wood types. */
public class GotFenceGateBlock extends FenceGateBlock {
    public GotFenceGateBlock(WoodType woodType, Properties p) {
        super(woodType, p.sound(SoundType.WOOD).strength(2f, 3f).ignitedByLava().forceSolidOn());
    }
    @Override public int getLightBlock(BlockState s) { return 0; }
    @Override public int getFlammability(BlockState s, BlockGetter w, BlockPos p, Direction f) { return 5; }
}
