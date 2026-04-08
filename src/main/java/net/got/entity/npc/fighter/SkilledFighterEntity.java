package net.got.entity.npc.fighter;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.got.entity.horse.GotHorseEntity;
import net.got.init.GotModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Abstract base for all Tier 3 Skilled Fighter NPC entities.
 */
public abstract class SkilledFighterEntity extends SmallfolkEntity {

    protected SkilledFighterEntity(EntityType<? extends SkilledFighterEntity> type, Level level) {
        super(type, level);
    }

    /**
     * Probability (0.0–1.0) that this fighter spawns mounted on a horse.
     * Return 0f to never spawn mounted.
     */
    public abstract float getHorseSpawnChance();

    @Override
    protected NpcGender selectGender() { return NpcGender.MALE; }

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

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
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

    private void trySpawnMounted(ServerLevel serverLevel) {
        GotHorseEntity horse = GotModEntities.GOT_HORSE.get()
                .create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
        if (horse == null) return;

        horse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0f);
        horse.setTamed(true);
        horse.setOwnerUUID(this.getUUID());

        if (serverLevel.tryAddFreshEntityWithPassengers(horse)) {
            this.startRiding(horse, true);
        }
    }
}
