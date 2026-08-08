package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class TigerwoodLeavesBlock extends LeavesBlock {
	public static final MapCodec<TigerwoodLeavesBlock> CODEC = simpleCodec(TigerwoodLeavesBlock::new);

	@Override
	public MapCodec<TigerwoodLeavesBlock> codec() { return CODEC; }

	public TigerwoodLeavesBlock(Properties properties) {
		super(0.1f, properties.sound(SoundType.GRASS).strength(0.2f).noOcclusion().pushReaction(PushReaction.DESTROY).isRedstoneConductor((bs, br, bp) -> false).ignitedByLava().isSuffocating((bs, br, bp) -> false).isViewBlocking((bs, br, bp) -> false));
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 1;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return 30;
	}

	@Override
	protected void spawnFallingLeavesParticle(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos, net.minecraft.util.RandomSource random) {}
}
