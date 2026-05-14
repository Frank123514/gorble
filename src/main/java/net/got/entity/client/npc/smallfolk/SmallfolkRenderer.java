package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for all Smallfolk NPC tiers (Tier 1 smallfolk, Tier 2 levies,
 * Tier 3 skilled fighters).
 *
 * <p>Uses {@link GotSmallfolkMaleModel} / {@link GotSmallfolkFemaleModel}.
 * The correct skeleton is swapped in {@link #render} before the super call
 * so {@code setupAnim} always fires on the right bones.
 *
 * <p>{@code super.extractRenderState} (via {@link HumanoidMobRenderer}) fills
 * all vanilla humanoid animation fields automatically — arm poses, attack time,
 * swim amount, crouch, riding, item use, etc.
 *
 * @param <T> concrete SmallfolkEntity subtype
 */
public final class SmallfolkRenderer<T extends SmallfolkEntity>
        extends MobRenderer<T, SmallfolkRenderState, EntityModel<SmallfolkRenderState>> {

    private final GotSmallfolkMaleModel   maleModel;
    private final GotSmallfolkFemaleModel femaleModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        super(ctx,
                new GotSmallfolkMaleModel(ctx.bakeLayer(GotModelLayers.SMALLFOLK_MALE)),
                0.5f);
        this.maleModel   = (GotSmallfolkMaleModel) this.model;
        this.femaleModel = new GotSmallfolkFemaleModel(ctx.bakeLayer(GotModelLayers.SMALLFOLK_FEMALE));
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
        // super fills all HumanoidRenderState fields: arm poses, attackTime,
        // swimAmount, isCrouching, isPassenger, mainArm, xRot/yRot, etc.
        super.extractRenderState(entity, state, partialTick);

        boolean female = entity.getGender() == NpcGender.FEMALE;
        state.isFemale          = female;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.isTalking         = entity.isTalking();
        state.talkHeadYaw       = entity.getTalkHeadYaw();
        state.talkHeadPitch     = entity.getTalkHeadPitch();
        state.talkGesture       = entity.getTalkGesture();

        // Resolve texture
        if (female) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            state.texture = femaleTextures[Math.abs(idx) % femaleTextures.length];
        } else {
            state.texture = maleTextures[entity.getVariant() % maleTextures.length];
        }
    }

    // ── Texture selection ─────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(SmallfolkRenderState state) {
        return state.texture;
    }

    // ── Scale to player size ──────────────────────────────────────────────────

    /**
     * The vanilla player model geometry spans 32 units (2 blocks) from foot to
     * top of head in model space, and renders at a scale that maps this to 1.8
     * blocks in world space — effectively a scale factor of 0.9375 (= 1.8 / 1.92,
     * since MobRenderer applies a base 1/16 conversion).
     *
     * Smallfolk use identical geometry so we simply apply the same scale here,
     * overriding whatever MobRenderer would normally derive from the entity's
     * bounding box height, ensuring they always appear exactly player-sized
     * regardless of their hitbox dimensions.
     */
    private static final float PLAYER_SCALE = 0.9375F;

    @Override
    protected void scale(SmallfolkRenderState state, PoseStack poseStack) {
        poseStack.scale(PLAYER_SCALE, PLAYER_SCALE, PLAYER_SCALE);
    }

    // ── Gender-aware render dispatch ──────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        this.model = state.isFemale ? femaleModel : maleModel;
        super.render(state, poseStack, buffer, packedLight);
    }
}