package net.got.event.entity.client.stag;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.stag.StagEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class StagRenderer
        extends MobRenderer<StagEntity, StagRenderState, StagModel> {

    private static final Identifier TEXTURE_RED =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_stag.png");
    private static final Identifier TEXTURE_WHITE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_stag_white.png");

    public StagRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new StagModel(ctx.bakeLayer(ModelLayers.GOT_STAG)),
                0.7F);
    }

    @Override
    public StagRenderState createRenderState() {
        return new StagRenderState();
    }

    @Override
    public void extractRenderState(StagEntity entity,
                                   StagRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater   = entity.isInWater();
        state.isSprinting = entity.isSprinting();
        state.isMoving    = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.variant     = entity.getVariant();
    }

    @Override
    public void submit(StagRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(StagRenderState state) {
        float t = state.ageInTicks;
        if (state.isSprinting || state.isInWater) {
            model.applyAnimation(StagAnimations.RUN, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(StagAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(StagAnimations.IDLE, t, 1.0F);
            model.applyAnimation(StagAnimations.TAIL_WAG, t, 1.0F);
        }
    }

    @Override
    public Identifier getTextureLocation(StagRenderState state) {
        return state.variant == 1 ? TEXTURE_WHITE : TEXTURE_RED;
    }

    @Override
    protected void scale(StagRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.2F, 1.2F, 1.2F);
        }
        super.scale(state, poseStack);
    }
}