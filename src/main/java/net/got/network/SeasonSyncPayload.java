package net.got.network;

import net.got.climate.Season;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SeasonSyncPayload(Season season) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "season_sync");

    public static final Type<SeasonSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SeasonSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> buf.writeEnum(pkt.season),
                    buf -> new SeasonSyncPayload(buf.readEnum(Season.class))
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
