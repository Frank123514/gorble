package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemFrameRenderer<T extends ItemFrame> extends EntityRenderer<T, ItemFrameRenderState> {
    public static final int GLOW_FRAME_BRIGHTNESS = 5;
    public static final int BRIGHT_MAP_LIGHT_ADJUSTMENT = 30;
    private final ItemRenderer itemRenderer;
    private final MapRenderer mapRenderer;
    private final BlockRenderDispatcher blockRenderer;

    public ItemFrameRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.mapRenderer = context.getMapRenderer();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return entity.getType() == EntityType.GLOW_ITEM_FRAME
            ? Math.max(5, super.getBlockLightLevel(entity, pos))
            : super.getBlockLightLevel(entity, pos);
    }

    public void render(ItemFrameRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(renderState, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        Direction direction = renderState.direction;
        Vec3 vec3 = this.getRenderOffset(renderState);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875;
        poseStack.translate((double)direction.getStepX() * 0.46875, (double)direction.getStepY() * 0.46875, (double)direction.getStepZ() * 0.46875);
        float f;
        float f1;
        if (direction.getAxis().isHorizontal()) {
            f = 0.0F;
            f1 = 180.0F - direction.toYRot();
        } else {
            f = (float)(-90 * direction.getAxisDirection().getStep());
            f1 = 180.0F;
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(f));
        poseStack.mulPose(Axis.YP.rotationDegrees(f1));
        ItemStack itemstack = renderState.itemStack;
        if (!renderState.isInvisible) {
            ModelManager modelmanager = this.blockRenderer.getBlockModelShaper().getModelManager();
            ModelResourceLocation modelresourcelocation = this.getFrameModelResourceLoc(renderState.isGlowFrame, itemstack);
            poseStack.pushPose();
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            this.blockRenderer
                .getModelRenderer()
                .renderModel(
                    poseStack.last(),
                    bufferSource.getBuffer(RenderType.entitySolidZOffsetForward(TextureAtlas.LOCATION_BLOCKS)),
                    null,
                    modelmanager.getModel(modelresourcelocation),
                    1.0F,
                    1.0F,
                    1.0F,
                    packedLight,
                    OverlayTexture.NO_OVERLAY
                );
            poseStack.popPose();
        }

        if (!itemstack.isEmpty()) {
            MapId mapid = renderState.mapId;
            if (renderState.isInvisible) {
                poseStack.translate(0.0F, 0.0F, 0.5F);
            } else {
                poseStack.translate(0.0F, 0.0F, 0.4375F);
            }

            int j = mapid != null ? renderState.rotation % 4 * 2 : renderState.rotation;
            poseStack.mulPose(Axis.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
            if (!net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.RenderItemInFrameEvent(renderState, this, poseStack, bufferSource, packedLight)).isCanceled()) {
            if (mapid != null) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                float f2 = 0.0078125F;
                poseStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
                poseStack.translate(-64.0F, -64.0F, 0.0F);
                poseStack.translate(0.0F, 0.0F, -1.0F);
                int i = this.getLightVal(renderState.isGlowFrame, 15728850, packedLight);
                this.mapRenderer.render(renderState.mapRenderState, poseStack, bufferSource, true, i);
            } else if (renderState.itemModel != null) {
                int k = this.getLightVal(renderState.isGlowFrame, 15728880, packedLight);
                poseStack.scale(0.5F, 0.5F, 0.5F);
                this.itemRenderer.render(itemstack, ItemDisplayContext.FIXED, false, poseStack, bufferSource, k, OverlayTexture.NO_OVERLAY, renderState.itemModel);
            }
            }
        }

        poseStack.popPose();
    }

    private int getLightVal(boolean isGlowFrame, int glowLightValue, int lightValue) {
        return isGlowFrame ? glowLightValue : lightValue;
    }

    private ModelResourceLocation getFrameModelResourceLoc(boolean isGlowFrame, ItemStack stack) {
        if (stack.has(DataComponents.MAP_ID)) {
            return isGlowFrame ? BlockStateModelLoader.GLOW_MAP_FRAME_LOCATION : BlockStateModelLoader.MAP_FRAME_LOCATION;
        } else {
            return isGlowFrame ? BlockStateModelLoader.GLOW_FRAME_LOCATION : BlockStateModelLoader.FRAME_LOCATION;
        }
    }

    public Vec3 getRenderOffset(ItemFrameRenderState renderState) {
        return new Vec3((double)((float)renderState.direction.getStepX() * 0.3F), -0.25, (double)((float)renderState.direction.getStepZ() * 0.3F));
    }

    protected boolean shouldShowName(T entity, double distanceToCameraSq) {
        return Minecraft.renderNames()
            && !entity.getItem().isEmpty()
            && entity.getItem().has(DataComponents.CUSTOM_NAME)
            && this.entityRenderDispatcher.crosshairPickEntity == entity;
    }

    protected Component getNameTag(T entity) {
        return entity.getItem().getHoverName();
    }

    public ItemFrameRenderState createRenderState() {
        return new ItemFrameRenderState();
    }

    public void extractRenderState(T entity, ItemFrameRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.direction = entity.getDirection();
        ItemStack itemstack = entity.getItem();
        reusedState.itemStack = itemstack.copy();
        reusedState.rotation = entity.getRotation();
        reusedState.isGlowFrame = entity.getType() == EntityType.GLOW_ITEM_FRAME;
        reusedState.itemModel = null;
        reusedState.mapId = null;
        if (!reusedState.itemStack.isEmpty()) {
            MapId mapid = entity.getFramedMapId(itemstack);
            if (mapid != null) {
                MapItemSavedData mapitemsaveddata = net.minecraft.world.item.MapItem.getSavedData(itemstack, entity.level());
                if (mapitemsaveddata != null) {
                    this.mapRenderer.extractRenderState(mapid, mapitemsaveddata, reusedState.mapRenderState);
                    reusedState.mapId = mapid;
                }
            } else {
                reusedState.itemModel = this.itemRenderer.getModel(itemstack, entity.level(), null, entity.getId());
            }
        }
    }
}
