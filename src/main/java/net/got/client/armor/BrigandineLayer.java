package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.got.GotMod;
import net.got.init.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Draws whatever is worn in the extra outer-layer slot (brigandine, boiled leather jerkin). Runs after
 * the gambeson layer and the vanilla armor layer, and the models here are larger than both the gambeson's
 * and the hauberk's, so they sit on top. The worn stack reaches the layer through render-state data.
 * Each item that can go in the slot has its own model + texture, looked up by item.
 */
public class BrigandineLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public static final ContextKey<ItemStack> WORN_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(GotMod.MODID, "worn_brigandine"));

    private record Entry(GotBodyArmorModel model, Identifier texture) {}

    private final Map<Item, Entry> entries;

    private BrigandineLayer(RenderLayerParent<S, M> parent, Map<Item, Entry> entries) {
        super(parent);
        this.entries = entries;
    }

    /** Bakes a model for every item that fits the slot. {@code baker} is e.g. {@code ctx::bakeLayer}. */
    public static <S extends HumanoidRenderState, M extends HumanoidModel<S>> BrigandineLayer<S, M> create(
            RenderLayerParent<S, M> parent, Function<ModelLayerLocation, ModelPart> baker) {
        Map<Item, Entry> map = new HashMap<>();
        map.put(ModItems.BRIGANDINE.get(), entry(baker, BrigandineModel.LAYER_LOCATION, "brigandine"));
        map.put(ModItems.BOILED_LEATHER_JERKIN.get(),
                entry(baker, BoiledLeatherJerkinModel.LAYER_LOCATION, "boiled_leather_jerkin"));
        return new BrigandineLayer<>(parent, map);
    }

    private static Entry entry(Function<ModelLayerLocation, ModelPart> baker, ModelLayerLocation layer, String name) {
        return new Entry(new GotBodyArmorModel(baker.apply(layer), true),
                Identifier.fromNamespaceAndPath(GotMod.MODID, "textures/entity/equipment/humanoid/" + name + ".png"));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, S state,
                       float yRot, float xRot) {
        ItemStack worn = state.getRenderData(WORN_KEY);
        if (worn == null || worn.isEmpty() || state.isInvisible) return;
        Entry entry = entries.get(worn.getItem());
        if (entry == null) return;

        // 7th argument is the entity OUTLINE colour (not a tint); non-zero draws a solid silhouette. 0 = none.
        collector.order(2).submitModel(entry.model(), state, poseStack, entry.model().renderType(entry.texture()),
                light, OverlayTexture.NO_OVERLAY, 0, null);
    }
}
