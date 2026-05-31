package net.got.init;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.got.worldgen.biome.GotConfiguredFeatures;

import java.util.Optional;

/**
 * Holds a {@link TreeGrower} instance for every GOT sapling.
 *
 * <h3>NeoForge / MC 1.21.4 pattern</h3>
 * <p>{@link TreeGrower} is a record that binds a sapling to its tree shape via a
 * {@link net.minecraft.resources.ResourceKey ResourceKey}{@code <ConfiguredFeature>}.
 * The constructor signature is:
 * <pre>{@code
 * TreeGrower(
 *   String                                        name,                 // unique id used in logging
 *   Optional<Float>                               secondaryChance,      // chance of alternate variant
 *   Optional<ResourceKey<ConfiguredFeature<?,?>>> primaryFeature,       // standard tree
 *   Optional<ResourceKey<ConfiguredFeature<?,?>>> primaryFlowerFeature  // flower-decorated variant
 * )
 * }</pre>
 *
 * <p>All GOT saplings produce a single tree variant with no mega or flower form,
 * so the secondary-chance and flower-feature slots are always {@link Optional#empty()}.
 *
 * <p>Each key resolves to a JSON file under
 * {@code data/got/worldgen/configured_feature/<n>.json}. The constants come from
 * {@link GotConfiguredFeatures}, so any mis-spelled name is a compile error rather
 * than a silent missing-feature warning at runtime.
 */
public final class GotTreeGrowers {

