package net.got.registry;

import com.mojang.serialization.MapCodec;
import net.got.GotMod;
import net.got.worldgen.GotChunkGenerator;
import net.got.worldgen.GotBiomeSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers all GoT worldgen codecs so they can be referenced in JSON data.
 *
 * <ul>
 *   <li>{@code got:chunk_generator} — {@link GotChunkGenerator#CODEC}</li>
 *   <li>{@code got:biome_source}    — {@link GotBiomeSource#CODEC}</li>
 * </ul>
 */
public final class WorldgenRegistries {

    /* ------------------------------------------------------------------ */
    /* Chunk generators                                                     */
    /* ------------------------------------------------------------------ */

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, GotMod.MODID);

    /* ------------------------------------------------------------------ */
    /* Biome sources                                                        */
    /* ------------------------------------------------------------------ */

    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES =
            DeferredRegister.create(Registries.BIOME_SOURCE, GotMod.MODID);

    /* ------------------------------------------------------------------ */
    /* Registration                                                         */
    /* ------------------------------------------------------------------ */

    static {
        CHUNK_GENERATORS.register("chunk_generator", () -> GotChunkGenerator.CODEC);
        BIOME_SOURCES   .register("biome_source",    () -> GotBiomeSource.CODEC);

        System.out.println("[GoT] Worldgen registries initialised");
    }

    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
        BIOME_SOURCES   .register(bus);
        System.out.println("[GoT] Worldgen registries registered to event bus");
    }

    private WorldgenRegistries() {}
}