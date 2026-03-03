package net.got.block;
import net.minecraft.world.level.material.PushReaction; import net.minecraft.world.level.block.state.*; import net.minecraft.world.level.block.*; import net.minecraft.world.level.BlockGetter; import net.minecraft.core.*;
public class CedarLeavesBlock extends LeavesBlock {
    public CedarLeavesBlock(Properties p) { super(p.sound(SoundType.GRASS).strength(0.2f).noOcclusion().pushReaction(PushReaction.DESTROY).isRedstoneConductor((a,b,c)->false).ignitedByLava().isSuffocating((a,b,c)->false).isViewBlocking((a,b,c)->false)); }
    @Override public int getLightBlock(BlockState s) { return 1; }
    @Override public int getFlammability(BlockState s,BlockGetter w,BlockPos p,Direction f) { return 30; }
}