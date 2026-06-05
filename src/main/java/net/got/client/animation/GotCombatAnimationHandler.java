package net.got.client.animation;

import net.got.network.GotCombatAnimPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Handles the non-render side: receiving payloads, ticking the animator,
 * and tracking blocking state.
 *
 * The actual bone mutation now happens inside PlayerRendererMixin, which
 * injects after vanilla's setupAnim() so our transforms aren't overwritten.
 */
@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class GotCombatAnimationHandler {

    public static void onCombatAnimPayload(GotCombatAnimPayload payload) {
        GotArmPose[] poses = GotArmPose.values();
        int id = payload.poseId();
        if (id < 0 || id >= poses.length) return;
        GotPlayerAnimator.INSTANCE.triggerAttack(poses[id]);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;

        LocalPlayer player = mc.player;
        GotPlayerAnimator.INSTANCE.tick(0F);

        boolean blocking = player.isUsingItem()
                && player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        GotPlayerAnimator.INSTANCE.setBlocking(blocking);
    }

    private GotCombatAnimationHandler() {}
}
