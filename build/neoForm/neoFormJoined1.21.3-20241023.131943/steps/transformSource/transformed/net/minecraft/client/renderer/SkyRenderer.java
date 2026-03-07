package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class SkyRenderer implements AutoCloseable {
    private static final ResourceLocation SUN_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");
    private static final ResourceLocation MOON_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/moon_phases.png");
    private static final ResourceLocation END_SKY_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/end_sky.png");
    private static final float SKY_DISC_RADIUS = 512.0F;
    private final VertexBuffer starBuffer = this.createStarBuffer();
    private final VertexBuffer topSkyBuffer = this.createTopSkyBuffer();
    private final VertexBuffer bottomSkyBuffer = this.createBottomSkyBuffer();

    private VertexBuffer createStarBuffer() {
        VertexBuffer vertexbuffer = new VertexBuffer(BufferUsage.STATIC_WRITE);
        vertexbuffer.bind();
        vertexbuffer.upload(this.drawStars(Tesselator.getInstance()));
        VertexBuffer.unbind();
        return vertexbuffer;
    }

    private VertexBuffer createTopSkyBuffer() {
        VertexBuffer vertexbuffer = new VertexBuffer(BufferUsage.STATIC_WRITE);
        vertexbuffer.bind();
        vertexbuffer.upload(this.buildSkyDisc(Tesselator.getInstance(), 16.0F));
        VertexBuffer.unbind();
        return vertexbuffer;
    }

    private VertexBuffer createBottomSkyBuffer() {
        VertexBuffer vertexbuffer = new VertexBuffer(BufferUsage.STATIC_WRITE);
        vertexbuffer.bind();
        vertexbuffer.upload(this.buildSkyDisc(Tesselator.getInstance(), -16.0F));
        VertexBuffer.unbind();
        return vertexbuffer;
    }

    private MeshData drawStars(Tesselator tesselator) {
        RandomSource randomsource = RandomSource.create(10842L);
        int i = 1500;
        float f = 100.0F;
        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int j = 0; j < 1500; j++) {
            float f1 = randomsource.nextFloat() * 2.0F - 1.0F;
            float f2 = randomsource.nextFloat() * 2.0F - 1.0F;
            float f3 = randomsource.nextFloat() * 2.0F - 1.0F;
            float f4 = 0.15F + randomsource.nextFloat() * 0.1F;
            float f5 = Mth.lengthSquared(f1, f2, f3);
            if (!(f5 <= 0.010000001F) && !(f5 >= 1.0F)) {
                Vector3f vector3f = new Vector3f(f1, f2, f3).normalize(100.0F);
                float f6 = (float)(randomsource.nextDouble() * (float) Math.PI * 2.0);
                Matrix3f matrix3f = new Matrix3f().rotateTowards(new Vector3f(vector3f).negate(), new Vector3f(0.0F, 1.0F, 0.0F)).rotateZ(-f6);
                bufferbuilder.addVertex(new Vector3f(f4, -f4, 0.0F).mul(matrix3f).add(vector3f));
                bufferbuilder.addVertex(new Vector3f(f4, f4, 0.0F).mul(matrix3f).add(vector3f));
                bufferbuilder.addVertex(new Vector3f(-f4, f4, 0.0F).mul(matrix3f).add(vector3f));
                bufferbuilder.addVertex(new Vector3f(-f4, -f4, 0.0F).mul(matrix3f).add(vector3f));
            }
        }

        return bufferbuilder.buildOrThrow();
    }

    private MeshData buildSkyDisc(Tesselator tesselator, float y) {
        float f = Math.signum(y) * 512.0F;
        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        bufferbuilder.addVertex(0.0F, y, 0.0F);

        for (int i = -180; i <= 180; i += 45) {
            bufferbuilder.addVertex(f * Mth.cos((float)i * (float) (Math.PI / 180.0)), y, 512.0F * Mth.sin((float)i * (float) (Math.PI / 180.0)));
        }

        return bufferbuilder.buildOrThrow();
    }

    public void renderSkyDisc(float red, float green, float blue) {
        RenderSystem.depthMask(false);
        RenderSystem.setShader(CoreShaders.POSITION);
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        this.topSkyBuffer.bind();
        this.topSkyBuffer.drawWithShader(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
        VertexBuffer.unbind();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }

    public void renderDarkDisc(PoseStack poseStack) {
        RenderSystem.depthMask(false);
        RenderSystem.setShader(CoreShaders.POSITION);
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
        poseStack.pushPose();
        poseStack.translate(0.0F, 12.0F, 0.0F);
        this.bottomSkyBuffer.bind();
        this.bottomSkyBuffer.drawWithShader(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
        VertexBuffer.unbind();
        poseStack.popPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }

    public void renderSunMoonAndStars(
        PoseStack poseStack, Tesselator tesselator, float timeOfDay, int moonPhase, float rainLevel, float starBrightness, FogParameters fog
    ) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(timeOfDay * 360.0F));
        this.renderSun(rainLevel, tesselator, poseStack);
        this.renderMoon(moonPhase, rainLevel, tesselator, poseStack);
        if (starBrightness > 0.0F) {
            this.renderStars(fog, starBrightness, poseStack);
        }

        poseStack.popPose();
    }

    private void renderSun(float alpha, Tesselator tesselator, PoseStack poseStack) {
        float f = 30.0F;
        float f1 = 100.0F;
        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        Matrix4f matrix4f = poseStack.last().pose();
        RenderSystem.depthMask(false);
        RenderSystem.overlayBlendFunc();
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);
        RenderSystem.enableBlend();
        bufferbuilder.addVertex(matrix4f, -30.0F, 100.0F, -30.0F).setUv(0.0F, 0.0F);
        bufferbuilder.addVertex(matrix4f, 30.0F, 100.0F, -30.0F).setUv(1.0F, 0.0F);
        bufferbuilder.addVertex(matrix4f, 30.0F, 100.0F, 30.0F).setUv(1.0F, 1.0F);
        bufferbuilder.addVertex(matrix4f, -30.0F, 100.0F, 30.0F).setUv(0.0F, 1.0F);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    }

    private void renderMoon(int phase, float alpha, Tesselator tesselator, PoseStack poseStack) {
        float f = 20.0F;
        int i = phase % 4;
        int j = phase / 4 % 2;
        float f1 = (float)(i + 0) / 4.0F;
        float f2 = (float)(j + 0) / 2.0F;
        float f3 = (float)(i + 1) / 4.0F;
        float f4 = (float)(j + 1) / 2.0F;
        float f5 = 100.0F;
        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        RenderSystem.depthMask(false);
        RenderSystem.overlayBlendFunc();
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, MOON_LOCATION);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = poseStack.last().pose();
        bufferbuilder.addVertex(matrix4f, -20.0F, -100.0F, 20.0F).setUv(f3, f4);
        bufferbuilder.addVertex(matrix4f, 20.0F, -100.0F, 20.0F).setUv(f1, f4);
        bufferbuilder.addVertex(matrix4f, 20.0F, -100.0F, -20.0F).setUv(f1, f2);
        bufferbuilder.addVertex(matrix4f, -20.0F, -100.0F, -20.0F).setUv(f3, f2);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    }

    private void renderStars(FogParameters fog, float starBrightness, PoseStack poseStack) {
        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.mul(poseStack.last().pose());
        RenderSystem.depthMask(false);
        RenderSystem.overlayBlendFunc();
        RenderSystem.setShader(CoreShaders.POSITION);
        RenderSystem.setShaderColor(starBrightness, starBrightness, starBrightness, starBrightness);
        RenderSystem.enableBlend();
        RenderSystem.setShaderFog(FogParameters.NO_FOG);
        this.starBuffer.bind();
        this.starBuffer.drawWithShader(matrix4fstack, RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
        VertexBuffer.unbind();
        RenderSystem.setShaderFog(fog);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        matrix4fstack.popMatrix();
    }

    public void renderSunriseAndSunset(PoseStack poseStack, Tesselator tesselator, float sunAngle, int color) {
        RenderSystem.setShader(CoreShaders.POSITION_COLOR);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        float f = Mth.sin(sunAngle) < 0.0F ? 180.0F : 0.0F;
        poseStack.mulPose(Axis.ZP.rotationDegrees(f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        float f1 = ARGB.from8BitChannel(ARGB.alpha(color));
        bufferbuilder.addVertex(matrix4f, 0.0F, 100.0F, 0.0F).setColor(color);
        int i = ARGB.transparent(color);
        int j = 16;

        for (int k = 0; k <= 16; k++) {
            float f2 = (float)k * (float) (Math.PI * 2) / 16.0F;
            float f3 = Mth.sin(f2);
            float f4 = Mth.cos(f2);
            bufferbuilder.addVertex(matrix4f, f3 * 120.0F, f4 * 120.0F, -f4 * 40.0F * f1).setColor(i);
        }

        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        poseStack.popPose();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }

    public void renderEndSky(PoseStack poseStack) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();

        for (int i = 0; i < 6; i++) {
            poseStack.pushPose();
            if (i == 1) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    @Override
    public void close() {
        this.starBuffer.close();
        this.topSkyBuffer.close();
        this.bottomSkyBuffer.close();
    }
}
