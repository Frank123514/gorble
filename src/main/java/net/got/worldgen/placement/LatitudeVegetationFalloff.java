package net.got.worldgen.placement;

import com.mojang.serialization.MapCodec;
import net.got.climate.LatitudeClimate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * Thins out ground vegetation (grasses, flowers, wild crops) the further
 * north of {@link LatitudeClimate}'s snow line a candidate position sits.
 *
 * <p>Gated off the exact same line and fade distance as
 * {@link net.got.climate.LatitudeSnowHandler} ({@link
 * LatitudeClimate#snowLatitudeStrength}) — so vegetation dies out over the
 * same stretch of ground the snow line dusts in, reaching zero right where
 * the ground snow reaches full, solid coverage.
 *
 * <p>South of the line this is a complete no-op: {@code strength} is 0, so
 * every candidate position survives untouched, same as before this modifier
 * was added to a feature's placement list.
 *
 * <p>Add {@code {"type": "got:latitude_vegetation_falloff"}} as the last
 * entry in a placed feature's {@code placement} list — after the
 * {@code minecraft:biome} filter — so it only rolls for positions that would
 * have spawned anyway, rather than wasting rolls on ones biome/heightmap
 * filtering was going to reject regardless.
 */
public class LatitudeVegetationFalloff extends PlacementModifier {

    public static final LatitudeVegetationFalloff INSTANCE = new LatitudeVegetationFalloff();
    public static final MapCodec<LatitudeVegetationFalloff> CODEC = MapCodec.unit(INSTANCE);

    private LatitudeVegetationFalloff() {}

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        float strength = LatitudeClimate.snowLatitudeStrength(pos.getX(), pos.getZ());
        if (strength <= 0f) return Stream.of(pos);

        // strength 0..1: 0 right at the line (full survival chance), 1 once
        // fully faded (guaranteed rejection) — same linear ramp the snow and
        // ice adjustments use, just applied as a survival roll instead of a
        // temperature offset.
        if (random.nextFloat() < strength) return Stream.empty();
        return Stream.of(pos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return GotPlacementModifiers.LATITUDE_VEGETATION_FALLOFF.get();
    }
}