    // ─────────────────────────────────────────────────────────────────────────
    // Factory helper
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Builds a {@link TreeGrower} whose only variant is the supplied configured
     * feature key.  The string id follows the convention {@code "got.<name>"} so
     * that Minecraft's internal logging can identify the grower unambiguously.
     */
    private static TreeGrower simple(
            String name,
            net.minecraft.resources.ResourceKey<
                    net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> feature) {
        return new TreeGrower(
                "got." + name,
                Optional.empty(),      // no secondary (mega-tree) variant
                Optional.of(feature),  // primary / normal tree
                Optional.empty()       // no flower-decorated variant
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Temperate broadleaf
    // ─────────────────────────────────────────────────────────────────────────

    public static final TreeGrower ALDER    = simple("alder",    GotConfiguredFeatures.ALDER);
    public static final TreeGrower APPLE    = simple("apple",    GotConfiguredFeatures.APPLE);
    public static final TreeGrower ASH      = simple("ash",      GotConfiguredFeatures.ASH);
    public static final TreeGrower ASPEN    = simple("aspen",    GotConfiguredFeatures.ASPEN);
    public static final TreeGrower BEECH    = simple("beech",    GotConfiguredFeatures.BEECH);
    public static final TreeGrower CHESTNUT = simple("chestnut", GotConfiguredFeatures.CHESTNUT);
    public static final TreeGrower ELM      = simple("elm",      GotConfiguredFeatures.ELM);
    public static final TreeGrower HAWTHORN = simple("hawthorn", GotConfiguredFeatures.HAWTHORN);
    public static final TreeGrower LINDEN   = simple("linden",   GotConfiguredFeatures.LINDEN);
    public static final TreeGrower MAPLE    = simple("maple",    GotConfiguredFeatures.MAPLE);
    public static final TreeGrower WILLOW   = simple("willow",   GotConfiguredFeatures.WILLOW);

    // ─────────────────────────────────────────────────────────────────────────
    // Conifers
    // ─────────────────────────────────────────────────────────────────────────

    public static final TreeGrower CEDAR        = simple("cedar",        GotConfiguredFeatures.CEDAR);
    public static final TreeGrower FIR          = simple("fir",          GotConfiguredFeatures.FIR);
    public static final TreeGrower PINE         = simple("pine",         GotConfiguredFeatures.PINE);
    public static final TreeGrower REDWOOD      = simple("redwood",      GotConfiguredFeatures.REDWOOD);
    public static final TreeGrower SENTINAL     = simple("sentinal",     GotConfiguredFeatures.SENTINAL);
    public static final TreeGrower SOLDIER_PINE = simple("soldier_pine", GotConfiguredFeatures.SOLDIER_PINE);

    // ─────────────────────────────────────────────────────────────────────────
    // Exotic / tropical
    // ─────────────────────────────────────────────────────────────────────────

    public static final TreeGrower BLUE_MAHOE  = simple("blue_mahoe",  GotConfiguredFeatures.BLUE_MAHOE);
    public static final TreeGrower CINNAMON    = simple("cinnamon",    GotConfiguredFeatures.CINNAMON);
    public static final TreeGrower CLOVE       = simple("clove",       GotConfiguredFeatures.CLOVE);
    public static final TreeGrower EBONY       = simple("ebony",       GotConfiguredFeatures.EBONY);
    public static final TreeGrower GOLDENHEART = simple("goldenheart", GotConfiguredFeatures.GOLDENHEART);
    public static final TreeGrower MAHOGANY    = simple("mahogany",    GotConfiguredFeatures.MAHOGANY);
    public static final TreeGrower MYRRH       = simple("myrrh",       GotConfiguredFeatures.MYRRH);

    // ─────────────────────────────────────────────────────────────────────────
    // Wetland / riverside
    // ─────────────────────────────────────────────────────────────────────────

    public static final TreeGrower BLACK_COTTONWOOD = simple("black_cottonwood", GotConfiguredFeatures.BLACK_COTTONWOOD);
    public static final TreeGrower COTTONWOOD       = simple("cottonwood",       GotConfiguredFeatures.COTTONWOOD);

    // ─────────────────────────────────────────────────────────────────────────
    // Dark / special
    // ─────────────────────────────────────────────────────────────────────────

    public static final TreeGrower BLACKBARK = simple("blackbark", GotConfiguredFeatures.BLACKBARK);
    public static final TreeGrower BLOODWOOD = simple("bloodwood", GotConfiguredFeatures.BLOODWOOD);
    public static final TreeGrower IRONWOOD  = simple("ironwood",  GotConfiguredFeatures.IRONWOOD);
    public static final TreeGrower WEIRWOOD  = simple("weirwood",  GotConfiguredFeatures.WEIRWOOD);
    public static final TreeGrower WORMTREE  = simple("wormtree",  GotConfiguredFeatures.WORMTREE);

public static final TreeGrower NIGHTWOOD            = simple("nightwood", GotConfiguredFeatures.NIGHTWOOD);
    public static final TreeGrower PURPLEHEART          = simple("purpleheart", GotConfiguredFeatures.PURPLEHEART);
    public static final TreeGrower TIGERWOOD            = simple("tigerwood", GotConfiguredFeatures.TIGERWOOD);
    public static final TreeGrower BURL                 = simple("burl", GotConfiguredFeatures.BURL);
    public static final TreeGrower SANDALWOOD           = simple("sandalwood", GotConfiguredFeatures.SANDALWOOD);
    public static final TreeGrower SANDBEGGAR           = simple("sandbeggar", GotConfiguredFeatures.SANDBEGGAR);
    public static final TreeGrower APRICOT              = simple("apricot", GotConfiguredFeatures.APRICOT);
    public static final TreeGrower BLACKTHORN           = simple("blackthorn", GotConfiguredFeatures.BLACKTHORN);
    public static final TreeGrower CHERRY               = simple("cherry", GotConfiguredFeatures.CHERRY);
    public static final TreeGrower CRABAPPLE            = simple("crabapple", GotConfiguredFeatures.CRABAPPLE);
    public static final TreeGrower DATE_PALM            = simple("date_palm", GotConfiguredFeatures.DATE_PALM);
    public static final TreeGrower FIG                  = simple("fig", GotConfiguredFeatures.FIG);
    public static final TreeGrower LEMON                = simple("lemon", GotConfiguredFeatures.LEMON);
    public static final TreeGrower LIME                 = simple("lime", GotConfiguredFeatures.LIME);
    public static final TreeGrower OLIVE                = simple("olive", GotConfiguredFeatures.OLIVE);
    public static final TreeGrower ORANGE               = simple("orange", GotConfiguredFeatures.ORANGE);
    public static final TreeGrower PEACH                = simple("peach", GotConfiguredFeatures.PEACH);
    public static final TreeGrower PEAR                 = simple("pear", GotConfiguredFeatures.PEAR);
    public static final TreeGrower PERSIMMON            = simple("persimmon", GotConfiguredFeatures.PERSIMMON);
    public static final TreeGrower PINK_IVORY           = simple("pink_ivory", GotConfiguredFeatures.PINK_IVORY);
    public static final TreeGrower PLUM                 = simple("plum", GotConfiguredFeatures.PLUM);
    public static final TreeGrower POMEGRANATE          = simple("pomegranate", GotConfiguredFeatures.POMEGRANATE);
    public static final TreeGrower PRUNE                = simple("prune", GotConfiguredFeatures.PRUNE);
    public static final TreeGrower ALMOND               = simple("almond", GotConfiguredFeatures.ALMOND);
    public static final TreeGrower NUTMEG               = simple("nutmeg", GotConfiguredFeatures.NUTMEG);
    public static final TreeGrower HEMLOCK              = simple("hemlock", GotConfiguredFeatures.HEMLOCK);
    // ─────────────────────────────────────────────────────────────────────────

    private GotTreeGrowers() {}
}