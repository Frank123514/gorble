package net.got.entity.client.horse;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame snapshot of {@link net.got.entity.horse.GotHorseEntity} fields
 * needed by {@link GotHorseModel}, {@link GotHorseMarkingsLayer}, and
 * {@link GotHorseArmorLayer}.
 *
 * <p>Populated in {@link GotHorseRenderer#extractRenderState}.
 * Inherits from {@link LivingEntityRenderState}, which already carries
 * {@code walkAnimationPos}, {@code walkAnimationSpeed}, {@code ageInTicks},
 * {@code yHeadRot}, {@code xRot}, {@code isInWater}, and {@code isBaby}.
 */
public class GotHorseRenderState extends LivingEntityRenderState {

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