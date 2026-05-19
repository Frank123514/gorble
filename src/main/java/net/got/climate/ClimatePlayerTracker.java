package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Applies season-based effects to players each second.
 *
 * <h3>Winter effects</h3>
 * <ul>
 *   <li>Passive cold damage while outdoors and not near a heat source.</li>
 *   <li>Slowness I while outdoors — the biting wind slows travel.</li>
 *   <li>Both effects are reduced by the number of armour pieces worn.</li>
 * </ul>
 *
 * <p>No effects are applied outside of Winter. Season-change announcements
 * are handled by {@link SeasonManager} itself.
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class ClimatePlayerTracker {

    private static final int   TICK_INTERVAL          = 20;
    private static final float BASE_WINTER_COLD_DAMAGE = 0.5f;
    private static final float ARMOUR_COLD_REDUCTION   = 0.20f;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (player.tickCount % TICK_INTERVAL != 0) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (player.isCreative() || player.isSpectator()) return;

        if (SeasonManager.getCurrentSeason().isWinter()) {
            applyWinterEffects(serverPlayer);
        }
    }

    private static void applyWinterEffects(ServerPlayer player) {
        if (!isOutdoors(player)) return;
        if (isNearHeatSource(player)) return;

        int armorPieces = countArmorPieces(player);

        // Cold damage
        if (BASE_WINTER_COLD_DAMAGE > 0f) {
            float protection = armorPieces * ARMOUR_COLD_REDUCTION;
            float coldDmg = BASE_WINTER_COLD_DAMAGE * (1f - protection);
            if (coldDmg > 0f) {
                player.hurt(player.damageSources().freeze(), coldDmg);
            }
        }

        // Slowness I — less armour = more wind chill
        if (armorPieces < 4) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN, TICK_INTERVAL + 5, 0, true, false));
        }
    }

    private static boolean isOutdoors(ServerPlayer player) {
        return player.serverLevel().canSeeSky(player.blockPosition().above());
    }

    private static boolean isNearHeatSource(ServerPlayer player) {
        var level = player.serverLevel();
        var pos   = player.blockPosition();
        for (int dy = -1; dy <= 1; dy++) {
            var state = level.getBlockState(pos.below(dy));
            if (state.is(net.minecraft.tags.BlockTags.CAMPFIRES)
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.LAVA
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.FIRE) {
                return true;
            }
        }
        return false;
    }

    private static int countArmorPieces(ServerPlayer player) {
        int count = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem) count++;
        }
        return count;
    }

    private ClimatePlayerTracker() {}
}
