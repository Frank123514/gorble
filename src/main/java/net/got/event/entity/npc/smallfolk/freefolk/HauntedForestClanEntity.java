package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Haunted Forest Clan — freefolk who inhabit the dense, ancient forest south of the Fist of
 * the First Men and the Milkwater.
 *
 * <p>The Haunted Forest is so named because settled folk believed it to be haunted by the
 * ghosts of the First Men. It is home to numerous unnamed clans who subsist by hunting in its
 * ancient groves. These clans are independent, have no great leaders, and raid the Gift
 * whenever hunger or cold drives them south. They wear bark-and-fur clothing and use carved
 * wood and bone weapons.</p>
 */
public class HauntedForestClanEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 20;
    public static final int FEMALE_VARIANT_COUNT = 12;

    public static final Identifier[] MALE_TEXTURES   = textures("haunted_forest", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("haunted_forest", true,  FEMALE_VARIANT_COUNT);

    public HauntedForestClanEntity(EntityType<? extends HauntedForestClanEntity> type, Level level) {
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
