package net.got.calendar;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.got.GotMod;
import net.got.climate.Season;
import net.got.climate.SeasonManager;
import net.got.client.command.MapCommand;
import net.got.worldgen.SubbiomeDebugCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = GotMod.MODID)
public final class DateCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
        MapCommand.register(event.getDispatcher());
        SubbiomeDebugCommand.register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gotdate")

                        .executes(DateCommand::executeShow)

                        .then(Commands.literal("season")
                                .executes(DateCommand::executeSeason)
                                .then(Commands.literal("set")
                                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                        .then(Commands.argument("season", StringArgumentType.word())
                                                .suggests((ctx, builder) -> {
                                                    for (Season s : Season.values())
                                                        builder.suggest(s.name().toLowerCase());
                                                    return builder.buildFuture();
                                                })
                                                .executes(DateCommand::executeSeasonSet))))

                        .then(Commands.literal("set")
                                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .then(Commands.argument("year",  IntegerArgumentType.integer(1))
                                        .then(Commands.argument("month", IntegerArgumentType.integer(1, 12))
                                                .then(Commands.argument("day",   IntegerArgumentType.integer(1, 30))
                                                        .executes(DateCommand::executeSet)))))

                        .then(Commands.literal("skip")
                                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .then(Commands.argument("days", IntegerArgumentType.integer(1, 36000))
                                        .executes(DateCommand::executeSkip)))
        );
    }

    private static int executeShow(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();
        Calendar cal = Calendar.get(src.getServer().overworld());

        src.sendSuccess(() -> Component.literal("§6═══ The GoT Date ═══"), false);
        src.sendSuccess(() -> Component.literal("§e" + cal.formatDate()), false);

        try {
            ServerPlayer player = src.getPlayerOrException();
            Calendar.sendDateTitle(player, cal, false);
        } catch (Exception ignored) {}

        return 1;
    }

    private static int executeSeason(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();

        String seasonName  = SeasonManager.getCurrentSeason().displayName;
        long daysRemaining = SeasonManager.getDaysRemaining(src.getServer().overworld());

        String seasonColor = switch (SeasonManager.getCurrentSeason()) {
            case WINTER -> "§b";
            case AUTUMN -> "§6";
            case SPRING -> "§a";
            case SUMMER -> "§e";
        };

        src.sendSuccess(() -> Component.literal(
                        "§6Current Season: " + seasonColor + seasonName +
                                "§7  (" + daysRemaining + " day" + (daysRemaining == 1 ? "" : "s") + " remaining)"),
                false);

        return 1;
    }

    private static int executeSet(CommandContext<CommandSourceStack> ctx) {
        int year  = IntegerArgumentType.getInteger(ctx, "year");
        int month = IntegerArgumentType.getInteger(ctx, "month");
        int day   = IntegerArgumentType.getInteger(ctx, "day");

        CommandSourceStack src = ctx.getSource();
        Calendar cal = Calendar.get(src.getServer().overworld());
        cal.setDate(year, month, day);

        String full = cal.formatDate();
        for (ServerPlayer player : src.getServer().getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.literal("§6[GoT Date] Date set to: §e" + full));
            Calendar.sendDateTitle(player, cal, false);
        }

        src.sendSuccess(() -> Component.literal("§aDate set to: §e" + full), true);
        return 1;
    }

    private static int executeSkip(CommandContext<CommandSourceStack> ctx) {
        int days = IntegerArgumentType.getInteger(ctx, "days");

        CommandSourceStack src = ctx.getSource();
        ServerLevel overworld = src.getServer().overworld();
        Calendar cal = Calendar.get(overworld);
        
        cal.skipDays(days, overworld);

        String full = cal.formatDate();
        for (ServerPlayer player : src.getServer().getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.literal(
                    "§6[GoT Date] Skipped §e" + days + " day" + (days == 1 ? "" : "s") +
                            "§6. Now: §e" + full));
            Calendar.sendDateTitle(player, cal, false);
        }

        src.sendSuccess(() -> Component.literal("§aSkipped " + days + " day(s). Now: §e" + full), true);
        return 1;
    }

    private static int executeSeasonSet(CommandContext<CommandSourceStack> ctx) {
        String input = StringArgumentType.getString(ctx, "season").toUpperCase();
        CommandSourceStack src = ctx.getSource();

        Season season;
        try {
            season = Season.valueOf(input);
        } catch (IllegalArgumentException e) {
            src.sendFailure(Component.literal(
                    "§cUnknown season \"" + input + "\". Valid: spring, summer, autumn, winter"));
            return 0;
        }

        ServerLevel overworld = src.getServer().overworld();
        SeasonManager.setSeason(season, overworld);

        Calendar cal = Calendar.get(overworld);
        for (ServerPlayer player : src.getServer().getPlayerList().getPlayers()) {
            Calendar.sendDateTitle(player, cal, false);
        }

        String seasonColor = switch (season) {
            case WINTER -> "§b"; case AUTUMN -> "§6";
            case SPRING -> "§a"; case SUMMER -> "§e";
        };
        src.sendSuccess(() -> Component.literal(
                "§aSeason set to: " + seasonColor + season.displayName), true);
        return 1;
    }

    private DateCommand() {}
}