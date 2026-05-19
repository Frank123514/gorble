package net.got.mixin;

import net.got.climate.WinterBiomeManager;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Intercepts {@link Biome#getBaseTemperature()} and {@link Biome#hasPrecipitation()}
 * at the bytecode level to return winter values when
 * {@link WinterBiomeManager#isWinterApplied()} is true.
 *
 * <p>This replaces the previous {@code Unsafe.putObject} approach, which wrote
 * to the raw {@code climateSettings} field but was silently ignored because
 * NeoForge's {@code ReplaceFieldWithGetterAccess} coremod redirects every
 * {@code GETFIELD Biome.climateSettings} to a generated getter that bypasses
 * the field entirely.  Injecting at the method level sidesteps that entirely.
 *
 * <p>Eligibility (mirrors {@link WinterBiomeManager}):
 * <ul>
 *   <li>Winter must be applied ({@link WinterBiomeManager#isWinterApplied()})</li>
 *   <li>The biome must have precipitation (dry biomes are left alone)</li>
 *   <li>The base temperature must be ≤ 0.95 (excludes deserts / jungles)</li>
 * </ul>
 */
@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {

    private static final float WINTER_TEMPERATURE  = 0.0f;
    private static final float MAX_WINTERISABLE_TEMP = 0.95f;

    /**
     * If winter is active and this biome is eligible, force the returned
     * temperature to {@value #WINTER_TEMPERATURE} so vanilla snow logic
     * (chunk random-ticks, water-freeze, weather particle choice) all
     * treat it as frozen.
     */
    @Inject(method = "getBaseTemperature()F", at = @At("RETURN"), cancellable = true, remap = false)
    private void got_winterTemperature(CallbackInfoReturnable<Float> cir) {
        if (!WinterBiomeManager.isWinterApplied()) return;

        float original = cir.getReturnValue();
        if (original > MAX_WINTERISABLE_TEMP) return;   // desert / jungle — leave alone

        Biome self = (Biome) (Object) this;
        if (!self.hasPrecipitation()) return;            // dry biome — leave alone

        cir.setReturnValue(WINTER_TEMPERATURE);
    }
}