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

    private static final RawAnimation IDLE  = RawAnimation.begin().thenLoop("animation.got_horse.idle");
    private static final RawAnimation WALK  = RawAnimation.begin().thenLoop("animation.got_horse.walk");
    private static final RawAnimation RUN   = RawAnimation.begin().thenLoop("animation.got_horse.run");
    private static final RawAnimation REAR  = RawAnimation.begin().thenPlay("animation.got_horse.rear");
    private static final RawAnimation SWIM  = RawAnimation.begin().thenLoop("animation.got_horse.swim");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GotHorseEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    // ── GeckoLib ─────────────────────────────────────────────────────────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (this.isInWater()) {
                return state.setAndContinue(SWIM);
            }
            if (this.isStanding()) {
                return state.setAndContinue(REAR);
            }
            if (!state.isMoving()) {
                return state.setAndContinue(IDLE);
            }
            double speedSq = this.getDeltaMovement().horizontalDistanceSqr();
            if (speedSq > 0.08) {
                return state.setAndContinue(RUN);
            }
            return state.setAndContinue(WALK);
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
