package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;

/**
 * The Free Folk are many peoples, not one. Each clan has its own id, naming, temperament and
 * default stance toward the Night's Watch and the kneelers. Entity classes should return
 * {@link #getNameGenerator()} and use {@link #getId()} for textures, speech and spawning.
 */
public enum FreefolkClan {
    HORNFOOT("hornfoot", NpcNames.FREEFOLK_GENERIC, Stance.RAIDING,
            "Barefoot hunters whose soles have hardened to horn; they wander the frozen wastes."),
    ICE_RIVER("ice_river", NpcNames.FREEFOLK_GENERIC, Stance.NEUTRAL,
            "River-fishers of the frozen Milkwater's northern reaches, hardy and insular."),
    MILKWATER("milkwater", NpcNames.FREEFOLK_GENERIC, Stance.NEUTRAL,
            "Clans along the Milkwater, nearest the Wall and most often in contact with the Watch."),
    HAUNTED_FOREST("haunted_forest", NpcNames.FREEFOLK_GENERIC, Stance.WARY,
            "Forest clans who keep to the old ways beneath the weirwoods."),
    NIGHTRUNNER("nightrunner", NpcNames.FREEFOLK_GENERIC, Stance.RAIDING,
            "Swift raiders who strike by night and vanish before the Watch can answer."),
    FROZEN_SHORE("frozen_shore", NpcNames.FREEFOLK_GENERIC, Stance.NEUTRAL,
            "Coast-dwellers of the Frozen Shore, living off the sea and the ice."),
    CAVE_DWELLER("cave_dweller", NpcNames.FREEFOLK_GENERIC, Stance.HOSTILE,
            "Cave-dwellers of the far north; feared by other clans for their savage habits."),
    THENN("thenn", NpcNames.THENN, Stance.HOSTILE,
            "The Thenns of Thenn Valley, ruled by a Magnar; bronze-armed and disciplined, unlike most Free Folk."),
    SKAGOSSI("skagossi", NpcNames.SKAGOSSI, Stance.WARY,
            "Islanders of Skagos, apart from both the Free Folk and the kneelers."),
    SPEARWIFE("spearwife", NpcNames.FREEFOLK_FEMALE, Stance.NEUTRAL,
            "Women who fight, hunt and raid alongside the men; they are not kneelers' wives and answer to no lord."),
    RAIDER("wildling_raider", NpcNames.FREEFOLK_GENERIC, Stance.RAIDING,
            "Mixed bands who cross the Wall or sail the shore to raid.");

    public enum Stance { NEUTRAL, WARY, RAIDING, HOSTILE }

    private final String id;
    private final NameGenerator names;
    private final Stance defaultStance;
    private final String lore;

    FreefolkClan(String id, NameGenerator names, Stance defaultStance, String lore) {
        this.id = id;
        this.names = names;
        this.defaultStance = defaultStance;
        this.lore = lore;
    }

    public String getId() { return id; }
    public NameGenerator getNameGenerator() { return names; }
    public Stance getDefaultStance() { return defaultStance; }
    public String getLore() { return lore; }
}
