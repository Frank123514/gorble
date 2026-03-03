package net.got.block;
import net.minecraft.world.level.block.state.*; import net.minecraft.world.level.block.*; import net.minecraft.world.level.BlockGetter; import net.minecraft.core.*;
public class CinnamonFenceBlock extends FenceBlock {
    public CinnamonFenceBlock(Properties p) { super(p.sound(SoundType.WOOD).strength(2f,3f).ignitedByLava().forceSolidOn()); }
    @Override public int getLightBlock(BlockState s) { return 0; }
    @Override public int getFlammability(BlockState s,BlockGetter w,BlockPos p,Direction f) { return 5; }
}