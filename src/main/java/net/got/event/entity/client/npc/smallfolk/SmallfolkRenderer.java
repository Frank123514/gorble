package net.got.event.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.GotModelLayers;
import net.got.event.entity.npc.NpcGender;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for all Smallfolk NPC tiers.
 *
 * <p>Extends {@link HumanoidMobRenderer} so vanilla behaviour is inherited
 * for free: arm pose extraction, attack swing, swim, crouch, item-use
 * animations, held-item rendering, and the built-in humanoid armor layer.
 *
 * <p>Gender-specific geometry (slim arms + breasts) is handled inside
 * {@link GotSmallfolkModel#setupAnim} via part visibility toggling, so
 * this renderer only has to forward the {@code isFemale} flag through the
 * render state — no model instance swapping required.
 *
 * @param <T> concrete SmallfolkEntity subtype
 */
public final class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    private static final float PLAYER_SCALE = 0.9375F;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        super(ctx,
                new GotSmallfolkModel(ctx.bakeLayer(GotModelLayers.SMALLFOLK)),
                PLAYER_SCALE);
        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Render state ──────────────────────────────────────────────────────

    @Override
    public SmallfolkRenderState createRenderState() {
        return new SmallfolkRenderState();
    }

    @Override
    public void extractRenderState(T entity, SmallfolkRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        boolean female = entity.getGender() == NpcGender.FEMALE;
        state.isFemale = female;
        state.variant  = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();

        if (female) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            state.texture = femaleTextures[Math.abs(idx) % femaleTextures.length];
        } else {
            state.texture = maleTextures[entity.getVariant() % maleTextures.length];
        }

        state.isTalking    = entity.isTalking();
        state.talkHeadYaw  = entity.getTalkHeadYaw();
        state.talkHeadPitch = entity.getTalkHeadPitch();
        state.talkGesture  = entity.getTalkGesture();
    }

    // ── Scale ─────────────────────────────────────────────────────────────

    @Override
    protected void scale(SmallfolkRenderState state, PoseStack poseStack) {
        poseStack.scale(PLAYER_SCALE, PLAYER_SCALE, PLAYER_SCALE);
    }

    // ── Texture ───────────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(SmallfolkRenderState state) {
        return state.texture;
    }
}