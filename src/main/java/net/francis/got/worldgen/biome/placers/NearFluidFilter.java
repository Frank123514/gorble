package net.francis.got.worldgen.biome.placers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class NearFluidFilter extends PlacementModifier {

    public static final MapCodec<NearFluidFilter> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            TagKey.codec(Registries.FLUID)
                    .fieldOf("fluid_tag")
                    .forGetter(f -> f.fluidTag),
            Codec.intRange(1, 32)
                    .optionalFieldOf("radius", 3)
                    .forGetter(f -> f.radius),
            Codec.intRange(0, 8)
                    .optionalFieldOf("vertical_range", 1)
                    .forGetter(f -> f.verticalRange)
    ).apply(inst, NearFluidFilter::new));

    private final TagKey<Fluid> fluidTag;
    private final int radius;
    private final int verticalRange;

    public NearFluidFilter(TagKey<Fluid> fluidTag, int radius, int verticalRange) {
        this.fluidTag = fluidTag;
        this.radius = radius;
        this.verticalRange = verticalRange;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext ctx, RandomSource random, BlockPos pos) {
        int rSq = radius * radius;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > rSq) continue;
                for (int dy = -verticalRange; dy <= verticalRange; dy++) {
                    BlockPos check = pos.offset(dx, dy, dz);
                    FluidState fluid = ctx.getLevel().getFluidState(check);
                    if (fluid.is(fluidTag)) {
                        return Stream.of(pos);
                    }
                }
            }
        }
        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return net.francis.got.registry.WorldgenRegistries.NEAR_FLUID.get();
    }
}
