package net.got.init;

import net.minecraft.world.item.AxeItem;

/**
 * Registers all GOT log → stripped-log stripping mappings.
 * Must be called from {@code FMLCommonSetupEvent.enqueueWork()}.
 *
 * Both the log AND the wood (bark) block strip to the stripped log,
 * since this mod does not have separate stripped-wood blocks.
 */
public final class GotStrippingHelper {

    public static void registerAll() {
        // weirwood
        strip(GotModBlocks.WEIRWOOD_LOG,       GotModBlocks.STRIPPED_WEIRWOOD_LOG);
        strip(GotModBlocks.WEIRWOOD_WOOD,      GotModBlocks.STRIPPED_WEIRWOOD_LOG);
        // aspen
        strip(GotModBlocks.ASPEN_LOG,          GotModBlocks.STRIPPED_ASPEN_LOG);
        strip(GotModBlocks.ASPEN_WOOD,         GotModBlocks.STRIPPED_ASPEN_LOG);
        // alder
        strip(GotModBlocks.ALDER_LOG,          GotModBlocks.STRIPPED_ALDER_LOG);
        strip(GotModBlocks.ALDER_WOOD,         GotModBlocks.STRIPPED_ALDER_LOG);
        // pine
        strip(GotModBlocks.PINE_LOG,           GotModBlocks.STRIPPED_PINE_LOG);
        strip(GotModBlocks.PINE_WOOD,          GotModBlocks.STRIPPED_PINE_LOG);
        // fir
        strip(GotModBlocks.FIR_LOG,            GotModBlocks.STRIPPED_FIR_LOG);
        strip(GotModBlocks.FIR_WOOD,           GotModBlocks.STRIPPED_FIR_LOG);
        // sentinal
        strip(GotModBlocks.SENTINAL_LOG,       GotModBlocks.STRIPPED_SENTINAL_LOG);
        strip(GotModBlocks.SENTINAL_WOOD,      GotModBlocks.STRIPPED_SENTINAL_LOG);
        // ironwood
        strip(GotModBlocks.IRONWOOD_LOG,       GotModBlocks.STRIPPED_IRONWOOD_LOG);
        strip(GotModBlocks.IRONWOOD_WOOD,      GotModBlocks.STRIPPED_IRONWOOD_LOG);
        // beech
        strip(GotModBlocks.BEECH_LOG,          GotModBlocks.STRIPPED_BEECH_LOG);
        strip(GotModBlocks.BEECH_WOOD,         GotModBlocks.STRIPPED_BEECH_LOG);
        // soldier_pine
        strip(GotModBlocks.SOLDIER_PINE_LOG,   GotModBlocks.STRIPPED_SOLDIER_PINE_LOG);
        strip(GotModBlocks.SOLDIER_PINE_WOOD,  GotModBlocks.STRIPPED_SOLDIER_PINE_LOG);
        // ash
        strip(GotModBlocks.ASH_LOG,            GotModBlocks.STRIPPED_ASH_LOG);
        strip(GotModBlocks.ASH_WOOD,           GotModBlocks.STRIPPED_ASH_LOG);
        // hawthorn
        strip(GotModBlocks.HAWTHORN_LOG,       GotModBlocks.STRIPPED_HAWTHORN_LOG);
        strip(GotModBlocks.HAWTHORN_WOOD,      GotModBlocks.STRIPPED_HAWTHORN_LOG);
        // blackbark
        strip(GotModBlocks.BLACKBARK_LOG,      GotModBlocks.STRIPPED_BLACKBARK_LOG);
        strip(GotModBlocks.BLACKBARK_WOOD,     GotModBlocks.STRIPPED_BLACKBARK_LOG);
        // bloodwood
        strip(GotModBlocks.BLOODWOOD_LOG,      GotModBlocks.STRIPPED_BLOODWOOD_LOG);
        strip(GotModBlocks.BLOODWOOD_WOOD,     GotModBlocks.STRIPPED_BLOODWOOD_LOG);
        // blue_mahoe
        strip(GotModBlocks.BLUE_MAHOE_LOG,     GotModBlocks.STRIPPED_BLUE_MAHOE_LOG);
        strip(GotModBlocks.BLUE_MAHOE_WOOD,    GotModBlocks.STRIPPED_BLUE_MAHOE_LOG);
        // cottonwood
        strip(GotModBlocks.COTTONWOOD_LOG,     GotModBlocks.STRIPPED_COTTONWOOD_LOG);
        strip(GotModBlocks.COTTONWOOD_WOOD,    GotModBlocks.STRIPPED_COTTONWOOD_LOG);
        // black_cottonwood
        strip(GotModBlocks.BLACK_COTTONWOOD_LOG,  GotModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG);
        strip(GotModBlocks.BLACK_COTTONWOOD_WOOD, GotModBlocks.STRIPPED_BLACK_COTTONWOOD_LOG);
        // cinnamon
        strip(GotModBlocks.CINNAMON_LOG,       GotModBlocks.STRIPPED_CINNAMON_LOG);
        strip(GotModBlocks.CINNAMON_WOOD,      GotModBlocks.STRIPPED_CINNAMON_LOG);
        // clove
        strip(GotModBlocks.CLOVE_LOG,          GotModBlocks.STRIPPED_CLOVE_LOG);
        strip(GotModBlocks.CLOVE_WOOD,         GotModBlocks.STRIPPED_CLOVE_LOG);
        // ebony
        strip(GotModBlocks.EBONY_LOG,          GotModBlocks.STRIPPED_EBONY_LOG);
        strip(GotModBlocks.EBONY_WOOD,         GotModBlocks.STRIPPED_EBONY_LOG);
        // elm
        strip(GotModBlocks.ELM_LOG,            GotModBlocks.STRIPPED_ELM_LOG);
        strip(GotModBlocks.ELM_WOOD,           GotModBlocks.STRIPPED_ELM_LOG);
        // cedar
        strip(GotModBlocks.CEDAR_LOG,          GotModBlocks.STRIPPED_CEDAR_LOG);
        strip(GotModBlocks.CEDAR_WOOD,         GotModBlocks.STRIPPED_CEDAR_LOG);
        // apple
        strip(GotModBlocks.APPLE_LOG,          GotModBlocks.STRIPPED_APPLE_LOG);
        strip(GotModBlocks.APPLE_WOOD,         GotModBlocks.STRIPPED_APPLE_LOG);
        // goldenheart
        strip(GotModBlocks.GOLDENHEART_LOG,    GotModBlocks.STRIPPED_GOLDENHEART_LOG);
        strip(GotModBlocks.GOLDENHEART_WOOD,   GotModBlocks.STRIPPED_GOLDENHEART_LOG);
        // linden
        strip(GotModBlocks.LINDEN_LOG,         GotModBlocks.STRIPPED_LINDEN_LOG);
        strip(GotModBlocks.LINDEN_WOOD,        GotModBlocks.STRIPPED_LINDEN_LOG);
        // mahogany
        strip(GotModBlocks.MAHOGANY_LOG,       GotModBlocks.STRIPPED_MAHOGANY_LOG);
        strip(GotModBlocks.MAHOGANY_WOOD,      GotModBlocks.STRIPPED_MAHOGANY_LOG);
        // maple
        strip(GotModBlocks.MAPLE_LOG,          GotModBlocks.STRIPPED_MAPLE_LOG);
        strip(GotModBlocks.MAPLE_WOOD,         GotModBlocks.STRIPPED_MAPLE_LOG);
        // myrrh
        strip(GotModBlocks.MYRRH_LOG,          GotModBlocks.STRIPPED_MYRRH_LOG);
        strip(GotModBlocks.MYRRH_WOOD,         GotModBlocks.STRIPPED_MYRRH_LOG);
        // redwood
        strip(GotModBlocks.REDWOOD_LOG,        GotModBlocks.STRIPPED_REDWOOD_LOG);
        strip(GotModBlocks.REDWOOD_WOOD,       GotModBlocks.STRIPPED_REDWOOD_LOG);
        // chestnut
        strip(GotModBlocks.CHESTNUT_LOG,       GotModBlocks.STRIPPED_CHESTNUT_LOG);
        strip(GotModBlocks.CHESTNUT_WOOD,      GotModBlocks.STRIPPED_CHESTNUT_LOG);
        // willow
        strip(GotModBlocks.WILLOW_LOG,         GotModBlocks.STRIPPED_WILLOW_LOG);
        strip(GotModBlocks.WILLOW_WOOD,        GotModBlocks.STRIPPED_WILLOW_LOG);
        // wormtree
        strip(GotModBlocks.WORMTREE_LOG,       GotModBlocks.STRIPPED_WORMTREE_LOG);
        strip(GotModBlocks.WORMTREE_WOOD,      GotModBlocks.STRIPPED_WORMTREE_LOG);
    }

    private static void strip(
            net.neoforged.neoforge.registries.DeferredBlock<net.minecraft.world.level.block.Block> from,
            net.neoforged.neoforge.registries.DeferredBlock<net.minecraft.world.level.block.Block> to) {
        AxeItem.STRIPPABLES.put(from.get(), to.get());
    }

    private GotStrippingHelper() {}
}
