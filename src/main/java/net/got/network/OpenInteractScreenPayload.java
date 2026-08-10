package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent <b>server → client</b> when a player right-clicks any NPC.
 * The client opens {@link net.got.client.gui.NpcInteractScreen}.
 *
 * <p>{@code militaryTitle} is the rank string ("Levy", "Soldier", "Knight", …)
 * for non-civilian NPCs, or an empty string for civilians.  The screen uses
 * this to decide whether to show civilian job buttons or just the rank label.
 */
public record OpenInteractScreenPayload(int entityId, String occupationId,
                                        String npcName, String militaryTitle)
        implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "open_interact_screen");

    public static final Type<OpenInteractScreenPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, OpenInteractScreenPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeInt(pkt.entityId);
                        buf.writeUtf(pkt.occupationId);
                        buf.writeUtf(pkt.npcName);
                        buf.writeUtf(pkt.militaryTitle);
                    },
                    buf -> new OpenInteractScreenPayload(
                            buf.readInt(), buf.readUtf(), buf.readUtf(), buf.readUtf())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    /** True when this NPC is a civilian who can hold occupations. */
    public boolean isCivilian() { return militaryTitle == null || militaryTitle.isEmpty(); }
}
