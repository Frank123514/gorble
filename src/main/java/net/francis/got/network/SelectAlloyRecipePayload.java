package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SelectAlloyRecipePayload(int recipeIndex) implements CustomPacketPayload {

    public static final Type<SelectAlloyRecipePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("got", "select_alloy_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectAlloyRecipePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.recipeIndex),
                    buf -> new SelectAlloyRecipePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
