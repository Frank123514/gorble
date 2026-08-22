package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent when the player clicks Proceed on the closing "Very well." line —
 * the actual trigger for teleporting them out of the vanilla overworld and
 * into the knownworld dimension at their faction's spawn point.
 */
public record EnterKnownWorldPayload() implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "enter_known_world");

    public static final Type<EnterKnownWorldPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, EnterKnownWorldPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {  },
                    buf -> new EnterKnownWorldPayload()
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
