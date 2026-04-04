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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

/**
 * Abstract base for all Smallfolk NPC entities.
 *
 * <p>Handles the shared systems every smallfolk NPC needs:
 * <ul>
 *   <li>Gender (MALE / FEMALE) selection and NBT persistence.</li>
 *   <li>Skin-variant index selection per gender.</li>
 *   <li>Random name assignment via {@link NpcNameHelper}.</li>
 *   <li>NeutralMob anger timer.</li>
 *   <li>GeckoLib {@link GeoEntity} integration (cache + abstract controller hook).</li>
 * </ul>
 *
 * <p><b>Adding a new smallfolk culture</b> (e.g. Reachmen, Valemen): create a
 * sub-package under {@code net.got.entity.npc.smallfolk.<culture>}, extend this
 * class, implement {@link #getVariantCount()}, {@link #registerControllers},
 * and fill in goals / equipment.
 */
public abstract class SmallfolkEntity extends PathfinderMob implements NeutralMob, GeoEntity {

    // ── GeckoLib ──────────────────────────────────────────────────────────────

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    // ── Shared NPC fields ──────────────────────────────────────────────────────

    protected static final String NBT_VARIANT  = "GotVariant";
    protected static final String NBT_NPC_NAME = "GotNpcName";
    private   static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    private int       variant   = 0;
    private NpcGender gender    = NpcGender.MALE;
    private String    npcName   = "";

    private int            angerTime;
    @Nullable private UUID angerTarget;

    // ── Constructor ────────────────────────────────────────────────────────────

    protected SmallfolkEntity(EntityType<? extends SmallfolkEntity> type, Level level) {
        super(type, level);
    }

    // ── Subclass contract ──────────────────────────────────────────────────────

    /**
     * Total number of skin variants across all genders.
     * Must be even — the first half are male, the second half female.
     * Override in each concrete entity to return the correct count.
     */
    public abstract int getVariantCount();

    /** Returns the gender assigned at spawn. Override to lock a specific gender. */
    protected NpcGender selectGender() {
        return NpcGender.values()[this.random.nextInt(NpcGender.COUNT)];
    }

    // ── Accessors ──────────────────────────────────────────────────────────────

    public int       getVariant() { return variant; }
    public NpcGender getGender()  { return gender;  }
    public String    getNpcName() { return npcName; }

    /** Variants per gender = totalVariants / 2. */
    public final int getVariantsPerGender() {
        return getVariantCount() / NpcGender.COUNT;
    }

    // ── Spawn initialisation ───────────────────────────────────────────────────

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType, @Nullable SpawnGroupData groupData) {
        // 1. Gender — delegated so subclasses can restrict it.
        this.gender = selectGender();
        // 2. Variant within the gender's sub-range.
        int baseOffset = this.gender.ordinal() * getVariantsPerGender();
        this.variant   = baseOffset + this.random.nextInt(getVariantsPerGender());
        // 3. Name.
        this.npcName = NpcNameHelper.randomName(this.gender, this.random);
        this.setCustomName(net.minecraft.network.chat.Component.literal(this.npcName));
        this.setCustomNameVisible(true);
        populateDefaultEquipmentSlots(this.random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }

    // ── Anger / NeutralMob ─────────────────────────────────────────────────────

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

    // ── Spawn rule helper ─────────────────────────────────────────────────────

    protected static boolean defaultSpawnRules(EntityType<? extends SmallfolkEntity> type,
                                               ServerLevelAccessor level,
                                               EntitySpawnReason spawnType,
                                               BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    // ── NBT ────────────────────────────────────────────────────────────────────

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

    // ── GeckoLib ──────────────────────────────────────────────────────────────

    /**
     * Subclasses must implement this to register their animation controllers.
     * Typically add at least an "idle" / "walk" controller, and an "attack"
     * controller for combat NPCs.
     *
     * <pre>{@code
     * controllers.add(new AnimationController<>(this, "main", 2, state -> {
     *     if (state.isMoving()) return state.setAndContinue(RawAnimation.begin().thenLoop("animation.northman.walk"));
     *     return state.setAndContinue(RawAnimation.begin().thenLoop("animation.northman.idle"));
     * }));
     * }</pre>
     */
    @Override
    public abstract void registerControllers(AnimatableManager.ControllerRegistrar controllers);

    @Override
    public final AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}