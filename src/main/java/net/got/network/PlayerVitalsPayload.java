package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PlayerVitalsPayload(float bodyTemp, float thirst) implements CustomPacketPayload {

    public static final Identifier ID_LOC =
            Identifier.fromNamespaceAndPath("got", "player_vitals");
    public static final Type<PlayerVitalsPayload> TYPE = new Type<>(ID_LOC);

    public static final StreamCodec<FriendlyByteBuf, PlayerVitalsPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> { buf.writeFloat(pkt.bodyTemp); buf.writeFloat(pkt.thirst); },
                    buf -> new PlayerVitalsPayload(buf.readFloat(), buf.readFloat())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
