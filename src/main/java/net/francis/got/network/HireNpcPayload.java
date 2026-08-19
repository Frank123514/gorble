package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record HireNpcPayload(int entityId, String occupationId) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "hire_npc");

    public static final Type<HireNpcPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, HireNpcPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.entityId);
                        buf.writeUtf(pkt.occupationId);
                    },
                    buf -> new HireNpcPayload(buf.readInt(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
