package net.got.datagen;

import net.got.GotMod;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;

import java.util.Objects;

/**
 * Generates blockstate JSONs and block/item model JSONs for every GOT block.
 *
 * MC 1.21.4 BlockModelGenerators API changes from 1.21.1:
 *  - createRotatedPillarWithHorizontalVariant: the (Block, ModelTemplate, ModelTemplate, TextureMapping)
 *    overload is gone. Must pre-create models and pass their ResourceLocations.
 *  - createFence / createFenceGate / createPressurePlate / createWall now take ResourceLocation texture args.
 *  - createSimpleButton renamed to createButton.
 *  - TexturedModel.LEAVES is a TexturedModel.Provider; use g.createTrivialBlock(block, TexturedModel.LEAVES).
 *  - BlockModelGenerators.TintState was removed in 1.21.4.
 *    createPlant now takes a boolean: false = not tinted, true = tinted.
 */
public class GotBlockStateProvider extends ModelProvider {

    static final String[] WOOD_TYPES = {
            "weirwood", "aspen", "alder", "pine", "fir", "sentinal", "ironwood",
            "beech", "soldier_pine", "ash", "hawthorn", "blackbark", "bloodwood",
            "blue_mahoe", "cottonwood", "black_cottonwood", "cinnamon", "clove",
            "ebony", "elm", "cedar", "apple", "goldenheart", "linden", "mahogany",
            "maple", "myrrh", "redwood", "chestnut", "willow", "wormtree"
    };

    static final String[] STONE_REGIONS = {
            "basalt", "grey_granite", "limestone", "sandstone", "red_sandstone", "slate", "flint",
            "oily_black", "fused_black", "marble"
    };

    private static final String[] STONE_SUBTYPES = {
            "{r}_rock", "{r}_brick", "cracked_{r}_brick", "mossy_{r}_brick",
            "{r}_cobblestone", "mossy_{r}_cobblestone", "smooth_{r}_rock"
    };

    private static final String[] ORES = {
            "copper_ore", "tin_ore", "amber_ore", "topaz_ore",
            "silver_ore", "amethyst_ore", "opal_ore", "ruby_ore",
            "sapphire_ore", "dragonglass", "valyrian_ore"
    };

    public GotBlockStateProvider(PackOutput output) {
        super(output, GotMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerWoodBlocks(blockModels);
        registerStoneBlocks(blockModels);
        registerOres(blockModels);
        registerWetlandBlocks(blockModels);
    }

    // ── Wood blocks ───────────────────────────────────────────────────

    private void registerWoodBlocks(BlockModelGenerators g) {
        for (String w : WOOD_TYPES) {
            // 1.21.4: must pre-create pillar models and pass their ResourceLocations
            ResourceLocation logUpright = ModelTemplates.CUBE_COLUMN.create(
                    block(w + "_log"),
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log_top")),
                    g.modelOutput);
            ResourceLocation logHoriz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
                    block(w + "_log"), "_horizontal",
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log_top")),
                    g.modelOutput);
            g.createRotatedPillarWithHorizontalVariant(block(w + "_log"), logUpright, logHoriz);

            ResourceLocation woodUpright = ModelTemplates.CUBE_COLUMN.create(
                    block(w + "_wood"),
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log")),
                    g.modelOutput);
            ResourceLocation woodHoriz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
                    block(w + "_wood"), "_horizontal",
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log")),
                    g.modelOutput);
            g.createRotatedPillarWithHorizontalVariant(block(w + "_wood"), woodUpright, woodHoriz);

            ResourceLocation slogUpright = ModelTemplates.CUBE_COLUMN.create(
                    block("stripped_" + w + "_log"),
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log_top")),
                    g.modelOutput);
            ResourceLocation slogHoriz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
                    block("stripped_" + w + "_log"), "_horizontal",
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log_top")),
                    g.modelOutput);
            g.createRotatedPillarWithHorizontalVariant(block("stripped_" + w + "_log"), slogUpright, slogHoriz);

