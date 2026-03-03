package net.got.block;
import net.minecraft.world.level.block.state.*; import net.minecraft.world.level.block.*; import net.minecraft.world.level.BlockGetter; import net.minecraft.core.*;
public class BlackCottonwoodStairsBlock extends StairBlock {
    public BlackCottonwoodStairsBlock(Properties p) { super(Blocks.AIR.defaultBlockState(),p.sound(SoundType.WOOD).strength(3f,2f).ignitedByLava()); }
    @Override public float getExplosionResistance() { return 2f; }
    @Override public int getLightBlock(BlockState s) { return 0; }
    @Override public int getFlammability(BlockState s,BlockGetter w,BlockPos p,Direction f) { return 5; }
}