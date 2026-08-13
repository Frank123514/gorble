package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenInteractScreenPayload(int entityId, String occupationId,
                                        String npcName, String militaryTitle)
        implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_interact_screen");

    public static final Type<OpenInteractScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenInteractScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.entityId);
                        buf.writeUtf(pkt.occupationId);
                        buf.writeUtf(pkt.npcName);
                        buf.writeUtf(pkt.militaryTitle);
                    },
                    buf -> new OpenInteractScreenPayload(
                            buf.readInt(), buf.readUtf(), buf.readUtf(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public boolean isCivilian() { return militaryTitle == null || militaryTitle.isEmpty(); }
}
