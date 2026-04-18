package net.got.entity.npc.smallfolk;

import net.got.entity.npc.GotNpcTalkAnimations;
import net.got.entity.npc.NpcGender;
import net.got.entity.npc.data.GotGenderProvider;
import net.got.entity.npc.data.GotNpcPersonality;
import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


/**
 * Abstract base for ALL Smallfolk-hierarchy NPC entities (Tiers 1, 2 and 3).
 *
 * <p>Inspired by LOTR's {@code NPCEntity} architecture:
 * <ul>
 *   <li><b>Gender</b> — decided at spawn via {@link GotGenderProvider}, synced to clients.</li>
 *   <li><b>Name</b> — generated at spawn via {@link GotNameGenerator}, shown in the nameplate.</li>
 *   <li><b>Personality</b> — a {@link GotNpcPersonality} trait saved to NBT.</li>
 *   <li><b>Talk animations</b> — head nod / gesture floats synced via {@link GotNpcTalkAnimations}.</li>
 *   <li><b>Variant</b> — texture variant index, split by gender for skin variety.</li>
 * </ul>
 */
public abstract class SmallfolkEntity extends Animal {

    // ── Synced data ───────────────────────────────────────────────────────────

    protected static final EntityDataAccessor<Byte>    DATA_GENDER   =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Integer> DATA_VARIANT  =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<String>  DATA_NPC_NAME =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Boolean> DATA_TALKING  =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.BOOLEAN);
    // Talk-animation floats (see GotNpcTalkAnimations)
    protected static final EntityDataAccessor<Float>   DATA_TALK_HEAD_YAW   =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float>   DATA_TALK_HEAD_PITCH =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float>   DATA_TALK_GESTURE    =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);

    // ── Fields ────────────────────────────────────────────────────────────────

    private GotNpcPersonality personality = GotNpcPersonality.FRIENDLY;
    private final GotNpcTalkAnimations talkAnimations = new GotNpcTalkAnimations(this);

    /** Ticks until this NPC can speak to a player again. */
    private int speechCooldown;
    private static final int SPEECH_INTERVAL = 40;

    // ── Constructor ───────────────────────────────────────────────────────────

    protected SmallfolkEntity(EntityType<? extends SmallfolkEntity> type, Level level) {
        super(type, level);
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
                .add(Attributes.TEMPT_RANGE, 10.0);  // <-- FIX: Required for TemptGoal in MC 1.21.4
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    /** 5-arg SpawnPredicate used by RegisterSpawnPlacementsEvent. */
    public static <T extends SmallfolkEntity> boolean checkSpawnRules(
            EntityType<T> type, ServerLevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, reason, pos, random);
    }

    /** Named alias kept for GotHorseEntity compatibility. */
    public static boolean defaultSpawnRules(
            EntityType<? extends Mob> type, ServerLevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, reason, pos, random);
    }

    // ── Synced data lifecycle ─────────────────────────────────────────────────

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GENDER,   (byte) 0);
        builder.define(DATA_VARIANT,  0);
        builder.define(DATA_NPC_NAME, "");
        builder.define(DATA_TALKING,  false);
        builder.define(DATA_TALK_HEAD_YAW,   0f);
        builder.define(DATA_TALK_HEAD_PITCH, 0f);
        builder.define(DATA_TALK_GESTURE,    0f);
    }

    // ── Gender ────────────────────────────────────────────────────────────────

    /**
     * Override to control the gender distribution for this culture.
     * E.g. levy / fighter entities return {@link GotGenderProvider#MALE}.
     */
    protected GotGenderProvider getGenderProvider() {
        return GotGenderProvider.MALE_OR_FEMALE;
    }

    public NpcGender getGender() {
        return NpcGender.fromByte(entityData.get(DATA_GENDER));
    }

    public boolean isMale()   { return getGender() == NpcGender.MALE; }
    public boolean isFemale() { return getGender() == NpcGender.FEMALE; }

    protected void setGender(NpcGender g) {
        entityData.set(DATA_GENDER, g.toByte());
    }

    // ── Name ─────────────────────────────────────────────────────────────────

    /** Override to provide a culture-specific name generator. */
    protected GotNameGenerator getNameGenerator() {
        return GotNameGenerator.NAMELESS;
    }

    /** The NPC's personal name string (empty = no personal name). */
    public String getNpcName() { return entityData.get(DATA_NPC_NAME); }

    protected void setNpcName(String name) { entityData.set(DATA_NPC_NAME, name); }

    /**
     * Exposes the NPC's personal name as the entity's "custom name" so
     * Minecraft's nameplate renderer picks it up via {@code hasCustomName()}.
     *
     * <p>Returns the fully-formatted component — e.g. "Jon the Northman" —
     * so the nameplate, death messages, and chat logs all use the same text.
     * Returns {@code null} when no personal name has been assigned (before
     * {@code finalizeSpawn} runs), which suppresses the nameplate entirely.
     *
     * <p><b>FIX:</b> the old {@code getName()} override was invisible to the
     * nameplate because Minecraft checks {@code getCustomName() != null}, not
     * {@code getName()}. Moving the logic here corrects that.
     */
    @Override
    public @Nullable Component getCustomName() {
        String personal = getNpcName();
        if (personal == null || personal.isEmpty()) return null;
        return Component.translatable("entity.got.npc.named",
                Component.literal(personal),
                Component.translatable(getType().getDescriptionId()));
    }

    /**
     * Always show the nameplate above named NPCs, mirroring LOTR's behaviour.
     * Without this override the nameplate only appears when the player looks
     * directly at the entity.
     */
    @Override
    public boolean isCustomNameVisible() {
        String personal = getNpcName();
        return personal != null && !personal.isEmpty();
    }

    // ── Personality ───────────────────────────────────────────────────────────

    public GotNpcPersonality getPersonality() { return personality; }

    // ── Talk animations ───────────────────────────────────────────────────────

    public GotNpcTalkAnimations getTalkAnimations() { return talkAnimations; }

    public boolean isTalking() { return entityData.get(DATA_TALKING); }

    private void setTalking(boolean b) { entityData.set(DATA_TALKING, b); }

    /** Call this when the player right-clicks the NPC to start a conversation. */
    public void startTalkingTo(Player player) {
        if (!level().isClientSide) {
            setTalking(true);
        }
    }

    /** Called after the conversation timer expires. */
    public void stopTalking() {
        if (!level().isClientSide) {
            setTalking(false);
        }
    }

    // ── Variant (skin variety) ────────────────────────────────────────────────

    /** Number of texture variants per gender. Override in each culture class. */
    public int getVariantsPerGender() { return 1; }

    public int getVariant() { return entityData.get(DATA_VARIANT); }

    protected void setVariant(int v) { entityData.set(DATA_VARIANT, v); }

    // ── Civilian check ────────────────────────────────────────────────────────

    /**
     * Returns {@code true} if this is a non-combat NPC (Tier 1 smallfolk).
     * Overridden to {@code false} in levy and fighter subclasses.
     */
    public boolean isCivilian() { return true; }

    /** Initializes gender/variant/name/personality for newly created NPCs. */
    protected void assignIdentityFromRandom(RandomSource rand) {
        boolean male = getGenderProvider().isMale(rand);
        setGender(male ? NpcGender.MALE : NpcGender.FEMALE);

        int vpg = getVariantsPerGender();
        int variant = male
                ? rand.nextInt(Math.max(1, vpg))
                : vpg + rand.nextInt(Math.max(1, vpg));
        setVariant(variant);

        setNpcName(getNameGenerator().generateName(rand, male));
        personality = GotNpcPersonality.random(rand);
    }

    // ── Spawn ─────────────────────────────────────────────────────────────────

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);

        assignIdentityFromRandom(random);

        return result;
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            talkAnimations.serverTick();
            if (speechCooldown < Integer.MAX_VALUE) speechCooldown++;
        }
    }

    /** @return true if enough time has passed to speak to a player again. */
    protected boolean canSpeakToPlayer() {
        return speechCooldown >= SPEECH_INTERVAL;
    }

    protected void markSpoken() { speechCooldown = 0; }

    // ── NBT ───────────────────────────────────────────────────────────────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte(NpcGender.NBT_KEY, getGender().toByte());
        tag.putInt("Variant", getVariant());
        tag.putString("NpcName", getNpcName());
        tag.putString("Personality", personality.id);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setGender(NpcGender.fromByte(tag.getByte(NpcGender.NBT_KEY)));
        setVariant(tag.getInt("Variant"));
        setNpcName(tag.getString("NpcName"));
        personality = GotNpcPersonality.fromString(tag.getString("Personality"));
    }

    // ── Default AI (peaceful civilian) ───────────────────────────────────────

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        if (isCivilian()) {
            goalSelector.addGoal(2, new BreedGoal(this, 1.0));
            goalSelector.addGoal(3, new TemptGoal(this, 1.1, net.minecraft.world.item.crafting.Ingredient.of(Items.BREAD), false));
        }
        goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f, 0.02f));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, SmallfolkEntity.class, 5.0f, 0.02f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }


    // ── Player interaction (right-click to "talk") ────────────────────────────

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && isAlive() && canSpeakToPlayer()) {
            if (!level().isClientSide) {
                startTalkingTo(player);
                markSpoken();
                playSound(SoundEvents.VILLAGER_AMBIENT, 0.6f,
                        0.9f + random.nextFloat() * 0.2f);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return isCivilian() && stack.is(Items.BREAD);
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (!(otherAnimal instanceof SmallfolkEntity other)) return false;
        if (other == this) return false;
        if (!isCivilian() || !other.isCivilian()) return false;
        if (this.getClass() != other.getClass()) return false;
        return isInLove() && other.isInLove()
                && !isBaby() && !other.isBaby()
                && this.getGender() != other.getGender();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Entity child = getType().create(level, EntitySpawnReason.BREEDING);
        if (child instanceof SmallfolkEntity baby) {
            baby.assignIdentityFromRandom(level.getRandom());
            baby.setAge(-24000);
            return baby;
        }
        return null;
    }

    // ── Equipment slot helpers (for use in subclass finalizeSpawn) ────────────

    /**
     * Equip an item in the main hand.  Convenience wrapper matching LOTR's
     * {@code npcItemsInv.setMeleeWeapon()} pattern.
     */
    protected void setMainhandItem(ItemStack stack) {
        setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    /** Equip a helmet. */
    protected void setHelmet(ItemStack stack) {
        setItemSlot(EquipmentSlot.HEAD, stack);
    }

    /** Equip a chestplate. */
    protected void setChestplate(ItemStack stack) {
        setItemSlot(EquipmentSlot.CHEST, stack);
    }

    /** Equip leggings. */
    protected void setLeggings(ItemStack stack) {
        setItemSlot(EquipmentSlot.LEGS, stack);
    }

    /** Equip boots. */
    protected void setBoots(ItemStack stack) {
        setItemSlot(EquipmentSlot.FEET, stack);
    }

    // ── Talk-animation data accessors (package-private, used by GotNpcTalkAnimations) ──

    public void setTalkData(float headYaw, float headPitch, float gesture) {
        entityData.set(DATA_TALK_HEAD_YAW,   headYaw);
        entityData.set(DATA_TALK_HEAD_PITCH, headPitch);
        entityData.set(DATA_TALK_GESTURE,    gesture);
    }

    public float getTalkHeadYaw()   { return entityData.get(DATA_TALK_HEAD_YAW); }
    public float getTalkHeadPitch() { return entityData.get(DATA_TALK_HEAD_PITCH); }
    public float getTalkGesture()   { return entityData.get(DATA_TALK_GESTURE); }
}