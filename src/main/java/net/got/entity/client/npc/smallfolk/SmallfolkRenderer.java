package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Universal renderer for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <p>Male NPCs use {@link GotSmallfolkModel} — a fully custom Blockbench model
 * with standard-width (Steve / 4 px) arms.
 *
 * <p>Female NPCs use {@link GotSmallfolkFemaleModel} — also a custom Blockbench
 * model, sharing the same arm width but adding the breast sub-part for a
 * feminine silhouette. No vanilla HumanoidModel / ModelLayers.PLAYER is used
 * anywhere in this pipeline.
 *
 * @param <T> any entity extending {@link SmallfolkEntity}
 */
public class SmallfolkRenderer<T extends SmallfolkEntity>
        extends MobRenderer<T, SmallfolkRenderState, EntityModel<SmallfolkRenderState>> {

    /** 15/16 — matches LOTR's PLAYER_SCALE constant. */
    private static final float NPC_SCALE = 0.9375f;
    /** Additional scale applied to children. */
    private static final float CHILD_SCALE = 0.55f;

    /** Custom male model — standard-arm geometry, no breast sub-part. */
    private final GotSmallfolkModel maleModel;

    /**
     * Custom female model — standard-arm geometry plus breast sub-part.
     * Registered via {@link GotSmallfolkFemaleModel#LAYER_LOCATION}.
     */
    private final GotSmallfolkFemaleModel femaleModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        // Pass the male model as the "default" stored by MobRenderer.
        super(ctx, new GotSmallfolkModel(ctx.bakeLayer(GotSmallfolkModel.LAYER_LOCATION)), 0.5f);
        this.maleModel   = (GotSmallfolkModel) this.model;
        this.femaleModel = new GotSmallfolkFemaleModel(
                ctx.bakeLayer(GotSmallfolkFemaleModel.LAYER_LOCATION));

        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Render ───────────────────────────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        // Children use a single shared model regardless of gender.
        if (state.isChild) {
            this.model = maleModel;
        } else {
            this.model = state.isFemale ? femaleModel : maleModel;
        }

        poseStack.pushPose();
        poseStack.scale(NPC_SCALE, NPC_SCALE, NPC_SCALE);
        if (state.isChild) {
            poseStack.scale(CHILD_SCALE, CHILD_SCALE, CHILD_SCALE);
        }
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
        super.extractRenderState(entity, state, partialTick);

        state.isFemale          = entity.getGender() == NpcGender.FEMALE;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.isTalking         = entity.isTalking();
        state.isChild           = entity.isBaby();

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