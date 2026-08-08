package net.got.client;

import net.got.GotMod;
import net.got.init.GotModRecipeTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;

/**
 * Listens for the synced recipe payload from the server and stores smithy
 * recipes in {@link SmithyClientRecipes} so the Smithy GUI can read them.
 * Also clears the cache when the player disconnects.
 */
@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
public final class SmithyRecipeSyncClient {

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        var synced = event.getRecipeMap().byType(GotModRecipeTypes.SMITHY.get());
        SmithyClientRecipes.set(new ArrayList<>(synced));
        GotMod.LOGGER.debug("[SmithySync] Received {} smithy recipe(s) from server.", synced.size());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        SmithyClientRecipes.clear();
    }
}
