package net.got.climate;

import net.got.GotMod;
import net.got.worldgen.biome.GotBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages a per-player temperature value driven by the current GoT season
 * <em>and</em> the temperature of the GoT biome the player is standing in.
 *
 * <h3>Temperature scale</h3>
 * A float in [0.0, 1.0]:
 * <ul>
 *   <li><b>1.0</b> — fully warm</li>
 *   <li><b>0.0</b> — dangerously frozen</li>
 * </ul>
 *
 * <h3>How biome temperature feeds in</h3>
 * Each GoT biome has a Minecraft {@code temperature} value set in
 * {@link GotBiomes}. We map that value to a <em>target temperature</em> the
 * player wants to drift toward. A warm biome (temperature ≈ 0.8) buffers the
 * player well; a frozen biome (temperature ≈ −0.7) accelerates drain
 * regardless of armor.
 *
 * <table>
 *   <tr><th>biome temp</th><th>biome name examples</th><th>target player temp</th></tr>
 *   <tr><td>≥ 0.7</td><td>Wolfswood, Neck, North</td><td>0.85 (comfortable)</td></tr>
 *   <tr><td>0.3–0.7</td><td>Iron Hills, Ironwood, Barrowlands</td><td>0.65 (chilly)</td></tr>
 *   <tr><td>0.0–0.3</td><td>North Hills, North Mountains</td><td>0.45 (cold)</td></tr>
 *   <tr><td>&lt; 0.0</td><td>Frostfangs, Always Winter, Haunted Forest</td><td>0.20 (freezing)</td></tr>
 * </table>
 *
 * <h3>Season multiplier</h3>
 * The target is further scaled by the current season:
 * <ul>
 *   <li><b>Summer</b> — ×1.30 (adds warmth)</li>
 *   <li><b>Spring / Autumn</b> — ×1.00 (neutral)</li>
 *   <li><b>Winter</b> — ×0.55 (harshly reduces the target; even warm biomes become cold)</li>
 * </ul>
 * After applying the multiplier the target is clamped to [0.05, 1.0] so
 * players can never be completely safe outdoors in Winter, nor outright die
 * from standing in a warm biome in summer.
 *
 * <h3>Drift mechanics</h3>
 * Each second the player's temperature moves toward the biome target by a
 * fixed step ({@value #DRIFT_RATE_PER_TICK} per tick, applied every
 * {@value #INTERVAL} ticks). Armor slows drain (reduces negative drift) but
 * does not speed up recovery. Being indoors or near a heat source snaps the
 * target to {@link #TEMP_MAX} regardless of biome or season.
 *
 * <h3>Effects by band</h3>
 * <table>
 *   <tr><th>Band</th><th>Range</th><th>Effects</th></tr>
 *   <tr><td>Warm</td><td>≥ 0.75</td><td>None</td></tr>
 *   <tr><td>Chilly</td><td>0.50–0.75</td><td>Slowness I</td></tr>
 *   <tr><td>Cold</td><td>0.25–0.50</td><td>Slowness I + Mining Fatigue I</td></tr>
 *   <tr><td>Freezing</td><td>&lt; 0.25</td><td>All above + freeze damage</td></tr>
 * </table>
 *
 * <p>Replaces the old {@link ClimatePlayerTracker}. Remove that class's
 * {@code @EventBusSubscriber} annotation so it no longer fires.
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
    /**
     * How far the player temperature moves toward the target per application.
     * Applied every {@link #INTERVAL} ticks (once per second).
     * A value of 0.04 means 25 seconds to traverse the full 0→1 range.
     */
    private static final float DRIFT_RATE_PER_TICK = 0.04f;

    /** Armor reduces how fast temperature falls (only when temp > target). */
    private static final float ARMOUR_DRAIN_REDUCTION = 0.008f;

    /** How much faster you recover when directly beside fire/lava/campfire. */
    private static final float HEAT_SOURCE_BONUS = 0.06f;

    /** Tick interval between temperature updates. */
    private static final int INTERVAL = 20;

    /** Freeze damage applied per tick interval when in the Freezing band. */
    private static final float FREEZE_DAMAGE = 0.5f;

    // ── Season multipliers ────────────────────────────────────────────────────
    private static final float SEASON_MULT_SUMMER = 1.30f;
    private static final float SEASON_MULT_SPRING = 1.00f;
    private static final float SEASON_MULT_AUTUMN = 1.00f;
    private static final float SEASON_MULT_WINTER = 0.55f;

    // ── Biome target temperatures (base, before season multiplier) ────────────
    // Derived from GotBiomes biome temperature values:
    //  alwaysWinter   -0.50   → FROZEN
    //  frostfangs     -0.70   → FROZEN
    //  hauntedForest  -0.50   → FROZEN
    //  frozenLake      0.00   → COLD
    //  frozenRiver     0.00   → COLD
    //  northMountains -0.30   → FROZEN
    //  northHills      0.20   → COLD
    //  north           0.80   → WARM
    //  barrowlands     0.50   → TEMPERATE
    //  ironHills       0.20   → COLD
    //  ironwood        0.25   → COLD
    //  wolfswood       0.80   → WARM
    //  neck            0.80   → WARM
    //  creek           0.70   → WARM
    //  (and default vanilla/temperate biomes)

    /** Base player temp target for biomes with Minecraft temp ≥ 0.7 (warm). */
    private static final float BIOME_TARGET_WARM      = 0.85f;
    /** Base player temp target for biomes with Minecraft temp 0.3–0.7 (temperate). */
    private static final float BIOME_TARGET_TEMPERATE = 0.65f;
    /** Base player temp target for biomes with Minecraft temp 0.0–0.3 (cold). */
    private static final float BIOME_TARGET_COLD      = 0.45f;
    /** Base player temp target for biomes with Minecraft temp &lt; 0.0 (frozen). */
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
     * Returns the temperature value this player should drift toward.
     * Factors: biome base temperature × season multiplier, clamped to [0.05, 1.0].
     * Overrides: indoors or near heat → always TEMP_MAX.
     */
    private static float computeTarget(ServerPlayer player) {
        // Heat source / shelter overrides everything
        if (!isOutdoors(player) || isNearHeatSource(player)) {
            return TEMP_MAX;
        }

        // Biome base target
        float biomeTemp = getBiomeMcTemperature(player);
        float baseTarget = biomeBaseTarget(biomeTemp);

        // Season multiplier
        float seasonMult = switch (SeasonManager.getCurrentSeason()) {
            case SUMMER -> SEASON_MULT_SUMMER;
            case SPRING -> SEASON_MULT_SPRING;
            case AUTUMN -> SEASON_MULT_AUTUMN;
            case WINTER -> SEASON_MULT_WINTER;
        };

        float target = baseTarget * seasonMult;
        // Clamp: never 100% safe outdoors in deep winter, never insta-kill in summer
        return Math.max(0.05f, Math.min(TEMP_MAX, target));
    }

    /**
     * Maps the biome's Minecraft temperature float to a base player-temperature target.
     */
    private static float biomeBaseTarget(float mcTemp) {
        if (mcTemp >= 0.7f)  return BIOME_TARGET_WARM;
        if (mcTemp >= 0.3f)  return BIOME_TARGET_TEMPERATE;
        if (mcTemp >= 0.0f)  return BIOME_TARGET_COLD;
        return BIOME_TARGET_FROZEN;
    }

    /**
     * Returns the Minecraft temperature of the biome at the player's feet.
     * Uses the position-adjusted value so altitude affects temperature
     * (vanilla reduces temp by 1/600 per block above sea level).
     */
    private static float getBiomeMcTemperature(ServerPlayer player) {
        var level  = player.serverLevel();
        var pos    = player.blockPosition();
        Biome biome = level.getBiome(pos).value();
        return biome.getBaseTemperature();
    }

    // ── Drift ─────────────────────────────────────────────────────────────────

    private static float driftTowardTarget(float current, float target,
                                           ServerPlayer player) {
        float diff = target - current;
        float step;

        if (diff > 0f) {
            // Warming up — full drift rate
            step = Math.min(diff, DRIFT_RATE_PER_TICK);
            // Extra boost if directly next to a heat source
            if (isNearHeatSource(player)) step += HEAT_SOURCE_BONUS;
        } else {
            // Cooling down — armor slows the drain
            int   armour        = countArmorPieces(player);
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
        // Chilly+: Slowness I
        player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN, INTERVAL + 5, 0, true, false));
        // Cold+: Mining Fatigue I
        if (temp < TEMP_COLD) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN, INTERVAL + 5, 0, true, false));
        }
        // Freezing: damage
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
        /** ARGB for HUD color. */
        public final int argb;

        TemperatureBand(String displayName, int argb) {
            this.displayName = displayName;
            this.argb        = argb;
        }
    }

    private PlayerTemperatureSystem() {}
}