package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * waypointName is whichever WaypointData the player had dialed in on the
 * location nav when they hit Confirm (may be empty if the faction has no
 * waypoints, e.g. the Night's Watch) - it's what the closing CompleteIntroPayload
 * teleport target is resolved from.
 */
public record SelectFactionPayload(String factionId, String waypointName) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "select_faction");

    public static final Type<SelectFactionPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SelectFactionPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> { buf.writeUtf(pkt.factionId); buf.writeUtf(pkt.waypointName); },
                    buf -> new SelectFactionPayload(buf.readUtf(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
