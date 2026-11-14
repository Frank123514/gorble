package net.got.client.armor;

import com.google.common.reflect.TypeToken;
import net.got.GotMod;
import net.got.coif.CoifSlot;
import net.got.coif.CoifSlotData;
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

/** Client wiring for the coif slot: render-state data, the extra armor layer on players, the slot frame. */
public final class CoifClientEvents {

    /** Mod-bus events (renderers / render state). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class ModBus {

        @SubscribeEvent
        public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
            event.registerEntityModifier(
                    new TypeToken<LivingEntityRenderer<? extends LivingEntity, LivingEntityRenderState, ?>>() {},
                    (entity, renderState) -> {
                        ItemStack worn = CoifSlotData.get(entity);
                        if (!worn.isEmpty()) renderState.setRenderData(CoifLayer.WORN_KEY, worn.copy());
                    });
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            for (var skin : event.getSkins()) {
                AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
                if (renderer == null) continue;
                renderer.addLayer(new CoifLayer<>(renderer,
                        new GotHelmBodyModel(event.getEntityModels().bakeLayer(MailCoifModel.LAYER_LOCATION))));
            }
        }
    }

    /** Game-bus events (GUI). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class GameBus {

        /** The inventory textures have no slot frame at the coif slot's position, so draw one. */
        @SubscribeEvent
        public static void drawSlotFrame(ScreenEvent.Render.Background event) {
            if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) return;
            for (Slot slot : screen.getMenu().slots) {
                boolean isCoif = slot instanceof CoifSlot
                        || (slot instanceof CreativeSlotWrapperAccessor w && w.got$getTarget() instanceof CoifSlot);
                if (!isCoif) continue;
                event.getGuiGraphics().blitSprite(RenderPipelines.GUI_TEXTURED, CoifSlot.FRAME_SPRITE,
                        screen.getGuiLeft() + slot.x - 1, screen.getGuiTop() + slot.y - 1, 18, 18);
            }
        }
    }

    private CoifClientEvents() {}
}
