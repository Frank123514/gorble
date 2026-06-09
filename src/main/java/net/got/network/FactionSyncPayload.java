package net.got.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent <b>server → client</b> to sync the player's faction id, standing value,
 * and current title string so the HUD and selection screen can display live data
 * without an extra round-trip.
 *
 * <p>Send this:
 * <ul>
 *   <li>On login ({@code PlayerLoggedInEvent}).</li>
 *   <li>Whenever standing changes ({@link net.got.faction.PlayerFactionState#modifyStanding}).</li>
 *   <li>Whenever the faction is changed ({@link net.got.faction.PlayerFactionState#setFaction}).</li>
 * </ul>
 *
 * @param factionId The player's faction id string, e.g. {@code "north"}.
 * @param standing  Current reputation value (0 – 10 000).
 * @param title     Current rank title string, e.g. {@code "Bannerman of House Stark"}.
 */
public record FactionSyncPayload(
        String factionId,
        int    standing,
        String title
) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "faction_sync");

    public static final Type<FactionSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, FactionSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeUtf(pkt.factionId);
                        buf.writeInt(pkt.standing);
                        buf.writeUtf(pkt.title);
                    },
                    buf -> new FactionSyncPayload(
                            buf.readUtf(),
                            buf.readInt(),
                            buf.readUtf()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
