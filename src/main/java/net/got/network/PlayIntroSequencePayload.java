package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent on login to a player who hasn't entered the knownworld dimension yet,
 * to open the black-screen "waking up" intro on the client (see IntroScreen).
 *
 * resumeAtFinalLine is true when the player already picked a faction on a
 * previous session but disconnected before clicking through to the final
 * teleport - in that case we skip straight to the closing "Very well." line
 * instead of replaying character creation from scratch.
 */
public record PlayIntroSequencePayload(boolean resumeAtFinalLine) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "play_intro_sequence");

    public static final Type<PlayIntroSequencePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, PlayIntroSequencePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeBoolean(pkt.resumeAtFinalLine),
                    buf -> new PlayIntroSequencePayload(buf.readBoolean())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
