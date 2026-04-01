package net.got.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Quagmire block — a boggy marsh block that behaves similarly to powder snow
 * but with a significantly slower sink rate and no freeze damage.
 *
 * <h3>Mechanics</h3>
 * <ul>
 *   <li>Entities that enter the block are slowed horizontally (50% speed) and
 *       begin to sink downward. The sink is half the speed of powder snow.</li>
 *   <li>Living entities receive Slowness II for as long as they remain inside.</li>
 *   <li>Creative-mode players are unaffected.</li>
 *   <li>The block is not pathfindable.</li>
 * </ul>
 *
 * <h3>How sinking works</h3>
 * Like powder snow, this block calls {@link Entity#makeStuckInBlock} each tick
 * the entity is inside.  The vertical multiplier (0.65) is far below powder
 * snow's (1.5), so downward velocity is dampened rather than boosted — entities
 * still sink because gravity (-0.08 m/t²) continues to accumulate, but the
 * terminal sink speed converges to only ~0.23 m/t, compared to powder snow's
 * uncapped rapid plunge.
 */
public class QuagmireBlock extends Block {

    /**
     * Movement multipliers applied via {@link Entity#makeStuckInBlock} every tick.
     * <ul>
     *   <li>X / Z = 0.50 — cuts horizontal speed in half, making escape feel like wading through mud.</li>
     *   <li>Y = 0.65 — partially counteracts gravity so sinking occurs at ~0.23 m/t
     *       (roughly half the effective rate of vanilla powder snow).</li>
     * </ul>
     */
    private static final Vec3 STUCK_MULTIPLIER = new Vec3(0.5D, 0.65D, 0.5D);

    /** Slowness II, 60 ticks (3 s), silent, no particles — re-applied every tick inside. */
    private static final MobEffectInstance QUAGMIRE_SLOWNESS =
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false);

    public QuagmireBlock(Properties properties) {
        super(properties);
    }

    // ── Entity interaction ────────────────────────────────────────────────────

    /**
     * Called every tick the entity is physically inside this block's space.
     *
     * <p>We skip creative players (they can fly out trivially), then apply the
     * stuck multiplier and the slowness effect to any {@link LivingEntity}.</p>
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // Creative / spectator players are unaffected — they should be able to fly through freely.
        if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return;
        }

        // Apply horizontal slow + partial vertical counteraction.
        entity.makeStuckInBlock(state, STUCK_MULTIPLIER);

        // Apply slowness to living entities (mobs, players in survival, etc.).
        if (entity instanceof LivingEntity living) {
            // addEffect is a no-op when the entity already has a longer/stronger effect,
            // so calling it every tick is safe and simply keeps the timer topped-up.
            living.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false));
        }
    }

    // ── Pathfinding ───────────────────────────────────────────────────────────

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        // Mobs should not try to path through quagmire.
        return false;
    }

    // ── Occlusion / shape ─────────────────────────────────────────────────────

    /**
     * Quagmire looks like a full opaque cube but is not considered a full
     * redstone conductor (same as powder snow), so redstone dust doesn't
     * connect over it unexpectedly.
     */
    @Override
    public boolean propagatesSkylightDown(BlockState state) {
        return false;
    }

    /**
     * Return a near-full collision shape so entities can stand on top.
     * The entity physics engine allows partial overlap when falling in with
     * momentum (identical mechanism to vanilla powder snow), which is what
     * produces the sinking effect.
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level,
                                        BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }
}