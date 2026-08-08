package net.got.climate;

import net.got.GotMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thirst system.
 *
 * <h3>Thirst scale: [0.0, 1.0]</h3>
 * <ul>
 *   <li>1.0 = fully hydrated</li>
 *   <li>0.0 = critically dehydrated</li>
 * </ul>
 *
 * <h3>Drain rate</h3>
 * Baseline: 0.004 per second (full depletion ~4 min without heat).
 * Overheated body temp multiplies drain — being hot makes you sweat faster.
 * The hotter the environment, the faster thirst drops.
 *
 * <h3>Replenishment</h3>
 * Drinking a water bottle restores 0.6 thirst.
 * Drinking a potion or any item with a water bucket tag restores 0.3.
 *
 * <h3>Effects</h3>
 * <table>
 *   <tr><th>Level</th>         <th>Range</th>      <th>Effects</th></tr>
 *   <tr><td>Hydrated</td>      <td>≥ 0.50</td>     <td>None</td></tr>
 *   <tr><td>Thirsty</td>       <td>0.25–0.50</td>  <td>Slowness I</td></tr>
 *   <tr><td>Dehydrated</td>    <td>0.10–0.25</td>  <td>Slowness II + Mining Fatigue I + Weakness I</td></tr>
 *   <tr><td>Critical</td>      <td>&lt; 0.10</td>  <td>Above + damage every tick</td></tr>
 * </table>
 */
@EventBusSubscriber(modid = GotMod.MODID)
public final class PlayerThirstSystem {

    // ── Public thresholds (used by HUD) ───────────────────────────────────────
    public static final float THIRST_MAX         = 1.0f;
    public static final float THIRST_MIN         = 0.0f;
    public static final float THIRST_HYDRATED    = 0.50f;
    public static final float THIRST_THIRSTY     = 0.25f;
    public static final float THIRST_CRITICAL    = 0.10f;

    // ── Drain constants ───────────────────────────────────────────────────────
    /** Base drain per 20-tick interval at neutral body temperature. */
    private static final float BASE_DRAIN        = 0.004f;
    /**
     * Extra drain multiplier applied per unit of body temp above neutral (0.5).
     * At body temp 1.0 (max overheat), extra drain = 0.5 * HEAT_DRAIN_FACTOR.
     */
    private static final float HEAT_DRAIN_FACTOR = 0.030f;
    private static final float DEHYDRATION_DMG   = 0.5f;
    private static final int   INTERVAL          = 20;

    // ── Replenishment amounts ─────────────────────────────────────────────────
    private static final float DRINK_WATER_BOTTLE = 0.60f;
    private static final float DRINK_OTHER        = 0.30f;

    // ── Per-player storage ────────────────────────────────────────────────────
    private static final ConcurrentHashMap<UUID, Float> THIRST =
            new ConcurrentHashMap<>();

    public static float getThirst(UUID id) {
        return THIRST.getOrDefault(id, THIRST_MAX);
    }

    public static void setThirst(UUID id, float value) {
        THIRST.put(id, Mth.clamp(value, THIRST_MIN, THIRST_MAX));
    }

    public static void remove(UUID id) {
        THIRST.remove(id);
    }

    // ── Tick ──────────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;
        if (player.isCreative() || player.isSpectator()) return;
        if (player.tickCount % INTERVAL != 0) return;

        UUID  id     = player.getUUID();
        float thirst = getThirst(id);

        // Drain rate scales with body temperature above neutral
        float bodyTemp = PlayerTemperatureSystem.getBodyTemp(id);
        float heatBonus = Math.max(0f, bodyTemp - 0.5f) * HEAT_DRAIN_FACTOR;
        float drain = BASE_DRAIN + heatBonus;

        thirst = Mth.clamp(thirst - drain, THIRST_MIN, THIRST_MAX);
        setThirst(id, thirst);
        applyEffects(sp, thirst);
    }

    // ── Drinking ──────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer sp)) return;

        var stack = event.getItemStack();
        float restore = 0f;

        if (stack.is(Items.POTION)) {
            // Water bottle specifically
            var contents = stack.get(net.minecraft.core.component.DataComponents.POTION_CONTENTS);
            if (contents != null && contents.is(Potions.WATER)) {
                restore = DRINK_WATER_BOTTLE;
            } else {
                restore = DRINK_OTHER;
            }
        } else if (stack.is(Items.WATER_BUCKET)) {
            restore = DRINK_WATER_BOTTLE;
        } else if (stack.is(net.minecraft.tags.ItemTags.MEAT)
                || stack.get(net.minecraft.core.component.DataComponents.FOOD) != null) {
            // Food provides a small hydration (juices etc)
            restore = 0.05f;
        }

        if (restore > 0f) {
            UUID  id     = sp.getUUID();
            float thirst = getThirst(id);
            setThirst(id, thirst + restore);
        }
    }

    // ── Effects ───────────────────────────────────────────────────────────────
    // Slowness / weakness are applied as invisible effects (no particles,
    // no status-effect icons) so dehydration affects gameplay without adding
    // visual clutter. The thirst droplet HUD communicates the severity instead.

    private static void applyEffects(ServerPlayer player, float thirst) {
        if (thirst >= THIRST_HYDRATED) {
            player.removeEffect(MobEffects.SLOWNESS);
            player.removeEffect(MobEffects.MINING_FATIGUE);
            player.removeEffect(MobEffects.WEAKNESS);
            return;
        }

        // Thirsty — subtle slowness, completely silent visually
        // ambient=true (no particles), visible=false, showIcon=false
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, INTERVAL + 5, 0, true, false, false));

        if (thirst < THIRST_THIRSTY) {
            // Dehydrated — stronger penalties, still invisible
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, INTERVAL + 5, 1, true, false, false)); // Slowness II
            player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE,      INTERVAL + 5, 0, true, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,          INTERVAL + 5, 0, true, false, false));
        }

        if (thirst < THIRST_CRITICAL) {
            // Critical — raw damage
            player.hurt(player.damageSources().dryOut(), DEHYDRATION_DMG);
        }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public static ThirstBand getBand(UUID id) {
        float t = getThirst(id);
        if (t >= THIRST_HYDRATED)  return ThirstBand.HYDRATED;
        if (t >= THIRST_THIRSTY)   return ThirstBand.THIRSTY;
        if (t >= THIRST_CRITICAL)  return ThirstBand.DEHYDRATED;
        return ThirstBand.CRITICAL;
    }

    public enum ThirstBand {
        HYDRATED   ("Hydrated",   0xFF44AAFF),
        THIRSTY    ("Thirsty",    0xFFFFCC44),
        DEHYDRATED ("Dehydrated", 0xFFFF8822),
        CRITICAL   ("Parched",    0xFFFF2200);

        public final String label;
        public final int    color;
        ThirstBand(String label, int color) { this.label = label; this.color = color; }
    }

    private PlayerThirstSystem() {}
}
