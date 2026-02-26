package net.got.registry;

import com.mojang.serialization.MapCodec;
import net.got.GotMod;
import net.got.worldgen.GotChunkGenerator;
import net.minecraft.core.registries.Registries;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class WorldgenRegistries {

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, GotMod.MODID);



    static {
        // Register chunk generator
        CHUNK_GENERATORS.register("chunk_generator", () -> GotChunkGenerator.CODEC);



        System.out.println("[GoT] Worldgen registries initialized");
    }

    public static void register(IEventBus bus) {
        CHUNK_GENERATORS.register(bus);
        System.out.println("[GoT] Worldgen registries registered to event bus");
    }

    private WorldgenRegistries() {}
}