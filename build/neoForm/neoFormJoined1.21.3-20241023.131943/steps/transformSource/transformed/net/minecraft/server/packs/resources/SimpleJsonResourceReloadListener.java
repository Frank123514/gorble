package net.minecraft.server.packs.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public abstract class SimpleJsonResourceReloadListener<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final DynamicOps<JsonElement> ops;
    private final Codec<T> codec;
    private final String directory;

    protected SimpleJsonResourceReloadListener(HolderLookup.Provider registries, Codec<T> codec, String directory) {
        this(registries.createSerializationContext(JsonOps.INSTANCE), codec, directory);
    }

    protected SimpleJsonResourceReloadListener(Codec<T> codec, String directory) {
        this(JsonOps.INSTANCE, codec, directory);
    }

    private SimpleJsonResourceReloadListener(DynamicOps<JsonElement> ops, Codec<T> codec, String directory) {
        this.ops = ops;
        this.codec = codec;
        this.directory = directory;
    }

    /**
     * Performs any reloading that can be done off-thread, such as file IO
     */
    protected Map<ResourceLocation, T> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, T> map = new HashMap<>();
        // Neo: add condition context
        scanDirectory(resourceManager, this.directory, this.makeConditionalOps(), this.codec, map);
        return map;
    }

    public static <T> void scanDirectory(
        ResourceManager resourceManager, String directory, DynamicOps<JsonElement> ops, Codec<T> codec, Map<ResourceLocation, T> results
    ) {
        var conditionalCodec = net.neoforged.neoforge.common.conditions.ConditionalOps.createConditionalCodec(codec);
        FileToIdConverter filetoidconverter = FileToIdConverter.json(directory);

        for (Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(resourceManager).entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            ResourceLocation resourcelocation1 = filetoidconverter.fileToId(resourcelocation);

            try (Reader reader = entry.getValue().openAsReader()) {
                conditionalCodec.parse(ops, JsonParser.parseReader(reader)).ifSuccess(p_371454_ -> {
                    if (p_371454_.isEmpty()) {
                        LOGGER.debug("Skipping loading data file '{}' from '{}' as its conditions were not met", resourcelocation1, resourcelocation);
                    } else if (results.putIfAbsent(resourcelocation1, p_371454_.get()) != null) {
                        throw new IllegalStateException("Duplicate data file ignored with ID " + resourcelocation1);
                    }
                }).ifError(p_371566_ -> LOGGER.error("Couldn't parse data file '{}' from '{}': {}", resourcelocation1, resourcelocation, p_371566_));
            } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
                LOGGER.error("Couldn't parse data file '{}' from '{}'", resourcelocation1, resourcelocation, jsonparseexception);
            }
        }
    }

    protected ResourceLocation getPreparedPath(ResourceLocation rl) {
        return rl.withPath(this.directory + "/" + rl.getPath() + ".json");
    }
}
