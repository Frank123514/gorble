package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PlayerTemperaturePayload(float temperature) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "player_temperature");

    public static final Type<PlayerTemperaturePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, PlayerTemperaturePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeFloat(pkt.temperature),
                    buf -> new PlayerTemperaturePayload(buf.readFloat())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}