package net.got.entity.client.horse;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame snapshot of {@link net.got.entity.horse.GotHorseEntity} fields
 * needed by {@link GotHorseModel}, {@link GotHorseMarkingsLayer}, and
 * {@link GotHorseArmorLayer}.
 *
 * <p>Populated in {@link GotHorseRenderer#extractRenderState}.
 * Inherits {@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code isInWater}, and {@code isBaby} from
 * {@link LivingEntityRenderState}. {@code yHeadRot} and {@code xRot} are
 * declared here explicitly because they are not part of the base class in
 * 1.21.4.
 */
public class GotHorseRenderState extends LivingEntityRenderState {

    /** Head Y-rotation in degrees, copied from {@code entity.getYHeadRot()}. */
    public float yHeadRot;
    /** Head X-rotation (pitch) in degrees, copied from {@code entity.getXRot()}. */
    public float xRot;

    /** Index into the coat-texture array (0–5). */
    public int coatVariant;

    /** Index into the markings-texture array (0 = none). */
    public int markingsIndex;

    /** True when the horse is rearing up. */
    public boolean isStanding;

    /** True when the horse is eating from the ground. */
    public boolean isEating;

    /** Item in the BODY equipment slot (null = none). */
    public net.minecraft.world.item.Item bodyArmorItem;
}