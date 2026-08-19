package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CloseInteractScreenPayload(int entityId)
        implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "close_interact_screen");

    public static final Type<CloseInteractScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, CloseInteractScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeInt(pkt.entityId),
                    buf -> new CloseInteractScreenPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
