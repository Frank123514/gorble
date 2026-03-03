package net.got.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/** Generic wooden pressure plate for all GOT wood types. */
public class GotPressurePlateBlock extends PressurePlateBlock {
    public GotPressurePlateBlock(BlockSetType blockSetType, Properties p) {
        super(blockSetType, p.sound(SoundType.WOOD).strength(0.5f).pushReaction(PushReaction.DESTROY).ignitedByLava().forceSolidOn());
    }
    @Override public int getLightBlock(BlockState s) { return 0; }
    @Override public int getFlammability(BlockState s, BlockGetter w, BlockPos p, Direction f) { return 5; }
}
