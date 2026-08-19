package net.francis.got.worldgen;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Map;

public final class SubbiomeDebugCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gotsubdebug")
                        
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))

                        .executes(SubbiomeDebugCommand::executeReport)

                        .then(Commands.literal("scan")
                                .executes(ctx -> executeScan(ctx, 512))
                                .then(Commands.argument("radius", IntegerArgumentType.integer(32, 4096))
                                        .executes(ctx -> executeScan(
                                                ctx,
                                                IntegerArgumentType.getInteger(ctx, "radius")))))
        );
    }

    private static int executeReport(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();

        send(src, "§6════ GoT Subbiome Debug ════");

        boolean mapLoaded = BiomemapLoader.isLoaded();
        send(src, "§eBiomemap loaded: §f" + (mapLoaded ? "§aYES" : "§cNO — chunk gen returning fallback biome"));
        if (!mapLoaded) return 1;

        Map<String, List<SubbiomeDef>> subMap = SubbiomeResolver.getSubbiomeMap();
        if (subMap.isEmpty()) {
            send(src, "§eSubbiome map: §cEMPTY — subbiomes.json was not loaded or apply() was never called");
            send(src, "§7  Check: MapReloadListener registered? Server fully started before this command?");
            return 1;
        }
        send(src, "§eSubbiome map: §a" + subMap.size() + " parent(s) loaded");
        for (Map.Entry<String, List<SubbiomeDef>> e : subMap.entrySet()) {
            send(src, "§7  " + e.getKey() + " → " + e.getValue().size() + " subbiome(s):");
            for (SubbiomeDef def : e.getValue()) {
                send(src, "§7    • " + def.subbiomeId()
                        + "  scale=" + def.noiseScale()
                        + "  threshold=" + def.threshold()
                        + "  priority=" + def.priority());
            }
        }

        ServerPlayer player;
        try { player = src.getPlayerOrException(); }
        catch (Exception e) {
            send(src, "§cNot a player — position-specific checks skipped.");
            return 1;
        }

        int worldX = (int) player.getX();
        int worldZ = (int) player.getZ();
        send(src, "§ePosition: §f" + worldX + ", " + worldZ);

        ServerLevel level = (ServerLevel) player.level();
        BlockPos pos = player.blockPosition();
        Identifier actualBiome = level.registryAccess()
                .lookupOrThrow(Registries.BIOME)
                .getKey(level.getBiome(pos).value());
        send(src, "§eActual biome at pos: §f" + actualBiome);

        if (mapLoaded) {
            float cx = worldX / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
            float cz = worldZ / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
            int px = (int) Math.floor(cx);
            int pz = (int) Math.floor(cz);
            int rgb = BiomemapLoader.getRawPixel(px, pz);
            BiomeTerrainParams.Params params = BiomeTerrainParams.forColor(rgb);
            send(src, "§eBiomemap pixel (" + px + "," + pz + "): §f#"
                    + String.format("%06X", rgb) + " → biomeId=§a" + params.biomeId());

            String parentId = params.biomeId();
            List<SubbiomeDef> defs = subMap.get(parentId);
            if (defs == null) {
                send(src, "§eSubbiome check: §7parent §f" + parentId + " §7has NO subbiomes registered — nothing to place here");
                send(src, "§7  (Try walking into a §fgot:north§7 or §fgot:north_hills§7 area)");
            } else {
                send(src, "§eSubbiome noise samples for parent §f" + parentId + "§e:");
                for (SubbiomeDef def : defs) {
                    double sample = SubbiomeResolver.sampleNoise(def, worldX, worldZ);
                    boolean would = sample >= def.threshold();
                    send(src, "§7  " + def.subbiomeId()
                            + ":  noise=§f" + String.format("%.4f", sample)
                            + " §7threshold=§f" + def.threshold()
                            + (would ? " §a✔ WOULD PLACE" : " §c✘ below threshold"));
                }
            }
        }

        send(src, "§eRegistry check for subbiome biomes:");
        for (List<SubbiomeDef> defs : subMap.values()) {
            for (SubbiomeDef def : defs) {
                Identifier loc = Identifier.tryParse(def.subbiomeId());
                boolean inRegistry = loc != null && level.registryAccess()
                        .lookupOrThrow(Registries.BIOME)
                        .containsKey(loc);
                send(src, "§7  " + def.subbiomeId() + ": "
                        + (inRegistry ? "§aFOUND in registry" : "§cMISSING from registry — biome was never bootstrapped!"));
            }
        }

        send(src, "§eWorld seed: §f" + level.getSeed()
                + "  (if 0 something might be wrong with seed propagation)");

        return 1;
    }

    private static int executeScan(CommandContext<CommandSourceStack> ctx, int radius) {
        CommandSourceStack src = ctx.getSource();

        ServerPlayer player;
        try { player = src.getPlayerOrException(); }
        catch (Exception e) { src.sendFailure(Component.literal("Must be a player.")); return 0; }

        Map<String, List<SubbiomeDef>> subMap = SubbiomeResolver.getSubbiomeMap();
        if (subMap.isEmpty()) {
            src.sendFailure(Component.literal("§cSubbiome map is empty — nothing to scan for."));
            return 0;
        }

        int originX = (int) player.getX();
        int originZ = (int) player.getZ();

        send(src, "§6Scanning " + (radius * 2 + 1) + "² area for subbiome triggers (step=8 blocks)…");

        record Hit(String subbiomeId, int x, int z, double noise, double threshold) {}
        java.util.Map<String, Hit> nearest = new java.util.HashMap<>();

        outer:
        for (int dx = -radius; dx <= radius; dx += 8) {
            for (int dz = -radius; dz <= radius; dz += 8) {
                int wx = originX + dx;
                int wz = originZ + dz;

                if (!BiomemapLoader.isLoaded()) break outer;
                float cx = wx / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getWidth()  * 0.5f;
                float cz = wz / (float) BiomemapLoader.MAP_SCALE + BiomemapLoader.getHeight() * 0.5f;
                int px = (int) Math.floor(cx);
                int pz = (int) Math.floor(cz);
                int rgb = BiomemapLoader.getRawPixel(px, pz);
                BiomeTerrainParams.Params params = BiomeTerrainParams.forColor(rgb);
                String parentId = params.biomeId();

                List<SubbiomeDef> defs = subMap.get(parentId);
                if (defs == null) continue;

                for (SubbiomeDef def : defs) {
                    double sample = SubbiomeResolver.sampleNoise(def, wx, wz);
                    if (sample >= def.threshold()) {
                        int distSq = dx * dx + dz * dz;
                        Hit existing = nearest.get(def.subbiomeId());
                        if (existing == null) {
                            nearest.put(def.subbiomeId(), new Hit(def.subbiomeId(), wx, wz, sample, def.threshold()));
                        } else {
                            int existDist = (existing.x() - originX) * (existing.x() - originX)
                                    + (existing.z() - originZ) * (existing.z() - originZ);
                            if (distSq < existDist)
                                nearest.put(def.subbiomeId(), new Hit(def.subbiomeId(), wx, wz, sample, def.threshold()));
                        }
                    }
                }
            }
        }

        if (nearest.isEmpty()) {
            send(src, "§cNo subbiome trigger found in radius §f" + radius + "§c.");
            send(src, "§7  Either the parent biome doesn't appear in this area,");
            send(src, "§7  or the noise never exceeds the threshold here.");
            send(src, "§7  Try §f/gotsubdebug§7 to check noise values at your exact position.");
        } else {
            send(src, "§aNeareast trigger(s) found:");
            for (Hit h : nearest.values()) {
                int dist = (int) Math.sqrt((h.x() - originX) * (double)(h.x() - originX)
                        + (h.z() - originZ) * (double)(h.z() - originZ));
                send(src, "§7  §a" + h.subbiomeId()
                        + "§7  at (" + h.x() + ", " + h.z() + ")"
                        + "  dist=§f" + dist + "§7 blocks"
                        + "  noise=§f" + String.format("%.3f", h.noise())
                        + "§7/§f" + h.threshold());
                send(src, "§7    §e/tp @s " + h.x() + " ~ " + h.z());
            }
        }

        return 1;
    }

    private static void send(CommandSourceStack src, String text) {
        src.sendSuccess(() -> Component.literal(text), false);
    }

    private SubbiomeDebugCommand() {}
}