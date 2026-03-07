package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeRenderer extends MobRenderer<Slime, SlimeRenderState, SlimeModel> {
    public static final ResourceLocation SLIME_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/slime/slime.png");

    public SlimeRenderer(EntityRendererProvider.Context p_174391_) {
        super(p_174391_, new SlimeModel(p_174391_.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new SlimeOuterLayer(this, p_174391_.getModelSet()));
    }

    public void render(SlimeRenderState p_364733_, PoseStack p_115952_, MultiBufferSource p_115953_, int p_115954_) {
        this.shadowRadius = 0.25F * (float)p_364733_.size;
        super.render(p_364733_, p_115952_, p_115953_, p_115954_);
    }

    protected void scale(SlimeRenderState p_364158_, PoseStack p_115964_) {
        float f = 0.999F;
        p_115964_.scale(0.999F, 0.999F, 0.999F);
        p_115964_.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float)p_364158_.size;
        float f2 = p_364158_.squish / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        p_115964_.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }

    public ResourceLocation getTextureLocation(SlimeRenderState p_365351_) {
        return SLIME_LOCATION;
    }

    public SlimeRenderState createRenderState() {
        return new SlimeRenderState();
    }

    public void extractRenderState(Slime p_362664_, SlimeRenderState p_365237_, float p_361099_) {
        super.extractRenderState(p_362664_, p_365237_, p_361099_);
        p_365237_.squish = Mth.lerp(p_361099_, p_362664_.oSquish, p_362664_.squish);
        p_365237_.size = p_362664_.getSize();
    }
}
