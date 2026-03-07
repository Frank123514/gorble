package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public abstract class EntityRenderer<T extends Entity, S extends EntityRenderState> {
    protected static final float NAMETAG_SCALE = 0.025F;
    public static final int LEASH_RENDER_STEPS = 24;
    protected final EntityRenderDispatcher entityRenderDispatcher;
    private final Font font;
    protected float shadowRadius;
    protected float shadowStrength = 1.0F;
    private final S reusedState = this.createRenderState();

    protected EntityRenderer(EntityRendererProvider.Context context) {
        this.entityRenderDispatcher = context.getEntityRenderDispatcher();
        this.font = context.getFont();
    }

    public final int getPackedLightCoords(T entity, float partialTicks) {
        BlockPos blockpos = BlockPos.containing(entity.getLightProbePosition(partialTicks));
        return LightTexture.pack(this.getBlockLightLevel(entity, blockpos), this.getSkyLightLevel(entity, blockpos));
    }

    protected int getSkyLightLevel(T entity, BlockPos pos) {
        return entity.level().getBrightness(LightLayer.SKY, pos);
    }

    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return entity.isOnFire() ? 15 : entity.level().getBrightness(LightLayer.BLOCK, pos);
    }

    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        if (!livingEntity.shouldRender(camX, camY, camZ)) {
            return false;
        } else if (!this.affectedByCulling(livingEntity)) {
            return true;
        } else {
            AABB aabb = this.getBoundingBoxForCulling(livingEntity).inflate(0.5);
            if (aabb.hasNaN() || aabb.getSize() == 0.0) {
                aabb = new AABB(
                    livingEntity.getX() - 2.0,
                    livingEntity.getY() - 2.0,
                    livingEntity.getZ() - 2.0,
                    livingEntity.getX() + 2.0,
                    livingEntity.getY() + 2.0,
                    livingEntity.getZ() + 2.0
                );
            }

            if (camera.isVisible(aabb)) {
                return true;
            } else {
                if (livingEntity instanceof Leashable leashable) {
                    Entity entity = leashable.getLeashHolder();
                    if (entity != null) {
                        return camera.isVisible(this.entityRenderDispatcher.getRenderer(entity).getBoundingBoxForCulling(entity));
                    }
                }

                return false;
            }
        }
    }

    protected AABB getBoundingBoxForCulling(T minecraft) {
        return minecraft.getBoundingBox();
    }

    protected boolean affectedByCulling(T display) {
        return true;
    }

    public Vec3 getRenderOffset(S renderState) {
        return renderState.passengerOffset != null ? renderState.passengerOffset : Vec3.ZERO;
    }

    public void render(S renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        EntityRenderState.LeashState entityrenderstate$leashstate = renderState.leashState;
        if (entityrenderstate$leashstate != null) {
            renderLeash(poseStack, bufferSource, entityrenderstate$leashstate);
        }

        if (renderState.nameTag != null) {
            var event = new net.neoforged.neoforge.client.event.RenderNameTagEvent.DoRender(renderState, renderState.nameTag, this, poseStack, bufferSource, packedLight, renderState.partialTick);
            if (!net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event).isCanceled())
            this.renderNameTag(renderState, renderState.nameTag, poseStack, bufferSource, packedLight);
        }
    }

    private static void renderLeash(PoseStack poseStack, MultiBufferSource buffer, EntityRenderState.LeashState leashState) {
        float f = 0.025F;
        float f1 = (float)(leashState.end.x - leashState.start.x);
        float f2 = (float)(leashState.end.y - leashState.start.y);
        float f3 = (float)(leashState.end.z - leashState.start.z);
        float f4 = Mth.invSqrt(f1 * f1 + f3 * f3) * 0.025F / 2.0F;
        float f5 = f3 * f4;
        float f6 = f1 * f4;
        poseStack.pushPose();
        poseStack.translate(leashState.offset);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.leash());
        Matrix4f matrix4f = poseStack.last().pose();

        for (int i = 0; i <= 24; i++) {
            addVertexPair(
                vertexconsumer,
                matrix4f,
                f1,
                f2,
                f3,
                leashState.startBlockLight,
                leashState.endBlockLight,
                leashState.startSkyLight,
                leashState.endSkyLight,
                0.025F,
                0.025F,
                f5,
                f6,
                i,
                false
            );
        }

        for (int j = 24; j >= 0; j--) {
            addVertexPair(
                vertexconsumer,
                matrix4f,
                f1,
                f2,
                f3,
                leashState.startBlockLight,
                leashState.endBlockLight,
                leashState.startSkyLight,
                leashState.endSkyLight,
                0.025F,
                0.0F,
                f5,
                f6,
                j,
                true
            );
        }

        poseStack.popPose();
    }

    private static void addVertexPair(
        VertexConsumer buffer,
        Matrix4f pose,
        float startX,
        float startY,
        float startZ,
        int entityBlockLight,
        int holderBlockLight,
        int entitySkyLight,
        int holderSkyLight,
        float yOffset,
        float dy,
        float dx,
        float dz,
        int index,
        boolean reverse
    ) {
        float f = (float)index / 24.0F;
        int i = (int)Mth.lerp(f, (float)entityBlockLight, (float)holderBlockLight);
        int j = (int)Mth.lerp(f, (float)entitySkyLight, (float)holderSkyLight);
        int k = LightTexture.pack(i, j);
        float f1 = index % 2 == (reverse ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = startX * f;
        float f6 = startY > 0.0F ? startY * f * f : startY - startY * (1.0F - f) * (1.0F - f);
        float f7 = startZ * f;
        buffer.addVertex(pose, f5 - dx, f6 + dy, f7 + dz).setColor(f2, f3, f4, 1.0F).setLight(k);
        buffer.addVertex(pose, f5 + dx, f6 + yOffset - dy, f7 - dz).setColor(f2, f3, f4, 1.0F).setLight(k);
    }

    protected boolean shouldShowName(T entity, double distanceToCameraSq) {
        return entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity;
    }

    public Font getFont() {
        return this.font;
    }

    protected void renderNameTag(S renderState, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Vec3 vec3 = renderState.nameTagAttachment;
        if (vec3 != null) {
            boolean flag = !renderState.isDiscrete;
            int i = "deadmau5".equals(displayName.getString()) ? -10 : 0;
            poseStack.pushPose();
            poseStack.translate(vec3.x, vec3.y + 0.5, vec3.z);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.scale(0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = poseStack.last().pose();
            Font font = this.getFont();
            float f = (float)(-font.width(displayName)) / 2.0F;
            int j = (int)(Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
            font.drawInBatch(
                displayName, f, (float)i, -2130706433, false, matrix4f, bufferSource, flag ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, j, packedLight
            );
            if (flag) {
                font.drawInBatch(
                    displayName, f, (float)i, -1, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, LightTexture.lightCoordsWithEmission(packedLight, 2)
                );
            }

            poseStack.popPose();
        }
    }

    @Nullable
    protected Component getNameTag(T entity) {
        return entity.getDisplayName();
    }

    protected float getShadowRadius(S renderState) {
        return this.shadowRadius;
    }

    public abstract S createRenderState();

    public final S createRenderState(T entity, float partialTick) {
        S s = this.reusedState;
        this.extractRenderState(entity, s, partialTick);
        net.neoforged.neoforge.client.renderstate.RenderStateExtensions.onUpdateEntityRenderState(this, entity, s);
        return s;
    }

    public void extractRenderState(T p_entity, S reusedState, float partialTick) {
        reusedState.x = Mth.lerp((double)partialTick, p_entity.xOld, p_entity.getX());
        reusedState.y = Mth.lerp((double)partialTick, p_entity.yOld, p_entity.getY());
        reusedState.z = Mth.lerp((double)partialTick, p_entity.zOld, p_entity.getZ());
        reusedState.isInvisible = p_entity.isInvisible();
        reusedState.ageInTicks = (float)p_entity.tickCount + partialTick;
        reusedState.boundingBoxWidth = p_entity.getBbWidth();
        reusedState.boundingBoxHeight = p_entity.getBbHeight();
        reusedState.eyeHeight = p_entity.getEyeHeight();
        if (p_entity.isPassenger()
            && p_entity.getVehicle() instanceof AbstractMinecart abstractminecart
            && abstractminecart.getBehavior() instanceof NewMinecartBehavior newminecartbehavior
            && newminecartbehavior.cartHasPosRotLerp()) {
            double d2 = Mth.lerp((double)partialTick, abstractminecart.xOld, abstractminecart.getX());
            double d0 = Mth.lerp((double)partialTick, abstractminecart.yOld, abstractminecart.getY());
            double d1 = Mth.lerp((double)partialTick, abstractminecart.zOld, abstractminecart.getZ());
            reusedState.passengerOffset = newminecartbehavior.getCartLerpPosition(partialTick).subtract(new Vec3(d2, d0, d1));
        } else {
            reusedState.passengerOffset = null;
        }

        reusedState.distanceToCameraSq = this.entityRenderDispatcher.distanceToSqr(p_entity);
        var event = new net.neoforged.neoforge.client.event.RenderNameTagEvent.CanRender(p_entity, reusedState, this.getNameTag(p_entity), this, partialTick);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);
        boolean flag = event.canRender().isTrue() || (event.canRender().isDefault() && reusedState.distanceToCameraSq < 4096.0 && this.shouldShowName(p_entity, reusedState.distanceToCameraSq));
        if (flag) {
            reusedState.nameTag = event.getContent();
            reusedState.nameTagAttachment = p_entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, p_entity.getYRot(partialTick));
        } else {
            reusedState.nameTag = null;
        }

        reusedState.isDiscrete = p_entity.isDiscrete();
        Entity entity = p_entity instanceof Leashable leashable ? leashable.getLeashHolder() : null;
        if (entity != null) {
            float f = p_entity.getPreciseBodyRotation(partialTick) * (float) (Math.PI / 180.0);
            Vec3 vec3 = p_entity.getLeashOffset(partialTick).yRot(-f);
            BlockPos blockpos1 = BlockPos.containing(p_entity.getEyePosition(partialTick));
            BlockPos blockpos = BlockPos.containing(entity.getEyePosition(partialTick));
            if (reusedState.leashState == null) {
                reusedState.leashState = new EntityRenderState.LeashState();
            }

            EntityRenderState.LeashState entityrenderstate$leashstate = reusedState.leashState;
            entityrenderstate$leashstate.offset = vec3;
            entityrenderstate$leashstate.start = p_entity.getPosition(partialTick).add(vec3);
            entityrenderstate$leashstate.end = entity.getRopeHoldPosition(partialTick);
            entityrenderstate$leashstate.startBlockLight = this.getBlockLightLevel(p_entity, blockpos1);
            entityrenderstate$leashstate.endBlockLight = this.entityRenderDispatcher.getRenderer(entity).getBlockLightLevel(entity, blockpos);
            entityrenderstate$leashstate.startSkyLight = p_entity.level().getBrightness(LightLayer.SKY, blockpos1);
            entityrenderstate$leashstate.endSkyLight = p_entity.level().getBrightness(LightLayer.SKY, blockpos);
        } else {
            reusedState.leashState = null;
        }

        reusedState.displayFireAnimation = p_entity.displayFireAnimation();

        reusedState.partialTick = partialTick;
    }
}
