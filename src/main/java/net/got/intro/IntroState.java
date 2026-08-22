package net.got.intro;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

/**
 * Tracks the "waking up" intro / character-creation sequence per player.
 *
 * A player is considered done with the intro once they've clicked through
 * the closing "Very well." line (KEY_ENTERED_KNOWN_WORLD) — not just once
 * they've picked a faction. This matters because a player can disconnect
 * after confirming their house but before clicking through the final line,
 * in which case we resume them at the final line on next login instead of
 * replaying the whole sequence.
 *
 * The player starts in the normal vanilla overworld; finishing this intro
 * (see PlayerEvents.maybeResumeIntro) is what teleports them into the
 * knownworld dimension for the first time.
 */
public final class IntroState {

    public static final String KEY_CHARACTER_NAME      = "got.characterName";
    public static final String KEY_ENTERED_KNOWN_WORLD = "got.enteredKnownWorld";
    public static final String KEY_PENDING_WAYPOINT    = "got.pendingWaypoint";

    public static boolean hasEnteredKnownWorld(ServerPlayer player) {
        return player.getPersistentData().getBooleanOr(KEY_ENTERED_KNOWN_WORLD, false);
    }

    public static void markEnteredKnownWorld(ServerPlayer player) {
        player.getPersistentData().putBoolean(KEY_ENTERED_KNOWN_WORLD, true);
    }

    public static String getCharacterName(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        return data.getStringOr(KEY_CHARACTER_NAME, "");
    }

    public static void setCharacterName(ServerPlayer player, String name) {
        player.getPersistentData().putString(KEY_CHARACTER_NAME, name);
    }

    public static String getPendingWaypoint(ServerPlayer player) {
        return player.getPersistentData().getStringOr(KEY_PENDING_WAYPOINT, "");
    }

    public static void setPendingWaypoint(ServerPlayer player, String waypointName) {
        player.getPersistentData().putString(KEY_PENDING_WAYPOINT, waypointName == null ? "" : waypointName);
    }

    private IntroState() {}
}
