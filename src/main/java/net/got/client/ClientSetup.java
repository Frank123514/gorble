package net.got.client;

import net.got.GotMod;
import net.got.client.input.GotKeybinds;
import net.got.client.renderer.NorthBowmanRenderer;
import net.got.client.renderer.NorthmanRenderer;
import net.got.client.renderer.NorthWarriorRenderer;
import net.got.init.GotModBlockEntities;
import net.got.init.GotModEntities;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = "got",
        value = Dist.CLIENT
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(GotKeybinds.OPEN_MAP);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ── NPC renderers ────────────────────────────────────────────────
        event.registerEntityRenderer(GotModEntities.NORTH_BOWMAN.get(),
                ctx -> new NorthBowmanRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTH_WARRIOR.get(),
                ctx -> new NorthWarriorRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTHMAN.get(),
                ctx -> new NorthmanRenderer(ctx));
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ── Sign block entity renderers ──────────────────────────────────
        event.registerBlockEntityRenderer(GotModBlockEntities.WEIRWOOD_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ASPEN_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ALDER_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PINE_SIGN.get(),               SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.FIR_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SENTINAL_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.IRONWOOD_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BEECH_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SOLDIER_PINE_SIGN.get(),       SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ASH_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.HAWTHORN_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACKBARK_SIGN.get(),          SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLOODWOOD_SIGN.get(),          SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLUE_MAHOE_SIGN.get(),         SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.COTTONWOOD_SIGN.get(),         SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACK_COTTONWOOD_SIGN.get(),   SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CINNAMON_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CLOVE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.EBONY_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ELM_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CEDAR_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.APPLE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.GOLDENHEART_SIGN.get(),        SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.LINDEN_SIGN.get(),             SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MAHOGANY_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MAPLE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MYRRH_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.REDWOOD_SIGN.get(),            SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CHESTNUT_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.WILLOW_SIGN.get(),             SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.WORMTREE_SIGN.get(),           SignRenderer::new);

        // ── Hanging sign block entity renderers ──────────────────────────
        event.registerBlockEntityRenderer(GotModBlockEntities.WEIRWOOD_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ASPEN_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ALDER_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PINE_HANGING_SIGN.get(),             HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.FIR_HANGING_SIGN.get(),              HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SENTINAL_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.IRONWOOD_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BEECH_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SOLDIER_PINE_HANGING_SIGN.get(),     HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ASH_HANGING_SIGN.get(),              HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.HAWTHORN_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACKBARK_HANGING_SIGN.get(),        HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLOODWOOD_HANGING_SIGN.get(),        HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLUE_MAHOE_HANGING_SIGN.get(),       HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.COTTONWOOD_HANGING_SIGN.get(),       HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACK_COTTONWOOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CINNAMON_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CLOVE_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.EBONY_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ELM_HANGING_SIGN.get(),              HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CEDAR_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.APPLE_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.GOLDENHEART_HANGING_SIGN.get(),      HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.LINDEN_HANGING_SIGN.get(),           HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MAHOGANY_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MAPLE_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.MYRRH_HANGING_SIGN.get(),            HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.REDWOOD_HANGING_SIGN.get(),          HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CHESTNUT_HANGING_SIGN.get(),         HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.WILLOW_HANGING_SIGN.get(),           HangingSignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.WORMTREE_HANGING_SIGN.get(),         HangingSignRenderer::new);
    }
}