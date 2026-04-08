package net.got.entity.npc.smallfolk;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.NpcNameHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Abstract base for all Smallfolk NPC entities.
 */
public abstract class SmallfolkEntity extends PathfinderMob implements NeutralMob {

    protected static final String NBT_VARIANT  = "GotVariant";
    protected static final String NBT_NPC_NAME = "GotNpcName";
    private   static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    private int       variant   = 0;
    private NpcGender gender    = NpcGender.MALE;
    private String    npcName   = "";

    private int            angerTime;
    @Nullable private UUID angerTarget;

    protected SmallfolkEntity(EntityType<? extends SmallfolkEntity> type, Level level) {
        super(type, level);
    }

    public abstract int getVariantCount();

    protected NpcGender selectGender() {
        return NpcGender.values()[this.random.nextInt(NpcGender.COUNT)];
    }

    public int       getVariant() { return variant; }
    public NpcGender getGender()  { return gender;  }
    public String    getNpcName() { return npcName; }

    public final int getVariantsPerGender() {
        return getVariantCount() / NpcGender.COUNT;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType, @Nullable SpawnGroupData groupData) {
        this.gender = selectGender();
        int baseOffset = this.gender.ordinal() * getVariantsPerGender();
        this.variant   = baseOffset + this.random.nextInt(getVariantsPerGender());
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

    @Override public int getRemainingPersistentAngerTime()              { return angerTime;   }
    @Override public void setRemainingPersistentAngerTime(int t)        { angerTime = t;      }
    @Override public @Nullable UUID getPersistentAngerTarget()          { return angerTarget; }
    @Override public void setPersistentAngerTarget(@Nullable UUID uuid) { angerTarget = uuid; }
    @Override public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_RANGE.sample(this.random));
    }

    public static boolean defaultSpawnRules(EntityType<? extends Mob> type,
                                            ServerLevelAccessor level,
                                            EntitySpawnReason spawnType,
                                            BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
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
}
