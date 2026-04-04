package net.got.entity.animations;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

/**
 * Central registry of shared {@link RawAnimation} constants and reusable
 * {@link AnimationController} factory methods for all GoT NPC entities.
 *
 * <p>All animation names match the keys defined in
 * {@code assets/got/animations/entity/npc/humanoid.animation.json}. Every
 * levy and skilled-fighter entity uses this shared animation file, so any
 * update to keyframes here propagates to the entire NPC roster automatically.
 *
 * <h3>Usage in an entity's {@code registerControllers}</h3>
 * <pre>{@code
 * @Override
 * public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
 *     controllers.add(GotAnimationDefinitions.deathController(this));
 *     controllers.add(GotAnimationDefinitions.spawnController(this));
 *     controllers.add(GotAnimationDefinitions.attackController(this));
 *     controllers.add(GotAnimationDefinitions.movementController(this, () -> isAttacking));
 * }
 * }</pre>
 *
 * <p>Entities that need custom behaviour (e.g. a bowman shooting animation)
 * can still add their own controllers on top of these shared ones.
 */
public final class GotAnimationDefinitions {

    private GotAnimationDefinitions() {}

    // ── Shared RawAnimation constants ─────────────────────────────────────────
    // All keys live in assets/got/animations/entity/npc/humanoid.animation.json

    public static final RawAnimation IDLE   = RawAnimation.begin().thenLoop("misc.idle");
    public static final RawAnimation WALK   = RawAnimation.begin().thenLoop("misc.walk");
    public static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("misc.attack");
    public static final RawAnimation DIE    = RawAnimation.begin().thenPlay("misc.die");
    public static final RawAnimation SPAWN  = RawAnimation.begin().thenPlay("misc.spawn");

    // ── Controller factories ──────────────────────────────────────────────────

    /**
     * Plays the death animation while the entity is dying.
     * Priority: should be added first (lowest index).
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> deathController(T animatable) {
        return new AnimationController<>(animatable, "death", 0,
                state -> state.getAnimatable().isDeadOrDying()
                        ? state.setAndContinue(DIE)
                        : PlayState.STOP);
    }

    /**
     * Plays the spawn animation once for the first 50 ticks after the entity
     * is created. Resets automatically once the window has passed.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> spawnController(T animatable) {
        return new AnimationController<>(animatable, "spawn", 0, state -> {
            if (state.getAnimatable().tickCount < 50) {
                return state.setAndContinue(SPAWN);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    /**
     * Plays the attack animation while {@code swinging} is true.
     * Should be added before the movement controller so it takes priority.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> attackController(T animatable) {
        return new AnimationController<>(animatable, "attack", 0, state -> {
            if (state.getAnimatable().swinging) {
                return state.setAndContinue(ATTACK);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    /**
     * Plays walk or idle based on movement. Optionally layers an attack
     * animation on top via the {@code isAttacking} supplier.
     *
     * <p>This is the standard movement controller used by levies and
     * skilled fighters. Pass {@code () -> false} if attack blending is
     * handled by a separate {@link #attackController}.
     *
     * @param animatable  the entity
     * @param isAttacking supplier that returns {@code true} when the entity
     *                    is in a melee-attack swing (e.g. a field on the entity)
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> movementController(T animatable,
                                               java.util.function.BooleanSupplier isAttacking) {
        return new AnimationController<>(animatable, "movement", 3, state -> {
            if (isAttacking.getAsBoolean()) {
                return state.setAndContinue(ATTACK);
            }
            if (state.isMoving()) {
                return state.setAndContinue(WALK);
            }
            return state.setAndContinue(IDLE);
        });
    }

    /**
     * Convenience overload — movement controller with no separate attack flag.
     * Falls back to {@code swinging} for attack detection.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> movementController(T animatable) {
        return movementController(animatable, () -> animatable.swinging);
    }
}
