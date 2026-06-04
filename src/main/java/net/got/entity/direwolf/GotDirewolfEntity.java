package net.got.entity.direwolf;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
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
 * <p>Can be tamed using bones. Once tamed, right-click to toggle sit/stand.
 * Tamed direwolves will follow their owner and attack threats.
 */
public class GotDirewolfEntity extends TamableAnimal {

    private boolean attacking = false;
    private boolean howling   = false;
    private int howlCooldown  = 0;

    public GotDirewolfEntity(EntityType<? extends GotDirewolfEntity> type, Level level) {
        super(type, level);
    }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
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

        // Tamed behavior: sit, follow owner
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.2, 8.0F, 2.0F));

        // Attack goal (only when not sitting)
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.4, true) {
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
            @Override
            public boolean canUse() {
                return !isInSittingPose() && super.canUse();
            }
        });

        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        // Targeting: owner protection + retaliation
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));

        // Only attack players/monsters if NOT tamed
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return !isTame() && super.canUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, false) {
            @Override
            public boolean canUse() {
                return !isTame() && super.canUse();
            }
        });
    }

    // ── Taming & Interaction ──────────────────────────────────────────────────

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // === TAMING (untamed) ===
        if (!this.isTame()) {
            if (itemstack.is(Items.BONE)) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!this.level().isClientSide) {
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.setTarget(null);
                        this.level().broadcastEntityEvent(this, (byte) 7); // heart particles
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6); // smoke particles
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // === TAMED ===
        if (this.isOwnedBy(player)) {

            // HEALING: holding food and health is low
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal(4.0F);
                return InteractionResult.SUCCESS;
            }

            // BREEDING: holding food and can breed
            if (this.isFood(itemstack) && this.getAge() == 0 && this.canFallInLove()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }

            // TOGGLE SIT: empty hand or any non-food item
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && !attacking && !this.isInSittingPose()) {
            if (howlCooldown > 0) {
                howlCooldown--;
                if (howlCooldown == 0) howling = false;
            } else if (!isMoving() && this.random.nextInt(600) == 0) {
                howling      = true;
                howlCooldown = 40;
            }
        }
        if (this.isInSittingPose()) {
            howling = false;
            howlCooldown = 0;
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
        return stack.is(Items.BEEF) || stack.is(Items.PORKCHOP)
                || stack.is(Items.MUTTON) || stack.is(Items.ROTTEN_FLESH);
    }

    @Override
    public @Nullable GotDirewolfEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        GotDirewolfEntity baby = (GotDirewolfEntity) getType().create(level, EntitySpawnReason.BREEDING);
        if (baby != null && mate instanceof TamableAnimal tamedMate && tamedMate.isTame()) {
            baby.setOwnerUUID(tamedMate.getOwnerUUID());
            baby.setTame(true, true);
        }
        return baby;
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