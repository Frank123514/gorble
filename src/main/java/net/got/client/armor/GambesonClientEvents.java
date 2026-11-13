package net.got.client.armor;

import com.google.common.reflect.TypeToken;
import net.got.GotMod;
import net.got.gambeson.GambesonSlot;
import net.got.gambeson.GambesonSlotData;
import net.got.mixin.CreativeSlotWrapperAccessor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

/** Client wiring for the gambeson slot: render-state data, the extra armor layer on players, the slot frame. */
public final class GambesonClientEvents {

    /** Mod-bus events (renderers / render state). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class ModBus {

        @SubscribeEvent
        public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
            event.registerEntityModifier(
                    new TypeToken<LivingEntityRenderer<? extends LivingEntity, LivingEntityRenderState, ?>>() {},
                    (entity, renderState) -> {
                        ItemStack worn = GambesonSlotData.get(entity);
                        if (!worn.isEmpty()) renderState.setRenderData(GambesonLayer.WORN_KEY, worn.copy());
                    });
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            for (var skin : event.getSkins()) {
                AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
                if (renderer == null) continue;
                renderer.addLayer(new GambesonLayer<>(renderer,
                        new GotBodyArmorModel(event.getEntityModels().bakeLayer(GambesonModel.LAYER_LOCATION), true)));
            }
        }
    }

    /** Game-bus events (GUI). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class GameBus {

        /** The inventory textures have no slot frame at the gambeson slot's position, so draw one. */
        @SubscribeEvent
        public static void drawSlotFrame(ScreenEvent.Render.Background event) {
            if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) return;
            for (Slot slot : screen.getMenu().slots) {
                boolean isGambeson = slot instanceof GambesonSlot
                        || (slot instanceof CreativeSlotWrapperAccessor w && w.got$getTarget() instanceof GambesonSlot);
                if (!isGambeson) continue;
                event.getGuiGraphics().blitSprite(RenderPipelines.GUI_TEXTURED, GambesonSlot.FRAME_SPRITE,
                        screen.getGuiLeft() + slot.x - 1, screen.getGuiTop() + slot.y - 1, 18, 18);
            }
        }
    }

    private GambesonClientEvents() {}
}
