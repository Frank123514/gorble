package net.got.climate;

import net.got.GotMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Random;

/**
 * Manages the GoT season cycle and persists it across world reloads.
 *
 * <h3>Season lengths (in Minecraft days)</h3>
 * <ul>
 *   <li>Spring / Autumn: {@value #TRANSITION_DAYS} days (fixed, short)</li>
 *   <li>Summer / Winter: {@value #BASE_LONG_DAYS}–{@value #MAX_LONG_DAYS} days
 *       (random per-transition, seeded from world seed + game time)</li>
 * </ul>
 *
 * <h3>Quick access</h3>
 * Call {@link #getCurrentSeason()} from anywhere — mixins, event handlers, HUD
 * rendering — for a fast, thread-safe read of the active season.
 *
 * <p>Use {@link #getDaysRemaining(ServerLevel)} to query the season countdown,
 * {@link #advanceByDays(int, ServerLevel)} to consume season ticks in bulk (e.g.
 * when the calendar skips days), and {@link #setSeason(GotSeason, ServerLevel)}
 * to force-set the current season.
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class SeasonManager extends SavedData {

    // ── Storage key ───────────────────────────────────────────────────────────

    private static final String DATA_NAME = "got_seasons";

    // ── Season length constants ───────────────────────────────────────────────

    /** Ticks in a single Minecraft day. */
    private static final long TICKS_PER_DAY = 24_000L;

    /** Fixed length for Spring and Autumn (short, transitional). */
    private static final int TRANSITION_DAYS = 7;

    /** Minimum length for Summer and Winter. */
    private static final int BASE_LONG_DAYS = 28;

    /**
     * Maximum length for Summer and Winter.
     * GoT lore: seasons can last years — 70 in-game days ≈ roughly thematic.
     * Increase this for longer, more brutal winters.
     */
    private static final int MAX_LONG_DAYS = 70;

    // ── Static season cache (fast, lock-free read for mixins) ─────────────────

    private static volatile GotSeason CURRENT_SEASON = GotSeason.SUMMER;

    /**
     * Returns the currently active season.
     * Thread-safe and allocation-free — safe to call from hot paths like mixins.
     */
    public static GotSeason getCurrentSeason() {
        return CURRENT_SEASON;
    }

    // ── Persisted state ───────────────────────────────────────────────────────

    private GotSeason currentSeason     = GotSeason.SUMMER;
    private long      ticksRemaining    = daysToTicks(BASE_LONG_DAYS);

    // ── SavedData factory ─────────────────────────────────────────────────────

    private static final Factory<SeasonManager> FACTORY =
            new Factory<>(SeasonManager::new, SeasonManager::load, null);

    /**
     * Retrieves (or creates) the SeasonManager for the given server level.
     * Always pass the overworld; other dimensions share the same season.
     */
    public static SeasonManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
    }

    // ── NBT serialisation ─────────────────────────────────────────────────────

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putString("season", currentSeason.name());
        tag.putLong("ticksRemaining", ticksRemaining);
        return tag;
    }

    public static SeasonManager load(CompoundTag tag, HolderLookup.Provider registries) {
        SeasonManager mgr = new SeasonManager();
        try {
            mgr.currentSeason = GotSeason.valueOf(tag.getString("season"));
        } catch (IllegalArgumentException ignored) {
            mgr.currentSeason = GotSeason.SUMMER;
        }
        mgr.ticksRemaining = tag.getLong("ticksRemaining");
        CURRENT_SEASON = mgr.currentSeason;
        return mgr;
    }

    // ── Tick event (overworld only, runs every game tick) ─────────────────────

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        get(level).tick(level);
    }

    private void tick(ServerLevel level) {
        if (--ticksRemaining <= 0) {
            advanceSeason(level);
        }
    }

    // ── Season transition ─────────────────────────────────────────────────────

    private void advanceSeason(ServerLevel level) {
        GotSeason previous = currentSeason;
        currentSeason  = currentSeason.next();
        CURRENT_SEASON = currentSeason;

        // Seed per-transition randomness from world seed + game time for
        // consistent but unpredictable season lengths.
        Random rng = new Random(level.getSeed() ^ level.getGameTime());
        ticksRemaining = switch (currentSeason) {
            case SPRING, AUTUMN -> daysToTicks(TRANSITION_DAYS);
            case SUMMER, WINTER -> daysToTicks(
                    BASE_LONG_DAYS + rng.nextInt(MAX_LONG_DAYS - BASE_LONG_DAYS + 1));
        };

        // Apply or revert winter biome climate mutations on season transitions.
        if (currentSeason.isWinter()) {
            WinterBiomeManager.applyWinter(level);
        } else if (previous.isWinter()) {
            WinterBiomeManager.revertWinter(level);
        }

        setDirty();

        String message = buildTransitionMessage(currentSeason);
        for (ServerPlayer player : level.players()) {
            player.sendSystemMessage(Component.literal(message));
        }

        GotMod.LOGGER.info("[GoT Seasons] {} → {} (lasts ~{} days)",
                previous.displayName, currentSeason.displayName,
                ticksRemaining / TICKS_PER_DAY);
    }

    private static String buildTransitionMessage(GotSeason to) {
        return switch (to) {
            case SPRING -> "§aThe snows begin to melt. Spring has come to Westeros.";
            case SUMMER -> "§eThe days grow long and warm. Summer is upon the realm.";
            case AUTUMN -> "§6The leaves turn and the nights grow colder. Autumn has come.";
            case WINTER -> "§bThe cold descends from the north. Winter has come.";
        };
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static long daysToTicks(int days) {
        return (long) days * TICKS_PER_DAY;
    }

    // ── Static helpers (called from commands / calendar) ──────────────────────

    /**
     * Returns the number of in-game days remaining in the current season.
     * Always retrieves from the overworld so all dimensions stay in sync.
     */
    public static long getDaysRemaining(ServerLevel level) {
        SeasonManager mgr = get(level.getServer().overworld());
        return Math.max(0L, mgr.ticksRemaining / TICKS_PER_DAY);
    }

    /**
     * Advances the season timer by {@code days} in-game days, triggering one or
     * more season transitions if the timer runs out mid-skip.
     *
     * <p>Called by the calendar when skipping days so that {@code /gotdate skip N}
     * keeps the date and season in sync.
     */
    public static void advanceByDays(int days, ServerLevel level) {
        ServerLevel overworld = level.getServer().overworld();
        SeasonManager mgr = get(overworld);
        long ticksToConsume = (long) days * TICKS_PER_DAY;
        while (ticksToConsume > 0) {
            if (ticksToConsume >= mgr.ticksRemaining) {
                ticksToConsume -= mgr.ticksRemaining;
                mgr.ticksRemaining = 0;
                mgr.advanceSeason(overworld); // sets new ticksRemaining internally
            } else {
                mgr.ticksRemaining -= ticksToConsume;
                ticksToConsume = 0;
            }
        }
        mgr.setDirty();
    }

    /**
     * Force-sets the active season and resets its timer to a fresh random length.
     * Used by {@code /gotdate season set <season>}.
     */
    public static void setSeason(GotSeason season, ServerLevel level) {
        ServerLevel overworld = level.getServer().overworld();
        SeasonManager mgr = get(overworld);
        GotSeason previous = mgr.currentSeason;
        mgr.currentSeason  = season;
        CURRENT_SEASON     = season;

        Random rng = new Random(overworld.getSeed() ^ overworld.getGameTime());
        mgr.ticksRemaining = switch (season) {
            case SPRING, AUTUMN -> daysToTicks(TRANSITION_DAYS);
            case SUMMER, WINTER -> daysToTicks(
                    BASE_LONG_DAYS + rng.nextInt(MAX_LONG_DAYS - BASE_LONG_DAYS + 1));
        };
        mgr.setDirty();

        // Apply or revert winter biome mutations when the season is force-set.
        if (season.isWinter() && !previous.isWinter()) {
            WinterBiomeManager.applyWinter(overworld);
        } else if (!season.isWinter() && previous.isWinter()) {
            WinterBiomeManager.revertWinter(overworld);
        }

        if (season != previous) {
            String msg = buildTransitionMessage(season);
            for (ServerPlayer player : overworld.players()) {
                player.sendSystemMessage(Component.literal(msg));
            }
        }

        GotMod.LOGGER.info("[GoT Seasons] Season manually set to {} (~{} days)",
                season.displayName, mgr.ticksRemaining / TICKS_PER_DAY);
    }

    /** Returns a debug string showing the current season and days remaining. */
    public String getStatus() {
        return String.format("Season: %s (%d days remaining)",
                currentSeason.displayName, ticksRemaining / TICKS_PER_DAY);
    }
}