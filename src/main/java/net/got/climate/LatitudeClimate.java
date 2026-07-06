package net.got.climate;

import net.minecraft.util.Mth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Latitude-based climate override.
 *
 * <p>This mirrors the red marker line drawn across the full width of
 * {@code biomemap.png} (4207x3277 px, {@code MAP_SCALE}=50, origin at image
 * centre): the line and everything north of it (smaller world Z, since the
 * image's top edge is north) is the "frozen latitude" — permanently cold
 * regardless of what biome happens to sit there.  This is independent of
 * the physical Wall structure built in {@link net.got.worldgen.WallWorldGen};
 * that's a localized building, this is a world-spanning climate band.
 *
 * <h3>Line data</h3>
 * {@link #LATITUDE_SPINE} holds (worldX, freezeLineZ) points sampled
 * directly from the marker line with no smoothing (one point per image
 * pixel column), sorted ascending by X and covering the entire map width.  For any worldX, the line's Z is found with the same
 * uniform cubic B-spline interpolation used everywhere else in this mod
 * (see {@code WallWorldGen#wallCentreZ} and the bicubic B-spline sampling
 * in {@code GotChunkGenerator}), evaluated over the four nearest spine
 * control points. X outside the range clamps to the nearest end point.
 *
 * <h3>Gradient</h3>
 * Positions south of / on the line get no adjustment at all — every biome
 * and season effect already in place is untouched.  Positions north of the
 * line fade linearly from 0 down to {@link #MAX_ADJUSTMENT} over
 * {@link #FADE_DISTANCE} blocks, so the far north gets colder the further
 * north you go, eventually bottoming out at a guaranteed deep freeze that
 * overrides even the hottest biome's base temperature.
 */
public final class LatitudeClimate {

    // Distance (in blocks) north of the line before the freeze effect fully saturates.
    private static final float FADE_DISTANCE = 4000f;

    // Adjustment applied at full saturation. Large enough to floor even the
    // hottest biome (base temperature up to 2.0) down to the absolute
    // minimum (-0.5) once far enough north of the line.
    private static final float MAX_ADJUSTMENT = -3.0f;

    // ── Spine (worldX -> freezeLineZ) ──────────────────────────────────────
    // Sampled directly (no smoothing) from the red-marker line on biomemap.png,
    // one control point per image pixel column. (4207x3277 px, MAP_SCALE=50,
    // origin at image centre). Sorted by X ascending.
    //
    // NOTE: this used to be a 4207-entry int[][] literal declared directly in
    // source. A static array initializer that size compiles into a single
    // <clinit> method whose bytecode exceeds the JVM's 64KB per-method limit
    // ("code too large"). The data now lives in a bundled resource file
    // (parsed once, lazily, at class-init) instead of being baked into
    // bytecode.
    private static final String SPINE_RESOURCE = "/net/got/climate/latitude_spine.csv";

    private static final int[][] LATITUDE_SPINE = loadSpine();

    private static int[][] loadSpine() {
        List<int[]> points = new ArrayList<>(4207);
        try (InputStream in = LatitudeClimate.class.getResourceAsStream(SPINE_RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("Missing resource " + SPINE_RESOURCE);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    int comma = line.indexOf(',');
                    int x = Integer.parseInt(line.substring(0, comma));
                    int z = Integer.parseInt(line.substring(comma + 1));
                    points.add(new int[]{x, z});
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load " + SPINE_RESOURCE, e);
        }
        return points.toArray(new int[0][]);
    }


    private LatitudeClimate() {}

    /**
     * Returns the freeze-line world Z at the given world X, using a uniform
     * cubic B-spline evaluated over the four nearest spine control points —
     * the same interpolation used for {@code WallWorldGen#wallCentreZ} and
     * the biomemap/heightmap sampling in {@code GotChunkGenerator}.
     * X values outside the spine's range clamp to the nearest end point.
     */
    public static int freezeLineZ(int worldX) {
        int[][] spine = LATITUDE_SPINE;
        int n = spine.length;

        if (worldX <= spine[0][0])     return spine[0][1];
        if (worldX >= spine[n - 1][0]) return spine[n - 1][1];

        int lo = 0, hi = n - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (spine[mid][0] <= worldX) lo = mid;
            else                          hi = mid - 1;
        }

        if (lo == n - 1) return spine[lo][1];

        int i0 = Math.max(0, lo - 1);
        int i1 = lo;
        int i2 = lo + 1;
        int i3 = Math.min(n - 1, lo + 2);

        double z0 = spine[i0][1];
        double z1 = spine[i1][1];
        double z2 = spine[i2][1];
        double z3 = spine[i3][1];

        double x1 = spine[i1][0];
        double x2 = spine[i2][0];
        if (x1 == x2) return (int) Math.round(z1);

        double t  = (worldX - x1) / (x2 - x1);
        double t2 = t * t;
        double t3 = t2 * t;

        double b0 = -t3 + 3*t2 - 3*t + 1;
        double b1 =  3*t3 - 6*t2       + 4;
        double b2 = -3*t3 + 3*t2 + 3*t + 1;
        double b3 =  t3;

        double z = (b0*z0 + b1*z1 + b2*z2 + b3*z3) / 6.0;
        return (int) Math.round(z);
    }

    /**
     * Returns {@code true} if (worldX, worldZ) lies north of (or on) the
     * frozen latitude line.
     */
    public static boolean isBeyondLine(int worldX, int worldZ) {
        return worldZ <= freezeLineZ(worldX);
    }

    /**
     * Returns 0..1 — how far into the frozen-latitude fade a position is.
     * {@code 0} south of/on the line, ramping linearly up to {@code 1} at
     * {@link #FADE_DISTANCE} blocks north of it. This is the raw gradient
     * fraction {@link #temperatureAdjustment} scales {@link #MAX_ADJUSTMENT}
     * by, exposed separately for anything that wants the gradient itself
     * (e.g. a freeze-chance roll) rather than a temperature offset.
     */
    public static float latitudeStrength(int worldX, int worldZ) {
        int lineZ = freezeLineZ(worldX);
        int northOf = lineZ - worldZ; // positive = north of the line
        if (northOf <= 0) return 0f;
        return Mth.clamp(northOf / FADE_DISTANCE, 0f, 1f);
    }

    /**
     * Temperature offset to layer on top of a biome's own (seasonal) effective
     * temperature. Returns {@code 0} south of the line (no effect at all —
     * every other climate system is left completely untouched there), fading
     * linearly to {@link #MAX_ADJUSTMENT} over {@link #FADE_DISTANCE} blocks
     * north of it.
     */
    public static float temperatureAdjustment(int worldX, int worldZ) {
        return latitudeStrength(worldX, worldZ) * MAX_ADJUSTMENT;
    }
}
