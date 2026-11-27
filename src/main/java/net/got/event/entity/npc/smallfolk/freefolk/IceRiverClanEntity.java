package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Ice River Clan — nomadic freefolk who follow the frozen rivers of the deep north.
 *
 * <p>The Ice River Clan are cannibals who roam the frozen rivers beyond the Wall in search
 * of prey. They are among the most feared of the wildling peoples, painted in bone-white
 * and adorned with trophies of their kills. They worship the cold itself.</p>
 */
public class IceRiverClanEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 18;
    public static final int FEMALE_VARIANT_COUNT = 8;

    public static final Identifier[] MALE_TEXTURES   = textures("ice_river_clan", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("ice_river_clan", true,  FEMALE_VARIANT_COUNT);

    public IceRiverClanEntity(EntityType<? extends IceRiverClanEntity> type, Level level) {
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
