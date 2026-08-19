package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SelectSmithyRecipePayload(int recipeIndex) implements CustomPacketPayload {

    public static final Type<SelectSmithyRecipePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("got", "select_smithy_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectSmithyRecipePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.recipeIndex),
                    buf -> new SelectSmithyRecipePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}