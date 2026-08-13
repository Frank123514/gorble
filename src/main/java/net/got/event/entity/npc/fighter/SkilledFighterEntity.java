package net.got.event.entity.npc.fighter;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.goal.GotHurtByTargetGoal;
import net.got.event.entity.npc.goal.GotMeleeAttackGoal;
import net.got.event.entity.npc.goal.NearestTargetGoal;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

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

    public abstract float getHorseSpawnChance();

    @Override
    public boolean isCivilian() { return false; }

    @Override
    protected boolean shouldHaveOccupation() { return false; }

    @Override
    public abstract String getMilitaryTitle();

    @Override
    protected GenderProvider getGenderProvider() { return GenderProvider.MALE; }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new GotMeleeAttackGoal(this, 1.4));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        targetSelector.addGoal(1, new GotHurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestTargetGoal<>(this, Monster.class, true));
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
        
        Horse horse = EntityType.HORSE
                .create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
        if (horse == null) return;
        horse.snapTo(getX(), getY(), getZ(), getYRot(), 0f);
        horse.setTamed(true);
        horse.setOwner(this);
        
        horse.setItemSlot(net.minecraft.world.entity.EquipmentSlot.SADDLE,
                new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.SADDLE));
        
        if (serverLevel.tryAddFreshEntityWithPassengers(horse)) startRiding(horse, true, true);
    }
}
