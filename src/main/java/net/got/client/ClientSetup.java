package net.got.client;

import net.got.client.input.GotKeybinds;
import net.got.client.renderer.GotBoatRenderer;
import net.got.client.renderer.NorthBowmanRenderer;
import net.got.client.renderer.NorthmanRenderer;
import net.got.client.renderer.NorthWarriorRenderer;
import net.got.init.GotModBlockEntities;
import net.got.init.GotModBlocks;
import net.got.init.GotModBoatEntities;
import net.got.init.GotModEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = "got",
        bus = Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(GotKeybinds.OPEN_MAP);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // ── Doors ────────────────────────────────────────────────────
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WEIRWOOD_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ASPEN_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ALDER_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PINE_DOOR.get(),               RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.FIR_DOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SENTINAL_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.IRONWOOD_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BEECH_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SOLDIER_PINE_DOOR.get(),       RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ASH_DOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.HAWTHORN_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACKBARK_DOOR.get(),          RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLOODWOOD_DOOR.get(),          RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLUE_MAHOE_DOOR.get(),         RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.COTTONWOOD_DOOR.get(),         RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACK_COTTONWOOD_DOOR.get(),   RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CINNAMON_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CLOVE_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.EBONY_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ELM_DOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CEDAR_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.APPLE_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.GOLDENHEART_DOOR.get(),        RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LINDEN_DOOR.get(),             RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MAHOGANY_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MAPLE_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MYRRH_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.REDWOOD_DOOR.get(),            RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CHESTNUT_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WILLOW_DOOR.get(),             RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WORMTREE_DOOR.get(),           RenderType.cutout());
            // ── Trapdoors ────────────────────────────────────────────────
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WEIRWOOD_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ASPEN_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ALDER_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PINE_TRAPDOOR.get(),               RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.FIR_TRAPDOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SENTINAL_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.IRONWOOD_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BEECH_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SOLDIER_PINE_TRAPDOOR.get(),       RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ASH_TRAPDOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.HAWTHORN_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACKBARK_TRAPDOOR.get(),          RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLOODWOOD_TRAPDOOR.get(),          RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLUE_MAHOE_TRAPDOOR.get(),         RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.COTTONWOOD_TRAPDOOR.get(),         RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACK_COTTONWOOD_TRAPDOOR.get(),   RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CINNAMON_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CLOVE_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.EBONY_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ELM_TRAPDOOR.get(),                RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CEDAR_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.APPLE_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.GOLDENHEART_TRAPDOOR.get(),        RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LINDEN_TRAPDOOR.get(),             RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MAHOGANY_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MAPLE_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.MYRRH_TRAPDOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.REDWOOD_TRAPDOOR.get(),            RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CHESTNUT_TRAPDOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WILLOW_TRAPDOOR.get(),             RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WORMTREE_TRAPDOOR.get(),           RenderType.cutout());
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ── NPC renderers ────────────────────────────────────────────────
        event.registerEntityRenderer(GotModEntities.NORTH_BOWMAN.get(),  ctx -> new NorthBowmanRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTH_WARRIOR.get(), ctx -> new NorthWarriorRenderer(ctx));
        event.registerEntityRenderer(GotModEntities.NORTHMAN.get(),      ctx -> new NorthmanRenderer(ctx));

        // ── Boat renderers ───────────────────────────────────────────────
        event.registerEntityRenderer(GotModBoatEntities.WEIRWOOD_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "weirwood"));
        event.registerEntityRenderer(GotModBoatEntities.WEIRWOOD_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "weirwood"));
        event.registerEntityRenderer(GotModBoatEntities.ASPEN_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "aspen"));
        event.registerEntityRenderer(GotModBoatEntities.ASPEN_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "aspen"));
        event.registerEntityRenderer(GotModBoatEntities.ALDER_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "alder"));
        event.registerEntityRenderer(GotModBoatEntities.ALDER_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "alder"));
        event.registerEntityRenderer(GotModBoatEntities.PINE_BOAT.get(),                  ctx -> new GotBoatRenderer(ctx, false, "pine"));
        event.registerEntityRenderer(GotModBoatEntities.PINE_CHEST_BOAT.get(),            ctx -> new GotBoatRenderer(ctx, true,  "pine"));
        event.registerEntityRenderer(GotModBoatEntities.FIR_BOAT.get(),                   ctx -> new GotBoatRenderer(ctx, false, "fir"));
        event.registerEntityRenderer(GotModBoatEntities.FIR_CHEST_BOAT.get(),             ctx -> new GotBoatRenderer(ctx, true,  "fir"));
        event.registerEntityRenderer(GotModBoatEntities.SENTINAL_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "sentinal"));
        event.registerEntityRenderer(GotModBoatEntities.SENTINAL_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "sentinal"));
        event.registerEntityRenderer(GotModBoatEntities.IRONWOOD_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "ironwood"));
        event.registerEntityRenderer(GotModBoatEntities.IRONWOOD_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "ironwood"));
        event.registerEntityRenderer(GotModBoatEntities.BEECH_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "beech"));
        event.registerEntityRenderer(GotModBoatEntities.BEECH_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "beech"));
        event.registerEntityRenderer(GotModBoatEntities.SOLDIER_PINE_BOAT.get(),          ctx -> new GotBoatRenderer(ctx, false, "soldier_pine"));
        event.registerEntityRenderer(GotModBoatEntities.SOLDIER_PINE_CHEST_BOAT.get(),    ctx -> new GotBoatRenderer(ctx, true,  "soldier_pine"));
        event.registerEntityRenderer(GotModBoatEntities.ASH_BOAT.get(),                   ctx -> new GotBoatRenderer(ctx, false, "ash"));
        event.registerEntityRenderer(GotModBoatEntities.ASH_CHEST_BOAT.get(),             ctx -> new GotBoatRenderer(ctx, true,  "ash"));
        event.registerEntityRenderer(GotModBoatEntities.HAWTHORN_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "hawthorn"));
        event.registerEntityRenderer(GotModBoatEntities.HAWTHORN_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "hawthorn"));
        event.registerEntityRenderer(GotModBoatEntities.BLACKBARK_BOAT.get(),             ctx -> new GotBoatRenderer(ctx, false, "blackbark"));
        event.registerEntityRenderer(GotModBoatEntities.BLACKBARK_CHEST_BOAT.get(),       ctx -> new GotBoatRenderer(ctx, true,  "blackbark"));
        event.registerEntityRenderer(GotModBoatEntities.BLOODWOOD_BOAT.get(),             ctx -> new GotBoatRenderer(ctx, false, "bloodwood"));
        event.registerEntityRenderer(GotModBoatEntities.BLOODWOOD_CHEST_BOAT.get(),       ctx -> new GotBoatRenderer(ctx, true,  "bloodwood"));
        event.registerEntityRenderer(GotModBoatEntities.BLUE_MAHOE_BOAT.get(),            ctx -> new GotBoatRenderer(ctx, false, "blue_mahoe"));
        event.registerEntityRenderer(GotModBoatEntities.BLUE_MAHOE_CHEST_BOAT.get(),      ctx -> new GotBoatRenderer(ctx, true,  "blue_mahoe"));
        event.registerEntityRenderer(GotModBoatEntities.COTTONWOOD_BOAT.get(),            ctx -> new GotBoatRenderer(ctx, false, "cottonwood"));
        event.registerEntityRenderer(GotModBoatEntities.COTTONWOOD_CHEST_BOAT.get(),      ctx -> new GotBoatRenderer(ctx, true,  "cottonwood"));
        event.registerEntityRenderer(GotModBoatEntities.BLACK_COTTONWOOD_BOAT.get(),      ctx -> new GotBoatRenderer(ctx, false, "black_cottonwood"));
        event.registerEntityRenderer(GotModBoatEntities.BLACK_COTTONWOOD_CHEST_BOAT.get(),ctx -> new GotBoatRenderer(ctx, true,  "black_cottonwood"));
        event.registerEntityRenderer(GotModBoatEntities.CINNAMON_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "cinnamon"));
        event.registerEntityRenderer(GotModBoatEntities.CINNAMON_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "cinnamon"));
        event.registerEntityRenderer(GotModBoatEntities.CLOVE_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "clove"));
        event.registerEntityRenderer(GotModBoatEntities.CLOVE_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "clove"));
        event.registerEntityRenderer(GotModBoatEntities.EBONY_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "ebony"));
        event.registerEntityRenderer(GotModBoatEntities.EBONY_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "ebony"));
        event.registerEntityRenderer(GotModBoatEntities.ELM_BOAT.get(),                   ctx -> new GotBoatRenderer(ctx, false, "elm"));
        event.registerEntityRenderer(GotModBoatEntities.ELM_CHEST_BOAT.get(),             ctx -> new GotBoatRenderer(ctx, true,  "elm"));
        event.registerEntityRenderer(GotModBoatEntities.CEDAR_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "cedar"));
        event.registerEntityRenderer(GotModBoatEntities.CEDAR_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "cedar"));
        event.registerEntityRenderer(GotModBoatEntities.APPLE_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "apple"));
        event.registerEntityRenderer(GotModBoatEntities.APPLE_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "apple"));
        event.registerEntityRenderer(GotModBoatEntities.GOLDENHEART_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, false, "goldenheart"));
        event.registerEntityRenderer(GotModBoatEntities.GOLDENHEART_CHEST_BOAT.get(),     ctx -> new GotBoatRenderer(ctx, true,  "goldenheart"));
        event.registerEntityRenderer(GotModBoatEntities.LINDEN_BOAT.get(),                ctx -> new GotBoatRenderer(ctx, false, "linden"));
        event.registerEntityRenderer(GotModBoatEntities.LINDEN_CHEST_BOAT.get(),          ctx -> new GotBoatRenderer(ctx, true,  "linden"));
        event.registerEntityRenderer(GotModBoatEntities.MAHOGANY_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "mahogany"));
        event.registerEntityRenderer(GotModBoatEntities.MAHOGANY_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "mahogany"));
        event.registerEntityRenderer(GotModBoatEntities.MAPLE_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "maple"));
        event.registerEntityRenderer(GotModBoatEntities.MAPLE_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "maple"));
        event.registerEntityRenderer(GotModBoatEntities.MYRRH_BOAT.get(),                 ctx -> new GotBoatRenderer(ctx, false, "myrrh"));
        event.registerEntityRenderer(GotModBoatEntities.MYRRH_CHEST_BOAT.get(),           ctx -> new GotBoatRenderer(ctx, true,  "myrrh"));
        event.registerEntityRenderer(GotModBoatEntities.REDWOOD_BOAT.get(),               ctx -> new GotBoatRenderer(ctx, false, "redwood"));
        event.registerEntityRenderer(GotModBoatEntities.REDWOOD_CHEST_BOAT.get(),         ctx -> new GotBoatRenderer(ctx, true,  "redwood"));
        event.registerEntityRenderer(GotModBoatEntities.CHESTNUT_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "chestnut"));
        event.registerEntityRenderer(GotModBoatEntities.CHESTNUT_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "chestnut"));
        event.registerEntityRenderer(GotModBoatEntities.WILLOW_BOAT.get(),                ctx -> new GotBoatRenderer(ctx, false, "willow"));
        event.registerEntityRenderer(GotModBoatEntities.WILLOW_CHEST_BOAT.get(),          ctx -> new GotBoatRenderer(ctx, true,  "willow"));
        event.registerEntityRenderer(GotModBoatEntities.WORMTREE_BOAT.get(),              ctx -> new GotBoatRenderer(ctx, false, "wormtree"));
        event.registerEntityRenderer(GotModBoatEntities.WORMTREE_CHEST_BOAT.get(),        ctx -> new GotBoatRenderer(ctx, true,  "wormtree"));
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
    }
}
