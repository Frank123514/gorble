package net.got.event.entity.brownbear;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Brown Bear — a large, powerful bear of the forests of Westeros.
 *
 * <p>Passive unless provoked. When angered it charges and rears up to swipe.
 * Cubs follow their parents and grow into adults.
 *
 * <p>Animation states:
 * <ul>
 *   <li>{@code idle}    — gentle body sway, ear flicks, jaw yawn, tail wag.</li>
 *   <li>{@code walk}    — 4-beat diagonal walk with body roll.</li>
 *   <li>{@code run}     — bounding gallop with spine flex.</li>
 *   <li>{@code attack}  — rears up and swipes both front paws (one-shot).</li>
 *   <li>{@code stand}   — rears up on hind legs when alarmed (one-shot / hold).</li>
 * </ul>
 */
public class GotBrownBearEntity extends Animal {

    // ── Synced data ───────────────────────────────────────────────────────────

    private static final EntityDataAccessor<Boolean> DATA_ANGRY =
            SynchedEntityData.defineId(GotBrownBearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(GotBrownBearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STANDING =
            SynchedEntityData.defineId(GotBrownBearEntity.class, EntityDataSerializers.BOOLEAN);

    /** Ticks remaining to hold the attack animation after landing a hit. */
    private int attackAnimTicks = 0;
    /** Ticks remaining to hold the stand animation when alarmed without a direct target. */
    private int standAnimTicks  = 0;

    public GotBrownBearEntity(EntityType<? extends GotBrownBearEntity> type, Level level) {
        super(type, level);
    }

    // ── Synced data lifecycle ─────────────────────────────────────────────────

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANGRY,     false);
        builder.define(DATA_ATTACKING, false);
        builder.define(DATA_STANDING,  false);
    }

    private void setAngry(boolean v)     { this.entityData.set(DATA_ANGRY,     v); }
    private void setAttacking(boolean v) { this.entityData.set(DATA_ATTACKING, v); }
    private void setStanding(boolean v)  { this.entityData.set(DATA_STANDING,  v); }

    public boolean isAngry()     { return this.entityData.get(DATA_ANGRY);     }
    public boolean isAttacking() { return this.entityData.get(DATA_ATTACKING); }
    public boolean isStanding()  { return this.entityData.get(DATA_STANDING);  }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH,          40.0)
                .add(Attributes.MOVEMENT_SPEED,       0.25)
                .add(Attributes.ATTACK_DAMAGE,         8.0)
                .add(Attributes.FOLLOW_RANGE,         16.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,  0.5);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, true) {
            @Override
            public void stop() {
                super.stop();
                // Attack-anim hold countdown continues in tick(); don't clear early.
            }
        });
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return isAngry() && super.canUse();
            }
        });
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            // Attack animation hold
            if (attackAnimTicks > 0) {
                attackAnimTicks--;
                if (attackAnimTicks == 0) setAttacking(false);
            }

            // Stand animation hold — bear stands briefly when it first turns angry
            if (standAnimTicks > 0) {
                standAnimTicks--;
                setStanding(true);
                if (standAnimTicks == 0) setStanding(false);
            }
        }
    }

    // ── Combat ────────────────────────────────────────────────────────────────

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            // Hold attack anim for 1.2 s (matches clip length)
            attackAnimTicks = (int) (1.2F * 20F);
            setAttacking(true);
            setStanding(false);
            standAnimTicks = 0;
        }
        return result;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean result = super.hurtServer(level, source, amount);
        setAngry(true);
        // Bear rears up briefly when first hurt
        if (standAnimTicks == 0) {
            standAnimTicks = (int) (1.6667F * 20F);
        }
        return result;
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SALMON) || stack.is(Items.COD) || stack.is(Items.HONEY_BOTTLE);
    }

    @Override
    public @Nullable GotBrownBearEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotBrownBearEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotBrownBearEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return net.got.event.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
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