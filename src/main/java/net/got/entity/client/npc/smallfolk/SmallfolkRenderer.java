package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;

/**
 * Universal renderer for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <h3>Models</h3>
 * <ul>
 *   <li>Male NPCs use {@link GotSmallfolkModel} — standard-arm (Steve, 4 px) geometry.</li>
 *   <li>Female NPCs use {@link GotSmallfolkFemaleModel} — same arm width plus the
 *       breast sub-part for a feminine silhouette.</li>
 *   <li>Children use the male model regardless of gender.</li>
 * </ul>
 *
 * <h3>Render layers (added in constructor)</h3>
 * <ul>
 *   <li>{@link SmallfolkHeldItemLayer} — renders weapons / tools in the NPC's hands.</li>
 *   <li>{@link SmallfolkArmorLayer} — renders equipped armour by copying pose data
 *       into a vanilla {@code HumanoidModel} and calling {@code EquipmentRenderer}.</li>
 * </ul>
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

        // ── Render layers ─────────────────────────────────────────────────────
        // Order matters: armor is rendered first (behind the body), then held
        // items on top so the hand grip overlaps the armour cuff correctly.
        this.addLayer(new SmallfolkArmorLayer(this, ctx));
        this.addLayer(new SmallfolkHeldItemLayer(this,
                net.minecraft.client.Minecraft.getInstance().getItemRenderer()));
    }

    // ── Render ───────────────────────────────────────────────────────────────

    @Override
    public void render(SmallfolkRenderState state, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        // Children use the male model regardless of gender.
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

        // ── Identity ──────────────────────────────────────────────────────────
        state.isFemale          = entity.getGender() == NpcGender.FEMALE;
        state.variant           = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
        state.isChild           = entity.isBaby();

        // ── Riding ────────────────────────────────────────────────────────────
        // isPassenger() is true whenever the entity is mounted (horse, pig, etc.).
        state.isRiding = entity.isPassenger();

        // ── Talking ───────────────────────────────────────────────────────────
        state.isTalking = entity.isTalking();
        var talk = entity.getTalkAnimations();
        state.talkHeadYaw   = talk.getTalkHeadYaw();
        state.talkHeadPitch = talk.getTalkHeadPitch();
        state.talkGesture   = talk.getTalkGesture();

        // ── Combat ────────────────────────────────────────────────────────────
        var useItem = entity.getUseItem();
        state.isAimingBow = entity.isUsingItem()
                && (useItem.getItem() instanceof BowItem
                || useItem.getItem() instanceof CrossbowItem);
        state.isShieldBlocking = entity.isBlocking();

        // ── Held items ────────────────────────────────────────────────────────
        // These are consumed by SmallfolkHeldItemLayer.
        state.mainHandItem = entity.getItemInHand(InteractionHand.MAIN_HAND);
        state.offHandItem  = entity.getItemInHand(InteractionHand.OFF_HAND);

        // ── Armor items ───────────────────────────────────────────────────────
        // These are consumed by SmallfolkArmorLayer.
        state.headArmorItem     = entity.getItemBySlot(EquipmentSlot.HEAD);
        state.chestArmorItem    = entity.getItemBySlot(EquipmentSlot.CHEST);
        state.leggingsArmorItem = entity.getItemBySlot(EquipmentSlot.LEGS);
        state.bootsArmorItem    = entity.getItemBySlot(EquipmentSlot.FEET);
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