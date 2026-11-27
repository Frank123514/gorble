package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Wildling Raider — a generic mixed-clan freefolk warrior gathered under a King-Beyond-the-Wall.
 *
 * <p>When the Free Folk mass for a great ranging, warriors from dozens of clans ride together
 * under a single banner. The Wildling Raider represents this mixed-clan fighter: someone who
 * might be from the Frozen Shore, the Haunted Forest, the Milkwater tributaries, or any of
 * a dozen unnamed tribes. They are always hostile fighters.</p>
 *
 * <p>Always spawns as LEVY or higher — no civilian variants.</p>
 */
public class WildlingRaiderEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 24;
    public static final int FEMALE_VARIANT_COUNT = 12;

    public static final Identifier[] MALE_TEXTURES   = textures("wildling_raider", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("wildling_raider", true,  FEMALE_VARIANT_COUNT);

    public WildlingRaiderEntity(EntityType<? extends WildlingRaiderEntity> type, Level level) {
        super(type, level);
    }

    /** Raiders are always a combat rank. */
    @Override
    protected NpcRank rollRank(RandomSource rand) {
        float r = rand.nextFloat();
        if (r < 0.50F) return NpcRank.LEVY;
        if (r < 0.85F) return NpcRank.MAN_AT_ARMS;
        return NpcRank.RAIDER;
    }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.FREEFOLK_GENERIC; }

    public static AttributeSupplier.Builder createAttributes() {
        return FreefolkEntity.createAttributes();
    }
}
