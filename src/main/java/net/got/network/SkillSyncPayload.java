package net.got.network;

import net.got.skill.GotSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent <b>server → client</b> to sync the player's full skill state - total
 * XP for every {@link GotSkill} plus every perk id they've unlocked - so the
 * Skills screen and any future HUD can render without an extra round-trip.
 *
 * <p>Send this:
 * <ul>
 *   <li>On login / respawn (see {@code GotPlayerEvents}).</li>
 *   <li>Whenever a skill levels up (see {@code SkillXpService#grantXp}).</li>
 *   <li>Whenever a perk is unlocked (see {@code GotNetwork}'s handler for {@link UnlockPerkPayload}).</li>
 *   <li>Periodically (every second) so XP-bar progress stays live while the screen is open.</li>
 * </ul>
 *
 * @param xpBySkillOrdinal Total XP per skill, indexed by {@link GotSkill#ordinal()}.
 * @param unlockedPerkIds  Every perk id the player has unlocked, across all skills.
 */
public record SkillSyncPayload(
        int[] xpBySkillOrdinal,
        List<String> unlockedPerkIds
) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath("got", "skill_sync");

    public static final Type<SkillSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, SkillSyncPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        for (int i = 0; i < GotSkill.values().length; i++) {
                            buf.writeInt(i < pkt.xpBySkillOrdinal.length ? pkt.xpBySkillOrdinal[i] : 0);
                        }
                        buf.writeVarInt(pkt.unlockedPerkIds.size());
                        for (String id : pkt.unlockedPerkIds) buf.writeUtf(id);
                    },
                    buf -> {
                        int[] xp = new int[GotSkill.values().length];
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