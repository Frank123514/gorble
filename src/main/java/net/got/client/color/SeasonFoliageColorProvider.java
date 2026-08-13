package net.got.client.color;

import net.got.climate.SeasonManager;
import net.got.init.ModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class SeasonFoliageColorProvider {

    public static final int SPRING_FOLIAGE = 0x80C050;
    public static final int SUMMER_FOLIAGE = 0x48B518;
    public static final int AUTUMN_FOLIAGE = 0xC07820;
    public static final int WINTER_FOLIAGE = 0x7A6B52;

    public static final int SPRING_GRASS   = 0x91C844;
    public static final int SUMMER_GRASS   = 0x5DB535;
    public static final int AUTUMN_GRASS   = 0xA09030;
    public static final int WINTER_GRASS   = 0x8C7D5E;

    public static final float SEASON_BLEND = 0.70f;

    private static final int[] ALDER        = { 0x6CB830, 0x358712, 0xC07014, 0x6A5E48 };
    private static final int[] APPLE        = { 0x7AB828, 0x606E2A, 0xB06010, 0x706050 };
    private static final int[] ASH          = { 0x60A028, 0x315023, 0xA09010, 0x6A6050 };
    private static final int[] ASPEN        = { 0x94D931, 0x63BF3B, 0xD0BC10, 0x787060 };
    private static final int[] BEECH        = { 0x90C030, 0x4D991C, 0xC88810, 0x807060 };
    private static final int[] BLACK_COTTON = { 0x60A030, 0x385635, 0xB88810, 0x6A6858 };
    private static final int[] BLACKBARK    = { 0x486020, 0x1E2C0F, 0x844010, 0x5A5248 };
    private static final int[] BLOODWOOD    = { 0x6A7828, 0x475625, 0xA01010, 0x6A5A50 };
    private static final int[] BLUE_MAHOE   = { 0x60B060, 0x2F5D3F, 0x48944C, 0x587068 };
    private static final int[] CEDAR        = { 0x487830, 0x244935, 0x2C6030, 0x485850 };
    private static final int[] CHESTNUT     = { 0x709030, 0x426628, 0xC0600C, 0x6A6050 };
    private static final int[] CINNAMON     = { 0x7A9830, 0x5E6E2C, 0xBC6C14, 0x6E6450 };
    private static final int[] CLOVE        = { 0x508030, 0x2D532E, 0xA45C10, 0x5E5848 };
    private static final int[] COTTONWOOD   = { 0x789830, 0x5B7742, 0xC09014, 0x706858 };
    private static final int[] EBONY        = { 0x406020, 0x1D3814, 0x784C10, 0x545048 };
    private static final int[] ELM          = { 0x6A9028, 0x42602E, 0xA89814, 0x686050 };
    private static final int[] FIR          = { 0x589030, 0x38642E, 0x346420, 0x486050 };
    private static final int[] GOLDENHEART  = { 0x709020, 0x374904, 0xD0AC18, 0x706848 };
    private static final int[] HAWTHORN     = { 0x507020, 0x1D3C08, 0xC04C10, 0x5C5448 };
    private static final int[] IRONWOOD     = { 0x506828, 0x1D2F1D, 0x886018, 0x5A5450 };
    private static final int[] LINDEN       = { 0x80A830, 0x597F34, 0xB0A010, 0x726858 };
    private static final int[] MAHOGANY     = { 0x608830, 0x385F29, 0x983C10, 0x5E5850 };
    private static final int[] MAPLE        = { 0x78A830, 0xD6651A, 0xC01408, 0x6A5848 };
    private static final int[] MYRRH        = { 0x507020, 0x204005, 0xB07814, 0x5A5448 };
    private static final int[] OAK          = { 0x74B82A, 0x4E7828, 0xC05C18, 0x706050 };
    private static final int[] PINE         = { 0x508828, 0x335D2A, 0x306020, 0x405848 };
    private static final int[] REDWOOD      = { 0x608038, 0x436147, 0x306028, 0x506050 };
    private static final int[] SENTINAL     = { 0x407858, 0x234D38, 0x245840, 0x3A5448 };
    private static final int[] SOLDIER_PINE = { 0x608020, 0x3D5A1A, 0x3C6018, 0x4A5440 };
    private static final int[] WILLOW       = { 0x688830, 0x436532, 0x98941C, 0x606050 };
    private static final int[] WORMTREE     = { 0x687828, 0x42531A, 0x787C10, 0x626050 };

    private static final int[] NIGHTWOOD          = { 0x506838, 0x2A3C1E, 0x6A4830, 0x484038 };
    private static final int[] PURPLEHEART        = { 0x7868A0, 0x5C4A80, 0x8C5460, 0x605868 };
    private static final int[] TIGERWOOD          = { 0x789040, 0x587030, 0xC07818, 0x686050 };
    private static final int[] SANDALWOOD         = { 0x90A860, 0x708048, 0xC09030, 0x787060 };
    private static final int[] SANDBEGGAR         = { 0xA09858, 0x887840, 0xB07828, 0x806858 };
    private static final int[] APRICOT            = { 0x88C048, 0x60902C, 0xC87020, 0x706050 };
    private static final int[] BLACKTHORN         = { 0x506828, 0x304818, 0x804810, 0x585048 };
    private static final int[] RED_CHERRY             = { 0xF0A0B8, 0x70B038, 0xD02828, 0x685060 };
    private static final int[] BLACK_CHERRY           = { 0x882848, 0x304820, 0x8C1820, 0x483840 };
    private static final int[] WHITE_CHERRY           = { 0xF8E0E8, 0x78B840, 0xD8A818, 0x807068 };
    private static final int[] CRABAPPLE          = { 0x90C848, 0x68A030, 0xD05018, 0x706050 };
    private static final int[] DATE_PALM          = { 0x709858, 0x507840, 0xB08828, 0x707060 };
    private static final int[] FIG                = { 0x80A040, 0x587830, 0xA06828, 0x686050 };
    private static final int[] LEMON              = { 0xA0C850, 0x78A038, 0xD0A020, 0x787060 };
    private static final int[] LIME               = { 0x88C848, 0x60A030, 0xC09820, 0x707060 };
    private static final int[] OLIVE              = { 0x8AA858, 0x688040, 0x908830, 0x707060 };
    private static final int[] ORANGE             = { 0x98C850, 0x70A038, 0xD07818, 0x707050 };
    private static final int[] PEACH              = { 0xF8C8A0, 0x78A830, 0xE08028, 0x706050 };
    private static final int[] PEAR               = { 0x90C048, 0x68902C, 0xC89018, 0x706050 };
    private static final int[] PERSIMMON          = { 0x90B848, 0x689028, 0xD07018, 0x706050 };
    private static final int[] PINK_IVORY         = { 0xF0B0C8, 0xC888A0, 0xD07090, 0x806878 };
    private static final int[] PLUM               = { 0x88A848, 0x608030, 0xB85820, 0x686050 };
    private static final int[] POMEGRANATE        = { 0x98C048, 0x709030, 0xC85028, 0x706858 };
    private static final int[] PRUNE              = { 0x88A848, 0x608030, 0xA86828, 0x686050 };
    private static final int[] ALMOND             = { 0xF0D0B0, 0x789038, 0xC09020, 0x706858 };
    private static final int[] NUTMEG             = { 0x80B048, 0x608830, 0xA07828, 0x686050 };
    private static final int[] HEMLOCK            = { 0x508838, 0x386830, 0x306830, 0x406050 };

    private static final int[] HRANNA             = { 0x8B1010, 0x5DB535, 0x5C3208, 0x8C7D5E };

    private static int pick(int[] colors) {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> colors[0];
            case SUMMER -> colors[1];
            case AUTUMN -> colors[2];
            case WINTER -> colors[3];
        };
    }

    private static BlockColor treeColor(int[] colors) {
        return (state, level, pos, tintIndex) -> pick(colors);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors bc = event.getBlockColors();

        event.register(treeColor(ALDER),        ModBlocks.ALDER_LEAVES.get());
        event.register(treeColor(APPLE),        ModBlocks.APPLE_LEAVES.get());
        event.register(treeColor(ASH),          ModBlocks.ASH_LEAVES.get());
        event.register(treeColor(ASPEN),        ModBlocks.ASPEN_LEAVES.get());
        event.register(treeColor(BEECH),        ModBlocks.BEECH_LEAVES.get());
        event.register(treeColor(BLACK_COTTON), ModBlocks.BLACK_COTTONWOOD_LEAVES.get());
        event.register(treeColor(BLACKBARK),    ModBlocks.BLACKBARK_LEAVES.get());
        event.register(treeColor(BLOODWOOD),    ModBlocks.BLOODWOOD_LEAVES.get());
        event.register(treeColor(BLUE_MAHOE),   ModBlocks.BLUE_MAHOE_LEAVES.get());
        event.register(treeColor(CEDAR),        ModBlocks.CEDAR_LEAVES.get());
        event.register(treeColor(CHESTNUT),     ModBlocks.CHESTNUT_LEAVES.get());
        event.register(treeColor(CINNAMON),     ModBlocks.CINNAMON_LEAVES.get());
        event.register(treeColor(CLOVE),        ModBlocks.CLOVE_LEAVES.get());
        event.register(treeColor(COTTONWOOD),   ModBlocks.COTTONWOOD_LEAVES.get());
        event.register(treeColor(EBONY),        ModBlocks.EBONY_LEAVES.get());
        event.register(treeColor(ELM),          ModBlocks.ELM_LEAVES.get());
        event.register(treeColor(FIR),          ModBlocks.FIR_LEAVES.get());
        event.register(treeColor(GOLDENHEART),  ModBlocks.GOLDENHEART_LEAVES.get());
        event.register(treeColor(HAWTHORN),     ModBlocks.HAWTHORN_LEAVES.get());
        event.register(treeColor(IRONWOOD),     ModBlocks.IRONWOOD_LEAVES.get());
        event.register(treeColor(LINDEN),       ModBlocks.LINDEN_LEAVES.get());
        event.register(treeColor(MAHOGANY),     ModBlocks.MAHOGANY_LEAVES.get());
        event.register(treeColor(MAPLE),        ModBlocks.MAPLE_LEAVES.get());
        event.register(treeColor(MYRRH),        ModBlocks.MYRRH_LEAVES.get());
        event.register(treeColor(OAK),          Blocks.OAK_LEAVES);
        event.register(treeColor(PINE),         ModBlocks.PINE_LEAVES.get());
        event.register(treeColor(REDWOOD),      ModBlocks.REDWOOD_LEAVES.get());
        event.register(treeColor(SENTINAL),     ModBlocks.SENTINAL_LEAVES.get());
        event.register(treeColor(SOLDIER_PINE), ModBlocks.SOLDIER_PINE_LEAVES.get());
        event.register(treeColor(WILLOW),       ModBlocks.WILLOW_LEAVES.get());
        event.register(treeColor(WORMTREE),     ModBlocks.WORMTREE_LEAVES.get());

        event.register(treeColor(NIGHTWOOD),        ModBlocks.NIGHTWOOD_LEAVES.get());
        event.register(treeColor(PURPLEHEART),        ModBlocks.PURPLEHEART_LEAVES.get());
        event.register(treeColor(TIGERWOOD),        ModBlocks.TIGERWOOD_LEAVES.get());
        event.register(treeColor(SANDALWOOD),        ModBlocks.SANDALWOOD_LEAVES.get());
        event.register(treeColor(SANDBEGGAR),        ModBlocks.SANDBEGGAR_LEAVES.get());
        event.register(treeColor(APRICOT),        ModBlocks.APRICOT_LEAVES.get());
        event.register(treeColor(BLACKTHORN),        ModBlocks.BLACKTHORN_LEAVES.get());
        event.register(treeColor(RED_CHERRY),        ModBlocks.RED_CHERRY_LEAVES.get());
        event.register(treeColor(BLACK_CHERRY),      ModBlocks.BLACK_CHERRY_LEAVES.get());
        event.register(treeColor(WHITE_CHERRY),      ModBlocks.WHITE_CHERRY_LEAVES.get());
        event.register(treeColor(CRABAPPLE),        ModBlocks.CRABAPPLE_LEAVES.get());
        event.register(treeColor(DATE_PALM),        ModBlocks.DATE_PALM_LEAVES.get());
        event.register(treeColor(FIG),        ModBlocks.FIG_LEAVES.get());
        event.register(treeColor(LEMON),        ModBlocks.LEMON_LEAVES.get());
        event.register(treeColor(LIME),        ModBlocks.LIME_LEAVES.get());
        event.register(treeColor(OLIVE),        ModBlocks.OLIVE_LEAVES.get());
        event.register(treeColor(ORANGE),        ModBlocks.ORANGE_LEAVES.get());
        event.register(treeColor(PEACH),        ModBlocks.PEACH_LEAVES.get());
        event.register(treeColor(PEAR),        ModBlocks.PEAR_LEAVES.get());
        event.register(treeColor(PERSIMMON),        ModBlocks.PERSIMMON_LEAVES.get());
        event.register(treeColor(PINK_IVORY),        ModBlocks.PINK_IVORY_LEAVES.get());
        event.register(treeColor(PLUM),        ModBlocks.PLUM_LEAVES.get());
        event.register(treeColor(POMEGRANATE),        ModBlocks.POMEGRANATE_LEAVES.get());
        event.register(treeColor(PRUNE),        ModBlocks.PRUNE_LEAVES.get());
        event.register(treeColor(ALMOND),        ModBlocks.ALMOND_LEAVES.get());
        event.register(treeColor(NUTMEG),        ModBlocks.NUTMEG_LEAVES.get());
        event.register(treeColor(HEMLOCK),        ModBlocks.HEMLOCK_LEAVES.get());

        event.register(treeColor(HRANNA), ModBlocks.HRANNA.get());

        event.register((state, level, pos, tintIndex) -> 0xFFFFFF, ModBlocks.WEIRWOOD_LEAVES.get());

        event.register(
                (state, level, pos, tintIndex) ->
                        bc.getColor(Blocks.SHORT_GRASS.defaultBlockState(),
                                level, pos, tintIndex),
                ModBlocks.DEVILGRASS.get(),
                ModBlocks.WHEATGRASS.get(),
                ModBlocks.IVY.get(),
                ModBlocks.NETTLE.get()
        );
    }

    public static int getFoliageSeasonColor() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> SPRING_FOLIAGE;
            case SUMMER -> SUMMER_FOLIAGE;
            case AUTUMN -> AUTUMN_FOLIAGE;
            case WINTER -> WINTER_FOLIAGE;
        };
    }

    public static int getGrassSeasonColor() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> SPRING_GRASS;
            case SUMMER -> SUMMER_GRASS;
            case AUTUMN -> AUTUMN_GRASS;
            case WINTER -> WINTER_GRASS;
        };
    }

    public static float getSeasonBlend() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SUMMER -> 0.0f;
            default     -> SEASON_BLEND;
        };
    }

    private static final net.got.climate.LatitudeClimateConfig LATITUDE_CONFIG =
            net.got.climate.LatitudeClimateConfig.get();

    private static final int DEAD_GRASS_LINE_MAP_X0 = LATITUDE_CONFIG.deadGrassMapX0();
    private static final int DEAD_GRASS_LINE_ROW_X0  = LATITUDE_CONFIG.deadGrassRowX0();
    private static final int DEAD_GRASS_LINE_MAP_X1  = LATITUDE_CONFIG.deadGrassMapX1();
    private static final int DEAD_GRASS_LINE_ROW_X1  = LATITUDE_CONFIG.deadGrassRowX1();

    private static final float DEAD_GRASS_FADE_ROWS = LATITUDE_CONFIG.deadGrassFadeRows();

    public static final int DEAD_GRASS_COLOR_DARK  = LATITUDE_CONFIG.deadGrassColorDark();
    public static final int DEAD_GRASS_COLOR_LIGHT = LATITUDE_CONFIG.deadGrassColorLight();

    public static float getDeadGrassBlend(int worldX, int worldZ) {
        float mapX = worldX / (float) net.got.worldgen.BiomemapLoader.MAP_SCALE
                + net.got.worldgen.BiomemapLoader.getWidth() * 0.5f;
        float mapY = worldZ / (float) net.got.worldgen.BiomemapLoader.MAP_SCALE
                + net.got.worldgen.BiomemapLoader.getHeight() * 0.5f;

        float t = net.minecraft.util.Mth.clamp(
                (mapX - DEAD_GRASS_LINE_MAP_X0) / (float) (DEAD_GRASS_LINE_MAP_X1 - DEAD_GRASS_LINE_MAP_X0),
                0f, 1f);
        float thresholdRow = DEAD_GRASS_LINE_ROW_X0 + (DEAD_GRASS_LINE_ROW_X1 - DEAD_GRASS_LINE_ROW_X0) * t;

        float rowsNorth = thresholdRow - mapY;
        return net.minecraft.util.Mth.clamp(rowsNorth / DEAD_GRASS_FADE_ROWS, 0f, 1f);
    }

    private static final double GRASS_VARIATION_SCALE = 0.02;
    private static final float  GRASS_VARIATION_STRENGTH = 0.10f;

    public static float getGrassPatchVariation(int x, int z) {
        double n = net.got.worldgen.SimplexNoise.noise(
                x * GRASS_VARIATION_SCALE, z * GRASS_VARIATION_SCALE);
        return 1f + (float) n * GRASS_VARIATION_STRENGTH;
    }

    private static float getGrassPatchNoise01(int x, int z) {
        double n = net.got.worldgen.SimplexNoise.noise(
                x * GRASS_VARIATION_SCALE, z * GRASS_VARIATION_SCALE);
        return (float) (n * 0.5 + 0.5);
    }

    private static final float DEAD_GRASS_PATCH_CONTRAST = 0.35f;

    public static int getDeadGrassColor(int x, int z) {
        float t = getGrassPatchNoise01(x, z);
        float softened = 0.5f + (t - 0.5f) * DEAD_GRASS_PATCH_CONTRAST;
        return blendColors(DEAD_GRASS_COLOR_DARK, DEAD_GRASS_COLOR_LIGHT, softened);
    }

    public static int applyBrightness(int color, float factor) {
        int r = (int) net.minecraft.util.Mth.clamp(((color >> 16) & 0xFF) * factor, 0f, 255f);
        int g = (int) net.minecraft.util.Mth.clamp(((color >>  8) & 0xFF) * factor, 0f, 255f);
        int b = (int) net.minecraft.util.Mth.clamp(( color        & 0xFF) * factor, 0f, 255f);
        return (r << 16) | (g << 8) | b;
    }

    public static int blendColors(int base, int target, float t) {
        int br = (base   >> 16) & 0xFF, bg = (base   >>  8) & 0xFF, bb =  base          & 0xFF;
        int tr = (target >> 16) & 0xFF, tg = (target >>  8) & 0xFF, tb =  target        & 0xFF;
        return ((int)(br+(tr-br)*t) << 16) | ((int)(bg+(tg-bg)*t) << 8) | (int)(bb+(tb-bb)*t);
    }

    private SeasonFoliageColorProvider() {}
}