package net.got.entity.npc.fighter;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Abstract base for all Tier 3 Skilled Fighter NPC entities.
 *
 * <h3>Tier overview</h3>
 * <pre>
 *   Tier 1 — Smallfolk      : unarmed civilians
 *   Tier 2 — Levy           : armed conscripts
 *   Tier 3 — Skilled Fighter : THIS CLASS — professional soldiers with optional mounts
 * </pre>
 *
 * <h3>Horse-spawning system</h3>
 * Subclasses declare their mounted spawn probability via
 * {@link #getHorseSpawnChance()}. A value of {@code 0f} disables horse
 * spawning entirely (e.g. Iron Island fighters who have no horse culture).
 * A value of {@code 0.4f} means 40% of spawns will attempt to place the
 * fighter on a horse.
 *
 * <p>When a horse is spawned it is tamed and immediately mounted by the
 * fighter. If the horse cannot be placed (e.g. not enough space), the
 * fighter simply spawns on foot without error.
 *
 * <h3>Adding a new skilled fighter culture</h3>
 * Create a sub-package under {@code net.got.entity.npc.fighter.<house>},
 * extend this class, implement {@link SmallfolkEntity#getVariantCount()},
 * {@link SmallfolkEntity#registerControllers}, override
 * {@link #populateDefaultEquipmentSlots}, and return the correct horse
 * chance from {@link #getHorseSpawnChance()}.
 */
public abstract class SkilledFighterEntity extends SmallfolkEntity {

    protected SkilledFighterEntity(EntityType<? extends SkilledFighterEntity> type, Level level) {
        super(type, level);
    }

    // ── Subclass contract ─────────────────────────────────────────────────────

    /**
     * Probability (0.0–1.0) that this fighter spawns mounted on a horse.
     *
     * <ul>
     *   <li>0.0f — never mounted (e.g. Iron Islands, Dorne foot soldiers)</li>
     *   <li>0.25f — occasional cavalry (e.g. North soldiers on open plains)</li>
     *   <li>0.5f — frequent cavalry (e.g. Vale knights in mountain passes)</li>
     *   <li>0.8f — primarily cavalry (e.g. Reach or Lannister heavy horse)</li>
     * </ul>
     */
    public abstract float getHorseSpawnChance();

    @Override
    protected NpcGender selectGender() { return NpcGender.MALE; }

    // ── Goals ─────────────────────────────────────────────────────────────────
    // Skilled fighters are aggressive professionals — they actively hunt monsters
    // and maintain formation better than levies.

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    // ── Horse spawning ────────────────────────────────────────────────────────

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, net.minecraft.world.DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType,
                                        @org.jetbrains.annotations.Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, groupData);

        if (level instanceof ServerLevel serverLevel
                && getHorseSpawnChance() > 0f
                && this.random.nextFloat() < getHorseSpawnChance()) {
            trySpawnMounted(serverLevel);
        }

        return result;
    }

    /**
     * Attempts to spawn a tamed horse at this fighter's position and mount it.
     * Fails silently if the horse cannot be placed (no valid ground, etc.).
     */
    private void trySpawnMounted(ServerLevel serverLevel) {
        Horse horse = EntityType.HORSE.create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
        if (horse == null) return;

        horse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0f);
        horse.setTamed(true);
        horse.setOwnerUUID(this.getUUID());

        // Randomise horse appearance

        if (serverLevel.tryAddFreshEntityWithPassengers(horse)) {
            this.startRiding(horse, true);
        }
    }
}
