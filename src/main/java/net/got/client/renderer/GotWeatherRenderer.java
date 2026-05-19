package net.got.client.renderer;

import net.got.climate.SeasonManager;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

@EventBusSubscriber(modid = "got", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class GotWeatherRenderer extends WeatherEffectRenderer {

    public static final GotWeatherRenderer INSTANCE = new GotWeatherRenderer();

    private GotWeatherRenderer() {}

    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        DimensionSpecialEffects vanillaOverworld = event.getSpecialEffects(
                net.minecraft.world.level.dimension.BuiltinDimensionTypes.OVERWORLD);

        DimensionSpecialEffects customEffects = new DimensionSpecialEffects(
                vanillaOverworld.getCloudHeight(),
                vanillaOverworld.hasGround(),
                vanillaOverworld.getSkyType(),
                vanillaOverworld.forceBrightLightmap(),
                vanillaOverworld.getConstantAmbientLight()
        ) {
            @Override
            public boolean isSunriseOrSunset(float timeOfDay) {
                return vanillaOverworld.isSunriseOrSunset(timeOfDay);
            }

            @Override
            public float[] getSunriseColor(float timeOfDay, float partialTicks) {
                return vanillaOverworld.getSunriseColor(timeOfDay, partialTicks);
            }

            @Override
            public WeatherEffectRenderer getWeatherEffectRenderer() {
                return INSTANCE;
            }
        };

        event.register(net.minecraft.world.level.dimension.BuiltinDimensionTypes.OVERWORLD, customEffects);
    }

    @Override
    protected Biome.Precipitation getPrecipitationAt(Level level, BlockPos pos) {
        Biome.Precipitation vanilla = super.getPrecipitationAt(level, pos);

        if (vanilla == Biome.Precipitation.RAIN && SeasonManager.getCurrentSeason().isWinter()) {
            return Biome.Precipitation.SNOW;
        }

        return vanilla;
    }
}