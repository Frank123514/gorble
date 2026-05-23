package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent client→server when the player clicks a recipe in the Smithy screen.
 * {@code recipeIndex} is the position within the sorted list of matching
 * SmithyRecipes for the current input item (-1 to deselect).
 */
public record SelectSmithyRecipePayload(int recipeIndex) implements CustomPacketPayload {

    public static final Type<SelectSmithyRecipePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("got", "select_smithy_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectSmithyRecipePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.recipeIndex),
                    buf -> new SelectSmithyRecipePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}