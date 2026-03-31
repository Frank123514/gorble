package net.got.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * A shaped 3×3 crafting recipe that also requires fuel and a cook time,
 * ported from OFAW's OvenRecipe to NeoForge 1.21.
 *
 * JSON format:
 * {
 *   "type": "got:oven",
 *   "pattern": ["ABC", "DEF", "GHI"],
 *   "key": { "A": { "item": "..." }, ... },
 *   "result": { "id": "...", "count": 1 },
 *   "cookingtime": 200
 * }
 */
public class OvenRecipe implements Recipe<CraftingInput> {

    private final ShapedRecipePattern pattern;
    private final ItemStack result;
    private final int cookingTime;

    public OvenRecipe(ShapedRecipePattern pattern, ItemStack result, int cookingTime) {
        this.pattern    = pattern;
        this.result     = result;
        this.cookingTime = cookingTime;
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    public int getCookingTime()          { return cookingTime; }
    public ItemStack getResult()         { return result.copy(); }
    public ShapedRecipePattern getPattern() { return pattern; }

    // ── Recipe interface ───────────────────────────────────────────────────

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(pattern.ingredients());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return GotModRecipeSerializers.OVEN_CATEGORY.get();
    }

    @Override
    public RecipeSerializer<OvenRecipe> getSerializer() {
        return GotModRecipeSerializers.OVEN.get();
    }

    @Override
    public RecipeType<OvenRecipe> getType() {
        return GotModRecipeTypes.OVEN.get();
    }

    // ── Codec / StreamCodec ────────────────────────────────────────────────

    public static final MapCodec<OvenRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ShapedRecipePattern.MAP_CODEC
                    .forGetter(r -> r.pattern),
            ItemStack.STRICT_CODEC
                    .fieldOf("result")
                    .forGetter(r -> r.result),
            com.mojang.serialization.Codec.INT
                    .optionalFieldOf("cookingtime", 200)
                    .forGetter(r -> r.cookingTime)
    ).apply(inst, OvenRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ShapedRecipePattern.STREAM_CODEC, r -> r.pattern,
                    ItemStack.STREAM_CODEC,           r -> r.result,
                    ByteBufCodecs.VAR_INT,            r -> r.cookingTime,
                    OvenRecipe::new
            );
}