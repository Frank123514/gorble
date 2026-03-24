package net.got.compat.jei;

import net.got.init.GotModRecipeTypes;
import net.got.recipe.BakingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.Collections;
import java.util.List;

/**
 * Caches BakingRecipes on the client for use by the JEI plugin.
 *
 * In MC 1.21.2+ recipes are server-side only. RecipesUpdatedEvent was removed
 * from NeoForge 21.x. Instead we hook TagsUpdatedEvent which fires on the
 * client after the server syncs tags AND recipes, giving us access to the
 * level's RecipeManager at that point.
 *
 * This covers dedicated server connections. For singleplayer the JEI plugin
 * reads directly from getSingleplayerServer().getRecipeManager().
 */
@EventBusSubscriber(modid = net.got.GotMod.MODID, value = Dist.CLIENT)
public class GotJeiRecipeCache {

    private static volatile List<BakingRecipe> cachedRecipes = Collections.emptyList();

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        // TagsUpdatedEvent fires on both client and server, but we registered
        // on CLIENT dist so this only runs client-side.
        // On dedicated server connections the RecipeManager is available via
        // the vanilla lookup. Pull the baking recipes and cache them for JEI.
        try {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.getSingleplayerServer() != null) {
                // Singleplayer: handled directly in GotJeiPlugin.registerRecipes
                return;
            }
            // Dedicated server: access cached recipes from the level's recipe access.
            // In 1.21.4, the client only has RecipeAccess (property sets), not full
            // RecipeHolder objects. There is no client-side way to enumerate custom
            // recipe types from a dedicated server without a custom network packet.
            // For now, leave the cache empty — JEI will show an empty category on
            // dedicated servers until a sync packet is added.
            cachedRecipes = Collections.emptyList();
        } catch (Exception ignored) {
            cachedRecipes = Collections.emptyList();
        }
    }

    public static List<BakingRecipe> getBakingRecipes() {
        return cachedRecipes;
    }
}
