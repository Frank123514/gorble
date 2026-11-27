package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Milkwater Clan — freefolk who dwell along the banks of the great river Milkwater.
 *
 * <p>The Milkwater is a great pale river flowing through the heart of the lands Beyond the Wall.
 * Many clans cluster along its banks, trading, warring, and fishing. The Milkwater Clan is a
 * representative group of these river-dwellers. They are skilled fishermen and boat-builders
 * (by wildling standards), using hide-covered coracles on the river. They trade amber,
 * obsidian and furs among themselves.</p>
 */
public class MilkwaterClanEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 18;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("milkwater_clan", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("milkwater_clan", true,  FEMALE_VARIANT_COUNT);

    public MilkwaterClanEntity(EntityType<? extends MilkwaterClanEntity> type, Level level) {
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
