package net.got.entity.client.npc.smallfolk;

import net.got.entity.npc.NpcGender;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Concrete vanilla renderer for ALL Smallfolk-hierarchy NPC cultures
 * (Tier 1 civilians, Tier 2 levies, Tier 3 skilled fighters).
 *
 * <p>Uses the 1.21.4 render-state pattern: entity data (gender, variant) is
 * copied into {@link SmallfolkRenderState} during {@link #extractRenderState},
 * then used in {@link #getTextureLocation} to pick the correct texture.
 *
 * <p>Register in ClientSetup with texture arrays from the entity class:
 * <pre>{@code
 *   event.registerEntityRenderer(GotModEntities.NORTHMAN.get(),
 *       ctx -> new SmallfolkRenderer<>(ctx,
 *           NorthmanEntity.MALE_TEXTURES, NorthmanEntity.FEMALE_TEXTURES));
 * }</pre>
 *
 * @param <T> any entity that extends {@link SmallfolkEntity}
 */
public class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    private final ResourceLocation[] maleTextures;
    private final ResourceLocation[] femaleTextures;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                              ResourceLocation[] maleTextures,
                              ResourceLocation[] femaleTextures) {
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        this.maleTextures   = maleTextures;
        this.femaleTextures = femaleTextures;
    }

    @Override
    public SmallfolkRenderState createRenderState() {
        return new SmallfolkRenderState();
    }

    @Override
    public void extractRenderState(T entity, SmallfolkRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isFemale         = entity.getGender() == NpcGender.FEMALE;
        state.variant          = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();
    }

    @Override
    public ResourceLocation getTextureLocation(SmallfolkRenderState state) {
        if (state.isFemale) {
            int idx = state.variant - state.variantsPerGender;
            return femaleTextures[Math.abs(idx) % femaleTextures.length];
        }
        return maleTextures[state.variant % maleTextures.length];
    }
}
