package net.got.plate;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import net.got.GotMod;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** Storage for the five plate slots: one synced, saved ItemStack attachment per piece (works for any LivingEntity). */
public final class PlateAttachments {

    public static final DeferredRegister<AttachmentType<?>> REGISTRY =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GotMod.MODID);

    public static final Map<PlatePiece, Supplier<AttachmentType<ItemStack>>> SLOTS = new EnumMap<>(PlatePiece.class);

    static {
        for (PlatePiece piece : PlatePiece.values()) {
            SLOTS.put(piece, REGISTRY.register("plate_slot_" + piece.id,
                    () -> AttachmentType.builder(() -> ItemStack.EMPTY)
                            .serialize(ItemStack.OPTIONAL_CODEC.fieldOf("stack"), stack -> !stack.isEmpty())
                            .sync(ItemStack.OPTIONAL_STREAM_CODEC)
                            .copyOnDeath()
                            .build()));
        }
    }

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }

    private PlateAttachments() {}
}
