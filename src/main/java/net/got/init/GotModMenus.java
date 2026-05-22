package net.got.init;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY =
            DeferredRegister.create(Registries.MENU, GotMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<net.got.menu.NpcTradeMenu>> NPC_TRADE =
            REGISTRY.register("npc_trade", () ->
                    new MenuType<>(net.got.menu.NpcTradeMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
