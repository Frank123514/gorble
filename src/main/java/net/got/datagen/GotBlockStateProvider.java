package net.got.datagen;

import net.got.GotMod;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;

import java.util.Objects;

/**
 * Generates blockstate JSONs and block/item model JSONs for every GOT block.
 *
 * NeoForge 21.4 (MC 1.21.4): the old neoforge BlockStateProvider and
 * ItemModelProvider from net.neoforged.neoforge.client.model.generators are
 * completely removed. Everything now goes through the vanilla
 * net.minecraft.client.data.models.ModelProvider with its
 * registerModels(BlockModelGenerators, ItemModelGenerators) callback.
 *
 * Item model generation (previously GotItemModelProvider) is folded into this
 * class via the itemModels parameter — see registerWoodItems() etc.
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
    }

    // ── Wood blocks ───────────────────────────────────────────────────

    private void registerWoodBlocks(BlockModelGenerators g) {
        for (String w : WOOD_TYPES) {
            // Logs: side texture = {w}_log, end = {w}_log_top
            g.createRotatedPillarWithHorizontalVariant(
                    block(w + "_log"),
                    ModelTemplates.CUBE_COLUMN,
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL,
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log_top"))
            );
            // Wood (bark): side == end == {w}_log (same texture all round)
            g.createRotatedPillarWithHorizontalVariant(
                    block(w + "_wood"),
                    ModelTemplates.CUBE_COLUMN,
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL,
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(w + "_log"))
                            .put(TextureSlot.END, modBlock(w + "_log"))
            );
            // Stripped log
            g.createRotatedPillarWithHorizontalVariant(
                    block("stripped_" + w + "_log"),
                    ModelTemplates.CUBE_COLUMN,
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL,
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log_top"))
            );
            // Stripped wood (bark all sides)
            g.createRotatedPillarWithHorizontalVariant(
                    block("stripped_" + w + "_wood"),
                    ModelTemplates.CUBE_COLUMN,
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL,
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock("stripped_" + w + "_log"))
                            .put(TextureSlot.END, modBlock("stripped_" + w + "_log"))
            );

            // Planks, leaves, sapling
            g.createTrivialCube(block(w + "_planks"));
            g.createTrivialBlock(block(w + "_leaves"), TexturedModel.LEAVES);
            g.createPlant(block(w + "_sapling"), block(w + "_sapling"),
                    BlockModelGenerators.TintState.NOT_TINTED);

            // Stairs, slab
            g.createStairs(block(w + "_stairs"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"), modBlock(w + "_planks"));
            g.createSlab(block(w + "_slab"),
                    modBlock(w + "_planks"), modBlock(w + "_planks"), modBlock(w + "_planks"));

            // Fence, fence gate
            g.createFence(block(w + "_fence"), block(w + "_planks"));
            g.createFenceGate(block(w + "_fence_gate"), block(w + "_planks"));

            // Pressure plate, button
            g.createPressurePlate(block(w + "_pressure_plate"), block(w + "_planks"));
            g.createSimpleButton(block(w + "_button"), block(w + "_planks"));

            // Door, trapdoor
            g.createDoor(block(w + "_door"));
            g.createTrapdoor(block(w + "_trapdoor"));
        }
    }

    // ── Stone blocks ──────────────────────────────────────────────────

    private void registerStoneBlocks(BlockModelGenerators g) {
        for (String region : STONE_REGIONS) {
            // Pillar (RotatedPillarBlock): side = {region}_pillar, end = {region}_pillar_top
            g.createRotatedPillarWithHorizontalVariant(
                    block(region + "_pillar"),
                    ModelTemplates.CUBE_COLUMN,
                    ModelTemplates.CUBE_COLUMN_HORIZONTAL,
                    new TextureMapping()
                            .put(TextureSlot.SIDE, modBlock(region + "_pillar"))
                            .put(TextureSlot.END, modBlock(region + "_pillar_top"))
            );

            // Rock: button + pressure plate only
            String rock = region + "_rock";
            g.createSimpleButton(block(rock + "_button"), block(rock));
            g.createPressurePlate(block(rock + "_pressure_plate"), block(rock));

            // All solid base subtypes + slab / stairs / wall
            for (String pattern : STONE_SUBTYPES) {
                String base = pattern.replace("{r}", region);
                g.createTrivialCube(block(base));
                g.createSlab(block(base + "_slab"),
                        modBlock(base), modBlock(base), modBlock(base));
                g.createStairs(block(base + "_stairs"),
                        modBlock(base), modBlock(base), modBlock(base));
                g.createWall(block(base + "_wall"), modBlock(base));
            }
        }
    }

    // ── Ores ──────────────────────────────────────────────────────────

    private void registerOres(BlockModelGenerators g) {
        for (String ore : ORES) {
            g.createTrivialCube(block(ore));
        }
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