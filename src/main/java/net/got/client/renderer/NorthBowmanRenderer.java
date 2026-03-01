package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.entity.NorthBowmanEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for the North Bowman.
 * Uses a custom RenderState that carries the variant index so
 * getTextureLocation() can choose the correct skin without touching the entity.
 */
public class NorthBowmanRenderer
        extends HumanoidMobRenderer<NorthBowmanEntity, NorthBowmanRenderer.State, HumanoidModel<NorthBowmanRenderer.State>> {

    /** Inner render-state class — holds the skin variant chosen at spawn. */
    public static class State extends HumanoidRenderState {
        public int variant = 0;
    }

    private static final ResourceLocation[] TEXTURES = {
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_bowman_1.png"),
            ResourceLocation.fromNamespaceAndPath("got", "textures/entity/north_bowman_2.png"),
    };

    public NorthBowmanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE)),
                0.5f);
        this.addLayer(new HumanoidArmorLayer<NorthBowmanEntity, HumanoidModel<State>, State>(
                this,
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                ctx.getEquipmentRenderer()
        ));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(NorthBowmanEntity entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.variant = entity.getVariant();
    }

    @Override
    public ResourceLocation getTextureLocation(State state) {
        return TEXTURES[state.variant % TEXTURES.length];
    }
}