package net.got;

import net.got.client.GotClient;
import net.got.init.GotModBlocks;
import net.got.init.GotModItems;
import net.got.init.GotModTabs;
import net.got.network.GotNetwork;
import net.got.registry.WorldgenRegistries;
import net.got.sounds.ModSounds;
import net.got.worldgen.BiomeMapLoader;
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
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
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

    /* ---------------------------- */
    /* Constructor                  */
    /* ---------------------------- */

    public GotMod() {
        // Mod event bus (NOT the NeoForge bus)
        IEventBus modBus = ModLoadingContext
                .get()
                .getActiveContainer()
                .getEventBus();

        /* ---------- Lifecycle ---------- */
        assert modBus != null;
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerNetworking);

        /* ---------- Registries ---------- */
        GotModBlocks.REGISTRY.register(modBus);
        GotModItems.REGISTRY.register(modBus);
        GotModTabs.REGISTRY.register(modBus);
        ModSounds.register(modBus);
        WorldgenRegistries.register(modBus);

        /* ---------- Runtime events ---------- */
        NeoForge.EVENT_BUS.register(this);

        /* ---------- Client bootstrap ---------- */
        if (FMLEnvironment.dist == Dist.CLIENT) {
            GotClient.init();
        }

        LOGGER.info("Game of Thrones mod loaded");
    }

    /* ---------------------------- */
    /* Common Setup                 */
    /* ---------------------------- */

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("GoT common setup complete");
        GotNetwork.init();
    }

    /* ---------------------------- */
    /* Networking                   */
    /* ---------------------------- */

    private void registerNetworking(final RegisterPayloadHandlersEvent event) {
        GotNetwork.register(event);
        LOGGER.info("GoT networking registered");
    }

    /* ---------------------------- */
    /* Datapack Reload (Maps)       */
    /* ---------------------------- */

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new MapReloadListener());
        LOGGER.info("Registered GoT map reload listener");
    }

    /* ---------------------------- */
    /* Server Work Queue            */
    /* ---------------------------- */

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

    /* ---------------------------- */
    /* Utilities                    */
    /* ---------------------------- */

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        var manager = event.getServer().getResourceManager();

        try (
                var bc = manager.open(ResourceLocation.parse(
                        "got:worldgen/biomecolors/biome_colors.json"));
                var bm = manager.open(ResourceLocation.parse(
                        "got:worldgen/map/biome_map.png"))
        ) {
            BiomeMapLoader.loadBiomeColors(bc);
            BiomeMapLoader.loadBiomeMap(bm);
            BiomeMapLoader.finishLoading();

            System.out.println("[GoT] Biome maps preloaded at server start");

        } catch (Exception e) {
            throw new RuntimeException("Failed to preload biome maps", e);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
