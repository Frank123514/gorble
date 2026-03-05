package net.got.datagen;

import net.got.GotMod;
import net.got.init.GotModBlocks;
import net.got.init.GotModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Generates loot table JSONs for every GOT block.
 *
 * Rules applied:
 *  - Logs, wood, stripped logs, planks    → drop self
 *  - Leaves                               → drop sapling (with fortune chance), self with silk touch
 *  - Sapling                              → drop self
 *  - Stairs, fence, fence gate, pressure plate, button → drop self
 *  - Slab                                 → drop 1 (or 2 for double slab)
 *  - Stone blocks                         → drop self
 *  - Metal ores (copper, tin, silver)     → drop raw material (fortune); self with silk touch
 *  - Gem ores                             → drop gem (fortune); self with silk touch
 *  - Valyrian ore                         → drop raw_valyrian_steel (fortune); self with silk touch
 */
public class GotLootTableProvider extends LootTableProvider {

    public GotLootTableProvider(PackOutput output,
                                CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Set.of(),
                List.of(new SubProviderEntry(GotBlockLoot::new, LootContextParamSets.BLOCK)),
                lookupProvider);
    }

    // ─────────────────────────────────────────────────────────────────
    // Inner block loot sub-provider
    // ─────────────────────────────────────────────────────────────────

    static class GotBlockLoot extends BlockLootSubProvider {

        protected GotBlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            generateWoodLoot();
            generateStoneLoot();
            generateOreLoot();
        }

        // ── Wood ──────────────────────────────────────────────────────

        private void generateWoodLoot() {
            for (String w : GotBlockStateProvider.WOOD_TYPES) {
                // Self-drop blocks
                dropSelf(block(w + "_log"));
                dropSelf(block(w + "_wood"));
                dropSelf(block("stripped_" + w + "_log"));
                dropSelf(block("stripped_" + w + "_wood"));
                dropSelf(block(w + "_planks"));
                dropSelf(block(w + "_sapling"));
                dropSelf(block(w + "_stairs"));
                dropSelf(block(w + "_fence"));
                dropSelf(block(w + "_fence_gate"));
                dropSelf(block(w + "_pressure_plate"));
                dropSelf(block(w + "_button"));

                // Slab: drops 1 or 2 depending on block-half state
                add(block(w + "_slab"), this::createSlabItemTable);

                // Door: drops itself as item
                add(block(w + "_door"), this::createDoorTable);

                // Trapdoor: drops itself
                dropSelf(block(w + "_trapdoor"));

                // Leaves: drop self with silk touch/shears, otherwise chance sapling + sticks
                add(block(w + "_leaves"),
                        b -> createLeavesDrops(b, block(w + "_sapling"),
                                NORMAL_LEAVES_SAPLING_CHANCES));
            }
        }

        // ── Stone / regional rock ──────────────────────────────────────

        private void generateStoneLoot() {
            String[] subtypePats = {
                    "{r}_rock", "{r}_brick", "cracked_{r}_brick", "mossy_{r}_brick",
                    "{r}_cobblestone", "mossy_{r}_cobblestone", "polished_{r}_rock"
            };

            for (String region : GotBlockStateProvider.STONE_REGIONS) {
                // Pillar
                dropSelf(block(region + "_pillar"));

                // Rock button + pressure plate
                dropSelf(block(region + "_rock_button"));
                dropSelf(block(region + "_rock_pressure_plate"));

                for (String pat : subtypePats) {
                    String base = pat.replace("{r}", region);
                    dropSelf(block(base));
                    add(block(base + "_slab"), this::createSlabItemTable);
                    dropSelf(block(base + "_stairs"));
                    dropSelf(block(base + "_wall"));
                }
            }
        }

        // ── Ores ──────────────────────────────────────────────────────

        private void generateOreLoot() {
            // Metal ores → raw material (silk touch drops ore)
            add(block("copper_ore"),   b -> createOreDrop(b, GotModItems.RAW_COPPER.get()));
            add(block("tin_ore"),      b -> createOreDrop(b, GotModItems.RAW_TIN.get()));
            add(block("silver_ore"),   b -> createOreDrop(b, GotModItems.RAW_SILVER.get()));
            add(block("valyrian_ore"), b -> createOreDrop(b, GotModItems.RAW_VALYRIAN_STEEL.get()));

            // Gem ores → gem drop (silk touch drops ore)
            add(block("amber_ore"),    b -> createOreDrop(b, GotModItems.AMBER.get()));
            add(block("topaz_ore"),    b -> createOreDrop(b, GotModItems.TOPAZ.get()));
            add(block("amethyst_ore"), b -> createOreDrop(b, GotModItems.AMETHYST.get()));
            add(block("opal_ore"),     b -> createOreDrop(b, GotModItems.OPAL.get()));
            add(block("ruby_ore"),     b -> createOreDrop(b, GotModItems.RUBY.get()));
            add(block("sapphire_ore"), b -> createOreDrop(b, GotModItems.SAPPHIRE.get()));
            add(block("dragonglass"),  b -> createOreDrop(b, GotModItems.DRAGONGLASS_SHARD.get()));
        }

        // ── Helpers ───────────────────────────────────────────────────

        /** Doors drop only from the lower half (vanilla behaviour). */
        public LootTable.Builder createDoorTable(Block block) {
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER)))
                            .add(LootItem.lootTableItem(block))
                            .when(ExplosionCondition.survivesExplosion()));
        }

        private Block block(String name) {
            return Objects.requireNonNull(
                    BuiltInRegistries.BLOCK.getValue(
                            ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name)),
                    "Unknown block for loot: " + name);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return GotModBlocks.REGISTRY.getEntries()
                    .stream()
                    .map(DeferredHolder::get)
                    .collect(Collectors.toList());
        }
    }
}