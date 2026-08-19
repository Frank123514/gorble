package net.got.network;

import net.got.skill.Skill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record SkillSyncPayload(
        int[] xpBySkillOrdinal,
        List<String> unlockedPerkIds
) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath("got", "skill_sync");

    public static final Type<SkillSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SkillSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        for (int i = 0; i < Skill.values().length; i++) {
                            buf.writeInt(i < pkt.xpBySkillOrdinal.length ? pkt.xpBySkillOrdinal[i] : 0);
                        }
                        buf.writeVarInt(pkt.unlockedPerkIds.size());
                        for (String id : pkt.unlockedPerkIds) buf.writeUtf(id);
                    },
                    buf -> {
                        int[] xp = new int[Skill.values().length];
                        for (int i = 0; i < xp.length; i++) xp[i] = buf.readInt();
                        int perkCount = buf.readVarInt();
                        List<String> perks = new ArrayList<>(perkCount);
                        for (int i = 0; i < perkCount; i++) perks.add(buf.readUtf());
                        return new SkillSyncPayload(xp, perks);
                    }
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}