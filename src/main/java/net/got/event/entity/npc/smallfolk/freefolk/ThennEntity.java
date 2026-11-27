package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Thenns — the most organised and disciplined of the Free Folk.
 *
 * <p>The Thenns inhabit the deep valley of Thenn, north of the Gift. They are the most
 * culturally advanced of the Free Folk, using bronze armour and weapons, practising
 * cannibalism of slain enemies, and following a Magnar (lord) in a feudal-ish structure.
 * They are feared and respected by other Free Folk clans.</p>
 *
 * <p>Mechanically: heavy fighters with bronze gear. Skilled rank is {@link NpcRank#THENN_WARRIOR}.</p>
 */
public class ThennEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 20;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("thenn", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("thenn", true,  FEMALE_VARIANT_COUNT);

    public ThennEntity(EntityType<? extends ThennEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.THENN; }

    /** Thenns use bronze armour — the most organised freefolk fighters. */
    @Override
    protected NpcRank skilledRank() { return NpcRank.THENN_WARRIOR; }

    @Override
    public String getRegionId() { return "thenn"; }

    public static AttributeSupplier.Builder createAttributes() {
        return FreefolkEntity.createAttributes();
    }
}
