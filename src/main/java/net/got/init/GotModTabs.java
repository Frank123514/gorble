package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModTabs {

    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GotMod.MODID);

    /**
     * Safe wrapper: only adds the item to the tab if its registry object
     * is present and produces a valid (count == 1) ItemStack.
     * This prevents the "stack count must be 1" crash when a block/item
     * is declared in GotModBlocks but its ItemBlock was never registered.
     */
    private static void safeAccept(CreativeModeTab.Output output,
                                   Block block) {
        try {
            ItemStack stack = new ItemStack(block);
            if (!stack.isEmpty() && stack.getCount() == 1) {
                output.accept(stack);
            }
        } catch (Exception ignored) {
            // Block has no item form yet — skip silently
        }
    }

    /**
     * Overloaded safe wrapper for Item objects (boats, chest boats, etc.)
     */
    private static void safeAccept(CreativeModeTab.Output output,
                                   Item item) {
        try {
            ItemStack stack = new ItemStack(item);
            if (!stack.isEmpty() && stack.getCount() == 1) {
                output.accept(stack);
            }
        } catch (Exception ignored) {
            // Item not registered yet — skip silently
        }
    }

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 1 — GOT: CARPENTRY
     * All wood types: logs, planks, stairs, slabs, fences, doors,
     * trapdoors, pressure plates, buttons, branches, roofing, signs,
     * hanging signs, boats, and chest boats.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_CARPENTRY =
            REGISTRY.register("got_carpentry", () -> CreativeModeTab.builder()
                    .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.SPAWN_EGGS.identifier())
                    .title(Component.translatable("itemGroup.got.got_carpentry"))
                    .icon(() -> new ItemStack(GotModBlocks.WEIRWOOD_PLANKS.get()))
                    .displayItems((params, output) -> {

                        // ── Acacia (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.ACACIA_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ACACIA_BRANCH.get());
                        safeAccept(output, GotModBlocks.ACACIA_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ACACIA_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ACACIA_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ACACIA_WOOD_STAIRS.get());

                        // ── Alder ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ALDER_LOG.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_WOOD.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALDER_PLANKS.get());
                        safeAccept(output, GotModBlocks.ALDER_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALDER_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_FENCE.get());
                        safeAccept(output, GotModBlocks.ALDER_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ALDER_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ALDER_BUTTON.get());
                        safeAccept(output, GotModBlocks.ALDER_DOOR.get());
                        safeAccept(output, GotModBlocks.ALDER_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ALDER_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_BRANCH.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ALDER_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ALDER_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ALDER_BOAT.get());
                        safeAccept(output, GotModItems.ALDER_CHEST_BOAT.get());

                        // ── Apple ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.APPLE_LOG.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.APPLE_PLANKS.get());
                        safeAccept(output, GotModBlocks.APPLE_STAIRS.get());
                        safeAccept(output, GotModBlocks.APPLE_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_FENCE.get());
                        safeAccept(output, GotModBlocks.APPLE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.APPLE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.APPLE_BUTTON.get());
                        safeAccept(output, GotModBlocks.APPLE_DOOR.get());
                        safeAccept(output, GotModBlocks.APPLE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.APPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.APPLE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.APPLE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.APPLE_BOAT.get());
                        safeAccept(output, GotModItems.APPLE_CHEST_BOAT.get());

                        // ── Ash ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASH_LOG.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_WOOD.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_PLANKS.get());
                        safeAccept(output, GotModBlocks.ASH_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ASH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ASH_BUTTON.get());
                        safeAccept(output, GotModBlocks.ASH_DOOR.get());
                        safeAccept(output, GotModBlocks.ASH_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ASH_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_BRANCH.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ASH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASH_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ASH_BOAT.get());
                        safeAccept(output, GotModItems.ASH_CHEST_BOAT.get());

                        // ── Aspen ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASPEN_LOG.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_WOOD.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASPEN_PLANKS.get());
                        safeAccept(output, GotModBlocks.ASPEN_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASPEN_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_FENCE.get());
                        safeAccept(output, GotModBlocks.ASPEN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ASPEN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ASPEN_BUTTON.get());
                        safeAccept(output, GotModBlocks.ASPEN_DOOR.get());
                        safeAccept(output, GotModBlocks.ASPEN_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ASPEN_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_BRANCH.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ASPEN_BOAT.get());
                        safeAccept(output, GotModItems.ASPEN_CHEST_BOAT.get());

                        // ── Thatch (light and dark) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LIGHT_THATCH.get());
                        safeAccept(output, GotModBlocks.LIGHT_THATCH_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_THATCH_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIGHT_THATCH_WALL.get());
                        safeAccept(output, GotModBlocks.DARK_THATCH.get());
                        safeAccept(output, GotModBlocks.DARK_THATCH_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_THATCH_STAIRS.get());
                        safeAccept(output, GotModBlocks.DARK_THATCH_WALL.get());

                        // ── Bamboo (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BAMBOO_ROOFING.get());
                        safeAccept(output, GotModBlocks.BAMBOO_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BAMBOO_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BAMBOO_ROOFING_WALL.get());

                        // ── Beech ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BEECH_LOG.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_WOOD.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_PLANKS.get());
                        safeAccept(output, GotModBlocks.BEECH_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BEECH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BEECH_BUTTON.get());
                        safeAccept(output, GotModBlocks.BEECH_DOOR.get());
                        safeAccept(output, GotModBlocks.BEECH_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BEECH_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_BRANCH.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BEECH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BEECH_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BEECH_BOAT.get());
                        safeAccept(output, GotModItems.BEECH_CHEST_BOAT.get());

                        // ── Birch (vanilla) ───────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.BIRCH_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BIRCH_BRANCH.get());
                        safeAccept(output, GotModBlocks.BIRCH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BIRCH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BIRCH_WOOD_STAIRS.get());

                        // ── Blackbark ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACKBARK_LOG.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_DOOR.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLACKBARK_BOAT.get());
                        safeAccept(output, GotModItems.BLACKBARK_CHEST_BOAT.get());

                        // ── Black Cottonwood ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLACK_COTTONWOOD_BOAT.get());
                        safeAccept(output, GotModItems.BLACK_COTTONWOOD_CHEST_BOAT.get());

                        // ── Bloodwood ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLOODWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLOODWOOD_BOAT.get());
                        safeAccept(output, GotModItems.BLOODWOOD_CHEST_BOAT.get());

                        // ── Blue Mahoe ────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LOG.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_FENCE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_DOOR.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLUE_MAHOE_BOAT.get());
                        safeAccept(output, GotModItems.BLUE_MAHOE_CHEST_BOAT.get());

                        // ── Cedar ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CEDAR_LOG.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_WOOD.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.CEDAR_PLANKS.get());
                        safeAccept(output, GotModBlocks.CEDAR_STAIRS.get());
                        safeAccept(output, GotModBlocks.CEDAR_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_FENCE.get());
                        safeAccept(output, GotModBlocks.CEDAR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CEDAR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CEDAR_BUTTON.get());
                        safeAccept(output, GotModBlocks.CEDAR_DOOR.get());
                        safeAccept(output, GotModBlocks.CEDAR_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.CEDAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.CEDAR_BOAT.get());
                        safeAccept(output, GotModItems.CEDAR_CHEST_BOAT.get());

                        // ── Cherry (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_WALL.get());

                        // ── Chestnut ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CHESTNUT_LOG.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_WOOD.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_PLANKS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_FENCE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_BUTTON.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_DOOR.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_BRANCH.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.CHESTNUT_BOAT.get());
                        safeAccept(output, GotModItems.CHESTNUT_CHEST_BOAT.get());

                        // ── Cinnamon ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CINNAMON_LOG.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_WOOD.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_PLANKS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_STAIRS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_FENCE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_BUTTON.get());
                        safeAccept(output, GotModBlocks.CINNAMON_DOOR.get());
                        safeAccept(output, GotModBlocks.CINNAMON_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.CINNAMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.CINNAMON_BOAT.get());
                        safeAccept(output, GotModItems.CINNAMON_CHEST_BOAT.get());

                        // ── Clove ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CLOVE_LOG.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_WOOD.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.CLOVE_PLANKS.get());
                        safeAccept(output, GotModBlocks.CLOVE_STAIRS.get());
                        safeAccept(output, GotModBlocks.CLOVE_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_FENCE.get());
                        safeAccept(output, GotModBlocks.CLOVE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CLOVE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CLOVE_BUTTON.get());
                        safeAccept(output, GotModBlocks.CLOVE_DOOR.get());
                        safeAccept(output, GotModBlocks.CLOVE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.CLOVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.CLOVE_BOAT.get());
                        safeAccept(output, GotModItems.CLOVE_CHEST_BOAT.get());

                        // ── Cottonwood ────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.COTTONWOOD_BOAT.get());
                        safeAccept(output, GotModItems.COTTONWOOD_CHEST_BOAT.get());

                        // ── Dark Oak (vanilla) ────────────────────────────────────────
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DARK_OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DARK_OAK_WOOD_STAIRS.get());

                        // ── Ebony ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.EBONY_LOG.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_WOOD.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.EBONY_PLANKS.get());
                        safeAccept(output, GotModBlocks.EBONY_STAIRS.get());
                        safeAccept(output, GotModBlocks.EBONY_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_FENCE.get());
                        safeAccept(output, GotModBlocks.EBONY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.EBONY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.EBONY_BUTTON.get());
                        safeAccept(output, GotModBlocks.EBONY_DOOR.get());
                        safeAccept(output, GotModBlocks.EBONY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.EBONY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_BRANCH.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.EBONY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.EBONY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.EBONY_BOAT.get());
                        safeAccept(output, GotModItems.EBONY_CHEST_BOAT.get());

                        // ── Elm ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ELM_LOG.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_WOOD.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ELM_PLANKS.get());
                        safeAccept(output, GotModBlocks.ELM_STAIRS.get());
                        safeAccept(output, GotModBlocks.ELM_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_FENCE.get());
                        safeAccept(output, GotModBlocks.ELM_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ELM_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ELM_BUTTON.get());
                        safeAccept(output, GotModBlocks.ELM_DOOR.get());
                        safeAccept(output, GotModBlocks.ELM_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ELM_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_BRANCH.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ELM_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ELM_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ELM_BOAT.get());
                        safeAccept(output, GotModItems.ELM_CHEST_BOAT.get());

                        // ── Fir ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FIR_LOG.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_WOOD.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIR_PLANKS.get());
                        safeAccept(output, GotModBlocks.FIR_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIR_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_FENCE.get());
                        safeAccept(output, GotModBlocks.FIR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.FIR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.FIR_BUTTON.get());
                        safeAccept(output, GotModBlocks.FIR_DOOR.get());
                        safeAccept(output, GotModBlocks.FIR_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.FIR_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_BRANCH.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.FIR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.FIR_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.FIR_BOAT.get());
                        safeAccept(output, GotModItems.FIR_CHEST_BOAT.get());

                        // ── Goldenheart ───────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.GOLDENHEART_LOG.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_PLANKS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_STAIRS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_FENCE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_BUTTON.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_DOOR.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_BRANCH.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.GOLDENHEART_BOAT.get());
                        safeAccept(output, GotModItems.GOLDENHEART_CHEST_BOAT.get());

                        // ── Hawthorn ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.HAWTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_PLANKS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_BUTTON.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_DOOR.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_BRANCH.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.HAWTHORN_BOAT.get());
                        safeAccept(output, GotModItems.HAWTHORN_CHEST_BOAT.get());

                        // ── Ironwood ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.IRONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.IRONWOOD_BOAT.get());
                        safeAccept(output, GotModItems.IRONWOOD_CHEST_BOAT.get());

                        // ── Jungle (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.JUNGLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_JUNGLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.JUNGLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_JUNGLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_JUNGLE_WOOD_STAIRS.get());

                        // ── Linden ────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LINDEN_LOG.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_WOOD.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.LINDEN_PLANKS.get());
                        safeAccept(output, GotModBlocks.LINDEN_STAIRS.get());
                        safeAccept(output, GotModBlocks.LINDEN_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_FENCE.get());
                        safeAccept(output, GotModBlocks.LINDEN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.LINDEN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.LINDEN_BUTTON.get());
                        safeAccept(output, GotModBlocks.LINDEN_DOOR.get());
                        safeAccept(output, GotModBlocks.LINDEN_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.LINDEN_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_BRANCH.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.LINDEN_BOAT.get());
                        safeAccept(output, GotModItems.LINDEN_CHEST_BOAT.get());

                        // ── Mahogany ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MAHOGANY_LOG.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_WOOD.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_PLANKS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_FENCE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_BUTTON.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_DOOR.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_BRANCH.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.MAHOGANY_BOAT.get());
                        safeAccept(output, GotModItems.MAHOGANY_CHEST_BOAT.get());

                        // ── Mangrove (vanilla) ────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.MANGROVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MANGROVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.MANGROVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MANGROVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MANGROVE_WOOD_STAIRS.get());

                        // ── Maple ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MAPLE_LOG.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAPLE_PLANKS.get());
                        safeAccept(output, GotModBlocks.MAPLE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAPLE_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_FENCE.get());
                        safeAccept(output, GotModBlocks.MAPLE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MAPLE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MAPLE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MAPLE_DOOR.get());
                        safeAccept(output, GotModBlocks.MAPLE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.MAPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.MAPLE_BOAT.get());
                        safeAccept(output, GotModItems.MAPLE_CHEST_BOAT.get());

                        // ── Myrrh ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MYRRH_LOG.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_WOOD.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.MYRRH_PLANKS.get());
                        safeAccept(output, GotModBlocks.MYRRH_STAIRS.get());
                        safeAccept(output, GotModBlocks.MYRRH_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_FENCE.get());
                        safeAccept(output, GotModBlocks.MYRRH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MYRRH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MYRRH_BUTTON.get());
                        safeAccept(output, GotModBlocks.MYRRH_DOOR.get());
                        safeAccept(output, GotModBlocks.MYRRH_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.MYRRH_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_BRANCH.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.MYRRH_BOAT.get());
                        safeAccept(output, GotModItems.MYRRH_CHEST_BOAT.get());

                        // ── Oak (vanilla) ─────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OAK_ROOFING.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OAK_WOOD_STAIRS.get());

                        // ── Pale Oak (vanilla) ────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PALE_OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PALE_OAK_BRANCH.get());
                        safeAccept(output, GotModBlocks.PALE_OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PALE_OAK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PALE_OAK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PALE_OAK_WOOD_STAIRS.get());

                        // ── Pine ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PINE_LOG.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINE_PLANKS.get());
                        safeAccept(output, GotModBlocks.PINE_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINE_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_FENCE.get());
                        safeAccept(output, GotModBlocks.PINE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PINE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PINE_BUTTON.get());
                        safeAccept(output, GotModBlocks.PINE_DOOR.get());
                        safeAccept(output, GotModBlocks.PINE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PINE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_BRANCH.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PINE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PINE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PINE_BOAT.get());
                        safeAccept(output, GotModItems.PINE_CHEST_BOAT.get());

                        // ── Redwood ───────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.REDWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.REDWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.REDWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.REDWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.REDWOOD_BOAT.get());
                        safeAccept(output, GotModItems.REDWOOD_CHEST_BOAT.get());

                        // ── Sentinal ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SENTINAL_LOG.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_WOOD.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_PLANKS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_STAIRS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_FENCE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_BUTTON.get());
                        safeAccept(output, GotModBlocks.SENTINAL_DOOR.get());
                        safeAccept(output, GotModBlocks.SENTINAL_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.SENTINAL_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_BRANCH.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.SENTINAL_BOAT.get());
                        safeAccept(output, GotModItems.SENTINAL_CHEST_BOAT.get());

                        // ── Soldier Pine ──────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PLANKS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_BUTTON.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_DOOR.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_BRANCH.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.SOLDIER_PINE_BOAT.get());
                        safeAccept(output, GotModItems.SOLDIER_PINE_CHEST_BOAT.get());

                        // ── Spruce (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.SPRUCE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SPRUCE_BRANCH.get());
                        safeAccept(output, GotModBlocks.SPRUCE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SPRUCE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SPRUCE_WOOD_STAIRS.get());

                        // ── Weirwood ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WEIRWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.WEIRWOOD_BOAT.get());
                        safeAccept(output, GotModItems.WEIRWOOD_CHEST_BOAT.get());

                        // ── Willow ────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WILLOW_LOG.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_WOOD.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WILLOW_PLANKS.get());
                        safeAccept(output, GotModBlocks.WILLOW_STAIRS.get());
                        safeAccept(output, GotModBlocks.WILLOW_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_FENCE.get());
                        safeAccept(output, GotModBlocks.WILLOW_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WILLOW_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WILLOW_BUTTON.get());
                        safeAccept(output, GotModBlocks.WILLOW_DOOR.get());
                        safeAccept(output, GotModBlocks.WILLOW_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.WILLOW_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_BRANCH.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.WILLOW_BOAT.get());
                        safeAccept(output, GotModItems.WILLOW_CHEST_BOAT.get());

                        // ── Wormtree ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WORMTREE_LOG.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_WOOD.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_PLANKS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_STAIRS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_FENCE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_BUTTON.get());
                        safeAccept(output, GotModBlocks.WORMTREE_DOOR.get());
                        safeAccept(output, GotModBlocks.WORMTREE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.WORMTREE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_BRANCH.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.WORMTREE_BOAT.get());
                        safeAccept(output, GotModItems.WORMTREE_CHEST_BOAT.get());

                        // ── Nightwood ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.NIGHTWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.NIGHTWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.NIGHTWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.NIGHTWOOD_BOAT.get());
                        safeAccept(output, GotModItems.NIGHTWOOD_CHEST_BOAT.get());

                        // ── Purpleheart ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PURPLEHEART_LOG.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_PLANKS.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_STAIRS.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_FENCE.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_BUTTON.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_DOOR.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_BRANCH.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_ROOFING.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PURPLEHEART_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PURPLEHEART_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PURPLEHEART_BOAT.get());
                        safeAccept(output, GotModItems.PURPLEHEART_CHEST_BOAT.get());

                        // ── Tigerwood ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.TIGERWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.TIGERWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.TIGERWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.TIGERWOOD_BOAT.get());
                        safeAccept(output, GotModItems.TIGERWOOD_CHEST_BOAT.get());


                        // ── Sandalwood ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SANDALWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_BUTTON.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_DOOR.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_BRANCH.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SANDALWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SANDALWOOD_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.SANDALWOOD_BOAT.get());
                        safeAccept(output, GotModItems.SANDALWOOD_CHEST_BOAT.get());

                        // ── Sandbeggar ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SANDBEGGAR_LOG.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_WOOD.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_PLANKS.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_FENCE.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_BUTTON.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_DOOR.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_ROOFING.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SANDBEGGAR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SANDBEGGAR_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.SANDBEGGAR_BOAT.get());
                        safeAccept(output, GotModItems.SANDBEGGAR_CHEST_BOAT.get());

                        // ── Apricot ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.APRICOT_LOG.get());
                        safeAccept(output, GotModBlocks.APRICOT_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_WOOD.get());
                        safeAccept(output, GotModBlocks.APRICOT_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.APRICOT_PLANKS.get());
                        safeAccept(output, GotModBlocks.APRICOT_STAIRS.get());
                        safeAccept(output, GotModBlocks.APRICOT_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_FENCE.get());
                        safeAccept(output, GotModBlocks.APRICOT_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.APRICOT_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.APRICOT_BUTTON.get());
                        safeAccept(output, GotModBlocks.APRICOT_DOOR.get());
                        safeAccept(output, GotModBlocks.APRICOT_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.APRICOT_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_BRANCH.get());
                        safeAccept(output, GotModBlocks.APRICOT_ROOFING.get());
                        safeAccept(output, GotModBlocks.APRICOT_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.APRICOT_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.APRICOT_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.APRICOT_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.APRICOT_BOAT.get());
                        safeAccept(output, GotModItems.APRICOT_CHEST_BOAT.get());

                        // ── Blackthorn ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACKTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_DOOR.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACKTHORN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACKTHORN_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLACKTHORN_BOAT.get());
                        safeAccept(output, GotModItems.BLACKTHORN_CHEST_BOAT.get());

                        // ── Cherry ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.RED_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_PLANKS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_FENCE.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_BUTTON.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_DOOR.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_BUTTON.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_DOOR.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_PLANKS.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_STAIRS.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_FENCE.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_BUTTON.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_DOOR.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHERRY_BRANCH.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_WALL.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.RED_CHERRY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.RED_CHERRY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.RED_CHERRY_BOAT.get());
                        safeAccept(output, GotModItems.RED_CHERRY_CHEST_BOAT.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACK_CHERRY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACK_CHERRY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.BLACK_CHERRY_BOAT.get());
                        safeAccept(output, GotModItems.BLACK_CHERRY_CHEST_BOAT.get());
                        safeAccept(output, Block.byItem(GotModItems.WHITE_CHERRY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WHITE_CHERRY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.WHITE_CHERRY_BOAT.get());
                        safeAccept(output, GotModItems.WHITE_CHERRY_CHEST_BOAT.get());

                        // ── Crabapple ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CRABAPPLE_LOG.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_PLANKS.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_FENCE.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_DOOR.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_BRANCH.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CRABAPPLE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CRABAPPLE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.CRABAPPLE_BOAT.get());
                        safeAccept(output, GotModItems.CRABAPPLE_CHEST_BOAT.get());

                        // ── Date Palm ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.DATE_PALM_LOG.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_WOOD.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_PLANKS.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_STAIRS.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_FENCE.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_BUTTON.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_DOOR.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_BRANCH.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_ROOFING.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.DATE_PALM_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.DATE_PALM_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.DATE_PALM_BOAT.get());
                        safeAccept(output, GotModItems.DATE_PALM_CHEST_BOAT.get());

                        // ── Fig ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FIG_LOG.get());
                        safeAccept(output, GotModBlocks.FIG_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_WOOD.get());
                        safeAccept(output, GotModBlocks.FIG_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIG_PLANKS.get());
                        safeAccept(output, GotModBlocks.FIG_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIG_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_FENCE.get());
                        safeAccept(output, GotModBlocks.FIG_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.FIG_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.FIG_BUTTON.get());
                        safeAccept(output, GotModBlocks.FIG_DOOR.get());
                        safeAccept(output, GotModBlocks.FIG_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.FIG_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_BRANCH.get());
                        safeAccept(output, GotModBlocks.FIG_ROOFING.get());
                        safeAccept(output, GotModBlocks.FIG_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIG_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.FIG_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.FIG_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.FIG_BOAT.get());
                        safeAccept(output, GotModItems.FIG_CHEST_BOAT.get());

                        // ── Lemon ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LEMON_LOG.get());
                        safeAccept(output, GotModBlocks.LEMON_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_WOOD.get());
                        safeAccept(output, GotModBlocks.LEMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.LEMON_PLANKS.get());
                        safeAccept(output, GotModBlocks.LEMON_STAIRS.get());
                        safeAccept(output, GotModBlocks.LEMON_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_FENCE.get());
                        safeAccept(output, GotModBlocks.LEMON_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.LEMON_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.LEMON_BUTTON.get());
                        safeAccept(output, GotModBlocks.LEMON_DOOR.get());
                        safeAccept(output, GotModBlocks.LEMON_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.LEMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.LEMON_ROOFING.get());
                        safeAccept(output, GotModBlocks.LEMON_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.LEMON_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.LEMON_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.LEMON_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.LEMON_BOAT.get());
                        safeAccept(output, GotModItems.LEMON_CHEST_BOAT.get());

                        // ── Lime ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LIME_LOG.get());
                        safeAccept(output, GotModBlocks.LIME_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_WOOD.get());
                        safeAccept(output, GotModBlocks.LIME_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIME_PLANKS.get());
                        safeAccept(output, GotModBlocks.LIME_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIME_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_FENCE.get());
                        safeAccept(output, GotModBlocks.LIME_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.LIME_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.LIME_BUTTON.get());
                        safeAccept(output, GotModBlocks.LIME_DOOR.get());
                        safeAccept(output, GotModBlocks.LIME_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.LIME_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_BRANCH.get());
                        safeAccept(output, GotModBlocks.LIME_ROOFING.get());
                        safeAccept(output, GotModBlocks.LIME_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIME_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.LIME_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.LIME_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.LIME_BOAT.get());
                        safeAccept(output, GotModItems.LIME_CHEST_BOAT.get());

                        // ── Olive ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OLIVE_LOG.get());
                        safeAccept(output, GotModBlocks.OLIVE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_WOOD.get());
                        safeAccept(output, GotModBlocks.OLIVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.OLIVE_PLANKS.get());
                        safeAccept(output, GotModBlocks.OLIVE_STAIRS.get());
                        safeAccept(output, GotModBlocks.OLIVE_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_FENCE.get());
                        safeAccept(output, GotModBlocks.OLIVE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.OLIVE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.OLIVE_BUTTON.get());
                        safeAccept(output, GotModBlocks.OLIVE_DOOR.get());
                        safeAccept(output, GotModBlocks.OLIVE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.OLIVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_BRANCH.get());
                        safeAccept(output, GotModBlocks.OLIVE_ROOFING.get());
                        safeAccept(output, GotModBlocks.OLIVE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.OLIVE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.OLIVE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.OLIVE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.OLIVE_BOAT.get());
                        safeAccept(output, GotModItems.OLIVE_CHEST_BOAT.get());

                        // ── Orange ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ORANGE_LOG.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_WOOD.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ORANGE_PLANKS.get());
                        safeAccept(output, GotModBlocks.ORANGE_STAIRS.get());
                        safeAccept(output, GotModBlocks.ORANGE_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_FENCE.get());
                        safeAccept(output, GotModBlocks.ORANGE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ORANGE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ORANGE_BUTTON.get());
                        safeAccept(output, GotModBlocks.ORANGE_DOOR.get());
                        safeAccept(output, GotModBlocks.ORANGE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ORANGE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_BRANCH.get());
                        safeAccept(output, GotModBlocks.ORANGE_ROOFING.get());
                        safeAccept(output, GotModBlocks.ORANGE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ORANGE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ORANGE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ORANGE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ORANGE_BOAT.get());
                        safeAccept(output, GotModItems.ORANGE_CHEST_BOAT.get());

                        // ── Peach ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PEACH_LOG.get());
                        safeAccept(output, GotModBlocks.PEACH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_WOOD.get());
                        safeAccept(output, GotModBlocks.PEACH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEACH_PLANKS.get());
                        safeAccept(output, GotModBlocks.PEACH_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEACH_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_FENCE.get());
                        safeAccept(output, GotModBlocks.PEACH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PEACH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PEACH_BUTTON.get());
                        safeAccept(output, GotModBlocks.PEACH_DOOR.get());
                        safeAccept(output, GotModBlocks.PEACH_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PEACH_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_BRANCH.get());
                        safeAccept(output, GotModBlocks.PEACH_ROOFING.get());
                        safeAccept(output, GotModBlocks.PEACH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEACH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PEACH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PEACH_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PEACH_BOAT.get());
                        safeAccept(output, GotModItems.PEACH_CHEST_BOAT.get());

                        // ── Pear ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PEAR_LOG.get());
                        safeAccept(output, GotModBlocks.PEAR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_WOOD.get());
                        safeAccept(output, GotModBlocks.PEAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEAR_PLANKS.get());
                        safeAccept(output, GotModBlocks.PEAR_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEAR_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_FENCE.get());
                        safeAccept(output, GotModBlocks.PEAR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PEAR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PEAR_BUTTON.get());
                        safeAccept(output, GotModBlocks.PEAR_DOOR.get());
                        safeAccept(output, GotModBlocks.PEAR_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PEAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_BRANCH.get());
                        safeAccept(output, GotModBlocks.PEAR_ROOFING.get());
                        safeAccept(output, GotModBlocks.PEAR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PEAR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PEAR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PEAR_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PEAR_BOAT.get());
                        safeAccept(output, GotModItems.PEAR_CHEST_BOAT.get());

                        // ── Persimmon ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PERSIMMON_LOG.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_WOOD.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_PLANKS.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_STAIRS.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_FENCE.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_BUTTON.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_DOOR.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_BRANCH.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_ROOFING.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PERSIMMON_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PERSIMMON_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PERSIMMON_BOAT.get());
                        safeAccept(output, GotModItems.PERSIMMON_CHEST_BOAT.get());

                        // ── Pink Ivory ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PINK_IVORY_LOG.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_WOOD.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_PLANKS.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_FENCE.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_BUTTON.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_DOOR.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_BRANCH.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_ROOFING.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PINK_IVORY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PINK_IVORY_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PINK_IVORY_BOAT.get());
                        safeAccept(output, GotModItems.PINK_IVORY_CHEST_BOAT.get());

                        // ── Plum ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PLUM_LOG.get());
                        safeAccept(output, GotModBlocks.PLUM_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_WOOD.get());
                        safeAccept(output, GotModBlocks.PLUM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PLUM_PLANKS.get());
                        safeAccept(output, GotModBlocks.PLUM_STAIRS.get());
                        safeAccept(output, GotModBlocks.PLUM_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_FENCE.get());
                        safeAccept(output, GotModBlocks.PLUM_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PLUM_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PLUM_BUTTON.get());
                        safeAccept(output, GotModBlocks.PLUM_DOOR.get());
                        safeAccept(output, GotModBlocks.PLUM_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PLUM_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_BRANCH.get());
                        safeAccept(output, GotModBlocks.PLUM_ROOFING.get());
                        safeAccept(output, GotModBlocks.PLUM_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PLUM_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PLUM_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PLUM_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PLUM_BOAT.get());
                        safeAccept(output, GotModItems.PLUM_CHEST_BOAT.get());

                        // ── Pomegranate ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.POMEGRANATE_LOG.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_WOOD.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_PLANKS.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_STAIRS.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_FENCE.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_BUTTON.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_DOOR.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_BRANCH.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_ROOFING.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.POMEGRANATE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.POMEGRANATE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.POMEGRANATE_BOAT.get());
                        safeAccept(output, GotModItems.POMEGRANATE_CHEST_BOAT.get());

                        // ── Prune ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PRUNE_LOG.get());
                        safeAccept(output, GotModBlocks.PRUNE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_WOOD.get());
                        safeAccept(output, GotModBlocks.PRUNE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.PRUNE_PLANKS.get());
                        safeAccept(output, GotModBlocks.PRUNE_STAIRS.get());
                        safeAccept(output, GotModBlocks.PRUNE_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_FENCE.get());
                        safeAccept(output, GotModBlocks.PRUNE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PRUNE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PRUNE_BUTTON.get());
                        safeAccept(output, GotModBlocks.PRUNE_DOOR.get());
                        safeAccept(output, GotModBlocks.PRUNE_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.PRUNE_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_BRANCH.get());
                        safeAccept(output, GotModBlocks.PRUNE_ROOFING.get());
                        safeAccept(output, GotModBlocks.PRUNE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PRUNE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PRUNE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PRUNE_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.PRUNE_BOAT.get());
                        safeAccept(output, GotModItems.PRUNE_CHEST_BOAT.get());

                        // ── Almond ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ALMOND_LOG.get());
                        safeAccept(output, GotModBlocks.ALMOND_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_WOOD.get());
                        safeAccept(output, GotModBlocks.ALMOND_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALMOND_PLANKS.get());
                        safeAccept(output, GotModBlocks.ALMOND_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALMOND_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_FENCE.get());
                        safeAccept(output, GotModBlocks.ALMOND_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ALMOND_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ALMOND_BUTTON.get());
                        safeAccept(output, GotModBlocks.ALMOND_DOOR.get());
                        safeAccept(output, GotModBlocks.ALMOND_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.ALMOND_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_BRANCH.get());
                        safeAccept(output, GotModBlocks.ALMOND_ROOFING.get());
                        safeAccept(output, GotModBlocks.ALMOND_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALMOND_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ALMOND_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ALMOND_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.ALMOND_BOAT.get());
                        safeAccept(output, GotModItems.ALMOND_CHEST_BOAT.get());

                        // ── Nutmeg ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.NUTMEG_LOG.get());
                        safeAccept(output, GotModBlocks.NUTMEG_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_WOOD.get());
                        safeAccept(output, GotModBlocks.NUTMEG_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.NUTMEG_PLANKS.get());
                        safeAccept(output, GotModBlocks.NUTMEG_STAIRS.get());
                        safeAccept(output, GotModBlocks.NUTMEG_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_FENCE.get());
                        safeAccept(output, GotModBlocks.NUTMEG_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.NUTMEG_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.NUTMEG_BUTTON.get());
                        safeAccept(output, GotModBlocks.NUTMEG_DOOR.get());
                        safeAccept(output, GotModBlocks.NUTMEG_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.NUTMEG_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_BRANCH.get());
                        safeAccept(output, GotModBlocks.NUTMEG_ROOFING.get());
                        safeAccept(output, GotModBlocks.NUTMEG_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.NUTMEG_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.NUTMEG_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.NUTMEG_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.NUTMEG_BOAT.get());
                        safeAccept(output, GotModItems.NUTMEG_CHEST_BOAT.get());

                        // ── Hemlock ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.HEMLOCK_LOG.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_WOOD.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_WOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_WOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_PLANKS.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_FENCE.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_DOOR.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_TRAPDOOR.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_BRANCH.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_BRANCH.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_ROOFING.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.HEMLOCK_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.HEMLOCK_HANGING_SIGN.get()));
                        safeAccept(output, GotModItems.HEMLOCK_BOAT.get());
                        safeAccept(output, GotModItems.HEMLOCK_CHEST_BOAT.get());


                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 2 — GOT: MASONRY
     * All stone, brick, cobblestone, and rock-based building blocks.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_MASONRY =
            REGISTRY.register("got_masonry", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_CARPENTRY.getId())
                    .title(Component.translatable("itemGroup.got.got_masonry"))
                    .icon(() -> new ItemStack(GotModBlocks.LIMESTONE_BRICK.get()))
                    .displayItems((params, output) -> {

                        // ── Basalt ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BASALT_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.BASALT_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_BASALT_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_BRICK.get());
                        safeAccept(output, GotModBlocks.BASALT_PILLAR.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_BASALT_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_BASALT_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_BASALT_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.BASALT_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.BASALT_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.BASALT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.BASALT_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_BASALT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_BASALT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_BASALT_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_BRICK_WALL.get());

                        // ── Flint ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FLINT_ROCK.get());
                        safeAccept(output, GotModBlocks.FLINT_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_FLINT_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_FLINT_BRICK.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.FLINT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.FLINT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.FLINT_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_FLINT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_FLINT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_FLINT_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_FLINT_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FLINT_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_FLINT_BRICK_WALL.get());

                        // ── Fused Black Stone ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_FUSED_BLACK_ROCK.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_FUSED_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_PILLAR.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_FUSED_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_FUSED_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_FUSED_BLACK_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_BRICK_WALL.get());

                        // ── Grey Granite ───────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_GREY_GRANITE_ROCK.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_GREY_GRANITE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_BRICK.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_PILLAR.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_GREY_GRANITE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_GREY_GRANITE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_GREY_GRANITE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_BRICK_WALL.get());

                        // ── Limestone ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_LIMESTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_LIMESTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_PILLAR.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_LIMESTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_LIMESTONE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_LIMESTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_LIMESTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_LIMESTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_BRICK_WALL.get());

                        // ── Marble ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MARBLE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_MARBLE_ROCK.get());
                        safeAccept(output, GotModBlocks.MARBLE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MARBLE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_MARBLE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_BRICK.get());
                        safeAccept(output, GotModBlocks.MARBLE_PILLAR.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_MARBLE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_MARBLE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_MARBLE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.MARBLE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MARBLE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MARBLE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MARBLE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_MARBLE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_MARBLE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_MARBLE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_BRICK_WALL.get());

                        // ── Oily Black Stone ───────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_OILY_BLACK_ROCK.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_OILY_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_BRICK.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_PILLAR.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_OILY_BLACK_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_OILY_BLACK_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_OILY_BLACK_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_OILY_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_OILY_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_OILY_BLACK_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_BRICK_WALL.get());

                        // ── Red Sandstone ──────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_PILLAR.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_WALL.get());

                        // ── Sandstone ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_PILLAR.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SANDSTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK_WALL.get());

                        // ── Slate ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SLATE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SLATE_ROCK.get());
                        safeAccept(output, GotModBlocks.SLATE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.SLATE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_SLATE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_BRICK.get());
                        safeAccept(output, GotModBlocks.SLATE_PILLAR.get());
                        safeAccept(output, GotModBlocks.SLATE_SHINGLES.get());
                        safeAccept(output, GotModBlocks.SLATE_SHINGLES_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_SHINGLES_STAIRS.get());
                        safeAccept(output, GotModBlocks.SLATE_SHINGLES_WALL.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SLATE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SLATE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SLATE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.SLATE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.SLATE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.SLATE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SLATE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_SLATE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_SLATE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_SLATE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_BRICK_WALL.get());

                        // ── Path blocks ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PATH_BLOCK.get());
                        safeAccept(output, GotModBlocks.COBBLED_PATH_BLOCK.get());

                        // ── Vanilla dirt-family slabs/stairs ──────────────────────────────
                        safeAccept(output, GotModBlocks.DIRT_SLAB.get());
                        safeAccept(output, GotModBlocks.DIRT_STAIRS.get());
                        safeAccept(output, GotModBlocks.MUD_SLAB.get());
                        safeAccept(output, GotModBlocks.MUD_STAIRS.get());
                        safeAccept(output, GotModBlocks.DIRT_PATH_SLAB.get());
                        safeAccept(output, GotModBlocks.DIRT_PATH_STAIRS.get());
                        safeAccept(output, GotModBlocks.COARSE_DIRT_SLAB.get());
                        safeAccept(output, GotModBlocks.COARSE_DIRT_STAIRS.get());
                        safeAccept(output, GotModBlocks.ROOTED_DIRT_SLAB.get());
                        safeAccept(output, GotModBlocks.ROOTED_DIRT_STAIRS.get());
                        safeAccept(output, GotModBlocks.PODZOL_SLAB.get());
                        safeAccept(output, GotModBlocks.PODZOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.GRASS_BLOCK_SLAB.get());
                        safeAccept(output, GotModBlocks.GRASS_BLOCK_STAIRS.get());

                        // ── Fieldstone ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FIELDSTONE.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_WALL.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_PRESSURE_PLATE.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 3 — GOT: DECORATIVE BLOCKS
     * Leaves, saplings, flowers, grasses, wild plants.
     * Ores, gems, ingots, and coins live in GOT: INGREDIENTS.
     * Tools and armour live in GOT: ARMORY.
     * Spawn eggs live in GOT: SPAWN EGGS.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_DECORATIVE =
            REGISTRY.register("got_decorative", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_MASONRY.getId())
                    .title(Component.translatable("itemGroup.got.got_decorative"))
                    .icon(() -> new ItemStack(GotModBlocks.WEIRWOOD_LEAVES.get()))
                    .displayItems((params, output) -> {

                        // ── Leaves ────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WEIRWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.ASPEN_LEAVES.get());
                        safeAccept(output, GotModBlocks.ALDER_LEAVES.get());
                        safeAccept(output, GotModBlocks.PINE_LEAVES.get());
                        safeAccept(output, GotModBlocks.FIR_LEAVES.get());
                        safeAccept(output, GotModBlocks.SENTINAL_LEAVES.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BEECH_LEAVES.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LEAVES.get());
                        safeAccept(output, GotModBlocks.ASH_LEAVES.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LEAVES.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.CINNAMON_LEAVES.get());
                        safeAccept(output, GotModBlocks.CLOVE_LEAVES.get());
                        safeAccept(output, GotModBlocks.EBONY_LEAVES.get());
                        safeAccept(output, GotModBlocks.ELM_LEAVES.get());
                        safeAccept(output, GotModBlocks.CEDAR_LEAVES.get());
                        safeAccept(output, GotModBlocks.APPLE_LEAVES.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_LEAVES.get());
                        safeAccept(output, GotModBlocks.LINDEN_LEAVES.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_LEAVES.get());
                        safeAccept(output, GotModBlocks.MAPLE_LEAVES.get());
                        safeAccept(output, GotModBlocks.MYRRH_LEAVES.get());
                        safeAccept(output, GotModBlocks.REDWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_LEAVES.get());
                        safeAccept(output, GotModBlocks.WILLOW_LEAVES.get());
                        safeAccept(output, GotModBlocks.WORMTREE_LEAVES.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_LEAVES.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_LEAVES.get());
                        safeAccept(output, GotModBlocks.APRICOT_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_LEAVES.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_LEAVES.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_LEAVES.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_LEAVES.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_LEAVES.get());
                        safeAccept(output, GotModBlocks.FIG_LEAVES.get());
                        safeAccept(output, GotModBlocks.LEMON_LEAVES.get());
                        safeAccept(output, GotModBlocks.LIME_LEAVES.get());
                        safeAccept(output, GotModBlocks.OLIVE_LEAVES.get());
                        safeAccept(output, GotModBlocks.ORANGE_LEAVES.get());
                        safeAccept(output, GotModBlocks.PEACH_LEAVES.get());
                        safeAccept(output, GotModBlocks.PEAR_LEAVES.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_LEAVES.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_LEAVES.get());
                        safeAccept(output, GotModBlocks.PLUM_LEAVES.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_LEAVES.get());
                        safeAccept(output, GotModBlocks.PRUNE_LEAVES.get());
                        safeAccept(output, GotModBlocks.ALMOND_LEAVES.get());
                        safeAccept(output, GotModBlocks.NUTMEG_LEAVES.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_LEAVES.get());


                        // ── Saplings ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WEIRWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.ASPEN_SAPLING.get());
                        safeAccept(output, GotModBlocks.ALDER_SAPLING.get());
                        safeAccept(output, GotModBlocks.PINE_SAPLING.get());
                        safeAccept(output, GotModBlocks.FIR_SAPLING.get());
                        safeAccept(output, GotModBlocks.SENTINAL_SAPLING.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.BEECH_SAPLING.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_SAPLING.get());
                        safeAccept(output, GotModBlocks.ASH_SAPLING.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_SAPLING.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.CINNAMON_SAPLING.get());
                        safeAccept(output, GotModBlocks.CLOVE_SAPLING.get());
                        safeAccept(output, GotModBlocks.EBONY_SAPLING.get());
                        safeAccept(output, GotModBlocks.ELM_SAPLING.get());
                        safeAccept(output, GotModBlocks.CEDAR_SAPLING.get());
                        safeAccept(output, GotModBlocks.APPLE_SAPLING.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_SAPLING.get());
                        safeAccept(output, GotModBlocks.LINDEN_SAPLING.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_SAPLING.get());
                        safeAccept(output, GotModBlocks.MAPLE_SAPLING.get());
                        safeAccept(output, GotModBlocks.MYRRH_SAPLING.get());
                        safeAccept(output, GotModBlocks.REDWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_SAPLING.get());
                        safeAccept(output, GotModBlocks.WILLOW_SAPLING.get());
                        safeAccept(output, GotModBlocks.WORMTREE_SAPLING.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_SAPLING.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_SAPLING.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_SAPLING.get());
                        safeAccept(output, GotModBlocks.APRICOT_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_SAPLING.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_SAPLING.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_SAPLING.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_SAPLING.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_SAPLING.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_SAPLING.get());
                        safeAccept(output, GotModBlocks.FIG_SAPLING.get());
                        safeAccept(output, GotModBlocks.LEMON_SAPLING.get());
                        safeAccept(output, GotModBlocks.LIME_SAPLING.get());
                        safeAccept(output, GotModBlocks.OLIVE_SAPLING.get());
                        safeAccept(output, GotModBlocks.ORANGE_SAPLING.get());
                        safeAccept(output, GotModBlocks.PEACH_SAPLING.get());
                        safeAccept(output, GotModBlocks.PEAR_SAPLING.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_SAPLING.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_SAPLING.get());
                        safeAccept(output, GotModBlocks.PLUM_SAPLING.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_SAPLING.get());
                        safeAccept(output, GotModBlocks.PRUNE_SAPLING.get());
                        safeAccept(output, GotModBlocks.ALMOND_SAPLING.get());
                        safeAccept(output, GotModBlocks.NUTMEG_SAPLING.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_SAPLING.get());


                        // ── Flowers ───────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BELLFLOWER.get());
                        safeAccept(output, GotModBlocks.BLACK_LOTUS.get());
                        safeAccept(output, GotModBlocks.BLOOD_BLOOM.get());
                        safeAccept(output, GotModBlocks.COLDSNAPS.get());
                        safeAccept(output, GotModBlocks.DRAGONS_BREATH.get());
                        safeAccept(output, GotModBlocks.EVENING_STAR.get());
                        safeAccept(output, GotModBlocks.FORGET_ME_NOT.get());
                        safeAccept(output, GotModBlocks.FROSTFIRES.get());
                        safeAccept(output, GotModBlocks.GILLYFLOWER.get());
                        safeAccept(output, GotModBlocks.GINGER.get());
                        safeAccept(output, GotModBlocks.GOATHEAD.get());
                        safeAccept(output, GotModBlocks.GOLDENCUP.get());
                        safeAccept(output, GotModBlocks.GOLDENROD.get());
                        safeAccept(output, GotModBlocks.GOLDEN_ROSE.get());
                        safeAccept(output, GotModBlocks.GOLDEN_ROSE_BUSH.get());
                        safeAccept(output, GotModBlocks.GORSE.get());
                        safeAccept(output, GotModBlocks.RED_ROSE.get());
                        safeAccept(output, GotModBlocks.RED_ROSE_BUSH.get());
                        safeAccept(output, GotModBlocks.WHITE_ROSE.get());
                        safeAccept(output, GotModBlocks.WHITE_ROSE_BUSH.get());
                        safeAccept(output, GotModBlocks.WINTER_ROSE.get());
                        safeAccept(output, GotModBlocks.WINTER_ROSE_BUSH.get());
                        safeAccept(output, GotModBlocks.LADYS_LACE.get());
                        safeAccept(output, GotModBlocks.LAVENDER.get());
                        safeAccept(output, GotModBlocks.LIVERWORT.get());
                        safeAccept(output, GotModBlocks.LUNGWORT.get());
                        safeAccept(output, GotModBlocks.MOONBLOOM.get());
                        safeAccept(output, GotModBlocks.NIGHTSHADE.get());
                        safeAccept(output, GotModBlocks.OPIUM_POPPY.get());
                        safeAccept(output, GotModBlocks.PENNYROYAL.get());
                        safeAccept(output, GotModBlocks.POISON_KISSES.get());
                        safeAccept(output, GotModBlocks.SAFFRON_CROCUS.get());
                        safeAccept(output, GotModBlocks.SEDGE.get());
                        safeAccept(output, GotModBlocks.SPICEFLOWER.get());
                        safeAccept(output, GotModBlocks.TANSY.get());
                        safeAccept(output, GotModBlocks.THISTLE.get());
                        safeAccept(output, GotModBlocks.THORNBUSH.get());
                        safeAccept(output, GotModBlocks.WILD_RADISH.get());

                        // ── Plants & Herbs ────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WILD_BEAN.get());

                        safeAccept(output, GotModBlocks.BRACKEN.get());
                        safeAccept(output, GotModBlocks.BRIAR.get());
                        safeAccept(output, GotModBlocks.BROOM.get());
                        safeAccept(output, GotModBlocks.WILD_CARDAMOM.get());
                        safeAccept(output, GotModBlocks.WILD_CHICKPEA.get());

                        safeAccept(output, GotModBlocks.WILD_CUCUMBER.get());
                        safeAccept(output, GotModBlocks.DAGGERLEAF.get());
                        safeAccept(output, GotModBlocks.FIREPOD.get());
                        safeAccept(output, GotModBlocks.GHOSTSKIN.get());
                        safeAccept(output, GotModBlocks.GRAPE_VINE.get());
                        safeAccept(output, GotModBlocks.HARPYS_GOLD.get());
                        safeAccept(output, GotModBlocks.WILD_HEMP.get());
                        safeAccept(output, GotModBlocks.HORNWORT.get());
                        safeAccept(output, GotModBlocks.IVY.get());
                        safeAccept(output, GotModBlocks.KINGSCOPPER.get());
                        safeAccept(output, GotModBlocks.WILD_LICORICE.get());
                        safeAccept(output, GotModBlocks.MISTLETOE.get());
                        safeAccept(output, GotModBlocks.WILD_MUSTARD_PLANT.get());
                        safeAccept(output, GotModBlocks.NETTLE.get());
                        safeAccept(output, GotModBlocks.WILD_PEPPER_PLANT.get());
                        safeAccept(output, GotModBlocks.PINCHFIRE.get());
                        safeAccept(output, GotModBlocks.PRICKLY_BEN.get());
                        safeAccept(output, GotModBlocks.SANDWILLOW.get());
                        safeAccept(output, GotModBlocks.SMOKEBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.SOURLEAF.get());
                        safeAccept(output, GotModBlocks.STING_ME_NOT.get());
                        safeAccept(output, GotModBlocks.WASPWILLOW.get());


                        // ── Grasses & Wetland Plants ──────────────────────────────────
                        safeAccept(output, GotModBlocks.DEVILGRASS.get());
                        safeAccept(output, GotModBlocks.GHOST_GRASS.get());
                        safeAccept(output, GotModBlocks.HRANNA.get());
                        safeAccept(output, GotModBlocks.PIPERS_GRASS.get());
                        safeAccept(output, GotModBlocks.WHEATGRASS.get());
                        safeAccept(output, GotModBlocks.REEDS.get());
                        safeAccept(output, GotModBlocks.SHORT_REEDS.get());
                        safeAccept(output, GotModBlocks.RUSHES.get());
                        safeAccept(output, GotModBlocks.QUAGMIRE.get());

                        // ── Wool Slabs & Stairs ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WHITE_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAGENTA_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAGENTA_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIGHT_BLUE_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_BLUE_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.YELLOW_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.YELLOW_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIME_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINK_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.GRAY_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.GRAY_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.LIGHT_GRAY_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_GRAY_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.CYAN_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.CYAN_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.PURPLE_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLE_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.BROWN_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.BROWN_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.GREEN_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.GREEN_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_WOOL_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_WOOL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_WOOL_STAIRS.get());

                        // ── Banner Patterns ───────────────────────────────────────────
                        output.accept(GotModItems.STARK_BANNER_PATTERN.get());
                        output.accept(GotModItems.LANNISTER_BANNER_PATTERN.get());
                        output.accept(GotModItems.TARGARYEN_BANNER_PATTERN.get());
                        output.accept(GotModItems.BARATHEON_BANNER_PATTERN.get());
                        output.accept(GotModItems.GREYJOY_BANNER_PATTERN.get());
                        output.accept(GotModItems.TYRELL_BANNER_PATTERN.get());
                        output.accept(GotModItems.MARTELL_BANNER_PATTERN.get());
                        output.accept(GotModItems.TULLY_BANNER_PATTERN.get());
                        output.accept(GotModItems.ARRYN_BANNER_PATTERN.get());
                        output.accept(GotModItems.BOLTON_BANNER_PATTERN.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 4 — GOT: FOOD
     * Bread, vegetables, berries, wild crops, berry bushes, and crop blocks.
     * Seeds, grains, flour, and dough live in GOT: INGREDIENTS.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_FOOD =
            REGISTRY.register("got_food", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_DECORATIVE.getId())
                    .title(Component.translatable("itemGroup.got.got_food"))
                    .icon(() -> new ItemStack(net.minecraft.world.item.Items.BREAD))
                    .displayItems((params, output) -> {

                        // ── Cooking Equipment ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OVEN.get());
                        safeAccept(output, GotModBlocks.FORGE.get());
                        safeAccept(output, GotModBlocks.SMITHING_ANVIL.get());
                        output.accept(GotModItems.SMITHING_HAMMER.get());
                        safeAccept(output, GotModBlocks.BELLOWS.get());

                        // ── Bread ─────────────────────────────────────────────────────
                        output.accept(GotModItems.OAT_BREAD.get());
                        output.accept(GotModItems.RYE_BREAD.get());
                        output.accept(GotModItems.BARLEY_BREAD.get());

                        // ── Vegetables ────────────────────────────────────────────────
                        output.accept(GotModItems.PARSNIP.get());
                        output.accept(GotModItems.ONION.get());
                        output.accept(GotModItems.TURNIP.get());
                        output.accept(GotModItems.PEAS.get());
                        output.accept(GotModItems.CABBAGE.get());
                        output.accept(GotModItems.GARLIC.get());
                        output.accept(GotModItems.NEEP.get());
                        output.accept(GotModItems.HORSERADISH.get());
                        output.accept(GotModItems.LEEK.get());
                        output.accept(GotModItems.CORN_ON_THE_COB.get());
                        output.accept(GotModItems.BEAN.get());
                        output.accept(GotModItems.CHICKPEA.get());
                        output.accept(GotModItems.CUCUMBER.get());

                        // ── Herbs & Spices ────────────────────────────────────────────
                        output.accept(GotModItems.CARDAMOM.get());
                        output.accept(GotModItems.CINNAMON.get());
                        output.accept(GotModItems.CLOVE.get());
                        output.accept(GotModItems.LICORICE.get());
                        output.accept(GotModItems.PEPPER_PLANT.get());

                        // ── Crafting Ingredients ──────────────────────────────────────
                        output.accept(GotModItems.HEMP.get());

                        // ── Berries ───────────────────────────────────────────────────
                        output.accept(GotModItems.BLACKBERRIES.get());
                        output.accept(GotModItems.BLUEBERRIES.get());
                        output.accept(GotModItems.SMOKEBERRIES.get());
                        output.accept(GotModItems.RASPBERRIES.get());
                        output.accept(GotModItems.STRAWBERRIES.get());

                        // ── Fruits & Nuts ─────────────────────────────────────────────
                        output.accept(GotModItems.APRICOT.get());
                        output.accept(GotModItems.RED_CHERRY.get());
                        output.accept(GotModItems.BLACK_CHERRY.get());
                        output.accept(GotModItems.WHITE_CHERRY.get());
                        output.accept(GotModItems.CRABAPPLE.get());
                        output.accept(GotModItems.DATE.get());
                        output.accept(GotModItems.FIG.get());
                        output.accept(GotModItems.LEMON.get());
                        output.accept(GotModItems.LIME.get());
                        output.accept(GotModItems.OLIVE.get());
                        output.accept(GotModItems.ORANGE.get());
                        output.accept(GotModItems.PEACH.get());
                        output.accept(GotModItems.PEAR.get());
                        output.accept(GotModItems.PERSIMMON.get());
                        output.accept(GotModItems.PLUM.get());
                        output.accept(GotModItems.POMEGRANATE.get());
                        output.accept(GotModItems.PRUNE.get());
                        output.accept(GotModItems.ALMOND.get());
                        output.accept(GotModItems.NUTMEG.get());

                        // ── Meats ─────────────────────────────────────────────────────
                        output.accept(GotModItems.RAW_BEAR_MEAT.get());
                        output.accept(GotModItems.COOKED_BEAR_MEAT.get());
                        output.accept(GotModItems.RAW_MAMMOTH_MEAT.get());
                        output.accept(GotModItems.COOKED_MAMMOTH_MEAT.get());
                        output.accept(GotModItems.RAW_HORSE_MEAT.get());
                        output.accept(GotModItems.COOKED_HORSE_MEAT.get());
                        output.accept(GotModItems.RAW_HERON.get());
                        output.accept(GotModItems.COOKED_HERON.get());
                        output.accept(GotModItems.RAW_VENISON.get());
                        output.accept(GotModItems.COOKED_VENISON.get());


                        // ── Wild Crops (placeable blocks) ─────────────────────────────
                        safeAccept(output, GotModBlocks.WILD_WHEAT.get());
                        safeAccept(output, GotModBlocks.WILD_OAT.get());
                        safeAccept(output, GotModBlocks.WILD_RYE.get());
                        safeAccept(output, GotModBlocks.WILD_BARLEY.get());
                        safeAccept(output, GotModBlocks.WILD_BEETROOT.get());
                        safeAccept(output, GotModBlocks.WILD_COTTON.get());
                        safeAccept(output, GotModBlocks.WILD_PEPPERCORN.get());
                        safeAccept(output, GotModBlocks.WILD_CARROT.get());
                        safeAccept(output, GotModBlocks.WILD_PARSNIP.get());
                        safeAccept(output, GotModBlocks.WILD_ONION.get());
                        safeAccept(output, GotModBlocks.WILD_TURNIP.get());
                        safeAccept(output, GotModBlocks.WILD_NEEP.get());
                        safeAccept(output, GotModBlocks.WILD_PEAS.get());
                        safeAccept(output, GotModBlocks.WILD_CABBAGE.get());
                        safeAccept(output, GotModBlocks.WILD_GARLIC.get());
                        safeAccept(output, GotModBlocks.WILD_HORSERADISH.get());
                        safeAccept(output, GotModBlocks.WILD_LEEK.get());
                        safeAccept(output, GotModBlocks.WILD_BEAN.get());
                        safeAccept(output, GotModBlocks.WILD_CARDAMOM.get());
                        safeAccept(output, GotModBlocks.WILD_CHICKPEA.get());
                        safeAccept(output, GotModBlocks.WILD_CUCUMBER.get());
                        safeAccept(output, GotModBlocks.WILD_HEMP.get());
                        safeAccept(output, GotModBlocks.WILD_LICORICE.get());
                        safeAccept(output, GotModBlocks.WILD_MUSTARD_PLANT.get());
                        safeAccept(output, GotModBlocks.WILD_PEPPER_PLANT.get());

                        // ── Crop Blocks ───────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OAT_CROP.get());
                        safeAccept(output, GotModBlocks.RYE_CROP.get());
                        safeAccept(output, GotModBlocks.BARLEY_CROP.get());
                        safeAccept(output, GotModBlocks.COTTON_CROP.get());
                        safeAccept(output, GotModBlocks.PARSNIP_CROP.get());
                        safeAccept(output, GotModBlocks.ONION_CROP.get());
                        safeAccept(output, GotModBlocks.TURNIP_CROP.get());
                        safeAccept(output, GotModBlocks.PEAS_CROP.get());
                        safeAccept(output, GotModBlocks.CABBAGE_CROP.get());
                        safeAccept(output, GotModBlocks.GARLIC_CROP.get());
                        safeAccept(output, GotModBlocks.NEEP_CROP.get());
                        safeAccept(output, GotModBlocks.HORSERADISH_CROP.get());
                        safeAccept(output, GotModBlocks.LEEK_CROP.get());
                        safeAccept(output, GotModBlocks.PEPPERCORN_CROP.get());
                        safeAccept(output, GotModBlocks.BEAN_CROP.get());
                        safeAccept(output, GotModBlocks.CARDAMOM_CROP.get());
                        safeAccept(output, GotModBlocks.CHICKPEA_CROP.get());
                        safeAccept(output, GotModBlocks.CORN_CROP.get());
                        safeAccept(output, GotModBlocks.CUCUMBER_CROP.get());
                        safeAccept(output, GotModBlocks.HEMP_CROP.get());
                        safeAccept(output, GotModBlocks.LICORICE_CROP.get());
                        safeAccept(output, GotModBlocks.MUSTARD_PLANT_CROP.get());
                        safeAccept(output, GotModBlocks.PEPPER_PLANT_CROP.get());
                        safeAccept(output, GotModBlocks.STRAWBERRY_CROP.get());

                        // ── Berry Bushes ──────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACKBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.BLUEBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.SMOKEBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.RASPBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.WILD_STRAWBERRY.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 5 — GOT: ARMORY
     * All weapons (swords, axes, tools used as weapons) and armor sets.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_ARMORY =
            REGISTRY.register("got_armory", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_FOOD.getId())
                    .title(Component.translatable("itemGroup.got.got_armory"))
                    .icon(() -> new ItemStack(GotModItems.BRONZE_LONGSWORD_CROSSGUARD_POMMEL.get()))
                    .displayItems((params, output) -> {

                        // ── Bronze Weapons ────────────────────────────────────────────
                        output.accept(GotModItems.BRONZE_AXE.get());
                        output.accept(GotModItems.BRONZE_PICKAXE.get());
                        output.accept(GotModItems.BRONZE_SHOVEL.get());
                        output.accept(GotModItems.BRONZE_HOE.get());

                        // ── Steel Weapons ─────────────────────────────────────────────
                        output.accept(GotModItems.STEEL_AXE.get());
                        output.accept(GotModItems.STEEL_PICKAXE.get());
                        output.accept(GotModItems.STEEL_SHOVEL.get());
                        output.accept(GotModItems.STEEL_HOE.get());

                        // ── Ranged Weapons ───────────────────────────────────────────
                        output.accept(GotModItems.LONGBOW.get());

                        // ── Bronze Armor ──────────────────────────────────────────────
                        output.accept(GotModItems.BRONZE_HELMET.get());
                        output.accept(GotModItems.BRONZE_CHESTPLATE.get());
                        output.accept(GotModItems.BRONZE_LEGGINGS.get());
                        output.accept(GotModItems.BRONZE_BOOTS.get());

                        // ── Steel Armor ───────────────────────────────────────────────
                        output.accept(GotModItems.STEEL_HELMET.get());
                        output.accept(GotModItems.STEEL_CHESTPLATE.get());
                        output.accept(GotModItems.STEEL_LEGGINGS.get());
                        output.accept(GotModItems.STEEL_BOOTS.get());

                        // ── Smithy Components — Iron ──────────────────────────────────
                        output.accept(GotModItems.IRON_SPEAR_HEAD.get());
                        output.accept(GotModItems.IRON_ARROWHEAD.get());
                        output.accept(GotModItems.IRON_SHORT_AXE_HEAD.get());
                        output.accept(GotModItems.IRON_LONG_AXE_HEAD.get());
                        output.accept(GotModItems.IRON_LONGSWORD_BLADE.get());
                        output.accept(GotModItems.IRON_BASTARD_SWORD_BLADE.get());
                        output.accept(GotModItems.IRON_SHORTSWORD_BLADE.get());
                        output.accept(GotModItems.IRON_FALCHION_BLADE.get());
                        output.accept(GotModItems.IRON_GREATSWORD_BLADE.get());
                        output.accept(GotModItems.POMMEL.get());
                        output.accept(GotModItems.HILT.get());
                        output.accept(GotModItems.LONG_HILT.get());
                        output.accept(GotModItems.CROSSGUARD.get());
                        output.accept(GotModItems.SLOPED_CROSSGUARD.get());
                        output.accept(GotModItems.IRON_ARMOR_PLATE.get());

                        // ── Smithy Components — Bronze ────────────────────────────────
                        output.accept(GotModItems.BRONZE_SPEAR_HEAD.get());
                        output.accept(GotModItems.BRONZE_ARROWHEAD.get());
                        output.accept(GotModItems.BRONZE_SHORT_AXE_HEAD.get());
                        output.accept(GotModItems.BRONZE_LONG_AXE_HEAD.get());
                        output.accept(GotModItems.BRONZE_LONGSWORD_BLADE.get());
                        output.accept(GotModItems.BRONZE_BASTARD_SWORD_BLADE.get());
                        output.accept(GotModItems.BRONZE_SHORTSWORD_BLADE.get());
                        output.accept(GotModItems.BRONZE_FALCHION_BLADE.get());
                        output.accept(GotModItems.BRONZE_GREATSWORD_BLADE.get());
                        output.accept(GotModItems.BRONZE_ARMOR_PLATE.get());

                        // ── Smithy Components — Steel ─────────────────────────────────
                        output.accept(GotModItems.STEEL_SPEAR_HEAD.get());
                        output.accept(GotModItems.STEEL_ARROWHEAD.get());
                        output.accept(GotModItems.STEEL_SHORT_AXE_HEAD.get());
                        output.accept(GotModItems.STEEL_LONG_AXE_HEAD.get());
                        output.accept(GotModItems.STEEL_LONGSWORD_BLADE.get());
                        output.accept(GotModItems.STEEL_BASTARD_SWORD_BLADE.get());
                        output.accept(GotModItems.STEEL_SHORTSWORD_BLADE.get());
                        output.accept(GotModItems.STEEL_FALCHION_BLADE.get());
                        output.accept(GotModItems.STEEL_GREATSWORD_BLADE.get());
                        output.accept(GotModItems.STEEL_ARMOR_PLATE.get());

                        // ── Assembled Swords — Iron ───────────────────────────────────
                        output.accept(GotModItems.IRON_SHORTSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.IRON_FALCHION_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.IRON_LONGSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.IRON_BASTARD_SWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.IRON_GREATSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.IRON_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get());

                        // ── Assembled Swords — Bronze ─────────────────────────────────
                        output.accept(GotModItems.BRONZE_SHORTSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.BRONZE_FALCHION_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.BRONZE_LONGSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.BRONZE_BASTARD_SWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.BRONZE_GREATSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.BRONZE_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get());

                        // ── Assembled Swords — Steel ──────────────────────────────────
                        output.accept(GotModItems.STEEL_SHORTSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.STEEL_FALCHION_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.STEEL_LONGSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.STEEL_BASTARD_SWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.STEEL_GREATSWORD_CROSSGUARD_POMMEL.get());
                        output.accept(GotModItems.STEEL_CLAYMORE_SLOPED_CROSSGUARD_POMMEL.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 6 — GOT: INGREDIENTS
     * Currency, gemstones, raw ores, ingots, seeds, and non-edible produce
     * (grains, cotton, peppercorn, flour, dough).
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_INGREDIENTS =
            REGISTRY.register("got_ingredients", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_ARMORY.getId())
                    .title(Component.translatable("itemGroup.got.got_ingredients"))
                    .icon(() -> new ItemStack(GotModItems.COIN_DRAGON.get()))
                    .displayItems((params, output) -> {

                        // ── Currency ──────────────────────────────────────────────────
                        output.accept(GotModItems.COIN_HALFPENNY.get());
                        output.accept(GotModItems.COIN_PENNY.get());
                        output.accept(GotModItems.COIN_HALFGROAT.get());
                        output.accept(GotModItems.COIN_GROAT.get());
                        output.accept(GotModItems.COIN_STAR.get());
                        output.accept(GotModItems.COIN_STAG.get());
                        output.accept(GotModItems.COIN_MOON.get());
                        output.accept(GotModItems.COIN_DRAGON.get());

                        // ── Gemstones ─────────────────────────────────────────────────
                        output.accept(GotModItems.AMBER.get());
                        output.accept(GotModItems.AMETHYST.get());
                        output.accept(GotModItems.BERYL.get());
                        output.accept(GotModItems.BLOODSTONE.get());
                        output.accept(GotModItems.CARNELIAN.get());
                        output.accept(GotModItems.CHALCEDONY.get());
                        output.accept(GotModItems.DRAGONGLASS_SHARD.get());
                        output.accept(GotModItems.GARNET.get());
                        output.accept(GotModItems.IVORY.get());
                        output.accept(GotModItems.JADE.get());
                        output.accept(GotModItems.JASPER.get());
                        output.accept(GotModItems.JET.get());
                        output.accept(GotModItems.MALACHITE.get());
                        output.accept(GotModItems.MOONSTONE.get());
                        output.accept(GotModItems.ONYX.get());
                        output.accept(GotModItems.OPAL.get());
                        output.accept(GotModItems.PEARL.get());
                        output.accept(GotModItems.RUBY.get());
                        output.accept(GotModItems.SAPPHIRE.get());
                        output.accept(GotModItems.TIGERS_EYE.get());
                        output.accept(GotModItems.TOPAZ.get());
                        output.accept(GotModItems.TOURMALINE.get());

                        // ── Gem Blocks ────────────────────────────────────────────────
                        output.accept(GotModItems.BERYL_BLOCK.get());
                        output.accept(GotModItems.BLOODSTONE_BLOCK.get());
                        output.accept(GotModItems.CARNELIAN_BLOCK.get());
                        output.accept(GotModItems.CHALCEDONY_BLOCK.get());
                        output.accept(GotModItems.GARNET_BLOCK.get());
                        output.accept(GotModItems.JADE_BLOCK.get());
                        output.accept(GotModItems.JASPER_BLOCK.get());
                        output.accept(GotModItems.MALACHITE_BLOCK.get());
                        output.accept(GotModItems.MOONSTONE_BLOCK.get());
                        output.accept(GotModItems.ONYX_BLOCK.get());
                        output.accept(GotModItems.OPAL_BLOCK.get());
                        output.accept(GotModItems.RUBY_BLOCK.get());
                        output.accept(GotModItems.SAPPHIRE_BLOCK.get());
                        output.accept(GotModItems.TIGERS_EYE_BLOCK.get());
                        output.accept(GotModItems.TOPAZ_BLOCK.get());
                        output.accept(GotModItems.TOURMALINE_BLOCK.get());

                        // ── Ore Blocks ────────────────────────────────────────────────
                        output.accept(GotModItems.BERYL_ORE.get());
                        output.accept(GotModItems.BLOODSTONE_ORE.get());
                        output.accept(GotModItems.CARNELIAN_ORE.get());
                        output.accept(GotModItems.CHALCEDONY_ORE.get());
                        output.accept(GotModItems.DRAGONGLASS_ORE.get());
                        output.accept(GotModItems.GARNET_ORE.get());
                        output.accept(GotModItems.JADE_ORE.get());
                        output.accept(GotModItems.JASPER_ORE.get());
                        output.accept(GotModItems.MALACHITE_ORE.get());
                        output.accept(GotModItems.MOONSTONE_ORE.get());
                        output.accept(GotModItems.ONYX_ORE.get());
                        output.accept(GotModItems.OPAL_ORE.get());
                        output.accept(GotModItems.RUBY_ORE.get());
                        output.accept(GotModItems.SAPPHIRE_ORE.get());
                        output.accept(GotModItems.SILVER_ORE.get());
                        output.accept(GotModItems.AMETHYST_ORE.get());
                        output.accept(GotModItems.TIGERS_EYE_ORE.get());
                        output.accept(GotModItems.TIN_ORE.get());
                        output.accept(GotModItems.TOPAZ_ORE.get());
                        output.accept(GotModItems.TOURMALINE_ORE.get());
                        output.accept(GotModItems.VALYRIAN_STEEL_ORE.get());
                        output.accept(GotModItems.COBALT_ORE.get());
                        output.accept(GotModItems.LEAD_ORE.get());
                        output.accept(GotModItems.PLATINUM_ORE.get());
                        output.accept(GotModItems.ZINC_ORE.get());

                        // ── Deepslate Ore Blocks ──────────────────────────────────────
                        output.accept(GotModItems.DEEPSLATE_BERYL_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_BLOODSTONE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_CARNELIAN_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_CHALCEDONY_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_GARNET_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_JADE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_JASPER_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_MALACHITE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_MOONSTONE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_ONYX_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_OPAL_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_RUBY_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_SAPPHIRE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_SILVER_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_AMETHYST_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_TIGERS_EYE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_TIN_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_TOPAZ_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_TOURMALINE_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_COBALT_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_LEAD_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_PLATINUM_ORE.get());
                        output.accept(GotModItems.DEEPSLATE_ZINC_ORE.get());

                        // ── Raw Ores ──────────────────────────────────────────────────
                        output.accept(GotModItems.RAW_SILVER.get());
                        output.accept(GotModItems.RAW_TIN.get());
                        output.accept(GotModItems.RAW_VALYRIAN_STEEL.get());
                        output.accept(GotModItems.RAW_COBALT.get());
                        output.accept(GotModItems.RAW_LEAD.get());
                        output.accept(GotModItems.RAW_PLATINUM.get());
                        output.accept(GotModItems.RAW_ZINC.get());

                        // ── Raw Metal Blocks ──────────────────────────────────────────
                        output.accept(GotModItems.RAW_SILVER_BLOCK.get());
                        output.accept(GotModItems.RAW_TIN_BLOCK.get());
                        output.accept(GotModItems.RAW_COBALT_BLOCK.get());
                        output.accept(GotModItems.RAW_LEAD_BLOCK.get());
                        output.accept(GotModItems.RAW_PLATINUM_BLOCK.get());
                        output.accept(GotModItems.RAW_ZINC_BLOCK.get());

                        // ── Ingots ────────────────────────────────────────────────────
                        output.accept(GotModItems.SILVER_INGOT.get());
                        output.accept(GotModItems.TIN_INGOT.get());
                        output.accept(GotModItems.BRONZE_INGOT.get());
                        output.accept(GotModItems.STEEL_INGOT.get());
                        output.accept(GotModItems.VALYRIAN_STEEL_INGOT.get());
                        output.accept(GotModItems.COBALT_INGOT.get());
                        output.accept(GotModItems.LEAD_INGOT.get());
                        output.accept(GotModItems.PLATINUM_INGOT.get());
                        output.accept(GotModItems.ZINC_INGOT.get());

                        // ── Seeds ─────────────────────────────────────────────────────
                        output.accept(GotModItems.OAT_SEEDS.get());
                        output.accept(GotModItems.RYE_SEEDS.get());
                        output.accept(GotModItems.BARLEY_SEEDS.get());
                        output.accept(GotModItems.COTTON_SEEDS.get());
                        output.accept(GotModItems.PEPPERCORN_SEEDS.get());
                        output.accept(GotModItems.CABBAGE_PLANT_SEEDS.get());
                        output.accept(GotModItems.CARDAMOM_SEEDS.get());
                        output.accept(GotModItems.CHICKPEA_SEEDS.get());
                        output.accept(GotModItems.CORN_SEEDS.get());
                        output.accept(GotModItems.CUCUMBER_SEEDS.get());
                        output.accept(GotModItems.HEMP_SEEDS.get());
                        output.accept(GotModItems.LICORICE_SEEDS.get());
                        output.accept(GotModItems.MUSTARD_PLANT_SEEDS.get());
                        output.accept(GotModItems.PEPPER_PLANT_SEEDS.get());

                        // ── Non-Edible Produce (grains, cotton, processing items) ─────
                        output.accept(GotModItems.OAT.get());
                        output.accept(GotModItems.RYE.get());
                        output.accept(GotModItems.BARLEY.get());
                        output.accept(GotModItems.COTTON.get());
                        output.accept(GotModItems.PEPPERCORN.get());

                        // ── Flour & Dough (non-edible intermediates) ──────────────────
                        output.accept(GotModItems.WHEAT_FLOUR.get());
                        output.accept(GotModItems.WHEAT_DOUGH.get());
                        output.accept(GotModItems.OAT_FLOUR.get());
                        output.accept(GotModItems.OAT_DOUGH.get());
                        output.accept(GotModItems.RYE_FLOUR.get());
                        output.accept(GotModItems.RYE_DOUGH.get());
                        output.accept(GotModItems.BARLEY_FLOUR.get());
                        output.accept(GotModItems.BARLEY_DOUGH.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 7 — GOT: SPAWN EGGS
     * All NPC, soldier, mount, and wildlife spawn eggs.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_SPAWN_EGGS =
            REGISTRY.register("got_spawn_eggs", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_INGREDIENTS.getId())
                    .title(Component.translatable("itemGroup.got.got_spawn_eggs"))
                    .icon(() -> new ItemStack(GotModItems.NORTHMAN_SPAWN_EGG.get()))
                    .displayItems((params, output) -> {

                        // ── Smallfolk (Tier 1) ────────────────────────────────────────
                        output.accept(GotModItems.NORTHMAN_SPAWN_EGG.get());
                        output.accept(GotModItems.RIVERLANDER_SPAWN_EGG.get());
                        output.accept(GotModItems.VALEMAN_SPAWN_EGG.get());
                        output.accept(GotModItems.WESTERMAN_SPAWN_EGG.get());
                        output.accept(GotModItems.STORMLORDER_SPAWN_EGG.get());
                        output.accept(GotModItems.IRONBORN_SPAWN_EGG.get());
                        output.accept(GotModItems.DORNISHMAN_SPAWN_EGG.get());
                        output.accept(GotModItems.REACHMAN_SPAWN_EGG.get());

                        // ── Levies (Tier 2) ───────────────────────────────────────────
                        output.accept(GotModItems.STARK_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.TULLY_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.LANNISTER_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.BARATHEON_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.GREYJOY_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.MARTELL_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.TYRELL_LEVY_SPAWN_EGG.get());
                        output.accept(GotModItems.ARRYN_LEVY_SPAWN_EGG.get());

                        // ── Skilled Fighters (Tier 3) ─────────────────────────────────
                        output.accept(GotModItems.NORTH_SOLDIER_SPAWN_EGG.get());
                        output.accept(GotModItems.VALE_KNIGHT_SPAWN_EGG.get());

                        // ── Mounts & Wildlife ─────────────────────────────────────────
                        output.accept(GotModItems.GOT_STAG_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_HERON_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_DIREWOLF_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_CROW_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_MAMMOTH_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_BROWN_BEAR_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_GIANT_SPAWN_EGG.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 8 — GOT: VERTICAL SLABS
     * Vertical-slab variants of every slab in the mod, plus every vanilla slab.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_VERTICAL_SLABS =
            REGISTRY.register("got_vertical_slabs", () -> CreativeModeTab.builder()
                    .withTabsBefore(GOT_SPAWN_EGGS.getId())
                    .title(Component.translatable("itemGroup.got.got_vertical_slabs"))
                    .icon(() -> new ItemStack(GotModBlocks.OAK_VERTICAL_SLAB.get()))
                    .displayItems((params, output) -> {

                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ACACIA_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ALMOND_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.APRICOT_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BAMBOO_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BASALT_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKTHORN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BROWN_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COARSE_DIRT_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRABAPPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_BASALT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_FLINT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_FUSED_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_GREY_GRANITE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_LIMESTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_MARBLE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_OILY_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_SLATE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CYAN_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_THATCH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DATE_PALM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DIRT_PATH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DIRT_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIELDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIG_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FLINT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FLINT_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.FUSED_BLACK_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GRASS_BLOCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GRAY_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GREEN_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GREY_GRANITE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.HEMLOCK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LEMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIGHT_THATCH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIME_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LIMESTONE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAGENTA_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MARBLE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_BASALT_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FLINT_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_FUSED_BLACK_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_GREY_GRANITE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_LIMESTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_MARBLE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_OILY_BLACK_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_SLATE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MUD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NIGHTWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NUTMEG_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OILY_BLACK_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OLIVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ORANGE_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PALE_OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEACH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PEAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PERSIMMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_IVORY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PINK_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PLUM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PODZOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POMEGRANATE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PRUNE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLE_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPLEHEART_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ROOTED_DIRT_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDALWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDBEGGAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SLATE_SHINGLES_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_BASALT_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_FUSED_BLACK_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_GREY_GRANITE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_LIMESTONE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_MARBLE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_OILY_BLACK_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SLATE_ROCK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALMOND_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APRICOT_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BEECH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKTHORN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CRABAPPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_DATE_PALM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIG_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HEMLOCK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LEMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LIME_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NIGHTWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_NUTMEG_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_OLIVE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ORANGE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PALE_OAK_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEACH_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PEAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PERSIMMON_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINK_IVORY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PLUM_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_POMEGRANATE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PRUNE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PURPLEHEART_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_RED_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDALWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SANDBEGGAR_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_TIGERWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WHITE_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.TIGERWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_CHERRY_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WHITE_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.YELLOW_WOOL_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ACACIA_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BAMBOO_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BAMBOO_MOSAIC_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CRIMSON_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.WARPED_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PALE_OAK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PETRIFIED_OAK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_STONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.GRANITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_GRANITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DIORITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_DIORITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.ANDESITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MUD_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.NETHER_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.STONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.END_STONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PRISMARINE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_PRISMARINE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.PURPUR_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.TUFF_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_TUFF_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.TUFF_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RESIN_BRICK_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CUT_SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.QUARTZ_VERTICAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}