package net.got.client.armor;

import com.google.common.reflect.TypeToken;
import java.util.EnumMap;
import java.util.Map;
import net.got.GotMod;
import net.got.mixin.CreativeSlotWrapperAccessor;
import net.got.plate.PlatePiece;
import net.got.plate.PlateSlot;
import net.got.plate.PlateSlotData;
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

/** Client wiring for the plate slots: layer definitions, render-state data, the extra armor layer on players, slot frames. */
public final class PlateClientEvents {

    /** Mod-bus events (model layers / renderers / render state). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class ModBus {

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (PlatePiece piece : PlatePiece.values()) {
                event.registerLayerDefinition(PlateModel.LAYERS.get(piece), () -> PlateModel.createLayer(piece));
            }
        }

        @SubscribeEvent
        public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
            event.registerEntityModifier(
                    new TypeToken<LivingEntityRenderer<? extends LivingEntity, LivingEntityRenderState, ?>>() {},
                    (entity, renderState) -> {
                        Map<PlatePiece, ItemStack> worn = null;
                        for (PlatePiece piece : PlatePiece.values()) {
                            ItemStack stack = PlateSlotData.get(entity, piece);
                            if (stack.isEmpty()) continue;
                            if (worn == null) worn = new EnumMap<>(PlatePiece.class);
                            worn.put(piece, stack.copy());
                        }
                        if (worn != null) renderState.setRenderData(PlateLayer.WORN_KEY, worn);
                    });
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            for (var skin : event.getSkins()) {
                AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
                if (renderer == null) continue;
                renderer.addLayer(PlateLayer.create(renderer, event.getEntityModels()::bakeLayer));
            }
        }
    }

    /** Game-bus events (GUI). */
    @EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
    public static final class GameBus {

        /** The inventory textures have no slot frame at the plate slots' positions, so draw one. */
        @SubscribeEvent
        public static void drawSlotFrames(ScreenEvent.Render.Background event) {
            if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) return;
            for (Slot slot : screen.getMenu().slots) {
                boolean isPlate = slot instanceof PlateSlot
                        || (slot instanceof CreativeSlotWrapperAccessor w && w.got$getTarget() instanceof PlateSlot);
                if (!isPlate) continue;
                event.getGuiGraphics().blitSprite(RenderPipelines.GUI_TEXTURED, PlateSlot.FRAME_SPRITE,
                        screen.getGuiLeft() + slot.x - 1, screen.getGuiTop() + slot.y - 1, 18, 18);
            }
        }
    }

    private PlateClientEvents() {}
}
