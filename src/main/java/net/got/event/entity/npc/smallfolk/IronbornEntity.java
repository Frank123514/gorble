package net.got.event.entity.npc.smallfolk;

import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class IronbornEntity extends SmallfolkEntity {

    public static final int MALE_VARIANT_COUNT   = 9;
    
    public static final int FEMALE_VARIANT_COUNT = 9;
    
    public static final Identifier[] MALE_TEXTURES   = textures("ironborn", false, MALE_VARIANT_COUNT);
    
    public static final Identifier[] FEMALE_TEXTURES = textures("ironborn", true,  FEMALE_VARIANT_COUNT);

    private static Identifier[] textures(String id, boolean female, int count) {
        String prefix = female ? "female" : "male";
        Identifier[] arr = new Identifier[Math.max(1, count)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Identifier.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/" + id + "/generated/" + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public IronbornEntity(EntityType<? extends IronbornEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected GenderProvider getGenderProvider() { return GenderProvider.MALE_OR_FEMALE; }

    @Override
    protected NameGenerator getNameGenerator() { return NpcNames.IRONBORN; }

    @Override
    public int getVariantsPerGender() { return MALE_VARIANT_COUNT; }

    @Override
    public boolean isCivilian() { return true; }

    public static AttributeSupplier.Builder createAttributes() {
        return SmallfolkEntity.createAttributes();
    }
}
