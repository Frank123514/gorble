package net.got.init;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

/**
 * Armor materials for GoT custom metals (MC 1.21.4 / NeoForge 21.4.x).
 *
 * The last constructor parameter of ArmorMaterial changed from ResourceLocation
 * to ResourceKey<EquipmentAsset> in 1.21.4.  Use ResourceKey.create() with
 * EquipmentAssets.ROOT_ID as the registry key.
 */
public class GotModArmorMaterials {

    private static TagKey<Item> repairTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
    }

    private static ResourceKey<EquipmentAsset> equipmentKey(String path) {
        return ResourceKey.create(
                EquipmentAssets.ROOT_ID,
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, path)
        );
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
                    equipmentKey("copper")  // ← ResourceKey<EquipmentAsset>, not ResourceLocation
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
                    equipmentKey("bronze")  // ← ResourceKey<EquipmentAsset>, not ResourceLocation
            )
    );
}