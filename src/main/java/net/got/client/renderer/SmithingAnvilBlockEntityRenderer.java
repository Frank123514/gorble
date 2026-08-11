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

// NOTE (1.21.9+ rendering rewrite): see BellowsBlockEntityRenderer for background on the
// extractRenderState()/submit() split. ItemStackRenderState#render was renamed to #submit and now
// takes a SubmitNodeCollector + outline color instead of a MultiBufferSource - that part is
// confirmed by the migration primer. The submit() method signature/light-coord source below is
// my best-effort reconstruction and should be checked against your local NeoForge jar.
@OnlyIn(Dist.CLIENT)
public class SmithingAnvilBlockEntityRenderer implements BlockEntityRenderer<SmithingAnvilBlockEntity, SmithingAnvilBlockEntityRenderer.AnvilRenderState> {

    private final ItemModelResolver itemModelResolver;

    public SmithingAnvilBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelResolver = ctx.getItemModelResolver();
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
        super.extractRenderState(be, state, partialTick, cameraPos, crumblingOverlay);

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

        state.itemRenderState.submit(poseStack, collector, state.aboveLight, state.outlineColor);

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
