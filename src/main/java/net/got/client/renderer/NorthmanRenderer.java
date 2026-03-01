package net.got.client.renderer;

import net.got.entity.NorthmanEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for the Northman smallfolk.
 * Picks one of 8 skin textures (4 male + 4 female) via a per-entity variant index.
 *
 *   0 = northman_male_1      4 = northman_female_1
 *   1 = northman_male_2      5 = northman_female_2
 *   2 = northman_male_3      6 = northman_female_3
 *   3 = northman_male_4      7 = northman_female_4
 */
public class NorthmanRenderer
        extends HumanoidMobRenderer<NorthmanEntity, NorthmanRenderer.State, HumanoidModel<NorthmanRenderer.State>> {

    public static class State extends HumanoidRenderState {
        public int variant = 0;
    }

    private static final ResourceLocation[] TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_2.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_3.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_male_4.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_2.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_3.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/northman_female_4.png"),
    };

    public NorthmanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE)),
                0.5f);
        this.addLayer(new HumanoidArmorLayer<State, HumanoidModel<State>, HumanoidModel<State>>(
                this,
                new HumanoidModel<State>(ctx.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel<State>(ctx.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                ctx.getEquipmentRenderer()
        ));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(NorthmanEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant = entity.getVariant();
    }

    @Override
    public ResourceLocation getTextureLocation(State state) {
        return TEXTURES[state.variant % TEXTURES.length];
    }
}