package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>client → server</b> when the player clicks an unlockable perk node
 * on the Skills screen. The server independently re-validates the request
 * (level requirement, chain order, available points) in
 * {@link net.got.skill.PlayerSkillState#unlockPerk} - the client's UI only
 * pre-filters which nodes are clickable, it is never trusted directly.
 *
 * @param perkId The {@link net.got.skill.SkillPerk} id being requested.
 */
public record UnlockPerkPayload(String perkId) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "unlock_perk");

    public static final Type<UnlockPerkPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, UnlockPerkPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeUtf(pkt.perkId),
                    buf -> new UnlockPerkPayload(buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}