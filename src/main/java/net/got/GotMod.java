package net.got;

import net.got.client.GotClient;
import net.got.worldgen.biome.placers.GotTreePlacers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.HashSet;
import net.got.init.GotModBlocks;
import net.got.init.GotModBlockEntities;
import net.got.init.GotModBoatEntities;
import net.got.init.GotWoodTypes;
import net.got.init.GotModEntities;
import net.got.init.GotModItems;
import net.got.init.GotModMenus;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.got.init.GotModTabs;
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
import net.got.climate.SeasonManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
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