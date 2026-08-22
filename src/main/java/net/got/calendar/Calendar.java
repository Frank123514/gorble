package net.got.calendar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.GotMod;
import net.got.climate.SeasonManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = GotMod.MODID)
public final class Calendar extends SavedData {

    public static final int DAYS_PER_MONTH   = 30;
    public static final int MONTHS_PER_YEAR  = 12;
    public static final int DAYS_PER_YEAR    = DAYS_PER_MONTH * MONTHS_PER_YEAR;

    public static final int DEFAULT_START_YEAR = 298;

    private static final long TICKS_PER_DAY = 24_000L;

    private static final int ACTION_BAR_INTERVAL = 60;

    private static final String DATA_NAME = "got_calendar";

    public static final SavedDataType<Calendar> TYPE = new SavedDataType<>(
            DATA_NAME,
            Calendar::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("day").forGetter(c -> c.day),
                    Codec.INT.fieldOf("month").forGetter(c -> c.month),
                    Codec.INT.fieldOf("year").forGetter(c -> c.year),
                    Codec.LONG.fieldOf("lastMinecraftDay").forGetter(c -> c.lastMinecraftDay)
            ).apply(instance, Calendar::new)),
            null
    );

    public static Calendar get(ServerLevel level) {
        
        ServerLevel overworld = level.getServer().overworld();
        return overworld.getDataStorage().computeIfAbsent(TYPE);
    }

    private int  day   = 1;
    private int  month = 1;
    private int  year  = DEFAULT_START_YEAR;

    private long lastMinecraftDay = -1L;

    public Calendar() {
    }

    private Calendar(int day, int month, int year, long lastMinecraftDay) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.lastMinecraftDay = lastMinecraftDay;
        
        if (this.day   < 1 || this.day   > DAYS_PER_MONTH)  this.day   = 1;
        if (this.month < 1 || this.month > MONTHS_PER_YEAR) this.month = 1;
        if (this.year  < 1)                                  this.year  = DEFAULT_START_YEAR;
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(net.got.worldgen.ModDimensions.KNOWNWORLD_LEVEL_KEY)) return;

        Calendar cal = get(level);
        long currentMcDay = level.getDayTime() / TICKS_PER_DAY;

        if (cal.lastMinecraftDay < 0) {
            cal.lastMinecraftDay = currentMcDay;
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

        if (level.getGameTime() % ACTION_BAR_INTERVAL == 0) {
            pushActionBar(level, cal);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ServerLevel overworld = ((ServerLevel) player.level()).getServer().overworld();
        Calendar cal = get(overworld);
        sendDateTitle(player, cal, false);
    }

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

    public void setDate(int newYear, int newMonth, int newDay) {
        this.year  = newYear;
        this.month = Math.max(1, Math.min(MONTHS_PER_YEAR, newMonth));
        this.day   = Math.max(1, Math.min(DAYS_PER_MONTH, newDay));
        setDirty();
    }

    public void skipDays(int days, ServerLevel level) {
        for (int i = 0; i < days; i++) advanceDay();
        setDirty();
        if (level != null) {
            SeasonManager.advanceByDays(days, level);
        }
    }

    public int getDay()   { return day;   }
    public int getMonth() { return month; }
    public int getYear()  { return year;  }

    public Month getGotMonth() { return Month.of(month); }

    public String formatDate() {
        return String.format("The %s of the %s, Year %d AC — %s",
                ordinalDay(day),
                getGotMonth().displayName,
                year,
                SeasonManager.getCurrentSeason().displayName);
    }

    public String formatCompact() {
        return String.format("%d AC  ·  %s  ·  Day %d  ·  %s",
                year,
                getGotMonth().displayName,
                day,
                SeasonManager.getCurrentSeason().displayName);
    }

    private static void pushActionBar(ServerLevel level, Calendar cal) {
        Component text = coloredActionBar(cal);
        for (ServerPlayer player : level.players()) {
            player.displayClientMessage(text, true);
        }
    }

    private static void announceNewDay(ServerLevel level, Calendar cal) {
        for (ServerPlayer player : level.players()) {
            sendDateTitle(player, cal, true);
        }
        GotMod.LOGGER.debug("[GoT Calendar] New day: {}", cal.formatDate());
    }

    public static void sendDateTitle(ServerPlayer player, Calendar cal, boolean newDay) {
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
                        10, 40, 20));
    }

    private static Component coloredActionBar(Calendar cal) {
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

    private static String ordinalDay(int n) {
        if (n >= 11 && n <= 13) return n + "th day";
        return switch (n % 10) {
            case 1  -> n + "st day";
            case 2  -> n + "nd day";
            case 3  -> n + "rd day";
            default -> n + "th day";
        };
    }

}