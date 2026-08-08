package net.got.event.entity.crow;


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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Three-Eyed Crow / Common Crow — the ravens and crows of Westeros.
 *
 * <p>In the lore, ravens carry messages and crows are omens of the Night's Watch
 * ("All crows are liars"). This entity represents the small black corvid found
 * perched on ruins, trees, and battlements across the Seven Kingdoms.
 *
 * <p>Behaviour summary:
 * <ul>
 *   <li>Passive — flees players when approached closely.</li>
 *   <li>Occasionally takes flight via {@link GotCrowFlyGoal}, gliding
 *       to new perch positions before landing.</li>
 *   <li>Breeds with wheat seeds or pumpkin seeds.</li>
 *   <li>Prefers forests, cold biomes, and elevated terrain.</li>
 * </ul>
 *
 * <p>Animation states driven by {@link net.got.event.entity.client.crow.GotCrowRenderer}:
 * idle, walk, fly, perch.
 */
public class GotCrowEntity extends Animal {

    /** Ticks spent continuously off the ground. Resets to 0 on landing. */
    public int airTicks = 0;

    public GotCrowEntity(EntityType<? extends GotCrowEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround() || this.isInWater()) {
            airTicks = 0;
        } else {
            airTicks++;
        }
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 18.0)
                .add(Attributes.FLYING_SPEED, 0.35);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.8));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new GotCrowFlyGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // ── Sounds ────────────────────────────────────────────────────────────────

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        // Use parrot squawk as closest vanilla equivalent to a crow caw
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    // ── Physics ───────────────────────────────────────────────────────────────

    /** Birds don't take fall damage. */
    @Override
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS) || stack.is(Items.PUMPKIN_SEEDS)
                || stack.is(Items.MELON_SEEDS);
    }

    @Override
    public @Nullable GotCrowEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotCrowEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotCrowEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        var groundBlock = level.getBlockState(pos.below()).getBlock();
        if (groundBlock != Blocks.GRASS_BLOCK
                && groundBlock != Blocks.PODZOL
                && groundBlock != Blocks.GRAVEL
                && groundBlock != Blocks.STONE
                && groundBlock != Blocks.SNOW_BLOCK
                && groundBlock != Blocks.COARSE_DIRT) {
            return false;
        }
        return net.got.event.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
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