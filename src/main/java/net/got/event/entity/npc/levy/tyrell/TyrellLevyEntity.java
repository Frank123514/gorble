package net.got.event.entity.npc.levy.tyrell;

import net.got.event.entity.npc.GotSpawnEquipment;
import net.got.event.entity.npc.data.GotGenderProvider;
import net.got.event.entity.npc.data.name.GotNameGenerator;
import net.got.event.entity.npc.data.name.GotNpcNames;
import net.got.event.entity.npc.levy.LevyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Tier-2 Levy conscript of House Tyrell. */
public class TyrellLevyEntity extends LevyEntity {

    private static final GotSpawnEquipment WEAPONS = GotSpawnEquipment.of(Items.IRON_SWORD, Items.STONE_SWORD);

    private static ResourceLocation[] textures(boolean female) {
        String prefix = female ? "female" : "male";
        ResourceLocation[] arr = new ResourceLocation[16];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ResourceLocation.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/reachman/generated/" + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public static final ResourceLocation[] MALE_TEXTURES   = textures(false);
    public static final ResourceLocation[] FEMALE_TEXTURES = textures(true);

    public TyrellLevyEntity(EntityType<? extends TyrellLevyEntity> type, Level level) {
        super(type, level);
    }

    @Override protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE; }
    @Override protected GotNameGenerator  getNameGenerator()  { return GotNpcNames.TYRELL_LEVY; }
    @Override public int getVariantsPerGender() { return 16; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            net.minecraft.world.entity.EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);
        setMainhandItem(WEAPONS.pick(random));
        // 40% chance to spawn with a leather helmet
        if (random.nextFloat() < 0.4f) setHelmet(new net.minecraft.world.item.ItemStack(Items.LEATHER_HELMET));
        return result;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LevyEntity.createAttributes();
    }
}
