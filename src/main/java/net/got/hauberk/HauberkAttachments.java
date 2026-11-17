package net.got.hauberk;

import java.util.function.Supplier;

import net.got.GotMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Storage for the extra "hauberk" armor slot. Vanilla's EquipmentSlot enum cannot be extended
 * in NeoForge 1.21.11, so the worn hauberk lives in a synced, saved data attachment on the
 * entity instead. Works for players and any LivingEntity (the regional NPCs use it too).
 */
public final class HauberkAttachments {

    public static final DeferredRegister<AttachmentType<?>> REGISTRY =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GotMod.MODID);

    public static final Supplier<AttachmentType<ItemStack>> HAUBERK_SLOT = REGISTRY.register("hauberk_slot",
            () -> AttachmentType.builder(() -> ItemStack.EMPTY)
                    .serialize(ItemStack.OPTIONAL_CODEC.fieldOf("stack"), stack -> !stack.isEmpty())
                    .sync(ItemStack.OPTIONAL_STREAM_CODEC)
                    .copyOnDeath()
                    .build());

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }

    private HauberkAttachments() {}
}
