package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent <b>server → client</b> when a player joins for the first time (or before
 * they have confirmed a faction) to open the {@link net.got.client.gui.FactionSelectionScreen}.
 *
 * <p>This payload carries no data — its mere receipt is the signal to open the screen.
 */
public record OpenFactionScreenPayload() implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_faction_screen");

    public static final Type<OpenFactionScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenFactionScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> { /* nothing to write */ },
                    buf -> new OpenFactionScreenPayload()
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}