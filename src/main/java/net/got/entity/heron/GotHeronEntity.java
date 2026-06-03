package net.got.entity.heron;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
 * GOT Heron — a great blue heron that wades in rivers and coastal shallows.
 *
 * <p>Behaviour summary:
 * <ul>
 *   <li>Passive — flees players when approached closely.</li>
 *   <li>Prefers water-adjacent biomes; spawns near rivers and beaches.</li>
 *   <li>Breeds with raw fish (cod or salmon).</li>
 *   <li>Occasionally takes flight via {@link GotHeronFlyGoal}, gliding to a new
 *       spot before landing.</li>
 * </ul>
 *
 * <p>Animation states driven by {@link net.got.entity.client.heron.GotHeronRenderer}:
 * idle, walk, fly (airborne), wade (water + moving).
 */
public class GotHeronEntity extends Animal {

    public GotHeronEntity(EntityType<? extends GotHeronEntity> type, Level level) {
        super(type, level);
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.FLYING_SPEED, 0.28);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.6));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new GotHeronFlyGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.001F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    // ── Physics ───────────────────────────────────────────────────────────────

    /** Birds don't take fall damage. */
    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COD) || stack.is(Items.SALMON);
    }

    @Override
    public @Nullable GotHeronEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return (GotHeronEntity) getType().create(level, EntitySpawnReason.BREEDING);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static boolean checkSpawnRules(EntityType<GotHeronEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        var groundBlock = level.getBlockState(pos.below()).getBlock();
        if (groundBlock != Blocks.GRASS_BLOCK
                && groundBlock != Blocks.SAND
                && groundBlock != Blocks.GRAVEL) {
            return false;
        }
        for (BlockPos neighbour : BlockPos.withinManhattan(pos, 4, 1, 4)) {
            if (level.getBlockState(neighbour).is(Blocks.WATER)) {
                return net.got.entity.npc.smallfolk.SmallfolkEntity.defaultSpawnRules(
                        (EntityType) type, level, spawnType, pos, random);
            }
        }
        return false;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                                  DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnType,
                                                  @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}
