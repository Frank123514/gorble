package net.got.client;

import net.got.client.armor.*;
import net.got.client.input.Keybinds;
import net.got.client.particle.WeirwoodLeafParticle;
import net.got.client.renderer.BoatRenderer;
import net.got.event.entity.client.brownbear.BrownBearModel;
import net.got.event.entity.client.brownbear.BrownBearRenderer;
import net.got.event.entity.client.giant.GiantModel;
import net.got.event.entity.client.giant.GiantRenderer;
import net.got.event.entity.client.model.ModelLayers;
import net.got.event.entity.client.npc.smallfolk.SmallfolkModel;
import net.got.event.entity.client.stag.StagModel;
import net.got.event.entity.client.heron.HeronModel;
import net.got.event.entity.client.heron.HeronRenderer;
import net.got.event.entity.client.direwolf.DirewolfModel;
import net.got.event.entity.client.direwolf.DirewolfRenderer;
import net.got.event.entity.client.crow.CrowModel;
import net.got.event.entity.client.crow.CrowRenderer;
import net.got.event.entity.client.mammoth.MammothModel;
import net.got.event.entity.client.mammoth.MammothRenderer;
import net.got.event.entity.client.npc.smallfolk.SmallfolkRenderer;

import net.got.event.entity.npc.smallfolk.NorthmanEntity;
import net.got.event.entity.npc.smallfolk.RiverlanderEntity;
import net.got.event.entity.npc.smallfolk.ValemanEntity;
import net.got.event.entity.npc.smallfolk.WestermanEntity;
import net.got.event.entity.npc.smallfolk.StormlorderEntity;
import net.got.event.entity.npc.smallfolk.IronbornEntity;
import net.got.event.entity.npc.smallfolk.DornishmanEntity;
import net.got.event.entity.npc.smallfolk.ReachmanEntity;
import net.got.event.entity.npc.levy.stark.StarkLevyEntity;
import net.got.event.entity.npc.levy.tully.TullyLevyEntity;
import net.got.event.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.event.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.event.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.event.entity.npc.levy.martell.MartellLevyEntity;
import net.got.event.entity.npc.levy.tyrell.TyrellLevyEntity;
import net.got.event.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.event.entity.npc.fighter.vale.ValeKnightEntity;

import net.got.event.entity.client.stag.StagRenderer;
import net.got.init.*;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.got.client.gui.OvenScreen;
import net.got.client.gui.SmithyScreen;
import net.got.client.gui.AlloyScreen;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.got.client.model.HotIronIngotModel;
import net.got.client.renderer.BellowsBlockEntityRenderer;
import net.got.client.renderer.SmithingAnvilBlockEntityRenderer;
import net.got.client.gui.SmithingAnvilScreen;
import net.got.client.gui.HeatTreatingScreen;

@EventBusSubscriber(
        modid = "got",
        value = Dist.CLIENT
)
public final class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {

