package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent client→server when the player clicks the mode tab in the Forge GUI
 * (Smithing &lt;-&gt; Alloying). The server sets the Forge's mode and
 * re-opens the menu so the player gets the correct screen/slots.
 */
public record SelectForgeModePayload(int mode) implements CustomPacketPayload {

    public static final Type<SelectForgeModePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("got", "select_forge_mode"));

    public static final StreamCodec<FriendlyByteBuf, SelectForgeModePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> buf.writeInt(payload.mode),
                    buf -> new SelectForgeModePayload(buf.readInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
