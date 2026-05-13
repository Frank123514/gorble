package net.got.entity.client.stag;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame snapshot of {@link net.got.entity.stag.GotStagEntity} fields
 * needed by {@link GotStagModel}.
 *
 * <p>Inherits {@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code isInWater}, and {@code isBaby} from
 * {@link LivingEntityRenderState}. {@code yHeadRot} and {@code xRot} are
 * declared here explicitly because they are not part of the base class in
 * 1.21.4.
 */
public class GotStagRenderState extends LivingEntityRenderState {

    /** Head Y-rotation in degrees, copied from {@code entity.getYHeadRot()}. */
    public float yHeadRot;
    /** Head X-rotation (pitch) in degrees, copied from {@code entity.getXRot()}. */
    public float xRot;

    /** True when the stag is rearing on its hind legs. */
    public boolean isStanding;

    /** True when the stag has its head lowered to eat. */
    public boolean isEating;
}