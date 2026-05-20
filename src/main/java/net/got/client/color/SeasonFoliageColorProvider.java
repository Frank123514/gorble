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
 * Overrides foliage (leaf) and grass tint colors based on the current GoT season.
 *
 * <p>Color palette per season:
 * <ul>
 *   <li><b>Spring</b> – fresh yellow-green  {@code 0x80C050}</li>
 *   <li><b>Summer</b> – deep rich green      {@code 0x48B518} (vanilla-ish)</li>
 *   <li><b>Autumn</b> – warm orange-brown    {@code 0xC07820}</li>
 *   <li><b>Winter</b> – desaturated grey-green {@code 0x8CA888}</li>
 * </ul>
 *
 * <p>Registration is handled here via {@link RegisterColorHandlersEvent}; no
 * changes are needed in {@link net.got.client.ClientSetup}.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class SeasonFoliageColorProvider {

    // ── Per-season foliage colors ────────────────────────────────────────────
    private static final int SPRING_FOLIAGE = 0x80C050; // yellow-green
    private static final int SUMMER_FOLIAGE = 0x48B518; // rich green
    private static final int AUTUMN_FOLIAGE = 0xC07820; // orange-brown
    private static final int WINTER_FOLIAGE = 0x7A6B52; // dead bare brown

    // ── Per-season grass colors ──────────────────────────────────────────────
    private static final int SPRING_GRASS   = 0x91C844; // bright spring green
    private static final int SUMMER_GRASS   = 0x5DB535; // lush summer
    private static final int AUTUMN_GRASS   = 0xA09030; // dry golden
    private static final int WINTER_GRASS   = 0x8C7D5E; // dead dry straw

    // ── Transition smoothing ─────────────────────────────────────────────────
    /**
     * Blend factor used to lerp the biome's own color toward the season color.
     * 0.0 = pure biome color, 1.0 = pure season color.
     *
     * <p>Keeping this below 1.0 means jungle / desert biomes still look
     * somewhat distinct from temperate ones even in the same season.
     */
    /**
     * Blend factor used to lerp the biome's own color toward the season color.
     * 0.0 = pure biome color, 1.0 = pure season color.
     *
     * <p>Exposed as {@code public} so {@link net.got.mixin.BiomeColorsMixin} can
     * reuse it without duplicating the value.
     */
    public static final float SEASON_BLEND = 0.70f;

    // ────────────────────────────────────────────────────────────────────────
    // BlockColor implementation
    // ────────────────────────────────────────────────────────────────────────

    /**
     * Returns the biome foliage color for GoT leaf blocks.
     *
     * <p>No extra blending is needed here: {@link net.got.mixin.BiomeColorsMixin}
     * already intercepts {@code BiomeColors.getAverageFoliageColor} globally and
     * blends in the season color before this handler ever receives the value.
     * Blending a second time would over-saturate the season tint.
     */
    private static final BlockColor FOLIAGE_COLOR = (state, level, pos, tintIndex) ->
            getFoliageBiomeColor(level, pos);

    /**
     * Returns the biome grass color for GoT grass blocks.
     *
     * <p>Same as {@link #FOLIAGE_COLOR}: the mixin has already applied the season
     * blend at the {@code BiomeColors} level, so we just pass the value through.
     */
    private static final BlockColor GRASS_COLOR = (state, level, pos, tintIndex) ->
            getGrassBiomeColor(level, pos);

    // ────────────────────────────────────────────────────────────────────────
    // Registration
    // ────────────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

        // ── Register season foliage color for every custom GoT leaves block ──
        event.register(FOLIAGE_COLOR,
                GotModBlocks.WEIRWOOD_LEAVES.get(),
                GotModBlocks.ASPEN_LEAVES.get(),
                GotModBlocks.ALDER_LEAVES.get(),
                GotModBlocks.PINE_LEAVES.get(),
                GotModBlocks.FIR_LEAVES.get(),
                GotModBlocks.SENTINAL_LEAVES.get(),
                GotModBlocks.IRONWOOD_LEAVES.get(),
                GotModBlocks.BEECH_LEAVES.get(),
                GotModBlocks.SOLDIER_PINE_LEAVES.get(),
                GotModBlocks.ASH_LEAVES.get(),
                GotModBlocks.HAWTHORN_LEAVES.get(),
                GotModBlocks.BLACKBARK_LEAVES.get(),
                GotModBlocks.BLOODWOOD_LEAVES.get(),
                GotModBlocks.BLUE_MAHOE_LEAVES.get(),
                GotModBlocks.COTTONWOOD_LEAVES.get(),
                GotModBlocks.BLACK_COTTONWOOD_LEAVES.get(),
                GotModBlocks.CINNAMON_LEAVES.get(),
                GotModBlocks.CLOVE_LEAVES.get(),
                GotModBlocks.EBONY_LEAVES.get(),
                GotModBlocks.ELM_LEAVES.get(),
                GotModBlocks.CEDAR_LEAVES.get(),
                GotModBlocks.APPLE_LEAVES.get(),
                GotModBlocks.GOLDENHEART_LEAVES.get(),
                GotModBlocks.LINDEN_LEAVES.get(),
                GotModBlocks.MAHOGANY_LEAVES.get(),
                GotModBlocks.MAPLE_LEAVES.get(),
                GotModBlocks.MYRRH_LEAVES.get(),
                GotModBlocks.REDWOOD_LEAVES.get(),
                GotModBlocks.CHESTNUT_LEAVES.get(),
                GotModBlocks.WILLOW_LEAVES.get(),
                GotModBlocks.WORMTREE_LEAVES.get()
        );

        // ── Register season grass color for custom GoT short-grass blocks ────
        // The mixin already adjusts BiomeColors.getAverageGrassColor globally,
        // so the color returned here for SHORT_GRASS is already season-tinted.
        // We just delegate to the vanilla tint to get the correct biome gradient
        // without blending a second time.
        event.register(
                (state, level, pos, tintIndex) ->
                        blockColors.getColor(
                                net.minecraft.world.level.block.Blocks.SHORT_GRASS.defaultBlockState(),
                                level, pos, tintIndex),
                GotModBlocks.DEVILGRASS.get(),
                GotModBlocks.PIPERS_GRASS.get(),
                GotModBlocks.WHEATGRASS.get()
        );
    }

    // ────────────────────────────────────────────────────────────────────────
    // Helpers
    // ────────────────────────────────────────────────────────────────────────

    /** Season target color for leaves. */
    public static int getFoliageSeasonColor() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> SPRING_FOLIAGE;
            case SUMMER -> SUMMER_FOLIAGE;
            case AUTUMN -> AUTUMN_FOLIAGE;
            case WINTER -> WINTER_FOLIAGE;
        };
    }

    /** Season target color for grass. */
    public static int getGrassSeasonColor() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SPRING -> SPRING_GRASS;
            case SUMMER -> SUMMER_GRASS;
            case AUTUMN -> AUTUMN_GRASS;
            case WINTER -> WINTER_GRASS;
        };
    }

    /**
     * Looks up the biome-supplied foliage color for the given position.
     * Falls back to {@link #SUMMER_FOLIAGE} if level or pos is null
     * (e.g., when rendering as an inventory item).
     */
    private static int getFoliageBiomeColor(@Nullable BlockAndTintGetter level,
                                            @Nullable BlockPos pos) {
        if (level == null || pos == null) return SUMMER_FOLIAGE;
        return net.minecraft.client.Minecraft.getInstance()
                .getBlockColors()
                .getColor(net.minecraft.world.level.block.Blocks.OAK_LEAVES.defaultBlockState(),
                        level, pos, 0);
    }

    /**
     * Looks up the biome-supplied grass color for the given position.
     * Falls back to {@link #SUMMER_GRASS} if level or pos is null.
     */
    private static int getGrassBiomeColor(@Nullable BlockAndTintGetter level,
                                          @Nullable BlockPos pos) {
        if (level == null || pos == null) return SUMMER_GRASS;
        return net.minecraft.client.Minecraft.getInstance()
                .getBlockColors()
                .getColor(net.minecraft.world.level.block.Blocks.SHORT_GRASS.defaultBlockState(),
                        level, pos, 0);
    }

    /**
     * Blend factor for the current season.
     * Returns {@code 0} in Summer so the raw biome color is used unchanged —
     * summer is the baseline / "normal" look. Other seasons blend toward their
     * season color at {@link #SEASON_BLEND}.
     *
     * <p>Exposed as {@code public} so {@link net.got.mixin.BiomeColorsMixin} can
     * use the season-aware value instead of the constant.
     */
    public static float getSeasonBlend() {
        return switch (SeasonManager.getCurrentSeason()) {
            case SUMMER -> 0.0f; // pure biome color — summer is the normal reference
            default     -> SEASON_BLEND;
        };
    }

    /**
     *
     * <p>Exposed as {@code public} so {@link net.got.mixin.BiomeColorsMixin} can
     * reuse it without duplicating the implementation.
     *
     * @param base   source color (biome)
     * @param target target color (season)
     * @param t      blend factor in [0,1] toward target
     */
    public static int blendColors(int base, int target, float t) {
        int br = (base   >> 16) & 0xFF;
        int bg = (base   >>  8) & 0xFF;
        int bb =  base          & 0xFF;
        int tr = (target >> 16) & 0xFF;
        int tg = (target >>  8) & 0xFF;
        int tb =  target        & 0xFF;
        int r = (int)(br + (tr - br) * t);
        int g = (int)(bg + (tg - bg) * t);
        int b = (int)(bb + (tb - bb) * t);
        return (r << 16) | (g << 8) | b;
    }

    private SeasonFoliageColorProvider() {}
}