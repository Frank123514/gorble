package net.got.event.entity.stag;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;


/**
 * GOT Stag — a great red deer stag of the Westerosi forests.
 *
 * <p>Extends {@link Animal} directly (not {@link net.minecraft.world.entity.animal.equine.Horse})
 * because the stag uses a fully custom geo model ({@code gotdeer.bbmodel}) and its own
 * animation set ({@link net.got.event.entity.client.stag.GotStagAnimations}).  No horse-specific
 * machinery (taming, saddle slots, AbstractHorse data-accessors) is needed or wanted.
 *
 * <p>Behaviour summary:
 * <ul>
 *   <li>Passive herbivore — flees players and hostile mobs.</li>
 *   <li>Breeds with wheat (consistent with vanilla deer-like animals).</li>
 *   <li>Spawns like other woodland creatures via {@link #checkSpawnRules}.</li>
 * </ul>
 *
 * <p>Animation states driven by {@link net.got.event.entity.client.stag.GotStagRenderer}:
 * <ul>
 *   <li>{@code idle}     — subtle breathing bob.</li>
 *   <li>{@code walk}     — 4-beat walk gait.</li>
 *   <li>{@code run}      — bounding gallop.</li>
 *   <li>{@code swim}     — paddling motion in water.</li>
 *   <li>{@code tail_wag} — idle tail flick.</li>
 * </ul>
 */
public class GotStagEntity extends Animal {

    // ── Variant ───────────────────────────────────────────────────────────────

    /** 0 = red stag (default), 1 = white stag */
    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(GotStagEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT);
    }

    private void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    public GotStagEntity(EntityType<? extends GotStagEntity> type, Level level) {
        super(type, level);
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    /**
     * Registered by {@link net.got.event.entity.GotEntityEvents} during
     * {@code EntityAttributeCreationEvent}.
     * Stags are fast and nimble — lighter stats than a warhorse.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        // Panic + flee
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        // Breeding
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        // Follow parent when baby
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25));
        // Wander and look around
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        // Flee players and hostile mobs
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    /** Stags breed with wheat, like other vanilla deer-like animals. */
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT);
    }

    @Override
    public @Nullable GotStagEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotStagEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotStagEntity> type,
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
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        // ~10% chance of white stag variant
        this.setVariant(level.getRandom().nextFloat() < 0.10F ? 1 : 0);
        return spawnGroupData;
    }
}
