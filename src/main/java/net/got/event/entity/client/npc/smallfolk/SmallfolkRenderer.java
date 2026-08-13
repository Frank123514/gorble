package net.got.event.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.npc.NpcGender;
import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

public final class SmallfolkRenderer<T extends SmallfolkEntity>
        extends HumanoidMobRenderer<T, SmallfolkRenderState, HumanoidModel<SmallfolkRenderState>> {

    private final Identifier[] maleTextures;
    private final Identifier[] femaleTextures;

    private static final float PLAYER_SCALE = 0.9375F;

    public SmallfolkRenderer(EntityRendererProvider.Context ctx,
                             Identifier[] maleTextures,
                             Identifier[] femaleTextures) {
        super(ctx,
                new SmallfolkModel(ctx.bakeLayer(ModelLayers.SMALLFOLK)),
                PLAYER_SCALE);
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

        boolean female = entity.getGender() == NpcGender.FEMALE;
        state.isFemale = female;
        state.variant  = entity.getVariant();
        state.variantsPerGender = entity.getVariantsPerGender();

        if (female) {
            int idx = entity.getVariant() - entity.getVariantsPerGender();
            state.texture = femaleTextures[Math.abs(idx) % femaleTextures.length];
        } else {
            state.texture = maleTextures[entity.getVariant() % maleTextures.length];
        }

        state.isTalking    = entity.isTalking();
        state.talkHeadYaw  = entity.getTalkHeadYaw();
        state.talkHeadPitch = entity.getTalkHeadPitch();
        state.talkGesture  = entity.getTalkGesture();
    }

    @Override
    protected void scale(SmallfolkRenderState state, PoseStack poseStack) {
        poseStack.scale(PLAYER_SCALE, PLAYER_SCALE, PLAYER_SCALE);
    }

    @Override
    public Identifier getTextureLocation(SmallfolkRenderState state) {
        return state.texture;
    }
}