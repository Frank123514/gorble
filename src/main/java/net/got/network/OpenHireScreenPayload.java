package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>server → client</b> when a player sneak-right-clicks an unemployed
 * NPC.  The client opens {@link net.got.client.gui.NpcHireScreen} for the
 * given entity so the player can choose a job.
 */
public record OpenHireScreenPayload(int entityId) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "open_hire_screen");

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
