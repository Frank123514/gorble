package net.got.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * A shaped 3×3 cooking recipe for the Oven block.
 *
 * Key design decisions vs the old implementation:
 *  - Uses OvenInput (our own RecipeInput) instead of CraftingInput.
 *    CraftingInput triggers vanilla crafting logic and pattern matching that
 *    is incompatible with a furnace-style block — that was the root cause of
 *    recipes never matching in-game.
 *  - Ingredient list is stored as a flat NonNullList<Ingredient> of exactly
 *    9 entries (one per grid slot, row-major, empty slots = Ingredient.EMPTY).
 *    This matches the slot layout of OvenMenu and OvenBlockEntity directly,
 *    so matching is a simple index-by-index ingredient test.
 *  - Pattern matching supports mirroring (left↔right) to match vanilla
 *    crafting behaviour that players expect.
 *  - The recipe serializer codec is self-contained; no dependency on
 *    ShapedRecipePattern internals which changed across 1.21.x patch versions.
 *
 * JSON format:
 * {
 *   "type": "got:oven",
 *   "pattern": ["ABC", "DEF", "GHI"],
 *   "key": { "A": { "item": "minecraft:wheat" }, ... },
 *   "result": { "id": "got:bread_loaf", "count": 1 },
 *   "cookingtime": 200
 * }
 *
 * Unused grid positions should be filled with a space character in the pattern.
 * Pattern rows shorter than 3 chars are padded with spaces automatically.
 * All three rows must be present; use " " for a fully empty row.
 */
public class OvenRecipe implements Recipe<OvenRecipe.OvenInput> {

    // ── Inner RecipeInput ──────────────────────────────────────────────────

    /**
     * Simple wrapper around the 9-slot grid so we have our own RecipeInput
     * type that doesn't interfere with CraftingInput dispatch.
     */
    public record OvenInput(NonNullList<ItemStack> grid) implements RecipeInput {
        /** grid must have exactly 9 entries. */
        public OvenInput {
            if (grid.size() != 9)
                throw new IllegalArgumentException("OvenInput grid must have exactly 9 slots");
        }

        @Override public ItemStack getItem(int index) { return grid.get(index); }
        @Override public int size() { return 9; }
    }

    // ── Fields ─────────────────────────────────────────────────────────────

    /** Flat 9-slot ingredient list, row-major (slot 0 = top-left). */
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int cookingTime;

    // ── Constructor ────────────────────────────────────────────────────────

    public OvenRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int cookingTime) {
        if (ingredients.size() != 9)
            throw new IllegalArgumentException("OvenRecipe must have exactly 9 ingredient slots");
        this.ingredients = ingredients;
        this.result       = result;
        this.cookingTime  = cookingTime;
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    public int getCookingTime()                      { return cookingTime; }
    public ItemStack getResultItem()                 { return result.copy(); }
    public NonNullList<Ingredient> getIngredients()  { return ingredients; }

    // ── Recipe<OvenInput> ──────────────────────────────────────────────────

    @Override
    public boolean matches(OvenInput input, Level level) {
        return matchesNormal(input) || matchesMirrored(input);
    }

    /** Check the grid exactly as placed. */
    private boolean matchesNormal(OvenInput input) {
        for (int i = 0; i < 9; i++) {
            if (!ingredients.get(i).test(input.getItem(i))) return false;
        }
        return true;
    }

    /**
     * Check the grid mirrored left↔right (columns 0↔2 swapped, column 1 stays).
     * Matches vanilla crafting "mirror" behaviour.
     */
    private boolean matchesMirrored(OvenInput input) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int mirroredCol = 2 - col;
                Ingredient ing  = ingredients.get(col + row * 3);
                ItemStack item  = input.getItem(mirroredCol + row * 3);
                if (!ing.test(item)) return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(OvenInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public RecipeSerializer<OvenRecipe> getSerializer() {
        return GotModRecipeSerializers.OVEN.get();
    }

    @Override
    public RecipeType<OvenRecipe> getType() {
        return GotModRecipeTypes.OVEN.get();
    }

    // ── Codec ──────────────────────────────────────────────────────────────

    /**
     * Intermediate record used only for serialisation so we can express the
     * pattern/key JSON format without pulling in ShapedRecipePattern.
     */
    private record RawRecipe(List<String> pattern, java.util.Map<Character, Ingredient> key,
                             ItemStack result, int cookingTime) {}

    public static final MapCodec<OvenRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.listOf().fieldOf("pattern").forGetter(r -> rawPattern(r.ingredients)),
            Codec.unboundedMap(
                    Codec.STRING.comapFlatMap(
                            s -> s.length() == 1
                                    ? com.mojang.serialization.DataResult.success(s.charAt(0))
                                    : com.mojang.serialization.DataResult.error(() -> "Key must be a single character"),
                            c -> String.valueOf(c)
                    ),
                    Ingredient.CODEC
            ).fieldOf("key").forGetter(r -> rawKey(r.ingredients)),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
            Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(r -> r.cookingTime)
    ).apply(inst, OvenRecipe::fromRaw));

    private static OvenRecipe fromRaw(List<String> patternRows,
                                      java.util.Map<Character, Ingredient> key,
                                      ItemStack result, int cookingTime) {
        NonNullList<Ingredient> grid = NonNullList.withSize(9, Ingredient.of());
        for (int row = 0; row < 3; row++) {
            String line = row < patternRows.size() ? patternRows.get(row) : "   ";
            for (int col = 0; col < 3; col++) {
                char c = col < line.length() ? line.charAt(col) : ' ';
                grid.set(col + row * 3, c == ' ' ? Ingredient.of()
                        : key.getOrDefault(c, Ingredient.of()));
            }
        }
        return new OvenRecipe(grid, result, cookingTime);
    }

    /** Reconstruct a 3-row pattern for serialisation (inverse of fromRaw). */
    private static List<String> rawPattern(NonNullList<Ingredient> ingredients) {
        // We can't perfectly reconstruct the original character keys,
        // so we generate A-I for non-empty slots. This is only used during
        // data generation / saving, not for matching.
        StringBuilder[] rows = { new StringBuilder("   "),
                new StringBuilder("   "), new StringBuilder("   ") };
        char next = 'A';
        for (int i = 0; i < 9; i++) {
            if (!ingredients.get(i).isEmpty()) {
                rows[i / 3].setCharAt(i % 3, next++);
            }
        }
        return List.of(rows[0].toString(), rows[1].toString(), rows[2].toString());
    }

    private static java.util.Map<Character, Ingredient> rawKey(NonNullList<Ingredient> ingredients) {
        java.util.Map<Character, Ingredient> map = new java.util.LinkedHashMap<>();
        char next = 'A';
        for (Ingredient ing : ingredients) {
            if (!ing.isEmpty()) map.put(next++, ing);
        }
        return map;
    }

    // ── StreamCodec ────────────────────────────────────────────────────────

    /**
     * Network codec: send the flat 9-slot ingredient list + result + cookingTime.
     * Avoids any ShapedRecipePattern dependency over the wire.
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC =
            StreamCodec.of(
                    (buf, recipe) -> {
                        for (int i = 0; i < 9; i++) {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredients.get(i));
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                        buf.writeVarInt(recipe.cookingTime);
                    },
                    buf -> {
                        NonNullList<Ingredient> grid = NonNullList.withSize(9, Ingredient.of());
                        for (int i = 0; i < 9; i++) {
                            grid.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                        }
                        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                        int cookingTime  = buf.readVarInt();
                        return new OvenRecipe(grid, result, cookingTime);
                    }
            );
}