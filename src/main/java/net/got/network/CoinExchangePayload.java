package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent <b>client → server</b> when the player clicks Break or Combine
 * in the coin exchange screen.
 *
 * <p>{@code fromCoinId} — the denomination being converted.
 * {@code toSmaller} — if {@code true}, break 1 of {@code fromCoinId} into
 * {@code ratio} of the next-smaller denomination; if {@code false}, combine
 * {@code ratio} of the next-smaller into 1 of {@code fromCoinId}.
 */
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
