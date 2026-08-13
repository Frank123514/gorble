package net.got.event.entity.mammoth;

import net.got.event.entity.giant.GiantEntity;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MammothEntity extends Animal {

    public static final double MOUNT_SEEK_RADIUS = 20.0;

    private static final float RIDER_Y_OFFSET = 6.0F;

    private static final EntityDataAccessor<Boolean> DATA_ANGRY =
            SynchedEntityData.defineId(MammothEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(MammothEntity.class, EntityDataSerializers.BOOLEAN);

    private int attackAnimTicks = 0;

    public MammothEntity(EntityType<? extends MammothEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANGRY,     false);
        builder.define(DATA_ATTACKING, false);
    }

    private void setAngry(boolean value)    { this.entityData.set(DATA_ANGRY,     value); }
    private void setAttacking(boolean value){ this.entityData.set(DATA_ATTACKING, value); }

    public boolean isAngry()     { return this.entityData.get(DATA_ANGRY); }
    public boolean isAttacking() { return this.entityData.get(DATA_ATTACKING); }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH,          150.0)
                .add(Attributes.MOVEMENT_SPEED,       0.18)
                .add(Attributes.ATTACK_DAMAGE,        20.0)
                .add(Attributes.FOLLOW_RANGE,         28.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,  1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            public void start() {
                super.start();
            }
            @Override
            public void tick() {
                super.tick();
            }
            @Override
            public void stop() {
                super.stop();
                if (attackAnimTicks <= 0) {
                    setAttacking(false);
                }
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
                if (attackAnimTicks == 0) {
                    setAttacking(false);
                }
            }
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            attackAnimTicks = 20;
            setAttacking(true);
        }
        return result;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        super.hurtServer(level, source, amount);
        setAngry(true);
        return false;
    }

    public boolean hasGiantRider() {
        return this.getFirstPassenger() instanceof GiantEntity;
    }

    @Override
    public Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        
        return new Vec3(0.0, RIDER_Y_OFFSET / 2.2F, -0.3);
    }

    @Override
    public boolean canBeRiddenUnderFluidType(net.neoforged.neoforge.fluids.FluidType type, Entity rider) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.HAY_BLOCK);
    }

    @Override
    public @Nullable MammothEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (MammothEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<MammothEntity> type,
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