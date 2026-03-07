package net.minecraft.world.level.storage;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.FastBufferedInputStream;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;

public class DimensionDataStorage implements AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Map<String, Optional<SavedData>> cache = new HashMap<>();
    private final DataFixer fixerUpper;
    private final HolderLookup.Provider registries;
    private final Path dataFolder;
    private CompletableFuture<?> pendingWriteFuture = CompletableFuture.completedFuture(null);

    public DimensionDataStorage(Path dataFolder, DataFixer fixerUpper, HolderLookup.Provider registries) {
        this.fixerUpper = fixerUpper;
        this.dataFolder = dataFolder;
        this.registries = registries;
    }

    private Path getDataFile(String filename) {
        return this.dataFolder.resolve(filename + ".dat");
    }

    public <T extends SavedData> T computeIfAbsent(SavedData.Factory<T> factory, String name) {
        T t = this.get(factory, name);
        if (t != null) {
            return t;
        } else {
            T t1 = (T)factory.constructor().get();
            this.set(name, t1);
            return t1;
        }
    }

    @Nullable
    public <T extends SavedData> T get(SavedData.Factory<T> factory, String name) {
        Optional<SavedData> optional = this.cache.get(name);
        if (optional == null) {
            optional = Optional.ofNullable(this.readSavedData(factory.deserializer(), factory.type(), name));
            this.cache.put(name, optional);
        }

        return (T)optional.orElse(null);
    }

    @Nullable
    private <T extends SavedData> T readSavedData(BiFunction<CompoundTag, HolderLookup.Provider, T> reader, @Nullable DataFixTypes dataFixType, String filename) {
        try {
            Path path = this.getDataFile(filename);
            if (Files.exists(path)) {
                CompoundTag compoundtag = this.readTagFromDisk(filename, dataFixType, SharedConstants.getCurrentVersion().getDataVersion().getVersion());
                return reader.apply(compoundtag.getCompound("data"), this.registries);
            }
        } catch (Exception exception) {
            LOGGER.error("Error loading saved data: {}", filename, exception);
        }

        return null;
    }

    public void set(String name, SavedData savedData) {
        this.cache.put(name, Optional.of(savedData));
        savedData.setDirty();
    }

    public CompoundTag readTagFromDisk(String filename, @Nullable DataFixTypes dataFixType, int version) throws IOException {
        CompoundTag compoundtag1;
        try (
            InputStream inputstream = Files.newInputStream(this.getDataFile(filename));
            PushbackInputStream pushbackinputstream = new PushbackInputStream(new FastBufferedInputStream(inputstream), 2);
        ) {
            CompoundTag compoundtag;
            if (this.isGzip(pushbackinputstream)) {
                compoundtag = NbtIo.readCompressed(pushbackinputstream, NbtAccounter.unlimitedHeap());
            } else {
                try (DataInputStream datainputstream = new DataInputStream(pushbackinputstream)) {
                    compoundtag = NbtIo.read(datainputstream);
                }
            }

            if (dataFixType != null) {
                int i = NbtUtils.getDataVersion(compoundtag, 1343);
                compoundtag1 = dataFixType.update(this.fixerUpper, compoundtag, i, version);
            } else {
                compoundtag1 = compoundtag;
            }
        }

        // Neo: delete any temporary files so that we don't inflate disk space unnecessarily.
        net.neoforged.neoforge.common.IOUtilities.cleanupTempFiles(this.dataFolder, filename);

        return compoundtag1;
    }

    private boolean isGzip(PushbackInputStream inputStream) throws IOException {
        byte[] abyte = new byte[2];
        boolean flag = false;
        int i = inputStream.read(abyte, 0, 2);
        if (i == 2) {
            int j = (abyte[1] & 255) << 8 | abyte[0] & 255;
            if (j == 35615) {
                flag = true;
            }
        }

        if (i != 0) {
            inputStream.unread(abyte, 0, i);
        }

        return flag;
    }

    public CompletableFuture<?> scheduleSave() {
        Map<Path, CompoundTag> map = this.collectDirtyTagsToSave();
        if (map.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        } else {
            this.pendingWriteFuture = this.pendingWriteFuture
                .thenCompose(
                    p_360267_ -> CompletableFuture.allOf(
                            map.entrySet().stream().map(p_360262_ -> tryWriteAsync(p_360262_.getKey(), p_360262_.getValue())).toArray(CompletableFuture[]::new)
                        )
                );
            return this.pendingWriteFuture;
        }
    }

    private Map<Path, CompoundTag> collectDirtyTagsToSave() {
        Map<Path, CompoundTag> map = new Object2ObjectArrayMap<>();
        this.cache
            .forEach(
                (p_360264_, p_360265_) -> p_360265_.filter(SavedData::isDirty)
                        .ifPresent(p_360261_ -> map.put(this.getDataFile(p_360264_), p_360261_.save(this.registries)))
            );
        return map;
    }

    private static CompletableFuture<Void> tryWriteAsync(Path path, CompoundTag tag) {
        return CompletableFuture.runAsync(() -> {
            try {
                // Neo: ensure parent directories exist if the SavedData's path contains slashes
                if (!Files.exists(path)) {
                    Files.createDirectories(path.getParent());
                }
                net.neoforged.neoforge.common.IOUtilities.writeNbtCompressed(tag, path);
            } catch (IOException ioexception) {
                LOGGER.error("Could not save data to {}", path.getFileName(), ioexception);
            }
        }, Util.ioPool());
    }

    public void saveAndJoin() {
        this.scheduleSave().join();
    }

    @Override
    public void close() {
        this.saveAndJoin();
    }
}
