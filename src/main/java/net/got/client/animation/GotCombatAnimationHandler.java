package net.got.client.animation;

import net.got.client.input.GotKeybinds;
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
     * Primary trigger for client-side combat animations (immediate, no round-trip).
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

        GotPlayerAnimator animator = GotPlayerAnimator.INSTANCE;

        // ── Tick the animator (handles combo firing, looping, etc.) ───────────
        animator.tick();

        LocalPlayer player = mc.player;

        // ── Block detection: dedicated BLOCK key (Q) OR right-click with sword ─
        // The block key lets players block without consuming a right-click use item.
        boolean blockKeyHeld = GotKeybinds.BLOCK.isDown();
        boolean usingItem    = player.isUsingItem()
                && player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        // Only activate block guard when holding a sword/shield-able weapon
        GotArmPose currentWeaponPose = GotWeaponPoseClassifier.of(player.getMainHandItem());
        boolean canBlock = currentWeaponPose == GotArmPose.SWORD
                        || currentWeaponPose == GotArmPose.GREATSWORD;

        boolean blocking = canBlock && (blockKeyHeld || usingItem);
        animator.setBlocking(blocking);

        // ── Base locomotion animation ─────────────────────────────────────────
        // Select the right base anim from GotPlayerBaseAnimations based on
        // player movement state so idle/walk/run play when not in combat.
        if (!player.onGround()) {
            animator.setBaseAnimation(GotPlayerBaseAnimations.FALLING);
        } else if (player.isCrouching()) {
            animator.setBaseAnimation(GotPlayerBaseAnimations.IDLE_SNEAK);
        } else {
            // Check movement speed to pick walk vs run vs idle
            double speedSq = player.getDeltaMovement().horizontalDistanceSqr();
            if (speedSq > 0.08) {
                animator.setBaseAnimation(GotPlayerBaseAnimations.RUNNING);
            } else if (speedSq > 0.001) {
                animator.setBaseAnimation(GotPlayerBaseAnimations.WALKING);
            } else {
                animator.setBaseAnimation(GotPlayerBaseAnimations.IDLE_STANDING);
            }
        }
    }

    private GotCombatAnimationHandler() {}
}
