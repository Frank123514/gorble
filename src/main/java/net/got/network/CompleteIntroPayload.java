package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent when the player clicks Proceed on the closing "Very well." line.
 * The player is already standing in the knownworld (this whole sequence
 * only ever runs on a world generated with the knownworld preset), so this
 * doesn't teleport anyone anywhere — it just marks character creation done
 * so the intro never plays again for them.
 */
public record CompleteIntroPayload() implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "complete_intro");

    public static final Type<CompleteIntroPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, CompleteIntroPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {  },
                    buf -> new CompleteIntroPayload()
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
