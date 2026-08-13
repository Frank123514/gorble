package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.NpcSpeechBank;
import net.got.event.entity.npc.NpcTalkAnimations;
import net.got.event.entity.npc.NpcGender;
import net.got.event.entity.npc.NpcInventory;
import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.NpcOccupation;
import net.got.event.entity.npc.data.NpcPersonality;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.network.OpenInteractScreenPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

    private NpcPersonality personality = NpcPersonality.FRIENDLY;
    private final NpcTalkAnimations talkAnimations = new NpcTalkAnimations(this);
    
    private int speechCooldown;
    private static final int SPEECH_INTERVAL = 40;

    protected int talkTimer;
    private static final int TALK_DURATION = 80;

    private @Nullable Player talkingPlayer;

    private NpcOccupation occupation = NpcOccupation.NONE;

    private final NpcInventory npcInventory = new NpcInventory();

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
            String title = getMilitaryTitle();
            if (title != null && !title.isEmpty()) {
                return Component.translatable("entity.got.npc.named_military",
                        Component.literal(personal),
                        Component.literal(title));
            }
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

    public @Nullable String getMilitaryTitle() { return null; }

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

    public int getVariant() { return entityData.get(DATA_VARIANT); }

    protected void setVariant(int v) { entityData.set(DATA_VARIANT, v); }

    public boolean isCivilian() { return true; }

    protected void assignIdentityFromRandom(RandomSource rand) {
        boolean male = getGenderProvider().isMale(rand);
        setGender(male ? NpcGender.MALE : NpcGender.FEMALE);

        int vpg = getVariantsPerGender();
        int variant = male
                ? rand.nextInt(Math.max(1, vpg))
                : vpg + rand.nextInt(Math.max(1, vpg));
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
        assignIdentityFromRandom(random);
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
        npcInventory.save(output);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setGender(NpcGender.fromByte(input.getByteOr(NpcGender.NBT_KEY, (byte) 0)));
        setVariant(input.getIntOr("Variant", 0));
        setNpcName(input.getStringOr("NpcName", ""));
        personality = NpcPersonality.fromString(input.getStringOr("Personality", ""));
        if (isCivilian()) {
            setOccupation(NpcOccupation.fromString(input.getStringOr(NpcOccupation.NBT_KEY, "")));
        } else {
            setOccupation(NpcOccupation.NONE);
        }
        npcInventory.load(input);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f, 0.02f));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, SmallfolkEntity.class, 5.0f, 0.02f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
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