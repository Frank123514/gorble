package net.got.entity.direwolf;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
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
import org.jetbrains.annotations.Nullable;

/**
 * GOT Direwolf — a great grey predator of the northern wilderness.
 *
 * <p>Aggressive by default: attacks players and monsters on sight.
 * Pups (babies) follow parents but do not attack until grown.
 *
 * <p>Howling state is triggered randomly when idle and angry, used
 * by the renderer to play the howl animation.
 *
 * <p>Animation states:
 * <ul>
 *   <li>{@code idle}    — breathing, ear flick, tail sway.</li>
 *   <li>{@code walk}    — diagonal trot.</li>
 *   <li>{@code run}     — bounding gallop with spine flex.</li>
 *   <li>{@code attack}  — lunge and jaw snap.</li>
 *   <li>{@code howl}    — head-up howl display.</li>
 * </ul>
 */
public class GotDirewolfEntity extends Animal {

    private boolean attacking = false;
    private boolean howling   = false;
    private int howlCooldown  = 0;

    public GotDirewolfEntity(EntityType<? extends GotDirewolfEntity> type, Level level) {
        super(type, level);
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true) {
            @Override
            public void start() {
                super.start();
                attacking = true;
                howling   = false;
            }
            @Override
            public void stop() {
                super.stop();
                attacking = false;
            }
        });
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // Retaliate and hunt
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, false));
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && !attacking) {
            if (howlCooldown > 0) {
                howlCooldown--;
                if (howlCooldown == 0) howling = false;
            } else if (!isMoving() && this.random.nextInt(600) == 0) {
                howling      = true;
                howlCooldown = 40; // 2 seconds
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
    }

    // ── State accessors ───────────────────────────────────────────────────────

    public boolean isAttacking() { return attacking; }
    public boolean isHowling()   { return howling; }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.BEEF) || stack.is(Items.PORKCHOP) || stack.is(Items.MUTTON);
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