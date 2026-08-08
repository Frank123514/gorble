package net.got.faction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

/**
 * Manages the per-player faction state stored in {@link ServerPlayer#getPersistentData()}.
 *
 * <p>Persisted data:
 * <ul>
 *   <li>{@code got.faction}  — the chosen faction id string (e.g. {@code "north"}).</li>
 *   <li>{@code got.standing} — integer reputation with that faction (0 – 10 000).</li>
 *   <li>{@code got.title}    — cached title string, refreshed on standing change.</li>
 * </ul>
 *
 * <p>Use the static helper methods everywhere faction data is read or written to keep
 * all NBT keys in one place.
 */
public final class PlayerFactionState {

    // ── NBT keys ──────────────────────────────────────────────────────────────
    public static final String KEY_FACTION  = "got.faction";
    public static final String KEY_STANDING = "got.standing";
    public static final String KEY_TITLE    = "got.title";

    // ── Standing bounds ───────────────────────────────────────────────────────
    public static final int STANDING_MIN    =     0;
    public static final int STANDING_MAX    = 10_000;
    public static final int STANDING_DEFAULT=   200; // start as a free smallfolk

    // ── Reputation change constants (use these for consistent gain/loss amounts) ─
    /** Gained for completing a faction quest or killing an enemy. */
    public static final int GAIN_QUEST_MINOR  =  50;
    public static final int GAIN_QUEST_MAJOR  = 150;
    public static final int GAIN_KILL_ENEMY   =  10;
    public static final int GAIN_TRADE        =   5;
    public static final int GAIN_GIFT         =  25;

    /** Lost for attacking a faction NPC or committing crimes. */
    public static final int LOSS_ATTACK_GUARD   = -100;
    public static final int LOSS_ATTACK_CIVILIAN=  -50;
    public static final int LOSS_CRIMINAL_ACT   =  -75;
    public static final int LOSS_BETRAY_QUEST   = -200;

    // ── Read helpers ──────────────────────────────────────────────────────────

    /** Returns the player's chosen faction id, or empty string if none. */
    public static String getFactionId(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getStringOr(KEY_FACTION, "");
    }

    /** Returns {@code true} if the player has chosen a valid faction. */
    public static boolean hasFaction(ServerPlayer player) {
        String id = getFactionId(player);
        return !id.isEmpty() && GotFactions.BY_ID.containsKey(id);
    }

    /**
     * Returns the player's standing with their faction (0 – 10 000).
     * Defaults to {@link #STANDING_DEFAULT} for a new selection.
     */
    public static int getStanding(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getIntOr(KEY_STANDING, STANDING_DEFAULT);
    }

    /** Returns the cached title string for the player, or an empty string. */
    public static String getCachedTitle(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getStringOr(KEY_TITLE, "");
    }

    /**
     * Returns the player's current {@link GotFactionData.RankTitle}, or {@code null}
     * if they have no faction.
     */
    public static GotFactionData.RankTitle getCurrentRank(ServerPlayer player) {
        GotFactionData faction = GotFactions.get(getFactionId(player));
        if (faction == null) return null;
        return faction.rankFor(getStanding(player));
    }

    // ── Write helpers ─────────────────────────────────────────────────────────

    /**
     * Assigns a faction and resets standing to {@link #STANDING_DEFAULT}.
     * Call this when the player confirms their selection screen choice.
     */
    public static void setFaction(ServerPlayer player, String factionId) {
        CompoundTag data = player.getPersistentData();
        data.putString(KEY_FACTION, factionId);
        data.putInt(KEY_STANDING, STANDING_DEFAULT);
        refreshTitle(player);
    }

    /**
     * Adds {@code delta} to the player's standing, clamped to [0, 10 000].
     * Automatically refreshes the cached title.
     *
     * @param delta positive to gain standing, negative to lose it.
     * @return the new standing value.
     */
    public static int modifyStanding(ServerPlayer player, int delta) {
        int current = getStanding(player);
        int updated = Math.clamp(current + delta, STANDING_MIN, STANDING_MAX);
        player.getPersistentData().putInt(KEY_STANDING, updated);
        refreshTitle(player);
        return updated;
    }

    /**
     * Forces the standing to an absolute value, clamped to [0, 10 000].
     * Useful for admin commands or special events.
     */
    public static void setStanding(ServerPlayer player, int value) {
        int clamped = Math.clamp(value, STANDING_MIN, STANDING_MAX);
        player.getPersistentData().putInt(KEY_STANDING, clamped);
        refreshTitle(player);
    }

    /** Clears all faction data from the player (e.g. on death with penalty, or reset). */
    public static void clearFaction(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        data.remove(KEY_FACTION);
        data.remove(KEY_STANDING);
        data.remove(KEY_TITLE);
    }

    // ── Internal ──────────────────────────────────────────────────────────────

    /** Recomputes and caches the title string from current faction + standing. */
    private static void refreshTitle(ServerPlayer player) {
        GotFactionData.RankTitle rank = getCurrentRank(player);
        String title = (rank != null) ? rank.title() : "";
        player.getPersistentData().putString(KEY_TITLE, title);
    }

    private PlayerFactionState() {}
}
