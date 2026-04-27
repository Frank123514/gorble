package net.got.entity.npc.fighter.vale;

import net.got.entity.npc.GotSpawnEquipment;
import net.got.entity.npc.smallfolk.ValemanEntity;
import net.got.entity.npc.data.name.GotNameGenerator;
import net.got.entity.npc.data.name.GotNpcNames;
import net.got.entity.npc.fighter.SkilledFighterEntity;
import net.minecraft.resources.ResourceLocation;
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

/**
 * Elite armoured knight of the Vale — 50% horse spawn chance.
 * Always spawns with iron sword; 80% chance of full iron armour.
 */
public class ValeKnightEntity extends SkilledFighterEntity {

    private static final GotSpawnEquipment WEAPONS =
            GotSpawnEquipment.of(Items.IRON_SWORD, Items.IRON_SWORD, Items.IRON_AXE);

    /** Reuse the regional smallfolk male skins — levies are drawn from the same population. */
    public static final ResourceLocation[] MALE_TEXTURES   = ValemanEntity.MALE_TEXTURES;
    public static final ResourceLocation[] FEMALE_TEXTURES = ValemanEntity.MALE_TEXTURES;

    public ValeKnightEntity(EntityType<? extends ValeKnightEntity> type, Level level) {
        super(type, level);
    }

    @Override public float getHorseSpawnChance() { return 0.50f; }

    @Override
    public String getMilitaryTitle() {
        return "";
    }

    @Override public int   getVariantsPerGender() { return ValemanEntity.MALE_VARIANT_COUNT; }
    @Override protected GotNameGenerator getNameGenerator() { return GotNpcNames.VALE_KNIGHT; }

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
