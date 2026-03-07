package net.minecraft.client.resources.model;

import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BundleItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelDiscovery {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final String INVENTORY_MODEL_PREFIX = "item/";
    private final Map<ResourceLocation, UnbakedModel> inputModels;
    final UnbakedModel missingModel;
    private final Map<ModelResourceLocation, UnbakedModel> topModels = new HashMap<>();
    private final Map<ResourceLocation, UnbakedModel> referencedModels = new HashMap<>();

    public ModelDiscovery(Map<ResourceLocation, UnbakedModel> inputModels, UnbakedModel missingModel) {
        this.inputModels = inputModels;
        this.missingModel = missingModel;
        this.registerTopModel(MissingBlockModel.VARIANT, missingModel);
        this.referencedModels.put(MissingBlockModel.LOCATION, missingModel);
    }

    private static Set<ModelResourceLocation> listMandatoryModels() {
        Set<ModelResourceLocation> set = new HashSet<>();
        BuiltInRegistries.ITEM.listElements().forEach(p_370358_ -> {
            ResourceLocation resourcelocation = p_370358_.value().components().get(DataComponents.ITEM_MODEL);
            if (resourcelocation != null) {
                set.add(ModelResourceLocation.inventory(resourcelocation));
            }

            if (p_370358_.value() instanceof BundleItem bundleitem) {
                set.add(ModelResourceLocation.inventory(bundleitem.openFrontModel()));
                set.add(ModelResourceLocation.inventory(bundleitem.openBackModel()));
            }
        });
        set.add(ItemRenderer.TRIDENT_MODEL);
        set.add(ItemRenderer.SPYGLASS_MODEL);
        net.neoforged.neoforge.client.ClientHooks.onRegisterAdditionalModels(set);
        return set;
    }

    private void registerTopModel(ModelResourceLocation id, UnbakedModel model) {
        this.topModels.put(id, model);
    }

    public void registerStandardModels(BlockStateModelLoader.LoadedModels loadedModels) {
        this.referencedModels.put(SpecialModels.BUILTIN_GENERATED, SpecialModels.GENERATED_MARKER);
        this.referencedModels.put(SpecialModels.BUILTIN_BLOCK_ENTITY, SpecialModels.BLOCK_ENTITY_MARKER);
        Set<ModelResourceLocation> set = listMandatoryModels();
        loadedModels.models().forEach((p_370360_, p_370361_) -> {
            this.registerTopModel(p_370360_, p_370361_.model());
            set.remove(p_370360_);
        });
        this.inputModels
            .keySet()
            .forEach(
                p_370363_ -> {
                    if (p_370363_.getPath().startsWith("item/")) {
                        ModelResourceLocation modelresourcelocation = ModelResourceLocation.inventory(
                            p_370363_.withPath(p_370356_ -> p_370356_.substring("item/".length()))
                        );
                        this.registerTopModel(modelresourcelocation, new ItemModel(p_370363_));
                        set.remove(modelresourcelocation);
                    }
                }
            );
        // Neo: ensure standalone models registered in ModelEvent.RegisterAdditional are loaded
        var it = set.iterator();
        while (it.hasNext()) {
            ModelResourceLocation mrl = it.next();
            if (mrl.getVariant().equals(ModelResourceLocation.STANDALONE_VARIANT)) {
                registerTopModel(mrl, getBlockModel(mrl.id()));
                it.remove();
            }
        }
        if (!set.isEmpty()) {
            LOGGER.warn("Missing mandatory models: {}", set.stream().map(p_370354_ -> "\n\t" + p_370354_).collect(Collectors.joining()));
        }
    }

    public void discoverDependencies() {
        this.topModels.values().forEach(p_370355_ -> p_370355_.resolveDependencies(new ModelDiscovery.ResolverImpl()));
    }

    public Map<ModelResourceLocation, UnbakedModel> getTopModels() {
        return this.topModels;
    }

    public Map<ResourceLocation, UnbakedModel> getReferencedModels() {
        return this.referencedModels;
    }

    UnbakedModel getBlockModel(ResourceLocation modelLocation) {
        return this.referencedModels.computeIfAbsent(modelLocation, this::loadBlockModel);
    }

    private UnbakedModel loadBlockModel(ResourceLocation modelLocation) {
        UnbakedModel unbakedmodel = this.inputModels.get(modelLocation);
        if (unbakedmodel == null) {
            LOGGER.warn("Missing block model: '{}'", modelLocation);
            return this.missingModel;
        } else {
            return unbakedmodel;
        }
    }

    @OnlyIn(Dist.CLIENT)
    class ResolverImpl implements UnbakedModel.Resolver {
        private final List<ResourceLocation> stack = new ArrayList<>();
        private final Set<ResourceLocation> resolvedModels = new HashSet<>();

        @Override
        public UnbakedModel resolve(ResourceLocation p_361784_) {
            if (this.stack.contains(p_361784_)) {
                ModelDiscovery.LOGGER.warn("Detected model loading loop: {}->{}", this.stacktraceToString(), p_361784_);
                return ModelDiscovery.this.missingModel;
            } else {
                UnbakedModel unbakedmodel = ModelDiscovery.this.getBlockModel(p_361784_);
                if (this.resolvedModels.add(p_361784_)) {
                    this.stack.add(p_361784_);
                    unbakedmodel.resolveDependencies(this);
                    this.stack.remove(p_361784_);
                }

                return unbakedmodel;
            }
        }

        private String stacktraceToString() {
            return this.stack.stream().map(ResourceLocation::toString).collect(Collectors.joining("->"));
        }
    }
}
