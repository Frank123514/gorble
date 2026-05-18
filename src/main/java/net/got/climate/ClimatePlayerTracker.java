package net.got.climate;

import net.got.GotMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Server-side tick handler that applies climate effects to players.
 *
 * <h3>What this does</h3>
 * <ul>
 *   <li>Tracks each player's current {@link ClimateZone} and fires zone-change
 *       notifications when they cross a boundary.
 *   <li>Applies passive cold / heat damage in extreme zones, reduced by armour.
 *   <li>Applies {@link MobEffects#MOVEMENT_SLOWDOWN slowness} in blizzard (Polar) conditions.
 *   <li>Persists the last-known zone in player NBT so the check survives log-out.
 * </ul>
 *
 * <h3>Tuning</h3>
 * <ul>
 *   <li>{@link #TICK_INTERVAL} — how often (in game ticks) climate checks run per player.
 *   <li>{@link #ARMOUR_COLD_REDUCTION} — fraction of cold damage negated per armour piece.
 *   <li>{@link #ARMOUR_HEAT_REDUCTION} — fraction of heat damage negated per armour piece.
 * </ul>
 *
 * <p>This class is registered on the NeoForge game-event bus by
 * {@link net.got.GotMod}'s constructor via {@code NeoForge.EVENT_BUS.register(this)}.
 * No separate registration call is needed.
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class ClimatePlayerTracker {

    // ── Tuning ────────────────────────────────────────────────────────────────

    /** Run climate logic every N ticks (20 = once per second). */
    private static final int TICK_INTERVAL = 20;

    /** Fraction of cold damage blocked per armour piece worn (4 pieces max → 0.80 max block). */
    private static final float ARMOUR_COLD_REDUCTION = 0.20f;

    /** Fraction of heat damage blocked per armour piece worn (4 pieces max). */
    private static final float ARMOUR_HEAT_REDUCTION = 0.10f;

    /** NBT key for persisting the player's last-known zone name across log-outs. */
    private static final String NBT_LAST_ZONE = "got.climate.lastZone";

    // ── Tick handler ──────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Only run on the server, and only every TICK_INTERVAL ticks
        if (player.level().isClientSide()) return;
        if (player.tickCount % TICK_INTERVAL != 0) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (player.isCreative() || player.isSpectator()) return;

        ClimateFeatures features = ClimateSystem.getFeaturesFor(serverPlayer);
        checkZoneChange(serverPlayer, features);
        applyClimateEffects(serverPlayer, features);
    }

    // ── Zone-change detection ─────────────────────────────────────────────────

    private static void checkZoneChange(ServerPlayer player, ClimateFeatures features) {
        String lastZoneName = getLastZoneName(player);
        String currentZoneName = features.zone.name();

        if (!currentZoneName.equals(lastZoneName)) {
            onZoneChanged(player, features, lastZoneName);
            saveLastZoneName(player, currentZoneName);
        }
    }

    /**
     * Called when a player moves into a new climate zone.
     * Sends a chat notification and logs at INFO level.
     *
     * <p>Override or extend this method to hook additional systems
     * (e.g. achievements, faction-specific debuffs, sound events).
     */
    private static void onZoneChanged(ServerPlayer player, ClimateFeatures features,
                                      String previousZoneName) {
        ClimateZone zone = features.zone;

        // Build a contextual flavour message
        String msg = buildZoneEntryMessage(zone);
        player.sendSystemMessage(Component.literal(msg));

        GotMod.LOGGER.debug(
                "[Climate] {} moved from zone {} → {} at z={}",
                player.getName().getString(), previousZoneName, zone.name(), features.worldZ
        );
    }

    private static String buildZoneEntryMessage(ClimateZone zone) {
        return switch (zone) {
            case POLAR ->
                    "The cold bites deep. You have entered the Land of Always Winter.";
            case SUBARCTIC ->
                    "A biting wind descends from the north. The Wall looms somewhere ahead.";
            case TEMPERATE_NORTH ->
                    "The air turns crisp. You are in the northern reaches of Westeros.";
            case RIVERLANDS ->
                    "Rolling hills and rushing rivers surround you. You have entered the Riverlands.";
            case TEMPERATE_SOUTH ->
                    "The climate softens. The great houses of the south hold sway here.";
            case SUBTROPICAL ->
                    "Warmth settles over you. The fertile heartlands stretch out before you.";
            case TROPICAL ->
                    "The heat is relentless. You have entered the sun-scorched south.";
        };
    }

    // ── Climate effect application ────────────────────────────────────────────

    private static void applyClimateEffects(ServerPlayer player, ClimateFeatures features) {

        // ── Cold damage ───────────────────────────────────────────────────────
        if (features.passiveColdDamageRate > 0f) {
            float coldDmg = features.passiveColdDamageRate;

            // Reduce by armour (each piece worn reduces damage by ARMOUR_COLD_REDUCTION)
            int armorPieces = countArmorPieces(player);
            float protection = armorPieces * ARMOUR_COLD_REDUCTION;
            coldDmg = Math.max(0f, coldDmg * (1f - protection));

            // Standing near fire / in lava cancels cold damage
            if (!player.isOnFire() && !player.isInLava() && !isNearHeatSource(player)) {
                player.hurt(player.damageSources().freeze(), coldDmg);
            }

            // Blizzard slowness in the Polar zone
            if (features.zone == ClimateZone.POLAR) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN, TICK_INTERVAL + 5, 0, true, false));
            }
        }

        // ── Heat damage ───────────────────────────────────────────────────────
        if (features.passiveHeatDamageRate > 0f) {
            float heatDmg = features.passiveHeatDamageRate;

            // Armour makes heat worse (insulation) — light/no armour is better
            int armorPieces = countArmorPieces(player);
            float heatPenalty = armorPieces * ARMOUR_HEAT_REDUCTION;
            heatDmg = heatDmg * (1f + heatPenalty);

            // Being in water or rain cancels heat damage
            if (!player.isInWater() && !player.isInWaterOrRain() && !isInShade(player)) {
                player.hurt(player.damageSources().hotFloor(), heatDmg);
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Counts the number of armour slots currently filled. */
    private static int countArmorPieces(ServerPlayer player) {
        int count = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof ArmorItem) {
                count++;
            }
        }
        return count;
    }

    /**
     * Rough check for a nearby heat source — campfire, lava, etc.
     * Checks the block at and slightly below the player's feet.
     */
    private static boolean isNearHeatSource(ServerPlayer player) {
        var level = player.serverLevel();
        var pos   = player.blockPosition();
        // Check a small vertical column below and at the player
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

    /**
     * Simple shade check: returns true if the player has solid blocks above them
     * (within 8 blocks), providing shelter from the sun's heat.
     */
    private static boolean isInShade(ServerPlayer player) {
        var level = player.serverLevel();
        var pos   = player.blockPosition();
        for (int dy = 1; dy <= 8; dy++) {
            if (level.getBlockState(pos.above(dy)).isSolid()) {
                return true;
            }
        }
        return false;
    }

    // ── NBT persistence ───────────────────────────────────────────────────────

    private static String getLastZoneName(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.contains(NBT_LAST_ZONE) ? data.getString(NBT_LAST_ZONE) : "";
    }

    private static void saveLastZoneName(ServerPlayer player, String zoneName) {
        player.getPersistentData().putString(NBT_LAST_ZONE, zoneName);
    }

    // ── Clone (death / dimension change) — copy NBT ───────────────────────────

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        CompoundTag oldData = event.getOriginal().getPersistentData();
        if (oldData.contains(NBT_LAST_ZONE)) {
            player.getPersistentData().putString(NBT_LAST_ZONE, oldData.getString(NBT_LAST_ZONE));
        }
    }

    // ── No instances ─────────────────────────────────────────────────────────

    private ClimatePlayerTracker() {}
}