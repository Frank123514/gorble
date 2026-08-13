package net.got.client;

import net.got.recipe.SmithyRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class SmithyClientRecipes {

    private static List<RecipeHolder<SmithyRecipe>> recipes = Collections.emptyList();

    private SmithyClientRecipes() {}

    public static void set(List<RecipeHolder<SmithyRecipe>> incoming) {
        recipes = Collections.unmodifiableList(new ArrayList<>(incoming));
    }

    public static List<RecipeHolder<SmithyRecipe>> get() {
        return recipes;
    }

    public static void clear() {
        recipes = Collections.emptyList();
    }
}
