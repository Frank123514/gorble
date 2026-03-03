package net.got.init;

import net.got.GotMod;
import net.got.init.GotModBlocks;
import net.got.init.GotModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
                                   net.minecraft.world.level.block.Block block) {
        try {
            ItemStack stack = new ItemStack(block);
            if (!stack.isEmpty() && stack.getCount() == 1) {
                output.accept(stack);
            }
        } catch (Exception ignored) {
            // Block has no item form yet — skip silently
        }
    }

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 1 — GOT: BUILDING BLOCKS
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_BUILDING =
            REGISTRY.register("got_building", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_building"))
                    .icon(() -> new ItemStack(GotModBlocks.NORTH_ROCK.get()))
                    .displayItems((params, output) -> {

                        safeAccept(output, GotModBlocks.NORTH_ROCK.get());

                        // ── Weirwood ──────────────────────────────────────
                        safeAccept(output, GotModBlocks.WEIRWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.ASPEN_LEAVES.get());
                        safeAccept(output, GotModBlocks.ALDER_LEAVES.get());
                        safeAccept(output, GotModBlocks.PINE_LEAVES.get());
                        safeAccept(output, GotModBlocks.FIR_LEAVES.get());
                        safeAccept(output, GotModBlocks.SENTINAL_LEAVES.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_LEAVES.get());
                        safeAccept(output, GotModBlocks.BEECH_LEAVES.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LEAVES.get());
                        safeAccept(output, GotModBlocks.ASH_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LEAVES.get());
                        safeAccept(output, GotModBlocks.BURL_LEAVES.get());
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
                        safeAccept(output, GotModBlocks.HAWTHORN_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_BUTTON.get());

                        // ── Beech
                        safeAccept(output, GotModBlocks.BEECH_LOG.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD.get());
                        safeAccept(output, GotModBlocks.BEECH_PLANKS.get());
                        safeAccept(output, GotModBlocks.BEECH_LEAVES.get());
                        safeAccept(output, GotModBlocks.BEECH_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BEECH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BEECH_BUTTON.get());

                        // ── Soldier Pine
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PLANKS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LEAVES.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_BUTTON.get());

                        // ── Ash
                        safeAccept(output, GotModBlocks.ASH_LOG.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD.get());
                        safeAccept(output, GotModBlocks.ASH_PLANKS.get());
                        safeAccept(output, GotModBlocks.ASH_LEAVES.get());
                        safeAccept(output, GotModBlocks.ASH_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ASH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ASH_BUTTON.get());

                        // ── Black-Barked
                        safeAccept(output, GotModBlocks.BLACKBARK_LOG.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_BUTTON.get());

                        // ── Bloodwood
                        safeAccept(output, GotModBlocks.BLOODWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_BUTTON.get());

                        // ── Blue Mahoe
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LOG.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_FENCE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_BUTTON.get());

                        // ── Burl
                        safeAccept(output, GotModBlocks.BURL_LOG.get());
                        safeAccept(output, GotModBlocks.BURL_WOOD.get());
                        safeAccept(output, GotModBlocks.BURL_PLANKS.get());
                        safeAccept(output, GotModBlocks.BURL_LEAVES.get());
                        safeAccept(output, GotModBlocks.BURL_STAIRS.get());
                        safeAccept(output, GotModBlocks.BURL_SLAB.get());
                        safeAccept(output, GotModBlocks.BURL_FENCE.get());
                        safeAccept(output, GotModBlocks.BURL_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BURL_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BURL_BUTTON.get());

                        // ── Cottonwood
                        safeAccept(output, GotModBlocks.COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_BUTTON.get());

                        // ── Black Cottonwood
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_BUTTON.get());

                        // ── Cinnamon
                        safeAccept(output, GotModBlocks.CINNAMON_LOG.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD.get());
                        safeAccept(output, GotModBlocks.CINNAMON_PLANKS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_LEAVES.get());
                        safeAccept(output, GotModBlocks.CINNAMON_STAIRS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_FENCE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CINNAMON_BUTTON.get());

                        // ── Clove
                        safeAccept(output, GotModBlocks.CLOVE_LOG.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD.get());
                        safeAccept(output, GotModBlocks.CLOVE_PLANKS.get());
                        safeAccept(output, GotModBlocks.CLOVE_LEAVES.get());
                        safeAccept(output, GotModBlocks.CLOVE_STAIRS.get());
                        safeAccept(output, GotModBlocks.CLOVE_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_FENCE.get());
                        safeAccept(output, GotModBlocks.CLOVE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CLOVE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CLOVE_BUTTON.get());

                        // ── Ebony
                        safeAccept(output, GotModBlocks.EBONY_LOG.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD.get());
                        safeAccept(output, GotModBlocks.EBONY_PLANKS.get());
                        safeAccept(output, GotModBlocks.EBONY_LEAVES.get());
                        safeAccept(output, GotModBlocks.EBONY_STAIRS.get());
                        safeAccept(output, GotModBlocks.EBONY_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_FENCE.get());
                        safeAccept(output, GotModBlocks.EBONY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.EBONY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.EBONY_BUTTON.get());

                        // ── Elm
                        safeAccept(output, GotModBlocks.ELM_LOG.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD.get());
                        safeAccept(output, GotModBlocks.ELM_PLANKS.get());
                        safeAccept(output, GotModBlocks.ELM_LEAVES.get());
                        safeAccept(output, GotModBlocks.ELM_STAIRS.get());
                        safeAccept(output, GotModBlocks.ELM_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_FENCE.get());
                        safeAccept(output, GotModBlocks.ELM_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ELM_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ELM_BUTTON.get());

                        // ── Cedar
                        safeAccept(output, GotModBlocks.CEDAR_LOG.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD.get());
                        safeAccept(output, GotModBlocks.CEDAR_PLANKS.get());
                        safeAccept(output, GotModBlocks.CEDAR_LEAVES.get());
                        safeAccept(output, GotModBlocks.CEDAR_STAIRS.get());
                        safeAccept(output, GotModBlocks.CEDAR_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_FENCE.get());
                        safeAccept(output, GotModBlocks.CEDAR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CEDAR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CEDAR_BUTTON.get());

                        // ── Apple
                        safeAccept(output, GotModBlocks.APPLE_LOG.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.APPLE_PLANKS.get());
                        safeAccept(output, GotModBlocks.APPLE_LEAVES.get());
                        safeAccept(output, GotModBlocks.APPLE_STAIRS.get());
                        safeAccept(output, GotModBlocks.APPLE_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_FENCE.get());
                        safeAccept(output, GotModBlocks.APPLE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.APPLE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.APPLE_BUTTON.get());

                        // ── Goldenheart
                        safeAccept(output, GotModBlocks.GOLDENHEART_LOG.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_PLANKS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_LEAVES.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_STAIRS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_FENCE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_BUTTON.get());

                        // ── Linden
                        safeAccept(output, GotModBlocks.LINDEN_LOG.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD.get());
                        safeAccept(output, GotModBlocks.LINDEN_PLANKS.get());
                        safeAccept(output, GotModBlocks.LINDEN_LEAVES.get());
                        safeAccept(output, GotModBlocks.LINDEN_STAIRS.get());
                        safeAccept(output, GotModBlocks.LINDEN_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_FENCE.get());
                        safeAccept(output, GotModBlocks.LINDEN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.LINDEN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.LINDEN_BUTTON.get());

                        // ── Mahogany
                        safeAccept(output, GotModBlocks.MAHOGANY_LOG.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_PLANKS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_LEAVES.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_FENCE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_BUTTON.get());

                        // ── Maple
                        safeAccept(output, GotModBlocks.MAPLE_LOG.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.MAPLE_PLANKS.get());
                        safeAccept(output, GotModBlocks.MAPLE_LEAVES.get());
                        safeAccept(output, GotModBlocks.MAPLE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAPLE_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_FENCE.get());
                        safeAccept(output, GotModBlocks.MAPLE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MAPLE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MAPLE_BUTTON.get());

                        // ── Myrrh
                        safeAccept(output, GotModBlocks.MYRRH_LOG.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD.get());
                        safeAccept(output, GotModBlocks.MYRRH_PLANKS.get());
                        safeAccept(output, GotModBlocks.MYRRH_LEAVES.get());
                        safeAccept(output, GotModBlocks.MYRRH_STAIRS.get());
                        safeAccept(output, GotModBlocks.MYRRH_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_FENCE.get());
                        safeAccept(output, GotModBlocks.MYRRH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.MYRRH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MYRRH_BUTTON.get());

                        // ── Redwood
                        safeAccept(output, GotModBlocks.REDWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.REDWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_LEAVES.get());
                        safeAccept(output, GotModBlocks.REDWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.REDWOOD_BUTTON.get());

                        // ── Chestnut
                        safeAccept(output, GotModBlocks.CHESTNUT_LOG.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_PLANKS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_LEAVES.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_FENCE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_BUTTON.get());

                        // ── Willow
                        safeAccept(output, GotModBlocks.WILLOW_LOG.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD.get());
                        safeAccept(output, GotModBlocks.WILLOW_PLANKS.get());
                        safeAccept(output, GotModBlocks.WILLOW_LEAVES.get());
                        safeAccept(output, GotModBlocks.WILLOW_STAIRS.get());
                        safeAccept(output, GotModBlocks.WILLOW_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_FENCE.get());
                        safeAccept(output, GotModBlocks.WILLOW_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WILLOW_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WILLOW_BUTTON.get());

                        // ── Wormtree
                        safeAccept(output, GotModBlocks.WORMTREE_LOG.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD.get());
                        safeAccept(output, GotModBlocks.WORMTREE_PLANKS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_LEAVES.get());
                        safeAccept(output, GotModBlocks.WORMTREE_STAIRS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_FENCE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WORMTREE_BUTTON.get());

                        // ── Crownlands
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_PILLAR.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_WALL.get());

                        // ── Dorne
                        safeAccept(output, GotModBlocks.DORNE_ROCK.get());
                        safeAccept(output, GotModBlocks.DORNE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK.get());
                        safeAccept(output, GotModBlocks.DORNE_PILLAR.get());
                        safeAccept(output, GotModBlocks.DORNE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.DORNE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.DORNE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.DORNE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.DORNE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.DORNE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.DORNE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.DORNE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_WALL.get());

                        // ── Iron Islands
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_PILLAR.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_WALL.get());

                        // ── North
                        safeAccept(output, GotModBlocks.NORTH_ROCK.get());
                        safeAccept(output, GotModBlocks.NORTH_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK.get());
                        safeAccept(output, GotModBlocks.NORTH_PILLAR.get());
                        safeAccept(output, GotModBlocks.NORTH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.NORTH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.NORTH_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.NORTH_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.NORTH_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.NORTH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.NORTH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.NORTH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_WALL.get());

                        // ── Reach
                        safeAccept(output, GotModBlocks.REACH_ROCK.get());
                        safeAccept(output, GotModBlocks.REACH_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK.get());
                        safeAccept(output, GotModBlocks.REACH_PILLAR.get());
                        safeAccept(output, GotModBlocks.REACH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.REACH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.REACH_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.REACH_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.REACH_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.REACH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.REACH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.REACH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_WALL.get());

                        // ── Riverlands
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_PILLAR.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_WALL.get());

                        // ── Stormlands
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_PILLAR.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_WALL.get());

                        // ── Vale
                        safeAccept(output, GotModBlocks.VALE_ROCK.get());
                        safeAccept(output, GotModBlocks.VALE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK.get());
                        safeAccept(output, GotModBlocks.VALE_PILLAR.get());
                        safeAccept(output, GotModBlocks.VALE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.VALE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.VALE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.VALE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.VALE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.VALE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.VALE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.VALE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_WALL.get());

                        // ── Westerlands
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_PILLAR.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_WALL.get());
                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 2 — GOT: NATURAL BLOCKS
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_NATURE =
            REGISTRY.register("got_nature", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_nature"))
                    .icon(() -> new ItemStack(GotModBlocks.WEIRWOOD_LEAVES.get()))
                    .displayItems((params, output) -> {
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
                        safeAccept(output, GotModBlocks.NORTH_ROCK.get());
                        // ── Crownlands ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CROWNLANDS_ROCK.get());

                        // ── Dorne ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.DORNE_ROCK.get());

                        // ── Iron Islands ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_ROCK.get());

                        // ── North ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.NORTH_ROCK.get());

                        // ── Reach ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.REACH_ROCK.get());

                        // ── Riverlands ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.RIVERLANDS_ROCK.get());

                        // ── Stormlands ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.STORMLANDS_ROCK.get());

                        // ── Vale ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.VALE_ROCK.get());

                        // ── Westerlands ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WESTERLANDS_ROCK.get());
                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 3 — GOT: MATERIALS (ores, gems, ingots, tools, armour)
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_MATERIALS =
            REGISTRY.register("got_materials", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_materials"))
                    .icon(() -> new ItemStack(GotModItems.VALYRIAN_STEEL_INGOT.get()))
                    .displayItems((params, output) -> {

                        // ── Ore blocks ──────────────────────────────────
                        output.accept(GotModItems.AMBER_ORE.get());
                        output.accept(GotModItems.AMETHYST_ORE.get());
                        output.accept(GotModItems.COPPER_ORE.get());
                        output.accept(GotModItems.DRAGONGLASS_ORE.get());
                        output.accept(GotModItems.OPAL_ORE.get());
                        output.accept(GotModItems.RUBY_ORE.get());
                        output.accept(GotModItems.SAPPHIRE_ORE.get());
                        output.accept(GotModItems.SILVER_ORE.get());
                        output.accept(GotModItems.TIN_ORE.get());
                        output.accept(GotModItems.TOPAZ_ORE.get());
                        output.accept(GotModItems.VALYRIAN_STEEL_ORE.get());

                        // ── Gemstones ────────────────────────────────────
                        output.accept(GotModItems.AMBER.get());
                        output.accept(GotModItems.AMETHYST.get());
                        output.accept(GotModItems.DRAGONGLASS_SHARD.get());
                        output.accept(GotModItems.OPAL.get());
                        output.accept(GotModItems.RUBY.get());
                        output.accept(GotModItems.SAPPHIRE.get());
                        output.accept(GotModItems.TOPAZ.get());

                        // ── Raw ores ─────────────────────────────────────
                        output.accept(GotModItems.RAW_COPPER.get());
                        output.accept(GotModItems.RAW_SILVER.get());
                        output.accept(GotModItems.RAW_TIN.get());
                        output.accept(GotModItems.RAW_VALYRIAN_STEEL.get());

                        // ── Ingots ───────────────────────────────────────
                        output.accept(GotModItems.COPPER_INGOT.get());
                        output.accept(GotModItems.SILVER_INGOT.get());
                        output.accept(GotModItems.TIN_INGOT.get());
                        output.accept(GotModItems.BRONZE_INGOT.get());
                        output.accept(GotModItems.VALYRIAN_STEEL_INGOT.get());

                        // ── Copper tools ─────────────────────────────────
                        output.accept(GotModItems.COPPER_SWORD.get());
                        output.accept(GotModItems.COPPER_PICKAXE.get());
                        output.accept(GotModItems.COPPER_AXE.get());
                        output.accept(GotModItems.COPPER_SHOVEL.get());
                        output.accept(GotModItems.COPPER_HOE.get());

                        // ── Copper armour ────────────────────────────────
                        output.accept(GotModItems.COPPER_HELMET.get());
                        output.accept(GotModItems.COPPER_CHESTPLATE.get());
                        output.accept(GotModItems.COPPER_LEGGINGS.get());
                        output.accept(GotModItems.COPPER_BOOTS.get());

                        // ── Bronze tools ─────────────────────────────────
                        output.accept(GotModItems.BRONZE_SWORD.get());
                        output.accept(GotModItems.BRONZE_PICKAXE.get());
                        output.accept(GotModItems.BRONZE_AXE.get());
                        output.accept(GotModItems.BRONZE_SHOVEL.get());
                        output.accept(GotModItems.BRONZE_HOE.get());

                        // ── Bronze armour ────────────────────────────────
                        output.accept(GotModItems.BRONZE_HELMET.get());
                        output.accept(GotModItems.BRONZE_CHESTPLATE.get());
                        output.accept(GotModItems.BRONZE_LEGGINGS.get());
                        output.accept(GotModItems.BRONZE_BOOTS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}