package net.got.cloak;

import java.util.function.Supplier;

import net.got.GotMod;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Storage for the extra "cloak" slot (cape / fur cloak). Vanilla's EquipmentSlot enum cannot be extended
 * in NeoForge 1.21.11, so the worn cloak lives in a synced, saved data attachment on the entity instead.
 * Works for players and any LivingEntity (the regional NPCs use it too).
 */
public final class CloakAttachments {

    public static final DeferredRegister<AttachmentType<?>> REGISTRY =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GotMod.MODID);

    public static final Supplier<AttachmentType<ItemStack>> CLOAK_SLOT = REGISTRY.register("cloak_slot",
            () -> AttachmentType.builder(() -> ItemStack.EMPTY)
                    .serialize(ItemStack.OPTIONAL_CODEC.fieldOf("stack"), stack -> !stack.isEmpty())
                    .sync(ItemStack.OPTIONAL_STREAM_CODEC)
                    .copyOnDeath()
                    .build());

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }

    private CloakAttachments() {}
}
