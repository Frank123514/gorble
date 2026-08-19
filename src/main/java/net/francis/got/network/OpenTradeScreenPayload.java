package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenTradeScreenPayload(int entityId, String occupationId, String npcName)
        implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_trade_screen");

    public static final Type<OpenTradeScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenTradeScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.entityId);
                        buf.writeUtf(pkt.occupationId);
                        buf.writeUtf(pkt.npcName);
                    },
                    buf -> new OpenTradeScreenPayload(buf.readInt(), buf.readUtf(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
