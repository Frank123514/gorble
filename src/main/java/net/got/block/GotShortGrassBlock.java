package net.got.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GotShortGrassBlock extends BushBlock {

    public static final MapCodec<GotShortGrassBlock> CODEC = MapCodec.unit(() -> null);

    public GotShortGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
