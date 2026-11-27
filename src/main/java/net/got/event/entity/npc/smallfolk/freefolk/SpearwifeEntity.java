package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Spear-Wife — a Free Folk woman warrior, found in any tribe.
 *
 * <p>Among the Free Folk, women are not confined to hearth and home. Spear-wives are female
 * warriors who fight alongside men, often wielding spears. They are found in every clan
 * and tribe, though they may not always be welcome in leadership roles. Val, Ygritte, and
 * Karsi are famous examples.</p>
 *
 * <p>This entity is <em>always</em> female and always spawns as a fighter rank — no civilians.</p>
 */
public class SpearwifeEntity extends FreefolkEntity {

    // Uses the generic freefolk female textures shared across tribes
    public static final int MALE_VARIANT_COUNT   = 20;
    public static final int FEMALE_VARIANT_COUNT = 20;

    public static final Identifier[] MALE_TEXTURES   = textures("spearwife", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("spearwife", true,  FEMALE_VARIANT_COUNT);

    public SpearwifeEntity(EntityType<? extends SpearwifeEntity> type, Level level) {
        super(type, level);
    }

    /** Spear-wives are always female. */
    @Override
    protected GenderProvider getGenderProvider() { return GenderProvider.FEMALE; }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.FREEFOLK_FEMALE; }

    /** "Name the Spearwife" (not "Spearwife Raider"). */
    @Override
    protected net.minecraft.network.chat.Component freefolkTitle() {
        return net.minecraft.network.chat.Component.translatable(getType().getDescriptionId());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return FreefolkEntity.createAttributes();
    }
}
