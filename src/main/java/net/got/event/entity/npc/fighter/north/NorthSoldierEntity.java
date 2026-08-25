package net.got.event.entity.npc.fighter.north;

import net.got.event.entity.npc.SpawnEquipment;
import net.got.event.entity.npc.data.GenderProvider;
import net.got.event.entity.npc.smallfolk.NorthmanEntity;
import net.got.event.entity.npc.data.name.NameGenerator;
import net.got.event.entity.npc.data.name.NpcNames;
import net.got.event.entity.npc.fighter.SkilledFighterEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class NorthSoldierEntity extends SkilledFighterEntity {

    private static final SpawnEquipment WEAPONS =
            SpawnEquipment.of(Items.IRON_SWORD, Items.IRON_SWORD, Items.STONE_SWORD);

    public static final Identifier[] MALE_TEXTURES   = NorthmanEntity.MALE_TEXTURES;
    public static final Identifier[] FEMALE_TEXTURES = NorthmanEntity.FEMALE_TEXTURES;

    public NorthSoldierEntity(EntityType<? extends NorthSoldierEntity> type, Level level) {
        super(type, level);
    }

    @Override protected GenderProvider getGenderProvider() { return GenderProvider.MALE_OR_FEMALE; }

    @Override public float getHorseSpawnChance() { return 0.15f; }

    @Override
    public String getMilitaryTitle() {
        return "";
    }

    @Override public int   getVariantsPerGender() { return NorthmanEntity.MALE_VARIANT_COUNT; }
    @Override protected NameGenerator getNameGenerator() { return NpcNames.NORTH_SOLDIER; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);
        setMainhandItem(WEAPONS.pick(random));
        if (random.nextFloat() < 0.6f) {
            setHelmet(new ItemStack(Items.CHAINMAIL_HELMET));
            setChestplate(new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        }
        return result;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SkilledFighterEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ARMOR, 4.0);
    }
}
