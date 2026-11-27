package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Cave Dwellers — primitive freefolk who make their homes in caves and underground warrens.
 *
 * <p>The Cave Dwellers (sometimes called Cave Men) are perhaps the most primitive of the
 * Free Folk peoples. They live in caves and deep warrens throughout the mountains beyond the
 * Wall, shunning even the rough settlements other wildlings build. They tattoo their faces,
 * use bone and obsidian tools, and wear scraped hides. They are superstitious and fierce,
 * but lack the organisation of clans like the Thenns.</p>
 */
public class CaveDwellerEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 16;
    public static final int FEMALE_VARIANT_COUNT = 8;

    public static final Identifier[] MALE_TEXTURES   = textures("cave_dweller", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("cave_dweller", true,  FEMALE_VARIANT_COUNT);

    public CaveDwellerEntity(EntityType<? extends CaveDwellerEntity> type, Level level) {
        super(type, level);
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
