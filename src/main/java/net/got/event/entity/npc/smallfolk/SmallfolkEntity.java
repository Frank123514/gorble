package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.NpcSpeechBank;
import net.got.event.entity.npc.NpcTalkAnimations;
import net.got.event.entity.npc.NpcGender;
import net.got.event.entity.npc.NpcInventory;
import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.NpcOccupation;
import net.got.event.entity.npc.data.NpcPersonality;
import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.goal.GotHurtByTargetGoal;
import net.got.event.entity.npc.goal.GotMeleeAttackGoal;
import net.got.event.entity.npc.goal.NearestTargetGoal;
import net.got.network.OpenInteractScreenPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public abstract class SmallfolkEntity extends PathfinderMob {

    protected static final EntityDataAccessor<Byte>    DATA_GENDER   =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.BYTE);
    protected static final EntityDataAccessor<Integer> DATA_VARIANT  =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<String>  DATA_NPC_NAME =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Boolean> DATA_TALKING  =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.BOOLEAN);
    
    protected static final EntityDataAccessor<Float>   DATA_TALK_HEAD_YAW   =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float>   DATA_TALK_HEAD_PITCH =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float>   DATA_TALK_GESTURE    =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.FLOAT);
    
    protected static final EntityDataAccessor<String>  DATA_DIALOGUE_LINE   =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.STRING);
    
    protected static final EntityDataAccessor<String>  DATA_OCCUPATION      =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.STRING);

    /** Rank / station (peasant, levy, man-at-arms, cavalryman, knight). See {@link NpcRank}. */
    protected static final EntityDataAccessor<String>  DATA_RANK            =
            SynchedEntityData.defineId(SmallfolkEntity.class, EntityDataSerializers.STRING);

    private NpcPersonality personality = NpcPersonality.FRIENDLY;
    private final NpcTalkAnimations talkAnimations = new NpcTalkAnimations(this);
    
    private int speechCooldown;
    private static final int SPEECH_INTERVAL = 40;

    protected int talkTimer;
    private static final int TALK_DURATION = 80;

    private @Nullable Player talkingPlayer;

    private NpcOccupation occupation = NpcOccupation.NONE;

    private final NpcInventory npcInventory = new NpcInventory();

    /** True when the rank came from saved data / a spawn egg's entity_data, so spawning must not re-roll it. */
    private boolean rankFromData;

    protected SmallfolkEntity(EntityType<? extends SmallfolkEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0);
    }

    public static <T extends SmallfolkEntity> boolean checkSpawnRules(
            EntityType<T> type, ServerLevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, reason, pos, random);
    }

    public static boolean defaultSpawnRules(
            EntityType<? extends Mob> type, ServerLevelAccessor level,
            EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, reason, pos, random);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

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
        builder.define(DATA_DIALOGUE_LINE,   "");
        builder.define(DATA_OCCUPATION,      NpcOccupation.NONE.id);
        builder.define(DATA_RANK,            NpcRank.PEASANT.id);
    }

    protected GenderProvider getGenderProvider() {
        return GenderProvider.MALE_OR_FEMALE;
    }

    public NpcGender getGender() {
        return NpcGender.fromByte(entityData.get(DATA_GENDER));
    }

    public boolean isMale()   { return getGender() == NpcGender.MALE; }
    public boolean isFemale() { return getGender() == NpcGender.FEMALE; }

    protected void setGender(NpcGender g) {
        entityData.set(DATA_GENDER, g.toByte());
    }

    protected NameGenerator getNameGenerator() {
        return NameGenerator.NAMELESS;
    }

    public String getNpcName() { return entityData.get(DATA_NPC_NAME); }

    protected void setNpcName(String name) { entityData.set(DATA_NPC_NAME, name); }

    @Override
    public @Nullable Component getCustomName() {
        String personal = getNpcName();
        if (personal == null || personal.isEmpty()) return null;

        if (!isCivilian()) {
            NpcRank rank = getRank();
            if (rank.isPrefixTitle()) {
                return Component.translatable("entity.got.npc.named_knight",
                        Component.literal(rank.title),
                        Component.literal(personal));
            }
            return Component.translatable("entity.got.npc.named_military",
                    Component.literal(personal),
                    Component.literal(rank.title));
        }

        NpcOccupation occ = getOccupation();
        if (occ.isEmployed()) {
            return Component.translatable("entity.got.npc.named_with_occupation",
                    Component.literal(occ.label),
                    Component.literal(personal),
                    Component.translatable(getType().getDescriptionId()));
        }
        return Component.translatable("entity.got.npc.named",
                Component.literal(personal),
                Component.translatable(getType().getDescriptionId()));
    }

    public @Nullable String getMilitaryTitle() { return isCivilian() ? null : getRank().title; }

    @Override
    public boolean isCustomNameVisible() {
        String personal = getNpcName();
        return personal != null && !personal.isEmpty();
    }

    public NpcPersonality getPersonality() { return personality; }

    public NpcTalkAnimations getTalkAnimations() { return talkAnimations; }

    public boolean isTalking() { return entityData.get(DATA_TALKING); }

    private void setTalking(boolean b) { entityData.set(DATA_TALKING, b); }

    public void startTalkingTo(Player player) {
        if (!level().isClientSide()) {
            setTalking(true);
            talkTimer = TALK_DURATION;
            talkingPlayer = player;
            String line = getSpeechBank().randomLine(getRandom());
            entityData.set(DATA_DIALOGUE_LINE, line);
            getNavigation().stop();
        }
    }

    public void extendTalkTimer(int ticks) {
        if (!level().isClientSide()) {
            if (!isTalking()) setTalking(true);
            if (talkTimer < ticks) talkTimer = ticks;
            getNavigation().stop();
        }
    }

    protected boolean shouldHaveOccupation() {
        return isCivilian();
    }

    public void stopTalking() {
        if (!level().isClientSide()) {
            setTalking(false);
            talkingPlayer = null;
            entityData.set(DATA_DIALOGUE_LINE, "");
        }
    }

    protected NpcSpeechBank getSpeechBank() {
        return NpcSpeechBank.SMALLFOLK_CIVILIAN;
    }

    public String getCurrentDialogueLine() {
        return entityData.get(DATA_DIALOGUE_LINE);
    }

    public NpcOccupation getOccupation() {
        return NpcOccupation.fromString(entityData.get(DATA_OCCUPATION));
    }

    public void setOccupation(NpcOccupation o) {
        occupation = o;
        entityData.set(DATA_OCCUPATION, o.id);
    }

    public NpcInventory getNpcInventory() {
        return npcInventory;
    }

    public int getVariantsPerGender() { return 1; }

    /** How many female skins this NPC type has; defaults to the male count. */
    public int getFemaleVariantsPerGender() { return getVariantsPerGender(); }

    public int getVariant() { return entityData.get(DATA_VARIANT); }

    protected void setVariant(int v) { entityData.set(DATA_VARIANT, v); }

    public boolean isCivilian() { return getRank().civilian; }

    // ── Rank ──────────────────────────────────────────────────────────────────

    public NpcRank getRank() { return NpcRank.fromString(entityData.get(DATA_RANK)); }

    /** Rank to give a freshly spawned NPC of this region. Regions may override to shift the mix. */
    protected NpcRank rollRank(RandomSource rand) { return NpcRank.roll(rand); }

    /**
     * Changes rank: title, stats and behaviour follow. Does not touch gear or occupation
     * (spawning handles those), so it is safe to call when loading saved data.
     */
    public void setRank(NpcRank rank) {
        NpcRank previous = getRank();
        entityData.set(DATA_RANK, rank.id);
        if (level().isClientSide()) return;

        applyRankAttributes(rank);
        if (getHealth() > getMaxHealth()) setHealth(getMaxHealth());

        if (previous != rank) {
            goalSelector.removeAllGoals(g -> true);
            targetSelector.removeAllGoals(g -> true);
            registerGoals();
        }
    }

    private void applyRankAttributes(NpcRank rank) {
        setBase(Attributes.MAX_HEALTH,     rank.maxHealth);
        setBase(Attributes.ATTACK_DAMAGE,  rank.attackDamage);
        setBase(Attributes.ARMOR,          rank.armor);
        setBase(Attributes.MOVEMENT_SPEED, rank.speed);
    }

    private void setBase(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attr, double value) {
        AttributeInstance inst = getAttribute(attr);
        if (inst != null) inst.setBaseValue(value);
    }

    /** Weapon + armor for the current rank. Called once, at spawn. */
    protected void equipForRank(NpcRank rank) {
        Item[] weapons = rank.weapons();
        if (weapons.length > 0) {
            setMainhandItem(new ItemStack(weapons[random.nextInt(weapons.length)]));
        }
        Item[] armor = rank.armorSet();
        EquipmentSlot[] slots = { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
        for (int i = 0; i < slots.length; i++) {
            if (armor[i] != null && random.nextFloat() < rank.armorChance()) {
                setItemSlot(slots[i], new ItemStack(armor[i]));
            }
        }
        // Padded undergarment in the gambeson slot, layered beneath any chest armor.
        if (random.nextFloat() < rank.gambesonChance()) {
            net.got.gambeson.GambesonSlotData.set(this,
                    new ItemStack(net.got.init.ModItems.GAMBESON.get()));
        }
    }

    private void trySpawnMounted(ServerLevel serverLevel) {
        Horse horse = EntityType.HORSE.create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
        if (horse == null) return;
        horse.snapTo(getX(), getY(), getZ(), getYRot(), 0f);
        horse.setTamed(true);
        horse.setOwner(this);
        horse.setItemSlot(EquipmentSlot.SADDLE, new ItemStack(net.minecraft.world.item.Items.SADDLE));
        if (serverLevel.tryAddFreshEntityWithPassengers(horse)) startRiding(horse, true, true);
    }

    protected void assignIdentityFromRandom(RandomSource rand) {
        NpcRank rank = getRank();
        boolean male = rank.femaleChance >= 0f
                ? rand.nextFloat() >= rank.femaleChance
                : getGenderProvider().isMale(rand);
        setGender(male ? NpcGender.MALE : NpcGender.FEMALE);

        int vpg = getVariantsPerGender();
        int variant = male
                ? rand.nextInt(Math.max(1, vpg))
                : vpg + rand.nextInt(Math.max(1, getFemaleVariantsPerGender()));
        setVariant(variant);

        setNpcName(getNameGenerator().generateName(rand, male));
        personality = NpcPersonality.random(rand);

        if (shouldHaveOccupation()) {
            NpcOccupation[] pool = male
                    ? NpcOccupation.HIREABLE
                    : NpcOccupation.HIREABLE_FEMALE;
            setOccupation(pool[rand.nextInt(pool.length)]);
        } else {
            setOccupation(NpcOccupation.NONE);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);

        // Rank first: gender, occupation, stats and gear all depend on it.
        if (!rankFromData) setRank(rollRank(random));
        NpcRank rank = getRank();

        assignIdentityFromRandom(random);
        setHealth(getMaxHealth());
        equipForRank(rank);

        if (level instanceof ServerLevel serverLevel
                && rank.horseChance > 0f
                && random.nextFloat() < rank.horseChance) {
            trySpawnMounted(serverLevel);
        }
        return result;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            talkAnimations.serverTick();
            if (speechCooldown < Integer.MAX_VALUE) speechCooldown++;

            if (isTalking()) {
                getNavigation().stop();
                if (talkingPlayer != null && talkingPlayer.isAlive()) {
                    getLookControl().setLookAt(talkingPlayer, 30f, 30f);
                }
                if (talkTimer > 0) {
                    talkTimer--;
                } else {
                    stopTalking();
                }
            }
        }
    }

    protected boolean canSpeakToPlayer() {
        return speechCooldown >= SPEECH_INTERVAL;
    }

    protected void markSpoken() { speechCooldown = 0; }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putByte(NpcGender.NBT_KEY, getGender().toByte());
        output.putInt("Variant", getVariant());
        output.putString("NpcName", getNpcName());
        output.putString("Personality", personality.id);
        output.putString(NpcOccupation.NBT_KEY, occupation.id);
        output.putString(NpcRank.NBT_KEY, getRank().id);
        npcInventory.save(output);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setGender(NpcGender.fromByte(input.getByteOr(NpcGender.NBT_KEY, (byte) 0)));
        setVariant(input.getIntOr("Variant", 0));
        setNpcName(input.getStringOr("NpcName", ""));
        personality = NpcPersonality.fromString(input.getStringOr("Personality", ""));
        String savedRank = input.getStringOr(NpcRank.NBT_KEY, "");
        if (NpcRank.isValid(savedRank)) {
            rankFromData = true;
            setRank(NpcRank.fromString(savedRank));
        }
        if (isCivilian()) {
            setOccupation(NpcOccupation.fromString(input.getStringOr(NpcOccupation.NBT_KEY, "")));
        } else {
            setOccupation(NpcOccupation.NONE);
        }
        npcInventory.load(input);
    }

    /**
     * Goals depend on rank: peasants flee and wander, combat ranks fight back and hunt monsters.
     * Rebuilt by {@link #setRank} when the rank changes.
     */
    @Override
    protected void registerGoals() {
        NpcRank rank = getRank();
        goalSelector.addGoal(0, new FloatGoal(this));

        if (rank.civilian) {
            goalSelector.addGoal(1, new PanicGoal(this, 1.25));
            goalSelector.addGoal(4, new OpenDoorGoal(this, true));
            goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
            goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f, 0.02f));
            goalSelector.addGoal(6, new LookAtPlayerGoal(this, SmallfolkEntity.class, 5.0f, 0.02f));
            goalSelector.addGoal(7, new RandomLookAroundGoal(this));
            return;
        }

        goalSelector.addGoal(2, new GotMeleeAttackGoal(this, rank == NpcRank.LEVY ? 1.3 : 1.4));
        goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, rank == NpcRank.LEVY ? 1.0 : 0.9));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        targetSelector.addGoal(1, new GotHurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestTargetGoal<>(this, Monster.class, true));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND || !isAlive()) {
            return super.mobInteract(player, hand);
        }

        if (!level().isClientSide() && player instanceof ServerPlayer sp) {
            String npcName = getNpcName().isEmpty()
                    ? getType().getDescription().getString() : getNpcName();
            String milTitle = isCivilian() ? "" : (getMilitaryTitle() != null ? getMilitaryTitle() : "");
            PacketDistributor.sendToPlayer(sp,
                    new OpenInteractScreenPayload(
                            getId(), getOccupation().id, npcName, milTitle));
            startTalkingTo(player);
            extendTalkTimer(600);
        }
        return InteractionResult.SUCCESS;
    }

    protected void setMainhandItem(ItemStack stack) {
        setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    protected void setHelmet(ItemStack stack) {
        setItemSlot(EquipmentSlot.HEAD, stack);
    }

    protected void setChestplate(ItemStack stack) {
        setItemSlot(EquipmentSlot.CHEST, stack);
    }

    protected void setLeggings(ItemStack stack) {
        setItemSlot(EquipmentSlot.LEGS, stack);
    }

    protected void setBoots(ItemStack stack) {
        setItemSlot(EquipmentSlot.FEET, stack);
    }

    public void setTalkData(float headYaw, float headPitch, float gesture) {
        entityData.set(DATA_TALK_HEAD_YAW,   headYaw);
        entityData.set(DATA_TALK_HEAD_PITCH, headPitch);
        entityData.set(DATA_TALK_GESTURE,    gesture);
    }

    public float getTalkHeadYaw()   { return entityData.get(DATA_TALK_HEAD_YAW); }
    public float getTalkHeadPitch() { return entityData.get(DATA_TALK_HEAD_PITCH); }
    public float getTalkGesture()   { return entityData.get(DATA_TALK_GESTURE); }

    public void triggerAnim(String controllerName, String animName) {
        this.swing(InteractionHand.MAIN_HAND);
    }
}