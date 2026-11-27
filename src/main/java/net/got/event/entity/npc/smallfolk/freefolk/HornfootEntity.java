package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Hornfoots (Hornfoot Men) — a large clan of Free Folk who go barefoot even on ice and snow.
 *
 * <p>The Hornfoot men are one of the most numerous clans Beyond the Wall. Their soles are
 * said to be like horn from a lifetime of walking unshod over frozen ground, rock, and tundra.
 * They are hardy, numerous, and fight with spears and stone-tipped arrows.</p>
 */
public class HornfootEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 20;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("hornfoot", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("hornfoot", true,  FEMALE_VARIANT_COUNT);

    public HornfootEntity(EntityType<? extends HornfootEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.FREEFOLK_GENERIC; }

    public static AttributeSupplier.Builder createAttributes() {
        // Hornfoots are extremely fast runners — they never wear boots
        return FreefolkEntity.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.185);
    }
}
