package net.got.entity.mammoth;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Mammoth — the great shaggy mammoths of the lands Beyond the Wall.
 *
 * <p>Ridden by giants in the lore, these mammoths are massive neutral herbivores
 * that roam the frozen tundra north of the Wall.
 *
 * <p>Behaviour summary:
 * <ul>
 *   <li>Neutral — passive until attacked, then fights back with a charge/trample.</li>
 *   <li>Spawns on snowy plains and frozen terrain Beyond the Wall.</li>
 *   <li>Breeds with hay bales (massive herbivores need large food portions).</li>
 *   <li>Causes knockback on melee hit — being stomped by a mammoth hurts.</li>
 * </ul>
 *
 * <p>Animation states driven by {@link net.got.entity.client.mammoth.GotMammothRenderer}:
 * idle, walk, charge, swim.
 */
public class GotMammothEntity extends Animal {

    /** True when this mammoth is actively attacking. */
    public boolean isAttacking = false;

    public GotMammothEntity(EntityType<? extends GotMammothEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        isAttacking = this.getTarget() != null;
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    /**
     * Mammoths are titanic — more health than anything vanilla, and a sweeping
     * attack knockback that sends players flying.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // Fights back — mammoth is neutral, not passive
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    // ── Sounds ────────────────────────────────────────────────────────────────

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;   // closest vanilla mega-beast rumble
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RAVAGER_DEATH;
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.HAY_BLOCK) || stack.is(Items.WHEAT);
    }

    @Override
    public @Nullable GotMammothEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotMammothEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotMammothEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        var groundBlock = level.getBlockState(pos.below()).getBlock();
        if (groundBlock != Blocks.SNOW_BLOCK
                && groundBlock != Blocks.POWDER_SNOW
                && groundBlock != Blocks.PACKED_ICE
                && groundBlock != Blocks.COARSE_DIRT
                && groundBlock != Blocks.PODZOL
                && groundBlock != Blocks.GRASS_BLOCK) {
            return false;
        }
        return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                (EntityType) type, level, spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}