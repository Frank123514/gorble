package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>server → client</b> to trigger a combat animation on the receiving
 * player's arm layer.
 *
 * <p>{@code poseId} is the ordinal of {@link net.got.client.animation.GotArmPose}
 * so we avoid sending the full class name over the wire.
 */
public record GotCombatAnimPayload(int poseId) implements CustomPacketPayload {

    public static final ResourceLocation ID_LOC =
            ResourceLocation.fromNamespaceAndPath("got", "combat_anim");
    public static final Type<GotCombatAnimPayload> TYPE = new Type<>(ID_LOC);

    public static final StreamCodec<FriendlyByteBuf, GotCombatAnimPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeByte(pkt.poseId),
                    buf -> new GotCombatAnimPayload(buf.readByte() & 0xFF)
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
