package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PanoramaRenderer {
    public static final ResourceLocation PANORAMA_OVERLAY = ResourceLocation.withDefaultNamespace("textures/gui/title/background/panorama_overlay.png");
    private final Minecraft minecraft;
    private final CubeMap cubeMap;
    private float spin;

    public PanoramaRenderer(CubeMap cubeMap) {
        this.cubeMap = cubeMap;
        this.minecraft = Minecraft.getInstance();
    }

    public void render(GuiGraphics guiGraphics, int width, int height, float fade, float partialTick) {
        float f = this.minecraft.getDeltaTracker().getRealtimeDeltaTicks();
        float f1 = (float)((double)f * this.minecraft.options.panoramaSpeed().get());
        this.spin = wrap(this.spin + f1 * 0.1F, 360.0F);
        guiGraphics.flush();
        this.cubeMap.render(this.minecraft, 10.0F, -this.spin, fade);
        guiGraphics.flush();
        guiGraphics.blit(RenderType::guiTextured, PANORAMA_OVERLAY, 0, 0, 0.0F, 0.0F, width, height, 16, 128, 16, 128, ARGB.white(fade));
    }

    private static float wrap(float value, float max) {
        return value > max ? value - max : value;
    }
}
