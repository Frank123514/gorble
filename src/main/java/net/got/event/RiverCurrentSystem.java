package net.got.event;

import net.got.GotMod;
import net.got.worldgen.RiverFlowMap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = GotMod.MODID)
public final class RiverCurrentSystem {

    public static final double CURRENT_STRENGTH = 0.015;

    private RiverCurrentSystem() {}

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        applyCurrent(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) return;
        applyCurrent(entity);
    }

    private static void applyCurrent(Entity entity) {
        if (entity.level().isClientSide()) return;
        if (!isInOrOnRiverWater(entity)) return;

        RiverFlowMap.FlowVector flow =
                RiverFlowMap.flowAt(entity.getBlockX(), entity.getBlockZ());
        if (flow == null) return;

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.add(
                flow.dx() * CURRENT_STRENGTH,
                0.0,
                flow.dz() * CURRENT_STRENGTH));
        entity.hurtMarked = true;
        entity.needsSync = true;
    }

    public static boolean isInOrOnRiverWater(Entity entity) {
        if (entity.isInWater()) return true;
        BlockPos pos = entity.blockPosition();
        if (entity.level().getFluidState(pos).is(FluidTags.WATER)) return true;
        return entity.level().getFluidState(pos.below()).is(FluidTags.WATER);
    }
}