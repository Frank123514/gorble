package net.got.init;

import net.got.GotMod;
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
                        safeAccept(output, GotModBlocks.WEIRWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_BUTTON.get());

                        // ── Aspen ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASPEN_LOG.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD.get());
                        safeAccept(output, GotModBlocks.ASPEN_PLANKS.get());
                        safeAccept(output, GotModBlocks.ASPEN_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASPEN_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_FENCE.get());
                        safeAccept(output, GotModBlocks.ASPEN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ASPEN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ASPEN_BUTTON.get());

                        // ── Alder ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ALDER_LOG.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD.get());
                        safeAccept(output, GotModBlocks.ALDER_PLANKS.get());
                        safeAccept(output, GotModBlocks.ALDER_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALDER_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_FENCE.get());
                        safeAccept(output, GotModBlocks.ALDER_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ALDER_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ALDER_BUTTON.get());

                        // ── Pine ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PINE_LOG.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.PINE_PLANKS.get());
                        safeAccept(output, GotModBlocks.PINE_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINE_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_FENCE.get());
                        safeAccept(output, GotModBlocks.PINE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.PINE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.PINE_BUTTON.get());

                        // ── Fir ───────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FIR_LOG.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD.get());
                        safeAccept(output, GotModBlocks.FIR_PLANKS.get());
                        safeAccept(output, GotModBlocks.FIR_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIR_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_FENCE.get());
                        safeAccept(output, GotModBlocks.FIR_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.FIR_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.FIR_BUTTON.get());

                        // ── Sentinal ──────────────────────────────────────
                        safeAccept(output, GotModBlocks.SENTINAL_LOG.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD.get());
                        safeAccept(output, GotModBlocks.SENTINAL_PLANKS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_STAIRS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_FENCE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SENTINAL_BUTTON.get());

                        // ── Ironwood ──────────────────────────────────────
                        safeAccept(output, GotModBlocks.IRONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_PLANKS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_FENCE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_BUTTON.get());

                        // ── Beech ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BEECH_LOG.get());
                        safeAccept(output, GotModBlocks.BEECH_WOOD.get());
                        safeAccept(output, GotModBlocks.BEECH_PLANKS.get());
                        safeAccept(output, GotModBlocks.BEECH_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE.get());
                        safeAccept(output, GotModBlocks.BEECH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.BEECH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.BEECH_BUTTON.get());

                        // ── Soldier Pine ──────────────────────────────────
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PLANKS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_BUTTON.get());

                        // ── Ash ───────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASH_LOG.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD.get());
                        safeAccept(output, GotModBlocks.ASH_PLANKS.get());
                        safeAccept(output, GotModBlocks.ASH_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE.get());
                        safeAccept(output, GotModBlocks.ASH_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.ASH_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.ASH_BUTTON.get());

                        // ── Hawthorn ──────────────────────────────────────
                        safeAccept(output, GotModBlocks.HAWTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_PLANKS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_FENCE_GATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_BUTTON.get());
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
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}