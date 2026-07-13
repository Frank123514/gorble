package net.got;

import net.got.client.GotClient;
import net.got.init.*;
import net.got.worldgen.biome.placers.GotTreePlacers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.HashSet;

import net.got.network.GotNetwork;
import net.got.registry.WorldgenRegistries;
import net.got.sounds.ModSounds;
import net.got.worldgen.GotChunkGenerator;
import net.got.worldgen.MapReloadListener;

import net.minecraft.resources.ResourceLocation;
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

        GotWoodTypes.init();
        GotModMenus.REGISTRY.register(modBus);
        GotModRecipeTypes.REGISTRY.register(modBus);
        GotModRecipeSerializers.REGISTRY.register(modBus);
        GotModBlocks.REGISTRY.register(modBus);
        GotModItems.REGISTRY.register(modBus);
        GotModBlockEntities.REGISTRY.register(modBus);
        GotModBoatEntities.REGISTRY.register(modBus);
        GotModTabs.register(modBus);
        ModSounds.register(modBus);
        WorldgenRegistries.register(modBus);
        GotTreePlacers.register(modBus);
        GotModParticles.register(modBus);
        GotModDataComponents.register(modBus);

        GotModEntities.REGISTRY.register(modBus);

        NeoForge.EVENT_BUS.register(this);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            GotClient.init();
        }

        LOGGER.info("Game of Thrones mod loaded");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HashSet<net.minecraft.world.level.block.Block> blocks =
                    new HashSet<>(BlockEntityType.HANGING_SIGN.validBlocks);
            Arrays.asList(
                    GotModBlocks.WEIRWOOD_HANGING_SIGN.get(), GotModBlocks.WEIRWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ASPEN_HANGING_SIGN.get(), GotModBlocks.ASPEN_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ALDER_HANGING_SIGN.get(), GotModBlocks.ALDER_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PINE_HANGING_SIGN.get(), GotModBlocks.PINE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.FIR_HANGING_SIGN.get(), GotModBlocks.FIR_WALL_HANGING_SIGN.get(),
                    GotModBlocks.SENTINAL_HANGING_SIGN.get(), GotModBlocks.SENTINAL_WALL_HANGING_SIGN.get(),
                    GotModBlocks.IRONWOOD_HANGING_SIGN.get(), GotModBlocks.IRONWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BEECH_HANGING_SIGN.get(), GotModBlocks.BEECH_WALL_HANGING_SIGN.get(),
                    GotModBlocks.SOLDIER_PINE_HANGING_SIGN.get(), GotModBlocks.SOLDIER_PINE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ASH_HANGING_SIGN.get(), GotModBlocks.ASH_WALL_HANGING_SIGN.get(),
                    GotModBlocks.HAWTHORN_HANGING_SIGN.get(), GotModBlocks.HAWTHORN_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLACKBARK_HANGING_SIGN.get(), GotModBlocks.BLACKBARK_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLOODWOOD_HANGING_SIGN.get(), GotModBlocks.BLOODWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLUE_MAHOE_HANGING_SIGN.get(), GotModBlocks.BLUE_MAHOE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.COTTONWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLACK_COTTONWOOD_HANGING_SIGN.get(), GotModBlocks.BLACK_COTTONWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.CINNAMON_HANGING_SIGN.get(), GotModBlocks.CINNAMON_WALL_HANGING_SIGN.get(),
                    GotModBlocks.CLOVE_HANGING_SIGN.get(), GotModBlocks.CLOVE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.EBONY_HANGING_SIGN.get(), GotModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ELM_HANGING_SIGN.get(), GotModBlocks.ELM_WALL_HANGING_SIGN.get(),
                    GotModBlocks.CEDAR_HANGING_SIGN.get(), GotModBlocks.CEDAR_WALL_HANGING_SIGN.get(),
                    GotModBlocks.APPLE_HANGING_SIGN.get(), GotModBlocks.APPLE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.GOLDENHEART_HANGING_SIGN.get(), GotModBlocks.GOLDENHEART_WALL_HANGING_SIGN.get(),
                    GotModBlocks.LINDEN_HANGING_SIGN.get(), GotModBlocks.LINDEN_WALL_HANGING_SIGN.get(),
                    GotModBlocks.MAHOGANY_HANGING_SIGN.get(), GotModBlocks.MAHOGANY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.MAPLE_HANGING_SIGN.get(), GotModBlocks.MAPLE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.MYRRH_HANGING_SIGN.get(), GotModBlocks.MYRRH_WALL_HANGING_SIGN.get(),
                    GotModBlocks.REDWOOD_HANGING_SIGN.get(), GotModBlocks.REDWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.CHESTNUT_HANGING_SIGN.get(), GotModBlocks.CHESTNUT_WALL_HANGING_SIGN.get(),
                    GotModBlocks.WILLOW_HANGING_SIGN.get(), GotModBlocks.WILLOW_WALL_HANGING_SIGN.get(),
                    GotModBlocks.WORMTREE_HANGING_SIGN.get(), GotModBlocks.WORMTREE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.NIGHTWOOD_HANGING_SIGN.get(), GotModBlocks.NIGHTWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PURPLEHEART_HANGING_SIGN.get(), GotModBlocks.PURPLEHEART_WALL_HANGING_SIGN.get(),
                    GotModBlocks.TIGERWOOD_HANGING_SIGN.get(), GotModBlocks.TIGERWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BURL_HANGING_SIGN.get(), GotModBlocks.BURL_WALL_HANGING_SIGN.get(),
                    GotModBlocks.SANDALWOOD_HANGING_SIGN.get(), GotModBlocks.SANDALWOOD_WALL_HANGING_SIGN.get(),
                    GotModBlocks.SANDBEGGAR_HANGING_SIGN.get(), GotModBlocks.SANDBEGGAR_WALL_HANGING_SIGN.get(),
                    GotModBlocks.APRICOT_HANGING_SIGN.get(), GotModBlocks.APRICOT_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLACKTHORN_HANGING_SIGN.get(), GotModBlocks.BLACKTHORN_WALL_HANGING_SIGN.get(),
                    GotModBlocks.RED_CHERRY_HANGING_SIGN.get(), GotModBlocks.RED_CHERRY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.BLACK_CHERRY_HANGING_SIGN.get(), GotModBlocks.BLACK_CHERRY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.WHITE_CHERRY_HANGING_SIGN.get(), GotModBlocks.WHITE_CHERRY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.CRABAPPLE_HANGING_SIGN.get(), GotModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.DATE_PALM_HANGING_SIGN.get(), GotModBlocks.DATE_PALM_WALL_HANGING_SIGN.get(),
                    GotModBlocks.FIG_HANGING_SIGN.get(), GotModBlocks.FIG_WALL_HANGING_SIGN.get(),
                    GotModBlocks.LEMON_HANGING_SIGN.get(), GotModBlocks.LEMON_WALL_HANGING_SIGN.get(),
                    GotModBlocks.LIME_HANGING_SIGN.get(), GotModBlocks.LIME_WALL_HANGING_SIGN.get(),
                    GotModBlocks.OLIVE_HANGING_SIGN.get(), GotModBlocks.OLIVE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ORANGE_HANGING_SIGN.get(), GotModBlocks.ORANGE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PEACH_HANGING_SIGN.get(), GotModBlocks.PEACH_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PEAR_HANGING_SIGN.get(), GotModBlocks.PEAR_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PERSIMMON_HANGING_SIGN.get(), GotModBlocks.PERSIMMON_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PINK_IVORY_HANGING_SIGN.get(), GotModBlocks.PINK_IVORY_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PLUM_HANGING_SIGN.get(), GotModBlocks.PLUM_WALL_HANGING_SIGN.get(),
                    GotModBlocks.POMEGRANATE_HANGING_SIGN.get(), GotModBlocks.POMEGRANATE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.PRUNE_HANGING_SIGN.get(), GotModBlocks.PRUNE_WALL_HANGING_SIGN.get(),
                    GotModBlocks.ALMOND_HANGING_SIGN.get(), GotModBlocks.ALMOND_WALL_HANGING_SIGN.get(),
                    GotModBlocks.NUTMEG_HANGING_SIGN.get(), GotModBlocks.NUTMEG_WALL_HANGING_SIGN.get(),
                    GotModBlocks.HEMLOCK_HANGING_SIGN.get(), GotModBlocks.HEMLOCK_WALL_HANGING_SIGN.get()
            ).forEach(blocks::add);
            BlockEntityType.HANGING_SIGN.validBlocks = blocks;

            // Register flowers and saplings as pottable
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "bellflower"), GotModBlocks.POTTED_BELLFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "black_lotus"), GotModBlocks.POTTED_BLACK_LOTUS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "blood_bloom"), GotModBlocks.POTTED_BLOOD_BLOOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "coldsnaps"), GotModBlocks.POTTED_COLDSNAPS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "dragons_breath"), GotModBlocks.POTTED_DRAGONS_BREATH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "evening_star"), GotModBlocks.POTTED_EVENING_STAR);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "forget_me_not"), GotModBlocks.POTTED_FORGET_ME_NOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "frostfires"), GotModBlocks.POTTED_FROSTFIRES);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "gillyflower"), GotModBlocks.POTTED_GILLYFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ginger"), GotModBlocks.POTTED_GINGER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "goathead"), GotModBlocks.POTTED_GOATHEAD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "goldencup"), GotModBlocks.POTTED_GOLDENCUP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "goldenrod"), GotModBlocks.POTTED_GOLDENROD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "gorse"), GotModBlocks.POTTED_GORSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ladys_lace"), GotModBlocks.POTTED_LADYS_LACE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "lavender"), GotModBlocks.POTTED_LAVENDER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "liverwort"), GotModBlocks.POTTED_LIVERWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "lungwort"), GotModBlocks.POTTED_LUNGWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "moonbloom"), GotModBlocks.POTTED_MOONBLOOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "nightshade"), GotModBlocks.POTTED_NIGHTSHADE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pennyroyal"), GotModBlocks.POTTED_PENNYROYAL);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "poison_kisses"), GotModBlocks.POTTED_POISON_KISSES);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "thornbush"), GotModBlocks.POTTED_THORNBUSH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "opium_poppy"), GotModBlocks.POTTED_OPIUM_POPPY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "golden_rose"), GotModBlocks.POTTED_GOLDEN_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "red_rose"), GotModBlocks.POTTED_RED_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "white_rose"), GotModBlocks.POTTED_WHITE_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "winter_rose"), GotModBlocks.POTTED_WINTER_ROSE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "saffron_crocus"), GotModBlocks.POTTED_SAFFRON_CROCUS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sedge"), GotModBlocks.POTTED_SEDGE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "spiceflower"), GotModBlocks.POTTED_SPICEFLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "tansy"), GotModBlocks.POTTED_TANSY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "thistle"), GotModBlocks.POTTED_THISTLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_radish"), GotModBlocks.POTTED_WILD_RADISH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "hranna"), GotModBlocks.POTTED_HRANNA);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_wheat"), GotModBlocks.POTTED_WILD_WHEAT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_oat"), GotModBlocks.POTTED_WILD_OAT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_rye"), GotModBlocks.POTTED_WILD_RYE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_barley"), GotModBlocks.POTTED_WILD_BARLEY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_beetroot"), GotModBlocks.POTTED_WILD_BEETROOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_cotton"), GotModBlocks.POTTED_WILD_COTTON);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_peppercorn"), GotModBlocks.POTTED_WILD_PEPPERCORN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_carrot"), GotModBlocks.POTTED_WILD_CARROT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_parsnip"), GotModBlocks.POTTED_WILD_PARSNIP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_onion"), GotModBlocks.POTTED_WILD_ONION);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_turnip"), GotModBlocks.POTTED_WILD_TURNIP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_neep"), GotModBlocks.POTTED_WILD_NEEP);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_peas"), GotModBlocks.POTTED_WILD_PEAS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_cabbage"), GotModBlocks.POTTED_WILD_CABBAGE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_garlic"), GotModBlocks.POTTED_WILD_GARLIC);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_horseradish"), GotModBlocks.POTTED_WILD_HORSERADISH);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_leek"), GotModBlocks.POTTED_WILD_LEEK);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_bean"),           GotModBlocks.POTTED_WILD_BEAN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "bracken"),        GotModBlocks.POTTED_BRACKEN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "briar"),          GotModBlocks.POTTED_BRIAR);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "broom"),          GotModBlocks.POTTED_BROOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_cardamom"),       GotModBlocks.POTTED_WILD_CARDAMOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_chickpea"),       GotModBlocks.POTTED_WILD_CHICKPEA);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_cucumber"),       GotModBlocks.POTTED_WILD_CUCUMBER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "daggerleaf"),     GotModBlocks.POTTED_DAGGERLEAF);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "firepod"),        GotModBlocks.POTTED_FIREPOD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ghostskin"),      GotModBlocks.POTTED_GHOSTSKIN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "grape_vine"),     GotModBlocks.POTTED_GRAPE_VINE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "harpys_gold"),    GotModBlocks.POTTED_HARPYS_GOLD);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "hornwort"),       GotModBlocks.POTTED_HORNWORT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ivy"),            GotModBlocks.POTTED_IVY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "kingscopper"),    GotModBlocks.POTTED_KINGSCOPPER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_licorice"),       GotModBlocks.POTTED_WILD_LICORICE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "mistletoe"),      GotModBlocks.POTTED_MISTLETOE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_mustard_plant"),  GotModBlocks.POTTED_WILD_MUSTARD_PLANT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "nettle"),         GotModBlocks.POTTED_NETTLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wild_pepper_plant"),   GotModBlocks.POTTED_WILD_PEPPER_PLANT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pinchfire"),      GotModBlocks.POTTED_PINCHFIRE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "prickly_ben"),    GotModBlocks.POTTED_PRICKLY_BEN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sandwillow"),     GotModBlocks.POTTED_SANDWILLOW);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sourleaf"),       GotModBlocks.POTTED_SOURLEAF);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sting_me_not"),   GotModBlocks.POTTED_STING_ME_NOT);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "waspwillow"),     GotModBlocks.POTTED_WASPWILLOW);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "weirwood_sapling"), GotModBlocks.POTTED_WEIRWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "aspen_sapling"), GotModBlocks.POTTED_ASPEN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "alder_sapling"), GotModBlocks.POTTED_ALDER_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pine_sapling"), GotModBlocks.POTTED_PINE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "fir_sapling"), GotModBlocks.POTTED_FIR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sentinal_sapling"), GotModBlocks.POTTED_SENTINAL_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ironwood_sapling"), GotModBlocks.POTTED_IRONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "beech_sapling"), GotModBlocks.POTTED_BEECH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "soldier_pine_sapling"), GotModBlocks.POTTED_SOLDIER_PINE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ash_sapling"), GotModBlocks.POTTED_ASH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "hawthorn_sapling"), GotModBlocks.POTTED_HAWTHORN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "blackbark_sapling"), GotModBlocks.POTTED_BLACKBARK_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "bloodwood_sapling"), GotModBlocks.POTTED_BLOODWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "blue_mahoe_sapling"), GotModBlocks.POTTED_BLUE_MAHOE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "cottonwood_sapling"), GotModBlocks.POTTED_COTTONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "black_cottonwood_sapling"), GotModBlocks.POTTED_BLACK_COTTONWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "cinnamon_sapling"), GotModBlocks.POTTED_CINNAMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "clove_sapling"), GotModBlocks.POTTED_CLOVE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "ebony_sapling"), GotModBlocks.POTTED_EBONY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "elm_sapling"), GotModBlocks.POTTED_ELM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "cedar_sapling"), GotModBlocks.POTTED_CEDAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "apple_sapling"), GotModBlocks.POTTED_APPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "goldenheart_sapling"), GotModBlocks.POTTED_GOLDENHEART_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "linden_sapling"), GotModBlocks.POTTED_LINDEN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "mahogany_sapling"), GotModBlocks.POTTED_MAHOGANY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "maple_sapling"), GotModBlocks.POTTED_MAPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "myrrh_sapling"), GotModBlocks.POTTED_MYRRH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "redwood_sapling"), GotModBlocks.POTTED_REDWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "chestnut_sapling"), GotModBlocks.POTTED_CHESTNUT_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "willow_sapling"), GotModBlocks.POTTED_WILLOW_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "wormtree_sapling"), GotModBlocks.POTTED_WORMTREE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "nightwood_sapling"), GotModBlocks.POTTED_NIGHTWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "purpleheart_sapling"), GotModBlocks.POTTED_PURPLEHEART_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "tigerwood_sapling"), GotModBlocks.POTTED_TIGERWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "burl_sapling"), GotModBlocks.POTTED_BURL_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sandalwood_sapling"), GotModBlocks.POTTED_SANDALWOOD_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "sandbeggar_sapling"), GotModBlocks.POTTED_SANDBEGGAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "apricot_sapling"), GotModBlocks.POTTED_APRICOT_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "blackthorn_sapling"), GotModBlocks.POTTED_BLACKTHORN_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "red_cherry_sapling"), GotModBlocks.POTTED_RED_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "black_cherry_sapling"), GotModBlocks.POTTED_BLACK_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "white_cherry_sapling"), GotModBlocks.POTTED_WHITE_CHERRY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "crabapple_sapling"), GotModBlocks.POTTED_CRABAPPLE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "date_palm_sapling"), GotModBlocks.POTTED_DATE_PALM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "fig_sapling"), GotModBlocks.POTTED_FIG_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "lemon_sapling"), GotModBlocks.POTTED_LEMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "lime_sapling"), GotModBlocks.POTTED_LIME_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "olive_sapling"), GotModBlocks.POTTED_OLIVE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "orange_sapling"), GotModBlocks.POTTED_ORANGE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "peach_sapling"), GotModBlocks.POTTED_PEACH_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pear_sapling"), GotModBlocks.POTTED_PEAR_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "persimmon_sapling"), GotModBlocks.POTTED_PERSIMMON_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pink_ivory_sapling"), GotModBlocks.POTTED_PINK_IVORY_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "plum_sapling"), GotModBlocks.POTTED_PLUM_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "pomegranate_sapling"), GotModBlocks.POTTED_POMEGRANATE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "prune_sapling"), GotModBlocks.POTTED_PRUNE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "almond_sapling"), GotModBlocks.POTTED_ALMOND_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "nutmeg_sapling"), GotModBlocks.POTTED_NUTMEG_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ResourceLocation.fromNamespaceAndPath("got", "hemlock_sapling"), GotModBlocks.POTTED_HEMLOCK_SAPLING);
        });

        LOGGER.info("GoT common setup complete");
        GotNetwork.init();
    }

    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        GotNetwork.register(event);
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
                ResourceLocation.fromNamespaceAndPath(MODID, "map_reload"),
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

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}