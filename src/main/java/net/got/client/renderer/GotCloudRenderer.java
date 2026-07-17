package net.got.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Random;

/**
 * Vanilla-style blocky/voxelated clouds, but driven by procedural noise instead of
 * reading pixels from a fixed 256x256 texture, stacked as multiple layers with soft
 * per-cell opacity for blending — no custom GLSL shaders, no persistent VertexBuffer
 * management, just plain vertex-color geometry rebuilt each frame (like vanilla's own
 * cloud renderer, just with a different coverage source).
 *
 * <h2>Why this instead of the flat textured plane</h2>
 * The earlier version of this class sampled our painted {@code clouds.png} on a flat
 * plane for a smooth, non-blocky look. This version intentionally goes the other way:
 * it keeps vanilla's chunky cube silhouette (which is what "feels like Minecraft"),
 * but fixes the two things that make the *vanilla* voxelizer look bad at scale —
 * it's driven by continuous noise instead of a single small fixed texture, so it never
 * runs out of resolution or repeats obviously, and each cell's opacity is a smooth
 * function of the noise value instead of a hard opaque/transparent cutoff, so cloud
 * edges fade instead of stopping dead.
 *
 * <h2>How it works</h2>
 * <ul>
 *   <li>{@link net.got.mixin.OptionsCloudMixin} forces {@code Options.getCloudsType()}
 *       to report {@code CloudStatus.OFF} while {@link #ENABLED} is true, so vanilla's
 *       cloud pass never runs.</li>
 *   <li>{@link #fractalNoise} is a small self-contained value-noise function (hash the
 *       lattice corners around a point, smoothstep-interpolate between them, sum a few
 *       octaves) — deliberately not using any of Minecraft's internal terrain-noise
 *       classes, since those are exactly the kind of thing that changes shape between
 *       versions.</li>
 *   <li>For each layer, we walk a grid of {@code cellSizeBlocks}-wide cells around the
 *       camera. Each cell samples the noise once to get a 0..1 "coverage" value; below
 *       {@code threshold} the cell is empty (skipped entirely), above it the cell
 *       becomes a real cube, with alpha ramping smoothly across {@code fuzziness} above
 *       the threshold — that smooth ramp is where the "blending/gradient" comes from.</li>
 *   <li>Each solid cell draws its top and bottom always, and only draws a side face if
 *       the neighboring cell in that direction is empty — same internal-face culling
 *       idea as vanilla's own cloud mesh (and chunk meshes in general), so touching
 *       cloud cells don't waste triangles on invisible internal walls.</li>
 *   <li>Three layers (low/mid/high) use independent noise seeds, cell sizes, heights,
 *       drift speeds and opacities for cheap parallax depth.</li>
 * </ul>
 *
 * <h2>Performance note</h2>
 * This rebuilds all visible cloud geometry every frame, same as vanilla. Cell counts
 * are kept modest via {@code cellSizeBlocks}/{@code radiusBlocks} on each layer — if
 * you push the radius way up or the cell size way down, you'll feel it. If that ever
 * becomes a problem, the next step would be caching each layer's mesh in a real
 * VertexBuffer and only rebuilding when the camera crosses a cell boundary, but that's
 * meaningfully more code and wasn't needed to get this working.
 */
public final class GotCloudRenderer {

    private GotCloudRenderer() {}

    /** Master on/off switch for the whole custom renderer. */
    public static volatile boolean ENABLED = true;

    /** A 4x4 opaque white texture — cell color comes entirely from vertex color, this just lets us reuse entityTranslucent. */
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/environment/cloud_cell.png");

    private static final float DRIFT_X_PER_TICK = 0.020F;
    private static final float DRIFT_Z_PER_TICK = 0.006F;

    private static final float GLOBAL_ALPHA = 1.0F;

    private static final float MIN_DAILY_OPACITY = 0.55F;
    private static final float MAX_DAILY_OPACITY = 1.0F;
    private static final float DAILY_OPACITY_STEP = 0.0015F;
    private static final long DAILY_OPACITY_SEED = 233591206262L;

    private static boolean fancyLayersEnabled = true;

    private record CloudLayer(float heightOffset, float thickness, float cellSizeBlocks,
                              float radiusBlocks, float fadeDistance, float noiseScale,
                              int seed, int octaves, float threshold, float fuzziness,
                              float speedMultiplier, float alphaMultiplier, boolean fancyOnly) {
    }

