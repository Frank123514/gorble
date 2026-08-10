package net.got.network;

import net.got.climate.GotSeason;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent <b>server → client</b> whenever the season changes, and to each player
 * on login, so the client's BiomeMixin knows whether it's winter.
 */
public record SeasonSyncPayload(GotSeason season) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "season_sync");

    public static final Type<SeasonSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SeasonSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeEnum(pkt.season),
                    buf -> new SeasonSyncPayload(buf.readEnum(GotSeason.class))
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
