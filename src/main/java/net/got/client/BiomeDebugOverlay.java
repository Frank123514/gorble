package net.got.client;

import net.got.climate.SeasonManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT)
public final class BiomeDebugOverlay {

    private BiomeDebugOverlay() {}

}
