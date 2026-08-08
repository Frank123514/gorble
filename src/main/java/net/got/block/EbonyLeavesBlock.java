package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.material.PushReaction; import net.minecraft.world.level.block.state.*; import net.minecraft.world.level.block.*; import net.minecraft.world.level.BlockGetter; import net.minecraft.core.*;
public class EbonyLeavesBlock extends LeavesBlock {
	public static final MapCodec<EbonyLeavesBlock> CODEC = simpleCodec(EbonyLeavesBlock::new);

	@Override
	public MapCodec<EbonyLeavesBlock> codec() { return CODEC; }

    public EbonyLeavesBlock(Properties p) { super(0.1f, p.sound(SoundType.GRASS).strength(0.2f).noOcclusion().pushReaction(PushReaction.DESTROY).isRedstoneConductor((a,b,c)->false).ignitedByLava().isSuffocating((a,b,c)->false).isViewBlocking((a,b,c)->false)); }
    @Override public int getLightBlock(BlockState s) { return 1; }
    @Override public int getFlammability(BlockState s,BlockGetter w,BlockPos p,Direction f) { return 30; }

	@Override
	protected void spawnFallingLeavesParticle(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos, net.minecraft.util.RandomSource random) {}
}