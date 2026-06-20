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
 * recipes in {@link AlloyClientRecipes} so the Smithy GUI can read them.
 * Also clears the cache when the player disconnects.
 */
@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public final class AlloyRecipeSyncClient {

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        var synced = event.getRecipeMap().byType(GotModRecipeTypes.ALLOY.get());
        AlloyClientRecipes.set(new ArrayList<>(synced));
        GotMod.LOGGER.debug("[AlloySync] Received {} alloy recipe(s) from server.", synced.size());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        AlloyClientRecipes.clear();
    }
}
