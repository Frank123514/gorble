package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.CrossbowItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PiglinRenderer extends HumanoidMobRenderer<AbstractPiglin, PiglinRenderState, PiglinModel> {
    private static final ResourceLocation PIGLIN_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/piglin/piglin.png");
    private static final ResourceLocation PIGLIN_BRUTE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/piglin/piglin_brute.png");
    public static final CustomHeadLayer.Transforms PIGLIN_CUSTOM_HEAD_TRANSFORMS = new CustomHeadLayer.Transforms(0.0F, 0.0F, 1.0019531F);

    public PiglinRenderer(
        EntityRendererProvider.Context context,
        ModelLayerLocation adultModelLayer,
        ModelLayerLocation babyModelLayer,
        ModelLayerLocation innerModel,
        ModelLayerLocation outerModel,
        ModelLayerLocation innerModelBaby,
        ModelLayerLocation outerModelBaby
    ) {
        super(context, new PiglinModel(context.bakeLayer(adultModelLayer)), new PiglinModel(context.bakeLayer(babyModelLayer)), 0.5F, PIGLIN_CUSTOM_HEAD_TRANSFORMS);
        this.addLayer(
            new HumanoidArmorLayer<>(
                this,
                new HumanoidArmorModel(context.bakeLayer(innerModel)),
                new HumanoidArmorModel(context.bakeLayer(outerModel)),
                new HumanoidArmorModel(context.bakeLayer(innerModelBaby)),
                new HumanoidArmorModel(context.bakeLayer(outerModelBaby)),
                context.getEquipmentRenderer()
            )
        );
    }

    public ResourceLocation getTextureLocation(PiglinRenderState renderState) {
        return renderState.isBrute ? PIGLIN_BRUTE_LOCATION : PIGLIN_LOCATION;
    }

    public PiglinRenderState createRenderState() {
        return new PiglinRenderState();
    }

    public void extractRenderState(AbstractPiglin entity, PiglinRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isBrute = entity.getType() == EntityType.PIGLIN_BRUTE;
        reusedState.armPose = entity.getArmPose();
        reusedState.maxCrossbowChageDuration = (float)CrossbowItem.getChargeDuration(entity.getUseItem(), entity);
        reusedState.isConverting = entity.isConverting();
    }

    protected boolean isShaking(PiglinRenderState renderState) {
        return super.isShaking(renderState) || renderState.isConverting;
    }
}
