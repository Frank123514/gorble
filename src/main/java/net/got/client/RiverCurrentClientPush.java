package net.got.client;

import net.got.event.RiverCurrentSystem;
import net.got.worldgen.RiverFlowMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Applies the river current to the local player.
 *
 * <p>{@link RiverCurrentSystem} pushes every other entity from the server
 * tick, which works for them because they're server-authoritative — the
 * server's position is what clients see. The player is the opposite: the
 * client simulates its own movement locally and reports the result to the
 * server, so a push applied only server-side to a {@code ServerPlayer}
 * never shows up — it gets overwritten the instant the next movement
 * packet arrives from a client that never saw it. This mirrors the exact
 * same push, but on the client tick for the local player, so it actually
 * feeds into the movement the player reports.
 *
 * <p>Gated on {@link Minecraft#isPaused()}: {@code ClientTickEvent.Post}
 * keeps firing every real-time frame even while the pause menu is open,
 * but the level itself stops ticking — without this guard the push kept
 * nudging the player's position every frame the menu was open, so
 * stepping away for a while and coming back would show them having
 * drifted downstream despite the game being "paused".
 *
 * <p><b>Boats:</b> the same client-authority problem applies one level
 * up. When the local player is piloting a boat, {@code
 * Entity#isControlledByLocalInstance()} is true for the <i>boat</i> too —
 * the client simulates the boat's physics locally for responsive
 * steering and reports its position back, so {@link RiverCurrentSystem}'s
 * server-side push on the boat gets overwritten the same way raw player
 * movement did. While riding, this pushes the vehicle instead of the
 * player (pushing a passenger's own delta movement does nothing — its
 * position is driven by the vehicle it's sitting in).
 */
public final class RiverCurrentClientPush {

    private RiverCurrentClientPush() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RiverCurrentClientPush::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.isPaused()) return; // level isn't ticking — don't keep pushing into the void

        LocalPlayer player = mc.player;
        if (player == null) return;

        Entity vehicle = player.getVehicle();
        if (vehicle != null) {
            push(vehicle);
            return; // riding — pushing the player itself would do nothing
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
        entity.hasImpulse = true;
    }
}
