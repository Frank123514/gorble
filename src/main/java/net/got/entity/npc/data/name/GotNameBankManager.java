package net.got.entity.npc.data.name;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads NPC name banks from {@code data/got/npc_names/<path>.json}.
 * Each JSON file is a flat array of strings: {@code ["Eddard","Robb","Jon"]}
 */
@EventBusSubscriber(modid = "got", bus = EventBusSubscriber.Bus.MOD)
public class GotNameBankManager extends SimplePreparableReloadListener<Map<ResourceLocation, GotNameBank>> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
    private static final Type NAME_LIST_TYPE = new TypeToken<List<String>>(){}.getType();
    private static final String PREFIX = "npc_names/";
    private static final String SUFFIX = ".json";

    public static final GotNameBankManager INSTANCE = new GotNameBankManager();
    private Map<ResourceLocation, GotNameBank> banks = new HashMap<>();

    @SubscribeEvent
    public static void onAddReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(ResourceLocation.fromNamespaceAndPath("got", "npc_names"), INSTANCE);
    }

    @Override
    protected Map<ResourceLocation, GotNameBank> prepare(ResourceManager mgr, ProfilerFiller profiler) {
        Map<ResourceLocation, GotNameBank> result = new HashMap<>();
        mgr.listResources("npc_names", rl -> rl.getPath().endsWith(SUFFIX)).forEach((rl, resource) -> {
            try (var reader = new InputStreamReader(resource.open(), StandardCharsets.UTF_8)) {
                List<String> names = GSON.fromJson(reader, NAME_LIST_TYPE);
                // Convert npc_names/got/... path back to got:...
                String path = rl.getPath().substring(PREFIX.length(), rl.getPath().length() - SUFFIX.length());
                ResourceLocation bankId = ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), path);
                result.put(bankId, new GotNameBank(names));
            } catch (IOException e) {
                LOGGER.error("Failed to load name bank {}: {}", rl, e.getMessage());
            }
        });
        return result;
    }

    @Override
    protected void apply(Map<ResourceLocation, GotNameBank> prepared, ResourceManager mgr, ProfilerFiller profiler) {
        this.banks = prepared;
        LOGGER.info("Loaded {} GoT NPC name banks", prepared.size());
    }

    /** Retrieves a bank by its resource-location key. Falls back to an empty bank if not found. */
    public GotNameBank fetchBank(ResourceLocation id) {
        return banks.getOrDefault(id, new GotNameBank(List.of(id.getPath())));
    }
}