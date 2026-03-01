package net.got.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NorthWarriorEntity extends PathfinderMob implements NeutralMob {

    private static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    private static final String NBT_VARIANT = "GotVariant";

    private int variant = 0;
    private int angerTime;
    @Nullable private UUID angerTarget;

    public NorthWarriorEntity(EntityType<? extends NorthWarriorEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,           24.0)
                .add(Attributes.MOVEMENT_SPEED,       0.26)
                .add(Attributes.ATTACK_DAMAGE,        4.0)
                .add(Attributes.FOLLOW_RANGE,         16.0)
                .add(Attributes.ARMOR,                4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(random.nextBoolean() ? Items.IRON_AXE : Items.IRON_SWORD));
        if (random.nextFloat() < 0.4f)
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        if (random.nextFloat() < 0.5f)
            this.setItemSlot(EquipmentSlot.HEAD,  new ItemStack(Items.CHAINMAIL_HELMET));
        if (random.nextFloat() < 0.35f)
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        if (random.nextFloat() < 0.25f)
            this.setItemSlot(EquipmentSlot.LEGS,  new ItemStack(Items.CHAINMAIL_LEGGINGS));
    }

    public int getVariant() { return variant; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType, @Nullable SpawnGroupData groupData) {
        this.variant = this.random.nextInt(2);
        populateDefaultEquipmentSlots(this.random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }

    @Override
    public void hurtServer(ServerLevel level, DamageSource source, float amount) {
        super.hurtServer(level, source, amount);
        if (source.getEntity() instanceof Player player) {
            this.startPersistentAngerTimer();
            this.setPersistentAngerTarget(player.getUUID());
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    @Override public int getRemainingPersistentAngerTime()              { return angerTime; }
    @Override public void setRemainingPersistentAngerTime(int t)        { angerTime = t; }
    @Override public @Nullable UUID getPersistentAngerTarget()          { return angerTarget; }
    @Override public void setPersistentAngerTarget(@Nullable UUID uuid) { angerTarget = uuid; }
    @Override public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_RANGE.sample(this.random));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readPersistentAngerSaveData(this.level(), tag);
        this.variant = tag.getInt(NBT_VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addPersistentAngerSaveData(tag);
        tag.putInt(NBT_VARIANT, this.variant);
    }

    public static boolean checkSpawnRules(EntityType<NorthWarriorEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }
}