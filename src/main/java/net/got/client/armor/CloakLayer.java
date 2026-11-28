package net.got.client.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import net.got.GotMod;
import net.got.cloak.CloakItem;
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
 * Draws whatever is worn in the extra cloak slot (cape, fur cloak). It is the outermost layer: it runs after
 * the gambeson, hauberk, brigandine and plate layers and hangs behind / over the shoulders of all of them.
 * Each kind of cloak has its own model + texture (greyscale, tinted by the item's dye). The worn stack reaches the layer through render-state data.
 */
public class CloakLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public static final ContextKey<ItemStack> WORN_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(GotMod.MODID, "worn_cloak"));

    /** Movement snapshot (see CloakMotion) so the back panel can swing; set next to WORN_KEY. */
    public static final ContextKey<CloakMotion> MOTION_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath(GotMod.MODID, "cloak_motion"));

    private record Entry(CloakBodyModel model, Identifier texture) {}

    private final Map<CloakItem.Kind, Entry> entries;

    private CloakLayer(RenderLayerParent<S, M> parent, Map<CloakItem.Kind, Entry> entries) {
        super(parent);
        this.entries = entries;
    }

    /** Bakes a model for every kind of cloak. {@code baker} is e.g. {@code ctx::bakeLayer}. */
    public static <S extends HumanoidRenderState, M extends HumanoidModel<S>> CloakLayer<S, M> create(
            RenderLayerParent<S, M> parent, Function<ModelLayerLocation, ModelPart> baker) {
        Map<CloakItem.Kind, Entry> map = new EnumMap<>(CloakItem.Kind.class);
        map.put(CloakItem.Kind.CAPE, entry(baker, CapeModel.LAYER_LOCATION, "cape"));
        map.put(CloakItem.Kind.FUR_CLOAK, entry(baker, FurCloakModel.LAYER_LOCATION, "fur_cloak"));
        return new CloakLayer<>(parent, map);
    }

    private static Entry entry(Function<ModelLayerLocation, ModelPart> baker, ModelLayerLocation layer, String name) {
        // Torso + arms only: the cloaks have no leg geometry.
        return new Entry(new CloakBodyModel(baker.apply(layer)),
                Identifier.fromNamespaceAndPath(GotMod.MODID, "textures/entity/equipment/humanoid/" + name + ".png"));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, S state,
                       float yRot, float xRot) {
        ItemStack worn = state.getRenderData(WORN_KEY);
        if (worn == null || worn.isEmpty() || state.isInvisible) return;
        if (!(worn.getItem() instanceof CloakItem cloak)) return;
        Entry entry = entries.get(cloak.getKind());
        if (entry == null) return;

        // The textures are greyscale, so the dye colour (or the default) is applied as a multiplicative tint.
        int tint = 0xFF000000 | CloakItem.getColor(worn, cloak.getKind());

        // Args 7-10: tint, sprite (none: plain texture), entity OUTLINE colour (not a tint; 0 = none), crumbling overlay.
        collector.order(3).submitModel(entry.model(), state, poseStack, entry.model().renderType(entry.texture()),
                light, OverlayTexture.NO_OVERLAY, tint, null, 0, null);
    }
}
