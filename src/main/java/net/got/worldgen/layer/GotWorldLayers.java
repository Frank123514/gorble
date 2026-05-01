package net.got.worldgen.layer;

/**
 * Assembles the complete LOTR-style biome layer stack for GoT.
 * Pure Java — no net.minecraft.world.level.newbiome classes used.
 */
public final class GotWorldLayers {
    private GotWorldLayers() {}

    public static LayerArea create(long worldSeed, GotBiomeGenSettings settings) {
        int riverSize = settings.getRiverSize();
        int biomeSize = settings.getBiomeSize();

        // River branch
        LayerArea river = seedRivers(worldSeed, 100L);
        river = zoom(river, worldSeed, 1000L, 2 + riverSize);
        river = applyRiverFilter(river, worldSeed, 1L);
        river = smooth(river, worldSeed, 1000L);
        final LayerArea riverFinal = river;

        // Variant seed branch
        LayerArea varSeed = seedVariants(worldSeed, 3000L);
        varSeed = zoom(varSeed, worldSeed, 3000L, 2);
        final LayerArea varSeedFinal = varSeed;

        // Biome branch — sea mask
        LayerArea sea = seedSeas(worldSeed, 2012L);
        sea = zoom(sea, worldSeed, 200L, 3);
        sea = removeSeaAtOrigin(sea);

        // Classic biome assignment
        LayerArea biomes = classicBiomes(sea, worldSeed, 2013L);
        biomes = zoom(biomes, worldSeed, 300L, 2);

        // Sub-biome variants + deep ocean
        biomes = biomeVariants(biomes, varSeedFinal);

        // Scatter islands
        biomes = addIslands(biomes, worldSeed, 400L, 400);

        // Zoom loop with shore insertion
        biomes = zoomWithShores(biomes, biomeSize, worldSeed);

        // Smooth + rivers
        biomes = smooth(biomes, worldSeed, 1000L);
        biomes = addRivers(biomes, riverFinal);

        return biomes;
    }

    // ── Individual layer constructors ─────────────────────────────────────

    private static LayerArea seedRivers(long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> { rng.initCoord(x, z); return rng.nextInt(299999) + 2; });
    }

    private static LayerArea applyRiverFilter(LayerArea p, long ws, long salt) {
        return new LayerArea((x, z) -> {
            int c = rf(p.get(x, z)), n = rf(p.get(x, z-1)), s = rf(p.get(x, z+1));
            int w = rf(p.get(x-1, z)), e = rf(p.get(x+1, z));
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
        return new LayerArea((x, z) -> { rng.initCoord(x, z); return rng.nextInt(4) == 0 ? 1 : 0; });
    }

    private static LayerArea removeSeaAtOrigin(LayerArea p) {
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
            if (id == GotBiomeRegistry.ID_OCEAN
                && GotBiomeRegistry.isSea(biomeP.get(x-1, z))
                && GotBiomeRegistry.isSea(biomeP.get(x+1, z))
                && GotBiomeRegistry.isSea(biomeP.get(x, z-1))
                && GotBiomeRegistry.isSea(biomeP.get(x, z+1))) {
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
             && GotBiomeRegistry.isSea(p.get(x-1, z)) && GotBiomeRegistry.isSea(p.get(x+1, z))
             && GotBiomeRegistry.isSea(p.get(x, z-1)) && GotBiomeRegistry.isSea(p.get(x, z+1))) {
                rng.initCoord(x, z);
                if (rng.nextInt(chance) == 0) return GotBiomeRegistry.ID_STONY_SHORE;
            }
            return id;
        });
    }

    private static LayerArea zoomWithShores(LayerArea base, int numZooms, long ws) {
        LayerArea layer = base;
        for (int i = 0; i < numZooms; i++) {
            if (i == Math.max(0, numZooms - 4)) {
                layer = addIslands(layer, ws, 300L, 400);
                layer = shoreMainland(layer);
            }
            if (i == Math.max(0, numZooms - 3)) layer = shoreIsland(layer);
            layer = zoomOne(layer, ws, 1000L + i);
        }
        return layer;
    }

    private static LayerArea shoreMainland(LayerArea p) {
        return new LayerArea((x, z) -> {
            int c = p.get(x, z);
            if (!GotBiomeRegistry.isSea(c)
             && (GotBiomeRegistry.isSea(p.get(x,z-1)) || GotBiomeRegistry.isSea(p.get(x+1,z))
              || GotBiomeRegistry.isSea(p.get(x,z+1)) || GotBiomeRegistry.isSea(p.get(x-1,z))))
                return GotBiomeRegistry.getShoreFor(c);
            return c;
        });
    }

    private static LayerArea shoreIsland(LayerArea p) {
        return new LayerArea((x, z) -> {
            int c = p.get(x, z);
            if (GotBiomeRegistry.isSea(c)
             && (!GotBiomeRegistry.isSea(p.get(x,z-1)) || !GotBiomeRegistry.isSea(p.get(x+1,z))
              || !GotBiomeRegistry.isSea(p.get(x,z+1)) || !GotBiomeRegistry.isSea(p.get(x-1,z))))
                return GotBiomeRegistry.ID_STONY_SHORE;
            return c;
        });
    }

    private static LayerArea addRivers(LayerArea biomeP, LayerArea riverP) {
        return new LayerArea((x, z) -> {
            int id = biomeP.get(x, z);
            if (GotBiomeRegistry.isWater(id)) return id;
            if (riverP.get(x, z) >= 1) return GotBiomeRegistry.getRiverFor(id);
            return id;
        });
    }

    private static LayerArea zoomOne(LayerArea p, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int px = x >> 1, pz = z >> 1, lx = x & 1, lz = z & 1;
            int c = p.get(px, pz), e = p.get(px+1, pz), s = p.get(px, pz+1), se = p.get(px+1, pz+1);
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

    private static LayerArea smooth(LayerArea p, long ws, long salt) {
        LayerRng rng = new LayerRng(ws, salt);
        return new LayerArea((x, z) -> {
            int c = p.get(x,z), n = p.get(x,z-1), sv = p.get(x,z+1), w = p.get(x-1,z), e = p.get(x+1,z);
            if (n == sv && w == e) { rng.initCoord(x, z); return rng.nextInt(2) == 0 ? n : w; }
            if (n == sv) return n;
            if (w == e)  return w;
            return c;
        });
    }
}
