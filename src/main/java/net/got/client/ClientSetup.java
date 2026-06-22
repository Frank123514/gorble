package net.got.client;

import net.got.client.input.GotKeybinds;
import net.got.client.particle.WeirwoodLeafParticle;
import net.got.client.renderer.GotBoatRenderer;
import net.got.client.renderer.GotPlayerRenderer;
import net.got.entity.client.brownbear.GotBrownBearModel;
import net.got.entity.client.brownbear.GotBrownBearRenderer;
import net.got.entity.client.giant.GotGiantModel;
import net.got.entity.client.giant.GotGiantRenderer;
import net.got.entity.client.model.GotModelLayers;
import net.got.entity.client.npc.smallfolk.GotSmallfolkModel;
import net.got.entity.client.stag.GotStagModel;
import net.got.entity.client.heron.GotHeronModel;
import net.got.entity.client.heron.GotHeronRenderer;
import net.got.entity.client.direwolf.GotDirewolfModel;
import net.got.entity.client.direwolf.GotDirewolfRenderer;
import net.got.entity.client.crow.GotCrowModel;
import net.got.entity.client.crow.GotCrowRenderer;
import net.got.entity.client.mammoth.GotMammothModel;
import net.got.entity.client.mammoth.GotMammothRenderer;
import net.got.entity.client.npc.smallfolk.SmallfolkRenderer;
// ── Smallfolk entity imports — hold texture constants for all three tiers ─────
import net.got.entity.npc.smallfolk.NorthmanEntity;
import net.got.entity.npc.smallfolk.RiverlanderEntity;
import net.got.entity.npc.smallfolk.ValemanEntity;
import net.got.entity.npc.smallfolk.WestermanEntity;
import net.got.entity.npc.smallfolk.StormlorderEntity;
import net.got.entity.npc.smallfolk.IronbornEntity;
import net.got.entity.npc.smallfolk.DornishmanEntity;
import net.got.entity.npc.smallfolk.ReachmanEntity;
import net.got.entity.npc.levy.stark.StarkLevyEntity;
import net.got.entity.npc.levy.tully.TullyLevyEntity;
import net.got.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.entity.npc.levy.martell.MartellLevyEntity;
import net.got.entity.npc.levy.tyrell.TyrellLevyEntity;
import net.got.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.entity.npc.fighter.vale.ValeKnightEntity;
// ── Horse / Stag renderers ────────────────────────────────────────────────────
import net.got.entity.client.stag.GotStagRenderer;
import net.got.init.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.got.client.gui.OvenScreen;
import net.got.client.gui.SmithyScreen;
import net.got.client.gui.AlloyScreen;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.got.client.renderer.BellowsBlockEntityRenderer;
import net.minecraft.world.level.block.Blocks;

