package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenFactionScreenPayload() implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_faction_screen");

    public static final Type<OpenFactionScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenFactionScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {  },
                    buf -> new OpenFactionScreenPayload()
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}