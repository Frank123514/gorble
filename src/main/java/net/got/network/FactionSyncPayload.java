package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FactionSyncPayload(
        String factionId,
        int    standing,
        String title
) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "faction_sync");

    public static final Type<FactionSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, FactionSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeUtf(pkt.factionId);
                        buf.writeInt(pkt.standing);
                        buf.writeUtf(pkt.title);
                    },
                    buf -> new FactionSyncPayload(
                            buf.readUtf(),
                            buf.readInt(),
                            buf.readUtf()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
