package net.got.network;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * Creative inventory only (see {@link CustomArmorSlotsPayload}): the contents of the plate slots, one stack per
 * {@link net.got.plate.PlatePiece} in enum order.
 */
public record PlateSlotsPayload(List<ItemStack> stacks) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("got", "plate_slots");

    public static final Type<PlateSlotsPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PlateSlotsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_LIST_STREAM_CODEC, PlateSlotsPayload::stacks,
                    PlateSlotsPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
