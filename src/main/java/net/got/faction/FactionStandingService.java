package net.got.faction;

import net.minecraft.server.level.ServerPlayer;

/**
 * Evaluates the relationship between a player and any given faction,
 * taking into account the player's chosen faction, their standing, and
 * the diplomatic web of allies and enemies defined in {@link GotFactions}.
 *
 * <p>This is the single source of truth for questions like:
 * <ul>
 *   <li>"Will this NPC attack the player?"</li>
 *   <li>"Can the player open the faction shop?"</li>
 *   <li>"What colour is the faction name above the NPC's head?"</li>
 * </ul>
 */
public final class FactionStandingService {

    /**
     * Disposition of a player toward a given faction.
     * Used to drive NPC behaviour, UI colour-coding, and gate checks.
     */
    public enum Disposition {
        /**
         * Player is a member of this exact faction with adequate standing.
         * Full access: shops, quests, soldiers, structures.
         */
        MEMBER,

        /**
         * Player's faction is diplomatically allied with this faction AND
         * their standing with their own faction is at least Retainer (≥ 1200).
         * Partial access: trading, no-aggression.
         */
        ALLIED,

        /**
         * Player has no strong connection to this faction — neutral or unknown.
         * Basic access: markets, no special privileges or hostility.
         */
        NEUTRAL,

        /**
         * Player's faction is diplomatically hostile with this faction.
         * NPCs become wary; guards may challenge the player.
         */
        UNFRIENDLY,

        /**
         * Player's faction is at war with this faction, or their personal
         * standing has dropped into the hostile range (< 200).
         * NPCs will attack on sight.
         */
        HOSTILE
    }

    /** Standing threshold below which the player is treated as hostile. */
    private static final int HOSTILE_THRESHOLD    =  200;
    /** Standing threshold required before allied faction benefits kick in. */
    private static final int ALLY_STANDING_MINIMUM = 1200;

    /**
     * Returns the player's {@link Disposition} toward {@code targetFactionId}.
     *
     * @param player         The server-side player.
     * @param targetFactionId The faction whose disposition we are evaluating.
     */
    public static Disposition getDisposition(ServerPlayer player, String targetFactionId) {
        if (!PlayerFactionState.hasFaction(player)) return Disposition.NEUTRAL;

        String playerFactionId = PlayerFactionState.getFactionId(player);
        int standing = PlayerFactionState.getStanding(player);

        // ── Direct member ─────────────────────────────────────────────────────
        if (playerFactionId.equals(targetFactionId)) {
            return (standing >= HOSTILE_THRESHOLD) ? Disposition.MEMBER : Disposition.HOSTILE;
        }

        // ── Allied faction ────────────────────────────────────────────────────
        GotFactionData playerFaction = GotFactions.get(playerFactionId);
        if (playerFaction != null && playerFaction.isAlly(targetFactionId)) {
            if (standing < HOSTILE_THRESHOLD) return Disposition.UNFRIENDLY;
            return (standing >= ALLY_STANDING_MINIMUM) ? Disposition.ALLIED : Disposition.NEUTRAL;
        }

        // ── Enemy faction ─────────────────────────────────────────────────────
        if (playerFaction != null && playerFaction.isEnemy(targetFactionId)) {
            return (standing >= ALLY_STANDING_MINIMUM) ? Disposition.UNFRIENDLY : Disposition.HOSTILE;
        }

        // ── No defined relationship — neutral ─────────────────────────────────
        return Disposition.NEUTRAL;
    }

    /**
     * Convenience: returns {@code true} if the player will be attacked on sight
     * by NPCs of {@code targetFactionId}.
     */
    public static boolean isHostileTo(ServerPlayer player, String targetFactionId) {
        return getDisposition(player, targetFactionId) == Disposition.HOSTILE;
    }

    /**
     * Convenience: returns {@code true} if the player can access
     * faction-restricted shops and services.
     */
    public static boolean canAccess(ServerPlayer player, String targetFactionId) {
        Disposition d = getDisposition(player, targetFactionId);
        return d == Disposition.MEMBER || d == Disposition.ALLIED;
    }

    /**
     * Returns an ARGB colour to tint the faction label displayed in GUIs and
     * over NPC heads.
     *
     * <ul>
     *   <li>MEMBER  → bright gold</li>
     *   <li>ALLIED  → light green</li>
     *   <li>NEUTRAL → grey-white</li>
     *   <li>UNFRIENDLY → orange</li>
     *   <li>HOSTILE → red</li>
     * </ul>
     */
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
