package net.got.event.entity.npc.levy.martell;

import net.got.event.entity.npc.SpawnEquipment;
import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
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

public class MartellLevyEntity extends LevyEntity {

    private static final SpawnEquipment WEAPONS = SpawnEquipment.of(Items.IRON_SWORD, Items.STONE_SWORD);

    private static Identifier[] textures(boolean female) {
        String prefix = female ? "female" : "male";
        Identifier[] arr = new Identifier[16];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Identifier.fromNamespaceAndPath("got",
                    "textures/entity/npc/smallfolk/dornishman/generated/" + prefix + "_" + String.format("%02d", i + 1) + ".png");
        }
        return arr;
    }

    public static final Identifier[] MALE_TEXTURES   = textures(false);
    public static final Identifier[] FEMALE_TEXTURES = textures(true);

    public MartellLevyEntity(EntityType<? extends MartellLevyEntity> type, Level level) {
        super(type, level);
    }

    @Override protected GenderProvider getGenderProvider() { return GenderProvider.MALE; }
    @Override protected NameGenerator  getNameGenerator()  { return NpcNames.MARTELL_LEVY; }
    @Override public int getVariantsPerGender() { return 16; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            net.minecraft.world.entity.EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);
        setMainhandItem(WEAPONS.pick(random));
        
        if (random.nextFloat() < 0.4f) setHelmet(new net.minecraft.world.item.ItemStack(Items.LEATHER_HELMET));
        return result;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LevyEntity.createAttributes();
    }
}
