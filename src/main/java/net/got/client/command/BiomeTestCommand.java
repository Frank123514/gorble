package net.got.client.command;

import com.mojang.brigadier.CommandDispatcher;
import net.got.worldgen.BiomeMapLoader;
import net.got.worldgen.HeightmapLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

/**
 * Debug command to test biome map alignment
 * Usage: /biometest
 */
public class BiomeTestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("biometest")
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            BlockPos pos = player.blockPosition();

                            int x = pos.getX();
                            int z = pos.getZ();

                            // Get biome from map
                            ResourceKey<Biome> mappedBiome = BiomeMapLoader.getBiome(x, z);

                            // Get actual biome at position
                            var actualBiome = player.level().getBiome(pos);

                            // Get height info
                            float height = HeightmapLoader.getHeightAtWorld(x, z);
                            int terrainHeight = player.level().getHeight();

                            player.sendSystemMessage(
                                    Component.literal("=== Biome Test ===")
                            );
                            player.sendSystemMessage(
                                    Component.literal("Position: " + x + ", " + z)
                            );
                            player.sendSystemMessage(
                                    Component.literal("Mapped Biome: " +
                                            (mappedBiome != null ? mappedBiome.location() : "NULL"))
                            );
                            player.sendSystemMessage(
                                    Component.literal("Actual Biome: " +
                                            actualBiome.unwrapKey().map(k -> k.location().toString()).orElse("UNKNOWN"))
                            );
                            player.sendSystemMessage(
                                    Component.literal("Heightmap Value: " + height)
                            );
                            player.sendSystemMessage(
                                    Component.literal("Terrain Height: " + terrainHeight)
                            );
                            player.sendSystemMessage(
                                    Component.literal("Maps Loaded: Biome=" +
                                            (BiomeMapLoader.getAllBiomes().isEmpty() ? "NO" : "YES") +
                                            ", Height=" + (HeightmapLoader.isLoaded() ? "YES" : "NO"))
                            );

                            return 1;
                        })
        );
    }
}