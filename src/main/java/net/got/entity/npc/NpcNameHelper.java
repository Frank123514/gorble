package net.got.entity.npc;

import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;

/**
 * @deprecated Use {@link GotNpcNames} and {@link GotNameGenerator} instead.
 * This class is kept only for backward-compatibility; it now delegates to the
 * new LOTR-style name-bank system.
 */
@Deprecated(forRemoval = true)
public final class NpcNameHelper {
    private NpcNameHelper() {}
    // Old static helpers are gone; callers should use entity.getNpcName() directly.
}
