package net.got.event;

import net.got.GotMod;
import net.got.worldgen.RiverFlowMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Gives rivers a current: entities standing or swimming in river water get
 * a small per-tick push in the local downstream direction, computed by
 * {@link RiverFlowMap} from the biomemap's river network (source → ocean/
 * lake). Water outside a connected river (ocean, lake, ponds, isolated
 * puddles) has no current — {@link RiverFlowMap#flowAt} returns
 * {@code null} there and this system does nothing.
 *
 * <p>Players are pushed via {@link PlayerTickEvent.Post} specifically —
 * the same hook {@code PlayerThirstSystem}/{@code PlayerTemperatureSystem}
 * already rely on — rather than the more general entity hook, so player
 * push doesn't depend on assumptions about how {@code Player.tick()}
 * chains into {@code LivingEntity.tick()}. Other living entities (mobs,
 * animals) still get pushed via {@link EntityTickEvent.Post}; players are
 * explicitly skipped there so they aren't pushed twice.
 *
 * <p><b>Scope note:</b> non-living entities such as boats and dropped
 * items fire neither of these events and are not pushed by this system.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class RiverCurrentSystem {

    /** Velocity added per tick along the flow direction, in blocks/tick. */
    private static final double CURRENT_STRENGTH = 0.03;

    private RiverCurrentSystem() {}

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        applyCurrent(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) return; // players handled by onPlayerTick above
        applyCurrent(entity);
    }

    private static void applyCurrent(Entity entity) {
        if (entity.level().isClientSide()) return;
        if (!entity.isInWater()) return;

        RiverFlowMap.FlowVector flow =
                RiverFlowMap.flowAt(entity.getBlockX(), entity.getBlockZ());
        if (flow == null) return;

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.add(
                flow.dx() * CURRENT_STRENGTH,
                0.0,
                flow.dz() * CURRENT_STRENGTH));
        entity.hasImpulse = true;
    }
}