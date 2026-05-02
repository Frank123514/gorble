package net.got.worldgen.layer;

/**
 * Assembles the complete LOTR Renewed-style biome layer stack for GoT.
 *
 * <h2>Pipeline overview (matches LOTR Renewed 1.16.5)</h2>
 * <pre>
 *  1. seedSeas          — 25% ocean mask
 *  2. zoom × 3         — expand sea mask
 *  3. removeSeaAtOrigin — keep spawn on land
 *  4. classicBiomes    — assign land biomes
 *  5. zoom × 2         — expand biomes
 *  6. biomeVariants    — deep ocean + wolfswood→ironwood subtype
 *  7. addIslands       — rare ocean islands
 *  8. zoomWithShores   — 5 zoom passes + shore insertion + lake injection
 *  9. smooth
 * 10. (separate river branch, riverSize=3 zooms)
 * 11. addRivers        — merge rivers into final map
 * </pre>
 *
 * <h2>Key LOTR-matching design decisions</h2>
 * <ul>
 *   <li><b>riverSize = 3</b> (from {@link GotBiomeGenSettings}) — LOTR Renewed used
 *       fewer river zoom passes than vanilla, producing narrow channels (1–6 blocks wide
 *       at terrain scale) rather than the 10–20 block wide vanilla rivers.</li>
 *   <li><b>Lake injection</b> — LOTR Renewed injected inland lake biomes in flat
 *       land areas. Added here at the end of the zoom chain: flat plains / neck
 *       cells surrounded by same-type land have a 1-in-500 chance of becoming a
 *       lake, producing the mountain lakes visible in the screenshots.</li>
 *   <li><b>Shore on both sides</b> — mainland coastlines get {@code stony_shore},
 *       and ocean-adjacent ocean cells bordering land also get {@code stony_shore},
 *       exactly as in LOTR Renewed.</li>
 * </ul>
 */
public final class GotWorldLayers {
    private GotWorldLayers() {}

    public static LayerArea create(long worldSeed, GotBiomeGenSettings settings) {
        int riverSize = settings.getRiverSize();  // 3
        int biomeSize = settings.getBiomeSize();  // 5

        // ── River branch ──────────────────────────────────────────────────
        // LOTR Renewed: fewer zoom steps → narrower, more defined channels
        LayerArea river = seedRivers(worldSeed, 100L);
        river = zoom(river, worldSeed, 1000L, 2 + riverSize);   // 2+3=5 total
        river = applyRiverFilter(river, worldSeed, 1L);
        river = smooth(river, worldSeed, 1000L);
        final LayerArea riverFinal = river;

        // ── Variant seed branch (for wolfswood→ironwood etc.) ─────────────
        LayerArea varSeed = seedVariants(worldSeed, 3000L);
        varSeed = zoom(varSeed, worldSeed, 3000L, 2);
        final LayerArea varSeedFinal = varSeed;

        // ── Biome branch ──────────────────────────────────────────────────
        LayerArea sea = seedSeas(worldSeed, 2012L);
        sea = zoom(sea, worldSeed, 200L, 3);
        sea = removeSeaAtOrigin(sea);

        LayerArea biomes = classicBiomes(sea, worldSeed, 2013L);
        biomes = zoom(biomes, worldSeed, 300L, 2);

        // Sub-biome variants (deep ocean upgrade, wolfswood→ironwood)
        biomes = biomeVariants(biomes, varSeedFinal);

        // Scatter rare islands in open ocean
        biomes = addIslands(biomes, worldSeed, 400L, 400);

        // 5 zoom passes with shore + lake insertion
        biomes = zoomWithShores(biomes, biomeSize, worldSeed);

        biomes = smooth(biomes, worldSeed, 1000L);

        // Merge rivers
        biomes = addRivers(biomes, riverFinal);

        return biomes;
    }

    // ── Individual layer constructors ─────────────────────────────────────

