package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractBoatRenderer extends EntityRenderer<AbstractBoat, BoatRenderState> {
    public AbstractBoatRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
    }

    public void render(BoatRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - renderState.yRot));
        float f = renderState.hurtTime;
        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * renderState.damageTime / 10.0F * (float)renderState.hurtDir));
        }

        if (!Mth.equal(renderState.bubbleAngle, 0.0F)) {
            poseStack.mulPose(new Quaternionf().setAngleAxis(renderState.bubbleAngle * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        EntityModel<BoatRenderState> entitymodel = this.model();
        entitymodel.setupAnim(renderState);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.renderType());
        entitymodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        this.renderTypeAdditions(renderState, poseStack, bufferSource, packedLight);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    protected void renderTypeAdditions(BoatRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
    }

    protected abstract EntityModel<BoatRenderState> model();

    protected abstract RenderType renderType();

    public BoatRenderState createRenderState() {
        return new BoatRenderState();
    }

    public void extractRenderState(AbstractBoat entity, BoatRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.hurtTime = (float)entity.getHurtTime() - partialTick;
        reusedState.hurtDir = entity.getHurtDir();
        reusedState.damageTime = Math.max(entity.getDamage() - partialTick, 0.0F);
        reusedState.bubbleAngle = entity.getBubbleAngle(partialTick);
        reusedState.isUnderWater = entity.isUnderWater();
        reusedState.rowingTimeLeft = entity.getRowingTime(0, partialTick);
        reusedState.rowingTimeRight = entity.getRowingTime(1, partialTick);
    }
}
