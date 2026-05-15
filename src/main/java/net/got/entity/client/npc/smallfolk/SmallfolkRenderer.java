package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for all Smallfolk NPC tiers (Tier 1 smallfolk, Tier 2 levies,
 * Tier 3 skilled fighters).
 *
 * <p>Extends {@link HumanoidMobRenderer} so vanilla behaviour is inherited
 * for free: arm pose extraction, attack swing, swim, crouch, item-use
 * animations, held-item rendering, and the built-in humanoid armor layer.
 *
 * <p>The correct male/female skeleton is swapped in {@link #render} before
 * the super call so {@code setupAnim} fires on the right bones each frame.
 *
 * @param <T> concrete SmallfolkEntity subtype
 */
public final class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    private final HumanoidModel<SmallfolkRenderState> maleModel;
    private final HumanoidModel<SmallfolkRenderState> femaleModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    private static final float PLAYER_SCALE = 0.9375F;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        super(ctx,
                new GotSmallfolkMaleModel(ctx.bakeLayer(GotModelLayers.SMALLFOLK_MALE)),
                PLAYER_SCALE);
        this.maleModel    = this.model;
        this.femaleModel  = new GotSmallfolkFemaleModel(ctx.bakeLayer(GotModelLayers.SMALLFOLK_FEMALE));
        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public SmallfolkRenderState createRenderState() {
        return new SmallfolkRenderState();
    }

    @Override
    public void extractRenderState(T entity, SmallfolkRenderState state, float partialTick) {
        // Fills all HumanoidRenderState fields automatically:
        // arm poses, attackTime, attackArm, swimAmount, isCrouching,
        // isPassenger, mainArm, walkAnimation, xRot/yRot, etc.
        super.extractRenderState(entity, state, partialTick);

        // ── Gender / variant / texture ─────────────────────────────────────────
        boolean female = entity.getGender() == NpcGender.FEMALE;
        state.isFemale          = female;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        if (female) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            state.texture = femaleTextures[Math.abs(idx) % femaleTextures.length];
        } else {
            state.texture = maleTextures[entity.getVariant() % maleTextures.length];
        }

        // ── Talk animation ────────────────────────────────────────────────────
        state.isTalking     = entity.isTalking();
        state.talkHeadYaw   = entity.getTalkHeadYaw();
        state.talkHeadPitch = entity.getTalkHeadPitch();
        state.talkGesture   = entity.getTalkGesture();
    }

    // ── Scale to player size ──────────────────────────────────────────────────

    @Override
    protected void scale(SmallfolkRenderState state, PoseStack poseStack) {
        poseStack.scale(PLAYER_SCALE, PLAYER_SCALE, PLAYER_SCALE);
    }

    // ── Texture ───────────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(SmallfolkRenderState state) {
        return state.texture;
    }

    // ── Gender-aware render dispatch ──────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        this.model = state.isFemale ? femaleModel : maleModel;
        super.render(state, poseStack, buffer, packedLight);
    }
}