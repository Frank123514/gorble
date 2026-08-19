package net.francis.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record UnlockPerkPayload(String perkId) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "unlock_perk");

    public static final Type<UnlockPerkPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, UnlockPerkPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.perkId),
                    buf -> new UnlockPerkPayload(buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}