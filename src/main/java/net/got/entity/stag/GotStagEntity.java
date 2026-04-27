package net.got.entity.stag;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
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
 * GOT Stag — a great red deer stag of the Westerosi forests.
 *
 * <p>Uses the custom stag geo model with antlers and a deer-like silhouette.
 * Extends vanilla {@link Horse} to reuse all horse behaviour (taming,
 * saddling, riding, breeding, health) while being rendered via GeckoLib
 * with its own model and the horse animation set remapped to stag bone names.
 *
 * <p>Animation states (same logic as the warhorse, using stag-prefixed clips):
 * <ul>
 *   <li>{@code idle}    — subtle breathing bob.</li>
 *   <li>{@code walk}    — 4-beat walk gait.</li>
 *   <li>{@code run}     — bounding gallop.</li>
 *   <li>{@code rear}    — rearing when {@link #isStanding()}.</li>
 *   <li>{@code swim}    — paddling motion in water.</li>
 *   <li>{@code eat}     — grazing head-dip.</li>
 *   <li>{@code tail_wag}— tail flick when tamed and idle.</li>
 * </ul>
 */
public class GotStagEntity extends Horse implements GeoEntity {

    private static final RawAnimation IDLE     = RawAnimation.begin().thenLoop("animation.got_stag.idle");
    private static final RawAnimation WALK     = RawAnimation.begin().thenLoop("animation.got_stag.walk");
    private static final RawAnimation RUN      = RawAnimation.begin().thenLoop("animation.got_stag.run");
    private static final RawAnimation REAR     = RawAnimation.begin().thenPlay("animation.got_stag.rear");
    private static final RawAnimation SWIM     = RawAnimation.begin().thenLoop("animation.got_stag.swim");
    private static final RawAnimation EAT      = RawAnimation.begin().thenLoop("animation.got_stag.eat");
    private static final RawAnimation TAIL_WAG = RawAnimation.begin().thenLoop("animation.got_stag.tail_wag");

    // ── Attributes ────────────────────────────────────────────────────────────

    /**
     * Called by {@link net.got.entity.GotEntityEvents} during
     * {@code EntityAttributeCreationEvent} to register the stag's attribute set.
     * Stags are fast and nimble but not quite as tough as the warhorse.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return AbstractHorse.createBaseHorseAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.JUMP_STRENGTH, 0.65);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GotStagEntity(EntityType<? extends Horse> type, Level level) {
        super(type, level);
    }

    // ── GeckoLib ─────────────────────────────────────────────────────────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, state -> {
            if (this.isInWater())  return state.setAndContinue(SWIM);
            if (this.isStanding()) return state.setAndContinue(REAR);
            if (this.isEating())   return state.setAndContinue(EAT);
            if (!state.isMoving()) return state.setAndContinue(IDLE);
            double speedSq = this.getDeltaMovement().horizontalDistanceSqr();
            if (speedSq > 0.08)    return state.setAndContinue(RUN);
            return state.setAndContinue(WALK);
        }));

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
    public static boolean checkSpawnRules(EntityType<GotStagEntity> type,
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
                                                  @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}