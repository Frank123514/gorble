package net.francis.got;

import net.francis.got.client.Client;
import net.francis.got.init.*;
import net.francis.got.worldgen.biome.placers.TreePlacers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.HashSet;

import net.francis.got.network.Network;
import net.francis.got.registry.WorldgenRegistries;
import net.francis.got.sounds.ModSounds;
import net.francis.got.worldgen.GotChunkGenerator;
import net.francis.got.worldgen.MapReloadListener;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Tuple;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(GotMod.MODID)
public final class GotMod {

    public static final String MODID = "got";
    public static final Logger LOGGER = LogManager.getLogger();

    public GotMod() {
        IEventBus modBus = ModLoadingContext
                .get()
                .getActiveContainer()
                .getEventBus();

        assert modBus != null;
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerNetworking);
        modBus.addListener(net.francis.got.data.ModDataGenerators::gatherData);

        WoodTypes.init();
        ModMenus.REGISTRY.register(modBus);
        ModRecipeTypes.REGISTRY.register(modBus);
        ModRecipeSerializers.REGISTRY.register(modBus);
        ModBlocks.REGISTRY.register(modBus);
        ModItems.REGISTRY.register(modBus);
        ModBlockEntities.REGISTRY.register(modBus);
        ModBoatEntities.REGISTRY.register(modBus);
        ModTabs.register(modBus);
        ModSounds.register(modBus);
        WorldgenRegistries.register(modBus);
        TreePlacers.register(modBus);
        ModParticles.register(modBus);
        ModDataComponents.register(modBus);

