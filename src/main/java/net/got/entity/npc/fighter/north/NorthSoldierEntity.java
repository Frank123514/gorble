package net.got.entity.npc.fighter.north;

import net.got.entity.npc.GotSpawnEquipment;
import net.got.entity.npc.smallfolk.NorthmanEntity;
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
 * Professional infantryman of the North — 15% horse spawn chance.
 * Spawns with iron sword and has a 60% chance for chainmail armour.
 */
public class NorthSoldierEntity extends SkilledFighterEntity {

    private static final GotSpawnEquipment WEAPONS =
            GotSpawnEquipment.of(Items.IRON_SWORD, Items.IRON_SWORD, Items.STONE_SWORD);

    /** Reuse the regional smallfolk male skins — levies are drawn from the same population. */
    public static final ResourceLocation[] MALE_TEXTURES   = NorthmanEntity.MALE_TEXTURES;
    public static final ResourceLocation[] FEMALE_TEXTURES = NorthmanEntity.MALE_TEXTURES;

    public NorthSoldierEntity(EntityType<? extends NorthSoldierEntity> type, Level level) {
        super(type, level);
    }

    @Override public float getHorseSpawnChance() { return 0.15f; }

    @Override
    public String getMilitaryTitle() {
        return "";
    }

    @Override public int   getVariantsPerGender() { return NorthmanEntity.MALE_VARIANT_COUNT; }
    @Override protected GotNameGenerator getNameGenerator() { return GotNpcNames.NORTH_SOLDIER; }

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
