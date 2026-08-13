package net.got.client;

import net.got.event.RiverCurrentSystem;
import net.got.worldgen.RiverFlowMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class RiverCurrentClientPush {

    private RiverCurrentClientPush() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RiverCurrentClientPush::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.isPaused()) return;

        LocalPlayer player = mc.player;
        if (player == null) return;

        Entity vehicle = player.getVehicle();
        if (vehicle != null) {
            push(vehicle);
            return;
        }

        if (!player.isInWater()) return;
        push(player);
    }

    private static void push(Entity entity) {
        if (!RiverCurrentSystem.isInOrOnRiverWater(entity)) return;

        RiverFlowMap.FlowVector flow =
                RiverFlowMap.flowAt(entity.getBlockX(), entity.getBlockZ());
        if (flow == null) return;

        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.add(
                flow.dx() * RiverCurrentSystem.CURRENT_STRENGTH,
                0.0,
                flow.dz() * RiverCurrentSystem.CURRENT_STRENGTH));
        entity.hurtMarked = true;
        entity.needsSync = true;
    }
}