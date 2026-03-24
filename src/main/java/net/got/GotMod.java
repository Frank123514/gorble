package net.got;

import net.got.client.GotClient;
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
        IEventBus modBus = ModLoadingContext
                .get()
                .getActiveContainer()
                .getEventBus();

        /* ---------- Lifecycle ---------- */
        assert modBus != null;
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerNetworking);

        /* ---------- Registries ---------- */
        GotWoodTypes.init();
        GotModMenus.REGISTRY.register(modBus);
        GotModRecipeTypes.REGISTRY.register(modBus);
        // FIX: register the baking recipe serializer and its recipe book category
        GotModRecipeSerializers.REGISTRY.register(modBus);
        GotModRecipeSerializers.CATEGORY_REGISTRY.register(modBus);
        GotModBlocks.REGISTRY.register(modBus);
        GotModItems.REGISTRY.register(modBus);
        GotModBlockEntities.REGISTRY.register(modBus);
        GotModBoatEntities.REGISTRY.register(modBus);
        GotModTabs.register(modBus);
        ModSounds.register(modBus);
        WorldgenRegistries.register(modBus);

        // ── NPC entities ────────────────────────────────────────────────
        GotModEntities.REGISTRY.register(modBus);
        // GotEntityEvents is @EventBusSubscriber(Bus.MOD) — auto-registered

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
        // HangingSignBlockEntity hardcodes BlockEntityType.HANGING_SIGN internally.
        // We must add our custom hanging sign blocks to that type's validBlocks set
        // so BlockEntity.validateBlockState() passes. The field is exposed via AT.
        event.enqueueWork(() -> {
            HashSet<net.minecraft.world.level.block.Block> blocks =
                    new HashSet<>(BlockEntityType.HANGING_SIGN.validBlocks);
            java.util.Arrays.asList(
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
                    GotModBlocks.WORMTREE_HANGING_SIGN.get(), GotModBlocks.WORMTREE_WALL_HANGING_SIGN.get()
            ).forEach(blocks::add);
            BlockEntityType.HANGING_SIGN.validBlocks = blocks;
        });

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

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}