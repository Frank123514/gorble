package net.got.client.animation;

import net.got.network.GotCombatAnimPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class GotCombatAnimationHandler {

    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotCombatAnimHandler");

    /**
     * Called when the server confirms an attack-on-entity (via network packet).
     * Kept for cases where the server wants to drive the animation (e.g. NPC attacks).
     */
    public static void onCombatAnimPayload(GotCombatAnimPayload payload) {
        LOGGER.info("[GOT-ANIM] CLIENT received payload poseId={}", payload.poseId());
        GotArmPose[] poses = GotArmPose.values();
        int id = payload.poseId();
        if (id < 0 || id >= poses.length) return;
        GotPlayerAnimator.INSTANCE.triggerAttack(poses[id]);
    }

    /**
     * Fires every time the player presses the attack key — including air swings.
     * This is the primary trigger for client-side combat animations so they feel
     * immediate and responsive without a server round-trip.
     */
    @SubscribeEvent
    public static void onAttackInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        LocalPlayer player = mc.player;
        GotArmPose pose = GotWeaponPoseClassifier.of(player.getMainHandItem());
        if (pose == GotArmPose.NONE || pose == GotArmPose.BLOCK) return;

        LOGGER.debug("[GOT-ANIM] Attack input, pose={}", pose);
        GotPlayerAnimator.INSTANCE.triggerAttack(pose);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;

        GotPlayerAnimator.INSTANCE.tick();

        LocalPlayer player = mc.player;
        boolean blocking = player.isUsingItem()
                && player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        GotPlayerAnimator.INSTANCE.setBlocking(blocking);
    }

    private GotCombatAnimationHandler() {}
}
