package net.got.init;

import com.mojang.serialization.Codec;
import net.got.GotMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> REGISTRY =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GotMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> HOT =
            REGISTRY.register("hot", () -> DataComponentType.<Unit>builder()
                    .persistent(Unit.CODEC)
                    .networkSynchronized(ByteBufCodecs.fromCodec(Unit.CODEC))
                    .build());

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}