package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Nightrunners — a shadowy, stealthy freefolk clan famed for night raids.
 *
 * <p>The Nightrunners wear dark furs and are known for their prowess in darkness, striking
 * settlements in the dead of night and vanishing like wraiths before dawn. They are expert
 * scouts and assassins among the Free Folk, disdained by more warlike clans for their
 * preference of stealth over open battle.</p>
 */
public class NightrunnerEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 16;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("nightrunner", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("nightrunner", true,  FEMALE_VARIANT_COUNT);

    public NightrunnerEntity(EntityType<? extends NightrunnerEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.FREEFOLK_GENERIC; }

    public static AttributeSupplier.Builder createAttributes() {
        return FreefolkEntity.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.18);
    }
}
