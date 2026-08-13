package net.got.block;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.material.PushReaction;

public class GotSaplingBlock extends SaplingBlock {

    public GotSaplingBlock(TreeGrower grower, Properties p) {
        super(grower, p
                .sound(SoundType.GRASS)
                .strength(0.0f)
                .noCollision()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .instabreak());
    }
}
