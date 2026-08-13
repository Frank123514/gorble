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

public class BrownBearEntity extends Animal {

    private static final EntityDataAccessor<Boolean> DATA_ANGRY =
            SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_STANDING =
            SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.BOOLEAN);

    private int attackAnimTicks = 0;
    
    private int standAnimTicks  = 0;

    public BrownBearEntity(EntityType<? extends BrownBearEntity> type, Level level) {
        super(type, level);
    }

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

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH,          40.0)
                .add(Attributes.MOVEMENT_SPEED,       0.25)
                .add(Attributes.ATTACK_DAMAGE,         8.0)
                .add(Attributes.FOLLOW_RANGE,         16.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,  0.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, true) {
            @Override
            public void stop() {
                super.stop();
                
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

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            
            if (attackAnimTicks > 0) {
                attackAnimTicks--;
                if (attackAnimTicks == 0) setAttacking(false);
            }

            if (standAnimTicks > 0) {
                standAnimTicks--;
                setStanding(true);
                if (standAnimTicks == 0) setStanding(false);
            }
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            
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
        
        if (standAnimTicks == 0) {
            standAnimTicks = (int) (1.6667F * 20F);
        }
        return result;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SALMON) || stack.is(Items.COD) || stack.is(Items.HONEY_BOTTLE);
    }

    @Override
    public @Nullable BrownBearEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (BrownBearEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<BrownBearEntity> type,
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