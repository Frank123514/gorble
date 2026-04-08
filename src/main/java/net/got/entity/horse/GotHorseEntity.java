package net.got.entity.horse;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * GOT custom horse entity — a detailed animated warhorse for the Game of Thrones mod.
 *
 * <p>Extends vanilla {@link Horse} to inherit all vanilla horse behaviour
 * (taming, inventory, saddling, breeding, riding, jump strength, health) while
 * replacing the renderer entirely with the GeckoLib-powered
 * {@code got_horse.geo.json} model and its full set of procedural animations.
 *
 * <h3>Animation states</h3>
 * <pre>
 *   horse.idle   — gentle head-bob, ear twitches, tail sway
 *   horse.walk   — 4-beat walk with proper leg kinematics
 *   horse.trot   — 2-beat diagonal trot (medium speed)
 *   horse.gallop — full bounding gallop with body pitch (high speed)
 *   horse.eat    — head lowered to eat with jaw open/close
 *   horse.die    — dramatic toppling death
 * </pre>
 *
 * <h3>Usage in SkilledFighterEntity</h3>
 * {@code SkilledFighterEntity.trySpawnMounted()} uses this entity type
 * via {@code GotModEntities.GOT_HORSE} so mounted soldiers ride this
 * custom model rather than a vanilla horse.
 */
public class GotHorseEntity extends Horse implements GeoEntity {

    // ── Animation constants ────────────────────────────────────────────────────

    private static final RawAnimation ANIM_IDLE   = RawAnimation.begin().thenLoop("horse.idle");
    private static final RawAnimation ANIM_WALK   = RawAnimation.begin().thenLoop("horse.walk");
    private static final RawAnimation ANIM_TROT   = RawAnimation.begin().thenLoop("horse.trot");
    private static final RawAnimation ANIM_GALLOP = RawAnimation.begin().thenLoop("horse.gallop");
    private static final RawAnimation ANIM_EAT    = RawAnimation.begin().thenLoop("horse.eat");
    private static final RawAnimation ANIM_YAWN   = RawAnimation.begin().thenPlay("horse.yawn").thenLoop("horse.idle");
    private static final RawAnimation ANIM_DIE    = RawAnimation.begin().thenPlay("horse.die");

    // ── Yawn idle timer ───────────────────────────────────────────────────────
    /** Counts up while the horse is idle; triggers a yawn every ~12–18 seconds. */
    private int yawnTimer = 0;
    private int yawnCooldown = 0;

    /**
     * Horizontal-speed-squared thresholds used to pick the gait animation.
     * These are tuned for vanilla horse movement speeds (0.2 – 0.34 m/t).
     *
     * <ul>
     *   <li>{@code > GALLOP_THRESHOLD} → gallop</li>
     *   <li>{@code > TROT_THRESHOLD}   → trot</li>
     *   <li>Moving at all             → walk</li>
     *   <li>Stationary                → idle</li>
     * </ul>
     */
    private static final double GALLOP_THRESHOLD = 0.07;
    private static final double TROT_THRESHOLD   = 0.02;

    // ── GeckoLib cache ─────────────────────────────────────────────────────────

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    // ── Constructor ────────────────────────────────────────────────────────────

    public GotHorseEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    // ── GeckoLib — GeoEntity ───────────────────────────────────────────────────

    /**
     * Registers two animation controllers:
     * <ol>
     *   <li><b>death</b> — highest priority; plays the death animation once
     *       whenever the horse is dying. A hard transition (0 tick blend)
     *       ensures it snaps in immediately regardless of the gait controller.</li>
     *   <li><b>movement</b> — drives gait selection: dead → ignore (handled
     *       above), eating → eat, fast → gallop, medium → trot, slow → walk,
     *       still → idle. Uses a 5-tick blend for smooth gait transitions.</li>
     * </ol>
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        // ── Death controller (top priority, hard cut) ──────────────────────────
        controllers.add(new AnimationController<>(this, "death", 0, state -> {
            if (state.getAnimatable().isDeadOrDying()) {
                return state.setAndContinue(ANIM_DIE);
            }
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        }));

        // ── Gait / posture controller ──────────────────────────────────────────
        controllers.add(new AnimationController<>(this, "movement", 5, state -> {
            GotHorseEntity horse = state.getAnimatable();

            // Eat pose takes priority over movement when grazing
            if (horse.isEating()) {
                return state.setAndContinue(ANIM_EAT);
            }

            // Compute horizontal speed squared for gait selection.
            // getDeltaMovement() is available on both sides; for the server side
            // this is the authoritative velocity, for the client it is the last
            // received interpolated position delta — both are good enough for
            // animation switching.
            double speedSq = horse.getDeltaMovement().horizontalDistanceSqr();

            if (speedSq > GALLOP_THRESHOLD) {
                yawnTimer = 0;
                return state.setAndContinue(ANIM_GALLOP);
            }
            if (speedSq > TROT_THRESHOLD) {
                yawnTimer = 0;
                return state.setAndContinue(ANIM_TROT);
            }
            if (state.isMoving()) {
                yawnTimer = 0;
                return state.setAndContinue(ANIM_WALK);
            }

            // Occasionally play a yawn when standing still
            if (yawnCooldown > 0) {
                yawnCooldown--;
            }
            yawnTimer++;
            // Yawn roughly every 12–18 seconds (240–360 ticks), once cooldown is up
            if (yawnTimer > 240 + horse.level().random.nextInt(120) && yawnCooldown == 0) {
                yawnTimer = 0;
                yawnCooldown = 400; // wait ~20s before next yawn
                return state.setAndContinue(ANIM_YAWN);
            }
            return state.setAndContinue(ANIM_IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    // ── Spawn rules ────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotHorseEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        // Cast to raw type to bypass generic type checking
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  net.minecraft.world.DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, groupData);
        // Auto-tame horses spawned by the mod's fighter system
        // (SkilledFighterEntity calls setTamed(true) separately after this).
        return result;
    }
}