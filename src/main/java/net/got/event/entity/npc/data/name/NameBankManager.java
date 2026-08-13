package net.got.event.entity.npc.data.name;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.Identifier;
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

@EventBusSubscriber(modid = "got")
public class NameBankManager extends SimplePreparableReloadListener<Map<Identifier, NameBank>> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
    private static final Type NAME_LIST_TYPE = new TypeToken<List<String>>(){}.getType();
    private static final String PREFIX = "npc_names/";
    private static final String SUFFIX = ".json";

    public static final NameBankManager INSTANCE = new NameBankManager();
    private Map<Identifier, NameBank> banks = new HashMap<>();

    @SubscribeEvent
    public static void onAddReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(Identifier.fromNamespaceAndPath("got", "npc_names"), INSTANCE);
    }

    @Override
    protected Map<Identifier, NameBank> prepare(ResourceManager mgr, ProfilerFiller profiler) {
        Map<Identifier, NameBank> result = new HashMap<>();
        mgr.listResources("npc_names", rl -> rl.getPath().endsWith(SUFFIX)).forEach((rl, resource) -> {
            try (var reader = new InputStreamReader(resource.open(), StandardCharsets.UTF_8)) {
                List<String> names = GSON.fromJson(reader, NAME_LIST_TYPE);
                
                String path = rl.getPath().substring(PREFIX.length(), rl.getPath().length() - SUFFIX.length());
                Identifier bankId = Identifier.fromNamespaceAndPath(rl.getNamespace(), path);
                result.put(bankId, new NameBank(names));
            } catch (IOException e) {
                LOGGER.error("Failed to load name bank {}: {}", rl, e.getMessage());
            }
        });
        return result;
    }

    @Override
    protected void apply(Map<Identifier, NameBank> prepared, ResourceManager mgr, ProfilerFiller profiler) {
        this.banks = prepared;
        LOGGER.info("Loaded {} GoT NPC name banks", prepared.size());
    }

    public NameBank fetchBank(Identifier id) {
        return banks.getOrDefault(id, new NameBank(List.of(id.getPath())));
    }
}