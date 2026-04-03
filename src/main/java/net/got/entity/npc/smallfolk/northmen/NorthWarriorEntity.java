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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

/**
 * North Warrior — heavily-armed melee fighter of the North.
 *
 * <h3>Variant / gender layout (4 total)</h3>
 * <pre>
 *   variant 0-1  →  MALE    (north_warrior_male_1, north_warrior_male_2)
 *   variant 2-3  →  FEMALE  (north_warrior_female_1, north_warrior_female_2)
 * </pre>
 *
 * <h3>GeckoLib asset paths (create in Blockbench)</h3>
 * <pre>
 *   assets/got/geo/entity/northmen/north_warrior.geo.json
 *   assets/got/animations/entity/northmen/north_warrior.animation.json
 * </pre>
 */
public class NorthWarriorEntity extends SmallfolkEntity {

    // 2 male skins + 2 female skins
    private static final int VARIANT_COUNT = 4;

    private static final RawAnimation ANIM_WALK   = RawAnimation.begin().thenLoop("animation.north_warrior.walk");
    private static final RawAnimation ANIM_IDLE   = RawAnimation.begin().thenLoop("animation.north_warrior.idle");
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().thenPlay("animation.north_warrior.attack");

    /** Synced flag so the renderer can trigger the attack animation on the client. */
    private boolean isAttacking = false;

    public NorthWarriorEntity(EntityType<? extends NorthWarriorEntity> type, Level level) {
        super(type, level);
    }

    // ── Variant count ─────────────────────────────────────────────────────────

    @Override
    public int getVariantCount() { return VARIANT_COUNT; }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,           24.0)
                .add(Attributes.MOVEMENT_SPEED,       0.26)
                .add(Attributes.ATTACK_DAMAGE,        4.0)
                .add(Attributes.FOLLOW_RANGE,         16.0)
                .add(Attributes.ARMOR,                4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
    }

    // ── Goals ─────────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    // ── Equipment ─────────────────────────────────────────────────────────────

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(random.nextBoolean() ? Items.IRON_AXE : Items.IRON_SWORD));
        if (random.nextFloat() < 0.4f)
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        if (random.nextFloat() < 0.5f)
            this.setItemSlot(EquipmentSlot.HEAD,  new ItemStack(Items.CHAINMAIL_HELMET));
        if (random.nextFloat() < 0.35f)
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        if (random.nextFloat() < 0.25f)
            this.setItemSlot(EquipmentSlot.LEGS,  new ItemStack(Items.CHAINMAIL_LEGGINGS));
    }

    // ── Combat animation hook ─────────────────────────────────────────────────

    /** Called by {@link MeleeAttackGoal} via {@link #doHurtTarget}. */
    public void setIsAttacking(boolean attacking) { this.isAttacking = attacking; }
    public boolean isAttacking()                  { return isAttacking; }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    public static boolean checkSpawnRules(EntityType<NorthWarriorEntity> type,
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
            if (isAttacking) {
                return state.setAndContinue(ANIM_ATTACK);
            }
            if (state.isMoving()) {
                return state.setAndContinue(ANIM_WALK);
            }
            return state.setAndContinue(ANIM_IDLE);
        }));
    }
}