package net.got.event;

import net.got.GotMod;
import net.got.init.GotModRecipeTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

/**
 * Tells NeoForge to sync all got:smithy recipes to connected clients
 * whenever datapacks are (re)loaded.  Without this the client RecipeMap
 * is empty for custom recipe types and the Smithy GUI shows nothing.
 */
@EventBusSubscriber(modid = GotMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class SmithyRecipeSync {

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(GotModRecipeTypes.SMITHY.get());
    }
}
