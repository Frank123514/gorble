package net.got.client.model;

import net.got.init.GotModDataComponents;
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

/**
 * A thin wrapper ItemModel for minecraft:iron_ingot that swaps to the
 * got:hot_ingot model when the got:hot data component is present.
 *
 * Registered via ModelEvent.ModifyBakingResult, which gives access to the
 * mutable itemStackModels map before it's locked.
 */
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
        ItemModel target = stack.has(GotModDataComponents.HOT.get()) ? hotModel : normalModel;
        target.update(renderState, stack, resolver, displayContext, level, entity, seed);
    }

    /**
     * Called from ClientSetup.onModifyBakingResult.
     * Wraps the vanilla iron_ingot ItemModel with our hot-aware version.
     */
    public static void inject(Map<Identifier, ItemModel> itemStackModels) {
        Identifier ironKey = Identifier.withDefaultNamespace("iron_ingot");
        Identifier hotKey  = HOT_INGOT_KEY;

        ItemModel normalModel = itemStackModels.get(ironKey);
        ItemModel hotModel    = itemStackModels.get(hotKey);

        if (normalModel == null || hotModel == null) return; // safety: both must be loaded

        itemStackModels.put(ironKey, new HotIronIngotModel(normalModel, hotModel));
    }
}
