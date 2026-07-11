package net.got.worldgen.placement;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers GoT's custom {@link PlacementModifierType}s so they can be
 * referenced by {@code "type": "got:..."} entries in placed-feature JSON.
 */
public final class GotPlacementModifiers {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, GotMod.MODID);

    /**
     * Fades out vegetation the further north of the snow latitude line a
     * candidate position sits. See {@link LatitudeVegetationFalloff}.
     */
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<LatitudeVegetationFalloff>>
            LATITUDE_VEGETATION_FALLOFF = PLACEMENT_MODIFIER_TYPES.register(
                    "latitude_vegetation_falloff",
                    () -> () -> LatitudeVegetationFalloff.CODEC);

    public static void register(IEventBus bus) {
        PLACEMENT_MODIFIER_TYPES.register(bus);
    }

    private GotPlacementModifiers() {}
}
