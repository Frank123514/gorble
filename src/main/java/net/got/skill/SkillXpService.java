package net.got.skill;

import net.got.network.SkillSyncPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public final class SkillXpService {

    public static void grantXp(ServerPlayer player, Skill skill, int baseAmount) {
        if (baseAmount <= 0) return;

        int oldLevel = PlayerSkillState.getLevel(player, skill);
        int adjusted = Math.round(baseAmount * SkillPerkEffects.xpGainMultiplier(player, skill));
        PlayerSkillState.addXp(player, skill, adjusted);
        int newLevel = PlayerSkillState.getLevel(player, skill);

        if (newLevel > oldLevel) {
            onLevelUp(player, skill, oldLevel, newLevel);
        }
    }

    private static void onLevelUp(ServerPlayer player, Skill skill, int oldLevel, int newLevel) {
        player.displayClientMessage(Component.literal("")
                        .append(Component.literal("Skill increased! ").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                        .append(Component.literal(skill.displayName + " ").withStyle(ChatFormatting.YELLOW))
                        .append(Component.literal("is now level " + newLevel + ".").withStyle(ChatFormatting.YELLOW)),
                true);

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.4f, 1.6f);

        List<SkillPerk> newlyAvailable = new ArrayList<>();
        for (SkillPerk perk : SkillPerks.forSkill(skill)) {
            if (!PlayerSkillState.hasPerk(player, perk.id())
                    && perk.levelRequirement() > oldLevel
                    && perk.levelRequirement() <= newLevel) {
                newlyAvailable.add(perk);
            }
        }
        for (SkillPerk perk : newlyAvailable) {
            player.sendSystemMessage(Component.literal("A new perk is available: ")
                    .withStyle(ChatFormatting.AQUA)
                    .append(Component.literal(perk.name()).withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD))
                    .append(Component.literal(" (" + skill.displayName + ")").withStyle(ChatFormatting.GRAY)));
        }

        syncToClient(player);
    }

    public static void syncToClient(ServerPlayer player) {
        int[] xp = new int[Skill.values().length];
        for (Skill skill : Skill.values()) {
            xp[skill.ordinal()] = PlayerSkillState.getXp(player, skill);
        }
        PacketDistributor.sendToPlayer(player,
                new SkillSyncPayload(xp, PlayerSkillState.getUnlockedPerkIds(player)));
    }

    private SkillXpService() {}
}