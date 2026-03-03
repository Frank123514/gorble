package net.got.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/** Generic wooden button for all GOT wood types. */
public class GotButtonBlock extends ButtonBlock {
    public GotButtonBlock(BlockSetType blockSetType, Properties p) {
        super(blockSetType, 30, p.sound(SoundType.WOOD).strength(0.5f).pushReaction(PushReaction.DESTROY));
    }
    @Override public int getLightBlock(BlockState s) { return 0; }
    @Override public int getFlammability(BlockState s, BlockGetter w, BlockPos p, Direction f) { return 5; }
}
