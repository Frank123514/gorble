package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OpenHireScreenPayload(int entityId) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_hire_screen");

    public static final Type<OpenHireScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenHireScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeInt(pkt.entityId),
                    buf -> new OpenHireScreenPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