    private static final CloudLayer[] LAYERS = {
            // Low layer: the main cloud deck — vanilla-scale cells, most detail.
            new CloudLayer(0.0F, 5.0F, 24.0F, 768.0F, 192.0F, 400.0F,
                    1337, 4, 0.36F, 0.32F, 1.00F, 0.95F, false),
            // Mid layer: bigger, sparser cells, drifts slower.
            new CloudLayer(60.0F, 6.0F, 40.0F, 640.0F, 160.0F, 650.0F,
                    9001, 4, 0.40F, 0.34F, 0.55F, 0.55F, true),
            // High layer: huge sparse cells far above, drifts slowest, visible furthest out.
            new CloudLayer(480.0F, 8.0F, 64.0F, 1536.0F, 384.0F, 1100.0F,
                    424242, 3, 0.44F, 0.36F, 0.28F, 0.40F, true),
    };

    private static float driftX = 0.0F;
    private static float driftZ = 0.0F;

    private static long lastOpacityDay = Long.MIN_VALUE;
    private static float dailyOpacityTarget = 1.0F;
    private static float dailyOpacityCurrent = 1.0F;
    private static final Random OPACITY_RNG = new Random();

    public static void init() {
        NeoForge.EVENT_BUS.addListener(GotCloudRenderer::onClientTick);
        NeoForge.EVENT_BUS.addListener(GotCloudRenderer::onRenderLevelStage);
    }

    /** Call this if you want to respect the vanilla Fancy/Fast graphics setting for the extra layers. */
    public static void setFancyLayersEnabled(boolean enabled) {
        fancyLayersEnabled = enabled;
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (mc.isPaused() || level == null) return;

        driftX += DRIFT_X_PER_TICK;
        driftZ += DRIFT_Z_PER_TICK;

        updateDailyOpacity(level);
    }

    private static void updateDailyOpacity(ClientLevel level) {
        long day = level.getDayTime() / 24000L;
        if (day != lastOpacityDay) {
            lastOpacityDay = day;
            OPACITY_RNG.setSeed(day * DAILY_OPACITY_SEED + day + 83025820626792L);
            dailyOpacityTarget = MIN_DAILY_OPACITY
                    + OPACITY_RNG.nextFloat() * (MAX_DAILY_OPACITY - MIN_DAILY_OPACITY);
        }

        if (dailyOpacityCurrent < dailyOpacityTarget) {
            dailyOpacityCurrent = Math.min(dailyOpacityCurrent + DAILY_OPACITY_STEP, dailyOpacityTarget);
        } else if (dailyOpacityCurrent > dailyOpacityTarget) {
            dailyOpacityCurrent = Math.max(dailyOpacityCurrent - DAILY_OPACITY_STEP, dailyOpacityTarget);
        }
    }

    /** RGB multipliers blended toward at peak "pinkishness" — boosts red/blue, cuts green. */
    private static final double[] SUNSET_TINT_MULTIPLIER = {1.07, 0.85, 1.07};

    private static Vec3 applySunsetTint(Vec3 clouds, float sunAngleRadians) {
        double[] rgb = {clouds.x, clouds.y, clouds.z};

        float dayBright = Mth.cos(sunAngleRadians) * 2.0F + 0.5F;
        dayBright = Mth.clamp(dayBright, 0.0F, 1.0F);

        if (dayBright >= 0.2F && dayBright <= 0.98F) {
            float pinkishness;
            if (dayBright >= 0.5F && dayBright <= 0.8F) {
                pinkishness = 1.0F;
            } else if (dayBright < 0.5F) {
                pinkishness = (dayBright - 0.2F) / 0.3F;
            } else {
                pinkishness = (0.98F - dayBright) / 0.18F;
            }

            for (int i = 0; i < 3; i++) {
                double mult = Mth.lerp(pinkishness, 1.0, SUNSET_TINT_MULTIPLIER[i]);
                rgb[i] = Mth.clamp(rgb[i] * mult, 0.0, 1.0);
            }
        }

        return new Vec3(rgb[0], rgb[1], rgb[2]);
    }

