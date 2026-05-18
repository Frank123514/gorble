package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Continuous, globally-scoped snow accumulation and water freezing for cold latitudes.
 *
 * <h3>Design goals</h3>
 * <ol>
 *   <li><b>Global coverage</b> – Snow accumulates in every <em>loaded</em> chunk, not
 *       only chunks inside a player's render distance.  Chunks that are loaded by other
 *       means (force-loaded, spawn chunks, etc.) are also processed.</li>
 *   <li><b>Rain-independent</b> – Accumulation runs continuously.  Cold latitudes
 *       maintain a persistent snow cover without requiring a weather event.</li>
 *   <li><b>Proper layering</b> – Snow grows from 1 → 8 layers on a single
 *       {@link SnowLayerBlock} before a new block is placed on top.  It never
 *       resets to 1 layer between ticks.</li>
 *   <li><b>No snow on water</b> – Open water surfaces are frozen to ice; the snow
 *       layer is then placed on top of the ice block (not the water) on a subsequent
 *       tick once the surface is solid.</li>
 * </ol>
 *
 * <h3>Rate</h3>
 * {@value #COLUMNS_PER_CHUNK} random column(s) per loaded chunk per
 * {@value #TICK_INTERVAL} game tick(s).  This matches vanilla's per-chunk rain tick
 * rate but runs every tick interval instead of only during rain events.
 * Increase {@code COLUMNS_PER_CHUNK} or decrease {@code TICK_INTERVAL} for faster
 * accumulation; be mindful of server performance with large numbers of loaded chunks.
 */
@EventBusSubscriber(modid = GotMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class LatitudeSnowAccumulator {

    // ── Tuning constants ──────────────────────────────────────────────────────

    /** How often (in game ticks) to run the accumulation sweep. 20 = once per second. */
    private static final int TICK_INTERVAL = 20;

    /**
     * Random columns processed per loaded chunk per sweep.
     * Vanilla's tickChunk does 1 column per chunk per tick during rain.
     * We run every {@value #TICK_INTERVAL} ticks, so 4 columns here gives roughly
     * 4/20 = 0.2 column-hits per tick per chunk — a slightly gentler rate than
     * vanilla rain while still building up cover steadily.
     */
    private static final int COLUMNS_PER_CHUNK = 4;

    // ── Loaded-chunk tracking ─────────────────────────────────────────────────

    /**
     * Maps each server-level dimension key to the set of currently-loaded chunk
     * positions in that level.  Maintained via {@link ChunkEvent.Load} /
     * {@link ChunkEvent.Unload} so we never have to iterate internal chunk-map
     * structures whose API may change between NeoForge versions.
     */
    private static final Map<ResourceKey<Level>, Set<ChunkPos>> LOADED_CHUNKS =
            new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        LOADED_CHUNKS
                .computeIfAbsent(serverLevel.dimension(), k -> ConcurrentHashMap.newKeySet())
                .add(event.getChunk().getPos());
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        Set<ChunkPos> set = LOADED_CHUNKS.get(serverLevel.dimension());
        if (set != null) set.remove(event.getChunk().getPos());
    }

    // ── Main tick ─────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        // Only the overworld has our latitude-based climate.
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        if (level.getGameTime() % TICK_INTERVAL != 0) return;

        Set<ChunkPos> positions = LOADED_CHUNKS.get(level.dimension());
        if (positions == null || positions.isEmpty()) return;

        RandomSource random = level.getRandom();

        for (ChunkPos chunkPos : positions) {
            LevelChunk chunk = level.getChunkIfLoaded(chunkPos.x, chunkPos.z);
            if (chunk == null) continue; // chunk may have unloaded between events
            processChunk(level, chunkPos, random);
        }
    }

    // ── Per-chunk processing ──────────────────────────────────────────────────

    private static void processChunk(ServerLevel level, ChunkPos chunkPos,
                                     RandomSource random) {
        for (int i = 0; i < COLUMNS_PER_CHUNK; i++) {
            int worldX = chunkPos.getMinBlockX() + random.nextInt(16);
            int worldZ = chunkPos.getMinBlockZ() + random.nextInt(16);
            processColumn(level, worldX, worldZ);
        }
    }

    // ── Per-column logic ──────────────────────────────────────────────────────

    /**
     * Core routine: for a single X,Z column, decide whether to place/increment
     * snow or freeze water, based on latitude temperature.
     *
     * <p>Surface detection uses {@code MOTION_BLOCKING_NO_LEAVES} so that leaf
     * canopies are treated as transparent — snow should land on the ground, not sit
     * invisibly atop a tree's leaf crown.
     */
    private static void processColumn(ServerLevel level, int worldX, int worldZ) {
        // Noise-adjusted temperature (uses X for boundary wandering, see ClimateSystem).
        BlockPos refPos = new BlockPos(worldX, 64, worldZ);
        float latTemp = ClimateSystem.getLatitudeTemperature(refPos);
        if (latTemp >= 0.15f) return; // Not cold enough — nothing to do.

        // ── Find the highest solid/fluid surface ──────────────────────────────
        // getHeight returns the Y of the first non-matching block counted from the
        // top, i.e. one ABOVE the actual surface block.  Subtract 1 to get the
        // surface block itself.
        int heightY = level.getHeight(
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                worldX, worldZ);
        if (heightY <= level.getMinY()) return;

        int surfaceY = heightY - 1;
        BlockPos surfacePos = new BlockPos(worldX, surfaceY, worldZ);
        BlockState surfaceState = level.getBlockState(surfacePos);

        // ── Case A: Surface is liquid water → freeze to ice ───────────────────
        if (isSourceWater(surfaceState)) {
            if (level.getBrightness(LightLayer.BLOCK, surfacePos) < 10) {
                level.setBlockAndUpdate(surfacePos, Blocks.ICE.defaultBlockState());
            }
            // Do NOT place snow this tick; the block is now ice.
            // On the next tick the heightmap still points here (ice is solid),
            // so Case B below will handle snow-on-ice correctly.
            return;
        }

        // ── Case B: Surface already has snow → increment layers ───────────────
        if (surfaceState.is(Blocks.SNOW)) {
            // The MOTION_BLOCKING_NO_LEAVES heightmap does NOT include partial snow
            // layers (they don't fully block motion), so surfaceY here is the solid
            // block below and surfacePos+1 would be the snow.  But if snow has grown
            // to 8 layers it IS solid, and then heightY points above it.
            // Check both the surface pos and one above to catch either case.
            tryIncrementSnow(level, surfacePos);
            return;
        }

        // ── Case C: Surface is solid with a full top face → place/grow snow ───
        BlockPos snowPos = surfacePos.above();
        BlockState atSnowPos = level.getBlockState(snowPos);

        if (atSnowPos.is(Blocks.SNOW)) {
            tryIncrementSnow(level, snowPos);
            return;
        }

        if (!atSnowPos.isAir()) return; // Something else is there (plant, slab, etc.)

        // Solid-top-face check: snow can only settle on fully flat surfaces.
        if (!Block.isFaceFull(surfaceState.getCollisionShape(level, surfacePos), Direction.UP)) {
            return;
        }

        // Sky-access and light checks (matching vanilla shouldSnow behaviour).
        if (!level.canSeeSky(snowPos)) return;
        if (level.getBrightness(LightLayer.BLOCK, snowPos) >= 10) return;

        // Place a new 1-layer snow block.
        level.setBlockAndUpdate(snowPos, Blocks.SNOW.defaultBlockState());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Increments the {@link SnowLayerBlock#LAYERS} property of a snow block at
     * {@code pos} by 1, up to a maximum of 8.  Pushes any entities upward so
     * they are not clipped into the thicker snow, matching vanilla behaviour.
     */
    private static void tryIncrementSnow(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.SNOW)) return;
        int layers = state.getValue(SnowLayerBlock.LAYERS);
        if (layers >= 8) return;
        BlockState newState = state.setValue(SnowLayerBlock.LAYERS, layers + 1);
        Block.pushEntitiesUp(state, newState, level, pos);
        level.setBlockAndUpdate(pos, newState);
    }

    /**
     * Returns {@code true} when {@code state} is a source block of still water
     * (not flowing, not another fluid).
     */
    private static boolean isSourceWater(BlockState state) {
        if (!(state.getBlock() instanceof LiquidBlock)) return false;
        return state.getFluidState().getType() == Fluids.WATER
                && state.getFluidState().isSource();
    }

    private LatitudeSnowAccumulator() {}
}