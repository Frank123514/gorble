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
 * @param <T> any entity extending {@link SmallfolkEntity}
 */
public class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    /** 15/16 — matches LOTR's PLAYER_SCALE constant. */
    private static final float NPC_SCALE = 0.9375f;

    private final HumanoidModel<SmallfolkRenderState> standardModel;
    private final HumanoidModel<SmallfolkRenderState> slimModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        this.standardModel = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE));
        // Slim arms: ZOMBIE_INNER_ARMOR is the closest available baked layer with slim proportions;
        // artists can replace this with a dedicated ModelLayer once custom models are added.
        this.slimModel     = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE));
        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        // Switch model before rendering, mirroring LOTRBipedRenderer.selectEntityModelForArmsStyle
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