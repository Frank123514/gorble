package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent client→server when the player clicks a recipe in the Smithing Anvil GUI.
 */
public record SelectSmithingAnvilRecipePayload(int recipeIndex) implements CustomPacketPayload {

    public static final Type<SelectSmithingAnvilRecipePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("got", "select_smithing_anvil_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectSmithingAnvilRecipePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.recipeIndex),
                    buf -> new SelectSmithingAnvilRecipePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
