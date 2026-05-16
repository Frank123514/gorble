package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>client → server</b> when the player presses "Confirm Selection" on
 * the {@link net.got.client.gui.FactionSelectionScreen}.
 *
 * @param factionId The unique faction id chosen, e.g. {@code "north"}.
 */
public record SelectFactionPayload(String factionId) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "select_faction");

    public static final Type<SelectFactionPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SelectFactionPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.factionId),
                    buf -> new SelectFactionPayload(buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}