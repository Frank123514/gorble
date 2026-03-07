package net.minecraft.client.resources.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelManager implements PreparableReloadListener, AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter BLOCKSTATE_LISTER = FileToIdConverter.json("blockstates");
    private static final FileToIdConverter MODEL_LISTER = FileToIdConverter.json("models");
    private static final Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES = Map.of(
        Sheets.BANNER_SHEET,
        ResourceLocation.withDefaultNamespace("banner_patterns"),
        Sheets.BED_SHEET,
        ResourceLocation.withDefaultNamespace("beds"),
        Sheets.CHEST_SHEET,
        ResourceLocation.withDefaultNamespace("chests"),
        Sheets.SHIELD_SHEET,
        ResourceLocation.withDefaultNamespace("shield_patterns"),
        Sheets.SIGN_SHEET,
        ResourceLocation.withDefaultNamespace("signs"),
        Sheets.SHULKER_SHEET,
        ResourceLocation.withDefaultNamespace("shulker_boxes"),
        Sheets.ARMOR_TRIMS_SHEET,
        ResourceLocation.withDefaultNamespace("armor_trims"),
        Sheets.DECORATED_POT_SHEET,
        ResourceLocation.withDefaultNamespace("decorated_pot"),
        TextureAtlas.LOCATION_BLOCKS,
        ResourceLocation.withDefaultNamespace("blocks")
    );
    private Map<ModelResourceLocation, BakedModel> bakedRegistry = new java.util.HashMap<>();
    private final AtlasSet atlases;
    private final BlockModelShaper blockModelShaper;
    private final BlockColors blockColors;
    private int maxMipmapLevels;
    private BakedModel missingModel;
    private Object2IntMap<BlockState> modelGroups;
    private ModelBakery modelBakery;

    public ModelManager(TextureManager textureManager, BlockColors blockColors, int maxMipmapLevels) {
        this.blockColors = blockColors;
        this.maxMipmapLevels = maxMipmapLevels;
        this.blockModelShaper = new BlockModelShaper(this);
        Map<ResourceLocation, ResourceLocation> VANILLA_ATLASES = net.neoforged.neoforge.client.ClientHooks.gatherMaterialAtlases(ModelManager.VANILLA_ATLASES);
        this.atlases = new AtlasSet(VANILLA_ATLASES, textureManager);
    }

    public BakedModel getModel(ModelResourceLocation modelLocation) {
        return this.bakedRegistry.getOrDefault(modelLocation, this.missingModel);
    }

    public BakedModel getMissingModel() {
        return this.missingModel;
    }

    public BlockModelShaper getBlockModelShaper() {
        return this.blockModelShaper;
    }

    @Override
    public final CompletableFuture<Void> reload(
        PreparableReloadListener.PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor
    ) {
        net.neoforged.neoforge.client.model.geometry.GeometryLoaderManager.init();
        UnbakedModel unbakedmodel = MissingBlockModel.missingModel();
        BlockStateModelLoader blockstatemodelloader = new BlockStateModelLoader(unbakedmodel);
        CompletableFuture<Map<ResourceLocation, UnbakedModel>> completablefuture = loadBlockModels(manager, backgroundExecutor);
        CompletableFuture<BlockStateModelLoader.LoadedModels> completablefuture1 = loadBlockStates(blockstatemodelloader, manager, backgroundExecutor);
        CompletableFuture<ModelDiscovery> completablefuture2 = completablefuture1.thenCombineAsync(
            completablefuture,
            (p_359302_, p_359303_) -> this.discoverModelDependencies(unbakedmodel, (Map<ResourceLocation, UnbakedModel>)p_359303_, p_359302_),
            backgroundExecutor
        );
        CompletableFuture<Object2IntMap<BlockState>> completablefuture3 = completablefuture1.thenApplyAsync(
            p_359309_ -> buildModelGroups(this.blockColors, p_359309_), backgroundExecutor
        );
        Map<ResourceLocation, CompletableFuture<AtlasSet.StitchResult>> map = this.atlases.scheduleLoad(manager, this.maxMipmapLevels, backgroundExecutor);
        return CompletableFuture.allOf(
                Stream.concat(map.values().stream(), Stream.of(completablefuture2, completablefuture3)).toArray(CompletableFuture[]::new)
            )
            .thenApplyAsync(
                p_372565_ -> {
                    Map<ResourceLocation, AtlasSet.StitchResult> map1 = map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Entry::getKey, p_248988_ -> p_248988_.getValue().join()));
                    ModelDiscovery modeldiscovery = completablefuture2.join();
                    Object2IntMap<BlockState> object2intmap = completablefuture3.join();
                    return this.loadModels(
                        Profiler.get(), map1, new ModelBakery(modeldiscovery.getTopModels(), modeldiscovery.getReferencedModels(), unbakedmodel), object2intmap
                    );
                },
                backgroundExecutor
            )
            .thenCompose(p_252255_ -> p_252255_.readyForUpload.thenApply(p_251581_ -> (ModelManager.ReloadState)p_252255_))
            .thenCompose(barrier::wait)
            .thenAcceptAsync(p_372566_ -> this.apply(p_372566_, Profiler.get()), gameExecutor);
    }

    private static CompletableFuture<Map<ResourceLocation, UnbakedModel>> loadBlockModels(ResourceManager resourceManager, Executor executor) {
        return CompletableFuture.<Map<ResourceLocation, Resource>>supplyAsync(() -> MODEL_LISTER.listMatchingResources(resourceManager), executor)
            .thenCompose(
                p_250597_ -> {
                    List<CompletableFuture<Pair<ResourceLocation, BlockModel>>> list = new ArrayList<>(p_250597_.size());

                    for (Entry<ResourceLocation, Resource> entry : p_250597_.entrySet()) {
                        list.add(CompletableFuture.supplyAsync(() -> {
                            ResourceLocation resourcelocation = MODEL_LISTER.fileToId(entry.getKey());

                            try {
                                Pair pair;
                                try (Reader reader = entry.getValue().openAsReader()) {
                                    BlockModel blockmodel = BlockModel.fromStream(reader);
                                    blockmodel.name = resourcelocation.toString();
                                    pair = Pair.of(resourcelocation, blockmodel);
                                }

                                return pair;
                            } catch (Exception exception) {
                                LOGGER.error("Failed to load model {}", entry.getKey(), exception);
                                return null;
                            }
                        }, executor));
                    }

                    return Util.sequence(list)
                        .thenApply(
                            p_250813_ -> p_250813_.stream().filter(Objects::nonNull).collect(Collectors.toUnmodifiableMap(Pair::getFirst, Pair::getSecond))
                        );
                }
            );
    }

    private ModelDiscovery discoverModelDependencies(
        UnbakedModel missingModel, Map<ResourceLocation, UnbakedModel> inputModels, BlockStateModelLoader.LoadedModels loadedModels
    ) {
        ModelDiscovery modeldiscovery = new ModelDiscovery(inputModels, missingModel);
        modeldiscovery.registerStandardModels(loadedModels);
        modeldiscovery.discoverDependencies();
        return modeldiscovery;
    }

    private static CompletableFuture<BlockStateModelLoader.LoadedModels> loadBlockStates(
        BlockStateModelLoader modelLoader, ResourceManager resourceManager, Executor backgroundExecutor
    ) {
        Function<ResourceLocation, StateDefinition<Block, BlockState>> function = BlockStateModelLoader.definitionLocationToBlockMapper();
        return CompletableFuture.<Map<ResourceLocation, List<Resource>>>supplyAsync(() -> BLOCKSTATE_LISTER.listMatchingResourceStacks(resourceManager), backgroundExecutor)
            .thenCompose(p_359307_ -> {
                List<CompletableFuture<BlockStateModelLoader.LoadedModels>> list = new ArrayList<>(p_359307_.size());

                for (Entry<ResourceLocation, List<Resource>> entry : p_359307_.entrySet()) {
                    list.add(CompletableFuture.supplyAsync(() -> {
                        ResourceLocation resourcelocation = BLOCKSTATE_LISTER.fileToId(entry.getKey());
                        StateDefinition<Block, BlockState> statedefinition = function.apply(resourcelocation);
                        if (statedefinition == null) {
                            LOGGER.debug("Discovered unknown block state definition {}, ignoring", resourcelocation);
                            return null;
                        } else {
                            List<Resource> list1 = entry.getValue();
                            List<BlockStateModelLoader.LoadedBlockModelDefinition> list2 = new ArrayList<>(list1.size());

                            for (Resource resource : list1) {
                                try (Reader reader = resource.openAsReader()) {
                                    JsonObject jsonobject = GsonHelper.parse(reader);
                                    BlockModelDefinition blockmodeldefinition = BlockModelDefinition.fromJsonElement(jsonobject);
                                    list2.add(new BlockStateModelLoader.LoadedBlockModelDefinition(resource.sourcePackId(), blockmodeldefinition));
                                } catch (Exception exception1) {
                                    LOGGER.error("Failed to load blockstate definition {} from pack {}", resourcelocation, resource.sourcePackId(), exception1);
                                }
                            }

                            try {
                                return modelLoader.loadBlockStateDefinitionStack(resourcelocation, statedefinition, list2);
                            } catch (Exception exception) {
                                LOGGER.error("Failed to load blockstate definition {}", resourcelocation, exception);
                                return null;
                            }
                        }
                    }, backgroundExecutor));
                }

                return Util.sequence(list).thenApply(p_359308_ -> {
                    Map<ModelResourceLocation, BlockStateModelLoader.LoadedModel> map = new HashMap<>();

                    for (BlockStateModelLoader.LoadedModels blockstatemodelloader$loadedmodels : p_359308_) {
                        if (blockstatemodelloader$loadedmodels != null) {
                            map.putAll(blockstatemodelloader$loadedmodels.models());
                        }
                    }

                    return new BlockStateModelLoader.LoadedModels(map);
                });
            });
    }

    private ModelManager.ReloadState loadModels(
        ProfilerFiller profiler, Map<ResourceLocation, AtlasSet.StitchResult> stitchResults, ModelBakery modelBakery, Object2IntMap<BlockState> modelGroups
    ) {
        profiler.push("baking");
        Multimap<ModelResourceLocation, Material> multimap = HashMultimap.create();
        modelBakery.bakeModels((p_352403_, p_251262_) -> {
            AtlasSet.StitchResult atlasset$stitchresult = stitchResults.get(p_251262_.atlasLocation());
            TextureAtlasSprite textureatlassprite = atlasset$stitchresult.getSprite(p_251262_.texture());
            if (textureatlassprite != null) {
                return textureatlassprite;
            } else {
                multimap.put(p_352403_, p_251262_);
                return atlasset$stitchresult.missing();
            }
        });
        multimap.asMap()
            .forEach(
                (p_352087_, p_252017_) -> LOGGER.warn(
                        "Missing textures in model {}:\n{}",
                        p_352087_,
                        p_252017_.stream()
                            .sorted(Material.COMPARATOR)
                            .map(p_339314_ -> "    " + p_339314_.atlasLocation() + ":" + p_339314_.texture())
                            .collect(Collectors.joining("\n"))
                    )
            );
        profiler.popPush("forge_modify_baking_result");
        net.neoforged.neoforge.client.ClientHooks.onModifyBakingResult(modelBakery.getBakedTopLevelModels(), stitchResults, modelBakery);
        profiler.popPush("dispatch");
        Map<ModelResourceLocation, BakedModel> map = modelBakery.getBakedTopLevelModels();
        BakedModel bakedmodel = map.get(MissingBlockModel.VARIANT);
        Map<BlockState, BakedModel> map1 = new IdentityHashMap<>();

        for (Block block : BuiltInRegistries.BLOCK) {
            block.getStateDefinition().getPossibleStates().forEach(p_250633_ -> {
                ResourceLocation resourcelocation = p_250633_.getBlock().builtInRegistryHolder().key().location();
                BakedModel bakedmodel1 = map.getOrDefault(BlockModelShaper.stateToModelLocation(resourcelocation, p_250633_), bakedmodel);
                map1.put(p_250633_, bakedmodel1);
            });
        }

        CompletableFuture<Void> completablefuture = CompletableFuture.allOf(
            stitchResults.values().stream().map(AtlasSet.StitchResult::readyForUpload).toArray(CompletableFuture[]::new)
        );
        profiler.pop();
        return new ModelManager.ReloadState(modelBakery, modelGroups, bakedmodel, map1, stitchResults, completablefuture);
    }

    private static Object2IntMap<BlockState> buildModelGroups(BlockColors blockColors, BlockStateModelLoader.LoadedModels loadedModels) {
        return ModelGroupCollector.build(blockColors, loadedModels);
    }

    private void apply(ModelManager.ReloadState reloadState, ProfilerFiller profiler) {
        profiler.push("upload");
        reloadState.atlasPreparations.values().forEach(AtlasSet.StitchResult::upload);
        ModelBakery modelbakery = reloadState.modelBakery;
        this.bakedRegistry = modelbakery.getBakedTopLevelModels();
        this.modelGroups = reloadState.modelGroups;
        this.missingModel = reloadState.missingModel;
        this.modelBakery = modelbakery;
        net.neoforged.neoforge.client.ClientHooks.onModelBake(this, this.bakedRegistry, modelbakery);
        profiler.popPush("cache");
        this.blockModelShaper.replaceCache(reloadState.modelCache);
        profiler.pop();
    }

    public boolean requiresRender(BlockState oldState, BlockState newState) {
        if (oldState == newState) {
            return false;
        } else {
            int i = this.modelGroups.getInt(oldState);
            if (i != -1) {
                int j = this.modelGroups.getInt(newState);
                if (i == j) {
                    FluidState fluidstate = oldState.getFluidState();
                    FluidState fluidstate1 = newState.getFluidState();
                    return fluidstate != fluidstate1;
                }
            }

            return true;
        }
    }

    public TextureAtlas getAtlas(ResourceLocation location) {
        if (this.atlases == null) throw new RuntimeException("getAtlasTexture called too early!");
        return this.atlases.getAtlas(location);
    }

    @Override
    public void close() {
        this.atlases.close();
    }

    public void updateMaxMipLevel(int level) {
        this.maxMipmapLevels = level;
    }

    public ModelBakery getModelBakery() {
        return com.google.common.base.Preconditions.checkNotNull(modelBakery, "Attempted to query model bakery before it has been initialized.");
    }

    @OnlyIn(Dist.CLIENT)
    static record ReloadState(
        ModelBakery modelBakery,
        Object2IntMap<BlockState> modelGroups,
        BakedModel missingModel,
        Map<BlockState, BakedModel> modelCache,
        Map<ResourceLocation, AtlasSet.StitchResult> atlasPreparations,
        CompletableFuture<Void> readyForUpload
    ) {
    }
}
