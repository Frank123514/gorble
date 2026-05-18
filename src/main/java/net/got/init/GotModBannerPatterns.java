package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

/**
 * Registry keys and tag keys for all Game of Thrones Great House banner patterns.
 *
 * The actual BannerPattern entries are data-driven — no DeferredRegister needed.
 * JSON files live in: data/got/banner_pattern/<house>.json
 *
 * Each ResourceKey corresponds to a pattern JSON.
 * Each TagKey is used by a BannerPatternItem (registered in GotModItems) so the
 * loom can find and apply the pattern when a player holds the pattern item.
 */
public class GotModBannerPatterns {

    // ── ResourceKeys (point at data/got/banner_pattern/<house>.json) ─────────

    public static final ResourceKey<BannerPattern> STARK =
            key("stark");
    public static final ResourceKey<BannerPattern> LANNISTER =
            key("lannister");
    public static final ResourceKey<BannerPattern> TARGARYEN =
            key("targaryen");
    public static final ResourceKey<BannerPattern> BARATHEON =
            key("baratheon");
    public static final ResourceKey<BannerPattern> GREYJOY =
            key("greyjoy");
    public static final ResourceKey<BannerPattern> TYRELL =
            key("tyrell");
    public static final ResourceKey<BannerPattern> MARTELL =
            key("martell");
    public static final ResourceKey<BannerPattern> TULLY =
            key("tully");
    public static final ResourceKey<BannerPattern> ARRYN =
            key("arryn");
    public static final ResourceKey<BannerPattern> BOLTON =
            key("bolton");

    // ── TagKeys (used by BannerPatternItem so the loom can apply the pattern) ─

    public static final TagKey<BannerPattern> STARK_PATTERN_TAG =
            patternTag("stark");
    public static final TagKey<BannerPattern> LANNISTER_PATTERN_TAG =
            patternTag("lannister");
    public static final TagKey<BannerPattern> TARGARYEN_PATTERN_TAG =
            patternTag("targaryen");
    public static final TagKey<BannerPattern> BARATHEON_PATTERN_TAG =
            patternTag("baratheon");
    public static final TagKey<BannerPattern> GREYJOY_PATTERN_TAG =
            patternTag("greyjoy");
    public static final TagKey<BannerPattern> TYRELL_PATTERN_TAG =
            patternTag("tyrell");
    public static final TagKey<BannerPattern> MARTELL_PATTERN_TAG =
            patternTag("martell");
    public static final TagKey<BannerPattern> TULLY_PATTERN_TAG =
            patternTag("tully");
    public static final TagKey<BannerPattern> ARRYN_PATTERN_TAG =
            patternTag("arryn");
    public static final TagKey<BannerPattern> BOLTON_PATTERN_TAG =
            patternTag("bolton");

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static ResourceKey<BannerPattern> key(String name) {
        return ResourceKey.create(
                Registries.BANNER_PATTERN,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name));
    }

    private static TagKey<BannerPattern> patternTag(String name) {
        return TagKey.create(
                Registries.BANNER_PATTERN,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pattern_item/" + name));
    }
}
