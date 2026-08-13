package net.got.client.model;

import net.got.init.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HotIronIngotModel implements ItemModel {

    private static final Identifier HOT_INGOT_KEY =
            Identifier.fromNamespaceAndPath("got", "hot_ingot");

    private final ItemModel normalModel;
    private final ItemModel hotModel;

    public HotIronIngotModel(ItemModel normalModel, ItemModel hotModel) {
        this.normalModel = normalModel;
        this.hotModel    = hotModel;
    }

    @Override
    public void update(ItemStackRenderState renderState,
                       ItemStack stack,
                       ItemModelResolver resolver,
                       ItemDisplayContext displayContext,
                       @Nullable ClientLevel level,
                       @Nullable ItemOwner entity,
                       int seed) {
        ItemModel target = stack.has(ModDataComponents.HOT.get()) ? hotModel : normalModel;
        target.update(renderState, stack, resolver, displayContext, level, entity, seed);
    }

    public static void inject(Map<Identifier, ItemModel> itemStackModels) {
        Identifier ironKey = Identifier.withDefaultNamespace("iron_ingot");
        Identifier hotKey  = HOT_INGOT_KEY;

        ItemModel normalModel = itemStackModels.get(ironKey);
        ItemModel hotModel    = itemStackModels.get(hotKey);

        if (normalModel == null || hotModel == null) return;

        itemStackModels.put(ironKey, new HotIronIngotModel(normalModel, hotModel));
    }
}
