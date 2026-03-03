package net.got.block;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.material.PushReaction;

/**
 * Generic sapling block for all GOT wood types.
 * Each instance receives its own {@link TreeGrower} so it grows
 * into the correct species-specific tree when bone-mealed or
 * ticked on a valid surface.
 */
public class GotSaplingBlock extends SaplingBlock {

    public GotSaplingBlock(TreeGrower grower, Properties p) {
        super(grower, p
                .sound(SoundType.GRASS)
                .strength(0.0f)
                .noCollission()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .instabreak());
    }
}
