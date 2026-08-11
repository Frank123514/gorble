package net.got.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.got.block.SmithingAnvilBlockEntity;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class SmithingAnvilBlockEntityRenderer implements BlockEntityRenderer<SmithingAnvilBlockEntity, SmithingAnvilBlockEntityRenderer.AnvilRenderState> {

    private final ItemModelResolver itemModelResolver;

    public SmithingAnvilBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.itemModelResolver();
    }

    public static class AnvilRenderState extends BlockEntityRenderState {
        final ItemStackRenderState itemRenderState = new ItemStackRenderState();
        int aboveLight;
        boolean hasItem;
    }

    @Override
    public AnvilRenderState createRenderState() {
        return new AnvilRenderState();
    }

    @Override
    public void extractRenderState(SmithingAnvilBlockEntity be, AnvilRenderState state, float partialTick,
                                   Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(be, state, crumblingOverlay);

        ItemStack input = be.getInputItem();
        ItemStack toRender = input.isEmpty() ? be.getLastCraftedItem() : input;
        state.hasItem = !toRender.isEmpty();
        if (!state.hasItem) return;

        // Sample light from the air above the anvil top face, not the block itself
        state.aboveLight = getLightAbove(be.getLevel(), be.getBlockPos());

        itemModelResolver.updateForTopItem(
                state.itemRenderState,
                toRender,
                ItemDisplayContext.FIXED,
                be.getLevel(),
                null,
                0
        );
    }

    @Override
    public void submit(AnvilRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        if (!state.hasItem || state.itemRenderState.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.025, 0.5);
        poseStack.scale(0.75f, 0.75f, 0.75f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90f));

        // NOTE: ItemStackRenderState#submit's exact parameter list could not be verified against your
        // local jar (the decompiler failed on that class). This follows the confirmed pattern from the
        // 1.21.9 migration primer ("render -> submit, taking a SubmitNodeCollector instead of a
        // MultiBufferSource, plus an outline color") applied to the old render(PoseStack, MultiBufferSource,
        // packedLight, packedOverlay) signature. If this doesn't compile locally, check IntelliJ's
        // "go to declaration" on ItemStackRenderState#submit and let me know the real parameter list.
        state.itemRenderState.submit(poseStack, collector, state.aboveLight,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    private static int getLightAbove(Level level, BlockPos pos) {
        if (level == null) return LightTexture.FULL_BRIGHT;
        BlockPos above = pos.above();
        int block = level.getBrightness(LightLayer.BLOCK, above);
        int sky   = level.getBrightness(LightLayer.SKY,   above);
        return LightTexture.pack(block, sky);
    }
}