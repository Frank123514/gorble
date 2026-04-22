package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>client → server</b> when the player clicks "Trade" in
 * {@link net.got.client.gui.NpcInteractScreen}.
 * The server looks up the entity and opens the {@link net.got.menu.NpcTradeMenu}.
 */
public record RequestTradeMenuPayload(int entityId) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "request_trade_menu");

    public static final Type<RequestTradeMenuPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, RequestTradeMenuPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeInt(pkt.entityId),
                    buf -> new RequestTradeMenuPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
