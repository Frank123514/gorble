package net.got.event.entity.npc.fighter.vale;

import net.got.event.entity.npc.SpawnEquipment;
import net.got.event.entity.npc.smallfolk.ValemanEntity;
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

public class ValeKnightEntity extends SkilledFighterEntity {

    private static final SpawnEquipment WEAPONS =
            SpawnEquipment.of(Items.IRON_SWORD, Items.IRON_SWORD, Items.IRON_AXE);

    public static final Identifier[] MALE_TEXTURES   = ValemanEntity.MALE_TEXTURES;
    public static final Identifier[] FEMALE_TEXTURES = ValemanEntity.MALE_TEXTURES;

    public ValeKnightEntity(EntityType<? extends ValeKnightEntity> type, Level level) {
        super(type, level);
    }

    @Override public float getHorseSpawnChance() { return 0.50f; }

    @Override
    public String getMilitaryTitle() {
        return "";
    }

    @Override public int   getVariantsPerGender() { return ValemanEntity.MALE_VARIANT_COUNT; }
    @Override protected NameGenerator getNameGenerator() { return NpcNames.VALE_KNIGHT; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, reason, groupData);
        setMainhandItem(WEAPONS.pick(random));
        if (random.nextFloat() < 0.8f) {
            setHelmet(new ItemStack(Items.IRON_HELMET));
            setChestplate(new ItemStack(Items.IRON_CHESTPLATE));
            setLeggings(new ItemStack(Items.IRON_LEGGINGS));
            setBoots(new ItemStack(Items.IRON_BOOTS));
        }
        return result;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SkilledFighterEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.ARMOR, 8.0);
    }
}
