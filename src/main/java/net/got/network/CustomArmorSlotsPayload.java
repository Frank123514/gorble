package net.got.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * Creative inventory only: the creative screen edits slots on the client and tells the server by vanilla slot
 * number (1..45), which never covers the gambeson / brigandine slots. The client sends their contents here instead.
 */
public record CustomArmorSlotsPayload(ItemStack gambeson, ItemStack brigandine, ItemStack coif, ItemStack hauberk) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath("got", "custom_armor_slots");

    public static final Type<CustomArmorSlotsPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, CustomArmorSlotsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC, CustomArmorSlotsPayload::gambeson,
                    ItemStack.OPTIONAL_STREAM_CODEC, CustomArmorSlotsPayload::brigandine,
                    ItemStack.OPTIONAL_STREAM_CODEC, CustomArmorSlotsPayload::coif,
                    ItemStack.OPTIONAL_STREAM_CODEC, CustomArmorSlotsPayload::hauberk,
                    CustomArmorSlotsPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
