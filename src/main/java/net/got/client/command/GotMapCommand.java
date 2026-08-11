package net.got.client.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class GotMapCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gotmap")
                        // NOTE (1.21.11 permission overhaul): CommandSourceStack#hasPermission(int) was removed;
                        // commands now use PermissionCheck objects. Commands.LEVEL_GAMEMASTERS corresponds to the
                        // old permission level 2. Verify this against your local NeoForge jar - if the constant
                        // name differs, swap it for whichever Commands.LEVEL_* matches level 2 (gamemaster/op).
                        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            player.sendSystemMessage(
                                    net.minecraft.network.chat.Component.literal(
                                            "Use the map with key M"
                                    )
                            );
                            return 1;
                        })
        );
    }
}
