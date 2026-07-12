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

/**
 * Entry point for granting use-based skill XP. Every gameplay hook (mining a
 * block, landing a hit, pulling food from the oven, ...) should call
 * {@link #grantXp} rather than touching {@link PlayerSkillState} directly, so
 * level-up messages, perk-point notifications and client sync all stay
 * consistent in one place.
 */
public final class SkillXpService {

    /**
     * Grants XP to a skill, applying that skill's own XP_GAIN_MULT perks
     * first, then handles any level-ups (chat message, sound, sync).
     *
     * @param baseAmount the un-modified XP amount for this action.
     */
    public static void grantXp(ServerPlayer player, GotSkill skill, int baseAmount) {
        if (baseAmount <= 0) return;

        int oldLevel = PlayerSkillState.getLevel(player, skill);
        int adjusted = Math.round(baseAmount * SkillPerkEffects.xpGainMultiplier(player, skill));
        PlayerSkillState.addXp(player, skill, adjusted);
        int newLevel = PlayerSkillState.getLevel(player, skill);

        if (newLevel > oldLevel) {
            onLevelUp(player, skill, oldLevel, newLevel);
        }
    }

    private static void onLevelUp(ServerPlayer player, GotSkill skill, int oldLevel, int newLevel) {
        player.displayClientMessage(Component.literal("")
                        .append(Component.literal("Skill increased! ").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD))
                        .append(Component.literal(skill.displayName + " ").withStyle(ChatFormatting.YELLOW))
                        .append(Component.literal("is now level " + newLevel + ".").withStyle(ChatFormatting.YELLOW)),
                true); // action bar

        player.level().playSound(null, player.blockPosition(),
                SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.4f, 1.6f);

        // Mention any perks that just became unlockable so the player knows to check the tree.
        List<SkillPerk> newlyAvailable = new ArrayList<>();
        for (SkillPerk perk : GotSkillPerks.forSkill(skill)) {
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

    /** Pushes the player's full skill state to their client. Call after any XP/perk change. */
    public static void syncToClient(ServerPlayer player) {
        int[] xp = new int[GotSkill.values().length];
        for (GotSkill skill : GotSkill.values()) {
            xp[skill.ordinal()] = PlayerSkillState.getXp(player, skill);
        }
        PacketDistributor.sendToPlayer(player,
                new SkillSyncPayload(xp, PlayerSkillState.getUnlockedPerkIds(player)));
    }

    private SkillXpService() {}
}