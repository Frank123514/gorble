package net.got.event.entity.mammoth;

import net.got.event.entity.giant.GotGiantEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * GOT Mammoth — a great woolly mammoth of the lands beyond the Wall.
 *
 * <p>Passive unless provoked. When angered it charges relentlessly, dealing
 * heavy damage. Mammoth calves follow their parents.
 *
 * <p>Giants can ride mammoths. A giant within {@value #MOUNT_SEEK_RADIUS} blocks
 * will seek an unmounted mammoth and climb aboard. The giant is seated high on
 * the mammoth's back; it directs movement normally once mounted.
 *
 * <p>Animation states:
 * <ul>
 *   <li>{@code idle}   — breathing sway, slow trunk curl.</li>
 *   <li>{@code walk}   — heavy plodding walk with shoulder roll.</li>
 *   <li>{@code run}    — lumbering charge with trunk raised.</li>
 *   <li>{@code attack} — head-and-tusk lunge on melee hit.</li>
 *   <li>{@code death}  — topple to the side on death.</li>
 * </ul>
 */
public class GotMammothEntity extends Animal {

    // ── Constants ─────────────────────────────────────────────────────────────

    /** How far (blocks) a giant will search for a mammoth to mount. */
    public static final double MOUNT_SEEK_RADIUS = 20.0;

    /**
     * Y-offset (in scaled world units) of the rider seat above the mammoth's
     * origin. The mammoth is rendered at 2.2× scale; the back is ~2.2 blocks
     * above origin in model space, so ≈ 2.2 * 2.2 ≈ 4.8 world units. Tune
     * this if the giant floats or clips through the body.
     */
    private static final float RIDER_Y_OFFSET = 6.0F;

    // ── Synced data ───────────────────────────────────────────────────────────

    private static final EntityDataAccessor<Boolean> DATA_ANGRY =
            SynchedEntityData.defineId(GotMammothEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ATTACKING =
            SynchedEntityData.defineId(GotMammothEntity.class, EntityDataSerializers.BOOLEAN);

    /** Ticks remaining in the attack animation hold after landing a hit. */
    private int attackAnimTicks = 0;

    public GotMammothEntity(EntityType<? extends GotMammothEntity> type, Level level) {
        super(type, level);
    }

    // ── Synced data ───────────────────────────────────────────────────────────

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ANGRY,     false);
        builder.define(DATA_ATTACKING, false);
    }

    private void setAngry(boolean value)    { this.entityData.set(DATA_ANGRY,     value); }
    private void setAttacking(boolean value){ this.entityData.set(DATA_ATTACKING, value); }

    public boolean isAngry()     { return this.entityData.get(DATA_ANGRY); }
    public boolean isAttacking() { return this.entityData.get(DATA_ATTACKING); }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH,          150.0)  // titanic resilience
                .add(Attributes.MOVEMENT_SPEED,       0.18)  // slower — more weight
                .add(Attributes.ATTACK_DAMAGE,        20.0)  // devastating charge
                .add(Attributes.FOLLOW_RANGE,         28.0)
                .add(Attributes.KNOCKBACK_RESISTANCE,  1.0); // virtually unmovable
    }

    // ── AI goals ──────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            public void start() {
                super.start();
            }
            @Override
            public void tick() {
                super.tick();
            }
            @Override
            public void stop() {
                super.stop();
                if (attackAnimTicks <= 0) {
                    setAttacking(false);
                }
            }
        });
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return isAngry() && super.canUse();
            }
        });
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (attackAnimTicks > 0) {
                attackAnimTicks--;
                if (attackAnimTicks == 0) {
                    setAttacking(false);
                }
            }
        }
    }

    // ── Combat ────────────────────────────────────────────────────────────────

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean result = super.doHurtTarget(level, target);
        if (result) {
            attackAnimTicks = 20; // hold anim for 1 s after the hit lands
            setAttacking(true);
        }
        return result;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        super.hurtServer(level, source, amount);
        setAngry(true);
        return false;
    }

    // ── Rider / passenger support ─────────────────────────────────────────────

    /**
     * Returns true when a giant is currently riding this mammoth.
     * Checked by the renderer to tweak animations if needed.
     */
    public boolean hasGiantRider() {
        return this.getFirstPassenger() instanceof GotGiantEntity;
    }

    /**
     * Seat position for passengers.  The Vec3 is in the mammoth's <em>local</em>
     * (un-scaled) space; Minecraft multiplies it by the entity's scale when
     * placing the rider, so we divide by the render scale to get the right
     * world-space height.
     *
     * <p>Render scale is 2.2 (see {@code GotMammothRenderer#scale}).
     * We want the rider to sit ~6.0 world-blocks above the mammoth's feet,
     * so local Y = 6.0 / 2.2 ≈ 2.73.
     */
    @Override
    public Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        // Forward offset keeps the giant seated toward the shoulders, not the rump
        return new Vec3(0.0, RIDER_Y_OFFSET / 2.2F, -0.3);
    }

    /**
     * Only giants may ride mammoths.
     */
    @Override
    public boolean canBeRiddenUnderFluidType(net.neoforged.neoforge.fluids.FluidType type, Entity rider) {
        return false; // don't allow riding in water/lava
    }

    // ── Breeding ──────────────────────────────────────────────────────────────

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.HAY_BLOCK);
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