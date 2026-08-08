package net.got.event.entity.npc.fighter;

import net.got.event.entity.npc.data.GotGenderProvider;
import net.got.event.entity.npc.goal.GotHurtByTargetGoal;
import net.got.event.entity.npc.goal.GotMeleeAttackGoal;
import net.got.event.entity.npc.goal.GotNearestTargetGoal;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract base for all Tier-3 Skilled Fighter NPC entities.
 *
 * <p>Like LOTR's mannish warriors — always male, aggressively seek targets,
 * and have a configurable chance to spawn mounted on a vanilla {@link Horse}
 * (TODO: replace with GotHorseEntity once implemented).
 */
public abstract class SkilledFighterEntity extends SmallfolkEntity {

    protected SkilledFighterEntity(EntityType<? extends SkilledFighterEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.22);
    }

    /** Probability (0–1) that this fighter spawns mounted. Return 0 to never mount. */
    public abstract float getHorseSpawnChance();

    @Override
    public boolean isCivilian() { return false; }

    /** Fighters are military — they never hold civilian occupations. */
    @Override
    protected boolean shouldHaveOccupation() { return false; }

    /** Short rank label for the nameplate — e.g. "Soldier", "Knight". */
    @Override
    public abstract String getMilitaryTitle();

    @Override
    protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE; }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new GotMeleeAttackGoal(this, 1.4));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Aggressive: hunts monsters and attacks players that attack it
        targetSelector.addGoal(1, new GotHurtByTargetGoal(this));
        targetSelector.addGoal(2, new GotNearestTargetGoal<>(this, Monster.class, true));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnType,
                                        @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, groupData);
        if (level instanceof ServerLevel serverLevel
                && getHorseSpawnChance() > 0f
                && this.random.nextFloat() < getHorseSpawnChance()) {
            trySpawnMounted(serverLevel);
        }
        return result;
    }

    private void trySpawnMounted(ServerLevel serverLevel) {
        // TODO: Replace with GotHorseEntity / GotModEntities.GOT_HORSE once that entity is added.
        Horse horse = EntityType.HORSE
                .create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
        if (horse == null) return;
        horse.snapTo(getX(), getY(), getZ(), getYRot(), 0f);
        horse.setTamed(true);
        horse.setOwner(this);
        // Always give the horse a saddle so the rider can properly control it.
        horse.setItemSlot(net.minecraft.world.entity.EquipmentSlot.SADDLE,
                new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.SADDLE));
        if (serverLevel.tryAddFreshEntityWithPassengers(horse)) startRiding(horse, true);
    }
}
