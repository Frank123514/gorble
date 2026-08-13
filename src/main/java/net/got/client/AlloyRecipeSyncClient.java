package net.got.client;

import net.got.GotMod;
import net.got.init.ModRecipeTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;

@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
public final class AlloyRecipeSyncClient {

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        var synced = event.getRecipeMap().byType(ModRecipeTypes.ALLOY.get());
        AlloyClientRecipes.set(new ArrayList<>(synced));
        GotMod.LOGGER.debug("[AlloySync] Received {} alloy recipe(s) from server.", synced.size());
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        AlloyClientRecipes.clear();
    }
}
