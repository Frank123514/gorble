package net.got.init;

import net.got.GotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
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
 * The last constructor parameter of ArmorMaterial changed from Identifier
 * to ResourceKey<EquipmentAsset> in 1.21.4.  Use ResourceKey.create() with
 * EquipmentAssets.ROOT_ID as the registry key.
 */
public class ModArmorMaterials {

    private static TagKey<Item> repairTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace(path));
    }

    private static ResourceKey<EquipmentAsset> equipmentKey(String path) {
        return ResourceKey.create(
                EquipmentAssets.ROOT_ID,
                Identifier.fromNamespaceAndPath(GotMod.MODID, path)
        );
    }

    /**
     * Steel: 3/6/5/3 defense, enchantability 10, 1.0 toughness.
     */
    public static final Holder<ArmorMaterial> STEEL = Holder.direct(
            new ArmorMaterial(
                    800,
                    Map.of(
                            ArmorType.HELMET,     3,
                            ArmorType.CHESTPLATE, 6,
                            ArmorType.LEGGINGS,   5,
                            ArmorType.BOOTS,      3
                    ),
                    10,
                    SoundEvents.ARMOR_EQUIP_IRON,
                    1.0f,
                    0.0f,
                    repairTag("repairs_steel_armor"),
                    equipmentKey("steel")
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
                    equipmentKey("bronze")  // ← ResourceKey<EquipmentAsset>, not Identifier
            )
    );
}