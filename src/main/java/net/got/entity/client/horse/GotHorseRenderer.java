package net.got.entity.client.horse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.horse.GotHorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

/**
 * Renderer for {@link GotHorseEntity}.
 *
 * <p>Uses the custom {@link GotHorseModel} (converted from
 * {@code assets/got/geo/got_horse.geo.json}).  Replaced the old vanilla
 * {@code HorseModel} placeholder.
 *
 * <p>Rendering stack (back to front):
 * <ol>
 *   <li>Base coat — resolved per-entity by {@link #getTextureLocation}.</li>
 *   <li>Markings overlay — {@link GotHorseMarkingsLayer}.</li>
 *   <li>Horse-armour overlay — {@link GotHorseArmorLayer}.</li>
 * </ol>
 */
public class GotHorseRenderer
        extends MobRenderer<GotHorseEntity, GotHorseRenderState, GotHorseModel> {

    /** Indexed by {@link GotHorseEntity#getCoatVariant()} (0–5). */
    private static final ResourceLocation[] COAT_TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_black.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_brown.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_chestnut.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_creamy.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_darkbrown.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/horse/horse_gray.png"),
    };

    public GotHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GotHorseModel(ctx.bakeLayer(GotModelLayers.GOT_HORSE)), 0.9f);
        this.addLayer(new GotHorseMarkingsLayer(this, ctx.getModelSet()));
        this.addLayer(new GotHorseArmorLayer(this, ctx.getModelSet()));
    }

    // ── Render state ──────────────────────────────────────────────────────────

    @Override
    public GotHorseRenderState createRenderState() {
        return new GotHorseRenderState();
    }

    @Override
    public void extractRenderState(GotHorseEntity entity, GotHorseRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.coatVariant    = entity.getCoatVariant();
        state.markingsIndex  = entity.getMarkingsIndex();
        state.isStanding     = entity.isStanding();
        state.isEating       = entity.isEating();
        state.bodyArmorItem  = entity.getItemBySlot(EquipmentSlot.BODY).getItem();
        state.yHeadRot       = entity.getYHeadRot();
        state.xRot           = entity.getXRot();
    }

    // ── Texture selection ─────────────────────────────────────────────────────

    @Override
    public ResourceLocation getTextureLocation(GotHorseRenderState state) {
        int id = state.coatVariant;
        if (id < 0 || id >= COAT_TEXTURES.length) id = 0;
        return COAT_TEXTURES[id];
    }

    // ── Scale ─────────────────────────────────────────────────────────────────

    @Override
    protected void scale(GotHorseRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.65f, 0.65f, 0.65f);
            this.shadowRadius = 0.585f;
        } else {
            this.shadowRadius = 0.9f;
        }
    }
}