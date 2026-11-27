package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.NpcRank;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/**
 * Crownlander — a denizen of the Crownlands, the region surrounding King's Landing and
 * ruled directly by the Iron Throne.
 *
 * <p>The Crownlands are a patchwork of cultures, drawing settlers from across all Seven
 * Kingdoms due to the capital's gravitational pull. Crownlanders tend toward the Faith of
 * the Seven, speak the common tongue without accent, and serve the crown directly rather than
 * any single great house. The Gold Cloaks (City Watch of King's Landing) represent their
 * skilled-tier fighters: disciplined city guardsmen armed with spears and shortswords.</p>
 *
 * <p>The region is small but densely populated, encompassing King's Landing, Dragonstone,
 * Duskendale, and surrounding lands between the Gold Road and the Blackwater.</p>
 */
public class CrownlanderEntity extends SmallfolkEntity {

    public static final int MALE_VARIANT_COUNT   = 28;
    public static final int FEMALE_VARIANT_COUNT = 16;

    public static final Identifier[] MALE_TEXTURES   = textures("crownlander", false, MALE_VARIANT_COUNT);
    public static final Identifier[] FEMALE_TEXTURES = textures("crownlander", true,  FEMALE_VARIANT_COUNT);

    private static Identifier[] textures(String id, boolean female, int count) {
        String prefix = female ? "female" : "male";
        Identifier[] arr = new Identifier[Math.max(1, count)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Identifier.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/" + id + "/generated/"
                            + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public CrownlanderEntity(EntityType<? extends CrownlanderEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected GenderProvider getGenderProvider() { return GenderProvider.MALE_OR_FEMALE; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.CROWNLANDER; }

    @Override
    public int getVariantsPerGender()       { return MALE_VARIANT_COUNT; }
    @Override
    public int getFemaleVariantsPerGender() { return FEMALE_VARIANT_COUNT; }

    @Override
    public String getRegionId() { return "crownlands"; }

    /** Gold Cloak (City Watch) is the elite fighter of the Crownlands. */
    @Override
    protected NpcRank skilledRank() { return NpcRank.GOLD_CLOAK; }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes();
    }
}
