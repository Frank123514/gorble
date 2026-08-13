package net.got.init;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.got.worldgen.biome.ConfiguredFeatures;

import java.util.Optional;

public final class TreeGrowers {

    private static TreeGrower simple(
            String name,
            net.minecraft.resources.ResourceKey<
                    net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> feature) {
        return new TreeGrower(
                "got." + name,
                Optional.empty(),
                Optional.of(feature),
                Optional.empty()
        );
    }

    public static final TreeGrower ALDER    = simple("alder",    ConfiguredFeatures.ALDER);
    public static final TreeGrower APPLE    = simple("apple",    ConfiguredFeatures.APPLE);
    public static final TreeGrower ASH      = simple("ash",      ConfiguredFeatures.ASH);
    public static final TreeGrower ASPEN    = simple("aspen",    ConfiguredFeatures.ASPEN);
    public static final TreeGrower BEECH    = simple("beech",    ConfiguredFeatures.BEECH);
    public static final TreeGrower CHESTNUT = simple("chestnut", ConfiguredFeatures.CHESTNUT);
    public static final TreeGrower ELM      = simple("elm",      ConfiguredFeatures.ELM);
    public static final TreeGrower HAWTHORN = simple("hawthorn", ConfiguredFeatures.HAWTHORN);
    public static final TreeGrower LINDEN   = simple("linden",   ConfiguredFeatures.LINDEN);
    public static final TreeGrower MAPLE    = simple("maple",    ConfiguredFeatures.MAPLE);
    public static final TreeGrower WILLOW   = simple("willow",   ConfiguredFeatures.WILLOW);

    public static final TreeGrower CEDAR        = simple("cedar",        ConfiguredFeatures.CEDAR);
    public static final TreeGrower FIR          = simple("fir",          ConfiguredFeatures.FIR);
    public static final TreeGrower PINE         = simple("pine",         ConfiguredFeatures.PINE);
    public static final TreeGrower REDWOOD      = simple("redwood",      ConfiguredFeatures.REDWOOD);
    public static final TreeGrower SENTINAL     = simple("sentinal",     ConfiguredFeatures.SENTINAL);
    public static final TreeGrower SOLDIER_PINE = simple("soldier_pine", ConfiguredFeatures.SOLDIER_PINE);

    public static final TreeGrower BLUE_MAHOE  = simple("blue_mahoe",  ConfiguredFeatures.BLUE_MAHOE);
    public static final TreeGrower CINNAMON    = simple("cinnamon",    ConfiguredFeatures.CINNAMON);
    public static final TreeGrower CLOVE       = simple("clove",       ConfiguredFeatures.CLOVE);
    public static final TreeGrower EBONY       = simple("ebony",       ConfiguredFeatures.EBONY);
    public static final TreeGrower GOLDENHEART = simple("goldenheart", ConfiguredFeatures.GOLDENHEART);
    public static final TreeGrower MAHOGANY    = simple("mahogany",    ConfiguredFeatures.MAHOGANY);
    public static final TreeGrower MYRRH       = simple("myrrh",       ConfiguredFeatures.MYRRH);

    public static final TreeGrower BLACK_COTTONWOOD = simple("black_cottonwood", ConfiguredFeatures.BLACK_COTTONWOOD);
    public static final TreeGrower COTTONWOOD       = simple("cottonwood",       ConfiguredFeatures.COTTONWOOD);

    public static final TreeGrower BLACKBARK = simple("blackbark", ConfiguredFeatures.BLACKBARK);
    public static final TreeGrower BLOODWOOD = simple("bloodwood", ConfiguredFeatures.BLOODWOOD);
    public static final TreeGrower IRONWOOD  = simple("ironwood",  ConfiguredFeatures.IRONWOOD);
    public static final TreeGrower WEIRWOOD  = simple("weirwood",  ConfiguredFeatures.WEIRWOOD);
    public static final TreeGrower WORMTREE  = simple("wormtree",  ConfiguredFeatures.WORMTREE);

public static final TreeGrower NIGHTWOOD            = simple("nightwood", ConfiguredFeatures.NIGHTWOOD);
    public static final TreeGrower PURPLEHEART          = simple("purpleheart", ConfiguredFeatures.PURPLEHEART);
    public static final TreeGrower TIGERWOOD            = simple("tigerwood", ConfiguredFeatures.TIGERWOOD);
    public static final TreeGrower SANDALWOOD           = simple("sandalwood", ConfiguredFeatures.SANDALWOOD);
    public static final TreeGrower SANDBEGGAR           = simple("sandbeggar", ConfiguredFeatures.SANDBEGGAR);
    public static final TreeGrower APRICOT              = simple("apricot", ConfiguredFeatures.APRICOT);
    public static final TreeGrower BLACKTHORN           = simple("blackthorn", ConfiguredFeatures.BLACKTHORN);
    public static final TreeGrower RED_CHERRY               = simple("red_cherry", ConfiguredFeatures.RED_CHERRY);
    public static final TreeGrower WHITE_CHERRY               = simple("white_cherry", ConfiguredFeatures.WHITE_CHERRY);
    public static final TreeGrower BLACK_CHERRY               = simple("black_cherry", ConfiguredFeatures.BLACK_CHERRY);
    public static final TreeGrower CRABAPPLE            = simple("crabapple", ConfiguredFeatures.CRABAPPLE);
    public static final TreeGrower DATE_PALM            = simple("date_palm", ConfiguredFeatures.DATE_PALM);
    public static final TreeGrower FIG                  = simple("fig", ConfiguredFeatures.FIG);
    public static final TreeGrower LEMON                = simple("lemon", ConfiguredFeatures.LEMON);
    public static final TreeGrower LIME                 = simple("lime", ConfiguredFeatures.LIME);
    public static final TreeGrower OLIVE                = simple("olive", ConfiguredFeatures.OLIVE);
    public static final TreeGrower ORANGE               = simple("orange", ConfiguredFeatures.ORANGE);
    public static final TreeGrower PEACH                = simple("peach", ConfiguredFeatures.PEACH);
    public static final TreeGrower PEAR                 = simple("pear", ConfiguredFeatures.PEAR);
    public static final TreeGrower PERSIMMON            = simple("persimmon", ConfiguredFeatures.PERSIMMON);
    public static final TreeGrower PINK_IVORY           = simple("pink_ivory", ConfiguredFeatures.PINK_IVORY);
    public static final TreeGrower PLUM                 = simple("plum", ConfiguredFeatures.PLUM);
    public static final TreeGrower POMEGRANATE          = simple("pomegranate", ConfiguredFeatures.POMEGRANATE);
    public static final TreeGrower PRUNE                = simple("prune", ConfiguredFeatures.PRUNE);
    public static final TreeGrower ALMOND               = simple("almond", ConfiguredFeatures.ALMOND);
    public static final TreeGrower NUTMEG               = simple("nutmeg", ConfiguredFeatures.NUTMEG);
    public static final TreeGrower HEMLOCK              = simple("hemlock", ConfiguredFeatures.HEMLOCK);
    
    private TreeGrowers() {}
}