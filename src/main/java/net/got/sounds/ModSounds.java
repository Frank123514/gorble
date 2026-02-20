
package net.got.sounds;

import net.got.GotMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    //Sounds Deferred Register
    public static final DeferredRegister<SoundEvent> SOUNDS_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, GotMod.MODID);


    public static final DeferredHolder<SoundEvent, SoundEvent> WINDY_FOREST = registerSoundEvents("windy_forest");
    public static final DeferredHolder<SoundEvent, SoundEvent> WOLF_HOWL2 = registerSoundEvents("wolf_howl2");




    //Helper Method to register the sounds
    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = GotMod.id(name);
        return SOUNDS_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS_EVENTS.register(eventBus);
    }
}