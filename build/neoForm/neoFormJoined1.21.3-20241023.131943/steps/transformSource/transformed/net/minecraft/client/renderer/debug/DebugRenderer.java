package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugRenderer {
    public final PathfindingRenderer pathfindingRenderer = new PathfindingRenderer();
    public final DebugRenderer.SimpleDebugRenderer waterDebugRenderer;
    public final DebugRenderer.SimpleDebugRenderer chunkBorderRenderer;
    public final DebugRenderer.SimpleDebugRenderer heightMapRenderer;
    public final DebugRenderer.SimpleDebugRenderer collisionBoxRenderer;
    public final DebugRenderer.SimpleDebugRenderer supportBlockRenderer;
    public final NeighborsUpdateRenderer neighborsUpdateRenderer;
    public final RedstoneWireOrientationsRenderer redstoneWireOrientationsRenderer;
    public final StructureRenderer structureRenderer;
    public final DebugRenderer.SimpleDebugRenderer lightDebugRenderer;
    public final DebugRenderer.SimpleDebugRenderer worldGenAttemptRenderer;
    public final DebugRenderer.SimpleDebugRenderer solidFaceRenderer;
    public final DebugRenderer.SimpleDebugRenderer chunkRenderer;
    public final BrainDebugRenderer brainDebugRenderer;
    public final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
    public final BeeDebugRenderer beeDebugRenderer;
    public final RaidDebugRenderer raidDebugRenderer;
    public final GoalSelectorDebugRenderer goalSelectorRenderer;
    public final GameTestDebugRenderer gameTestDebugRenderer;
    public final GameEventListenerRenderer gameEventListenerRenderer;
    public final LightSectionDebugRenderer skyLightSectionDebugRenderer;
    public final BreezeDebugRenderer breezeDebugRenderer;
    public final ChunkCullingDebugRenderer chunkCullingDebugRenderer;
    public final OctreeDebugRenderer octreeDebugRenderer;
    private boolean renderChunkborder;
    private boolean renderOctree;

    public DebugRenderer(Minecraft minecraft) {
        this.waterDebugRenderer = new WaterDebugRenderer(minecraft);
        this.chunkBorderRenderer = new ChunkBorderRenderer(minecraft);
        this.heightMapRenderer = new HeightMapRenderer(minecraft);
        this.collisionBoxRenderer = new CollisionBoxRenderer(minecraft);
        this.supportBlockRenderer = new SupportBlockRenderer(minecraft);
        this.neighborsUpdateRenderer = new NeighborsUpdateRenderer(minecraft);
        this.redstoneWireOrientationsRenderer = new RedstoneWireOrientationsRenderer(minecraft);
        this.structureRenderer = new StructureRenderer(minecraft);
        this.lightDebugRenderer = new LightDebugRenderer(minecraft);
        this.worldGenAttemptRenderer = new WorldGenAttemptRenderer();
        this.solidFaceRenderer = new SolidFaceRenderer(minecraft);
        this.chunkRenderer = new ChunkDebugRenderer(minecraft);
        this.brainDebugRenderer = new BrainDebugRenderer(minecraft);
        this.villageSectionsDebugRenderer = new VillageSectionsDebugRenderer();
        this.beeDebugRenderer = new BeeDebugRenderer(minecraft);
        this.raidDebugRenderer = new RaidDebugRenderer(minecraft);
        this.goalSelectorRenderer = new GoalSelectorDebugRenderer(minecraft);
        this.gameTestDebugRenderer = new GameTestDebugRenderer();
        this.gameEventListenerRenderer = new GameEventListenerRenderer(minecraft);
        this.skyLightSectionDebugRenderer = new LightSectionDebugRenderer(minecraft, LightLayer.SKY);
        this.breezeDebugRenderer = new BreezeDebugRenderer(minecraft);
        this.chunkCullingDebugRenderer = new ChunkCullingDebugRenderer(minecraft);
        this.octreeDebugRenderer = new OctreeDebugRenderer(minecraft);
    }

    public void clear() {
        this.pathfindingRenderer.clear();
        this.waterDebugRenderer.clear();
        this.chunkBorderRenderer.clear();
        this.heightMapRenderer.clear();
        this.collisionBoxRenderer.clear();
        this.supportBlockRenderer.clear();
        this.neighborsUpdateRenderer.clear();
        this.structureRenderer.clear();
        this.lightDebugRenderer.clear();
        this.worldGenAttemptRenderer.clear();
        this.solidFaceRenderer.clear();
        this.chunkRenderer.clear();
        this.brainDebugRenderer.clear();
        this.villageSectionsDebugRenderer.clear();
        this.beeDebugRenderer.clear();
        this.raidDebugRenderer.clear();
        this.goalSelectorRenderer.clear();
        this.gameTestDebugRenderer.clear();
        this.gameEventListenerRenderer.clear();
        this.skyLightSectionDebugRenderer.clear();
        this.breezeDebugRenderer.clear();
        this.chunkCullingDebugRenderer.clear();
    }

    public boolean switchRenderChunkborder() {
        this.renderChunkborder = !this.renderChunkborder;
        return this.renderChunkborder;
    }

    public boolean toggleRenderOctree() {
        return this.renderOctree = !this.renderOctree;
    }

    public void render(PoseStack poseStack, Frustum frustum, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ) {
        if (this.renderChunkborder && !Minecraft.getInstance().showOnlyReducedInfo()) {
            this.chunkBorderRenderer.render(poseStack, bufferSource, camX, camY, camZ);
        }

        if (this.renderOctree) {
            this.octreeDebugRenderer.render(poseStack, frustum, bufferSource, camX, camY, camZ);
        }

        this.gameTestDebugRenderer.render(poseStack, bufferSource, camX, camY, camZ);
    }

    public void renderAfterTranslucents(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double camX, double camY, double camZ) {
        this.chunkCullingDebugRenderer.render(poseStack, bufferSource, camX, camY, camZ);
    }

    public static Optional<Entity> getTargetedEntity(@Nullable Entity entity, int distance) {
        if (entity == null) {
            return Optional.empty();
        } else {
            Vec3 vec3 = entity.getEyePosition();
            Vec3 vec31 = entity.getViewVector(1.0F).scale((double)distance);
            Vec3 vec32 = vec3.add(vec31);
            AABB aabb = entity.getBoundingBox().expandTowards(vec31).inflate(1.0);
            int i = distance * distance;
            EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(entity, vec3, vec32, aabb, EntitySelector.CAN_BE_PICKED, (double)i);
            if (entityhitresult == null) {
                return Optional.empty();
            } else {
                return vec3.distanceToSqr(entityhitresult.getLocation()) > (double)i ? Optional.empty() : Optional.of(entityhitresult.getEntity());
            }
        }
    }

    public static void renderFilledUnitCube(
        PoseStack poseStack, MultiBufferSource bufferSource, BlockPos pos, float red, float green, float blue, float alpha
    ) {
        renderFilledBox(poseStack, bufferSource, pos, pos.offset(1, 1, 1), red, green, blue, alpha);
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        BlockPos startPos,
        BlockPos endPos,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.isInitialized()) {
            Vec3 vec3 = camera.getPosition().reverse();
            AABB aabb = AABB.encapsulatingFullBlocks(startPos, endPos).move(vec3);
            renderFilledBox(poseStack, bufferSource, aabb, red, green, blue, alpha);
        }
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        BlockPos pos,
        float scale,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.isInitialized()) {
            Vec3 vec3 = camera.getPosition().reverse();
            AABB aabb = new AABB(pos).move(vec3).inflate((double)scale);
            renderFilledBox(poseStack, bufferSource, aabb, red, green, blue, alpha);
        }
    }

    public static void renderFilledBox(
        PoseStack poseStack, MultiBufferSource bufferSource, AABB boundingBox, float red, float green, float blue, float alpha
    ) {
        renderFilledBox(
            poseStack,
            bufferSource,
            boundingBox.minX,
            boundingBox.minY,
            boundingBox.minZ,
            boundingBox.maxX,
            boundingBox.maxY,
            boundingBox.maxZ,
            red,
            green,
            blue,
            alpha
        );
    }

    public static void renderFilledBox(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        double minX,
        double minY,
        double minZ,
        double maxX,
        double maxY,
        double maxZ,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.debugFilledBox());
        ShapeRenderer.addChainedFilledBoxVertices(
            poseStack, vertexconsumer, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha
        );
    }

    public static void renderFloatingText(
        PoseStack poseStack, MultiBufferSource bufferSource, String text, int x, int y, int z, int color
    ) {
        renderFloatingText(poseStack, bufferSource, text, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, color);
    }

    public static void renderFloatingText(
        PoseStack poseStack, MultiBufferSource bufferSource, String text, double x, double y, double z, int color
    ) {
        renderFloatingText(poseStack, bufferSource, text, x, y, z, color, 0.02F);
    }

    public static void renderFloatingText(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        String text,
        double x,
        double y,
        double z,
        int color,
        float scale
    ) {
        renderFloatingText(poseStack, bufferSource, text, x, y, z, color, scale, true, 0.0F, false);
    }

    public static void renderFloatingText(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        String text,
        double x,
        double y,
        double z,
        int color,
        float scale,
        boolean center,
        float xOffset,
        boolean transparent
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.getMainCamera();
        if (camera.isInitialized() && minecraft.getEntityRenderDispatcher().options != null) {
            Font font = minecraft.font;
            double d0 = camera.getPosition().x;
            double d1 = camera.getPosition().y;
            double d2 = camera.getPosition().z;
            poseStack.pushPose();
            poseStack.translate((float)(x - d0), (float)(y - d1) + 0.07F, (float)(z - d2));
            poseStack.mulPose(camera.rotation());
            poseStack.scale(scale, -scale, scale);
            float f = center ? (float)(-font.width(text)) / 2.0F : 0.0F;
            f -= xOffset / scale;
            font.drawInBatch(
                text,
                f,
                0.0F,
                color,
                false,
                poseStack.last().pose(),
                bufferSource,
                transparent ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL,
                0,
                15728880
            );
            poseStack.popPose();
        }
    }

    private static Vec3 mixColor(float shift) {
        float f = 5.99999F;
        int i = (int)(Mth.clamp(shift, 0.0F, 1.0F) * 5.99999F);
        float f1 = shift * 5.99999F - (float)i;

        return switch (i) {
            case 0 -> new Vec3(1.0, (double)f1, 0.0);
            case 1 -> new Vec3((double)(1.0F - f1), 1.0, 0.0);
            case 2 -> new Vec3(0.0, 1.0, (double)f1);
            case 3 -> new Vec3(0.0, 1.0 - (double)f1, 1.0);
            case 4 -> new Vec3((double)f1, 0.0, 1.0);
            case 5 -> new Vec3(1.0, 0.0, 1.0 - (double)f1);
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    private static Vec3 shiftHue(float red, float green, float blue, float shift) {
        Vec3 vec3 = mixColor(shift).scale((double)red);
        Vec3 vec31 = mixColor((shift + 0.33333334F) % 1.0F).scale((double)green);
        Vec3 vec32 = mixColor((shift + 0.6666667F) % 1.0F).scale((double)blue);
        Vec3 vec33 = vec3.add(vec31).add(vec32);
        double d0 = Math.max(Math.max(1.0, vec33.x), Math.max(vec33.y, vec33.z));
        return new Vec3(vec33.x / d0, vec33.y / d0, vec33.z / d0);
    }

    public static void renderVoxelShape(
        PoseStack poseStack,
        VertexConsumer buffer,
        VoxelShape shape,
        double x,
        double y,
        double z,
        float red,
        float green,
        float blue,
        float alpha,
        boolean lowerColorVariance
    ) {
        List<AABB> list = shape.toAabbs();
        if (!list.isEmpty()) {
            int i = lowerColorVariance ? list.size() : list.size() * 8;
            ShapeRenderer.renderShape(
                poseStack,
                buffer,
                Shapes.create(list.get(0)),
                x,
                y,
                z,
                ARGB.colorFromFloat(alpha, red, green, blue)
            );

            for (int j = 1; j < list.size(); j++) {
                AABB aabb = list.get(j);
                float f = (float)j / (float)i;
                Vec3 vec3 = shiftHue(red, green, blue, f);
                ShapeRenderer.renderShape(
                    poseStack,
                    buffer,
                    Shapes.create(aabb),
                    x,
                    y,
                    z,
                    ARGB.colorFromFloat(alpha, (float)vec3.x, (float)vec3.y, (float)vec3.z)
                );
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface SimpleDebugRenderer {
        void render(PoseStack poseStack, MultiBufferSource bufferSource, double camX, double camY, double camZ);

        default void clear() {
        }
    }
}
