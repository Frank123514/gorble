package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Base class for all Free Folk (wildling) NPC types.
 *
 * <p>The Free Folk are NOT a monolith. They are dozens of distinct clans, tribes and peoples
 * scattered across the lands Beyond the Wall, each with their own culture, fighting style and
 * look. Each concrete sub-class represents one such group. The skilled-tier rank for all
 * freefolk is {@link NpcRank#RAIDER} — a hardened wilderness fighter who does not bow to lords.</p>
 *
 * <p>All freefolk share the region-id {@code "freefolk"} so the loadout system can give them
 * the correct fur/bone/primitive gear, but each tribe supplies its own texture arrays and name
 * bank so they look and feel distinct in-game.</p>
 */
public abstract class FreefolkEntity extends SmallfolkEntity {

    protected FreefolkEntity(EntityType<? extends FreefolkEntity> type, Level level) {
        super(type, level);
    }

    /** All freefolk share the same region id for loadout / faction purposes. */
    @Override
    public String getRegionId() { return "freefolk"; }

    /** The skilled tier for every freefolk tribe is the Raider. */
    @Override
    protected NpcRank skilledRank() { return NpcRank.RAIDER; }

    /** Freefolk are predominantly male fighters, but women (spear-wives) also fight. */
    @Override
    protected GenderProvider getGenderProvider() { return GenderProvider.MALE_OR_FEMALE; }

    public static AttributeSupplier.Builder createAttributes() {
        // Slightly faster than settled folk — wilderness-hardened
        return SmallfolkEntity.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.165);
    }

    // ── Helper shared by all tribe subclasses ────────────────────────────────

    protected static Identifier[] textures(String tribe, boolean female, int count) {
        String prefix = female ? "female" : "male";
        Identifier[] arr = new Identifier[Math.max(1, count)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Identifier.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/freefolk/" + tribe + "/generated/"
                            + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    // ── Default name generator (tribes override) ─────────────────────────────

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.FREEFOLK_GENERIC; }
}
