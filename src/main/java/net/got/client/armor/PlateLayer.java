package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import net.got.GotMod;
import net.got.plate.PlateItem;
import net.got.plate.PlatePiece;
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
import net.minecraft.world.item.ItemStack;

/**
 * Draws whatever is worn in the five plate slots (gorget, spaulders, vambraces, breastplate, cuisses). Runs
 * after the brigandine layer; the plate models are inflated more than every other armor layer, so they sit on
 * top. The worn stacks reach the layer through render-state data, one model + texture per piece.
 */
public class PlateLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public static final ContextKey<Map<PlatePiece, ItemStack>> WORN_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(GotMod.MODID, "worn_plate"));

    private final Map<PlatePiece, GotBodyArmorModel> models;

    private PlateLayer(RenderLayerParent<S, M> parent, Map<PlatePiece, GotBodyArmorModel> models) {
        super(parent);
        this.models = models;
    }

    /** Bakes a model for every piece. {@code baker} is e.g. {@code ctx::bakeLayer}. */
    public static <S extends HumanoidRenderState, M extends HumanoidModel<S>> PlateLayer<S, M> create(
            RenderLayerParent<S, M> parent, Function<ModelLayerLocation, ModelPart> baker) {
        Map<PlatePiece, GotBodyArmorModel> map = new EnumMap<>(PlatePiece.class);
        for (PlatePiece piece : PlatePiece.values()) {
            map.put(piece, new GotBodyArmorModel(baker.apply(PlateModel.LAYERS.get(piece)), true));
        }
        return new PlateLayer<>(parent, map);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, S state,
                       float yRot, float xRot) {
        Map<PlatePiece, ItemStack> worn = state.getRenderData(WORN_KEY);
        if (worn == null || state.isInvisible) return;

        for (PlatePiece piece : PlatePiece.values()) {
            ItemStack stack = worn.get(piece);
            if (stack == null || stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof PlateItem item) || item.getPiece() != piece) continue;

            GotBodyArmorModel model = models.get(piece);
            // 7th argument is the entity OUTLINE colour (not a tint); non-zero draws a solid silhouette. 0 = none.
            collector.order(2).submitModel(model, state, poseStack, model.renderType(piece.texture()),
                    light, OverlayTexture.NO_OVERLAY, 0, null);
        }
    }
}
