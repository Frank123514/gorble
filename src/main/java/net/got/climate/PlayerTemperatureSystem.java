package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Body temperature system.
 *
 * <h3>Body temperature scale: [0.0, 1.0]</h3>
 * <ul>
 *   <li>1.0 = dangerously overheated</li>
 *   <li>~0.5 = comfortable/neutral</li>
 *   <li>0.0 = dangerously frozen</li>
 * </ul>
 *
 * <h3>Environmental target</h3>
 * Effective biome temp (biome + season offset) is mapped linearly to a body
 * temp target. Comfortable neutral is biome temp ~0.65 (temperate plains) → 0.5.
 *
 * <h3>Wet modifier</h3>
 * Being submerged in water or standing in rain while cold drives body temp down
 * much faster (hypothermia). Being wet while already overheated slightly cools you.
 *
 * <h3>Effects</h3>
 * <table>
 *   <tr><th>Band</th>         <th>Range</th>        <th>Effects</th></tr>
 *   <tr><td>Overheated</td>   <td>≥ 0.85</td>       <td>Nausea, Mining Fatigue</td></tr>
 *   <tr><td>Warm</td>         <td>0.60–0.85</td>     <td>None (comfortable)</td></tr>
 *   <tr><td>Chilly</td>       <td>0.40–0.60</td>     <td>Slowness I</td></tr>
 *   <tr><td>Cold</td>         <td>0.20–0.40</td>     <td>Slowness I + Mining Fatigue I</td></tr>
 *   <tr><td>Freezing</td>     <td>&lt; 0.20</td>     <td>Above + freeze damage</td></tr>
 * </table>
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class PlayerTemperatureSystem {

    // ── Public thresholds (used by HUD and ThirstSystem) ─────────────────────
    public static final float BODY_MAX        = 1.0f;
    public static final float BODY_MIN        = 0.0f;
    public static final float BODY_OVERHEAT   = 0.85f; // dangerously hot
    public static final float BODY_WARM       = 0.60f; // comfortable
    public static final float BODY_CHILLY     = 0.40f;
    public static final float BODY_COLD       = 0.20f; // dangerously cold

    // ── Drift rates (per 20-tick interval) ───────────────────────────────────
    /** Normal drift rate toward environmental target. */
    private static final float DRIFT_NORMAL       = 0.035f;
    /** Extra speed when wet and body temp < 0.5 (hypothermia). */
    private static final float DRIFT_WET_COLD     = 0.09f;
    /** Cooling bonus when wet and overheated. */
    private static final float DRIFT_WET_HOT      = 0.05f;
    /** Each armor piece slows cooling (not warming). */
    private static final float ARMOR_INSULATION   = 0.007f;
    /** Heat source warms you toward BODY_MAX quickly. */
    private static final float HEAT_SOURCE_BONUS  = 0.06f;

    private static final int   INTERVAL           = 20;
    private static final float FREEZE_DAMAGE      = 0.5f;
    private static final float OVERHEAT_DAMAGE    = 0.5f;

    // ── Season adjustments (shared with BiomeMixin / SnowMeltHandler) ─────────
    public static final float ADJ_SUMMER =  0.00f; // summer = normal biome temperature, no adjustment
    public static final float ADJ_SPRING = +0.05f;
    public static final float ADJ_AUTUMN = -0.20f;
    public static final float ADJ_WINTER = -0.80f;

    // ── Biome temp → body temp mapping ────────────────────────────────────────
    // Biome temp range [-0.5, 2.0] → body temp [0.0, 1.0]
    // Comfort zone: effective biome ~0.65 → body 0.5 (neutral)
    public static final float BIOME_TEMP_MIN   = -0.5f;
    public static final float BIOME_TEMP_MAX   =  2.0f;
    private static final float BIOME_RANGE     = BIOME_TEMP_MAX - BIOME_TEMP_MIN; // 2.5

    // ── Per-player storage ────────────────────────────────────────────────────
    private static final ConcurrentHashMap<UUID, Float> BODY_TEMPS =
            new ConcurrentHashMap<>();

    public static float getBodyTemp(UUID id) {
        return BODY_TEMPS.getOrDefault(id, 0.5f);
    }

    public static void setBodyTemp(UUID id, float value) {
        BODY_TEMPS.put(id, Mth.clamp(value, BODY_MIN, BODY_MAX));
    }

    public static void remove(UUID id) {
        BODY_TEMPS.remove(id);
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
        float body = getBodyTemp(id);

        float envTarget = computeEnvTarget(sp);
        body = drift(body, envTarget, sp);

        setBodyTemp(id, body);
        applyEffects(sp, body);
    }

    // ── Environment target ────────────────────────────────────────────────────

    private static float computeEnvTarget(ServerPlayer player) {
        if (isNearHeatSource(player)) return Mth.clamp(BODY_WARM + 0.1f, BODY_MIN, BODY_MAX);
        if (!isOutdoors(player))      return 0.5f; // indoors — drift toward neutral
        return biomeToBodyTarget(getEffectiveBiomeTemp(player));
    }

    public static float getEffectiveBiomeTemp(ServerPlayer player) {
        var   level = (net.minecraft.server.level.ServerLevel) player.level();
        var   pos   = player.blockPosition();
        Biome biome = level.getBiome(pos).value();
        float base  = biome.getTemperature(pos, level.getSeaLevel());
        float latitudeAdj = LatitudeClimate.temperatureAdjustment(pos.getX(), pos.getZ());

        // Hot biomes unaffected by season (same rule as BiomeMixin), but the
        // far north's latitude override still applies — no biome stays warm
        // forever once you're deep enough beyond the freeze line.
        if (biome.getBaseTemperature() > 0.8f) {
            return Mth.clamp(base + latitudeAdj, BIOME_TEMP_MIN, BIOME_TEMP_MAX);
        }

        float adj = switch (SeasonCache.get()) {
            case SUMMER -> ADJ_SUMMER;
            case SPRING -> ADJ_SPRING;
            case AUTUMN -> ADJ_AUTUMN;
            case WINTER -> ADJ_WINTER;
        };
        return Mth.clamp(base + adj + latitudeAdj, BIOME_TEMP_MIN, BIOME_TEMP_MAX);
    }

    private static float biomeToBodyTarget(float effectiveBiomeTemp) {
        return Mth.clamp(
                (effectiveBiomeTemp - BIOME_TEMP_MIN) / BIOME_RANGE,
                BODY_MIN, BODY_MAX
        );
    }

    // ── Drift ─────────────────────────────────────────────────────────────────

    private static float drift(float current, float target, ServerPlayer player) {
        float diff = target - current;
        float step;

        boolean wet = isWet(player);

        if (diff > 0f) {
            // Warming up
            step = Math.min(diff, DRIFT_NORMAL);
            if (isNearHeatSource(player)) step += HEAT_SOURCE_BONUS;
            // Being wet when overheated provides mild cooling (evaporative)
            if (wet && current > 0.5f) step -= DRIFT_WET_HOT;
        } else {
            // Cooling down
            int   armor      = countArmor(player);
            float insulation = armor * ARMOR_INSULATION;
            float rate       = DRIFT_NORMAL - insulation;

            // Wet + cold = hypothermia: rapid cooling
            if (wet && current < 0.5f) rate += DRIFT_WET_COLD;

            step = Math.max(diff, -Math.max(0f, rate));
        }

        return Mth.clamp(current + step, BODY_MIN, BODY_MAX);
    }

    // ── Effects ───────────────────────────────────────────────────────────────
    // No potion effects are applied for temperature — visual feedback comes
    // entirely from the HUD screen overlays (frozen / heat vignette).
    // Only raw damage is applied at the extremes.

    private static void applyEffects(ServerPlayer player, float body) {
        // Always clear any lingering climate potion effects (e.g. from old saves)
        player.removeEffect(MobEffects.SLOWNESS);
        player.removeEffect(MobEffects.MINING_FATIGUE);
        player.removeEffect(MobEffects.NAUSEA);

        // Extreme cold — freeze damage (screen overlay handles the visual)
        if (body < BODY_COLD) {
            player.hurt(player.damageSources().freeze(), FREEZE_DAMAGE);
        }

        // Extreme heat — hotfloor damage (heat vignette overlay handles the visual)
        if (body >= BODY_MAX) {
            player.hurt(player.damageSources().hotFloor(), OVERHEAT_DAMAGE);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    public static boolean isWet(ServerPlayer player) {
        var level = (net.minecraft.server.level.ServerLevel) player.level();
        var pos   = player.blockPosition();
        // Submerged in water
        FluidState fluid = level.getFluidState(pos);
        if (!fluid.isEmpty() && fluid.is(Fluids.WATER)) return true;
        if (!fluid.isEmpty() && fluid.is(Fluids.FLOWING_WATER)) return true;
        // Standing in rain (outdoors, raining, no overhead cover)
        return level.isRainingAt(pos) && isOutdoors(player);
    }

    private static boolean isOutdoors(ServerPlayer player) {
        return ((net.minecraft.server.level.ServerLevel) player.level()).canSeeSky(player.blockPosition().above());
    }

    private static boolean isNearHeatSource(ServerPlayer player) {
        var level = (net.minecraft.server.level.ServerLevel) player.level();
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

    private static int countArmor(ServerPlayer player) {
        int n = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            // NOTE (1.21.5 port): ArmorItem was removed — armor pieces are now plain
            // Items configured via Item.Properties#humanoidArmor, with no dedicated
            // subclass left to instanceof-check. Anything occupying a HEAD/CHEST/LEGS/FEET
            // equipment slot is armor for this purpose, so just check the slot is filled.
            ItemStack s = player.getItemBySlot(slot);
            if (!s.isEmpty()) n++;
        }
        return n;
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public static TempBand getBand(UUID id) {
        float t = getBodyTemp(id);
        if (t >= BODY_OVERHEAT) return TempBand.OVERHEATED;
        if (t >= BODY_WARM)     return TempBand.WARM;
        if (t >= BODY_CHILLY)   return TempBand.CHILLY;
        if (t >= BODY_COLD)     return TempBand.COLD;
        return TempBand.FREEZING;
    }

    public enum TempBand {
        FREEZING  ("Freezing",   0xFF4466EE),
        COLD      ("Cold",       0xFF88AAFF),
        CHILLY    ("Chilly",     0xFFBBDDFF),
        WARM      ("Warm",       0xFFFFFFFF),
        OVERHEATED("Overheated", 0xFFFF6622);

        public final String label;
        public final int    color;
        TempBand(String label, int color) { this.label = label; this.color = color; }
    }

    private PlayerTemperatureSystem() {}
}