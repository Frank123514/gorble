package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class InventoryScreen extends AbstractRecipeBookScreen<InventoryMenu> {
    /**
     * The old x position of the mouse pointer
     */
    private float xMouse;
    /**
     * The old y position of the mouse pointer
     */
    private float yMouse;
    private boolean buttonClicked;
    private final EffectsInInventory effects;

    public InventoryScreen(Player player) {
        super(
            player.inventoryMenu,
            new CraftingRecipeBookComponent(player.inventoryMenu),
            player.getInventory(),
            Component.translatable("container.crafting")
        );
        this.titleLabelX = 97;
        this.effects = new EffectsInInventory(this);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.minecraft
                .setScreen(
                    new CreativeModeInventoryScreen(
                        this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()
                    )
                );
        }
    }

    @Override
    protected void init() {
        if (this.minecraft.gameMode.hasInfiniteItems()) {
            this.minecraft
                .setScreen(
                    new CreativeModeInventoryScreen(
                        this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), this.minecraft.options.operatorItemsTab().get()
                    )
                );
        } else {
            super.init();
        }
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 104, this.height / 2 - 22);
    }

    @Override
    protected void onRecipeBookButtonClick() {
        this.buttonClicked = true;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    /**
     * Renders the graphical user interface (GUI) element.
     *
     * @param guiGraphics the GuiGraphics object used for rendering.
     * @param mouseX      the x-coordinate of the mouse cursor.
     * @param mouseY      the y-coordinate of the mouse cursor.
     * @param partialTick the partial tick time.
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.effects.render(guiGraphics, mouseX, mouseY, partialTick);
        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
    }

    @Override
    public boolean showsActiveEffects() {
        return this.effects.canSeeEffects();
    }

    @Override
    protected boolean isBiggerResultSlot() {
        return false;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderType::guiTextured, INVENTORY_LOCATION, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        renderEntityInInventoryFollowsMouse(guiGraphics, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.xMouse, this.yMouse, this.minecraft.player);
    }

    public static void renderEntityInInventoryFollowsMouse(
        GuiGraphics guiGraphics,
        int x1,
        int y1,
        int x2,
        int y2,
        int scale,
        float yOffset,
        float mouseX,
        float mouseY,
        LivingEntity entity
    ) {
        float f = (float)(x1 + x2) / 2.0F;
        float f1 = (float)(y1 + y2) / 2.0F;
        float f2 = (float)Math.atan((double)((f - mouseX) / 40.0F));
        float f3 = (float)Math.atan((double)((f1 - mouseY) / 40.0F));
        // Forge: Allow passing in direct angle components instead of mouse position
        renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, f2, f3, entity);
    }

    public static void renderEntityInInventoryFollowsAngle(
        GuiGraphics p_282802_,
        int p_275688_,
        int p_275245_,
        int p_275535_,
        int p_294406_,
        int p_294663_,
        float p_275604_,
        float angleXComponent,
        float angleYComponent,
        LivingEntity p_275689_
    ) {
        float f = (float)(p_275688_ + p_275535_) / 2.0F;
        float f1 = (float)(p_275245_ + p_294406_) / 2.0F;
        p_282802_.enableScissor(p_275688_, p_275245_, p_275535_, p_294406_);
        float f2 = angleXComponent;
        float f3 = angleYComponent;
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf1 = new Quaternionf().rotateX(f3 * 20.0F * (float) (Math.PI / 180.0));
        quaternionf.mul(quaternionf1);
        float f4 = p_275689_.yBodyRot;
        float f5 = p_275689_.getYRot();
        float f6 = p_275689_.getXRot();
        float f7 = p_275689_.yHeadRotO;
        float f8 = p_275689_.yHeadRot;
        p_275689_.yBodyRot = 180.0F + f2 * 20.0F;
        p_275689_.setYRot(180.0F + f2 * 40.0F);
        p_275689_.setXRot(-f3 * 20.0F);
        p_275689_.yHeadRot = p_275689_.getYRot();
        p_275689_.yHeadRotO = p_275689_.getYRot();
        float f9 = p_275689_.getScale();
        Vector3f vector3f = new Vector3f(0.0F, p_275689_.getBbHeight() / 2.0F + p_275604_ * f9, 0.0F);
        float f10 = (float)p_294663_ / f9;
        renderEntityInInventory(p_282802_, f, f1, f10, vector3f, quaternionf, quaternionf1, p_275689_);
        p_275689_.yBodyRot = f4;
        p_275689_.setYRot(f5);
        p_275689_.setXRot(f6);
        p_275689_.yHeadRotO = f7;
        p_275689_.yHeadRot = f8;
        p_282802_.disableScissor();
    }

    public static void renderEntityInInventory(
        GuiGraphics guiGraphics,
        float x,
        float y,
        float scale,
        Vector3f translate,
        Quaternionf pose,
        @Nullable Quaternionf cameraOrientation,
        LivingEntity entity
    ) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)x, (double)y, 50.0);
        guiGraphics.pose().scale(scale, scale, -scale);
        guiGraphics.pose().translate(translate.x, translate.y, translate.z);
        guiGraphics.pose().mulPose(pose);
        guiGraphics.flush();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (cameraOrientation != null) {
            entityrenderdispatcher.overrideCameraOrientation(cameraOrientation.conjugate(new Quaternionf()).rotateY((float) Math.PI));
        }

        entityrenderdispatcher.setRenderShadow(false);
        guiGraphics.drawSpecial(p_370280_ -> entityrenderdispatcher.render(entity, 0.0, 0.0, 0.0, 1.0F, guiGraphics.pose(), p_370280_, 15728880));
        guiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    /**
     * Called when a mouse button is released within the GUI element.
     * <p>
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     *
     * @param mouseX the X coordinate of the mouse.
     * @param mouseY the Y coordinate of the mouse.
     * @param button the button that was released.
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseX, mouseY, button);
        }
    }
}
