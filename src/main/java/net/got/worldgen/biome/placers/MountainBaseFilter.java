package net.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.worldgen.BiomemapLoader;
import net.got.worldgen.MountainSlopemapResolver;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * Placement filter that only lets a candidate position through if it sits
 * in the smooth, gently-climbing <em>foot</em> of a mountain biome — not
 * merely anywhere the local slope happens to be gentle (ridge shoulders,
 * saddles, and fold valleys can all be locally flat too, high up in the
 * range).
 *
 * <p>Reuses {@link MountainSlopemapResolver#rampWeight(int, int)}, the same
 * edge-distance field {@code GotChunkGenerator} uses to climb a mountain
 * pixel's height from {@link MountainSlopemapResolver#FOOT_HEIGHT} up to its
 * full configured peak. {@code rampWeight} is 0 right at the mountain's
 * border and rises to 1 once a pixel is {@link MountainSlopemapResolver#RAMP_PIXELS}
 * pixels deep into the blob — so capping it at a low threshold restricts a
 * feature to that first stretch of the climb (the actual foot of the
 * mountain, still near its edge) regardless of what the terrain is doing
 * further up the ridge or in some other locally-flat pocket.
 *
 * <p>If the biomemap/slopemap isn't loaded yet, this fails open (lets the
 * position through) rather than silently deleting the feature everywhere —
 * mirroring how {@code GotChunkGenerator} treats an unloaded map as "no
 * mountain shaping applies here".
 *
 * <h2>JSON example</h2>
 * <pre>{@code
 * {
 *   "type": "got:mountain_base_filter",
 *   "max_ramp_weight": 0.35
 * }
 * }</pre>
 */
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
        return net.got.registry.WorldgenRegistries.MOUNTAIN_BASE_FILTER.get();
    }
}
