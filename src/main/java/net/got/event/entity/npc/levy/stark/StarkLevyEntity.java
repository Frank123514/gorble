package net.got.event.entity.npc.levy.stark;

import net.got.event.entity.npc.GotSpawnEquipment;
import net.got.event.entity.npc.data.GotGenderProvider;
import net.got.event.entity.npc.data.name.GotNameGenerator;
import net.got.event.entity.npc.data.name.GotNpcNames;
import net.got.event.entity.npc.levy.LevyEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Tier-2 Levy conscript of House Stark. */
public class StarkLevyEntity extends LevyEntity {

    private static final GotSpawnEquipment WEAPONS = GotSpawnEquipment.of(Items.IRON_SWORD, Items.STONE_SWORD);

    private static Identifier[] textures(boolean female) {
        String prefix = female ? "female" : "male";
        Identifier[] arr = new Identifier[16];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Identifier.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/northman/generated/" + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public static final Identifier[] MALE_TEXTURES   = textures(false);
    public static final Identifier[] FEMALE_TEXTURES = textures(true);

    public StarkLevyEntity(EntityType<? extends StarkLevyEntity> type, Level level) {
        super(type, level);
    }

    @Override protected GotGenderProvider getGenderProvider() { return GotGenderProvider.MALE; }
    @Override protected GotNameGenerator  getNameGenerator()  { return GotNpcNames.STARK_LEVY; }
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