    private static LayerArea seedRivers(long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> { rng.initCoord(x, z); return rng.nextInt(299999) + 2; });
    }

    /**
     * River filter: a cell is "river" only if its 4 cardinal neighbours have
     * different river seeds, producing a single-cell-wide branch skeleton.
     * Matches the LOTR Renewed (= vanilla 1.16) river layer exactly.
     */
    private static LayerArea applyRiverFilter(LayerArea p, long ws, long salt) {
        return new LayerArea((x, z) -> {
            int c = rf(p.get(x, z)),  n = rf(p.get(x, z - 1)), s = rf(p.get(x, z + 1));
            int w = rf(p.get(x - 1, z)), e = rf(p.get(x + 1, z));
            return (c == n && c == s && c == w && c == e) ? 0 : 1;
        });
    }

    private static int rf(int v) { return v >= 2 ? 2 + (v & 1) : v; }

    private static LayerArea seedVariants(long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            if (x == 0 && z == 0) return 1000;
            rng.initCoord(x, z);
            return rng.nextInt(1000);
        });
    }

    private static LayerArea seedSeas(long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        // 25% ocean probability — same as LOTR Renewed / vanilla
        return new LayerArea((x, z) -> { rng.initCoord(x, z); return rng.nextInt(4) == 0 ? 1 : 0; });
    }

    private static LayerArea removeSeaAtOrigin(LayerArea p) {
        // Guarantee a 3×3 block of land at world origin so the player spawns on land
        return new LayerArea((x, z) -> (Math.abs(x) <= 1 && Math.abs(z) <= 1) ? 0 : p.get(x, z));
    }

    private static LayerArea classicBiomes(LayerArea seaP, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        int[] major = GotBiomeRegistry.MAJOR_IDS;
        return new LayerArea((x, z) -> {
            if (seaP.get(x, z) > 0) return GotBiomeRegistry.ID_OCEAN;
            rng.initCoord(x, z);
            return major[rng.nextInt(major.length)];
        });
    }

    private static LayerArea biomeVariants(LayerArea biomeP, LayerArea varP) {
        return new LayerArea((x, z) -> {
            int id = biomeP.get(x, z);
            float f = varP.get(x, z) / 1000f;

            // Upgrade ocean cells surrounded by other ocean to deep ocean
            if (id == GotBiomeRegistry.ID_OCEAN
                    && GotBiomeRegistry.isSea(biomeP.get(x - 1, z))
                    && GotBiomeRegistry.isSea(biomeP.get(x + 1, z))
                    && GotBiomeRegistry.isSea(biomeP.get(x, z - 1))
                    && GotBiomeRegistry.isSea(biomeP.get(x, z + 1))) {
                return GotBiomeRegistry.ID_DEEP_OCEAN;
            }
            int sub = GotBiomeRegistry.getSubtype(id, f);
            return sub >= 0 ? sub : id;
        });
    }

    private static LayerArea addIslands(LayerArea p, long ws, long salt, int chance) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int id = p.get(x, z);
            if (GotBiomeRegistry.isSea(id)
                    && GotBiomeRegistry.isSea(p.get(x - 1, z))
                    && GotBiomeRegistry.isSea(p.get(x + 1, z))
                    && GotBiomeRegistry.isSea(p.get(x, z - 1))
                    && GotBiomeRegistry.isSea(p.get(x, z + 1))) {
                rng.initCoord(x, z);
                if (rng.nextInt(chance) == 0) return GotBiomeRegistry.ID_STONY_SHORE;
            }
            return id;
        });
    }

    /**
     * 5-zoom loop with shore and lake insertion at fixed passes —
     * matches the LOTR Renewed pipeline ordering.
     *
     * <p>Pass order (numZooms=5):
     * <ol>
     *   <li>i=1 → mainland shore layer</li>
     *   <li>i=2 → island shore layer</li>
     *   <li>i=4 → lake injection (late, so lakes are ~biome-sized patches)</li>
     *   <li>each pass → one zoom step</li>
     * </ol>
     */
    private static LayerArea zoomWithShores(LayerArea base, int numZooms, long ws) {
        LayerArea layer = base;
        for (int i = 0; i < numZooms; i++) {
            // Shore insertion on second-to-last two passes (same as LOTR Renewed)
            if (i == Math.max(0, numZooms - 4)) {
                layer = addIslands(layer, ws, 300L, 400);
                layer = shoreMainland(layer);
            }
            if (i == Math.max(0, numZooms - 3)) {
                layer = shoreIsland(layer);
            }
            // Lake injection: flat plains / neck in large land blobs get rare lakes.
            // Done one zoom before the end so lakes are ~1–2 biome-cell patches.
            if (i == numZooms - 2) {
                layer = injectLakes(layer, ws, 500L);
            }
            layer = zoomOne(layer, ws, 1000L + i);
        }
        return layer;
    }

    /**
     * Land coastlines (non-sea cells bordering sea) become stony_shore.
     * Mirrors LOTR Renewed's mainLandEdge / shore layer.
     */
    private static LayerArea shoreMainland(LayerArea p) {
        return new LayerArea((x, z) -> {
            int c = p.get(x, z);
            if (!GotBiomeRegistry.isSea(c)
                    && (GotBiomeRegistry.isSea(p.get(x, z - 1))
                     || GotBiomeRegistry.isSea(p.get(x + 1, z))
                     || GotBiomeRegistry.isSea(p.get(x, z + 1))
                     || GotBiomeRegistry.isSea(p.get(x - 1, z)))) {
                return GotBiomeRegistry.getShoreFor(c);
            }
            return c;
        });
    }

    /**
     * Ocean cells bordering land become stony_shore (island beach layer).
     * Mirrors LOTR Renewed's oceanEdge / beach layer.
     */
    private static LayerArea shoreIsland(LayerArea p) {
        return new LayerArea((x, z) -> {
            int c = p.get(x, z);
            if (GotBiomeRegistry.isSea(c)
                    && (!GotBiomeRegistry.isSea(p.get(x, z - 1))
                     || !GotBiomeRegistry.isSea(p.get(x + 1, z))
                     || !GotBiomeRegistry.isSea(p.get(x, z + 1))
                     || !GotBiomeRegistry.isSea(p.get(x - 1, z)))) {
                return GotBiomeRegistry.ID_STONY_SHORE;
            }
            return c;
        });
    }

    /**
     * Lake injection — LOTR Renewed-equivalent inland lake placement.
     *
     * <p>Criteria: the cell and all 4 cardinal neighbours are the same flat-ish
     * land biome (north, neck, or haunted_forest), and a rare random event fires.
     * The cold-biome neighbour check upgrades the lake to frozen_lake.
     *
     * <p>Chance 1-in-500 (per biome cell, before final zooms) produces roughly
     * one lake per 40–60 km² of plains — visible but not spammy, matching the
     * rate seen in LOTR Renewed Middle-earth maps.
     */
    private static LayerArea injectLakes(LayerArea p, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int id = p.get(x, z);
            if (!isLakeable(id)) return id;
            // Require all 4 neighbours to also be land (not sea, not river, not shore)
            // so lakes don't border the ocean directly.
            int n = p.get(x, z - 1), s = p.get(x, z + 1);
            int w = p.get(x - 1, z), e = p.get(x + 1, z);
            if (GotBiomeRegistry.isWater(n) || GotBiomeRegistry.isWater(s)
             || GotBiomeRegistry.isWater(w) || GotBiomeRegistry.isWater(e)) return id;
            // Frozen-biome adjacency → frozen lake
            boolean frozen = isColdBiome(n) || isColdBiome(s)
                          || isColdBiome(w) || isColdBiome(e);
            rng.initCoord(x, z);
            if (rng.nextInt(500) == 0) {
                return frozen ? GotBiomeRegistry.ID_FROZEN_LAKE : GotBiomeRegistry.ID_LAKE;
            }
            return id;
        });
    }

    /** Flat land biomes that can host an injected lake. */
    private static boolean isLakeable(int id) {
        return id == GotBiomeRegistry.ID_NORTH
            || id == GotBiomeRegistry.ID_NECK
            || id == GotBiomeRegistry.ID_HAUNTED_FOREST
            || id == GotBiomeRegistry.ID_BARROWLANDS;
    }

    private static boolean isColdBiome(int id) {
        return id == GotBiomeRegistry.ID_ALWAYS_WINTER
            || id == GotBiomeRegistry.ID_FROSTFANGS;
    }

    /**
     * River merge: if the river layer flags a cell and the biome is dry land
     * (not already water), replace it with the contextually correct river biome.
     * Matches LOTR Renewed's river layer exactly.
     */
    private static LayerArea addRivers(LayerArea biomeP, LayerArea riverP) {
        return new LayerArea((x, z) -> {
            int id = biomeP.get(x, z);
            if (GotBiomeRegistry.isWater(id)) return id;
            if (riverP.get(x, z) >= 1) return GotBiomeRegistry.getRiverFor(id);
            return id;
        });
    }

    // ── Zoom helpers ──────────────────────────────────────────────────────

    /**
     * Single bilinear-style zoom step.
     * Corner cells copy their parent; edge/centre cells are randomly chosen
     * from two or four neighbours — same algorithm as LOTR Renewed / vanilla 1.16.
     */
    private static LayerArea zoomOne(LayerArea p, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int px = x >> 1, pz = z >> 1, lx = x & 1, lz = z & 1;
            int c = p.get(px, pz), e = p.get(px + 1, pz);
            int s = p.get(px, pz + 1), se = p.get(px + 1, pz + 1);
            rng.initCoord(x & ~1, z & ~1);
            if (lx == 0 && lz == 0) return c;
            if (lx == 0) return rng.nextInt(2) == 0 ? c : s;
            if (lz == 0) return rng.nextInt(2) == 0 ? c : e;
            int first = rng.nextInt(2) == 0 ? c : e;
            return rng.nextInt(2) == 0 ? first : (rng.nextInt(2) == 0 ? s : se);
        });
    }

    private static LayerArea zoom(LayerArea base, long ws, long salt, int count) {
        for (int i = 0; i < count; i++) base = zoomOne(base, ws, salt + i);
        return base;
    }

    /**
     * 3×3 majority-vote smooth layer.
     * Reduces noise in the layer map — identical to LOTR Renewed / vanilla.
     */
    private static LayerArea smooth(LayerArea p, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int c = p.get(x, z), n = p.get(x, z - 1), sv = p.get(x, z + 1);
            int w = p.get(x - 1, z), e = p.get(x + 1, z);
            if (n == sv && w == e) { rng.initCoord(x, z); return rng.nextInt(2) == 0 ? n : w; }
            if (n == sv) return n;
            if (w == e)  return w;
            return c;
        });
    }
}
