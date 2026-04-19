package net.got.entity.animations;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

/**
 * Central registry of {@link RawAnimation} constants and reusable
 * {@link AnimationController} factories for all GOT mod entities.
 *
 * <p>Animation names correspond to those defined in
 * {@code assets/got/animations/smallfolk.animation.json}.
 */
public final class GotAnimationDefinitions {

    // ── Core locomotion ───────────────────────────────────────────────────────

    /** Standard walking / movement cycle. */
    public static final RawAnimation WALK =
            RawAnimation.begin().thenLoop("animation.smallfolk.walk");

    /** Idle standing pose. */
    public static final RawAnimation IDLE =
            RawAnimation.begin().thenLoop("animation.smallfolk.idle");

    /** Sneaking / crouching locomotion. */
    public static final RawAnimation SNEAK =
            RawAnimation.begin().thenLoop("animation.humanoid.sneaking");

    /** Swimming locomotion. */
    public static final RawAnimation SWIM =
            RawAnimation.begin().thenLoop("animation.humanoid.swimming");

    // ── Head tracking ─────────────────────────────────────────────────────────

    /** Default head look-at-target animation. */
    public static final RawAnimation LOOK_AT_TARGET =
            RawAnimation.begin().thenLoop("animation.humanoid.look_at_target.default");

    /** Head look while gliding. */
    public static final RawAnimation LOOK_GLIDING =
            RawAnimation.begin().thenLoop("animation.humanoid.look_at_target.gliding");

    /** Head look while swimming. */
    public static final RawAnimation LOOK_SWIMMING =
            RawAnimation.begin().thenLoop("animation.humanoid.look_at_target.swimming");

    // ── Combat ────────────────────────────────────────────────────────────────

    /** Melee attack rotation. */
    public static final RawAnimation ATTACK =
            RawAnimation.begin().thenLoop("animation.humanoid.attack.rotations");

    /** Bow-and-arrow draw pose. */
    public static final RawAnimation BOW =
            RawAnimation.begin().thenLoop("animation.humanoid.bow_and_arrow");

    /** Charging / wind-up animation. */
    public static final RawAnimation CHARGE =
            RawAnimation.begin().thenLoop("animation.humanoid.charging");

    /** AoE damage nearby mobs pose. */
    public static final RawAnimation DAMAGE_NEARBY =
            RawAnimation.begin().thenLoop("animation.humanoid.damage_nearby_mobs");

    /** Brandish spear overhead. */
    public static final RawAnimation BRANDISH_SPEAR =
            RawAnimation.begin().thenLoop("animation.humanoid.brandish_spear");

    /** Holding melee spear (humanoid). */
    public static final RawAnimation MELEE_SPEAR_HOLD =
            RawAnimation.begin().thenLoop("animation.humanoid.melee_spear_hold");

    /** Using melee spear (humanoid). */
    public static final RawAnimation MELEE_SPEAR_USE =
            RawAnimation.begin().thenLoop("animation.humanoid.melee_spear_use");

    /** Holding melee spear (zombie-style). */
    public static final RawAnimation ZOMBIE_MELEE_SPEAR_HOLD =
            RawAnimation.begin().thenLoop("animation.zombie.melee_spear_hold");

    /** Using melee spear (zombie-style). */
    public static final RawAnimation ZOMBIE_MELEE_SPEAR_USE =
            RawAnimation.begin().thenLoop("animation.zombie.melee_spear_use");

    // ── Item usage ────────────────────────────────────────────────────────────

    /** Generic item-holding offset. */
    public static final RawAnimation HOLDING =
            RawAnimation.begin().thenLoop("animation.humanoid.holding");

    /** Holding-spyglass pose. */
    public static final RawAnimation HOLDING_SPYGLASS =
            RawAnimation.begin().thenLoop("animation.humanoid.holding_spyglass");

    /** Tooting a goat horn. */
    public static final RawAnimation TOOTING_GOAT_HORN =
            RawAnimation.begin().thenLoop("animation.humanoid.tooting_goat_horn");

    /** Holding a brush in the off-hand. */
    public static final RawAnimation HOLDING_BRUSH =
            RawAnimation.begin().thenLoop("animation.humanoid.holding_brush");

    /** Actively brushing (archaeology). */
    public static final RawAnimation BRUSHING =
            RawAnimation.begin().thenLoop("animation.humanoid.brushing");

    /** Use-item progress animation. */
    public static final RawAnimation USE_ITEM_PROGRESS =
            RawAnimation.begin().thenLoop("animation.humanoid.use_item_progress");

    // ── Misc ──────────────────────────────────────────────────────────────────

    /** Body-bob (e.g. while riding). */
    public static final RawAnimation BOB =
            RawAnimation.begin().thenLoop("animation.humanoid.bob");

    /** Victory / celebration pose. */
    public static final RawAnimation CELEBRATE =
            RawAnimation.begin().thenLoop("animation.humanoid.celebrating");

    /** Big-head cosmetic effect. */
    public static final RawAnimation BIG_HEAD =
            RawAnimation.begin().thenLoop("animation.humanoid.big_head");

    // ── Riding ────────────────────────────────────────────────────────────────

    /** Body adjustment while riding a mount. */
    public static final RawAnimation RIDING_BODY =
            RawAnimation.begin().thenLoop("animation.humanoid.riding.body");

    /** Arm pose while riding. */
    public static final RawAnimation RIDING_ARMS =
            RawAnimation.begin().thenLoop("animation.humanoid.riding.arms");

    /** Leg pose while riding. */
    public static final RawAnimation RIDING_LEGS =
            RawAnimation.begin().thenLoop("animation.humanoid.riding.legs");

    // ── Pre-built AnimationController factories ───────────────────────────────

    /**
     * Creates a locomotion controller that plays {@link #WALK} when the entity
     * is moving and {@link #IDLE} when stationary.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> locomotionController(T animatable) {
        return new AnimationController<>(animatable, "Locomotion", 4, state -> {
            if (state.isMoving()) {
                return state.setAndContinue(WALK);
            }
            return state.setAndContinue(IDLE);
        });
    }

    /**
     * Creates a sneak controller that plays {@link #SNEAK} while the entity is
     * crouching, and stops otherwise.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> sneakController(T animatable) {
        return new AnimationController<>(animatable, "Sneak", 2, state -> {
            if (state.getAnimatable().isCrouching()) {
                return state.setAndContinue(SNEAK);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    /**
     * Creates an attack controller that plays {@link #ATTACK} while the entity
     * is swinging and stops once the swing is over.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> attackController(T animatable) {
        return new AnimationController<>(animatable, "Attack", 0, state -> {
            if (state.getAnimatable().swinging) {
                return state.setAndContinue(ATTACK);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        });
    }

    /**
     * Creates a head look-at controller that runs the default look-at-target
     * animation whenever the entity is alive.
     */
    public static <T extends LivingEntity & GeoAnimatable>
    AnimationController<T> headLookController(T animatable) {
        return new AnimationController<>(animatable, "HeadLook", 2, state ->
                state.setAndContinue(LOOK_AT_TARGET));
    }

    // ── Private constructor (utility class) ───────────────────────────────────

    private GotAnimationDefinitions() {}
}