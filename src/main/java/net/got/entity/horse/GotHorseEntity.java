package net.got.entity.horse;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * GOT custom horse entity — a warhorse for the Game of Thrones mod.
 *
 * <p>Extends vanilla {@link Horse} to inherit all vanilla horse behaviour
 * (taming, inventory, saddling, breeding, riding, jump strength, health).
 * Rendered via GeckoLib using a custom 1.12.2-style geo model.
 *
 * <p>Animation states:
 * <ul>
 *   <li>{@code idle}  — subtle breathing while standing still.</li>
 *   <li>{@code walk}  — 4-beat walk gait.</li>
 *   <li>{@code run}   — faster diagonal canter/gallop.</li>
 *   <li>{@code rear}  — rearing animation when {@link #isStanding()}.</li>
 *   <li>{@code swim}  — paddling motion while in water.</li>
 * </ul>
 */
public class GotHorseEntity extends Horse implements GeoEntity {

    private static final RawAnimation IDLE     = RawAnimation.begin().thenLoop("animation.got_horse.idle");
    private static final RawAnimation WALK     = RawAnimation.begin().thenLoop("animation.got_horse.walk");
    private static final RawAnimation RUN      = RawAnimation.begin().thenLoop("animation.got_horse.run");
    private static final RawAnimation REAR     = RawAnimation.begin().thenPlay("animation.got_horse.rear");
    private static final RawAnimation SWIM     = RawAnimation.begin().thenLoop("animation.got_horse.swim");
    /** Head dips to graze with jaw chew cycles. Triggered while {@link #isEating()}. */
    private static final RawAnimation EAT      = RawAnimation.begin().thenLoop("animation.got_horse.eat");
    /** Energetic side-to-side tail wag. Plays on a separate controller when tamed and idle. */
    private static final RawAnimation TAIL_WAG = RawAnimation.begin().thenLoop("animation.got_horse.tail_wag");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GotHorseEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    // ── GeckoLib ─────────────────────────────────────────────────────────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Primary movement controller — eating takes precedence over idle/walk/run.
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (this.isInWater())  return state.setAndContinue(SWIM);
            if (this.isStanding()) return state.setAndContinue(REAR);
            if (this.isEating())   return state.setAndContinue(EAT);
            if (!state.isMoving()) return state.setAndContinue(IDLE);
            double speedSq = this.getDeltaMovement().horizontalDistanceSqr();
            if (speedSq > 0.08)    return state.setAndContinue(RUN);
            return state.setAndContinue(WALK);
        }));

        // Tail controller — plays the energetic tail-wag animation independently
        // whenever the horse is tamed and not busy with another action.
        // Registered second so its tail-bone output takes priority over the
        // movement controller's tail pose when both are active.
        controllers.add(new AnimationController<>(this, "tail", 2, state -> {
            if (this.isTamed()
                    && !this.isInWater()
                    && !this.isStanding()
                    && !this.isEating()
                    && !state.isMoving()) {
                return state.setAndContinue(TAIL_WAG);
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotHorseEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData groupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }
}