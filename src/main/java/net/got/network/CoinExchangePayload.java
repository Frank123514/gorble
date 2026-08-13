package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CoinExchangePayload(String fromCoinId, boolean toSmaller)
        implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "coin_exchange");

    public static final Type<CoinExchangePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, CoinExchangePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeUtf(pkt.fromCoinId);
                        buf.writeBoolean(pkt.toSmaller);
                    },
                    buf -> new CoinExchangePayload(buf.readUtf(), buf.readBoolean())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
