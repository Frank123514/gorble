package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.BellowsBlock;
import net.got.block.BellowsBlockEntity;
import net.got.client.animation.BellowsAnimations;
import net.got.client.model.BellowsModel;
import net.got.event.entity.client.model.GotModelLayers;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

// NOTE (1.21.9+ rendering rewrite): BlockEntityRenderer now works in two phases -
// extractRenderState() copies whatever the submit() phase will need off of the block entity
// (since submit() may run later / off-thread relative to the block entity's live state), and
// submit() pushes the actual model draw calls to the SubmitNodeCollector. I could not verify the
// exact submitModel(...) overload signature/argument order against the real jar in this environment
// (no network access to Mojang/NeoForge Maven) - please check this compiles locally and adjust the
// submitModel call if the argument list doesn't match.
@OnlyIn(Dist.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity, BellowsBlockEntityRenderer.BellowsRenderState> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath("got", "textures/block/bellows.png");

    private final BellowsModel model;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new BellowsModel(ctx.bakeLayer(GotModelLayers.BELLOWS));
    }

    public static LayerDefinition createBodyLayer() {
        return BellowsModel.createBodyLayer();
    }

    public static class BellowsRenderState extends BlockEntityRenderState {
        boolean pumping;
        float animationProgress;
        float partialTick;
        BlockState blockState;
    }

    @Override
    public BellowsRenderState createRenderState() {
        return new BellowsRenderState();
    }

    @Override
    public void extractRenderState(BellowsBlockEntity be, BellowsRenderState state, float partialTick,
                                    Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(be, state, partialTick, cameraPos, crumblingOverlay);
        state.pumping = be.pumping;
        state.animationProgress = be.animationProgress;
        state.partialTick = partialTick;
        state.blockState = be.getBlockState();
    }

    @Override
    public void submit(BellowsRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                        CameraRenderState cameraState) {

        // Drive animation from the block entity's existing tick counter.
        // Always call applyAnimation - even when not pumping - so the shared model
        // instance is reset to rest pose before each bellows is rendered.
        // (A single BellowsModel is reused for every bellows in the world.)
        if (state.pumping) {
            float ageInTicks = state.animationProgress + state.partialTick;
            model.applyAnimation(BellowsAnimations.PUMPING, ageInTicks, 1.0F);
        } else {
            model.applyAnimation(BellowsAnimations.PUMPING, 0f, 0.0F);
        }

        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        float yRot = state.blockState.getValue(BellowsBlock.FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(270f - yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        // TODO (1.21.9+): verify this submitModel(...) overload against your local NeoForge jar.
        // Per the migration primer, submitModel roughly takes: pose stack, model, render state,
        // render type, light coords, overlay coords, tint color, optional texture, outline color,
        // and an optional crumbling overlay - but I could not confirm exact parameter order here.
        collector.submitModel(
                poseStack,
                model,
                state,
                RenderTypes.entityCutout(TEXTURE),
                state.lightCoords,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                -1,
                TEXTURE,
                state.outlineColor,
                state.breakProgress
        );

        poseStack.popPose();
    }
}
