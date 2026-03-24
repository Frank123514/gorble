package net.got.registry;

import net.got.GotMod;
import net.got.worldgen.MapReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;  // ← renamed in 21.4

public final class ModWorldgen {

    @SubscribeEvent
    public static void onAddReloadListeners(AddServerReloadListenersEvent event) {  // ← renamed
        event.addListener(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "map_reload"),  // ← id required
                new MapReloadListener()
        );
    }
}