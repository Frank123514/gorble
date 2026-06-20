package net.got.client;

import net.got.recipe.AlloyRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the alloy recipe list that was last synced from the server.
 * Populated by {@link AlloyRecipeSyncClient} on {@code RecipesReceivedEvent}.
 */
@OnlyIn(Dist.CLIENT)
public final class AlloyClientRecipes {

    private static List<RecipeHolder<AlloyRecipe>> recipes = Collections.emptyList();

    private AlloyClientRecipes() {}

    public static void set(List<RecipeHolder<AlloyRecipe>> incoming) {
        recipes = Collections.unmodifiableList(new ArrayList<>(incoming));
    }

    public static List<RecipeHolder<AlloyRecipe>> get() {
        return recipes;
    }

    public static void clear() {
        recipes = Collections.emptyList();
    }
}
