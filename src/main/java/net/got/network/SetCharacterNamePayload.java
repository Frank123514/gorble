package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** Sent once the player types their name and clicks Proceed in the intro. */
public record SetCharacterNamePayload(String name) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "set_character_name");

    public static final Type<SetCharacterNamePayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SetCharacterNamePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.name),
                    buf -> new SetCharacterNamePayload(buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
