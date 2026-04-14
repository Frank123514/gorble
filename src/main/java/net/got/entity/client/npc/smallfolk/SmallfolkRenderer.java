package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Universal renderer for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <p>Uses the vanilla {@code ModelLayers.PLAYER} (Steve/standard-arm) model for
 * males and {@code ModelLayers.PLAYER_SLIM} (Alex/slim-arm) model for females —
 * exactly the same model layers the player renderer uses. No custom model
 * layer registration is required.
 *
 * <p>The correct model is selected once during {@link #extractRenderState}
 * by writing it into {@link SmallfolkRenderState#useSmallArms}, then
 * applied in {@link #render} before the super call drives the pipeline.
 *
 * @param <T> any entity extending {@link SmallfolkEntity}
 */
public class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    /** 15/16 — matches LOTR's PLAYER_SCALE constant. */
    private static final float NPC_SCALE = 0.9375f;

    /** Standard (Steve / 4 px arm) model — used for males. */
    private final HumanoidModel<SmallfolkRenderState> standardModel;
    /** Slim (Alex / 3 px arm) model — used for females. */
    private final HumanoidModel<SmallfolkRenderState> slimModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        // Start with the standard player model as the default.
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.standardModel = this.model;

        // Slim model uses the same vanilla layer as the player slim / Alex skin.
        this.slimModel = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_SLIM));

        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Render ───────────────────────────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        this.model = state.useSmallArms ? slimModel : standardModel;
        poseStack.pushPose();
        poseStack.scale(NPC_SCALE, NPC_SCALE, NPC_SCALE);
        super.render(state, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    // ── Render-state ──────────────────────────────────────────────────────────

    @Override
    public SmallfolkRenderState createRenderState() {
        return new SmallfolkRenderState();
    }

    @Override
    public void extractRenderState(T entity, SmallfolkRenderState state, float partialTick) {
        // Swap model BEFORE super call so setupAnim sees the right geometry.
        this.model = entity.useSmallArmsModel() ? slimModel : standardModel;

        super.extractRenderState(entity, state, partialTick);
        state.isFemale          = entity.getGender() == NpcGender.FEMALE;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.useSmallArms      = entity.useSmallArmsModel();
        // FIX: also set the vanilla HumanoidRenderState field so that
        // HumanoidModel.setupAnim() positions the slim arm bones at the
        // correct X offset (-5.5 instead of -5.0).  Without this, female
        // slim arms overlap the body by 0.5 px and look identical to the
        // standard male model.
        state.isUsingSmallArms  = state.useSmallArms;
        state.isTalking         = entity.isTalking();

        var talk = entity.getTalkAnimations();
        state.talkHeadYaw   = talk.getTalkHeadYaw();
        state.talkHeadPitch = talk.getTalkHeadPitch();
        state.talkGesture   = talk.getTalkGesture();
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(SmallfolkRenderState state) {
        if (state.isFemale) {
            int idx = state.variant - state.variantsPerGender;
            return femaleTextures[Math.abs(idx) % femaleTextures.length];
        }
        return maleTextures[state.variant % maleTextures.length];
    }
}
