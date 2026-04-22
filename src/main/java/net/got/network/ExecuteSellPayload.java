package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>client → server</b> when the player clicks the Sell button in
 * {@link net.got.client.gui.NpcTradeScreen}.
 *
 * <p>The server looks up the {@link net.got.menu.NpcTradeMenu} for the
 * player, finds the sell offer at {@code offerIndex}, checks that the player
 * has placed the required item in the sell-input slot, removes it, and grants
 * the reward.
 */
public record ExecuteSellPayload(int offerIndex) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "execute_sell");

    public static final Type<ExecuteSellPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ExecuteSellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeInt(pkt.offerIndex),
                    buf -> new ExecuteSellPayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
