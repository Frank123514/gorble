package net.got.entity.npc.smallfolk;

import net.got.entity.npc.data.GotGenderProvider;
import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/** Tier-1 civilian NPC — DornishmanEntity. */
public class DornishmanEntity extends SmallfolkEntity {

    /** Male texture variants. */
    public static final int MALE_VARIANT_COUNT   = 4;
    /** Number of female skin variants. */
    public static final int FEMALE_VARIANT_COUNT = 3;
    /** Male texture variants — dornishman/male_1.png … male_4.png */
    public static final ResourceLocation[] MALE_TEXTURES   = textures("dornishman", false, MALE_VARIANT_COUNT);
    /** Female texture variants. */
    /** Female texture variants — dornishman/female_1.png … female_3.png */
    public static final ResourceLocation[] FEMALE_TEXTURES = textures("dornishman", true,  FEMALE_VARIANT_COUNT);

    private static ResourceLocation[] textures(String id, boolean female, int count) {
        String prefix = female ? "female" : "male";
        ResourceLocation[] arr = new ResourceLocation[Math.max(1, count)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ResourceLocation.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/" + id + "/" + prefix + "_" + (i + 1) + ".png");
        }
        return arr;
    }

    public DornishmanEntity(EntityType<? extends DornishmanEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE_OR_FEMALE; }

    @Override
    protected GotNameGenerator getNameGenerator() { return GotNpcNames.DORNISHMAN; }

    @Override
    public int getVariantsPerGender() { return MALE_VARIANT_COUNT; }

    @Override
    public boolean isCivilian() { return true; }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes();
    }
}
