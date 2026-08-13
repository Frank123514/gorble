package net.got.climate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.GotMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.got.network.SeasonSyncPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Random;

@EventBusSubscriber(modid = GotMod.MODID)
public final class SeasonManager extends SavedData {

    private static final String DATA_NAME    = "got_seasons";
    private static final long TICKS_PER_DAY  = 24_000L;
    private static final int TRANSITION_DAYS = 7;
    private static final int BASE_LONG_DAYS  = 28;
    private static final int MAX_LONG_DAYS   = 70;

    private static volatile Season CURRENT_SEASON = Season.SUMMER;

    public static Season getCurrentSeason() {
        return CURRENT_SEASON;
    }

    private Season currentSeason  = Season.SUMMER;
    private long      ticksRemaining = daysToTicks(BASE_LONG_DAYS);

    public static final SavedDataType<SeasonManager> TYPE = new SavedDataType<>(
            DATA_NAME,
            SeasonManager::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("season").forGetter(m -> m.currentSeason.name()),
                    Codec.LONG.fieldOf("ticksRemaining").forGetter(m -> m.ticksRemaining)
            ).apply(instance, SeasonManager::new)),
            null
    );

    public static SeasonManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public SeasonManager() {
    }

    private SeasonManager(String seasonName, long ticksRemaining) {
        try {
            this.currentSeason = Season.valueOf(seasonName);
        } catch (IllegalArgumentException ignored) {
            this.currentSeason = Season.SUMMER;
        }
        this.ticksRemaining = ticksRemaining;
        CURRENT_SEASON = this.currentSeason;
        SeasonCache.set(this.currentSeason);
    }

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

    private void advanceSeason(ServerLevel level) {
        Season previous = currentSeason;
        currentSeason  = currentSeason.next();
        CURRENT_SEASON = currentSeason;
        SeasonCache.set(currentSeason);
        PacketDistributor.sendToAllPlayers(new SeasonSyncPayload(currentSeason));

        Random rng = new Random(level.getSeed() ^ level.getGameTime());
        ticksRemaining = switch (currentSeason) {
            case SPRING, AUTUMN -> daysToTicks(TRANSITION_DAYS);
            case SUMMER, WINTER -> daysToTicks(
                    BASE_LONG_DAYS + rng.nextInt(MAX_LONG_DAYS - BASE_LONG_DAYS + 1));
        };

        setDirty();

        String message = buildTransitionMessage(currentSeason);
        for (ServerPlayer player : level.players()) {
            player.sendSystemMessage(Component.literal(message));
        }

        GotMod.LOGGER.info("[GoT Seasons] {} → {} (lasts ~{} days)",
                previous.displayName, currentSeason.displayName,
                ticksRemaining / TICKS_PER_DAY);
    }

    private static String buildTransitionMessage(Season to) {
        return switch (to) {
            case SPRING -> "§aThe snows begin to melt. Spring has come to Westeros.";
            case SUMMER -> "§eThe days grow long and warm. Summer is upon the realm.";
            case AUTUMN -> "§6The leaves turn and the nights grow colder. Autumn has come.";
            case WINTER -> "§bThe cold descends from the north. Winter has come.";
        };
    }

    private static long daysToTicks(int days) {
        return (long) days * TICKS_PER_DAY;
    }

    public static long getDaysRemaining(ServerLevel level) {
        SeasonManager mgr = get(level.getServer().overworld());
        return Math.max(0L, mgr.ticksRemaining / TICKS_PER_DAY);
    }

    public static void advanceByDays(int days, ServerLevel level) {
        ServerLevel overworld = level.getServer().overworld();
        SeasonManager mgr = get(overworld);
        long ticksToConsume = (long) days * TICKS_PER_DAY;
        while (ticksToConsume > 0) {
            if (ticksToConsume >= mgr.ticksRemaining) {
                ticksToConsume -= mgr.ticksRemaining;
                mgr.ticksRemaining = 0;
                mgr.advanceSeason(overworld);
            } else {
                mgr.ticksRemaining -= ticksToConsume;
                ticksToConsume = 0;
            }
        }
        mgr.setDirty();
    }

    public static void setSeason(Season season, ServerLevel level) {
        ServerLevel overworld = level.getServer().overworld();
        SeasonManager mgr = get(overworld);
        Season previous = mgr.currentSeason;
        mgr.currentSeason  = season;
        CURRENT_SEASON     = season;
        SeasonCache.set(season);
        PacketDistributor.sendToAllPlayers(new SeasonSyncPayload(season));

        Random rng = new Random(overworld.getSeed() ^ overworld.getGameTime());
        mgr.ticksRemaining = switch (season) {
            case SPRING, AUTUMN -> daysToTicks(TRANSITION_DAYS);
            case SUMMER, WINTER -> daysToTicks(
                    BASE_LONG_DAYS + rng.nextInt(MAX_LONG_DAYS - BASE_LONG_DAYS + 1));
        };
        mgr.setDirty();

        if (season != previous) {
            String msg = buildTransitionMessage(season);
            for (ServerPlayer player : overworld.players()) {
                player.sendSystemMessage(Component.literal(msg));
            }
        }

        GotMod.LOGGER.info("[GoT Seasons] Season manually set to {} (~{} days)",
                season.displayName, mgr.ticksRemaining / TICKS_PER_DAY);
    }

    public String getStatus() {
        return String.format("Season: %s (%d days remaining)",
                currentSeason.displayName, ticksRemaining / TICKS_PER_DAY);
    }
    
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ServerLevel overworld = event.getServer().overworld();
        overworld.getGameRules()
                .set(net.minecraft.world.level.gamerules.GameRules.MAX_SNOW_ACCUMULATION_HEIGHT, 8, event.getServer());
        GotMod.LOGGER.info("[GoT Seasons] snowAccumulationHeight set to 8");
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        PacketDistributor.sendToPlayer(sp, new SeasonSyncPayload(CURRENT_SEASON));
    }
}