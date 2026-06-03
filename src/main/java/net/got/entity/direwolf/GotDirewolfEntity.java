package net.got.entity.direwolf;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Direwolf — the great wolves of the North, symbol of House Stark.
 *
 * <p>Behaviour summary:
 * <ul>
 *   <li>Neutral — attacks players only if provoked (hurt-by-target); otherwise
 *       hunts animals (cows, pigs, sheep) within follow range.</li>
 *   <li>Spawns in snowy taiga, taiga, and old-growth pine taiga biomes.</li>
 *   <li>Breeds with raw beef or porkchop.</li>
 *   <li>Pack hunters — alerts nearby direwolves when one is hurt.</li>
 * </ul>
 *
 * <p>Animation states driven by {@link net.got.entity.client.direwolf.GotDirewolfRenderer}:
 * idle, walk, run, attack, howl, swim.
 */
public class GotDirewolfEntity extends Animal {

    /** True when this direwolf is actively attacking a target. */
    public boolean isAttacking = false;

    public GotDirewolfEntity(EntityType<? extends GotDirewolfEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        isAttacking = this.getTarget() != null;
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    /**
     * Direwolves are apex predators of the North — significantly stronger than
     * a vanilla wolf, with high health, damage, and follow range for pack hunts.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, true));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // Target — hurt-by-target fires first (provocation), then hunts animals
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(GotDirewolfEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.animal.Cow.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.animal.Pig.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.animal.Sheep.class, true));
    }

    // ── Sounds ────────────────────────────────────────────────────────────────

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.BEEF) || stack.is(Items.PORKCHOP)
                || stack.is(Items.COOKED_BEEF) || stack.is(Items.COOKED_PORKCHOP)
                || stack.is(Items.MUTTON);
    }

    @Override
    public @Nullable GotDirewolfEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotDirewolfEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotDirewolfEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        // Spawns on grass, snow, or podzol (North / taiga environments)
        var groundBlock = level.getBlockState(pos.below()).getBlock();
        if (groundBlock != Blocks.GRASS_BLOCK
                && groundBlock != Blocks.SNOW_BLOCK
                && groundBlock != Blocks.PODZOL
                && groundBlock != Blocks.COARSE_DIRT) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(
                (EntityType<? extends Monster>) (EntityType<?>) type,
                (net.minecraft.world.level.ServerLevelAccessor) level,
                spawnType, pos, random);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}