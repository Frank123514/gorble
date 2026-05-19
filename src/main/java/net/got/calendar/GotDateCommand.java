package net.got.calendar;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.got.GotMod;
import net.got.climate.SeasonManager;
import net.got.client.command.GotMapCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Registers all GoT commands on the game event bus.
 *
 * <h3>{@code /gotdate}</h3>
 * <pre>
 *   /gotdate                          — Print current date + season in chat
 *   /gotdate season                   — Print current season + days remaining
 *   /gotdate set &lt;year&gt; &lt;month&gt; &lt;day&gt; — Set date (requires op)
 *   /gotdate skip &lt;days&gt;              — Advance calendar N days (requires op)
 * </pre>
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class GotDateCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
        GotMapCommand.register(event.getDispatcher()); // wire up the existing map command too
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gotdate")

                        // /gotdate  — show full date
                        .executes(GotDateCommand::executeShow)

                        // /gotdate season  — show season info
                        .then(Commands.literal("season")
                                .executes(GotDateCommand::executeSeason))

                        // /gotdate set <year> <month> <day>  — op only
                        .then(Commands.literal("set")
                                .requires(src -> src.hasPermission(2))
                                .then(Commands.argument("year",  IntegerArgumentType.integer(1))
                                        .then(Commands.argument("month", IntegerArgumentType.integer(1, 12))
                                                .then(Commands.argument("day",   IntegerArgumentType.integer(1, 30))
                                                        .executes(GotDateCommand::executeSet)))))

                        // /gotdate skip <days>  — op only
                        .then(Commands.literal("skip")
                                .requires(src -> src.hasPermission(2))
                                .then(Commands.argument("days", IntegerArgumentType.integer(1, 36000))
                                        .executes(GotDateCommand::executeSkip)))
        );
    }

    // ── /gotdate ──────────────────────────────────────────────────────────────

    private static int executeShow(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();
        GotCalendar cal = GotCalendar.get(src.getServer().overworld());

        src.sendSuccess(() -> Component.literal("§6═══ The GoT Date ═══"), false);
        src.sendSuccess(() -> Component.literal("§e" + cal.formatDate()), false);

        // If the source is a player also show a title
        try {
            ServerPlayer player = src.getPlayerOrException();
            GotCalendar.sendDateTitle(player, cal, false);
        } catch (Exception ignored) {}

        return 1;
    }

    // ── /gotdate season ───────────────────────────────────────────────────────

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

    // ── /gotdate set <year> <month> <day> ─────────────────────────────────────

    private static int executeSet(CommandContext<CommandSourceStack> ctx) {
        int year  = IntegerArgumentType.getInteger(ctx, "year");
        int month = IntegerArgumentType.getInteger(ctx, "month");
        int day   = IntegerArgumentType.getInteger(ctx, "day");

        CommandSourceStack src = ctx.getSource();
        GotCalendar cal = GotCalendar.get(src.getServer().overworld());
        cal.setDate(year, month, day);

        // Announce to everyone
        String full = cal.formatDate();
        for (ServerPlayer player : src.getServer().getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.literal("§6[GoT Date] Date set to: §e" + full));
            GotCalendar.sendDateTitle(player, cal, false);
        }

        src.sendSuccess(() -> Component.literal("§aDate set to: §e" + full), true);
        return 1;
    }

    // ── /gotdate skip <days> ──────────────────────────────────────────────────

    private static int executeSkip(CommandContext<CommandSourceStack> ctx) {
        int days = IntegerArgumentType.getInteger(ctx, "days");

        CommandSourceStack src = ctx.getSource();
        GotCalendar cal = GotCalendar.get(src.getServer().overworld());
        cal.skipDays(days);

        String full = cal.formatDate();
        for (ServerPlayer player : src.getServer().getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.literal(
                    "§6[GoT Date] Skipped §e" + days + " day" + (days == 1 ? "" : "s") +
                            "§6. Now: §e" + full));
            GotCalendar.sendDateTitle(player, cal, false);
        }

        src.sendSuccess(() -> Component.literal("§aSkipped " + days + " day(s). Now: §e" + full), true);
        return 1;
    }

    private GotDateCommand() {}
}