package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent client→server when the player clicks a recipe in the Forge's
 * Alloying screen. {@code recipeIndex} is the position within the sorted
 * list of matching AlloyRecipes for the current pair of inputs (-1 to
 * deselect).
 */
public record SelectAlloyRecipePayload(int recipeIndex) implements CustomPacketPayload {

    public static final Type<SelectAlloyRecipePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("got", "select_alloy_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectAlloyRecipePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.recipeIndex),
                    buf -> new SelectAlloyRecipePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
