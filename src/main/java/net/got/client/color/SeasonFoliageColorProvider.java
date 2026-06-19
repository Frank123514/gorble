package net.got.client.color;

import net.got.climate.SeasonManager;
import net.got.init.GotModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * Seasonal foliage tinting using desaturated (greyscale) leaf textures.
 *
 * Each tree's texture is greyscale. In summer the tint is set to the tree's
 * original average color so it looks exactly like the original texture.
 * In spring, autumn and winter a distinct seasonal tint is applied instead.
 *
 * Weirwood is excluded — its red texture is intentionally non-tinted.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class SeasonFoliageColorProvider {

    // ── Global season colors (used by BiomeColorsMixin for grass + generic foliage) ──
    public static final int SPRING_FOLIAGE = 0x80C050;
    public static final int SUMMER_FOLIAGE = 0x48B518;
    public static final int AUTUMN_FOLIAGE = 0xC07820;
    public static final int WINTER_FOLIAGE = 0x7A6B52;

    public static final int SPRING_GRASS   = 0x91C844;
    public static final int SUMMER_GRASS   = 0x5DB535;
    public static final int AUTUMN_GRASS   = 0xA09030;
    public static final int WINTER_GRASS   = 0x8C7D5E;

    public static final float SEASON_BLEND = 0.70f;

    // ── Per-tree season colors ────────────────────────────────────────────────
    // Format: { SPRING, SUMMER, AUTUMN, WINTER }
    // SUMMER values are sampled from the original texture so summer = unchanged look.
    // Spring is a fresh bright green. Autumn varies by species. Winter is desaturated/brown.

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
    private static final int[] BURL               = { 0x708850, 0x507040, 0xB07020, 0x686058 };
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

    // Hranna: blood red in spring (flowers bloom), grass-tinted in summer,
    // deep brown in autumn (dry season), grass-tinted in winter
    // Hranna: blood red in spring (flowers bloom), grass-tinted in summer,
    // deep brown in autumn (dry season), grass-tinted in winter
    // Summer = SUMMER_GRASS (0x5DB535), Winter = WINTER_GRASS (0x8C7D5E)
    private static final int[] HRANNA             = { 0x8B1010, 0x5DB535, 0x5C3208, 0x8C7D5E };


    // ── Helpers ───────────────────────────────────────────────────────────────

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

    // ── Registration ─────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors bc = event.getBlockColors();

        event.register(treeColor(ALDER),        GotModBlocks.ALDER_LEAVES.get());
        event.register(treeColor(APPLE),        GotModBlocks.APPLE_LEAVES.get());
        event.register(treeColor(ASH),          GotModBlocks.ASH_LEAVES.get());
        event.register(treeColor(ASPEN),        GotModBlocks.ASPEN_LEAVES.get());
        event.register(treeColor(BEECH),        GotModBlocks.BEECH_LEAVES.get());
        event.register(treeColor(BLACK_COTTON), GotModBlocks.BLACK_COTTONWOOD_LEAVES.get());
        event.register(treeColor(BLACKBARK),    GotModBlocks.BLACKBARK_LEAVES.get());
        event.register(treeColor(BLOODWOOD),    GotModBlocks.BLOODWOOD_LEAVES.get());
        event.register(treeColor(BLUE_MAHOE),   GotModBlocks.BLUE_MAHOE_LEAVES.get());
        event.register(treeColor(CEDAR),        GotModBlocks.CEDAR_LEAVES.get());
        event.register(treeColor(CHESTNUT),     GotModBlocks.CHESTNUT_LEAVES.get());
        event.register(treeColor(CINNAMON),     GotModBlocks.CINNAMON_LEAVES.get());
        event.register(treeColor(CLOVE),        GotModBlocks.CLOVE_LEAVES.get());
        event.register(treeColor(COTTONWOOD),   GotModBlocks.COTTONWOOD_LEAVES.get());
        event.register(treeColor(EBONY),        GotModBlocks.EBONY_LEAVES.get());
        event.register(treeColor(ELM),          GotModBlocks.ELM_LEAVES.get());
        event.register(treeColor(FIR),          GotModBlocks.FIR_LEAVES.get());
        event.register(treeColor(GOLDENHEART),  GotModBlocks.GOLDENHEART_LEAVES.get());
        event.register(treeColor(HAWTHORN),     GotModBlocks.HAWTHORN_LEAVES.get());
        event.register(treeColor(IRONWOOD),     GotModBlocks.IRONWOOD_LEAVES.get());
        event.register(treeColor(LINDEN),       GotModBlocks.LINDEN_LEAVES.get());
        event.register(treeColor(MAHOGANY),     GotModBlocks.MAHOGANY_LEAVES.get());
        event.register(treeColor(MAPLE),        GotModBlocks.MAPLE_LEAVES.get());
        event.register(treeColor(MYRRH),        GotModBlocks.MYRRH_LEAVES.get());
        event.register(treeColor(OAK),          Blocks.OAK_LEAVES);
        event.register(treeColor(PINE),         GotModBlocks.PINE_LEAVES.get());
        event.register(treeColor(REDWOOD),      GotModBlocks.REDWOOD_LEAVES.get());
        event.register(treeColor(SENTINAL),     GotModBlocks.SENTINAL_LEAVES.get());
        event.register(treeColor(SOLDIER_PINE), GotModBlocks.SOLDIER_PINE_LEAVES.get());
        event.register(treeColor(WILLOW),       GotModBlocks.WILLOW_LEAVES.get());
        event.register(treeColor(WORMTREE),     GotModBlocks.WORMTREE_LEAVES.get());

        event.register(treeColor(NIGHTWOOD),        GotModBlocks.NIGHTWOOD_LEAVES.get());
        event.register(treeColor(PURPLEHEART),        GotModBlocks.PURPLEHEART_LEAVES.get());
        event.register(treeColor(TIGERWOOD),        GotModBlocks.TIGERWOOD_LEAVES.get());
        event.register(treeColor(BURL),        GotModBlocks.BURL_LEAVES.get());
        event.register(treeColor(SANDALWOOD),        GotModBlocks.SANDALWOOD_LEAVES.get());
        event.register(treeColor(SANDBEGGAR),        GotModBlocks.SANDBEGGAR_LEAVES.get());
        event.register(treeColor(APRICOT),        GotModBlocks.APRICOT_LEAVES.get());
        event.register(treeColor(BLACKTHORN),        GotModBlocks.BLACKTHORN_LEAVES.get());
        event.register(treeColor(RED_CHERRY),        GotModBlocks.RED_CHERRY_LEAVES.get());
        event.register(treeColor(BLACK_CHERRY),      GotModBlocks.BLACK_CHERRY_LEAVES.get());
        event.register(treeColor(WHITE_CHERRY),      GotModBlocks.WHITE_CHERRY_LEAVES.get());
        event.register(treeColor(CRABAPPLE),        GotModBlocks.CRABAPPLE_LEAVES.get());
        event.register(treeColor(DATE_PALM),        GotModBlocks.DATE_PALM_LEAVES.get());
        event.register(treeColor(FIG),        GotModBlocks.FIG_LEAVES.get());
        event.register(treeColor(LEMON),        GotModBlocks.LEMON_LEAVES.get());
        event.register(treeColor(LIME),        GotModBlocks.LIME_LEAVES.get());
        event.register(treeColor(OLIVE),        GotModBlocks.OLIVE_LEAVES.get());
        event.register(treeColor(ORANGE),        GotModBlocks.ORANGE_LEAVES.get());
        event.register(treeColor(PEACH),        GotModBlocks.PEACH_LEAVES.get());
        event.register(treeColor(PEAR),        GotModBlocks.PEAR_LEAVES.get());
        event.register(treeColor(PERSIMMON),        GotModBlocks.PERSIMMON_LEAVES.get());
        event.register(treeColor(PINK_IVORY),        GotModBlocks.PINK_IVORY_LEAVES.get());
        event.register(treeColor(PLUM),        GotModBlocks.PLUM_LEAVES.get());
        event.register(treeColor(POMEGRANATE),        GotModBlocks.POMEGRANATE_LEAVES.get());
        event.register(treeColor(PRUNE),        GotModBlocks.PRUNE_LEAVES.get());
        event.register(treeColor(ALMOND),        GotModBlocks.ALMOND_LEAVES.get());
        event.register(treeColor(NUTMEG),        GotModBlocks.NUTMEG_LEAVES.get());
        event.register(treeColor(HEMLOCK),        GotModBlocks.HEMLOCK_LEAVES.get());

        // Hranna — seasonal tint: blood red spring, grass summer, deep brown autumn, grass winter
        event.register(treeColor(HRANNA), GotModBlocks.HRANNA.get());

        // Weirwood — registered with a white (0xFFFFFF) no-op tint so neither
        // seasonal nor biome foliage color is ever multiplied onto the texture.
        event.register((state, level, pos, tintIndex) -> 0xFFFFFF, GotModBlocks.WEIRWOOD_LEAVES.get());

        // Grass blocks
        event.register(
                (state, level, pos, tintIndex) ->
                        bc.getColor(Blocks.SHORT_GRASS.defaultBlockState(),
                                level, pos, tintIndex),
                GotModBlocks.DEVILGRASS.get(),
                GotModBlocks.PIPERS_GRASS.get(),
                GotModBlocks.WHEATGRASS.get()
        );
    }

    // ── Public helpers used by BiomeColorsMixin ───────────────────────────────

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

    public static int blendColors(int base, int target, float t) {
        int br = (base   >> 16) & 0xFF, bg = (base   >>  8) & 0xFF, bb =  base          & 0xFF;
        int tr = (target >> 16) & 0xFF, tg = (target >>  8) & 0xFF, tb =  target        & 0xFF;
        return ((int)(br+(tr-br)*t) << 16) | ((int)(bg+(tg-bg)*t) << 8) | (int)(bb+(tb-bb)*t);
    }

    private SeasonFoliageColorProvider() {}
}