package net.got.init;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Map;

/**
 * Armor materials for GoT custom metals (MC 1.21.3 / NeoForge 21.3.x).
 *
 * ArmorMaterial record constructor (exact signature from compiler error):
 *   (int durability, Map<ArmorType,Integer> defense, int enchantability,
 *    Holder<SoundEvent> equipSound, float toughness, float knockbackResistance,
 *    TagKey<Item> repairTag, ResourceLocation textureLocation)
 */
public class GotModArmorMaterials {

    private static TagKey<Item> repairTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
    }

    /**
     * Copper: 2/5/4/2 defense, enchantability 9, 0 toughness.
     */
    public static final Holder<ArmorMaterial> COPPER = Holder.direct(
            new ArmorMaterial(
                    250,
                    Map.of(
                            ArmorType.HELMET,     2,
                            ArmorType.CHESTPLATE, 5,
                            ArmorType.LEGGINGS,   4,
                            ArmorType.BOOTS,      2
                    ),
                    9,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    0.0f,
                    0.0f,
                    repairTag("repairs_copper_armor"),
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "copper")
            )
    );

    /**
     * Bronze: 3/6/5/3 defense, enchantability 10, 0.5 toughness.
     */
    public static final Holder<ArmorMaterial> BRONZE = Holder.direct(
            new ArmorMaterial(
                    400,
                    Map.of(
                            ArmorType.HELMET,     3,
                            ArmorType.CHESTPLATE, 6,
                            ArmorType.LEGGINGS,   5,
                            ArmorType.BOOTS,      3
                    ),
                    10,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    0.5f,
                    0.0f,
                    repairTag("repairs_bronze_armor"),
                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "bronze")
            )
    );
}
