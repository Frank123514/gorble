package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Per-frame snapshot of Smallfolk NPC state needed by the model and renderer.
 *
 * <p>Populated in {@link SmallfolkGeoRenderer#extractRenderState}.
 * Inherits locomotion fields ({@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code yHeadRot}, {@code xRot}, {@code isInWater},
 * {@code isBaby}) from {@link LivingEntityRenderState}.
 */
public class SmallfolkRenderState extends LivingEntityRenderState {

    /** True when the entity is female (uses female model skeleton). */
    public boolean isFemale;

    /** Raw variant index from {@link net.got.entity.npc.smallfolk.SmallfolkEntity#getVariant()}. */
    public int variant;

    /** Number of male texture variants (used to split male/female index). */
    public int variantsPerGender;

    /** Resolved texture for this frame. */
    public ResourceLocation texture;

    /** True when entity is riding a mount. */
    public boolean isRiding;

    /** True when entity is sneaking. */
    public boolean isSneaking;

    /** True when entity is in a talking state. */
    public boolean isTalking;
}