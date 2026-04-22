package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>server → client</b> when a player shift-right-clicks any NPC.
 * The client opens {@link net.got.client.gui.NpcInteractScreen}.
 */
public record OpenInteractScreenPayload(int entityId, String occupationId, String npcName)
        implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "open_interact_screen");

    public static final Type<OpenInteractScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenInteractScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.entityId);
                        buf.writeUtf(pkt.occupationId);
                        buf.writeUtf(pkt.npcName);
                    },
                    buf -> new OpenInteractScreenPayload(
                            buf.readInt(), buf.readUtf(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
