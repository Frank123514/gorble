package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>server → client</b> once per second to keep the client's displayed
 * temperature in sync with the authoritative server value.
 *
 * <p>The payload carries a single {@code float} in [0.0, 1.0] where 1.0 is
 * fully warm and 0.0 is completely frozen, matching the scale defined in
 * {@link net.got.climate.PlayerTemperatureSystem}.
 */
public record PlayerTemperaturePayload(float temperature) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "player_temperature");

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