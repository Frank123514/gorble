package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Mutates live biome climate settings at runtime to simulate winter and spring.
 *
 * <p>When winter begins, every eligible biome in the registry has its
 * {@code climateSettings} replaced with a cold variant (temperature 0.0,
 * precipitation SNOW).  On spring, the original settings are restored.
 *
 * <p>This makes all vanilla mechanics work automatically:
 * <ul>
 *   <li>Snow layers placed by chunk random-ticks</li>
 *   <li>Water freezes to ice</li>
 *   <li>Weather system shows snowfall instead of rain</li>
 *   <li>{@code Biome.getTemperature()} returns the cold value for anything that queries it</li>
 * </ul>
 *
 * <h3>Eligibility filter</h3>
 * A biome is winterised if it:
 * <ul>
 *   <li>has precipitation ({@code hasPrecipitation == true})</li>
 *   <li>has a base temperature between {@value #MIN_WINTERISABLE_TEMP} and
 *       {@value #MAX_WINTERISABLE_TEMP} (excludes deserts, jungles, and already-frozen biomes)</li>
 * </ul>
 *
 * <h3>Why Unsafe instead of an AccessTransformer</h3>
 * NeoForge 21.4 ships a coremod ({@code ReplaceFieldWithGetterAccess}) that redirects
 * every {@code GETFIELD Biome.climateSettings} bytecode to a generated getter call.
 * That coremod asserts the field is still {@code private}; exposing it via an AT
 * (even as {@code public-f}) triggers an {@link IllegalStateException} at bootstrap.
 * {@link Unsafe#putObject} bypasses both the {@code private} restriction and the
 * {@code final} modifier without touching the field's visibility, so the coremod
 * never sees a conflict.  NeoForge 1.21.4 uses Mojang's official mappings in both
 * dev and production, so the field name {@code "climateSettings"} is stable.
 *
 * <h3>Thread safety</h3>
 * {@code originalSettings} is only ever read/written on the server thread during
 * season transitions.  {@code isWinterApplied} is volatile for safe reads from
 * other contexts (e.g. HUD rendering).
 *
 * <h3>World reload</h3>
 * Call {@link #restoreIfWinter(ServerLevel)} from a {@code LevelEvent.Load}
 * handler (overworld only) when the persisted season is {@link GotSeason#WINTER}.
 */
public final class WinterBiomeManager {

    // ── Temperature thresholds ────────────────────────────────────────────────

    /** Biomes already colder than this are skipped (tundra, ice spikes, etc.). */
    private static final float MIN_WINTERISABLE_TEMP = 0.15f;

    /**
     * Biomes warmer than this are skipped (deserts, jungles, badlands).
     * 0.95 covers most warm biomes while still letting savanna-level biomes freeze.
     */
    private static final float MAX_WINTERISABLE_TEMP = 0.95f;

    /** Temperature applied to all winterised biomes. Must be below 0.15 to trigger vanilla snow. */
    private static final float WINTER_TEMPERATURE = 0.0f;

    // ── Unsafe field access ───────────────────────────────────────────────────

    /**
     * {@link Unsafe} instance, obtained via reflection on {@code theUnsafe}.
     * NeoForge runs the JVM with relaxed module restrictions, so this succeeds.
     */
    private static final Unsafe UNSAFE;

    /**
     * Memory offset of {@code Biome.climateSettings} within a {@link Biome} object.
     * Computed once at class-load time; valid for all {@link Biome} instances.
     */
    private static final long CLIMATE_SETTINGS_OFFSET;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);

            // getDeclaredField does NOT require setAccessible — we only need the
            // Field object to compute the memory offset, not to read/write through it.
            Field climateField = Biome.class.getDeclaredField("climateSettings");
            CLIMATE_SETTINGS_OFFSET = UNSAFE.objectFieldOffset(climateField);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /** Reads {@code biome.climateSettings} via Unsafe (bypasses private + final). */
    private static Biome.ClimateSettings getClimate(Biome biome) {
        return (Biome.ClimateSettings) UNSAFE.getObject(biome, CLIMATE_SETTINGS_OFFSET);
    }

    /** Writes {@code biome.climateSettings} via Unsafe (bypasses private + final). */
    private static void setClimate(Biome biome, Biome.ClimateSettings settings) {
        UNSAFE.putObject(biome, CLIMATE_SETTINGS_OFFSET, settings);
    }

    // ── State ─────────────────────────────────────────────────────────────────

    /** Tracks whether winter mutations are currently applied. */
    private static volatile boolean isWinterApplied = false;

    /**
     * Stores original {@code ClimateSettings} for each winterised biome so they
     * can be precisely restored.  Keyed by registry key so it survives across
     * registry instances on world reload.
     */
    private static final Map<ResourceKey<Biome>, Biome.ClimateSettings> originalSettings =
            new HashMap<>();

    // ── Public API ────────────────────────────────────────────────────────────

    /** Returns {@code true} while winter mutations are active. */
    public static boolean isWinterApplied() {
        return isWinterApplied;
    }

    /**
     * Applies winter climate to all eligible biomes in the server's biome registry.
     * Also starts precipitation so snow begins falling immediately.
     *
     * <p>Safe to call multiple times — skips biomes that are already winterised.
     */
    public static void applyWinter(ServerLevel overworld) {
        if (isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] applyWinter called but winter is already applied — skipping.");
            return;
        }

        // In 1.21.4, Registry<T> accessors were removed from RegistryAccess.
        // lookupOrThrow() returns a HolderLookup.RegistryLookup<T> with listElements()
        // for full iteration.
        var biomeRegistry = overworld.registryAccess().lookupOrThrow(Registries.BIOME);
        int count = 0;

        for (Holder.Reference<Biome> holder : biomeRegistry.listElements().toList()) {
            Biome biome = holder.value();
            Biome.ClimateSettings current = getClimate(biome);

            if (!isEligible(current)) continue;

            originalSettings.put(holder.key(), current);

            setClimate(biome, new Biome.ClimateSettings(
                    true,                            // hasPrecipitation — keep snow falling
                    WINTER_TEMPERATURE,              // temperature — below 0.15 triggers snow
                    Biome.TemperatureModifier.NONE,  // no altitude modifier needed at 0.0
                    current.downfall()               // keep original downfall
            ));
            count++;
        }

        isWinterApplied = true;

        // Kick off precipitation so snowfall starts immediately rather than waiting
        // for the next natural rain cycle.  Duration: ~15 real minutes of snowfall.
        overworld.setWeatherParameters(0, 18_000, true, false);

        GotMod.LOGGER.info("[WinterBiomeManager] Winter applied to {} biomes.", count);
    }

    /**
     * Restores all biomes to their original climate settings.
     * Also clears precipitation so snow stops and reverts to natural weather cycles.
     */
    public static void revertWinter(ServerLevel overworld) {
        if (!isWinterApplied) {
            GotMod.LOGGER.warn("[WinterBiomeManager] revertWinter called but winter is not applied — skipping.");
            return;
        }

        var biomeRegistry = overworld.registryAccess().lookupOrThrow(Registries.BIOME);
        int count = 0;

        for (Map.Entry<ResourceKey<Biome>, Biome.ClimateSettings> entry : originalSettings.entrySet()) {
            Optional<Holder.Reference<Biome>> holderOpt = biomeRegistry.get(entry.getKey());
            if (holderOpt.isEmpty()) {
                GotMod.LOGGER.warn("[WinterBiomeManager] Biome {} not found during revert — skipping.", entry.getKey().location());
                continue;
            }
            setClimate(holderOpt.get().value(), entry.getValue());
            count++;
        }

        originalSettings.clear();
        isWinterApplied = false;

        // Stop precipitation so the world doesn't immediately re-rain.
        overworld.setWeatherParameters(6_000, 0, false, false);

        GotMod.LOGGER.info("[WinterBiomeManager] Winter reverted for {} biomes.", count);
    }

    /**
     * Re-applies winter mutations after a server restart.
     *
     * <p>The biome registry is freshly populated on each world load, so any
     * runtime mutations are lost.  Called from the {@code LevelEvent.Load}
     * handler in {@code GotMod} (overworld only) when
     * {@link SeasonManager#getCurrentSeason()} is {@link GotSeason#WINTER}.
     */
    public static void restoreIfWinter(ServerLevel overworld) {
        if (SeasonManager.getCurrentSeason().isWinter() && !isWinterApplied) {
            GotMod.LOGGER.info("[WinterBiomeManager] Server restarted mid-winter — re-applying winter biome mutations.");
            applyWinter(overworld);
        }
    }

    // ── Eligibility ───────────────────────────────────────────────────────────

    private static boolean isEligible(Biome.ClimateSettings climate) {
        if (!climate.hasPrecipitation()) return false;  // dry biomes: deserts, badlands
        float temp = climate.temperature();
        return temp >= MIN_WINTERISABLE_TEMP && temp <= MAX_WINTERISABLE_TEMP;
    }

    // ── Private constructor ───────────────────────────────────────────────────

    private WinterBiomeManager() {}
}