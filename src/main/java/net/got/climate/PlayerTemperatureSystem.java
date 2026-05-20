package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.got.climate.SeasonCache;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages a per-player temperature value driven purely by the biome's
 * effective temperature at the player's position.
 *
 * <p>Season influence is already baked in: during winter, {@code BiomeMixin}
 * overrides {@code getHeightAdjustedTemperature} to return -1.0f for all
 * biomes, so no separate season multiplier is needed here.
 *
 * <h3>Temperature scale</h3>
 * A float in [0.0, 1.0]:
 * <ul>
 *   <li><b>1.0</b> — fully warm</li>
 *   <li><b>0.0</b> — dangerously frozen</li>
 * </ul>
 *
 * <h3>Biome temperature → player target mapping</h3>
 * <table>
 *   <tr><th>Biome temp</th><th>Player target</th></tr>
 *   <tr><td>≥ 0.7  (warm)      </td><td>0.85</td></tr>
 *   <tr><td>0.3–0.7 (temperate)</td><td>0.65</td></tr>
 *   <tr><td>0.0–0.3 (cold)     </td><td>0.45</td></tr>
 *   <tr><td>&lt; 0.0 (frozen)  </td><td>0.20</td></tr>
 * </table>
 *
 * <h3>Effects by band</h3>
 * <table>
 *   <tr><th>Band</th><th>Range</th><th>Effects</th></tr>
 *   <tr><td>Warm    </td><td>≥ 0.75   </td><td>None</td></tr>
 *   <tr><td>Chilly  </td><td>0.50–0.75</td><td>Slowness I</td></tr>
 *   <tr><td>Cold    </td><td>0.25–0.50</td><td>Slowness I + Mining Fatigue I</td></tr>
 *   <tr><td>Freezing</td><td>&lt; 0.25</td><td>All above + freeze damage</td></tr>
 * </table>
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class PlayerTemperatureSystem {

    // ── Public constants (used by HUD) ────────────────────────────────────────
    public static final float TEMP_MAX      = 1.0f;
    public static final float TEMP_MIN      = 0.0f;
    public static final float TEMP_WARM     = 0.75f;
    public static final float TEMP_COLD     = 0.50f;
    public static final float TEMP_FREEZING = 0.25f;

    // ── Drift constants ───────────────────────────────────────────────────────
    /** How far the player temperature moves toward the target per interval. */
    private static final float DRIFT_RATE_PER_TICK   = 0.04f;
    /** Each armor piece reduces cooling drain by this much. */
    private static final float ARMOUR_DRAIN_REDUCTION = 0.008f;
    /** Extra warming rate when directly next to a heat source. */
    private static final float HEAT_SOURCE_BONUS      = 0.06f;
    /** Tick interval between temperature updates (once per second). */
    private static final int   INTERVAL               = 20;
    /** Freeze damage applied per interval when in the Freezing band. */
    private static final float FREEZE_DAMAGE          = 0.5f;

    // ── Biome target temperatures ─────────────────────────────────────────────
    /** Biome effective temp ≥ 0.7 */
    private static final float BIOME_TARGET_WARM      = 0.85f;
    /** Biome effective temp 0.3–0.7 */
    private static final float BIOME_TARGET_TEMPERATE = 0.65f;
    /** Biome effective temp 0.0–0.3 */
    private static final float BIOME_TARGET_COLD      = 0.45f;
    /** Biome effective temp < 0.0 (also what BiomeMixin sets during winter) */
    private static final float BIOME_TARGET_FROZEN    = 0.20f;

    // ── Per-player storage ────────────────────────────────────────────────────
    private static final ConcurrentHashMap<UUID, Float> TEMPERATURES =
            new ConcurrentHashMap<>();

    public static float getTemperature(UUID id) {
        return TEMPERATURES.getOrDefault(id, TEMP_MAX);
    }

    public static void setTemperature(UUID id, float value) {
        TEMPERATURES.put(id, Math.max(TEMP_MIN, Math.min(TEMP_MAX, value)));
    }

    public static void removePlayer(UUID id) {
        TEMPERATURES.remove(id);
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.tickCount % INTERVAL != 0) return;

        UUID  id   = player.getUUID();
        float temp = getTemperature(id);

        float target = computeTarget(sp);
        temp = driftTowardTarget(temp, target, sp);

        setTemperature(id, temp);
        applyEffects(sp, temp);
    }

    // ── Target computation ────────────────────────────────────────────────────

    /**
     * Returns the temperature this player should drift toward.
     *
     * <p>Heat source / shelter → always TEMP_MAX.
     * Otherwise, reads the biome's height-adjusted temperature, which already
     * reflects the current season via {@code BiomeMixin}, and maps it to a
     * player target.
     */
    private static float computeTarget(ServerPlayer player) {
        if (!isOutdoors(player) || isNearHeatSource(player)) {
            return TEMP_MAX;
        }
        float biomeTemp = getEffectiveBiomeTemperature(player);
        return biomeBaseTarget(biomeTemp);
    }

    /**
     * Returns the biome's height-adjusted temperature at the player's position.
     * During winter this returns -1.0f for all biomes (set by BiomeMixin),
     * which maps to the FROZEN target automatically.
     */
    private static float getEffectiveBiomeTemperature(ServerPlayer player) {
        var   level = player.serverLevel();
        var   pos   = player.blockPosition();
        Biome biome = level.getBiome(pos).value();
        // getHeightAdjustedTemperature is private; BiomeMixin handles the override for
        // weather/snow. Here we replicate the same logic: winter = -1.0f, otherwise
        // use the public base temperature (altitude adjustment is minor for gameplay).
        if (SeasonCache.get().isWinter()) return -1.0f;
        return biome.getBaseTemperature();
    }

    /** Maps a biome's effective temperature to a player temperature target. */
    private static float biomeBaseTarget(float biomeTemp) {
        if (biomeTemp >= 0.7f) return BIOME_TARGET_WARM;
        if (biomeTemp >= 0.3f) return BIOME_TARGET_TEMPERATE;
        if (biomeTemp >= 0.0f) return BIOME_TARGET_COLD;
        return BIOME_TARGET_FROZEN;
    }

    // ── Drift ─────────────────────────────────────────────────────────────────

    private static float driftTowardTarget(float current, float target,
                                           ServerPlayer player) {
        float diff = target - current;
        float step;

        if (diff > 0f) {
            step = Math.min(diff, DRIFT_RATE_PER_TICK);
            if (isNearHeatSource(player)) step += HEAT_SOURCE_BONUS;
        } else {
            int   armour         = countArmorPieces(player);
            float drainReduction = armour * ARMOUR_DRAIN_REDUCTION;
            float effectiveDrift = DRIFT_RATE_PER_TICK - drainReduction;
            step = Math.max(diff, -Math.max(0f, effectiveDrift));
        }

        return Math.max(TEMP_MIN, Math.min(TEMP_MAX, current + step));
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    private static void applyEffects(ServerPlayer player, float temp) {
        if (temp >= TEMP_WARM) {
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
            return;
        }
        player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN, INTERVAL + 5, 0, true, false));
        if (temp < TEMP_COLD) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN, INTERVAL + 5, 0, true, false));
        }
        if (temp < TEMP_FREEZING) {
            player.hurt(player.damageSources().freeze(), FREEZE_DAMAGE);
        }
    }

    // ── Environment helpers ───────────────────────────────────────────────────

    private static boolean isOutdoors(ServerPlayer player) {
        return player.serverLevel().canSeeSky(player.blockPosition().above());
    }

    private static boolean isNearHeatSource(ServerPlayer player) {
        var level = player.serverLevel();
        var pos   = player.blockPosition();
        for (int dy = -1; dy <= 2; dy++) {
            var state = level.getBlockState(pos.below(dy));
            if (state.is(net.minecraft.tags.BlockTags.CAMPFIRES)
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.LAVA
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.FIRE
                    || state.getBlock() == net.minecraft.world.level.block.Blocks.SOUL_FIRE) {
                return true;
            }
        }
        return false;
    }

    private static int countArmorPieces(ServerPlayer player) {
        int n = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack s = player.getItemBySlot(slot);
            if (!s.isEmpty() && s.getItem() instanceof ArmorItem) n++;
        }
        return n;
    }

    // ── Public API (HUD / network) ────────────────────────────────────────────

    public static int getTemperaturePercent(UUID id) {
        return Math.round(getTemperature(id) * 100f);
    }

    public static TemperatureBand getBand(UUID id) {
        float t = getTemperature(id);
        if (t >= TEMP_WARM)     return TemperatureBand.WARM;
        if (t >= TEMP_COLD)     return TemperatureBand.CHILLY;
        if (t >= TEMP_FREEZING) return TemperatureBand.COLD;
        return TemperatureBand.FREEZING;
    }

    /** Named temperature bands, cold → warm. */
    public enum TemperatureBand {
        FREEZING("Freezing", 0xFF6688FF),
        COLD    ("Cold",     0xFFAABBFF),
        CHILLY  ("Chilly",   0xFFDDEEFF),
        WARM    ("Warm",     0xFFFFFFFF);

        public final String displayName;
        public final int    argb;

        TemperatureBand(String displayName, int argb) {
            this.displayName = displayName;
            this.argb        = argb;
        }
    }

    private PlayerTemperatureSystem() {}
}
