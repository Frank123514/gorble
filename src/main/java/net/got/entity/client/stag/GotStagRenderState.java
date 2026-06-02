package net.got.entity.client.stag;

import net.minecraft.client.renderer.entity.state.HorseRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.stag.GotStagEntity}.
 *
 * <p>Extends vanilla {@link HorseRenderState} so the inherited
 * {@link net.minecraft.client.model.HorseModel#setupAnim} receives every
 * field it expects ({@code walkAnimationPos}, {@code walkAnimationSpeed},
 * {@code ageInTicks}, {@code isInWater}, {@code isBaby}, {@code isStanding},
 * {@code isEating}, {@code isSaddled}, etc.) without us having to declare any
 * of them.  No new fields are needed for the stag.
 */
public class GotStagRenderState extends HorseRenderState {
    // intentionally empty — all state lives in HorseRenderState
}