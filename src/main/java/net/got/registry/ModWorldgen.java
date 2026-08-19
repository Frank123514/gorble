package net.got.registry;

import net.got.GotMod;
import net.got.worldgen.MapReloadListener;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

public final class ModWorldgen {

    @SubscribeEvent
    public static void onAddReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(
                Identifier.fromNamespaceAndPath(GotMod.MODID, "map_reload"),
                new MapReloadListener()
        );
    }
}