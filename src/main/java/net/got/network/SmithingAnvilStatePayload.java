package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent server→client to push the current smithing anvil working state
 * so the client HUD overlay can display the timing bar without the GUI open.
 *
 * active=false means the anvil is no longer "armed" (no recipe / ingot removed).
 */
public record SmithingAnvilStatePayload(
        boolean active,
        int markerPos,
        int hitCount,
        int hitsRequired,
        int zoneCenter,
        int zoneHalf,
        int lastHitQuality
) implements CustomPacketPayload {

    public static final Type<SmithingAnvilStatePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("got", "smithing_anvil_state"));

    public static final StreamCodec<FriendlyByteBuf, SmithingAnvilStatePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, p) -> {
                        buf.writeBoolean(p.active);
                        buf.writeInt(p.markerPos);
                        buf.writeInt(p.hitCount);
                        buf.writeInt(p.hitsRequired);
                        buf.writeInt(p.zoneCenter);
                        buf.writeInt(p.zoneHalf);
                        buf.writeInt(p.lastHitQuality);
                    },
                    buf -> new SmithingAnvilStatePayload(
                            buf.readBoolean(),
                            buf.readInt(), buf.readInt(), buf.readInt(),
                            buf.readInt(), buf.readInt(), buf.readInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