        ModEntities.REGISTRY.register(modBus);

        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            Client.init();
        }

        LOGGER.info("Game of Thrones mod loaded");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HashSet<net.minecraft.world.level.block.Block> blocks =
                    new HashSet<>(BlockEntityType.HANGING_SIGN.validBlocks);
            Arrays.asList(
                    ModBlocks.WEIRWOOD_HANGING_SIGN.get(), ModBlocks.WEIRWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.ASPEN_HANGING_SIGN.get(), ModBlocks.ASPEN_WALL_HANGING_SIGN.get(),
                    ModBlocks.ALDER_HANGING_SIGN.get(), ModBlocks.ALDER_WALL_HANGING_SIGN.get(),
                    ModBlocks.PINE_HANGING_SIGN.get(), ModBlocks.PINE_WALL_HANGING_SIGN.get(),
                    ModBlocks.FIR_HANGING_SIGN.get(), ModBlocks.FIR_WALL_HANGING_SIGN.get(),
                    ModBlocks.SENTINAL_HANGING_SIGN.get(), ModBlocks.SENTINAL_WALL_HANGING_SIGN.get(),
                    ModBlocks.IRONWOOD_HANGING_SIGN.get(), ModBlocks.IRONWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.BEECH_HANGING_SIGN.get(), ModBlocks.BEECH_WALL_HANGING_SIGN.get(),
                    ModBlocks.SOLDIER_PINE_HANGING_SIGN.get(), ModBlocks.SOLDIER_PINE_WALL_HANGING_SIGN.get(),
                    ModBlocks.ASH_HANGING_SIGN.get(), ModBlocks.ASH_WALL_HANGING_SIGN.get(),
                    ModBlocks.HAWTHORN_HANGING_SIGN.get(), ModBlocks.HAWTHORN_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLACKBARK_HANGING_SIGN.get(), ModBlocks.BLACKBARK_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLOODWOOD_HANGING_SIGN.get(), ModBlocks.BLOODWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLUE_MAHOE_HANGING_SIGN.get(), ModBlocks.BLUE_MAHOE_WALL_HANGING_SIGN.get(),
                    ModBlocks.COTTONWOOD_HANGING_SIGN.get(), ModBlocks.COTTONWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLACK_COTTONWOOD_HANGING_SIGN.get(), ModBlocks.BLACK_COTTONWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.CINNAMON_HANGING_SIGN.get(), ModBlocks.CINNAMON_WALL_HANGING_SIGN.get(),
                    ModBlocks.CLOVE_HANGING_SIGN.get(), ModBlocks.CLOVE_WALL_HANGING_SIGN.get(),
                    ModBlocks.EBONY_HANGING_SIGN.get(), ModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                    ModBlocks.ELM_HANGING_SIGN.get(), ModBlocks.ELM_WALL_HANGING_SIGN.get(),
                    ModBlocks.CEDAR_HANGING_SIGN.get(), ModBlocks.CEDAR_WALL_HANGING_SIGN.get(),
                    ModBlocks.APPLE_HANGING_SIGN.get(), ModBlocks.APPLE_WALL_HANGING_SIGN.get(),
                    ModBlocks.GOLDENHEART_HANGING_SIGN.get(), ModBlocks.GOLDENHEART_WALL_HANGING_SIGN.get(),
                    ModBlocks.LINDEN_HANGING_SIGN.get(), ModBlocks.LINDEN_WALL_HANGING_SIGN.get(),
                    ModBlocks.MAHOGANY_HANGING_SIGN.get(), ModBlocks.MAHOGANY_WALL_HANGING_SIGN.get(),
                    ModBlocks.MAPLE_HANGING_SIGN.get(), ModBlocks.MAPLE_WALL_HANGING_SIGN.get(),
                    ModBlocks.MYRRH_HANGING_SIGN.get(), ModBlocks.MYRRH_WALL_HANGING_SIGN.get(),
                    ModBlocks.REDWOOD_HANGING_SIGN.get(), ModBlocks.REDWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.CHESTNUT_HANGING_SIGN.get(), ModBlocks.CHESTNUT_WALL_HANGING_SIGN.get(),
                    ModBlocks.WILLOW_HANGING_SIGN.get(), ModBlocks.WILLOW_WALL_HANGING_SIGN.get(),
                    ModBlocks.WORMTREE_HANGING_SIGN.get(), ModBlocks.WORMTREE_WALL_HANGING_SIGN.get(),
                    ModBlocks.NIGHTWOOD_HANGING_SIGN.get(), ModBlocks.NIGHTWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.PURPLEHEART_HANGING_SIGN.get(), ModBlocks.PURPLEHEART_WALL_HANGING_SIGN.get(),
                    ModBlocks.TIGERWOOD_HANGING_SIGN.get(), ModBlocks.TIGERWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.SANDALWOOD_HANGING_SIGN.get(), ModBlocks.SANDALWOOD_WALL_HANGING_SIGN.get(),
                    ModBlocks.SANDBEGGAR_HANGING_SIGN.get(), ModBlocks.SANDBEGGAR_WALL_HANGING_SIGN.get(),
                    ModBlocks.APRICOT_HANGING_SIGN.get(), ModBlocks.APRICOT_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLACKTHORN_HANGING_SIGN.get(), ModBlocks.BLACKTHORN_WALL_HANGING_SIGN.get(),
                    ModBlocks.RED_CHERRY_HANGING_SIGN.get(), ModBlocks.RED_CHERRY_WALL_HANGING_SIGN.get(),
                    ModBlocks.BLACK_CHERRY_HANGING_SIGN.get(), ModBlocks.BLACK_CHERRY_WALL_HANGING_SIGN.get(),
                    ModBlocks.WHITE_CHERRY_HANGING_SIGN.get(), ModBlocks.WHITE_CHERRY_WALL_HANGING_SIGN.get(),
                    ModBlocks.CRABAPPLE_HANGING_SIGN.get(), ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                    ModBlocks.DATE_PALM_HANGING_SIGN.get(), ModBlocks.DATE_PALM_WALL_HANGING_SIGN.get(),
                    ModBlocks.FIG_HANGING_SIGN.get(), ModBlocks.FIG_WALL_HANGING_SIGN.get(),
                    ModBlocks.LEMON_HANGING_SIGN.get(), ModBlocks.LEMON_WALL_HANGING_SIGN.get(),
                    ModBlocks.LIME_HANGING_SIGN.get(), ModBlocks.LIME_WALL_HANGING_SIGN.get(),
                    ModBlocks.OLIVE_HANGING_SIGN.get(), ModBlocks.OLIVE_WALL_HANGING_SIGN.get(),
                    ModBlocks.ORANGE_HANGING_SIGN.get(), ModBlocks.ORANGE_WALL_HANGING_SIGN.get(),
                    ModBlocks.PEACH_HANGING_SIGN.get(), ModBlocks.PEACH_WALL_HANGING_SIGN.get(),
                    ModBlocks.PEAR_HANGING_SIGN.get(), ModBlocks.PEAR_WALL_HANGING_SIGN.get(),
                    ModBlocks.PERSIMMON_HANGING_SIGN.get(), ModBlocks.PERSIMMON_WALL_HANGING_SIGN.get(),
                    ModBlocks.PINK_IVORY_HANGING_SIGN.get(), ModBlocks.PINK_IVORY_WALL_HANGING_SIGN.get(),
                    ModBlocks.PLUM_HANGING_SIGN.get(), ModBlocks.PLUM_WALL_HANGING_SIGN.get(),
                    ModBlocks.POMEGRANATE_HANGING_SIGN.get(), ModBlocks.POMEGRANATE_WALL_HANGING_SIGN.get(),
                    ModBlocks.PRUNE_HANGING_SIGN.get(), ModBlocks.PRUNE_WALL_HANGING_SIGN.get(),
                    ModBlocks.ALMOND_HANGING_SIGN.get(), ModBlocks.ALMOND_WALL_HANGING_SIGN.get(),
                    ModBlocks.NUTMEG_HANGING_SIGN.get(), ModBlocks.NUTMEG_WALL_HANGING_SIGN.get(),
                    ModBlocks.HEMLOCK_HANGING_SIGN.get(), ModBlocks.HEMLOCK_WALL_HANGING_SIGN.get()
            ).forEach(blocks::add);
            BlockEntityType.HANGING_SIGN.validBlocks = blocks;

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "bellflower"), ModBlocks.POTTED_BELLFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "black_lotus"), ModBlocks.POTTED_BLACK_LOTUS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "blood_bloom"), ModBlocks.POTTED_BLOOD_BLOOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "coldsnaps"), ModBlocks.POTTED_COLDSNAPS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "dragons_breath"), ModBlocks.POTTED_DRAGONS_BREATH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "evening_star"), ModBlocks.POTTED_EVENING_STAR);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "forget_me_not"), ModBlocks.POTTED_FORGET_ME_NOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "frostfires"), ModBlocks.POTTED_FROSTFIRES);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "gillyflower"), ModBlocks.POTTED_GILLYFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ginger"), ModBlocks.POTTED_GINGER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "goathead"), ModBlocks.POTTED_GOATHEAD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "goldencup"), ModBlocks.POTTED_GOLDENCUP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "goldenrod"), ModBlocks.POTTED_GOLDENROD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "gorse"), ModBlocks.POTTED_GORSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ladys_lace"), ModBlocks.POTTED_LADYS_LACE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "lavender"), ModBlocks.POTTED_LAVENDER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "liverwort"), ModBlocks.POTTED_LIVERWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "lungwort"), ModBlocks.POTTED_LUNGWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "moonbloom"), ModBlocks.POTTED_MOONBLOOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "nightshade"), ModBlocks.POTTED_NIGHTSHADE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pennyroyal"), ModBlocks.POTTED_PENNYROYAL);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "poison_kisses"), ModBlocks.POTTED_POISON_KISSES);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "thornbush"), ModBlocks.POTTED_THORNBUSH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "opium_poppy"), ModBlocks.POTTED_OPIUM_POPPY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "golden_rose"), ModBlocks.POTTED_GOLDEN_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "red_rose"), ModBlocks.POTTED_RED_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "white_rose"), ModBlocks.POTTED_WHITE_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "winter_rose"), ModBlocks.POTTED_WINTER_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "saffron_crocus"), ModBlocks.POTTED_SAFFRON_CROCUS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sedge"), ModBlocks.POTTED_SEDGE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "spiceflower"), ModBlocks.POTTED_SPICEFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "tansy"), ModBlocks.POTTED_TANSY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "thistle"), ModBlocks.POTTED_THISTLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_radish"), ModBlocks.POTTED_WILD_RADISH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "hranna"), ModBlocks.POTTED_HRANNA);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_wheat"), ModBlocks.POTTED_WILD_WHEAT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_oat"), ModBlocks.POTTED_WILD_OAT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_rye"), ModBlocks.POTTED_WILD_RYE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_barley"), ModBlocks.POTTED_WILD_BARLEY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_beetroot"), ModBlocks.POTTED_WILD_BEETROOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_cotton"), ModBlocks.POTTED_WILD_COTTON);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_peppercorn"), ModBlocks.POTTED_WILD_PEPPERCORN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_carrot"), ModBlocks.POTTED_WILD_CARROT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_parsnip"), ModBlocks.POTTED_WILD_PARSNIP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_onion"), ModBlocks.POTTED_WILD_ONION);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_turnip"), ModBlocks.POTTED_WILD_TURNIP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_neep"), ModBlocks.POTTED_WILD_NEEP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_peas"), ModBlocks.POTTED_WILD_PEAS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_cabbage"), ModBlocks.POTTED_WILD_CABBAGE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_garlic"), ModBlocks.POTTED_WILD_GARLIC);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_horseradish"), ModBlocks.POTTED_WILD_HORSERADISH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_leek"), ModBlocks.POTTED_WILD_LEEK);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_bean"),           ModBlocks.POTTED_WILD_BEAN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "bracken"),        ModBlocks.POTTED_BRACKEN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "briar"),          ModBlocks.POTTED_BRIAR);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "broom"),          ModBlocks.POTTED_BROOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_cardamom"),       ModBlocks.POTTED_WILD_CARDAMOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_chickpea"),       ModBlocks.POTTED_WILD_CHICKPEA);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_cucumber"),       ModBlocks.POTTED_WILD_CUCUMBER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "daggerleaf"),     ModBlocks.POTTED_DAGGERLEAF);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "firepod"),        ModBlocks.POTTED_FIREPOD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ghostskin"),      ModBlocks.POTTED_GHOSTSKIN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "grape_vine"),     ModBlocks.POTTED_GRAPE_VINE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "harpys_gold"),    ModBlocks.POTTED_HARPYS_GOLD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "hornwort"),       ModBlocks.POTTED_HORNWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ivy"),            ModBlocks.POTTED_IVY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "kingscopper"),    ModBlocks.POTTED_KINGSCOPPER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_licorice"),       ModBlocks.POTTED_WILD_LICORICE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "mistletoe"),      ModBlocks.POTTED_MISTLETOE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_mustard_plant"),  ModBlocks.POTTED_WILD_MUSTARD_PLANT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "nettle"),         ModBlocks.POTTED_NETTLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wild_pepper_plant"),   ModBlocks.POTTED_WILD_PEPPER_PLANT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pinchfire"),      ModBlocks.POTTED_PINCHFIRE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "prickly_ben"),    ModBlocks.POTTED_PRICKLY_BEN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sandwillow"),     ModBlocks.POTTED_SANDWILLOW);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sourleaf"),       ModBlocks.POTTED_SOURLEAF);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sting_me_not"),   ModBlocks.POTTED_STING_ME_NOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "waspwillow"),     ModBlocks.POTTED_WASPWILLOW);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "weirwood_sapling"), ModBlocks.POTTED_WEIRWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "aspen_sapling"), ModBlocks.POTTED_ASPEN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "alder_sapling"), ModBlocks.POTTED_ALDER_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pine_sapling"), ModBlocks.POTTED_PINE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "fir_sapling"), ModBlocks.POTTED_FIR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sentinal_sapling"), ModBlocks.POTTED_SENTINAL_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ironwood_sapling"), ModBlocks.POTTED_IRONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "beech_sapling"), ModBlocks.POTTED_BEECH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "soldier_pine_sapling"), ModBlocks.POTTED_SOLDIER_PINE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ash_sapling"), ModBlocks.POTTED_ASH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "hawthorn_sapling"), ModBlocks.POTTED_HAWTHORN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "blackbark_sapling"), ModBlocks.POTTED_BLACKBARK_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "bloodwood_sapling"), ModBlocks.POTTED_BLOODWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "blue_mahoe_sapling"), ModBlocks.POTTED_BLUE_MAHOE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "cottonwood_sapling"), ModBlocks.POTTED_COTTONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "black_cottonwood_sapling"), ModBlocks.POTTED_BLACK_COTTONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "cinnamon_sapling"), ModBlocks.POTTED_CINNAMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "clove_sapling"), ModBlocks.POTTED_CLOVE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "ebony_sapling"), ModBlocks.POTTED_EBONY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "elm_sapling"), ModBlocks.POTTED_ELM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "cedar_sapling"), ModBlocks.POTTED_CEDAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "apple_sapling"), ModBlocks.POTTED_APPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "goldenheart_sapling"), ModBlocks.POTTED_GOLDENHEART_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "linden_sapling"), ModBlocks.POTTED_LINDEN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "mahogany_sapling"), ModBlocks.POTTED_MAHOGANY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "maple_sapling"), ModBlocks.POTTED_MAPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "myrrh_sapling"), ModBlocks.POTTED_MYRRH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "redwood_sapling"), ModBlocks.POTTED_REDWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "chestnut_sapling"), ModBlocks.POTTED_CHESTNUT_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "willow_sapling"), ModBlocks.POTTED_WILLOW_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "wormtree_sapling"), ModBlocks.POTTED_WORMTREE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "nightwood_sapling"), ModBlocks.POTTED_NIGHTWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "purpleheart_sapling"), ModBlocks.POTTED_PURPLEHEART_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "tigerwood_sapling"), ModBlocks.POTTED_TIGERWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sandalwood_sapling"), ModBlocks.POTTED_SANDALWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "sandbeggar_sapling"), ModBlocks.POTTED_SANDBEGGAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "apricot_sapling"), ModBlocks.POTTED_APRICOT_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "blackthorn_sapling"), ModBlocks.POTTED_BLACKTHORN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "red_cherry_sapling"), ModBlocks.POTTED_RED_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "black_cherry_sapling"), ModBlocks.POTTED_BLACK_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "white_cherry_sapling"), ModBlocks.POTTED_WHITE_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "crabapple_sapling"), ModBlocks.POTTED_CRABAPPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "date_palm_sapling"), ModBlocks.POTTED_DATE_PALM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "fig_sapling"), ModBlocks.POTTED_FIG_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "lemon_sapling"), ModBlocks.POTTED_LEMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "lime_sapling"), ModBlocks.POTTED_LIME_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "olive_sapling"), ModBlocks.POTTED_OLIVE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "orange_sapling"), ModBlocks.POTTED_ORANGE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "peach_sapling"), ModBlocks.POTTED_PEACH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pear_sapling"), ModBlocks.POTTED_PEAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "persimmon_sapling"), ModBlocks.POTTED_PERSIMMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pink_ivory_sapling"), ModBlocks.POTTED_PINK_IVORY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "plum_sapling"), ModBlocks.POTTED_PLUM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "pomegranate_sapling"), ModBlocks.POTTED_POMEGRANATE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "prune_sapling"), ModBlocks.POTTED_PRUNE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "almond_sapling"), ModBlocks.POTTED_ALMOND_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "nutmeg_sapling"), ModBlocks.POTTED_NUTMEG_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Identifier.fromNamespaceAndPath("got", "hemlock_sapling"), ModBlocks.POTTED_HEMLOCK_SAPLING);
        });

        LOGGER.info("GoT common setup complete");
        Network.init();
    }

    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        Network.register(event);
        LOGGER.info("GoT networking registered");
    }

    @SubscribeEvent
    public void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level
                && level.dimension() == Level.OVERWORLD) {
            GotChunkGenerator.initNoise(level.getSeed());
        }
    }

    @SubscribeEvent
    public void onAddReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(
                Identifier.fromNamespaceAndPath(MODID, "map_reload"),
                new MapReloadListener()
        );
        LOGGER.info("Registered GoT map reload listener");
    }

    private static final Collection<Tuple<Runnable, Integer>> WORK_QUEUE =
            new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int ticks, Runnable action) {
        WORK_QUEUE.add(new Tuple<>(action, ticks));
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        List<Tuple<Runnable, Integer>> ready = new ArrayList<>();

        for (Tuple<Runnable, Integer> t : WORK_QUEUE) {
            t.setB(t.getB() - 1);
            if (t.getB() <= 0) {
                ready.add(t);
            }
        }

        ready.forEach(t -> t.getA().run());
        WORK_QUEUE.removeAll(ready);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}