            ResourceLocation swoodUpright = ModelTemplates.CUBE_COLUMN.create(
                    block("stripped_" + w + "_wood"),
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log")),
                    g.modelOutput);
            ResourceLocation swoodHoriz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
                    block("stripped_" + w + "_wood"), "_horizontal",
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log")),
                    g.modelOutput);
            g.createRotatedPillarWithHorizontalVariant(block("stripped_" + w + "_wood"), swoodUpright, swoodHoriz);

            // Planks, leaves, sapling
            g.createTrivialCube(block(w + "_planks"));
            g.createTrivialBlock(block(w + "_leaves"), TexturedModel.LEAVES);
            g.createPlant(block(w + "_sapling"), block(w + "_sapling"), BlockModelGenerators.PlantType.NOT_TINTED);

            // Stairs, slab
            g.createStairs(block(w + "_stairs"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"), modBlock(w + "_planks"));
            g.createSlab(block(w + "_slab"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"), modBlock(w + "_planks"));

            // Fence, fence gate — 1.21.4 takes ResourceLocation texture args
            g.createFence(block(w + "_fence"), modBlock(w + "_planks"), modBlock(w + "_planks"));
            g.createFenceGate(block(w + "_fence_gate"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"), false);

            // Pressure plate, button — 1.21.4 takes ResourceLocation args; createSimpleButton -> createButton
            g.createPressurePlate(block(w + "_pressure_plate"), modBlock(w + "_planks"), modBlock(w + "_planks"));
            g.createButton(block(w + "_button"), modBlock(w + "_planks"), modBlock(w + "_planks"));

            // Door, trapdoor
            g.createDoor(block(w + "_door"));
            g.createTrapdoor(block(w + "_trapdoor"));
        }
    }

    // ── Stone blocks ──────────────────────────────────────────────────

    private void registerStoneBlocks(BlockModelGenerators g) {
        for (String region : STONE_REGIONS) {
            ResourceLocation pillarUpright = ModelTemplates.CUBE_COLUMN.create(
                    block(region + "_pillar"),
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(region + "_pillar"))
                            .put(TextureSlot.END, modBlock(region + "_pillar_top")),
                    g.modelOutput);
            ResourceLocation pillarHoriz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.createWithSuffix(
                    block(region + "_pillar"), "_horizontal",
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(region + "_pillar"))
                            .put(TextureSlot.END, modBlock(region + "_pillar_top")),
                    g.modelOutput);
            g.createRotatedPillarWithHorizontalVariant(block(region + "_pillar"), pillarUpright, pillarHoriz);

            // Rock: button + pressure plate only
            String rock = region + "_rock";
            g.createButton(block(rock + "_button"), modBlock(rock), modBlock(rock));
            g.createPressurePlate(block(rock + "_pressure_plate"), modBlock(rock), modBlock(rock));

            // All solid base subtypes + slab / stairs / wall
            for (String pattern : STONE_SUBTYPES) {
                String base = pattern.replace("{r}", region);
                g.createTrivialCube(block(base));
                g.createSlab(block(base + "_slab"),
                        modBlock(base), modBlock(base), modBlock(base));
                g.createStairs(block(base + "_stairs"),
                        modBlock(base), modBlock(base), modBlock(base));
                // 1.21.4 createWall takes (Block, ResourceLocation, ResourceLocation, ResourceLocation)
                g.createWall(block(base + "_wall"), modBlock(base), modBlock(base), modBlock(base));
            }
        }
    }

    // ── Ores ──────────────────────────────────────────────────────────

    private void registerOres(BlockModelGenerators g) {
        for (String ore : ORES) {
            g.createTrivialCube(block(ore));
        }
    }

    // ── Wetland plants & terrain ───────────────────────────────────────

    /**
     * Registers block-state / model JSON entries for the three new wetland
     * additions: wheatgrass, reeds, and quagmire.
     *
     * <ul>
     *   <li><b>Wheatgrass</b> — cross-model plant (same as sapling/flower).</li>
     *   <li><b>Reeds</b> — cross-model plant; age is internal-only so a single
     *       model variant covers all 16 ages (identical to vanilla sugar cane).</li>
     *   <li><b>Quagmire</b> — simple full-cube block.</li>
     * </ul>
     */
    private void registerWetlandBlocks(BlockModelGenerators g) {
        // Wheatgrass — plain cross-model plant, not tinted.
        g.createPlant(block("wheatgrass"), block("wheatgrass"),
                BlockModelGenerators.PlantType.NOT_TINTED);

        // Reeds — cross-model plant.  All 16 age values share the same model,
        // so we register the block-state once and suppress per-age variants.
        g.createPlant(block("reeds"), block("reeds"),
                BlockModelGenerators.PlantType.NOT_TINTED);

        // Quagmire — full opaque cube; textures provided by the resource pack.
        g.createTrivialCube(block("quagmire"));
    }

    // ── Helpers ───────────────────────────────────────────────────────

    private Block block(String name) {
        return Objects.requireNonNull(
                BuiltInRegistries.BLOCK.getValue(
                        ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                "Unknown GOT block: " + name);
    }

    /** Returns a ResourceLocation for a GOT block texture: got:block/{name} */
    private static ResourceLocation modBlock(String name) {
        return ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "block/" + name);
    }
}