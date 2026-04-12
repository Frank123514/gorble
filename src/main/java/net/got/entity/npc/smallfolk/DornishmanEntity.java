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
    public static final ResourceLocation[] MALE_TEXTURES   = textures("dornishman", false, 2);
    /** Female texture variants. */
    public static final ResourceLocation[] FEMALE_TEXTURES = textures("dornishman", true,  2);

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
    public int getVariantsPerGender() { return 2; }

    @Override
    public boolean isCivilian() { return true; }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes();
    }
}
