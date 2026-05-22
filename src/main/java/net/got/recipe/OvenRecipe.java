package net.got.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.got.init.GotModRecipeSerializers;
import net.got.init.GotModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A shaped 3×3 cooking recipe for the Oven block.
 *
 * Key design decisions vs the old implementation:
 *  - Uses OvenInput (our own RecipeInput) instead of CraftingInput.
 *  - Ingredient list is stored as a flat List<Optional<Ingredient>> of exactly
 *    9 entries (one per grid slot, row-major). Optional.empty() = empty slot.
 *    In 1.21.4, Ingredient can never be empty, so Optional is required.
 *  - Pattern matching supports mirroring (left↔right).
 *
 * JSON format:
 * {
 *   "type": "got:oven",
 *   "pattern": ["ABC", "DEF", "GHI"],
 *   "key": { "A": { "item": "minecraft:wheat" }, ... },
 *   "result": { "id": "got:bread_loaf", "count": 1 },
 *   "cookingtime": 200
 * }
 */
public class OvenRecipe implements Recipe<OvenRecipe.OvenInput> {

    // ── Inner RecipeInput ──────────────────────────────────────────────────

    public record OvenInput(NonNullList<ItemStack> grid) implements RecipeInput {
        public OvenInput {
            if (grid.size() != 9)
                throw new IllegalArgumentException("OvenInput grid must have exactly 9 slots");
        }

        @Override public ItemStack getItem(int index) { return grid.get(index); }
        @Override public int size() { return 9; }
    }

    // ── Fields ─────────────────────────────────────────────────────────────

    /**
     * Flat 9-slot grid, row-major (slot 0 = top-left).
     * Optional.empty() = this slot must be empty in the input.
     */
    private final List<Optional<Ingredient>> ingredients;
    private final ItemStack result;
    private final int cookingTime;

    // ── Constructor ────────────────────────────────────────────────────────

    public OvenRecipe(List<Optional<Ingredient>> ingredients, ItemStack result, int cookingTime) {
        if (ingredients.size() != 9)
            throw new IllegalArgumentException("OvenRecipe must have exactly 9 ingredient slots");
        this.ingredients = Collections.unmodifiableList(new ArrayList<>(ingredients));
        this.result       = result;
        this.cookingTime  = cookingTime;
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    public int getCookingTime()                              { return cookingTime; }
    public ItemStack getResultItem()                         { return result.copy(); }
    public List<Optional<Ingredient>> getIngredientSlots()  { return ingredients; }

    /** Returns only the non-empty ingredients (for JEI / display purposes). */
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        for (Optional<Ingredient> opt : ingredients) {
            opt.ifPresent(list::add);
        }
        return list;
    }

    // ── Recipe<OvenInput> ──────────────────────────────────────────────────

    @Override
    public boolean matches(OvenInput input, Level level) {
        return matchesNormal(input) || matchesMirrored(input);
    }

    private boolean matchesNormal(OvenInput input) {
        for (int i = 0; i < 9; i++) {
            if (!slotMatches(ingredients.get(i), input.getItem(i))) return false;
        }
        return true;
    }

    private boolean matchesMirrored(OvenInput input) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int mirroredCol = 2 - col;
                Optional<Ingredient> ing = ingredients.get(col + row * 3);
                ItemStack item = input.getItem(mirroredCol + row * 3);
                if (!slotMatches(ing, item)) return false;
            }
        }
        return true;
    }

    /**
     * Empty Optional = slot must be empty; present Ingredient = ingredient must match.
     */
    private static boolean slotMatches(Optional<Ingredient> slot, ItemStack stack) {
        return slot.map(ing -> ing.test(stack)).orElse(stack.isEmpty());
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
        List<Optional<Ingredient>> grid = new ArrayList<>(9);
        for (int row = 0; row < 3; row++) {
            String line = row < patternRows.size() ? patternRows.get(row) : "   ";
            for (int col = 0; col < 3; col++) {
                char c = col < line.length() ? line.charAt(col) : ' ';
                grid.add(c == ' ' ? Optional.empty()
                        : Optional.ofNullable(key.get(c)));
            }
        }
        return new OvenRecipe(grid, result, cookingTime);
    }

    private static List<String> rawPattern(List<Optional<Ingredient>> ingredients) {
        StringBuilder[] rows = { new StringBuilder("   "),
                new StringBuilder("   "), new StringBuilder("   ") };
        char next = 'A';
        for (int i = 0; i < 9; i++) {
            if (ingredients.get(i).isPresent()) {
                rows[i / 3].setCharAt(i % 3, next++);
            }
        }
        return List.of(rows[0].toString(), rows[1].toString(), rows[2].toString());
    }

    private static java.util.Map<Character, Ingredient> rawKey(List<Optional<Ingredient>> ingredients) {
        java.util.Map<Character, Ingredient> map = new java.util.LinkedHashMap<>();
        char[] next = {'A'};
        for (Optional<Ingredient> opt : ingredients) {
            if (opt.isPresent()) {
                map.put(next[0]++, opt.get());
            }
        }
        return map;
    }

    // ── StreamCodec ────────────────────────────────────────────────────────

    public static final StreamCodec<RegistryFriendlyByteBuf, OvenRecipe> STREAM_CODEC =
            StreamCodec.of(
                    (buf, recipe) -> {
                        for (int i = 0; i < 9; i++) {
                            Optional<Ingredient> opt = recipe.ingredients.get(i);
                            buf.writeBoolean(opt.isPresent());
                            opt.ifPresent(ing -> Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing));
                        }
                        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                        buf.writeVarInt(recipe.cookingTime);
                    },
                    buf -> {
                        List<Optional<Ingredient>> grid = new ArrayList<>(9);
                        for (int i = 0; i < 9; i++) {
                            boolean present = buf.readBoolean();
                            grid.add(present
                                    ? Optional.of(Ingredient.CONTENTS_STREAM_CODEC.decode(buf))
                                    : Optional.empty());
                        }
                        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                        int cookingTime  = buf.readVarInt();
                        return new OvenRecipe(grid, result, cookingTime);
                    }
            );
}