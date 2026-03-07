package net.minecraft.client.resources.model;

import com.mojang.logging.LogUtils;
import com.mojang.math.Transformation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ModelBakery {
    public static final Material FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/fire_0"));
    public static final Material FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/fire_1"));
    public static final Material LAVA_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/lava_flow"));
    public static final Material WATER_FLOW = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/water_flow"));
    public static final Material WATER_OVERLAY = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/water_overlay"));
    public static final Material BANNER_BASE = new Material(Sheets.BANNER_SHEET, ResourceLocation.withDefaultNamespace("entity/banner_base"));
    public static final Material SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, ResourceLocation.withDefaultNamespace("entity/shield_base"));
    public static final Material NO_PATTERN_SHIELD = new Material(Sheets.SHIELD_SHEET, ResourceLocation.withDefaultNamespace("entity/shield_base_nopattern"));
    public static final int DESTROY_STAGE_COUNT = 10;
    public static final List<ResourceLocation> DESTROY_STAGES = IntStream.range(0, 10)
        .mapToObj(p_349912_ -> ResourceLocation.withDefaultNamespace("block/destroy_stage_" + p_349912_))
        .collect(Collectors.toList());
    public static final List<ResourceLocation> BREAKING_LOCATIONS = DESTROY_STAGES.stream()
        .map(p_349910_ -> p_349910_.withPath(p_349911_ -> "textures/" + p_349911_ + ".png"))
        .collect(Collectors.toList());
    public static final List<RenderType> DESTROY_TYPES = BREAKING_LOCATIONS.stream().map(RenderType::crumbling).collect(Collectors.toList());
    static final Logger LOGGER = LogUtils.getLogger();
    static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
    final Map<ModelBakery.BakedCacheKey, BakedModel> bakedCache = new HashMap<>();
    private final Map<ModelResourceLocation, BakedModel> bakedTopLevelModels = new HashMap<>();
    private final Map<ModelResourceLocation, UnbakedModel> topModels;
    final Map<ResourceLocation, UnbakedModel> unbakedModels;
    final UnbakedModel missingModel;

    public ModelBakery(Map<ModelResourceLocation, UnbakedModel> topModels, Map<ResourceLocation, UnbakedModel> unbackedModels, UnbakedModel missingModel) {
        this.topModels = topModels;
        this.unbakedModels = unbackedModels;
        this.missingModel = missingModel;
    }

    public void bakeModels(ModelBakery.TextureGetter textureGetter) {
        this.topModels.forEach((p_351687_, p_351688_) -> {
            BakedModel bakedmodel = null;

            try {
                bakedmodel = new ModelBakery.ModelBakerImpl(textureGetter, p_351687_).bakeUncached(p_351688_, BlockModelRotation.X0_Y0);
            } catch (Exception exception) {
                LOGGER.warn("Unable to bake model: '{}': {}", p_351687_, exception);
            }

            if (bakedmodel != null) {
                this.bakedTopLevelModels.put(p_351687_, bakedmodel);
            }
        });
    }

    public Map<ModelResourceLocation, BakedModel> getBakedTopLevelModels() {
        return this.bakedTopLevelModels;
    }

    @OnlyIn(Dist.CLIENT)
    static record BakedCacheKey(ResourceLocation id, Transformation transformation, boolean isUvLocked) {
    }

    @OnlyIn(Dist.CLIENT)
    class ModelBakerImpl implements ModelBaker {
        private final Function<Material, TextureAtlasSprite> modelTextureGetter;

        ModelBakerImpl(ModelBakery.TextureGetter textureGetter, ModelResourceLocation modelLocation) {
            this.modelTextureGetter = p_351691_ -> textureGetter.get(modelLocation, p_351691_);
        }

        private UnbakedModel getModel(ResourceLocation name) {
            UnbakedModel unbakedmodel = ModelBakery.this.unbakedModels.get(name);
            if (unbakedmodel == null) {
                ModelBakery.LOGGER.warn("Requested a model that was not discovered previously: {}", name);
                return ModelBakery.this.missingModel;
            } else {
                return unbakedmodel;
            }
        }

        @Override
        @org.jetbrains.annotations.Nullable
        public UnbakedModel getTopLevelModel(ModelResourceLocation location) {
            return topModels.get(location);
        }

        @Override
        public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
            return this.modelTextureGetter;
        }

        @Override
        public BakedModel bake(ResourceLocation location, ModelState transform) {
            return bake(location, transform, this.modelTextureGetter);
        }

        @Override
        public BakedModel bake(ResourceLocation location, ModelState transform, Function<Material, TextureAtlasSprite> sprites) {
            ModelBakery.BakedCacheKey modelbakery$bakedcachekey = new ModelBakery.BakedCacheKey(location, transform.getRotation(), transform.isUvLocked());
            BakedModel bakedmodel = ModelBakery.this.bakedCache.get(modelbakery$bakedcachekey);
            if (bakedmodel != null) {
                return bakedmodel;
            } else {
                UnbakedModel unbakedmodel = this.getModel(location);
                BakedModel bakedmodel1 = this.bakeUncached(unbakedmodel, transform, sprites);
                ModelBakery.this.bakedCache.put(modelbakery$bakedcachekey, bakedmodel1);
                return bakedmodel1;
            }
        }

        BakedModel bakeUncached(UnbakedModel model, ModelState state) {
            return bakeUncached(model, state, this.modelTextureGetter);
        }

        @Override
        public BakedModel bakeUncached(UnbakedModel model, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
            if (model instanceof BlockModel blockmodel && blockmodel.getRootModel() == SpecialModels.GENERATED_MARKER) {
                return ModelBakery.ITEM_MODEL_GENERATOR.generateBlockModel(sprites, blockmodel).bake(sprites, state, false);
            }

            return model.bake(this, sprites, state);
        }
    }

    @FunctionalInterface
    @OnlyIn(Dist.CLIENT)
    public interface TextureGetter {
        TextureAtlasSprite get(ModelResourceLocation modelLocation, Material material);
    }
}