        event.register(ModMenus.OVEN.get(), OvenScreen::new);
        event.register(ModMenus.SMITHY.get(), SmithyScreen::new);
        event.register(ModMenus.ALLOY.get(), AlloyScreen::new);
        event.register(ModMenus.SMITHING_ANVIL.get(), SmithingAnvilScreen::new);
        event.register(ModMenus.HEAT_TREATING.get(), HeatTreatingScreen::new);
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(Keybinds.OPEN_MAP);
        event.register(Keybinds.BLOCK);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.WEIRWOOD_LEAF.get(), WeirwoodLeafParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SmallfolkModel.LAYER_LOCATION, SmallfolkModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_STAG,          StagModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_HERON,         HeronModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_DIREWOLF,      DirewolfModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_CROW,          CrowModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_MAMMOTH,       MammothModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_BROWN_BEAR, BrownBearModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.GOT_GIANT,      GiantModel::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.BELLOWS,          BellowsBlockEntityRenderer::createBodyLayer);

        event.registerLayerDefinition(SkullCapModel.LAYER_LOCATION,   SkullCapModel::createBodyLayer);
        event.registerLayerDefinition(ConicalCapModel.LAYER_LOCATION,      ConicalCapModel::createBodyLayer);
        event.registerLayerDefinition(KettleHelmModel.LAYER_LOCATION,          KettleHelmModel::createBodyLayer);
        event.registerLayerDefinition(PaddedCoifModel.LAYER_LOCATION,      PaddedCoifModel::createBodyLayer);
        event.registerLayerDefinition(MailCoifModel.LAYER_LOCATION,        MailCoifModel::createBodyLayer);
        event.registerLayerDefinition(HalfhelmModel.LAYER_LOCATION,   HalfhelmModel::createBodyLayer);
        event.registerLayerDefinition(BascinetModel.LAYER_LOCATION,   BascinetModel::createBodyLayer);
        event.registerLayerDefinition(GreathelmFlatModel.LAYER_LOCATION,   GreathelmFlatModel::createBodyLayer);
        event.registerLayerDefinition(GreathelmRoundedModel.LAYER_LOCATION, GreathelmRoundedModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASS_BLOCK_SLAB.get(),   ChunkSectionLayer.CUTOUT );
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRASS_BLOCK_STAIRS.get(), ChunkSectionLayer.CUTOUT );
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.DIRT_PATH_SLAB.get(),     ChunkSectionLayer.CUTOUT);

            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEIRWOOD_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ASPEN_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.NIGHTWOOD_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.NIGHTWOOD_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURPLEHEART_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURPLEHEART_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.TIGERWOOD_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.TIGERWOOD_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SANDALWOOD_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SANDALWOOD_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SANDBEGGAR_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SANDBEGGAR_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.APRICOT_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.APRICOT_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKTHORN_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKTHORN_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_CHERRY_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_CHERRY_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRABAPPLE_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRABAPPLE_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.DATE_PALM_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.DATE_PALM_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIG_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIG_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LEMON_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LEMON_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIME_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIME_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLIVE_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORANGE_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORANGE_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEACH_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEACH_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEAR_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEAR_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PERSIMMON_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PERSIMMON_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_IVORY_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_IVORY_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PLUM_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PLUM_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.POMEGRANATE_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.POMEGRANATE_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRUNE_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRUNE_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALMOND_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALMOND_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.NUTMEG_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.NUTMEG_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.HEMLOCK_DOOR.get(),     ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.HEMLOCK_TRAPDOOR.get(), ChunkSectionLayer.CUTOUT);

            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALDER_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINE_DOOR.get(),               ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIR_DOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SENTINAL_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRONWOOD_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEECH_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOLDIER_PINE_DOOR.get(),       ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ASH_DOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.HAWTHORN_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKBARK_DOOR.get(),          ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOODWOOD_DOOR.get(),          ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUE_MAHOE_DOOR.get(),         ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.COTTONWOOD_DOOR.get(),         ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACK_COTTONWOOD_DOOR.get(),   ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CINNAMON_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLOVE_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELM_DOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEDAR_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.APPLE_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLDENHEART_DOOR.get(),        ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LINDEN_DOOR.get(),             ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAHOGANY_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MYRRH_DOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.REDWOOD_DOOR.get(),            ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHESTNUT_DOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILLOW_DOOR.get(),             ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WORMTREE_DOOR.get(),           ChunkSectionLayer.CUTOUT);

            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEIRWOOD_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ASPEN_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALDER_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINE_TRAPDOOR.get(),               ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIR_TRAPDOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SENTINAL_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRONWOOD_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEECH_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.SOLDIER_PINE_TRAPDOOR.get(),       ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ASH_TRAPDOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.HAWTHORN_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACKBARK_TRAPDOOR.get(),          ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOODWOOD_TRAPDOOR.get(),          ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUE_MAHOE_TRAPDOOR.get(),         ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.COTTONWOOD_TRAPDOOR.get(),         ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACK_COTTONWOOD_TRAPDOOR.get(),   ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CINNAMON_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLOVE_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.EBONY_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELM_TRAPDOOR.get(),                ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEDAR_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.APPLE_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLDENHEART_TRAPDOOR.get(),        ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.LINDEN_TRAPDOOR.get(),             ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAHOGANY_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.MYRRH_TRAPDOOR.get(),              ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.REDWOOD_TRAPDOOR.get(),            ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHESTNUT_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILLOW_TRAPDOOR.get(),             ChunkSectionLayer.CUTOUT);
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(ModBlocks.WORMTREE_TRAPDOOR.get(),           ChunkSectionLayer.CUTOUT);
        });
    }

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

        event.registerEntityRenderer(ModEntities.NORTHMAN.get(),    ctx -> new SmallfolkRenderer<>(ctx, NorthmanEntity.MALE_TEXTURES,    NorthmanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.RIVERLANDER.get(), ctx -> new SmallfolkRenderer<>(ctx, RiverlanderEntity.MALE_TEXTURES, RiverlanderEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.VALEMAN.get(),     ctx -> new SmallfolkRenderer<>(ctx, ValemanEntity.MALE_TEXTURES,     ValemanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.WESTERMAN.get(),   ctx -> new SmallfolkRenderer<>(ctx, WestermanEntity.MALE_TEXTURES,   WestermanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.STORMLORDER.get(), ctx -> new SmallfolkRenderer<>(ctx, StormlorderEntity.MALE_TEXTURES, StormlorderEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.IRONBORN.get(),    ctx -> new SmallfolkRenderer<>(ctx, IronbornEntity.MALE_TEXTURES,    IronbornEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.DORNISHMAN.get(),  ctx -> new SmallfolkRenderer<>(ctx, DornishmanEntity.MALE_TEXTURES,  DornishmanEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.REACHMAN.get(),    ctx -> new SmallfolkRenderer<>(ctx, ReachmanEntity.MALE_TEXTURES,    ReachmanEntity.FEMALE_TEXTURES));

        event.registerEntityRenderer(ModEntities.STARK_LEVY.get(),     ctx -> new SmallfolkRenderer<>(ctx, StarkLevyEntity.MALE_TEXTURES,     StarkLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.TULLY_LEVY.get(),     ctx -> new SmallfolkRenderer<>(ctx, TullyLevyEntity.MALE_TEXTURES,     TullyLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.LANNISTER_LEVY.get(), ctx -> new SmallfolkRenderer<>(ctx, LannisterLevyEntity.MALE_TEXTURES, LannisterLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.BARATHEON_LEVY.get(), ctx -> new SmallfolkRenderer<>(ctx, BaratheonLevyEntity.MALE_TEXTURES, BaratheonLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.GREYJOY_LEVY.get(),   ctx -> new SmallfolkRenderer<>(ctx, GreyjoyLevyEntity.MALE_TEXTURES,   GreyjoyLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.MARTELL_LEVY.get(),   ctx -> new SmallfolkRenderer<>(ctx, MartellLevyEntity.MALE_TEXTURES,   MartellLevyEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.TYRELL_LEVY.get(),    ctx -> new SmallfolkRenderer<>(ctx, TyrellLevyEntity.MALE_TEXTURES,    TyrellLevyEntity.FEMALE_TEXTURES));

        event.registerEntityRenderer(ModEntities.NORTH_SOLDIER.get(), ctx -> new SmallfolkRenderer<>(ctx, NorthSoldierEntity.MALE_TEXTURES, NorthSoldierEntity.FEMALE_TEXTURES));
        event.registerEntityRenderer(ModEntities.VALE_KNIGHT.get(),   ctx -> new SmallfolkRenderer<>(ctx, ValeKnightEntity.MALE_TEXTURES,   ValeKnightEntity.FEMALE_TEXTURES));

        event.registerEntityRenderer(ModEntities.GOT_STAG.get(),     StagRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_HERON.get(),    HeronRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_DIREWOLF.get(), DirewolfRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_CROW.get(),     CrowRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_MAMMOTH.get(),  MammothRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_BROWN_BEAR.get(), BrownBearRenderer::new);
        event.registerEntityRenderer(ModEntities.GOT_GIANT.get(),      GiantRenderer::new);

        event.registerEntityRenderer(boat(ModBoatEntities.WEIRWOOD_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "weirwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.WEIRWOOD_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "weirwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.ASPEN_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "aspen"));
        event.registerEntityRenderer(boat(ModBoatEntities.ASPEN_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "aspen"));
        event.registerEntityRenderer(boat(ModBoatEntities.NIGHTWOOD_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "nightwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.NIGHTWOOD_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "nightwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.PURPLEHEART_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "purpleheart"));
        event.registerEntityRenderer(boat(ModBoatEntities.PURPLEHEART_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "purpleheart"));
        event.registerEntityRenderer(boat(ModBoatEntities.TIGERWOOD_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "tigerwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.TIGERWOOD_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "tigerwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.SANDALWOOD_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "sandalwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.SANDALWOOD_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "sandalwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.SANDBEGGAR_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "sandbeggar"));
        event.registerEntityRenderer(boat(ModBoatEntities.SANDBEGGAR_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "sandbeggar"));
        event.registerEntityRenderer(boat(ModBoatEntities.APRICOT_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "apricot"));
        event.registerEntityRenderer(boat(ModBoatEntities.APRICOT_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "apricot"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACKTHORN_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "blackthorn"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACKTHORN_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "blackthorn"));
        event.registerEntityRenderer(boat(ModBoatEntities.RED_CHERRY_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "red_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.RED_CHERRY_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "red_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.WHITE_CHERRY_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "white_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.WHITE_CHERRY_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "white_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACK_CHERRY_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "black_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACK_CHERRY_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "black_cherry"));
        event.registerEntityRenderer(boat(ModBoatEntities.CRABAPPLE_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "crabapple"));
        event.registerEntityRenderer(boat(ModBoatEntities.CRABAPPLE_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "crabapple"));
        event.registerEntityRenderer(boat(ModBoatEntities.DATE_PALM_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "date_palm"));
        event.registerEntityRenderer(boat(ModBoatEntities.DATE_PALM_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "date_palm"));
        event.registerEntityRenderer(boat(ModBoatEntities.FIG_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "fig"));
        event.registerEntityRenderer(boat(ModBoatEntities.FIG_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "fig"));
        event.registerEntityRenderer(boat(ModBoatEntities.LEMON_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "lemon"));
        event.registerEntityRenderer(boat(ModBoatEntities.LEMON_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "lemon"));
        event.registerEntityRenderer(boat(ModBoatEntities.LIME_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "lime"));
        event.registerEntityRenderer(boat(ModBoatEntities.LIME_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "lime"));
        event.registerEntityRenderer(boat(ModBoatEntities.OLIVE_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "olive"));
        event.registerEntityRenderer(boat(ModBoatEntities.OLIVE_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "olive"));
        event.registerEntityRenderer(boat(ModBoatEntities.ORANGE_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "orange"));
        event.registerEntityRenderer(boat(ModBoatEntities.ORANGE_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "orange"));
        event.registerEntityRenderer(boat(ModBoatEntities.PEACH_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "peach"));
        event.registerEntityRenderer(boat(ModBoatEntities.PEACH_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "peach"));
        event.registerEntityRenderer(boat(ModBoatEntities.PEAR_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "pear"));
        event.registerEntityRenderer(boat(ModBoatEntities.PEAR_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "pear"));
        event.registerEntityRenderer(boat(ModBoatEntities.PERSIMMON_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "persimmon"));
        event.registerEntityRenderer(boat(ModBoatEntities.PERSIMMON_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "persimmon"));
        event.registerEntityRenderer(boat(ModBoatEntities.PINK_IVORY_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "pink_ivory"));
        event.registerEntityRenderer(boat(ModBoatEntities.PINK_IVORY_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "pink_ivory"));
        event.registerEntityRenderer(boat(ModBoatEntities.PLUM_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "plum"));
        event.registerEntityRenderer(boat(ModBoatEntities.PLUM_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "plum"));
        event.registerEntityRenderer(boat(ModBoatEntities.POMEGRANATE_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "pomegranate"));
        event.registerEntityRenderer(boat(ModBoatEntities.POMEGRANATE_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "pomegranate"));
        event.registerEntityRenderer(boat(ModBoatEntities.PRUNE_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "prune"));
        event.registerEntityRenderer(boat(ModBoatEntities.PRUNE_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "prune"));
        event.registerEntityRenderer(boat(ModBoatEntities.ALMOND_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "almond"));
        event.registerEntityRenderer(boat(ModBoatEntities.ALMOND_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "almond"));
        event.registerEntityRenderer(boat(ModBoatEntities.NUTMEG_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "nutmeg"));
        event.registerEntityRenderer(boat(ModBoatEntities.NUTMEG_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "nutmeg"));
        event.registerEntityRenderer(boat(ModBoatEntities.HEMLOCK_BOAT.get()),       ctx -> new BoatRenderer(ctx, false, "hemlock"));
        event.registerEntityRenderer(boat(ModBoatEntities.HEMLOCK_CHEST_BOAT.get()), ctx -> new BoatRenderer(ctx, true,  "hemlock"));

        event.registerEntityRenderer(boat(ModBoatEntities.ALDER_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "alder"));
        event.registerEntityRenderer(boat(ModBoatEntities.ALDER_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "alder"));
        event.registerEntityRenderer(boat(ModBoatEntities.PINE_BOAT.get()),                  ctx -> new BoatRenderer(ctx, false, "pine"));
        event.registerEntityRenderer(boat(ModBoatEntities.PINE_CHEST_BOAT.get()),            ctx -> new BoatRenderer(ctx, true,  "pine"));
        event.registerEntityRenderer(boat(ModBoatEntities.FIR_BOAT.get()),                   ctx -> new BoatRenderer(ctx, false, "fir"));
        event.registerEntityRenderer(boat(ModBoatEntities.FIR_CHEST_BOAT.get()),             ctx -> new BoatRenderer(ctx, true,  "fir"));
        event.registerEntityRenderer(boat(ModBoatEntities.SENTINAL_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "sentinal"));
        event.registerEntityRenderer(boat(ModBoatEntities.SENTINAL_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "sentinal"));
        event.registerEntityRenderer(boat(ModBoatEntities.IRONWOOD_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "ironwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.IRONWOOD_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "ironwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.BEECH_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "beech"));
        event.registerEntityRenderer(boat(ModBoatEntities.BEECH_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "beech"));
        event.registerEntityRenderer(boat(ModBoatEntities.SOLDIER_PINE_BOAT.get()),          ctx -> new BoatRenderer(ctx, false, "soldier_pine"));
        event.registerEntityRenderer(boat(ModBoatEntities.SOLDIER_PINE_CHEST_BOAT.get()),    ctx -> new BoatRenderer(ctx, true,  "soldier_pine"));
        event.registerEntityRenderer(boat(ModBoatEntities.ASH_BOAT.get()),                   ctx -> new BoatRenderer(ctx, false, "ash"));
        event.registerEntityRenderer(boat(ModBoatEntities.ASH_CHEST_BOAT.get()),             ctx -> new BoatRenderer(ctx, true,  "ash"));
        event.registerEntityRenderer(boat(ModBoatEntities.HAWTHORN_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "hawthorn"));
        event.registerEntityRenderer(boat(ModBoatEntities.HAWTHORN_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "hawthorn"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACKBARK_BOAT.get()),             ctx -> new BoatRenderer(ctx, false, "blackbark"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACKBARK_CHEST_BOAT.get()),       ctx -> new BoatRenderer(ctx, true,  "blackbark"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLOODWOOD_BOAT.get()),             ctx -> new BoatRenderer(ctx, false, "bloodwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLOODWOOD_CHEST_BOAT.get()),       ctx -> new BoatRenderer(ctx, true,  "bloodwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLUE_MAHOE_BOAT.get()),            ctx -> new BoatRenderer(ctx, false, "blue_mahoe"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLUE_MAHOE_CHEST_BOAT.get()),      ctx -> new BoatRenderer(ctx, true,  "blue_mahoe"));
        event.registerEntityRenderer(boat(ModBoatEntities.COTTONWOOD_BOAT.get()),            ctx -> new BoatRenderer(ctx, false, "cottonwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.COTTONWOOD_CHEST_BOAT.get()),      ctx -> new BoatRenderer(ctx, true,  "cottonwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACK_COTTONWOOD_BOAT.get()),      ctx -> new BoatRenderer(ctx, false, "black_cottonwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.BLACK_COTTONWOOD_CHEST_BOAT.get()),ctx -> new BoatRenderer(ctx, true,  "black_cottonwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.CINNAMON_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "cinnamon"));
        event.registerEntityRenderer(boat(ModBoatEntities.CINNAMON_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "cinnamon"));
        event.registerEntityRenderer(boat(ModBoatEntities.CLOVE_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "clove"));
        event.registerEntityRenderer(boat(ModBoatEntities.CLOVE_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "clove"));
        event.registerEntityRenderer(boat(ModBoatEntities.EBONY_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "ebony"));
        event.registerEntityRenderer(boat(ModBoatEntities.EBONY_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "ebony"));
        event.registerEntityRenderer(boat(ModBoatEntities.ELM_BOAT.get()),                   ctx -> new BoatRenderer(ctx, false, "elm"));
        event.registerEntityRenderer(boat(ModBoatEntities.ELM_CHEST_BOAT.get()),             ctx -> new BoatRenderer(ctx, true,  "elm"));
        event.registerEntityRenderer(boat(ModBoatEntities.CEDAR_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "cedar"));
        event.registerEntityRenderer(boat(ModBoatEntities.CEDAR_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "cedar"));
        event.registerEntityRenderer(boat(ModBoatEntities.APPLE_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "apple"));
        event.registerEntityRenderer(boat(ModBoatEntities.APPLE_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "apple"));
        event.registerEntityRenderer(boat(ModBoatEntities.GOLDENHEART_BOAT.get()),           ctx -> new BoatRenderer(ctx, false, "goldenheart"));
        event.registerEntityRenderer(boat(ModBoatEntities.GOLDENHEART_CHEST_BOAT.get()),     ctx -> new BoatRenderer(ctx, true,  "goldenheart"));
        event.registerEntityRenderer(boat(ModBoatEntities.LINDEN_BOAT.get()),                ctx -> new BoatRenderer(ctx, false, "linden"));
        event.registerEntityRenderer(boat(ModBoatEntities.LINDEN_CHEST_BOAT.get()),          ctx -> new BoatRenderer(ctx, true,  "linden"));
        event.registerEntityRenderer(boat(ModBoatEntities.MAHOGANY_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "mahogany"));
        event.registerEntityRenderer(boat(ModBoatEntities.MAHOGANY_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "mahogany"));
        event.registerEntityRenderer(boat(ModBoatEntities.MAPLE_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "maple"));
        event.registerEntityRenderer(boat(ModBoatEntities.MAPLE_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "maple"));
        event.registerEntityRenderer(boat(ModBoatEntities.MYRRH_BOAT.get()),                 ctx -> new BoatRenderer(ctx, false, "myrrh"));
        event.registerEntityRenderer(boat(ModBoatEntities.MYRRH_CHEST_BOAT.get()),           ctx -> new BoatRenderer(ctx, true,  "myrrh"));
        event.registerEntityRenderer(boat(ModBoatEntities.REDWOOD_BOAT.get()),               ctx -> new BoatRenderer(ctx, false, "redwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.REDWOOD_CHEST_BOAT.get()),         ctx -> new BoatRenderer(ctx, true,  "redwood"));
        event.registerEntityRenderer(boat(ModBoatEntities.CHESTNUT_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "chestnut"));
        event.registerEntityRenderer(boat(ModBoatEntities.CHESTNUT_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "chestnut"));
        event.registerEntityRenderer(boat(ModBoatEntities.WILLOW_BOAT.get()),                ctx -> new BoatRenderer(ctx, false, "willow"));
        event.registerEntityRenderer(boat(ModBoatEntities.WILLOW_CHEST_BOAT.get()),          ctx -> new BoatRenderer(ctx, true,  "willow"));
        event.registerEntityRenderer(boat(ModBoatEntities.WORMTREE_BOAT.get()),              ctx -> new BoatRenderer(ctx, false, "wormtree"));
        event.registerEntityRenderer(boat(ModBoatEntities.WORMTREE_CHEST_BOAT.get()),        ctx -> new BoatRenderer(ctx, true,  "wormtree"));
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        event.registerBlockEntityRenderer(ModBlockEntities.BELLOWS.get(), BellowsBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SMITHING_ANVIL.get(), SmithingAnvilBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.WEIRWOOD_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ASPEN_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.NIGHTWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PURPLEHEART_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TIGERWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SANDALWOOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SANDBEGGAR_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.APRICOT_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLACKTHORN_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.RED_CHERRY_SIGN.get(),   SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WHITE_CHERRY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLACK_CHERRY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CRABAPPLE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DATE_PALM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIG_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LEMON_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LIME_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.OLIVE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ORANGE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PEACH_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PEAR_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PERSIMMON_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PINK_IVORY_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PLUM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.POMEGRANATE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PRUNE_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ALMOND_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.NUTMEG_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HEMLOCK_SIGN.get(), SignRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.ALDER_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PINE_SIGN.get(),               SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIR_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SENTINAL_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.IRONWOOD_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BEECH_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SOLDIER_PINE_SIGN.get(),       SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ASH_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.HAWTHORN_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLACKBARK_SIGN.get(),          SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLOODWOOD_SIGN.get(),          SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLUE_MAHOE_SIGN.get(),         SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.COTTONWOOD_SIGN.get(),         SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLACK_COTTONWOOD_SIGN.get(),   SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CINNAMON_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CLOVE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.EBONY_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ELM_SIGN.get(),                SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CEDAR_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.APPLE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GOLDENHEART_SIGN.get(),        SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LINDEN_SIGN.get(),             SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MAHOGANY_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MAPLE_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MYRRH_SIGN.get(),              SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.REDWOOD_SIGN.get(),            SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CHESTNUT_SIGN.get(),           SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WILLOW_SIGN.get(),             SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WORMTREE_SIGN.get(),           SignRenderer::new);
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        HotIronIngotModel.inject(event.getBakingResult().itemStackModels());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> level != null && pos != null
                        ? net.minecraft.client.renderer.BiomeColors.getAverageGrassColor(level, pos)
                        : 0x91BD59,
                ModBlocks.GRASS_BLOCK_SLAB.get(),
                ModBlocks.GRASS_BLOCK_STAIRS.get()
        );
    }

}