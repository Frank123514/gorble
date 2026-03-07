package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSkeletonRenderer<T extends AbstractSkeleton, S extends SkeletonRenderState> extends HumanoidMobRenderer<T, S, SkeletonModel<S>> {
    public AbstractSkeletonRenderer(
        EntityRendererProvider.Context context, ModelLayerLocation modelLayer, ModelLayerLocation skeletonLayer, ModelLayerLocation innerModelLayer
    ) {
        this(context, skeletonLayer, innerModelLayer, new SkeletonModel<>(context.bakeLayer(modelLayer)));
    }

    public AbstractSkeletonRenderer(
        EntityRendererProvider.Context context, ModelLayerLocation skeletonLayer, ModelLayerLocation innerModelLayer, SkeletonModel<S> model
    ) {
        super(context, model, 0.5F);
        this.addLayer(
            new HumanoidArmorLayer<>(
                this, new SkeletonModel(context.bakeLayer(skeletonLayer)), new SkeletonModel(context.bakeLayer(innerModelLayer)), context.getEquipmentRenderer()
            )
        );
    }

    public void extractRenderState(T entity, S reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isAggressive = entity.isAggressive();
        reusedState.isShaking = entity.isShaking();
    }

    protected boolean isShaking(S renderState) {
        return renderState.isShaking;
    }
}
