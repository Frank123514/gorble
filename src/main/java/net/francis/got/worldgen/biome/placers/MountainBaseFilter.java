package net.francis.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.francis.got.worldgen.BiomemapLoader;
import net.francis.got.worldgen.MountainSlopemapResolver;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class MountainBaseFilter extends PlacementModifier {

    public static final MapCodec<MountainBaseFilter> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.floatRange(0f, 1f)
                    .optionalFieldOf("max_ramp_weight", 0.35f)
                    .forGetter(f -> f.maxRampWeight)
    ).apply(inst, MountainBaseFilter::new));

    private final float maxRampWeight;

    public MountainBaseFilter(float maxRampWeight) {
        this.maxRampWeight = maxRampWeight;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext ctx, RandomSource random, BlockPos pos) {
        if (!BiomemapLoader.isLoaded() || !MountainSlopemapResolver.isLoaded()) {
            return Stream.of(pos);
        }

        float cx = pos.getX() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getWidth()  * 0.5f;
        float cz = pos.getZ() / (float) BiomemapLoader.MAP_SCALE
                + BiomemapLoader.getHeight() * 0.5f;
        int px = Math.round(cx);
        int pz = Math.round(cz);

        float ramp = MountainSlopemapResolver.rampWeight(px, pz);
        return ramp <= maxRampWeight ? Stream.of(pos) : Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return net.francis.got.registry.WorldgenRegistries.MOUNTAIN_BASE_FILTER.get();
    }
}
