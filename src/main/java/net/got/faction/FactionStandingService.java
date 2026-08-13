package net.got.faction;

import net.minecraft.server.level.ServerPlayer;

public final class FactionStandingService {

    public enum Disposition {
        
        MEMBER,

        ALLIED,

        NEUTRAL,

        UNFRIENDLY,

        HOSTILE
    }

    private static final int HOSTILE_THRESHOLD    =  200;
    
    private static final int ALLY_STANDING_MINIMUM = 1200;

    public static Disposition getDisposition(ServerPlayer player, String targetFactionId) {
        if (!PlayerFactionState.hasFaction(player)) return Disposition.NEUTRAL;

        String playerFactionId = PlayerFactionState.getFactionId(player);
        int standing = PlayerFactionState.getStanding(player);

        if (playerFactionId.equals(targetFactionId)) {
            return (standing >= HOSTILE_THRESHOLD) ? Disposition.MEMBER : Disposition.HOSTILE;
        }

        FactionData playerFaction = Factions.get(playerFactionId);
        if (playerFaction != null && playerFaction.isAlly(targetFactionId)) {
            if (standing < HOSTILE_THRESHOLD) return Disposition.UNFRIENDLY;
            return (standing >= ALLY_STANDING_MINIMUM) ? Disposition.ALLIED : Disposition.NEUTRAL;
        }

        if (playerFaction != null && playerFaction.isEnemy(targetFactionId)) {
            return (standing >= ALLY_STANDING_MINIMUM) ? Disposition.UNFRIENDLY : Disposition.HOSTILE;
        }

        return Disposition.NEUTRAL;
    }

    public static boolean isHostileTo(ServerPlayer player, String targetFactionId) {
        return getDisposition(player, targetFactionId) == Disposition.HOSTILE;
    }

    public static boolean canAccess(ServerPlayer player, String targetFactionId) {
        Disposition d = getDisposition(player, targetFactionId);
        return d == Disposition.MEMBER || d == Disposition.ALLIED;
    }

    public static int dispositionColour(Disposition disposition) {
        return switch (disposition) {
            case MEMBER     -> 0xFFFFD700;
            case ALLIED     -> 0xFF6FBF6F;
            case NEUTRAL    -> 0xFFCCCCAA;
            case UNFRIENDLY -> 0xFFFF8C00;
            case HOSTILE    -> 0xFFCC2222;
        };
    }

    private FactionStandingService() {}
}
