package net.got.datagen;

import net.got.GotMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Generates block_tags JSONs for every GOT block.
 */
public class GotBlockTagsProvider extends BlockTagsProvider {

    public GotBlockTagsProvider(PackOutput output,
                                CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GotMod.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addWoodTags();
        addStoneTags();
        addOreTags();
    }

    // ─────────────────────────────────────────────────────────────────
    // Wood tags
    // ─────────────────────────────────────────────────────────────────

    private void addWoodTags() {
        String[] woods = GotBlockStateProvider.WOOD_TYPES;

        // Collect block arrays per category
        Block[] logs = stream(woods, w -> block(w + "_log"),
                w -> block(w + "_wood"),
                w -> block("stripped_" + w + "_log"),
                w -> block("stripped_" + w + "_wood")).toArray(Block[]::new);
        Block[] planks          = blocks(woods, w -> block(w + "_planks"));
        Block[] leaves          = blocks(woods, w -> block(w + "_leaves"));
        Block[] saplings        = blocks(woods, w -> block(w + "_sapling"));
        Block[] stairs          = blocks(woods, w -> block(w + "_stairs"));
        Block[] slabs           = blocks(woods, w -> block(w + "_slab"));
        Block[] fences          = blocks(woods, w -> block(w + "_fence"));
        Block[] fenceGates      = blocks(woods, w -> block(w + "_fence_gate"));
        Block[] pressurePlates  = blocks(woods, w -> block(w + "_pressure_plate"));
        Block[] buttons         = blocks(woods, w -> block(w + "_button"));
        Block[] doors           = blocks(woods, w -> block(w + "_door"));
        Block[] trapdoors       = blocks(woods, w -> block(w + "_trapdoor"));

        tag(BlockTags.LOGS).add(logs);
        tag(BlockTags.LOGS_THAT_BURN).add(logs);
        tag(BlockTags.LEAVES).add(leaves);
        tag(BlockTags.SAPLINGS).add(saplings);
        tag(BlockTags.PLANKS).add(planks);
        tag(BlockTags.WOODEN_STAIRS).add(stairs);
        tag(BlockTags.WOODEN_SLABS).add(slabs);
        tag(BlockTags.WOODEN_FENCES).add(fences);
        tag(BlockTags.FENCE_GATES).add(fenceGates);
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(pressurePlates);
        tag(BlockTags.WOODEN_BUTTONS).add(buttons);
        tag(BlockTags.WOODEN_DOORS).add(doors);
        tag(BlockTags.WOODEN_TRAPDOORS).add(trapdoors);

        // Mineable
        Block[] allWoodBlocks = concat(logs, planks, leaves, saplings, stairs, slabs,
                fences, fenceGates, pressurePlates, buttons, doors, trapdoors);
        tag(BlockTags.MINEABLE_WITH_AXE).add(allWoodBlocks);
    }

    // ─────────────────────────────────────────────────────────────────
    // Stone / regional rock tags
    // ─────────────────────────────────────────────────────────────────

    private void addStoneTags() {
        String[] regions = GotBlockStateProvider.STONE_REGIONS;
        String[] subtypePats = {
                "{r}_rock", "{r}_brick", "cracked_{r}_brick", "mossy_{r}_brick",
                "{r}_cobblestone", "mossy_{r}_cobblestone", "polished_{r}_rock"
        };
        String[] derivativeSuffixes = { "", "_slab", "_stairs", "_wall" };
        String[] rockOnlySuffixes   = { "_button", "_pressure_plate" };

        java.util.List<Block> allStone = new java.util.ArrayList<>();

        for (String region : regions) {
            allStone.add(block(region + "_pillar"));

            for (String pat : subtypePats) {
                String base = pat.replace("{r}", region);
                allStone.add(block(base));
                allStone.add(block(base + "_slab"));
                allStone.add(block(base + "_stairs"));
                allStone.add(block(base + "_wall"));
            }
            allStone.add(block(region + "_rock_button"));
            allStone.add(block(region + "_rock_pressure_plate"));
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(allStone.toArray(Block[]::new));
    }

    // ─────────────────────────────────────────────────────────────────
    // Ore tags
    // ─────────────────────────────────────────────────────────────────

    private void addOreTags() {
        // Vanilla tool-tier requirements
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(block("copper_ore"), block("tin_ore"),
                        block("amber_ore"),  block("topaz_ore"));

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(block("silver_ore"),   block("amethyst_ore"),
                        block("opal_ore"),     block("ruby_ore"),
                        block("sapphire_ore"), block("dragonglass"));

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(block("valyrian_ore"));

        // Mineable/pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(block("copper_ore"),  block("tin_ore"),
                        block("amber_ore"),   block("topaz_ore"),
                        block("silver_ore"),  block("amethyst_ore"),
                        block("opal_ore"),    block("ruby_ore"),
                        block("sapphire_ore"),block("dragonglass"),
                        block("valyrian_ore"));

        // NeoForge ore tags
        tag(Tags.Blocks.ORES)
                .add(block("copper_ore"),  block("tin_ore"),
                        block("amber_ore"),   block("topaz_ore"),
                        block("silver_ore"),  block("amethyst_ore"),
                        block("opal_ore"),    block("ruby_ore"),
                        block("sapphire_ore"),block("dragonglass"),
                        block("valyrian_ore"));

        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(block("copper_ore"),  block("tin_ore"),
                        block("amber_ore"),   block("topaz_ore"),
                        block("silver_ore"),  block("amethyst_ore"),
                        block("opal_ore"),    block("ruby_ore"),
                        block("sapphire_ore"),block("dragonglass"),
                        block("valyrian_ore"));
    }

    // ─────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────

    private Block block(String name) {
        return Objects.requireNonNull(
                BuiltInRegistries.BLOCK.getValue(
                        ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                "Unknown block for tags: " + name);
    }

    /** Map a single transform over all wood types. */
    private Block[] blocks(String[] woods, java.util.function.Function<String, Block> fn) {
        return Arrays.stream(woods).map(fn).toArray(Block[]::new);
    }

    /** Stream multiple transforms over all wood types and concat. */
    @SafeVarargs
    private Stream<Block> stream(String[] woods, java.util.function.Function<String, Block>... fns) {
        return Arrays.stream(fns).flatMap(fn -> Arrays.stream(woods).map(fn));
    }

    @SafeVarargs
    private Block[] concat(Block[]... arrays) {
        return Arrays.stream(arrays).flatMap(Arrays::stream).toArray(Block[]::new);
    }
}