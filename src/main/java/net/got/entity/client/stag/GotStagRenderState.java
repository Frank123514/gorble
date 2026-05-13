package net.got.entity.client.stag;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame snapshot of {@link net.got.entity.stag.GotStagEntity} fields
 * needed by {@link GotStagModel}.
 *
 * <p>Inherits {@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code yHeadRot}, {@code xRot}, {@code isInWater},
 * and {@code isBaby} from {@link LivingEntityRenderState}.
 */
public class GotStagRenderState extends LivingEntityRenderState {

    /** True when the stag is rearing on its hind legs. */
    public boolean isStanding;

    /** True when the stag has its head lowered to eat. */
    public boolean isEating;
}