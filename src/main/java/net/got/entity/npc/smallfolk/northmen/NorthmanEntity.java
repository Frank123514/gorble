package net.got.entity.npc.smallfolk.northmen;

import net.got.entity.npc.NpcGender;
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
import net.minecraft.world.entity.monster.Monster;
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
 * Northman — civilian smallfolk of the North.
 *
 * <h3>Variant / gender layout (8 total)</h3>
 * <pre>
 *   variant 0-3  →  MALE    (northman_male_1 … northman_male_4)
 *   variant 4-7  →  FEMALE  (northman_female_1 … northman_female_4)
 * </pre>
 *
 * <h3>GeckoLib asset paths (create in Blockbench)</h3>
 * <pre>
 *   assets/got/geo/entity/northmen/northman.geo.json
 *   assets/got/animations/entity/northmen/northman.animation.json
 * </pre>
 * Texture paths are handled by the renderer and follow the pattern above.
 */
public class NorthmanEntity extends SmallfolkEntity {

    // 4 male skins + 4 female skins
    private static final int VARIANT_COUNT = 8;

    private static final RawAnimation ANIM_WALK = RawAnimation.begin().thenLoop("animation.northman.walk");
    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().thenLoop("animation.northman.idle");

    public NorthmanEntity(EntityType<? extends NorthmanEntity> type, Level level) {
        super(type, level);
    }

    // ── Variant count ─────────────────────────────────────────────────────────

    @Override
    public int getVariantCount() { return VARIANT_COUNT; }

    // ── Attributes ────────────────────────────────────────────────────────────

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,     16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE,  2.0)
                .add(Attributes.FOLLOW_RANGE,   16.0);
    }

    // ── Goals ─────────────────────────────────────────────────────────────────

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Monster.class, 8.0f, 0.8, 1.33));
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
        if (random.nextFloat() < 0.2f) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(
                    random.nextBoolean() ? Items.WOODEN_AXE : Items.WOODEN_PICKAXE));
        }
    }

    // ── Spawn rules ───────────────────────────────────────────────────────────

    public static boolean checkSpawnRules(EntityType<NorthmanEntity> type,
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
    }
}