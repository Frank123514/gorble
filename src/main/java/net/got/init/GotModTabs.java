package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
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

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 1 — GOT: MASONRY
     * All stone, brick, cobblestone, and rock-based building blocks.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_MASONRY =
            REGISTRY.register("got_masonry", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_masonry"))
                    .icon(() -> new ItemStack(GotModBlocks.LIMESTONE_BRICK.get()))
                    .displayItems((params, output) -> {

                        // ── Basalt ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BASALT_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_BASALT_ROCK.get());
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
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_RED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_PILLAR.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.RED_SANDSTONE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_RED_SANDSTONE_ROCK_STAIRS.get());
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
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SANDSTONE_ROCK.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_COBBLESTONE.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.CRACKED_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.MOSSY_SANDSTONE_BRICK.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_PILLAR.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_STAIRS.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_WALL.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_BUTTON.get());
                        safeAccept(output, GotModBlocks.SANDSTONE_ROCK_PRESSURE_PLATE.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SANDSTONE_ROCK_SLAB.get());
                        safeAccept(output, GotModBlocks.SMOOTH_SANDSTONE_ROCK_STAIRS.get());
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

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 2 — GOT: CARPENTRY
     * All wood types: logs, planks, stairs, slabs, fences, doors,
     * trapdoors, pressure plates, buttons, branches, roofing, signs,
     * hanging signs, boats, and chest boats.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_CARPENTRY =
            REGISTRY.register("got_carpentry", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_carpentry"))
                    .icon(() -> new ItemStack(GotModBlocks.WEIRWOOD_PLANKS.get()))
                    .displayItems((params, output) -> {

                        // ── Acacia (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ACACIA_ROOFING_WALL.get());

                        // ── Alder ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ALDER_LOG.get());
                        safeAccept(output, GotModBlocks.ALDER_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ALDER_WOOD.get());
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
                        safeAccept(output, GotModBlocks.ALDER_ROOFING.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ALDER_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ALDER_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ALDER_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ALDER_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.ALDER_CHEST_BOAT.get()));

                        // ── Apple ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.APPLE_LOG.get());
                        safeAccept(output, GotModBlocks.APPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_APPLE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.APPLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.APPLE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.APPLE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.APPLE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.APPLE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.APPLE_CHEST_BOAT.get()));

                        // ── Ash ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASH_LOG.get());
                        safeAccept(output, GotModBlocks.ASH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASH_WOOD.get());
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
                        safeAccept(output, GotModBlocks.ASH_ROOFING.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ASH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASH_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASH_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASH_CHEST_BOAT.get()));

                        // ── Aspen ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ASPEN_LOG.get());
                        safeAccept(output, GotModBlocks.ASPEN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ASPEN_WOOD.get());
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
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ASPEN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.ASPEN_CHEST_BOAT.get()));

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
                        safeAccept(output, GotModBlocks.BEECH_ROOFING.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BEECH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BEECH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BEECH_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BEECH_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.BEECH_CHEST_BOAT.get()));

                        // ── Birch (vanilla) ───────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BIRCH_ROOFING_WALL.get());

                        // ── Blackbark ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACKBARK_LOG.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACKBARK_WOOD.get());
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
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACKBARK_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACKBARK_CHEST_BOAT.get()));

                        // ── Black Cottonwood ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLACK_COTTONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLACK_COTTONWOOD_CHEST_BOAT.get()));

                        // ── Bloodwood ─────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLOODWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLOODWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLOODWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLOODWOOD_CHEST_BOAT.get()));

                        // ── Blue Mahoe ────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_LOG.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_BLUE_MAHOE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.BLUE_MAHOE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.BLUE_MAHOE_CHEST_BOAT.get()));

                        // ── Cedar ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CEDAR_LOG.get());
                        safeAccept(output, GotModBlocks.CEDAR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CEDAR_WOOD.get());
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
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CEDAR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.CEDAR_CHEST_BOAT.get()));

                        // ── Cherry (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHERRY_ROOFING_WALL.get());

                        // ── Chestnut ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CHESTNUT_LOG.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CHESTNUT_WOOD.get());
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
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CHESTNUT_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.CHESTNUT_CHEST_BOAT.get()));

                        // ── Cinnamon ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CINNAMON_LOG.get());
                        safeAccept(output, GotModBlocks.CINNAMON_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CINNAMON_WOOD.get());
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
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CINNAMON_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.CINNAMON_CHEST_BOAT.get()));

                        // ── Clove ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CLOVE_LOG.get());
                        safeAccept(output, GotModBlocks.CLOVE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_CLOVE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CLOVE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.CLOVE_CHEST_BOAT.get()));

                        // ── Cottonwood ────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_COTTONWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.COTTONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.COTTONWOOD_CHEST_BOAT.get()));

                        // ── Crimson (vanilla) ─────────────────────────────────────────
                        safeAccept(output, GotModBlocks.CRIMSON_ROOFING.get());
                        safeAccept(output, GotModBlocks.CRIMSON_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.CRIMSON_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.CRIMSON_ROOFING_WALL.get());

                        // ── Dark Oak (vanilla) ────────────────────────────────────────
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.DARK_OAK_ROOFING_WALL.get());

                        // ── Ebony ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.EBONY_LOG.get());
                        safeAccept(output, GotModBlocks.EBONY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_EBONY_WOOD.get());
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
                        safeAccept(output, GotModBlocks.EBONY_ROOFING.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.EBONY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.EBONY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.EBONY_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.EBONY_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.EBONY_CHEST_BOAT.get()));

                        // ── Elm ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.ELM_LOG.get());
                        safeAccept(output, GotModBlocks.ELM_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_ELM_WOOD.get());
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
                        safeAccept(output, GotModBlocks.ELM_ROOFING.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.ELM_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.ELM_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ELM_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.ELM_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.ELM_CHEST_BOAT.get()));

                        // ── Fir ───────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.FIR_LOG.get());
                        safeAccept(output, GotModBlocks.FIR_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_FIR_WOOD.get());
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
                        safeAccept(output, GotModBlocks.FIR_ROOFING.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.FIR_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.FIR_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.FIR_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.FIR_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.FIR_CHEST_BOAT.get()));

                        // ── Goldenheart ───────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.GOLDENHEART_LOG.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_GOLDENHEART_WOOD.get());
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
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.GOLDENHEART_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.GOLDENHEART_CHEST_BOAT.get()));

                        // ── Hawthorn ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.HAWTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_HAWTHORN_WOOD.get());
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
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.HAWTHORN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.HAWTHORN_CHEST_BOAT.get()));

                        // ── Ironwood ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.IRONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_IRONWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.IRONWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.IRONWOOD_CHEST_BOAT.get()));

                        // ── Jungle (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.JUNGLE_ROOFING_WALL.get());

                        // ── Linden ────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.LINDEN_LOG.get());
                        safeAccept(output, GotModBlocks.LINDEN_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_LINDEN_WOOD.get());
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
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.LINDEN_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.LINDEN_CHEST_BOAT.get()));

                        // ── Mahogany ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MAHOGANY_LOG.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAHOGANY_WOOD.get());
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
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAHOGANY_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAHOGANY_CHEST_BOAT.get()));

                        // ── Mangrove (vanilla) ────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MANGROVE_ROOFING_WALL.get());

                        // ── Maple ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MAPLE_LOG.get());
                        safeAccept(output, GotModBlocks.MAPLE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MAPLE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MAPLE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.MAPLE_CHEST_BOAT.get()));

                        // ── Myrrh ─────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.MYRRH_LOG.get());
                        safeAccept(output, GotModBlocks.MYRRH_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_MYRRH_WOOD.get());
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
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.MYRRH_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.MYRRH_CHEST_BOAT.get()));

                        // ── Oak (vanilla) ─────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OAK_ROOFING.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.OAK_ROOFING_WALL.get());

                        // ── Pine ──────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.PINE_LOG.get());
                        safeAccept(output, GotModBlocks.PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_PINE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.PINE_ROOFING.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.PINE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.PINE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PINE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.PINE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.PINE_CHEST_BOAT.get()));

                        // ── Redwood ───────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.REDWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.REDWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_REDWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.REDWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.REDWOOD_CHEST_BOAT.get()));

                        // ── Sentinal ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SENTINAL_LOG.get());
                        safeAccept(output, GotModBlocks.SENTINAL_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SENTINAL_WOOD.get());
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
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SENTINAL_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.SENTINAL_CHEST_BOAT.get()));

                        // ── Soldier Pine ──────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_SOLDIER_PINE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SOLDIER_PINE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.SOLDIER_PINE_CHEST_BOAT.get()));

                        // ── Spruce (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.SPRUCE_ROOFING_WALL.get());

                        // ── Warped (vanilla) ──────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WARPED_ROOFING.get());
                        safeAccept(output, GotModBlocks.WARPED_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WARPED_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WARPED_ROOFING_WALL.get());

                        // ── Weirwood ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WEIRWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WEIRWOOD_WOOD.get());
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
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WEIRWOOD_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.WEIRWOOD_CHEST_BOAT.get()));

                        // ── Willow ────────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WILLOW_LOG.get());
                        safeAccept(output, GotModBlocks.WILLOW_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WILLOW_WOOD.get());
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
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WILLOW_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.WILLOW_CHEST_BOAT.get()));

                        // ── Wormtree ──────────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.WORMTREE_LOG.get());
                        safeAccept(output, GotModBlocks.WORMTREE_WOOD.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_LOG.get());
                        safeAccept(output, GotModBlocks.STRIPPED_WORMTREE_WOOD.get());
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
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_SLAB.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_STAIRS.get());
                        safeAccept(output, GotModBlocks.WORMTREE_ROOFING_WALL.get());
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_HANGING_SIGN.get()));
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_BOAT.get()));
                        safeAccept(output, Block.byItem(GotModItems.WORMTREE_CHEST_BOAT.get()));

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 3 — GOT: DECORATIVE BLOCKS
     * Leaves, saplings, flowers, grasses, wild plants, and the oven.
     * Ores, gems, ingots, and coins live in GOT: INGREDIENTS.
     * Tools and armour live in GOT: ARMORY.
     * Spawn eggs live in GOT: SPAWN EGGS.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_DECORATIVE =
            REGISTRY.register("got_decorative", () -> CreativeModeTab.builder()
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
                        safeAccept(output, GotModBlocks.GORSE.get());
                        safeAccept(output, GotModBlocks.LADYS_LACE.get());
                        safeAccept(output, GotModBlocks.LAVENDER.get());
                        safeAccept(output, GotModBlocks.LILAC_FLOWER.get());
                        safeAccept(output, GotModBlocks.LIVERWORT.get());
                        safeAccept(output, GotModBlocks.LUNGWORT.get());
                        safeAccept(output, GotModBlocks.MOONBLOOM.get());
                        safeAccept(output, GotModBlocks.NIGHTSHADE.get());
                        safeAccept(output, GotModBlocks.OPIUM_POPPY.get());
                        safeAccept(output, GotModBlocks.PENNYROYAL.get());
                        safeAccept(output, GotModBlocks.POISON_KISSES.get());
                        safeAccept(output, GotModBlocks.RED_ROSE.get());
                        safeAccept(output, GotModBlocks.SAFFRON_CROCUS.get());
                        safeAccept(output, GotModBlocks.SEDGE.get());
                        safeAccept(output, GotModBlocks.SPICEFLOWER.get());
                        safeAccept(output, GotModBlocks.TANSY.get());
                        safeAccept(output, GotModBlocks.THISTLE.get());
                        safeAccept(output, GotModBlocks.THORNBUSH.get());
                        safeAccept(output, GotModBlocks.WHITE_ROSE.get());
                        safeAccept(output, GotModBlocks.WILD_RADISH.get());
                        safeAccept(output, GotModBlocks.WINTER_ROSE.get());

                        // ── Grasses & Wetland Plants ──────────────────────────────────
                        safeAccept(output, GotModBlocks.DEVILGRASS.get());
                        safeAccept(output, GotModBlocks.GHOST_GRASS.get());
                        safeAccept(output, GotModBlocks.HRANNA.get());
                        safeAccept(output, GotModBlocks.PIPERS_GRASS.get());
                        safeAccept(output, GotModBlocks.WHEATGRASS.get());
                        safeAccept(output, GotModBlocks.REEDS.get());
                        safeAccept(output, GotModBlocks.SHORT_REEDS.get());
                        safeAccept(output, GotModBlocks.QUAGMIRE.get());

                        // ── Oven & Utility ────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.OVEN.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 4 — GOT: FOOD
     * Bread, vegetables, berries, wild crops, berry bushes, and crop blocks.
     * Seeds, grains, flour, and dough live in GOT: INGREDIENTS.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_FOOD =
            REGISTRY.register("got_food", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_food"))
                    .icon(() -> new ItemStack(GotModBlocks.OVEN.get()))
                    .displayItems((params, output) -> {

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

                        // ── Berries ───────────────────────────────────────────────────
                        output.accept(GotModItems.BLACKBERRIES.get());
                        output.accept(GotModItems.BLUEBERRIES.get());
                        output.accept(GotModItems.RASPBERRIES.get());
                        output.accept(GotModItems.STRAWBERRIES.get());

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

                        // ── Berry Bushes ──────────────────────────────────────────────
                        safeAccept(output, GotModBlocks.BLACKBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.BLUEBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.RASPBERRY_BUSH.get());
                        safeAccept(output, GotModBlocks.STRAWBERRY_BUSH.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 5 — GOT: ARMORY
     * All weapons (swords, axes, tools used as weapons) and armor sets.
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_ARMORY =
            REGISTRY.register("got_armory", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.got.got_armory"))
                    .icon(() -> new ItemStack(GotModItems.BRONZE_SWORD.get()))
                    .displayItems((params, output) -> {

                        // ── Copper Weapons ────────────────────────────────────────────
                        output.accept(GotModItems.COPPER_SWORD.get());
                        output.accept(GotModItems.COPPER_AXE.get());
                        output.accept(GotModItems.COPPER_PICKAXE.get());
                        output.accept(GotModItems.COPPER_SHOVEL.get());
                        output.accept(GotModItems.COPPER_HOE.get());

                        // ── Bronze Weapons ────────────────────────────────────────────
                        output.accept(GotModItems.BRONZE_SWORD.get());
                        output.accept(GotModItems.BRONZE_AXE.get());
                        output.accept(GotModItems.BRONZE_PICKAXE.get());
                        output.accept(GotModItems.BRONZE_SHOVEL.get());
                        output.accept(GotModItems.BRONZE_HOE.get());

                        // ── Copper Armor ──────────────────────────────────────────────
                        output.accept(GotModItems.COPPER_HELMET.get());
                        output.accept(GotModItems.COPPER_CHESTPLATE.get());
                        output.accept(GotModItems.COPPER_LEGGINGS.get());
                        output.accept(GotModItems.COPPER_BOOTS.get());

                        // ── Bronze Armor ──────────────────────────────────────────────
                        output.accept(GotModItems.BRONZE_HELMET.get());
                        output.accept(GotModItems.BRONZE_CHESTPLATE.get());
                        output.accept(GotModItems.BRONZE_LEGGINGS.get());
                        output.accept(GotModItems.BRONZE_BOOTS.get());

                    })
                    .build());

    /* ─────────────────────────────────────────────────────────────────────
     * TAB 6 — GOT: INGREDIENTS
     * Currency, gemstones, raw ores, ingots, seeds, and non-edible produce
     * (grains, cotton, peppercorn, flour, dough).
     * ───────────────────────────────────────────────────────────────────── */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GOT_INGREDIENTS =
            REGISTRY.register("got_ingredients", () -> CreativeModeTab.builder()
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
                        output.accept(GotModItems.DRAGONGLASS_SHARD.get());
                        output.accept(GotModItems.OPAL.get());
                        output.accept(GotModItems.RUBY.get());
                        output.accept(GotModItems.SAPPHIRE.get());
                        output.accept(GotModItems.TOPAZ.get());

                        // ── Raw Ores ──────────────────────────────────────────────────
                        output.accept(GotModItems.RAW_SILVER.get());
                        output.accept(GotModItems.RAW_TIN.get());
                        output.accept(GotModItems.RAW_VALYRIAN_STEEL.get());

                        // ── Ingots ────────────────────────────────────────────────────
                        output.accept(GotModItems.SILVER_INGOT.get());
                        output.accept(GotModItems.TIN_INGOT.get());
                        output.accept(GotModItems.BRONZE_INGOT.get());
                        output.accept(GotModItems.VALYRIAN_STEEL_INGOT.get());

                        // ── Seeds ─────────────────────────────────────────────────────
                        output.accept(GotModItems.OAT_SEEDS.get());
                        output.accept(GotModItems.RYE_SEEDS.get());
                        output.accept(GotModItems.BARLEY_SEEDS.get());
                        output.accept(GotModItems.COTTON_SEEDS.get());
                        output.accept(GotModItems.PEPPERCORN_SEEDS.get());

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
                        output.accept(GotModItems.GOT_HORSE_SPAWN_EGG.get());
                        output.accept(GotModItems.GOT_STAG_SPAWN_EGG.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}