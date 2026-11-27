package net.got.event.entity.npc.smallfolk.freefolk;

import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.smallfolk.FreefolkEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Skagosi (Stoneborn) — the fierce islanders of Skagos, technically still within the North
 * but fiercely independent and closer in culture to the Free Folk.
 *
 * <p>The Skagosi are the inhabitants of the island of Skagos, in the Bay of Seals. They are
 * rumoured to be cannibals and to ride great shaggy unicorns in battle. They refused to yield
 * to Stark authority and are considered barely civilised even by northern standards. They wear
 * stone-adorned armour and carry stone weapons.</p>
 *
 * <p>Their region id is {@code "skagos"} to give them distinct stone-based gear.</p>
 */
public class SkagossiEntity extends FreefolkEntity {

    public static final int MALE_VARIANT_COUNT   = 18;
    public static final int FEMALE_VARIANT_COUNT = 10;

    public static final Identifier[] MALE_TEXTURES   = textures("skagossi", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("skagossi", true,  FEMALE_VARIANT_COUNT);

    public SkagossiEntity(EntityType<? extends SkagossiEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.SKAGOSSI; }

    @Override
    public String getRegionId() { return "skagos"; }

    public static AttributeSupplier.Builder createAttributes() {
        return FreefolkEntity.createAttributes();
    }
}
