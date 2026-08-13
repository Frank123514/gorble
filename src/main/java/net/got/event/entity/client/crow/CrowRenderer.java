package net.got.event.entity.client.crow;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.crow.CrowEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class CrowRenderer
        extends MobRenderer<CrowEntity, CrowRenderState, CrowModel> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_crow.png");

    public CrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new CrowModel(ctx.bakeLayer(ModelLayers.GOT_CROW)),
                0.25F);
    }

    @Override
    public CrowRenderState createRenderState() {
        return new CrowRenderState();
    }

    @Override
    public void extractRenderState(CrowEntity entity,
                                   CrowRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater = entity.isInWater();
        state.isMoving  = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.airTicks  = entity.airTicks;
        state.isFlying  = entity.airTicks >= 10 && !entity.isInWater();
    }

    @Override
    public void submit(CrowRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(CrowRenderState state) {
        float t = state.ageInTicks;
        if (state.isFlying) {
            model.applyAnimation(CrowAnimations.FLY, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(CrowAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(CrowAnimations.IDLE, t, 1.0F);
        }
    }

    @Override
    public Identifier getTextureLocation(CrowRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(CrowRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.7F, 0.7F, 0.7F);
        }
        super.scale(state, poseStack);
    }
}