package net.got.client.animation;

import net.got.network.GotCombatAnimPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Client-side glue that:
 * 1. Receives {@link GotCombatAnimPayload} from the server and forwards it
 *    to {@link GotPlayerAnimator}.
 * 2. Tracks blocking state.
 * 3. Ticks the animator every client tick.
 * 4. Applies animation offsets in {@link RenderHandEvent} before the hand draws.
 *
 * <p>The payload handler is registered in {@link net.got.network.GotNetwork#register}.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class GotCombatAnimationHandler {

    // ── Payload reception ─────────────────────────────────────────────────────

    public static void onCombatAnimPayload(GotCombatAnimPayload payload) {
        GotArmPose[] poses = GotArmPose.values();
        int id = payload.poseId();
        if (id < 0 || id >= poses.length) return;
        GotPlayerAnimator.INSTANCE.triggerAttack(poses[id]);
    }

    // ── Arm render hook ───────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        // Only animate the main hand
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof LocalPlayer player)) return;

        // Apply current animation frame to the model before it renders
        GotPlayerAnimator.INSTANCE.applyToPlayer(player);

        // Track blocking
        boolean blocking = player.isUsingItem()
                && player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        GotPlayerAnimator.INSTANCE.setBlocking(blocking);
    }

    // ── Client tick ───────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;
        GotPlayerAnimator.INSTANCE.tick(0F);
    }

    private GotCombatAnimationHandler() {}
}