package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>client → server</b> when the player closes {@link net.got.client.gui.NpcInteractScreen}.
 * The server calls {@code stopTalking()} on the NPC so it resumes normal behaviour.
 */
public record CloseInteractScreenPayload(int entityId)
        implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "close_interact_screen");

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
