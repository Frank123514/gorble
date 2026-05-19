package net.got.calendar;

import net.got.GotMod;
import net.got.climate.SeasonManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

/**
 * Tracks the in-world GoT date (day, month, year) and keeps players informed.
 *
 * <h3>Calendar</h3>
 * <ul>
 *   <li>{@value DAYS_PER_MONTH} in-game days per moon (month)</li>
 *   <li>{@value MONTHS_PER_YEAR} moons per year</li>
 *   <li>Default starting year: {@value DEFAULT_START_YEAR} AC</li>
 * </ul>
 *
 * <h3>Display</h3>
 * <ul>
 *   <li>Action bar updated every {@value ACTION_BAR_INTERVAL} ticks (~3 s) so the
 *       date stays persistent on screen without being intrusive.</li>
 *   <li>Title + subtitle shown on day change and to players on join.</li>
 * </ul>
 *
 * <h3>Access</h3>
 * Call {@link #get(ServerLevel)} with the overworld to retrieve the instance.
 * Use {@link #formatDate()} for a full date string.
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class GotCalendar extends SavedData {

    // ── Calendar constants ────────────────────────────────────────────────────

    public static final int DAYS_PER_MONTH   = 30;
    public static final int MONTHS_PER_YEAR  = 12;
    public static final int DAYS_PER_YEAR    = DAYS_PER_MONTH * MONTHS_PER_YEAR; // 360

    /** Default starting year (AC = After Conquest). Start of the show/books. */
    public static final int DEFAULT_START_YEAR = 298;

    // ── Timing ────────────────────────────────────────────────────────────────

    /** Ticks in one Minecraft day. */
    private static final long TICKS_PER_DAY = 24_000L;

    /** How often to push an action bar update to all players (in ticks). */
    private static final int ACTION_BAR_INTERVAL = 60;

    // ── Storage ───────────────────────────────────────────────────────────────

    private static final String DATA_NAME = "got_calendar";

    private static final SavedData.Factory<GotCalendar> FACTORY =
            new SavedData.Factory<>(GotCalendar::new, GotCalendar::load, null);

    public static GotCalendar get(ServerLevel level) {
        // Always retrieve from overworld so all dimensions share one calendar.
        ServerLevel overworld = level.getServer().overworld();
        return overworld.getDataStorage().computeIfAbsent(FACTORY, DATA_NAME);
    }

    // ── Persisted state ───────────────────────────────────────────────────────

    private int  day   = 1;
    private int  month = 1;
    private int  year  = DEFAULT_START_YEAR;

    /** Last absolute Minecraft day index we processed; used to detect day changes. */
    private long lastMinecraftDay = -1L;

    // ── Tick event ────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        GotCalendar cal = get(level);
        long currentMcDay = level.getDayTime() / TICKS_PER_DAY;

        // Advance the calendar once per Minecraft day.
        if (cal.lastMinecraftDay < 0) {
            cal.lastMinecraftDay = currentMcDay; // first tick — sync without advancing
            cal.setDirty();
        } else if (currentMcDay > cal.lastMinecraftDay) {
            long daysElapsed = currentMcDay - cal.lastMinecraftDay;
            for (long i = 0; i < daysElapsed; i++) {
                cal.advanceDay();
            }
            cal.lastMinecraftDay = currentMcDay;
            cal.setDirty();
            announceNewDay(level, cal);
        }

        // Periodic action bar update.
        if (level.getGameTime() % ACTION_BAR_INTERVAL == 0) {
            pushActionBar(level, cal);
        }
    }

    // ── Player join ───────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ServerLevel overworld = player.server.overworld();
        GotCalendar cal = get(overworld);
        sendDateTitle(player, cal, false);
    }

    // ── Calendar arithmetic ───────────────────────────────────────────────────

    private void advanceDay() {
        day++;
        if (day > DAYS_PER_MONTH) {
            day = 1;
            month++;
        }
        if (month > MONTHS_PER_YEAR) {
            month = 1;
            year++;
        }
    }

    /** Sets the date directly. Clamps month to 1-12 and day to 1-30. */
    public void setDate(int newYear, int newMonth, int newDay) {
        this.year  = newYear;
        this.month = Math.max(1, Math.min(MONTHS_PER_YEAR, newMonth));
        this.day   = Math.max(1, Math.min(DAYS_PER_MONTH, newDay));
        setDirty();
    }

    /**
     * Advances the calendar by {@code days} days and keeps the season timer in
     * sync.  Pass the overworld (or any ServerLevel — the season manager always
     * operates on the overworld internally).
     */
    public void skipDays(int days, ServerLevel level) {
        for (int i = 0; i < days; i++) advanceDay();
        setDirty();
        if (level != null) {
            SeasonManager.advanceByDays(days, level);
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int getDay()   { return day;   }
    public int getMonth() { return month; }
    public int getYear()  { return year;  }

    public GotMonth getGotMonth() { return GotMonth.of(month); }

    // ── Formatting ────────────────────────────────────────────────────────────

    /**
     * Full date string, e.g.:
     * {@code "The 15th day of the Moon of Flowers, Year 298 AC — Summer"}
     */
    public String formatDate() {
        return String.format("The %s of the %s, Year %d AC — %s",
                ordinalDay(day),
                getGotMonth().displayName,
                year,
                SeasonManager.getCurrentSeason().displayName);
    }

    /**
     * Compact string for the action bar, e.g.:
     * {@code "298 AC  ·  Moon of Flowers  ·  Day 15  ·  Winter"}
     */
    public String formatCompact() {
        return String.format("%d AC  ·  %s  ·  Day %d  ·  %s",
                year,
                getGotMonth().displayName,
                day,
                SeasonManager.getCurrentSeason().displayName);
    }

    // ── Display helpers ───────────────────────────────────────────────────────

    private static void pushActionBar(ServerLevel level, GotCalendar cal) {
        Component text = coloredActionBar(cal);
        for (ServerPlayer player : level.players()) {
            player.displayClientMessage(text, true);
        }
    }

    private static void announceNewDay(ServerLevel level, GotCalendar cal) {
        for (ServerPlayer player : level.players()) {
            sendDateTitle(player, cal, true);
        }
        GotMod.LOGGER.debug("[GoT Calendar] New day: {}", cal.formatDate());
    }

    /**
     * Sends a title (+ subtitle) to a player showing the current date.
     *
     * @param newDay if true, shows "A New Day Dawns" as the main title;
     *               if false (e.g. on join), shows the year as the main title.
     */
    public static void sendDateTitle(ServerPlayer player, GotCalendar cal, boolean newDay) {
        Component title = newDay
                ? Component.literal("§6A New Day Dawns")
                : Component.literal("§6" + cal.year + " AC");

        Component subtitle = Component.literal(
                "§7" + cal.getGotMonth().displayName +
                        " · Day " + cal.day +
                        " · " + SeasonManager.getCurrentSeason().displayName);

        player.connection.send(
                new net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket(title));
        player.connection.send(
                new net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket(subtitle));
        player.connection.send(
                new net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket(
                        10, 40, 20)); // fade-in, stay, fade-out
    }

    private static Component coloredActionBar(GotCalendar cal) {
        String season = SeasonManager.getCurrentSeason().displayName;
        String seasonColor = switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> "§b";
            case AUTUMN -> "§6";
            case SPRING -> "§a";
            case SUMMER -> "§e";
        };
        return Component.literal(
                "§7" + cal.year + " AC  ·  " +
                        cal.getGotMonth().displayName + "  ·  Day " + cal.day +
                        "  ·  " + seasonColor + season);
    }

    // ── Ordinal formatting ────────────────────────────────────────────────────

    private static String ordinalDay(int n) {
        if (n >= 11 && n <= 13) return n + "th day";
        return switch (n % 10) {
            case 1  -> n + "st day";
            case 2  -> n + "nd day";
            case 3  -> n + "rd day";
            default -> n + "th day";
        };
    }

    // ── SavedData ─────────────────────────────────────────────────────────────

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("day",              day);
        tag.putInt("month",            month);
        tag.putInt("year",             year);
        tag.putLong("lastMinecraftDay", lastMinecraftDay);
        return tag;
    }

    public static GotCalendar load(CompoundTag tag, HolderLookup.Provider registries) {
        GotCalendar cal = new GotCalendar();
        cal.day              = tag.getInt("day");
        cal.month            = tag.getInt("month");
        cal.year             = tag.getInt("year");
        cal.lastMinecraftDay = tag.getLong("lastMinecraftDay");
        // Sanity clamp in case of corrupted data
        if (cal.day   < 1 || cal.day   > DAYS_PER_MONTH)   cal.day   = 1;
        if (cal.month < 1 || cal.month > MONTHS_PER_YEAR)   cal.month = 1;
        if (cal.year  < 1)                                   cal.year  = DEFAULT_START_YEAR;
        return cal;
    }
}