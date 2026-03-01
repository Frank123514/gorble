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

                        // ── Crownlands
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
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_CROWNLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.CROWNLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_CROWNLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_CROWNLANDS_ROCK_PRESSURE_PLATE.get());

                        // ── Dorne
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
                        safeAccept(output, GotModBlocks.DORNE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.DORNE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_DORNE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.DORNE_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_DORNE_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_DORNE_ROCK_PRESSURE_PLATE.get());

                        // ── Iron Islands
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
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_IRON_ISLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.IRON_ISLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_IRON_ISLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_IRON_ISLANDS_ROCK_PRESSURE_PLATE.get());

                        // ── North
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
                        safeAccept(output, GotModBlocks.NORTH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.NORTH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_NORTH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.NORTH_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_NORTH_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_NORTH_ROCK_PRESSURE_PLATE.get());

                        // ── Reach
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
                        safeAccept(output, GotModBlocks.REACH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.REACH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_REACH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.REACH_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_REACH_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_REACH_ROCK_PRESSURE_PLATE.get());

                        // ── Riverlands
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
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_RIVERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.RIVERLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_RIVERLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_RIVERLANDS_ROCK_PRESSURE_PLATE.get());

                        // ── Stormlands
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
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_STORMLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.STORMLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_STORMLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_STORMLANDS_ROCK_PRESSURE_PLATE.get());

                        // ── Vale
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
                        safeAccept(output, GotModBlocks.VALE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.VALE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_VALE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.VALE_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_VALE_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_VALE_ROCK_PRESSURE_PLATE.get());

                        // ── Westerlands
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
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.CRACKED_WESTERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_BRICK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.WESTERLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_SLAB.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_STAIRS.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_WALL.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_BUTTON.get());
                        safeAccept(output, GotModBlocks.MOSSY_WESTERLANDS_COBBLESTONE_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.POLISHED_WESTERLANDS_ROCK_PRESSURE_PLATE.get());
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

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}