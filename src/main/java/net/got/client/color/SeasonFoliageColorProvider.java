package net.got.client.color;

import net.got.climate.GotSeason;
import net.got.climate.SeasonManager;
import net.got.init.GotModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import javax.annotation.Nullable;

/**
 * Provides per-tree seasonal foliage tint colors.
 *
 * Each tree species has its own set of season colors so that, for example,
 * maple turns deep crimson-red in autumn while aspen goes bright gold, rather
 * than every tree sharing one global tint.
 *
 * Weirwood is deliberately excluded — it never changes color.
 *
 * The BiomeColorsMixin still blends grass/foliage at the biome level using
 * the global season colors below; per-tree tinting is applied on top of that
 * via the BlockColor handlers registered here.
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
    // SUMMER is always the "natural" base color of that tree.

    // Deciduous — vivid standouts
    private static final int[] MAPLE         = { 0x82C830, 0x3C8C18, 0xC01408, 0x7A6050 };  // crimson-red autumn
    private static final int[] ASPEN         = { 0x90CC30, 0x50A020, 0xD4C010, 0x8C8060 };  // bright gold autumn
    private static final int[] GOLDENHEART   = { 0x94D030, 0x58A820, 0xD0AC18, 0x908060 };  // deep amber-gold autumn
    private static final int[] BLOODWOOD     = { 0x78882C, 0x3C6C18, 0xA01010, 0x786060 };  // dark crimson autumn

    // Deciduous — warm oranges, all distinct
    private static final int[] ALDER         = { 0x84C830, 0x48981C, 0xC07014, 0x7A6858 };  // amber-orange
    private static final int[] BEECH         = { 0x88CC2C, 0x4C9C1C, 0xC48810, 0x7C6C58 };  // golden-orange
    private static final int[] CHESTNUT      = { 0x80C42C, 0x489018, 0xC0600C, 0x787058 };  // burnt orange
    private static final int[] COTTONWOOD    = { 0x84CC2C, 0x4A9C1C, 0xC09014, 0x7C7058 };  // soft orange-gold
    private static final int[] BLACK_COTTON  = { 0x80C82C, 0x489818, 0xBC8810, 0x787058 };  // cooler orange-gold
    private static final int[] HAWTHORN      = { 0x80C42C, 0x4C9018, 0xC04C10, 0x786858 };  // russet-orange
    private static final int[] APPLE         = { 0x8CCC30, 0x50A01C, 0xC07010, 0x7C6C58 };  // warm orange-red

    // Deciduous — yellows and bronzes
    private static final int[] ELM           = { 0x88CC2C, 0x4C9C1C, 0xA89814, 0x7C7458 };  // yellow-bronze
    private static final int[] ASH           = { 0x88CC2C, 0x4C9C1C, 0xB0A010, 0x7C7458 };  // pale yellow-gold
    private static final int[] LINDEN        = { 0x8CD02C, 0x509C1C, 0xB0A010, 0x807860 };  // warm soft yellow
    private static final int[] WILLOW        = { 0x84C82C, 0x4A981C, 0x98941C, 0x7C7458 };  // muted yellow-green

    // Deciduous — russets and dark tones
    private static final int[] IRONWOOD      = { 0x80C02C, 0x4A8C1C, 0x886018, 0x787060 };  // bronze-brown
    private static final int[] EBONY         = { 0x74B828, 0x408018, 0x784C10, 0x6C6858 };  // dark bronze
    private static final int[] BLACKBARK     = { 0x78BC28, 0x448418, 0x844010, 0x706858 };  // dark russet

    // Tropical/exotic
    private static final int[] BLUE_MAHOE    = { 0x6CC864, 0x389C4C, 0x48944C, 0x648070 };  // stays greenish
    private static final int[] MAHOGANY      = { 0x7CC02C, 0x488818, 0x983C10, 0x786860 };  // dark reddish
    private static final int[] CINNAMON      = { 0x80C42C, 0x4C9018, 0xBC6C14, 0x7A6C58 };  // spicy warm orange
    private static final int[] CLOVE         = { 0x7CC02C, 0x488818, 0xA45C10, 0x786858 };  // dark amber
    private static final int[] MYRRH         = { 0x80C42C, 0x4A9018, 0xB07814, 0x7A7058 };  // warm amber-gold

    // Other deciduous
    private static final int[] WORMTREE      = { 0x80C02C, 0x4C8C1C, 0x787C10, 0x747060 };  // olive-green autumn

    // Evergreens — stay green all year, minor seasonal shift
    private static final int[] PINE          = { 0x4CA830, 0x2C7818, 0x306020, 0x245018 };
    private static final int[] FIR           = { 0x50AC30, 0x307C18, 0x346420, 0x285418 };
    private static final int[] SENTINAL      = { 0x3CA870, 0x206848, 0x245840, 0x184830 };
    private static final int[] SOLDIER_PINE  = { 0x5CA028, 0x387010, 0x3C6018, 0x2C5010 };
    private static final int[] CEDAR         = { 0x48A840, 0x287828, 0x2C6030, 0x205020 };
    private static final int[] REDWOOD       = { 0x50A038, 0x2C7020, 0x306028, 0x245018 };

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static int pick(int[] colors) {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> colors[0];
            case SUMMER -> colors[1];
            case AUTUMN -> colors[2];
            case WINTER -> colors[3];
        };
    }

    /** Builds a BlockColor handler for one specific tree's color array. */
    private static BlockColor treeColor(int[] colors) {
        return (state, level, pos, tintIndex) -> {
            if (level == null || pos == null) return colors[1]; // fallback: summer
            int biome  = getBiomeFoliage(level, pos);
            int season = pick(colors);
            float blend = getSeasonBlend();
            return blend == 0f ? biome : blendColors(biome, season, blend);
        };
    }

    // ── Registration ─────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors bc = event.getBlockColors();

        // Vivid deciduous
        event.register(treeColor(MAPLE),        GotModBlocks.MAPLE_LEAVES.get());
        event.register(treeColor(ASPEN),         GotModBlocks.ASPEN_LEAVES.get());
        event.register(treeColor(GOLDENHEART),   GotModBlocks.GOLDENHEART_LEAVES.get());
        event.register(treeColor(BLOODWOOD),     GotModBlocks.BLOODWOOD_LEAVES.get());

        // Warm orange deciduous
        event.register(treeColor(ALDER),         GotModBlocks.ALDER_LEAVES.get());
        event.register(treeColor(BEECH),         GotModBlocks.BEECH_LEAVES.get());
        event.register(treeColor(CHESTNUT),      GotModBlocks.CHESTNUT_LEAVES.get());
        event.register(treeColor(COTTONWOOD),    GotModBlocks.COTTONWOOD_LEAVES.get());
        event.register(treeColor(BLACK_COTTON),  GotModBlocks.BLACK_COTTONWOOD_LEAVES.get());
        event.register(treeColor(HAWTHORN),      GotModBlocks.HAWTHORN_LEAVES.get());
        event.register(treeColor(APPLE),         GotModBlocks.APPLE_LEAVES.get());

        // Yellows and bronzes
        event.register(treeColor(ELM),           GotModBlocks.ELM_LEAVES.get());
        event.register(treeColor(ASH),           GotModBlocks.ASH_LEAVES.get());
        event.register(treeColor(LINDEN),        GotModBlocks.LINDEN_LEAVES.get());
        event.register(treeColor(WILLOW),        GotModBlocks.WILLOW_LEAVES.get());

        // Russets and dark tones
        event.register(treeColor(IRONWOOD),      GotModBlocks.IRONWOOD_LEAVES.get());
        event.register(treeColor(EBONY),         GotModBlocks.EBONY_LEAVES.get());
        event.register(treeColor(BLACKBARK),     GotModBlocks.BLACKBARK_LEAVES.get());

        // Tropical/exotic
        event.register(treeColor(BLUE_MAHOE),    GotModBlocks.BLUE_MAHOE_LEAVES.get());
        event.register(treeColor(MAHOGANY),      GotModBlocks.MAHOGANY_LEAVES.get());
        event.register(treeColor(CINNAMON),      GotModBlocks.CINNAMON_LEAVES.get());
        event.register(treeColor(CLOVE),         GotModBlocks.CLOVE_LEAVES.get());
        event.register(treeColor(MYRRH),         GotModBlocks.MYRRH_LEAVES.get());

        // Other deciduous
        event.register(treeColor(WORMTREE),      GotModBlocks.WORMTREE_LEAVES.get());

        // Evergreens
        event.register(treeColor(PINE),          GotModBlocks.PINE_LEAVES.get());
        event.register(treeColor(FIR),           GotModBlocks.FIR_LEAVES.get());
        event.register(treeColor(SENTINAL),      GotModBlocks.SENTINAL_LEAVES.get());
        event.register(treeColor(SOLDIER_PINE),  GotModBlocks.SOLDIER_PINE_LEAVES.get());
        event.register(treeColor(CEDAR),         GotModBlocks.CEDAR_LEAVES.get());
        event.register(treeColor(REDWOOD),       GotModBlocks.REDWOOD_LEAVES.get());

        // Weirwood — intentionally NOT registered here so it keeps vanilla/default behavior

        // Grass blocks
        event.register(
                (state, level, pos, tintIndex) ->
                        bc.getColor(net.minecraft.world.level.block.Blocks.SHORT_GRASS.defaultBlockState(),
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
        int br = (base   >> 16) & 0xFF;
        int bg = (base   >>  8) & 0xFF;
        int bb =  base          & 0xFF;
        int tr = (target >> 16) & 0xFF;
        int tg = (target >>  8) & 0xFF;
        int tb =  target        & 0xFF;
        int r  = (int)(br + (tr - br) * t);
        int g  = (int)(bg + (tg - bg) * t);
        int b  = (int)(bb + (tb - bb) * t);
        return (r << 16) | (g << 8) | b;
    }

    private static int getBiomeFoliage(@Nullable BlockAndTintGetter level,
                                       @Nullable BlockPos pos) {
        if (level == null || pos == null) return SUMMER_FOLIAGE;
        return net.minecraft.client.Minecraft.getInstance()
                .getBlockColors()
                .getColor(net.minecraft.world.level.block.Blocks.OAK_LEAVES.defaultBlockState(),
                        level, pos, 0);
    }

    private SeasonFoliageColorProvider() {}
}
