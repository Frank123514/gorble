package net.got.entity.client.npc.smallfolk;

import net.got.entity.client.model.GotModelLayers;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for all Smallfolk NPC tiers (Tier 1 smallfolk, Tier 2 levies,
 * Tier 3 skilled fighters).
 *
 * <p>Replaced the old GeckoLib-era placeholder.  Now uses the custom
 * {@link GotSmallfolkMaleModel} / {@link GotSmallfolkFemaleModel} baked
 * from the GOT geo.json files.  The correct model is chosen once in
 * the constructor based on gender is <em>not</em> known at construction
 * time, so we hold both models and swap in {@link #getModel} at render time.
 *
 * <p>Texture selection (male vs female, variant index) is handled by
 * {@link #getTextureLocation} using the arrays passed in at construction.
 *
 * @param <T> concrete SmallfolkEntity subtype
 */
public final class SmallfolkRenderer<T extends SmallfolkEntity>
        extends MobRenderer<T, GotSmallfolkMaleModel<T>> {

    // We use the male model as the "primary" generic parameter to satisfy
    // MobRenderer's type bound, and keep the female model separately.
    // getModel() is overridden to swap at runtime.
    private final GotSmallfolkMaleModel<T>   maleModel;
    private final GotSmallfolkFemaleModel<T> femaleModel;

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    @SuppressWarnings("unchecked")
    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             ResourceLocation[] maleTextures,
                             ResourceLocation[] femaleTextures) {
        super(ctx,
              new GotSmallfolkMaleModel<>(ctx.bakeLayer(GotModelLayers.SMALLFOLK_MALE)),
              0.5f);
        this.maleModel    = (GotSmallfolkMaleModel<T>) this.model;
        this.femaleModel  = new GotSmallfolkFemaleModel<>(ctx.bakeLayer(GotModelLayers.SMALLFOLK_FEMALE));
        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    // ── Model selection ───────────────────────────────────────────────────────

    /**
     * Returns the male or female model depending on the entity's gender.
     * MobRenderer calls this in its render path to obtain the model that
     * will be used for {@code setupAnim} and vertex emission this frame.
     */
    @Override
    public GotSmallfolkMaleModel<T> getModel() {
        // We always return the male model from the generic type perspective,
        // but override renderModel to actually use the female one when needed.
        return maleModel;
    }

    // ── Texture selection ─────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        boolean female = entity.getGender() == NpcGender.FEMALE;
        if (female) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            return femaleTextures[Math.abs(idx) % femaleTextures.length];
        }
        return maleTextures[entity.getVariant() % maleTextures.length];
    }

    // ── Gender-aware render dispatch ──────────────────────────────────────────

    @Override
    public void render(T entity, float entityYaw, float partialTick,
                       com.mojang.blaze3d.vertex.PoseStack poseStack,
                       net.minecraft.client.renderer.MultiBufferSource buffer,
                       int packedLight) {
        // Swap the active model so setupAnim fires on the right skeleton.
        boolean female = entity.getGender() == NpcGender.FEMALE;
        this.model = female ? (GotSmallfolkMaleModel<T>)(Object) femaleModel : maleModel;
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
