package net.got.entity.client.direwolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.direwolf.GotDirewolfEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for {@link GotDirewolfEntity}.
 *
 * <h3>Animation priority (highest first)</h3>
 * <ol>
 *   <li>Sitting                        → {@link GotDirewolfAnimations#SIT}</li>
 *   <li>Attacking                      → {@link GotDirewolfAnimations#ATTACK}</li>
 *   <li>Running / sprinting            → {@link GotDirewolfAnimations#RUN}</li>
 *   <li>Walking                        → {@link GotDirewolfAnimations#WALK}</li>
 *   <li>Idle (default)                 → {@link GotDirewolfAnimations#IDLE}</li>
 * </ol>
 */
public class GotDirewolfRenderer
        extends MobRenderer<GotDirewolfEntity, GotDirewolfRenderState, GotDirewolfModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/animals/got_direwolf.png");

    public GotDirewolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new GotDirewolfModel(ctx.bakeLayer(GotModelLayers.GOT_DIREWOLF)),
                0.8F);
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotDirewolfRenderState createRenderState() {
        return new GotDirewolfRenderState();
    }

    @Override
    public void extractRenderState(GotDirewolfEntity entity,
                                   GotDirewolfRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isSprinting = entity.isSprinting();
        state.isMoving    = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
        state.isAttacking = entity.isAttacking;
        state.isSitting   = entity.isSitting();
    }

    // ── Animation ─────────────────────────────────────────────────────────────

    @Override
    public void render(GotDirewolfRenderState state,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight) {
        selectAndApplyAnimation(state);
        super.render(state, poseStack, bufferSource, packedLight);
    }

    private void selectAndApplyAnimation(GotDirewolfRenderState state) {
        float t = state.ageInTicks;
        if (state.isSitting) {
            model.applyAnimation(GotDirewolfAnimations.SIT, t, 1.0F);
        } else if (state.isAttacking) {
            model.applyAnimation(GotDirewolfAnimations.ATTACK, t, 1.0F);
        } else if (state.isSprinting) {
            model.applyAnimation(GotDirewolfAnimations.RUN, t, 1.0F);
        } else if (state.isMoving) {
            model.applyAnimation(GotDirewolfAnimations.WALK, t, 1.0F);
        } else {
            model.applyAnimation(GotDirewolfAnimations.IDLE, t, 1.0F);
        }
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(GotDirewolfRenderState state) {
        return TEXTURE;
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotDirewolfRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.45F, 0.45F, 0.45F);
        } else {
            poseStack.scale(1.5F, 1.5F, 1.5F);   // direwolves are huge
        }
        super.scale(state, poseStack);
    }
}
