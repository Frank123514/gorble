package net.got.event.entity;

import net.got.event.entity.brownbear.BrownBearEntity;
import net.got.event.entity.giant.GiantEntity;
import net.got.event.entity.stag.StagEntity;
import net.got.event.entity.heron.HeronEntity;
import net.got.event.entity.npc.smallfolk.NorthmanEntity;
import net.got.event.entity.npc.smallfolk.RiverlanderEntity;
import net.got.event.entity.npc.smallfolk.ValemanEntity;
import net.got.event.entity.npc.smallfolk.WestermanEntity;
import net.got.event.entity.npc.smallfolk.StormlorderEntity;
import net.got.event.entity.npc.smallfolk.IronbornEntity;
import net.got.event.entity.npc.smallfolk.DornishmanEntity;
import net.got.event.entity.npc.smallfolk.ReachmanEntity;

import net.got.event.entity.direwolf.DirewolfEntity;
import net.got.event.entity.crow.CrowEntity;
import net.got.event.entity.mammoth.MammothEntity;
import net.got.init.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = "got")
public class EntityEvents {

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        
        event.put(ModEntities.NORTHMAN.get(),    NorthmanEntity.createAttributes().build());
        event.put(ModEntities.RIVERLANDER.get(), RiverlanderEntity.createAttributes().build());
        event.put(ModEntities.VALEMAN.get(),     ValemanEntity.createAttributes().build());
        event.put(ModEntities.WESTERMAN.get(),   WestermanEntity.createAttributes().build());
        event.put(ModEntities.STORMLORDER.get(), StormlorderEntity.createAttributes().build());
        event.put(ModEntities.IRONBORN.get(),    IronbornEntity.createAttributes().build());
        event.put(ModEntities.DORNISHMAN.get(),  DornishmanEntity.createAttributes().build());
        event.put(ModEntities.REACHMAN.get(),    ReachmanEntity.createAttributes().build());

        event.put(ModEntities.GOT_STAG.get(), StagEntity.createAttributes().build());

        event.put(ModEntities.GOT_HERON.get(), HeronEntity.createAttributes().build());

        event.put(ModEntities.GOT_DIREWOLF.get(), DirewolfEntity.createAttributes().build());

        event.put(ModEntities.GOT_CROW.get(), CrowEntity.createAttributes().build());

        event.put(ModEntities.GOT_MAMMOTH.get(), MammothEntity.createAttributes().build());

        event.put(ModEntities.GOT_BROWN_BEAR.get(), BrownBearEntity.createAttributes().build());

        event.put(ModEntities.GOT_GIANT.get(), GiantEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        
        event.register(ModEntities.NORTHMAN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NorthmanEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.RIVERLANDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RiverlanderEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.VALEMAN.get(),     SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ValemanEntity::checkSpawnRules,     RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.WESTERMAN.get(),   SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WestermanEntity::checkSpawnRules,   RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.STORMLORDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StormlorderEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.IRONBORN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IronbornEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.DORNISHMAN.get(),  SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DornishmanEntity::checkSpawnRules,  RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.REACHMAN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ReachmanEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_STAG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StagEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_HERON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HeronEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_DIREWOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DirewolfEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_CROW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrowEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_MAMMOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MammothEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_BROWN_BEAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrownBearEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(ModEntities.GOT_GIANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GiantEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}