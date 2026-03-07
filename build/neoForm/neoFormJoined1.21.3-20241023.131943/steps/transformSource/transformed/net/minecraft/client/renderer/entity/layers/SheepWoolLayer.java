package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepWoolLayer extends RenderLayer<SheepRenderState, SheepModel> {
    private static final ResourceLocation SHEEP_FUR_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/sheep/sheep_fur.png");
    private final EntityModel<SheepRenderState> adultModel;
    private final EntityModel<SheepRenderState> babyModel;

    public SheepWoolLayer(RenderLayerParent<SheepRenderState, SheepModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.adultModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_WOOL));
        this.babyModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_BABY_WOOL));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SheepRenderState renderState, float yRot, float xRot) {
        if (!renderState.isSheared) {
            EntityModel<SheepRenderState> entitymodel = renderState.isBaby ? this.babyModel : this.adultModel;
            if (renderState.isInvisible) {
                if (renderState.appearsGlowing) {
                    entitymodel.setupAnim(renderState);
                    VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.outline(SHEEP_FUR_LOCATION));
                    entitymodel.renderToBuffer(poseStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(renderState, 0.0F), -16777216);
                }
            } else {
                int i;
                if (renderState.customName != null && "jeb_".equals(renderState.customName.getString())) {
                    int j = 25;
                    int k = Mth.floor(renderState.ageInTicks);
                    int l = k / 25 + renderState.id;
                    int i1 = DyeColor.values().length;
                    int j1 = l % i1;
                    int k1 = (l + 1) % i1;
                    float f = ((float)(k % 25) + Mth.frac(renderState.ageInTicks)) / 25.0F;
                    int l1 = Sheep.getColor(DyeColor.byId(j1));
                    int i2 = Sheep.getColor(DyeColor.byId(k1));
                    i = ARGB.lerp(f, l1, i2);
                } else {
                    i = Sheep.getColor(renderState.woolColor);
                }

                coloredCutoutModelCopyLayerRender(entitymodel, SHEEP_FUR_LOCATION, poseStack, bufferSource, packedLight, renderState, i);
            }
        }
    }
}
