package net.got.entity.npc.smallfolk;

import net.got.entity.npc.data.GotGenderProvider;
import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

/** Tier-1 civilian NPC — ValemanEntity. */
public class ValemanEntity extends SmallfolkEntity {

    /** Number of generated skin variants per gender (12 variants from skin generator). */
    public static final int MALE_VARIANT_COUNT   = 12;
    /** Number of female skin variants. */
    public static final int FEMALE_VARIANT_COUNT = 12;
    /** Male texture variants — valeman/generated/male_01.png ... male_12.png */
    public static final ResourceLocation[] MALE_TEXTURES   = textures("valeman", false, MALE_VARIANT_COUNT);
    /** Female texture variants — valeman/generated/female_01.png ... female_12.png */
    public static final ResourceLocation[] FEMALE_TEXTURES = textures("valeman", true,  FEMALE_VARIANT_COUNT);

    private static ResourceLocation[] textures(String id, boolean female, int count) {
        String prefix = female ? "female" : "male";
        ResourceLocation[] arr = new ResourceLocation[Math.max(1, count)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ResourceLocation.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/" + id + "/generated/" + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public ValemanEntity(EntityType<? extends ValemanEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE_OR_FEMALE; }

    @Override
    protected GotNameGenerator getNameGenerator() { return GotNpcNames.VALEMAN; }

    @Override
    public int getVariantsPerGender() { return MALE_VARIANT_COUNT; }

    @Override
    public boolean isCivilian() { return true; }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes();
    }
}