    private static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (!ENABLED) return;
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) return;

        if (level.dimension() != Level.OVERWORLD) return;

        PoseStack poseStack = event.getPoseStack();
        if (poseStack == null) return;

        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();

        Vec3 cloudColor = Vec3.fromRGB24(level.getCloudColor(1.0F));
        cloudColor = applySunsetTint(cloudColor, level.getSunAngle(1.0F));
        int r = (int) (cloudColor.x * 255.0);
        int g = (int) (cloudColor.y * 255.0);
        int b = (int) (cloudColor.z * 255.0);

        RenderType renderType = RenderType.entityTranslucent(TEXTURE);
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        VertexConsumer consumer = bufferSource.getBuffer(renderType);

        RenderSystem.disableCull();

        for (CloudLayer layer : LAYERS) {
            if (layer.fancyOnly() && !fancyLayersEnabled) continue;
            renderLayer(consumer, poseStack, camPos, layer, r, g, b);
        }

        RenderSystem.enableCull();
        bufferSource.endBatch(renderType);
    }

    private static void renderLayer(VertexConsumer consumer, PoseStack poseStack, Vec3 camPos,
                                    CloudLayer layer, int r, int g, int b) {
        float layerDriftX = driftX * layer.speedMultiplier();
        float layerDriftZ = driftZ * layer.speedMultiplier();
        float layerAlphaScale = GLOBAL_ALPHA * layer.alphaMultiplier() * dailyOpacityCurrent;
        if (layerAlphaScale <= 0.0F) return;

        float cell = layer.cellSizeBlocks();
        float radius = layer.radiusBlocks();

        int minCx = (int) Math.floor((camPos.x - radius - layerDriftX) / cell);
        int maxCx = (int) Math.ceil((camPos.x + radius - layerDriftX) / cell);
        int minCz = (int) Math.floor((camPos.z - radius - layerDriftZ) / cell);
        int maxCz = (int) Math.ceil((camPos.z + radius - layerDriftZ) / cell);

        float y0 = (layer.heightOffset()) - (float) camPos.y;
        y0 += 192.0F; // base cloud height, matches vanilla's default
        float y1 = y0 + layer.thickness();

        for (int cx = minCx; cx <= maxCx; cx++) {
            for (int cz = minCz; cz <= maxCz; cz++) {
                float worldCenterX = (cx + 0.5F) * cell + layerDriftX;
                float worldCenterZ = (cz + 0.5F) * cell + layerDriftZ;

                float relCenterX = worldCenterX - (float) camPos.x;
                float relCenterZ = worldCenterZ - (float) camPos.z;
                float distFromCam = (float) Math.sqrt(relCenterX * relCenterX + relCenterZ * relCenterZ);
                if (distFromCam > radius) continue;

                float coverage = coverage(worldCenterX, worldCenterZ, layer);
                if (coverage < layer.threshold()) continue;

                float alpha = smoothstep((coverage - layer.threshold()) / Math.max(layer.fuzziness(), 1e-4F));
                alpha *= layerAlphaScale * fadeMultiplier(distFromCam, radius, layer.fadeDistance());
                int a = (int) (255 * Mth.clamp(alpha, 0.0F, 1.0F));
                if (a <= 0) continue;

                boolean solidNorth = coverage(worldCenterX, worldCenterZ - cell, layer) >= layer.threshold();
                boolean solidSouth = coverage(worldCenterX, worldCenterZ + cell, layer) >= layer.threshold();
                boolean solidWest = coverage(worldCenterX - cell, worldCenterZ, layer) >= layer.threshold();
                boolean solidEast = coverage(worldCenterX + cell, worldCenterZ, layer) >= layer.threshold();

                float x0 = relCenterX - cell * 0.5F;
                float x1 = relCenterX + cell * 0.5F;
                float z0 = relCenterZ - cell * 0.5F;
                float z1 = relCenterZ + cell * 0.5F;

                putCube(consumer, poseStack, x0, y0, z0, x1, y1, z1, r, g, b, a,
                        !solidNorth, !solidSouth, !solidWest, !solidEast);
            }
        }
    }

    /** 1.0 in the middle of the layer, fading linearly to 0.0 at radius. */
    private static float fadeMultiplier(float dist, float radius, float fadeDistance) {
        float fadeStart = radius - fadeDistance;
        if (dist <= fadeStart) return 1.0F;
        float t = (dist - fadeStart) / fadeDistance;
        return 1.0F - Mth.clamp(t, 0.0F, 1.0F);
    }

    private static float smoothstep(float t) {
        t = Mth.clamp(t, 0.0F, 1.0F);
        return t * t * (3.0F - 2.0F * t);
    }

    /** Fractal value noise, 0..1, sampled in world space for this layer's scale/seed/octaves/drift. */
    private static float coverage(float worldX, float worldZ, CloudLayer layer) {
        // Domain warp: distort the sample point with a lower-frequency noise field first,
        // so cloud clump boundaries come out as organic curves instead of straight-edged
        // lattice patches. This is what turns blobby-but-boxy shapes into the soft, rounded
        // cumulus silhouettes you get from real cloud noise.
        float warpScale = layer.noiseScale() * 2.5F;
        float warpX = fractalNoise(worldX / warpScale, worldZ / warpScale, layer.seed() + 777, 2) - 0.5F;
        float warpZ = fractalNoise((worldX + 500.0F) / warpScale, (worldZ + 500.0F) / warpScale, layer.seed() + 888, 2) - 0.5F;
        float warpedX = worldX + warpX * layer.noiseScale() * 0.6F;
        float warpedZ = worldZ + warpZ * layer.noiseScale() * 0.6F;

        float nx = warpedX / layer.noiseScale();
        float nz = warpedZ / layer.noiseScale();
        return fractalNoise(nx, nz, layer.seed(), layer.octaves());
    }

    // ~50 degrees — an arbitrary, non-axis-aligned rotation applied per octave below.
    private static final float OCTAVE_ROT_SIN = Mth.sin(0.87F);
    private static final float OCTAVE_ROT_COS = Mth.cos(0.87F);

    private static float fractalNoise(float x, float z, int seed, int octaves) {
        float total = 0.0F;
        float amplitude = 0.5F;
        float frequency = 1.0F;
        float amplitudeSum = 0.0F;
        float rx = x;
        float rz = z;
        for (int o = 0; o < octaves; o++) {
            total += valueNoise(rx * frequency, rz * frequency, seed + o * 101) * amplitude;
            amplitudeSum += amplitude;
            amplitude *= 0.5F;
            frequency *= 2.0F;
            // Rotate the sampling axes before the next octave — plain value noise is biased
            // along the X/Z grid directions, which is exactly what produces thin diagonal
            // streaks instead of round clumps. Rotating each octave breaks that alignment.
            float nrx = rx * OCTAVE_ROT_COS - rz * OCTAVE_ROT_SIN;
            float nrz = rx * OCTAVE_ROT_SIN + rz * OCTAVE_ROT_COS;
            rx = nrx;
            rz = nrz;
        }
        return total / amplitudeSum;
    }

    private static float valueNoise(float x, float z, int seed) {
        int x0 = Mth.floor(x);
        int z0 = Mth.floor(z);
        int x1 = x0 + 1;
        int z1 = z0 + 1;

        float tx = smoothstep(x - x0);
        float tz = smoothstep(z - z0);

        float n00 = hash(x0, z0, seed);
        float n10 = hash(x1, z0, seed);
        float n01 = hash(x0, z1, seed);
        float n11 = hash(x1, z1, seed);

        float ix0 = Mth.lerp(tx, n00, n10);
        float ix1 = Mth.lerp(tx, n01, n11);
        return Mth.lerp(tz, ix0, ix1);
    }

    /** Deterministic 0..1 hash of a lattice point — no shared state, safe to call every frame. */
    private static float hash(int x, int z, int seed) {
        int h = x * 374761393 + z * 668265263 + seed * 1274126177;
        h = (h ^ (h >>> 13)) * 1274126177;
        h ^= (h >>> 16);
        return (h & 0x7fffffff) / (float) 0x7fffffff;
    }

    private static void putCube(VertexConsumer consumer, PoseStack poseStack,
                                float x0, float y0, float z0,
                                float x1, float y1, float z1,
                                int r, int g, int b, int a,
                                boolean drawNorth, boolean drawSouth, boolean drawWest, boolean drawEast) {
        var matrix = poseStack.last().pose();
        int light = LightTexture.FULL_BRIGHT;

        // Top
        quad(consumer, matrix, light, r, g, b, a,
                x0, y1, z0, x0, y1, z1, x1, y1, z1, x1, y1, z0, 0, 1, 0);
        // Bottom
        quad(consumer, matrix, light, r, g, b, a,
                x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1, 0, -1, 0);

        if (drawNorth) {
            quad(consumer, matrix, light, r, g, b, a,
                    x0, y0, z0, x0, y1, z0, x1, y1, z0, x1, y0, z0, 0, 0, -1);
        }
        if (drawSouth) {
            quad(consumer, matrix, light, r, g, b, a,
                    x1, y0, z1, x1, y1, z1, x0, y1, z1, x0, y0, z1, 0, 0, 1);
        }
        if (drawWest) {
            quad(consumer, matrix, light, r, g, b, a,
                    x0, y0, z1, x0, y1, z1, x0, y1, z0, x0, y0, z0, -1, 0, 0);
        }
        if (drawEast) {
            quad(consumer, matrix, light, r, g, b, a,
                    x1, y0, z0, x1, y1, z0, x1, y1, z1, x1, y0, z1, 1, 0, 0);
        }
    }

    private static void quad(VertexConsumer consumer, org.joml.Matrix4f matrix, int light,
                             int r, int g, int b, int a,
                             float x0, float y0, float z0,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float x3, float y3, float z3,
                             float nx, float ny, float nz) {
        consumer.addVertex(matrix, x0, y0, z0).setColor(r, g, b, a).setUv(0f, 0f)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, a).setUv(0f, 1f)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, a).setUv(1f, 1f)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
        consumer.addVertex(matrix, x3, y3, z3).setColor(r, g, b, a).setUv(1f, 0f)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(nx, ny, nz);
    }
}