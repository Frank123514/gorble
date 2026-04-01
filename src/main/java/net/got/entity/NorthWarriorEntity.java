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
import net.got.entity.NpcNameHelper;

/**
 * North Warrior NPC — heavily-armed melee fighter.
 *
 * <h3>Variant / gender layout</h3>
 * <pre>
 *   variant 0-1  →  MALE    (north_warrior_male_1, north_warrior_male_2)
 *   variant 2-3  →  FEMALE  (north_warrior_female_1, north_warrior_female_2)
 * </pre>
 */
public class NorthWarriorEntity extends PathfinderMob implements NeutralMob {

    public static final int VARIANT_COUNT       = 4;  // 2 male + 2 female
    public static final int VARIANTS_PER_GENDER = VARIANT_COUNT / NpcGender.COUNT; // 2

    private static final String NBT_VARIANT  = "GotVariant";
    private static final String NBT_NPC_NAME = "GotNpcName";
    private static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    private int variant = 0;
    private NpcGender gender = NpcGender.MALE;
    private String npcName = "";
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
    public NpcGender getGender() { return gender; }
    public String getNpcName() { return npcName; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType, @Nullable SpawnGroupData groupData) {
        // 1. Pick gender (50 / 50).
        this.gender = NpcGender.values()[this.random.nextInt(NpcGender.COUNT)];
        // 2. Pick a skin within that gender's range: male → 0-1, female → 2-3.
        int baseOffset = this.gender.ordinal() * VARIANTS_PER_GENDER;
        this.variant = baseOffset + this.random.nextInt(VARIANTS_PER_GENDER);
        // 3. Assign a random name from the gender-appropriate name list.
        this.npcName = NpcNameHelper.randomName(this.gender, this.random);
        this.setCustomName(net.minecraft.network.chat.Component.literal(this.npcName));
        this.setCustomNameVisible(true);
        populateDefaultEquipmentSlots(this.random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean result = super.hurtServer(level, source, amount);
        if (result && source.getEntity() instanceof Player player) {
            this.startPersistentAngerTimer();
            this.setPersistentAngerTarget(player.getUUID());
        }
        return result;
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
        this.gender  = NpcGender.fromByte(tag.getByte(NpcGender.NBT_KEY));
        if (tag.contains(NBT_NPC_NAME)) {
            this.npcName = tag.getString(NBT_NPC_NAME);
            this.setCustomName(net.minecraft.network.chat.Component.literal(this.npcName));
            this.setCustomNameVisible(true);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addPersistentAngerSaveData(tag);
        tag.putInt(NBT_VARIANT,        this.variant);
        tag.putByte(NpcGender.NBT_KEY, this.gender.toByte());
        tag.putString(NBT_NPC_NAME,    this.npcName);
    }

    public static boolean checkSpawnRules(EntityType<NorthWarriorEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }
}