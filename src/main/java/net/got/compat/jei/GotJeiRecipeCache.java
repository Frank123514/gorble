package net.got.compat.jei;

import net.got.init.GotModRecipeTypes;
import net.got.recipe.OvenRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.Collections;
import java.util.List;

/**
 * Client-side cache of OvenRecipes for JEI on dedicated servers.
 *
 * In MC 1.21.2+ recipes are server-side only. TagsUpdatedEvent fires on the
 * client after the server syncs tags and recipes, giving us access to the
 * integrated server's RecipeManager at that point.
 *
 * For singleplayer the JEI plugin reads directly from
 * getSingleplayerServer().getRecipeManager(). This cache covers dedicated
 * server connections, though it remains empty until a custom sync packet is
 * added (MC 1.21 does not expose full recipe lists to the client remotely).
 */
@EventBusSubscriber(modid = net.got.GotMod.MODID, value = Dist.CLIENT)
public class GotJeiRecipeCache {

    private static volatile List<OvenRecipe> cachedRecipes = Collections.emptyList();

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        try {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.getSingleplayerServer() != null) {
                // Singleplayer: handled directly in GotJeiPlugin.registerRecipes
                return;
            }
            // Dedicated server: no client-side enumeration of custom recipe types
            // available in 1.21.4 without a custom sync packet. Leave cache empty.
            cachedRecipes = Collections.emptyList();
        } catch (Exception ignored) {
            cachedRecipes = Collections.emptyList();
        }
    }

    public static List<OvenRecipe> getOvenRecipes() {
        return cachedRecipes;
    }
}
