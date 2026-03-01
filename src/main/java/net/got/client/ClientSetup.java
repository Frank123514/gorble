package net.got.client;

import net.got.client.input.GotKeybinds;
import net.got.client.renderer.NorthBowmanRenderer;
import net.got.client.renderer.NorthmanRenderer;
import net.got.client.renderer.NorthWarriorRenderer;
import net.got.init.GotModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = "got",
        value = Dist.CLIENT
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(GotKeybinds.OPEN_MAP);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Explicit lambdas required in 1.21.4 due to the 3-arg generic renderer signature
        event.registerEntityRenderer(GotModEntities.NORTH_BOWMAN.get(),
                ctx -> new NorthBowmanRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTH_WARRIOR.get(),
                ctx -> new NorthWarriorRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTHMAN.get(),
                ctx -> new NorthmanRenderer(ctx));
    }
}