// The bus parameter was deprecated for removal in NeoForge 21.3.x.
// Omitting it defaults to the MOD bus, which is the correct behaviour here.
@EventBusSubscriber(
        modid = "got",
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        // NPC_TRADE menu type is kept registered but NpcTradeScreen is opened directly
        // via OpenTradeScreenPayload — no ScreenConstructor registration needed.
        event.register(GotModMenus.OVEN.get(), OvenScreen::new);
        event.register(GotModMenus.SMITHY.get(), SmithyScreen::new);
        event.register(GotModMenus.ALLOY.get(), AlloyScreen::new);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(GotKeybinds.OPEN_MAP);
        event.register(GotKeybinds.BLOCK);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GotModParticles.WEIRWOOD_LEAF.get(), WeirwoodLeafParticle.Provider::new);
    }
    /**
     * Registers the {@link net.minecraft.client.model.geom.LayerDefinition} for every
     * custom GOT entity model so NeoForge's model baking infrastructure can
     * build them before the first render frame.
     *
     * <p>Each entry maps a {@link net.minecraft.client.model.geom.ModelLayerLocation}
     * (declared in {@link GotModelLayers}) to the factory that produces the
     * corresponding {@link net.minecraft.client.model.geom.builders.LayerDefinition}.
     */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GotSmallfolkModel.LAYER_LOCATION, GotSmallfolkModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_STAG,          GotStagModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_HERON,         GotHeronModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_DIREWOLF,      GotDirewolfModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_CROW,          GotCrowModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_MAMMOTH,       GotMammothModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_BROWN_BEAR, GotBrownBearModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.GOT_GIANT,      GotGiantModel::createBodyLayer);
        event.registerLayerDefinition(GotModelLayers.BELLOWS,          BellowsBlockEntityRenderer::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // ── Doors ────────────────────────────────────────────────────
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.WEIRWOOD_DOOR.get(),           RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ASPEN_DOOR.get(),              RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.NIGHTWOOD_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.NIGHTWOOD_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PURPLEHEART_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PURPLEHEART_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.TIGERWOOD_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.TIGERWOOD_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BURL_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BURL_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SANDALWOOD_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SANDALWOOD_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SANDBEGGAR_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.SANDBEGGAR_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.APRICOT_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.APRICOT_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACKTHORN_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.BLACKTHORN_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.RED_CHERRY_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.RED_CHERRY_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CRABAPPLE_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.CRABAPPLE_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.DATE_PALM_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.DATE_PALM_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.FIG_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.FIG_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LEMON_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LEMON_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LIME_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.LIME_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.OLIVE_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.OLIVE_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ORANGE_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ORANGE_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PEACH_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PEACH_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PEAR_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PEAR_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PERSIMMON_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PERSIMMON_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PINK_IVORY_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PINK_IVORY_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PLUM_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PLUM_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.POMEGRANATE_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.POMEGRANATE_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PRUNE_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.PRUNE_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ALMOND_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.ALMOND_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.NUTMEG_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.NUTMEG_TRAPDOOR.get(), RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.HEMLOCK_DOOR.get(),     RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(GotModBlocks.HEMLOCK_TRAPDOOR.get(), RenderType.cutout());

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

    /**
     * Casts any boat EntityType to EntityType<AbstractBoat> so Java's type
     * inference resolves T=AbstractBoat when registering GotBoatRenderer
     * (which is an EntityRenderer<AbstractBoat, ...>). Safe at runtime because
     * GotBoat and GotChestBoat both extend AbstractBoat, and renderer lookup
     * uses EntityType object identity, not generic type parameters.
     */
    @SuppressWarnings("unchecked")
    private static EntityType<AbstractBoat> boat(EntityType<?> type) {
        return (EntityType<AbstractBoat>) type;
    }

    @SuppressWarnings("unchecked")
    private static <T extends net.minecraft.world.entity.player.Player>
    EntityType<T> player(EntityType<?> type) {
        return (EntityType<T>) type;
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ── Tier-1 Smallfolk renderers (SmallfolkGeoRenderer with culture textures) ─
        event.registerEntityRenderer(GotModEntities.NORTHMAN.get(),    ctx -> new SmallfolkRenderer<>(ctx, NorthmanEntity.MALE_TEXTURES,    NorthmanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.RIVERLANDER.get(), ctx -> new SmallfolkRenderer<>(ctx, RiverlanderEntity.MALE_TEXTURES, RiverlanderEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.VALEMAN.get(),     ctx -> new SmallfolkRenderer<>(ctx, ValemanEntity.MALE_TEXTURES,     ValemanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.WESTERMAN.get(),   ctx -> new SmallfolkRenderer<>(ctx, WestermanEntity.MALE_TEXTURES,   WestermanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.STORMLORDER.get(), ctx -> new SmallfolkRenderer<>(ctx, StormlorderEntity.MALE_TEXTURES, StormlorderEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.IRONBORN.get(),    ctx -> new SmallfolkRenderer<>(ctx, IronbornEntity.MALE_TEXTURES,    IronbornEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.DORNISHMAN.get(),  ctx -> new SmallfolkRenderer<>(ctx, DornishmanEntity.MALE_TEXTURES,  DornishmanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.REACHMAN.get(),    ctx -> new SmallfolkRenderer<>(ctx, ReachmanEntity.MALE_TEXTURES,    ReachmanEntity.FEMALE_TEXTURES));

        // ── Levy NPC renderers (Tier 2) — SmallfolkGeoRenderer with house textures ──
        event.registerEntityRenderer(GotModEntities.STARK_LEVY.get(),     ctx -> new SmallfolkRenderer<>(ctx, StarkLevyEntity.MALE_TEXTURES,     StarkLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.TULLY_LEVY.get(),     ctx -> new SmallfolkRenderer<>(ctx, TullyLevyEntity.MALE_TEXTURES,     TullyLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.LANNISTER_LEVY.get(), ctx -> new SmallfolkRenderer<>(ctx, LannisterLevyEntity.MALE_TEXTURES, LannisterLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.BARATHEON_LEVY.get(), ctx -> new SmallfolkRenderer<>(ctx, BaratheonLevyEntity.MALE_TEXTURES, BaratheonLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.GREYJOY_LEVY.get(),   ctx -> new SmallfolkRenderer<>(ctx, GreyjoyLevyEntity.MALE_TEXTURES,   GreyjoyLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.MARTELL_LEVY.get(),   ctx -> new SmallfolkRenderer<>(ctx, MartellLevyEntity.MALE_TEXTURES,   MartellLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.TYRELL_LEVY.get(),    ctx -> new SmallfolkRenderer<>(ctx, TyrellLevyEntity.MALE_TEXTURES,    TyrellLevyEntity.FEMALE_TEXTURES));

        // ── Skilled Fighter renderers (Tier 3) ───────────────────────────────
        event.registerEntityRenderer(GotModEntities.NORTH_SOLDIER.get(), ctx -> new SmallfolkRenderer<>(ctx, NorthSoldierEntity.MALE_TEXTURES, NorthSoldierEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(GotModEntities.VALE_KNIGHT.get(),   ctx -> new SmallfolkRenderer<>(ctx, ValeKnightEntity.MALE_TEXTURES,   ValeKnightEntity.FEMALE_TEXTURES));

        // ── GOT Horse / Stag / Animal renderers ──────────────────────────────
        event.registerEntityRenderer(GotModEntities.GOT_STAG.get(),     GotStagRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_HERON.get(),    GotHeronRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_DIREWOLF.get(), GotDirewolfRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_CROW.get(),     GotCrowRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_MAMMOTH.get(),  GotMammothRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_BROWN_BEAR.get(), GotBrownBearRenderer::new);
        event.registerEntityRenderer(GotModEntities.GOT_GIANT.get(),      GotGiantRenderer::new);

        // ── Boat renderers ───────────────────────────────────────────────
        event.registerEntityRenderer(boat(GotModBoatEntities.WEIRWOOD_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "weirwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WEIRWOOD_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "weirwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ASPEN_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "aspen"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ASPEN_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "aspen"));
        event.registerEntityRenderer(boat(GotModBoatEntities.NIGHTWOOD_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "nightwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.NIGHTWOOD_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "nightwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PURPLEHEART_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "purpleheart"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PURPLEHEART_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "purpleheart"));
        event.registerEntityRenderer(boat(GotModBoatEntities.TIGERWOOD_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "tigerwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.TIGERWOOD_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "tigerwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BURL_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "burl"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BURL_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "burl"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SANDALWOOD_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "sandalwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SANDALWOOD_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "sandalwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SANDBEGGAR_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "sandbeggar"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SANDBEGGAR_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "sandbeggar"));
        event.registerEntityRenderer(boat(GotModBoatEntities.APRICOT_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "apricot"));
        event.registerEntityRenderer(boat(GotModBoatEntities.APRICOT_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "apricot"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACKTHORN_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "blackthorn"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACKTHORN_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "blackthorn"));
        event.registerEntityRenderer(boat(GotModBoatEntities.RED_CHERRY_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "red_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.RED_CHERRY_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "red_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WHITE_CHERRY_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "white_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WHITE_CHERRY_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "white_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACK_CHERRY_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "black_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACK_CHERRY_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "black_cherry"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CRABAPPLE_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "crabapple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CRABAPPLE_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "crabapple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.DATE_PALM_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "date_palm"));
        event.registerEntityRenderer(boat(GotModBoatEntities.DATE_PALM_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "date_palm"));
        event.registerEntityRenderer(boat(GotModBoatEntities.FIG_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "fig"));
        event.registerEntityRenderer(boat(GotModBoatEntities.FIG_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "fig"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LEMON_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "lemon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LEMON_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "lemon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LIME_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "lime"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LIME_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "lime"));
        event.registerEntityRenderer(boat(GotModBoatEntities.OLIVE_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "olive"));
        event.registerEntityRenderer(boat(GotModBoatEntities.OLIVE_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "olive"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ORANGE_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "orange"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ORANGE_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "orange"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PEACH_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "peach"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PEACH_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "peach"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PEAR_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "pear"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PEAR_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "pear"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PERSIMMON_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "persimmon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PERSIMMON_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "persimmon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PINK_IVORY_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "pink_ivory"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PINK_IVORY_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "pink_ivory"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PLUM_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "plum"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PLUM_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "plum"));
        event.registerEntityRenderer(boat(GotModBoatEntities.POMEGRANATE_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "pomegranate"));
        event.registerEntityRenderer(boat(GotModBoatEntities.POMEGRANATE_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "pomegranate"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PRUNE_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "prune"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PRUNE_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "prune"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ALMOND_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "almond"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ALMOND_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "almond"));
        event.registerEntityRenderer(boat(GotModBoatEntities.NUTMEG_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "nutmeg"));
        event.registerEntityRenderer(boat(GotModBoatEntities.NUTMEG_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "nutmeg"));
        event.registerEntityRenderer(boat(GotModBoatEntities.HEMLOCK_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, false, "hemlock"));
        event.registerEntityRenderer(boat(GotModBoatEntities.HEMLOCK_CHEST_BOAT.get()), ctx -> new GotBoatRenderer(ctx, true,  "hemlock"));

        event.registerEntityRenderer(boat(GotModBoatEntities.ALDER_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "alder"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ALDER_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "alder"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PINE_BOAT.get()),                  ctx -> new GotBoatRenderer(ctx, false, "pine"));
        event.registerEntityRenderer(boat(GotModBoatEntities.PINE_CHEST_BOAT.get()),            ctx -> new GotBoatRenderer(ctx, true,  "pine"));
        event.registerEntityRenderer(boat(GotModBoatEntities.FIR_BOAT.get()),                   ctx -> new GotBoatRenderer(ctx, false, "fir"));
        event.registerEntityRenderer(boat(GotModBoatEntities.FIR_CHEST_BOAT.get()),             ctx -> new GotBoatRenderer(ctx, true,  "fir"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SENTINAL_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "sentinal"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SENTINAL_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "sentinal"));
        event.registerEntityRenderer(boat(GotModBoatEntities.IRONWOOD_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "ironwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.IRONWOOD_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "ironwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BEECH_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "beech"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BEECH_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "beech"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SOLDIER_PINE_BOAT.get()),          ctx -> new GotBoatRenderer(ctx, false, "soldier_pine"));
        event.registerEntityRenderer(boat(GotModBoatEntities.SOLDIER_PINE_CHEST_BOAT.get()),    ctx -> new GotBoatRenderer(ctx, true,  "soldier_pine"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ASH_BOAT.get()),                   ctx -> new GotBoatRenderer(ctx, false, "ash"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ASH_CHEST_BOAT.get()),             ctx -> new GotBoatRenderer(ctx, true,  "ash"));
        event.registerEntityRenderer(boat(GotModBoatEntities.HAWTHORN_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "hawthorn"));
        event.registerEntityRenderer(boat(GotModBoatEntities.HAWTHORN_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "hawthorn"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACKBARK_BOAT.get()),             ctx -> new GotBoatRenderer(ctx, false, "blackbark"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACKBARK_CHEST_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, true,  "blackbark"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLOODWOOD_BOAT.get()),             ctx -> new GotBoatRenderer(ctx, false, "bloodwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLOODWOOD_CHEST_BOAT.get()),       ctx -> new GotBoatRenderer(ctx, true,  "bloodwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLUE_MAHOE_BOAT.get()),            ctx -> new GotBoatRenderer(ctx, false, "blue_mahoe"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLUE_MAHOE_CHEST_BOAT.get()),      ctx -> new GotBoatRenderer(ctx, true,  "blue_mahoe"));
        event.registerEntityRenderer(boat(GotModBoatEntities.COTTONWOOD_BOAT.get()),            ctx -> new GotBoatRenderer(ctx, false, "cottonwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.COTTONWOOD_CHEST_BOAT.get()),      ctx -> new GotBoatRenderer(ctx, true,  "cottonwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACK_COTTONWOOD_BOAT.get()),      ctx -> new GotBoatRenderer(ctx, false, "black_cottonwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.BLACK_COTTONWOOD_CHEST_BOAT.get()),ctx -> new GotBoatRenderer(ctx, true,  "black_cottonwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CINNAMON_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "cinnamon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CINNAMON_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "cinnamon"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CLOVE_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "clove"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CLOVE_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "clove"));
        event.registerEntityRenderer(boat(GotModBoatEntities.EBONY_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "ebony"));
        event.registerEntityRenderer(boat(GotModBoatEntities.EBONY_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "ebony"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ELM_BOAT.get()),                   ctx -> new GotBoatRenderer(ctx, false, "elm"));
        event.registerEntityRenderer(boat(GotModBoatEntities.ELM_CHEST_BOAT.get()),             ctx -> new GotBoatRenderer(ctx, true,  "elm"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CEDAR_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "cedar"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CEDAR_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "cedar"));
        event.registerEntityRenderer(boat(GotModBoatEntities.APPLE_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "apple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.APPLE_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "apple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.GOLDENHEART_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, false, "goldenheart"));
        event.registerEntityRenderer(boat(GotModBoatEntities.GOLDENHEART_CHEST_BOAT.get()),     ctx -> new GotBoatRenderer(ctx, true,  "goldenheart"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LINDEN_BOAT.get()),                ctx -> new GotBoatRenderer(ctx, false, "linden"));
        event.registerEntityRenderer(boat(GotModBoatEntities.LINDEN_CHEST_BOAT.get()),          ctx -> new GotBoatRenderer(ctx, true,  "linden"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MAHOGANY_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "mahogany"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MAHOGANY_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "mahogany"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MAPLE_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "maple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MAPLE_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "maple"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MYRRH_BOAT.get()),                 ctx -> new GotBoatRenderer(ctx, false, "myrrh"));
        event.registerEntityRenderer(boat(GotModBoatEntities.MYRRH_CHEST_BOAT.get()),           ctx -> new GotBoatRenderer(ctx, true,  "myrrh"));
        event.registerEntityRenderer(boat(GotModBoatEntities.REDWOOD_BOAT.get()),               ctx -> new GotBoatRenderer(ctx, false, "redwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.REDWOOD_CHEST_BOAT.get()),         ctx -> new GotBoatRenderer(ctx, true,  "redwood"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CHESTNUT_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "chestnut"));
        event.registerEntityRenderer(boat(GotModBoatEntities.CHESTNUT_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "chestnut"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WILLOW_BOAT.get()),                ctx -> new GotBoatRenderer(ctx, false, "willow"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WILLOW_CHEST_BOAT.get()),          ctx -> new GotBoatRenderer(ctx, true,  "willow"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WORMTREE_BOAT.get()),              ctx -> new GotBoatRenderer(ctx, false, "wormtree"));
        event.registerEntityRenderer(boat(GotModBoatEntities.WORMTREE_CHEST_BOAT.get()),        ctx -> new GotBoatRenderer(ctx, true,  "wormtree"));
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // ── Bellows ──────────────────────────────────────────────────────
        event.registerBlockEntityRenderer(GotModBlockEntities.BELLOWS.get(), BellowsBlockEntityRenderer::new);

        // ── Sign block entity renderers ──────────────────────────────────
        event.registerBlockEntityRenderer(GotModBlockEntities.WEIRWOOD_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ASPEN_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.NIGHTWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PURPLEHEART_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.TIGERWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BURL_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SANDALWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.SANDBEGGAR_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.APRICOT_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACKTHORN_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.RED_CHERRY_SIGN.get(),   SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.WHITE_CHERRY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.BLACK_CHERRY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.CRABAPPLE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.DATE_PALM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.FIG_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.LEMON_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.LIME_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.OLIVE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ORANGE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PEACH_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PEAR_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PERSIMMON_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PINK_IVORY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PLUM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.POMEGRANATE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.PRUNE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.ALMOND_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.NUTMEG_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(GotModBlockEntities.HEMLOCK_SIGN.get(), SignRenderer::new);

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