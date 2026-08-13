package net.got.client.gui.widget;

import net.got.client.gui.MainMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class IdlePreviewWidget extends AbstractWidget {

    private static final float DRAG_SENSITIVITY = 0.02f;
    private static final float MAX_PITCH = 1.2f;

    private float previewYaw   = 0f;
    private float previewPitch = 0f;
    private boolean dragging = false;

    public IdlePreviewWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        gfx.fill(getX(), getY(), getX() + width, getY() + height, 0x50000000);

        Minecraft mc = Minecraft.getInstance();
        AbstractClientPlayer player = mc.player;
        if (player != null) {
            int scale = Mth.clamp(Math.min(width, height) / 3, 20, 60);
            InventoryScreen.renderEntityInInventoryFollowsAngle(
                    gfx,
                    getX(), getY(), getX() + width, getY() + height,
                    scale,
                    0.0f,
                    previewYaw, previewPitch,
                    (LivingEntity) player
            );
        }

        var font = mc.font;
        String hint = "Click and drag to look around";
        gfx.drawString(font, hint,
                getX() + (width - font.width(hint)) / 2,
                getY() + height - font.lineHeight - 6,
                0xFFAA9050, false);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent __event, boolean __doubleClick){
        if (!isMouseOver(__event.x(), __event.y())) return false;
        if (__event.button() == 0) { dragging = true; return true; }
        return false;
    }

    @Override
    public boolean mouseDragged(net.minecraft.client.input.MouseButtonEvent __event, double dx, double dy){
        if (!dragging) return false;
        previewYaw   += (float) dx * DRAG_SENSITIVITY;
        previewPitch  = Mth.clamp(previewPitch - (float) dy * DRAG_SENSITIVITY, -MAX_PITCH, MAX_PITCH);
        return true;
    }

    @Override
    public boolean mouseReleased(net.minecraft.client.input.MouseButtonEvent __event){
        dragging = false;
        return false;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}
}
