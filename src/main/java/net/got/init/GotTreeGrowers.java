package net.got.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

/**
 * Holds a {@link TreeGrower} instance for every GOT wood type.
 * Each grower references the mod's own configured_feature JSON by ResourceKey,
 * so saplings grow into the correct species-specific tree.
 */
public final class GotTreeGrowers {

    private static TreeGrower of(String woodName) {
        ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath("got", woodName)
        );
        return new TreeGrower(
                "got." + woodName,
                Optional.empty(),   // no 2×2 mega-tree variant
                Optional.empty(),   // no flower-triggered mega-tree
                Optional.of(key)    // normal single tree
        );
    }

    // ── Temperate broadleaf ───────────────────────────────────────────────
    public static final TreeGrower ALDER        = of("alder");
    public static final TreeGrower ASH          = of("ash");
    public static final TreeGrower ASPEN        = of("aspen");
    public static final TreeGrower BEECH        = of("beech");
    public static final TreeGrower CHESTNUT     = of("chestnut");
    public static final TreeGrower ELM          = of("elm");
    public static final TreeGrower HAWTHORN     = of("hawthorn");
    public static final TreeGrower LINDEN       = of("linden");
    public static final TreeGrower MAPLE        = of("maple");
    public static final TreeGrower WILLOW       = of("willow");

    // ── Conifers ──────────────────────────────────────────────────────────
    public static final TreeGrower CEDAR        = of("cedar");
    public static final TreeGrower FIR          = of("fir");
    public static final TreeGrower PINE         = of("pine");
    public static final TreeGrower REDWOOD      = of("redwood");
    public static final TreeGrower SENTINAL     = of("sentinal");
    public static final TreeGrower SOLDIER_PINE = of("soldier_pine");

    // ── Exotic / tropical ─────────────────────────────────────────────────
    public static final TreeGrower BLUE_MAHOE   = of("blue_mahoe");
    public static final TreeGrower CINNAMON     = of("cinnamon");
    public static final TreeGrower CLOVE        = of("clove");
    public static final TreeGrower EBONY        = of("ebony");
    public static final TreeGrower GOLDENHEART  = of("goldenheart");
    public static final TreeGrower MAHOGANY     = of("mahogany");
    public static final TreeGrower MYRRH        = of("myrrh");

    // ── Wetland / riverside ───────────────────────────────────────────────
    public static final TreeGrower BLACK_COTTONWOOD = of("black_cottonwood");
    public static final TreeGrower COTTONWOOD   = of("cottonwood");

    // ── Dark / special ────────────────────────────────────────────────────
    public static final TreeGrower APPLE        = of("apple");
    public static final TreeGrower BLACKBARK    = of("blackbark");
    public static final TreeGrower BLOODWOOD    = of("bloodwood");
    public static final TreeGrower IRONWOOD     = of("ironwood");
    public static final TreeGrower WEIRWOOD     = of("weirwood");
    public static final TreeGrower WORMTREE     = of("wormtree");

    private GotTreeGrowers() {}
}
