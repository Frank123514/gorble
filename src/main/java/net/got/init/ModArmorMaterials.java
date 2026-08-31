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

    /**
     * Helmet-only materials for the distinct headwear types from the AWOIAF
     * Armament#Headwear page. Each gets its own equipment asset so it renders
     * with its own texture; durability/defense follow the wiki's rough
     * protection tiers (padded/mail lightest, greathelms heaviest).
     */
    private static Holder<ArmorMaterial> helmetOnly(String name, int durability, int defense,
                                                      int enchantability, float toughness) {
        return Holder.direct(
                new ArmorMaterial(
                        durability,
                        Map.of(ArmorType.HELMET, defense),
                        enchantability,
                        SoundEvents.ARMOR_EQUIP_IRON,
                        toughness,
                        0.0f,
                        repairTag("repairs_steel_armor"),
                        equipmentKey(name)
                )
        );
    }

    public static final Holder<ArmorMaterial> SKULL_CAP =
            helmetOnly("skull_cap", 130, 1, 9, 0.0f);
    public static final Holder<ArmorMaterial> CONICAL_CAP =
            helmetOnly("conical_cap", 145, 2, 9, 0.0f);
    public static final Holder<ArmorMaterial> KETTLE_HELM =
            helmetOnly("kettle_helm", 160, 2, 9, 0.0f);
    public static final Holder<ArmorMaterial> PADDED_COIF =
            helmetOnly("padded_coif", 70, 1, 9, 0.0f);
    public static final Holder<ArmorMaterial> MAIL_COIF =
            helmetOnly("mail_coif", 120, 2, 10, 0.5f);
    public static final Holder<ArmorMaterial> HALFHELM =
            helmetOnly("halfhelm", 170, 2, 10, 0.5f);
    public static final Holder<ArmorMaterial> BASCINET =
            helmetOnly("bascinet", 200, 3, 10, 1.0f);
    public static final Holder<ArmorMaterial> GREATHELM_FLAT =
            helmetOnly("greathelm_flat", 230, 3, 10, 1.0f);
    public static final Holder<ArmorMaterial> GREATHELM_ROUNDED =
            helmetOnly("greathelm_rounded", 250, 4, 10, 1.5f);
}