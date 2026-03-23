package net.got.init;

import net.got.GotMod;
import net.got.menu.OvenMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY =
            DeferredRegister.create(Registries.MENU, GotMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<OvenMenu>> OVEN =
            REGISTRY.register("oven", () ->
                    new MenuType<>(OvenMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
