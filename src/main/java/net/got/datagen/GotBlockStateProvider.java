package net.got.datagen;

import net.got.GotMod;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

/**
 * Generates block state JSONs and block model JSONs for every GOT block.
 */
public class GotBlockStateProvider extends BlockStateProvider {

    static final String[] WOOD_TYPES = {
            "weirwood", "aspen", "alder", "pine", "fir", "sentinal", "ironwood",
            "beech", "soldier_pine", "ash", "hawthorn", "blackbark", "bloodwood",
            "blue_mahoe", "cottonwood", "black_cottonwood", "cinnamon", "clove",
            "ebony", "elm", "cedar", "apple", "goldenheart", "linden", "mahogany",
            "maple", "myrrh", "redwood", "chestnut", "willow", "wormtree"
    };

    static final String[] STONE_REGIONS = {
            "crownlands", "dorne", "iron_islands", "north", "reach",
            "riverlands", "stormlands", "vale", "westerlands"
    };

    private static final String[] STONE_SUBTYPES = {
            "{r}_rock", "{r}_brick", "cracked_{r}_brick", "mossy_{r}_brick",
            "{r}_cobblestone", "mossy_{r}_cobblestone", "polished_{r}_rock"
    };

    private static final String[] ORES = {
            "copper_ore", "tin_ore", "amber_ore", "topaz_ore",
            "silver_ore", "amethyst_ore", "opal_ore", "ruby_ore",
            "sapphire_ore", "dragonglass", "valyrian_ore"
    };

    public GotBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GotMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerWoodBlocks();
        registerStoneBlocks();
        registerOres();
    }

    // ── Wood blocks ───────────────────────────────────────────────────

    private void registerWoodBlocks() {
        for (String w : WOOD_TYPES) {
            gotAxisBlock(w + "_log",
                    gotBlock(w + "_log"),
                    gotBlock(w + "_log_top"));
            gotAxisBlock(w + "_wood",
                    gotBlock(w + "_log"),
                    gotBlock(w + "_log"));          // bark: side == top
            gotAxisBlock("stripped_" + w + "_log",
                    gotBlock("stripped_" + w + "_log"),
                    gotBlock("stripped_" + w + "_log_top"));
            gotAxisBlock("stripped_" + w + "_wood",
                    gotBlock("stripped_" + w + "_log"),
                    gotBlock("stripped_" + w + "_log"));  // bark all sides: side == top

            simpleBlock(block(w + "_planks"),
                    models().cubeAll(w + "_planks", gotBlock(w + "_planks")));

            simpleBlock(block(w + "_leaves"),
                    models().singleTexture(w + "_leaves",
                            mcLoc("block/leaves"),
                            "all", gotBlock(w + "_leaves")));

            simpleBlock(block(w + "_sapling"),
                    models().cross(w + "_sapling", gotBlock(w + "_sapling"))
                            .renderType("cutout"));

            stairsBlock((StairBlock)  block(w + "_stairs"),       gotBlock(w + "_planks"));
            slabBlock  ((SlabBlock)   block(w + "_slab"),         gotBlock(w + "_planks"), gotBlock(w + "_planks"));
            fenceBlock ((FenceBlock)  block(w + "_fence"),        gotBlock(w + "_planks"));
            itemModels().fenceInventory(w + "_fence", gotBlock(w + "_planks"));
            fenceGateBlock((FenceGateBlock)     block(w + "_fence_gate"),     gotBlock(w + "_planks"));
            pressurePlateBlock((PressurePlateBlock) block(w + "_pressure_plate"), gotBlock(w + "_planks"));
            buttonBlock((ButtonBlock) block(w + "_button"),       gotBlock(w + "_planks"));
            itemModels().buttonInventory(w + "_button", gotBlock(w + "_planks"));

            doorBlockWithRenderType((DoorBlock) block(w + "_door"),
                    gotBlock(w + "_door_bottom"), gotBlock(w + "_door_top"), "cutout");

            trapdoorBlockWithRenderType((TrapDoorBlock) block(w + "_trapdoor"),
                    gotBlock(w + "_trapdoor"), true, "cutout");
        }
    }

    // ── Stone blocks ──────────────────────────────────────────────────

    private void registerStoneBlocks() {
        for (String region : STONE_REGIONS) {
            // Pillar — RotatedPillarBlock, textures must be named {region}_pillar and {region}_pillar_top
            // logBlock(RotatedPillarBlock) auto-derives textures from block name: side=block_name, end=block_name_top
            logBlock((RotatedPillarBlock) block(region + "_pillar"));

            // Rock-only: button + pressure plate
            String rock = region + "_rock";
            buttonBlock((ButtonBlock)       block(rock + "_button"),         gotBlock(rock));
            itemModels().buttonInventory(rock + "_button", gotBlock(rock));
            pressurePlateBlock((PressurePlateBlock) block(rock + "_pressure_plate"), gotBlock(rock));

            // All solid base subtypes + their derived slab / stairs / wall
            for (String pattern : STONE_SUBTYPES) {
                String base = pattern.replace("{r}", region);
                simpleBlock(block(base));
                slabBlock  ((SlabBlock)  block(base + "_slab"),   gotBlock(base), gotBlock(base));
                stairsBlock((StairBlock) block(base + "_stairs"), gotBlock(base));
                wallBlock  ((WallBlock)  block(base + "_wall"),   gotBlock(base));
                itemModels().wallInventory(base + "_wall", gotBlock(base));
            }
        }
    }

    // ── Ores ──────────────────────────────────────────────────────────

    private void registerOres() {
        for (String ore : ORES) {
            simpleBlock(block(ore));
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────

    /**
     * Axis-rotating block (log / wood / stripped log).
     * These extend Block (not RotatedPillarBlock) and have their own AXIS property.
     */
    private void gotAxisBlock(String name, ResourceLocation side, ResourceLocation end) {
        Block     b  = block(name);
        ModelFile mY = models().cubeColumn(name, side, end);
        ModelFile mH = models().cubeColumnHorizontal(name + "_horizontal", side, end);

        getVariantBuilder(b)
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(mY).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X)
                .modelForState().modelFile(mH).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(mH).addModel();
    }

    private Block block(String name) {
        return Objects.requireNonNull(
                BuiltInRegistries.BLOCK.getValue(
                        ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                "Unknown block: " + name);
    }

    /** Returns a got: block texture path. */
    private ResourceLocation gotBlock(String name) {
        return ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "block/" + name);
    }
}