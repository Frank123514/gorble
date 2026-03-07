package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.AbstractEquineModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UndeadHorseRenderer extends AbstractHorseRenderer<AbstractHorse, EquineRenderState, AbstractEquineModel<EquineRenderState>> {
    private static final ResourceLocation ZOMBIE_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_zombie.png");
    private static final ResourceLocation SKELETON_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_skeleton.png");
    private final ResourceLocation texture;

    public UndeadHorseRenderer(EntityRendererProvider.Context context, ModelLayerLocation adultModelLayer, ModelLayerLocation babyModelLayer, boolean skeletonHorse) {
        super(context, new HorseModel(context.bakeLayer(adultModelLayer)), new HorseModel(context.bakeLayer(babyModelLayer)), 1.0F);
        this.texture = skeletonHorse ? SKELETON_TEXTURE : ZOMBIE_TEXTURE;
    }

    public ResourceLocation getTextureLocation(EquineRenderState renderState) {
        return this.texture;
    }

    public EquineRenderState createRenderState() {
        return new EquineRenderState();
    }
}
