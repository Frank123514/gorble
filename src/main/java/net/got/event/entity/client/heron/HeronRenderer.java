package net.got.event.entity.client.heron;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.heron.HeronEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class HeronRenderer
        extends MobRenderer<HeronEntity, HeronRenderState, HeronModel> {

    private static final Identifier TEXTURE_GREY =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron.png");
    private static final Identifier TEXTURE_BLUE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_blue.png");
    private static final Identifier TEXTURE_WHITE =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_white.png");
    private static final Identifier TEXTURE_NIGHT =
            Identifier.fromNamespaceAndPath("got", "textures/entity/animals/got_heron_night.png");

    public HeronRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new HeronModel(ctx.bakeLayer(ModelLayers.GOT_HERON)),
                0.4F);
    }

    @Override
    public HeronRenderState createRenderState() {
        return new HeronRenderState();
    }

    @Override
    public void extractRenderState(HeronEntity entity,
                                   HeronRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isInWater = entity.isInWater();
        state.isMoving  = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.airTicks  = entity.airTicks;
        state.isFlying  = entity.airTicks >= 15 && !entity.isInWater();
        state.variant   = entity.getVariant();
    }

    @Override
    public void submit(HeronRenderState state,
                       PoseStack poseStack,
                       SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        selectAndApplyAnimation(state);
        super.submit(state, poseStack, collector, cameraState);
    }

    private void selectAndApplyAnimation(HeronRenderState state) {
        float t = state.ageInTicks;
        if (state.isFlying) {
            model.applyAnimation(HeronAnimations.FLY, t, 1.0F);
        } else if (state.isInWater && state.isMoving) {
            model.applyAnimation(HeronAnimations.WADE, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(HeronAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(HeronAnimations.IDLE, t, 1.0F);
        }
    }

    @Override
    public Identifier getTextureLocation(HeronRenderState state) {
        return switch (state.variant) {
            case 1  -> TEXTURE_BLUE;
            case 2  -> TEXTURE_WHITE;
            case 3  -> TEXTURE_NIGHT;
            default -> TEXTURE_GREY;
        };
    }

    @Override
    protected void scale(HeronRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.9F, 0.9F, 0.9F);
        }
        super.scale(state, poseStack);
    }
}