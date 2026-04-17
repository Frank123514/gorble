package net.got.entity.npc.levy.baratheon;

import net.got.entity.npc.GotSpawnEquipment;
import net.got.entity.npc.data.GotGenderProvider;
import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;
import net.got.entity.npc.levy.LevyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Tier-2 Levy conscript of House Baratheon. */
public class BaratheonLevyEntity extends LevyEntity {

    private static final GotSpawnEquipment WEAPONS = GotSpawnEquipment.of(Items.IRON_SWORD, Items.IRON_AXE);

    public static final ResourceLocation[] MALE_TEXTURES = {
        ResourceLocation.fromNamespaceAndPath("got", "textures/entity/npc/levy/baratheon_levy/male_1.png")
    };
    public static final ResourceLocation[] FEMALE_TEXTURES = MALE_TEXTURES;

    public BaratheonLevyEntity(EntityType<? extends BaratheonLevyEntity> type, Level level) {
        super(type, level);
    }

    @Override protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE; }
    @Override protected GotNameGenerator  getNameGenerator()  { return GotNpcNames.BARATHEON_LEVY; }
    @Override public int getVariantsPerGender() { return 1; }

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
