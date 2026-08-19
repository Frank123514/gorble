package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SelectForgeModePayload(int mode) implements CustomPacketPayload {

    public static final Type<SelectForgeModePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("got", "select_forge_mode"));

    public static final StreamCodec<FriendlyByteBuf, SelectForgeModePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.mode),
                    buf -> new SelectForgeModePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
