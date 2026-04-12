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
 * <p>Mirrors LOTR's {@code LOTRBipedRenderer}:
 * <ul>
 *   <li>Scale: 93.75% (15/16) of player size.</li>
 *   <li>Model switching: slim arms for females ({@link SmallfolkEntity#useSmallArmsModel()}).</li>
 *   <li>Talk-animation data forwarded into {@link SmallfolkRenderState}.</li>
 *   <li>Gender-split texture array lookup.</li>
 * </ul>
 *
 * <p><b>Female model:</b> {@code slimModel} is baked from
 * {@link GotFemaleSmallfolkModel#LAYER}, which is registered in
 * {@code ClientSetup.registerLayerDefinitions} — an event that fires
 * <em>before</em> {@code RegisterRenderers}, so the layer is always
 * available when this constructor runs.
 *
 * <p><b>Model switch mechanism:</b> {@code this.model} (the protected field
 * on {@code LivingEntityRenderer}) is reassigned to the gender-appropriate
 * model in both {@link #extractRenderState} (before {@code super} runs its
 * internal {@code setupAnim}) and {@link #render} (before
 * {@code super.render} drives {@code renderModel}).  There is no
 * {@code getModel(state)} hook to override in NeoForge 1.21.4 —
 * {@code FeatureRendererContext#getModel()} simply returns {@code this.model},
 * so direct field assignment is the correct approach.
 *
 * @param <T> any entity extending {@link SmallfolkEntity}
 */
public class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    /** 15/16 — matches LOTR's PLAYER_SCALE constant. */
    private static final float NPC_SCALE = 0.9375f;

    /** Standard (Steve/4px-arm) model used for males and non-civilian NPCs. */
    private final HumanoidModel<SmallfolkRenderState> standardModel;
    /** Slim-armed (Alex/3px-arm) model used for females. */
    private final HumanoidModel<SmallfolkRenderState> slimModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        // Bake the standard (Steve/4px-arm) model and pass it to super as default.
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)), 0.5f);

        // Keep a named reference so we can swap back to it for males.
        this.standardModel = this.model;

        // Slim-armed model: GotFemaleSmallfolkModel.LAYER must be registered via
        // ClientSetup.registerLayerDefinitions before this constructor is called.
        // That event fires before RegisterRenderers, so the ordering is guaranteed.
        this.slimModel = new GotFemaleSmallfolkModel(
                ctx.bakeLayer(GotFemaleSmallfolkModel.LAYER));

        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Model switch + render ─────────────────────────────────────────────────
    //
    // this.model is the protected field on LivingEntityRenderer that the
    // vanilla pipeline reads for both setupAnim() and renderModel(). We swap
    // it before super.render() so both calls see the correct geometry.
    // There is no getModel(state) hook to override in NeoForge 1.21.4;
    // FeatureRendererContext#getModel() simply returns this.model directly.

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
        // Swap this.model BEFORE super.extractRenderState() so that any
        // internal setupAnim() the super invokes already sees the correct
        // slim vs. standard geometry for this entity.
        this.model = entity.useSmallArmsModel() ? slimModel : standardModel;

        super.extractRenderState(entity, state, partialTick);
        state.isFemale          = entity.getGender() == NpcGender.FEMALE;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.useSmallArms      = entity.useSmallArmsModel();
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
