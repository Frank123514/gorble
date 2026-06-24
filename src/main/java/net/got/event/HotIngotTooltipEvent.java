package net.got.event;

import net.got.GotMod;
import net.got.init.GotModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
public final class HotIngotTooltipEvent {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().has(GotModDataComponents.HOT.get())) {
            event.getToolTip().add(
                Component.translatable("item.got.hot_ingot.tooltip")
                    .withStyle(ChatFormatting.RED)
            );
        }
    }
}
