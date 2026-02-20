package net.got.client;

import net.got.client.input.GotKeybinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = "got",
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.MOD
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(GotKeybinds.OPEN_MAP);
    }
}
