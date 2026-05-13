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
        this.maleModel    = (GotSmallfolkMaleModel) this.model;
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
        super.extractRenderState(entity, state, partialTick);
        boolean female = entity.getGender() == NpcGender.FEMALE;
        state.isFemale          = female;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.isRiding          = entity.isPassenger();
        state.isSneaking        = entity.isCrouching();
        state.isTalking         = entity.isTalking();
        state.yHeadRot          = entity.getYHeadRot();
        state.xRot              = entity.getXRot();
        // Resolve and cache the texture for this frame
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

    // ── Gender-aware render dispatch ──────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        // Swap the active skeleton so setupAnim fires on the right bones
        this.model = state.isFemale ? femaleModel : maleModel;
        super.render(state, poseStack, buffer, packedLight);
    }
}