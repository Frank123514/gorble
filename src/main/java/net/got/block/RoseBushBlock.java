package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.DoublePlantBlock;

public class RoseBushBlock extends DoublePlantBlock {

    public static final MapCodec<RoseBushBlock> CODEC =
            MapCodec.unit(() -> new RoseBushBlock(Properties.of()));

    @Override
    public MapCodec<? extends DoublePlantBlock> codec() { return CODEC; }

    public RoseBushBlock(Properties properties) {
        super(properties);
    }
}