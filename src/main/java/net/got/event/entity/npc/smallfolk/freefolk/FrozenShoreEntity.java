package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Frozen Shore Clan — coastal hunters who ride giant walruses and hunt sea mammals.
 *
 * <p>The Frozen Shore clan live along the icy coasts of the Lands of Always Winter. They are
 * short and stocky, with flat faces, and are known for riding walruses in battle. Their
 * clothing is made entirely of sealskin and whale-bone. They are fierce coastal raiders.</p>
 */
public class FrozenShoreEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 18;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("frozen_shore", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("frozen_shore", true,  FEMALE_VARIANT_COUNT);

    public FrozenShoreEntity(EntityType<? extends FrozenShoreEntity> type, Level level) {
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
