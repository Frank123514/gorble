package net.got.event.entity.direwolf;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class DirewolfEntity extends TamableAnimal {

    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(DirewolfEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HOWLING =
            SynchedEntityData.defineId(DirewolfEntity.class, EntityDataSerializers.BOOLEAN);

    private int howlCooldown = 0;
    
    private int attackAnimTicks = 0;

    public DirewolfEntity(EntityType<? extends DirewolfEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ATTACKING, false);
        builder.define(DATA_HOWLING,   false);
    }

    private void setAttacking(boolean value) { this.entityData.set(DATA_ATTACKING, value); }
    private void setHowling(boolean value)   { this.entityData.set(DATA_HOWLING,   value); }

    public boolean isAttacking() { return this.entityData.get(DATA_ATTACKING); }
    public boolean isHowling()   { return this.entityData.get(DATA_HOWLING); }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.2, 8.0F, 2.0F));

        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.4, true) {
            @Override
            public void start() {
                super.start();
                setHowling(false);
            }
            @Override
            public void tick() {
                super.tick();
                setHowling(false);
            }
            @Override
            public void stop() {
                super.stop();
                setAttacking(false);
            }
            @Override
            public boolean canUse() {
                return !isInSittingPose() && super.canUse();
            }
        });

        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return !isTame() && super.canUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, false) {
            @Override
            public boolean canUse() {
                return !isTame() && super.canUse();
            }
        });
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isTame()) {
            if (itemstack.is(Items.BONE)) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!this.level().isClientSide()) {
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.setTarget(null);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        if (this.isOwnedBy(player)) {

            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal(4.0F);
                return InteractionResult.SUCCESS;
            }

            if (this.isFood(itemstack) && this.getAge() == 0 && this.canFallInLove()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            if (!this.level().isClientSide()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (attackAnimTicks > 0) {
                attackAnimTicks--;
                if (attackAnimTicks == 0) setAttacking(false);
            }
            if (!isAttacking() && !this.isInSittingPose()) {
                if (howlCooldown > 0) {
                    howlCooldown--;
                    if (howlCooldown == 0) setHowling(false);
                } else if (!isMoving() && this.random.nextInt(600) == 0) {
                    setHowling(true);
                    howlCooldown = 40;
                }
            }
            if (this.isInSittingPose()) {
                setHowling(false);
                howlCooldown = 0;
            }
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            attackAnimTicks = 15;
            setAttacking(true);
        }
        return result;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.BEEF) || stack.is(Items.PORKCHOP)
                || stack.is(Items.MUTTON) || stack.is(Items.ROTTEN_FLESH);
    }

    @Override
    public @Nullable DirewolfEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        DirewolfEntity baby = (DirewolfEntity) getType().create(level, EntitySpawnReason.BREEDING);
        if (baby != null && mate instanceof TamableAnimal tamedMate && tamedMate.isTame()) {
            baby.setOwnerReference(tamedMate.getOwnerReference());
            baby.setTame(true, true);
        }
        return baby;
    }

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<DirewolfEntity> type,
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
