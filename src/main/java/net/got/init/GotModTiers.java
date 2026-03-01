package net.got.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/**
 * Tool materials for GoT custom metals (MC 1.21.3 / NeoForge 21.3.x).
 *
 * ItemTags.REPAIRS_*_TOOLS constants don't exist in 1.21.3 — we build
 * the TagKey directly via TagKey.create instead.
 */
public class GotModTiers {

    private static TagKey<Item> repairTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(path));
    }

    /** Copper: durability 250, speed 6, +1.5 dmg bonus, enchantability 14 */
    public static final ToolMaterial COPPER = new ToolMaterial(
            BlockTags.NEEDS_STONE_TOOL,
            250,
            6.0f,
            1.5f,
            14,
            repairTag("repairs_iron_tools")
    );

    /** Bronze: durability 400, speed 7, +2 dmg bonus, enchantability 12 */
    public static final ToolMaterial BRONZE = new ToolMaterial(
            BlockTags.NEEDS_IRON_TOOL,
            400,
            7.0f,
            2.0f,
            12,
            repairTag("repairs_iron_tools")
    );

    /** Valyrian Steel: durability 3500, speed 10, +4 dmg bonus, enchantability 20 */
    public static final ToolMaterial VALYRIAN_STEEL = new ToolMaterial(
            BlockTags.NEEDS_DIAMOND_TOOL,
            3500,
            10.0f,
            4.0f,
            20,
            repairTag("repairs_diamond_tools")
    );
}
