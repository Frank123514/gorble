package net.minecraft.world.item.equipment.trim;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;

public record TrimMaterial(
    String assetName, Holder<Item> ingredient, float itemModelIndex, Map<ResourceLocation, String> overrideArmorMaterials, Component description
) {
    public static final Codec<TrimMaterial> DIRECT_CODEC = RecordCodecBuilder.create(
        p_381597_ -> p_381597_.group(
                    ExtraCodecs.RESOURCE_PATH_CODEC.fieldOf("asset_name").forGetter(TrimMaterial::assetName),
                    Item.CODEC.fieldOf("ingredient").forGetter(TrimMaterial::ingredient),
                    Codec.FLOAT.fieldOf("item_model_index").forGetter(TrimMaterial::itemModelIndex),
                    Codec.unboundedMap(ResourceLocation.CODEC, Codec.STRING)
                        .optionalFieldOf("override_armor_materials", Map.of())
                        .forGetter(TrimMaterial::overrideArmorMaterials),
                    ComponentSerialization.CODEC.fieldOf("description").forGetter(TrimMaterial::description)
                )
                .apply(p_381597_, TrimMaterial::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TrimMaterial> DIRECT_STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        TrimMaterial::assetName,
        ByteBufCodecs.holderRegistry(Registries.ITEM),
        TrimMaterial::ingredient,
        ByteBufCodecs.FLOAT,
        TrimMaterial::itemModelIndex,
        ByteBufCodecs.map(Object2ObjectOpenHashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.STRING_UTF8),
        TrimMaterial::overrideArmorMaterials,
        ComponentSerialization.STREAM_CODEC,
        TrimMaterial::description,
        TrimMaterial::new
    );
    public static final Codec<Holder<TrimMaterial>> CODEC = RegistryFileCodec.create(Registries.TRIM_MATERIAL, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TrimMaterial>> STREAM_CODEC = ByteBufCodecs.holder(
        Registries.TRIM_MATERIAL, DIRECT_STREAM_CODEC
    );

    public static TrimMaterial create(String assetName, Item ingredient, float itemModelIndex, Component description, Map<ResourceLocation, String> overrideArmorMaterials) {
        return new TrimMaterial(assetName, BuiltInRegistries.ITEM.wrapAsHolder(ingredient), itemModelIndex, overrideArmorMaterials, description);
    }
}
