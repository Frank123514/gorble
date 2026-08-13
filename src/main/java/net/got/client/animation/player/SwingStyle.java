package net.got.client.animation.player;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;

public enum SwingStyle {

    PUNCH,
    
    SWORD,
    
    GREATSWORD,
    
    AXE,
    
    TRIDENT,
    
    TOOL,
    
    GENERIC;

    public static SwingStyle fromItem(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return PUNCH;
        }
        Item item = stack.getItem();
        
        if (stack.is(ItemTags.SWORDS)) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            String path = id.getPath();
            
            if (path.contains("greatsword") || path.contains("claymore")) {
                return GREATSWORD;
            }
            return SWORD;
        }
        if (item instanceof TridentItem) {
            return TRIDENT;
        }
        if (item instanceof AxeItem) {
            return AXE;
        }
        if (stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES)) {
            return TOOL;
        }
        return GENERIC;
    }
}
