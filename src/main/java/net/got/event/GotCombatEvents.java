package net.got.event;

import net.got.client.animation.GotArmPose;
import net.got.client.animation.GotWeaponPoseClassifier;
import net.got.network.GotCombatAnimPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Listens for {@link AttackEntityEvent} on the server and dispatches a
 * {@link GotCombatAnimPayload} back to the attacking player so that their
 * client-side arm animator fires the correct weapon animation.
 *
 * <p>Using a server event (rather than a pure client swing event) ensures that
 * the animation only fires on confirmed hit attempts, giving it medieval-combat
 * weight rather than playing on every air-swing.
 */
@EventBusSubscriber(modid = "got")
public final class GotCombatEvents {

    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger("GotCombatEvents");

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        LOGGER.info("[GOT-ANIM] AttackEntityEvent fired by: {}", event.getEntity().getClass().getSimpleName());

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            LOGGER.info("[GOT-ANIM] Not a ServerPlayer, ignoring");
            return;
        }

        ItemStack held = player.getMainHandItem();
        LOGGER.info("[GOT-ANIM] Player {} attacking with: {}", player.getName().getString(), held);

        GotArmPose pose = GotWeaponPoseClassifier.of(held);
        LOGGER.info("[GOT-ANIM] Classified pose: {}", pose);

        if (pose == GotArmPose.NONE) {
            LOGGER.info("[GOT-ANIM] Pose is NONE, no animation sent");
            return;
        }

        LOGGER.info("[GOT-ANIM] Sending GotCombatAnimPayload poseId={} to {}", pose.ordinal(), player.getName().getString());
        PacketDistributor.sendToPlayer(player,
                new GotCombatAnimPayload(pose.ordinal()));
    }

    private GotCombatEvents() {}
}