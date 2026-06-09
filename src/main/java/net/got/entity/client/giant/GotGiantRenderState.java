package net.got.entity.client.giant;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Per-frame render snapshot for {@link net.got.entity.giant.GotGiantEntity}.
 *
 * <p>Populated each frame by {@link GotGiantRenderer#extractRenderState} from
 * the synced entity data; the model and animation code only ever reads this,
 * never the entity directly.
 */
public class GotGiantRenderState extends LivingEntityRenderState {

    /** True while the giant is executing a club-swing attack. */
    public boolean isAttacking;

    /** True during the initial enrage roar. */
    public boolean isRoaring;

    /** True once the giant has been hit at least once (drives combat animations). */
    public boolean isEnraged;

    /** True when the giant has non-trivial horizontal velocity. */
    public boolean isMoving;

    /** True when the giant is sprinting (charge mode). */
    public boolean isSprinting;

    /** True when the giant is dead or dying. */
    public boolean isDeadOrDying;

    /**
     * The animation clip chosen this frame by the renderer.
     * Read by {@link GotGiantModel#setupAnim} to drive keyframe playback.
     */
    public AnimationDefinition animationToPlay = GotGiantAnimations.IDLE;

    /**
     * Time value (in ticks) to pass to the animation system.
     * Looping clips use absolute {@code ageInTicks}; one-shot clips use
     * elapsed ticks since the clip started.
     */
    public float animationTime = 0F;
}