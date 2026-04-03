package net.got.entity.npc.smallfolk.northmen;

import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

/**
 * North Bowman — ranged archer of the North.
 *
 * <h3>Variant / gender layout (4 total)</h3>
 * <pre>
 *   variant 0-1  →  MALE    (north_bowman_male_1, north_bowman_male_2)
 *   variant 2-3  →  FEMALE  (north_bowman_female_1, north_bowman_female_2)
 * </pre>
 *
 * <h3>GeckoLib asset paths (create in Blockbench)</h3>
 * <pre>
 *   assets/got/geo/entity/northmen/north_bowman.geo.json
 *   assets/got/animations/entity/northmen/north_bowman.animation.json
 * </pre>
 */
public class NorthBowmanEntity extends SmallfolkEntity implements RangedAttackMob {

    // 2 male skins + 2 female skins
    private static final int VARIANT_COUNT = 4;

    private static final RawAnimation ANIM_WALK   = RawAnimation.begin().thenLoop("animation.north_bowman.walk");
    private static final RawAnimation ANIM_IDLE   = RawAnimation.begin().thenLoop("animation.north_bowman.idle");
    private static final RawAnimation ANIM_SHOOT  = RawAnimation.begin().thenPlay("animation.north_bowman.shoot");

    public NorthBowmanEntity(EntityType<? extends NorthBowmanEntity> type, Level level) {
        super(type, level);
    }

    // ── Variant count ─────────────────────────────────────────────────────────

    @Override
    public int getVariantCount() { return VARIANT_COUNT; }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,     20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE,  2.0)
                .add(Attributes.FOLLOW_RANGE,   40.0)
                .add(Attributes.ARMOR,          2.0);
    }

    // ── Goals ─────────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangedBowAttackGoal<>(this, 1.0, 24, 15.0f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    // ── Equipment ─────────────────────────────────────────────────────────────

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        if (random.nextFloat() < 0.3f)
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
    }

    // ── Ranged attack ─────────────────────────────────────────────────────────

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        ItemStack bowStack   = new ItemStack(Items.BOW);
        ItemStack arrowStack = new ItemStack(Items.ARROW);
        AbstractArrow arrow  = ProjectileUtil.getMobArrow(this, arrowStack, power, bowStack);

        double dx   = target.getX() - this.getX();
        double dy   = target.getY(0.3333) - arrow.getY();
        double dz   = target.getZ() - this.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);

        arrow.shoot(dx, dy + dist * 0.20f, dz, 1.6f,
                14 - this.level().getDifficulty().getId() * 4);
        this.level().addFreshEntity(arrow);
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    public static boolean checkSpawnRules(EntityType<NorthBowmanEntity> type,
                                          ServerLevelAccessor level,
                                          EntitySpawnReason spawnType,
                                          BlockPos pos,
                                          RandomSource random) {
        return defaultSpawnRules(type, level, spawnType, pos, random);
    }

    // ── GeckoLib animations ───────────────────────────────────────────────────

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 3, state -> {
            if (state.isMoving()) {
                return state.setAndContinue(ANIM_WALK);
            }
            return state.setAndContinue(ANIM_IDLE);
        }));
        // The "shoot" animation is triggered imperatively via triggerAnim() from a goal if needed.
        // For simple bow raising, the movement controller's IDLE state already looks natural.
    }
}