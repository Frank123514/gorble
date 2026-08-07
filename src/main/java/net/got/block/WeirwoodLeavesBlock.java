package net.got.block;

import net.got.init.GotModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class WeirwoodLeavesBlock extends LeavesBlock {

    public WeirwoodLeavesBlock(Properties properties) {
        super(0.1f, properties.sound(SoundType.GRASS).strength(0.2f).noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor((bs, br, bp) -> false)
                .ignitedByLava()
                .isSuffocating((bs, br, bp) -> false)
                .isViewBlocking((bs, br, bp) -> false));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        // ~1-in-15 chance per tick, matching cherry leaves frequency
        if (random.nextInt(15) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() - 0.05;
            double z = pos.getZ() + random.nextDouble();
            level.addParticle(GotModParticles.WEIRWOOD_LEAF.get(), x, y, z, 0, 0, 0);
        }
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