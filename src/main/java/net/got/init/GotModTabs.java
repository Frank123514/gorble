package net.got.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.got.GotMod;

@EventBusSubscriber
public class GotModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GotMod.MODID);

    @SubscribeEvent
    public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
        if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            tabData.accept(GotModBlocks.WEIRWOOD_LOG.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_WOOD.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_PLANKS.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_STAIRS.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_SLAB.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_FENCE.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_FENCE_GATE.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_PRESSURE_PLATE.get().asItem());
            tabData.accept(GotModBlocks.WEIRWOOD_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_LOG.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_WOOD.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_PLANKS.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_STAIRS.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_SLAB.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_FENCE.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_FENCE_GATE.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_PRESSURE_PLATE.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.ALDER_LOG.get().asItem());
            tabData.accept(GotModBlocks.ALDER_WOOD.get().asItem());
            tabData.accept(GotModBlocks.ALDER_PLANKS.get().asItem());
            tabData.accept(GotModBlocks.ALDER_STAIRS.get().asItem());
            tabData.accept(GotModBlocks.ALDER_SLAB.get().asItem());
            tabData.accept(GotModBlocks.ALDER_FENCE.get().asItem());
            tabData.accept(GotModBlocks.ALDER_FENCE_GATE.get().asItem());
            tabData.accept(GotModBlocks.ALDER_PRESSURE_PLATE.get().asItem());
            tabData.accept(GotModBlocks.ALDER_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.PINE_LOG.get().asItem());
			tabData.accept(GotModBlocks.PINE_WOOD.get().asItem());
			tabData.accept(GotModBlocks.PINE_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.PINE_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.PINE_FENCE.get().asItem());
			tabData.accept(GotModBlocks.PINE_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.PINE_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.PINE_BUTTON.get().asItem());
			tabData.accept(GotModBlocks.FIR_LOG.get().asItem());
			tabData.accept(GotModBlocks.FIR_WOOD.get().asItem());
			tabData.accept(GotModBlocks.FIR_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.FIR_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.FIR_SLAB.get().asItem());
			tabData.accept(GotModBlocks.FIR_FENCE.get().asItem());
			tabData.accept(GotModBlocks.FIR_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.FIR_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.FIR_BUTTON.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_LOG.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_WOOD.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_SLAB.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_FENCE.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_BUTTON.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_LOG.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_WOOD.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_SLAB.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_FENCE.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.BEECH_LOG.get().asItem());
			tabData.accept(GotModBlocks.BEECH_WOOD.get().asItem());
			tabData.accept(GotModBlocks.BEECH_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.BEECH_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.BEECH_SLAB.get().asItem());
			tabData.accept(GotModBlocks.BEECH_FENCE.get().asItem());
			tabData.accept(GotModBlocks.BEECH_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.BEECH_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.BEECH_BUTTON.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_LOG.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_WOOD.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_PLANKS.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_STAIRS.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_SLAB.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_FENCE.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_FENCE_GATE.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_PRESSURE_PLATE.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.ASH_LOG.get().asItem());
            tabData.accept(GotModBlocks.ASH_WOOD.get().asItem());
            tabData.accept(GotModBlocks.ASH_PLANKS.get().asItem());
            tabData.accept(GotModBlocks.ASH_STAIRS.get().asItem());
            tabData.accept(GotModBlocks.ASH_SLAB.get().asItem());
            tabData.accept(GotModBlocks.ASH_FENCE.get().asItem());
            tabData.accept(GotModBlocks.ASH_FENCE_GATE.get().asItem());
            tabData.accept(GotModBlocks.ASH_PRESSURE_PLATE.get().asItem());
            tabData.accept(GotModBlocks.ASH_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_LOG.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_WOOD.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_BUTTON.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_PLANKS.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_STAIRS.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_SLAB.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_FENCE.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_FENCE_GATE.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_PRESSURE_PLATE.get().asItem());
        } else if (tabData.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            tabData.accept(GotModBlocks.WEIRWOOD_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.ASPEN_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.ALDER_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.PINE_LEAVES.get().asItem());
			tabData.accept(GotModBlocks.FIR_LEAVES.get().asItem());
			tabData.accept(GotModBlocks.SENTINAL_LEAVES.get().asItem());
			tabData.accept(GotModBlocks.IRONWOOD_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.BEECH_LEAVES.get().asItem());
			tabData.accept(GotModBlocks.SOLDIER_PINE_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.ASH_LEAVES.get().asItem());
            tabData.accept(GotModBlocks.HAWTHORN_LEAVES.get().asItem());
        }
    }
}