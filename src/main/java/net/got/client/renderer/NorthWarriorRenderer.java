package net.got.client.renderer;

import net.got.entity.NorthWarriorEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public class NorthWarriorRenderer
        extends HumanoidMobRenderer<NorthWarriorEntity, NorthWarriorRenderer.State, HumanoidModel<NorthWarriorRenderer.State>> {

    public static class State extends HumanoidRenderState {
        public int variant = 0;
    }

    private static final ResourceLocation[] TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_warrior_2.png"),
    };

    public NorthWarriorRenderer(EntityRendererProvider.Context ctx) {
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
    public void extractRenderState(NorthWarriorEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant = entity.getVariant();
    }

    @Override
    public ResourceLocation getTextureLocation(State state) {
        return TEXTURES[state.variant % TEXTURES.length];
    }
}