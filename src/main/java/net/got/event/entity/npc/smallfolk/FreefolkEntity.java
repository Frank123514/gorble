package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.ValueInput;
import org.jetbrains.annotations.Nullable;

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
    protected GenderProvider getGenderProvider() { return rand -> rand.nextFloat() < 0.75F; }

    // ── No kneeler-society naming / station ──────────────────────────────────
    // The Free Folk have no peasants, levies, men-at-arms or tradesmen. Those are southron
    // (kneeler) structures, so they never appear. A freefolk is named "<Name> the <Clan> <Rank>",
    // e.g. "Name the Hornfoot Raider".

    /** Title after the personal name: the clan (entity type name) plus the freefolk rank. */
    protected Component freefolkTitle() {
        return Component.translatable(getType().getDescriptionId())
                .append(Component.literal(" " + getRank().title));
    }

    @Override
    public @Nullable Component getCustomName() {
        String personal = getNpcName();
        if (personal == null || personal.isEmpty()) return null;
        return Component.translatable("entity.got.npc.named_military",
                Component.literal(personal), freefolkTitle());
    }

    /** Always the tribe's own fighter rank, never peasant / levy / man-at-arms. */
    @Override
    protected NpcRank rollRank(RandomSource rand) { return skilledRank(); }

    /** Freefolk never hold a job (baker, smith, farmer...). */
    @Override
    protected boolean shouldHaveOccupation() { return false; }

    /** Old saves may contain a peasant / levy / man-at-arms freefolk or an occupation: convert them. */
    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (getRank() != skilledRank()) setRank(skilledRank());
        setOccupation(net.got.event.entity.npc.data.NpcOccupation.NONE);
    }

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
