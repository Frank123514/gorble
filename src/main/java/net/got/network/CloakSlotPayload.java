package net.got.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/** Creative inventory only (see {@link CustomArmorSlotsPayload}): the contents of the cloak slot. */
public record CloakSlotPayload(ItemStack cloak) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("got", "cloak_slot");

    public static final Type<CloakSlotPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, CloakSlotPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC, CloakSlotPayload::cloak,
                    CloakSlotPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
