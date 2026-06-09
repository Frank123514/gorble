package net.got.entity.giant;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Giant — a towering humanoid creature that roams the frozen lands Beyond the Wall.
 *
 * <p>Giants are passive toward other freefolk and their mammoth companions but
 * intensely hostile to the Night's Watch and any outsider who attacks first.
 * They wield enormous clubs (simulated via a massive hit-box reach) and can
 * smash through wooden doors.
 *
 * <p>Animation states (matched by {@link net.got.entity.client.giant.GotGiantAnimations}):
 * <ul>
 *   <li>{@code idle}    — slow breathing sway, occasional head turn.</li>
 *   <li>{@code walk}    — heavy bipedal lumber, arm swing.</li>
 *   <li>{@code run}     — lumbering charge with forward lean.</li>
 *   <li>{@code attack}  — wide club-swing overhead smash.</li>
 *   <li>{@code roar}    — open-mouthed roar when first enraged.</li>
 *   <li>{@code death}   — topple-and-crash collapse.</li>
 * </ul>
 */
public class GotGiantEntity extends PathfinderMob {

    // ── Synced animation-state flags ─────────────────────────────────────────

    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(GotGiantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ROARING   =
            SynchedEntityData.defineId(GotGiantEntity.class, EntityDataSerializers.BOOLEAN);
    /** Set once on first hurt so the roar plays only the first time. */
    private static final EntityDataAccessor<Boolean> DATA_ENRAGED   =
            SynchedEntityData.defineId(GotGiantEntity.class, EntityDataSerializers.BOOLEAN);

    /** Ticks to hold the attack animation after a successful hit. */
    private int attackAnimTicks = 0;
    /** Ticks to hold the roar animation at the start of combat. */
    private int roarAnimTicks   = 0;

    private static final int ATTACK_HOLD_TICKS = 25;
    private static final int ROAR_HOLD_TICKS   = 50; // ~2.5 s

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotGiantEntity(EntityType<? extends GotGiantEntity> type, Level level) {
        super(type, level);
    }

    // ── Synced data ───────────────────────────────────────────────────────────

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ATTACKING, false);
        builder.define(DATA_ROARING,   false);
        builder.define(DATA_ENRAGED,   false);
    }

    public boolean isAttacking() { return this.entityData.get(DATA_ATTACKING); }
    public boolean isRoaring()   { return this.entityData.get(DATA_ROARING);   }
    public boolean isEnraged()   { return this.entityData.get(DATA_ENRAGED);   }

    private void setAttacking(boolean v) { this.entityData.set(DATA_ATTACKING, v); }
    private void setRoaring  (boolean v) { this.entityData.set(DATA_ROARING,   v); }
    private void setEnraged  (boolean v) { this.entityData.set(DATA_ENRAGED,   v); }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,          300.0)  // legendary toughness
                .add(Attributes.MOVEMENT_SPEED,       0.22)  // deceptively fast
                .add(Attributes.ATTACK_DAMAGE,        30.0)  // one-shots most armour
                .add(Attributes.FOLLOW_RANGE,         40.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,  1.0)  // completely unmovable
                .add(Attributes.ARMOR,                 8.0) // thick hide
                .add(Attributes.STEP_HEIGHT,            2.0); // can walk up 2-block steps naturally
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        // Priority 0 — can't drown
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Priority 1 — melee (huge reach to match visual)
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1, true) {
            @Override public void stop() {
                super.stop();
                if (attackAnimTicks <= 0) setAttacking(false);
            }
        });

        // Priority 2 — kick doors open
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));

        // Priority 3 — patrol
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85));

        // Priority 4 — idle look
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        // Target: retaliate on anything that hits us
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        // Target: players within range once enraged
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override public boolean canUse() { return isEnraged() && super.canUse(); }
        });
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            // Count down attack animation hold
            if (attackAnimTicks > 0 && --attackAnimTicks == 0) setAttacking(false);
            // Count down roar hold
            if (roarAnimTicks   > 0 && --roarAnimTicks   == 0) setRoaring(false);
        }
    }

    // ── Combat ────────────────────────────────────────────────────────────────

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hit = super.doHurtTarget(level, target);
        if (hit) {
            attackAnimTicks = ATTACK_HOLD_TICKS;
            setAttacking(true);
            // Huge knockback on hit
            if (target instanceof LivingEntity living) {
                living.knockback(3.0, this.getX() - target.getX(), this.getZ() - target.getZ());
            }
        }
        return hit;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean result = super.hurtServer(level, source, amount);
        // Trigger roar only once at the start of combat
        if (!isEnraged()) {
            setEnraged(true);
            roarAnimTicks = ROAR_HOLD_TICKS;
            setRoaring(true);
        }
        return result;
    }

    // ── Sounds ────────────────────────────────────────────────────────────────

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.PLAYER_BURP; // placeholder — replace with custom sound
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotGiantEntity> type,
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
        // Give the giant a procedural name for the nameplate
        this.setCustomName(Component.literal(GiantNameBank.randomName(this.random)));
        this.setCustomNameVisible(true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // ── Misc ──────────────────────────────────────────────────────────────────

    /** Giants are immune to fall damage — they just step over obstacles. */
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }
}