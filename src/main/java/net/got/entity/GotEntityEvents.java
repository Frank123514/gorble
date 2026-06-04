package net.got.entity;

// ── Tier-1 Smallfolk ──────────────────────────────────────────────────────────
import net.got.entity.brownbear.GotBrownBearEntity;
import net.got.entity.stag.GotStagEntity;
import net.got.entity.heron.GotHeronEntity;
import net.got.entity.npc.smallfolk.NorthmanEntity;
import net.got.entity.npc.smallfolk.RiverlanderEntity;
import net.got.entity.npc.smallfolk.ValemanEntity;
import net.got.entity.npc.smallfolk.WestermanEntity;
import net.got.entity.npc.smallfolk.StormlorderEntity;
import net.got.entity.npc.smallfolk.IronbornEntity;
import net.got.entity.npc.smallfolk.DornishmanEntity;
import net.got.entity.npc.smallfolk.ReachmanEntity;
// ── Levy ──────────────────────────────────────────────────────────────────────
import net.got.entity.npc.levy.stark.StarkLevyEntity;
import net.got.entity.npc.levy.tully.TullyLevyEntity;
import net.got.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.entity.npc.levy.martell.MartellLevyEntity;
import net.got.entity.npc.levy.tyrell.TyrellLevyEntity;
import net.got.entity.npc.levy.arryn.ArrynLevyEntity;
// ── Skilled Fighters ──────────────────────────────────────────────────────────
import net.got.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.entity.npc.fighter.vale.ValeKnightEntity;
import net.got.entity.direwolf.GotDirewolfEntity;
import net.got.entity.crow.GotCrowEntity;
import net.got.entity.mammoth.GotMammothEntity;
import net.got.init.GotModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = "got")
public class GotEntityEvents {

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        // ── Tier-1 Smallfolk ──────────────────────────────────────────────────
        event.put(GotModEntities.NORTHMAN.get(),    NorthmanEntity.createAttributes().build());
        event.put(GotModEntities.RIVERLANDER.get(), RiverlanderEntity.createAttributes().build());
        event.put(GotModEntities.VALEMAN.get(),     ValemanEntity.createAttributes().build());
        event.put(GotModEntities.WESTERMAN.get(),   WestermanEntity.createAttributes().build());
        event.put(GotModEntities.STORMLORDER.get(), StormlorderEntity.createAttributes().build());
        event.put(GotModEntities.IRONBORN.get(),    IronbornEntity.createAttributes().build());
        event.put(GotModEntities.DORNISHMAN.get(),  DornishmanEntity.createAttributes().build());
        event.put(GotModEntities.REACHMAN.get(),    ReachmanEntity.createAttributes().build());

        // ── Levies (Tier 2) ────────────────────────────────────────────────────
        event.put(GotModEntities.STARK_LEVY.get(),     StarkLevyEntity.createAttributes().build());
        event.put(GotModEntities.TULLY_LEVY.get(),     TullyLevyEntity.createAttributes().build());
        event.put(GotModEntities.LANNISTER_LEVY.get(), LannisterLevyEntity.createAttributes().build());
        event.put(GotModEntities.BARATHEON_LEVY.get(), BaratheonLevyEntity.createAttributes().build());
        event.put(GotModEntities.GREYJOY_LEVY.get(),   GreyjoyLevyEntity.createAttributes().build());
        event.put(GotModEntities.MARTELL_LEVY.get(),   MartellLevyEntity.createAttributes().build());
        event.put(GotModEntities.TYRELL_LEVY.get(),    TyrellLevyEntity.createAttributes().build());
        event.put(GotModEntities.ARRYN_LEVY.get(),     ArrynLevyEntity.createAttributes().build());

        // ── Skilled Fighters (Tier 3) ──────────────────────────────────────────
        event.put(GotModEntities.NORTH_SOLDIER.get(), NorthSoldierEntity.createAttributes().build());
        event.put(GotModEntities.VALE_KNIGHT.get(),   ValeKnightEntity.createAttributes().build());

        // ── GOT Stag ───────────────────────────────────────────────────────────
        event.put(GotModEntities.GOT_STAG.get(), GotStagEntity.createAttributes().build());

        // ── GOT Heron ──────────────────────────────────────────────────────────
        event.put(GotModEntities.GOT_HERON.get(), GotHeronEntity.createAttributes().build());

        // ── GOT Direwolf ───────────────────────────────────────────────────────
        event.put(GotModEntities.GOT_DIREWOLF.get(), GotDirewolfEntity.createAttributes().build());

        // ── GOT Crow ───────────────────────────────────────────────────────────
        event.put(GotModEntities.GOT_CROW.get(), GotCrowEntity.createAttributes().build());

        // ── GOT Mammoth ────────────────────────────────────────────────────────
        event.put(GotModEntities.GOT_MAMMOTH.get(), GotMammothEntity.createAttributes().build());

        event.put(GotModEntities.GOT_BROWN_BEAR.get(), GotBrownBearEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // ── Tier-1 Smallfolk ──────────────────────────────────────────────────
        event.register(GotModEntities.NORTHMAN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NorthmanEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.RIVERLANDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RiverlanderEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.VALEMAN.get(),     SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ValemanEntity::checkSpawnRules,     RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.WESTERMAN.get(),   SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WestermanEntity::checkSpawnRules,   RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.STORMLORDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StormlorderEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.IRONBORN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IronbornEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.DORNISHMAN.get(),  SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DornishmanEntity::checkSpawnRules,  RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.REACHMAN.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ReachmanEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── Levies (Tier 2) ────────────────────────────────────────────────────
        event.register(GotModEntities.STARK_LEVY.get(),     SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarkLevyEntity::checkSpawnRules,     RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.TULLY_LEVY.get(),     SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TullyLevyEntity::checkSpawnRules,     RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.LANNISTER_LEVY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LannisterLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.BARATHEON_LEVY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaratheonLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.GREYJOY_LEVY.get(),   SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GreyjoyLevyEntity::checkSpawnRules,   RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.MARTELL_LEVY.get(),   SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MartellLevyEntity::checkSpawnRules,   RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.TYRELL_LEVY.get(),    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TyrellLevyEntity::checkSpawnRules,    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.ARRYN_LEVY.get(),     SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ArrynLevyEntity::checkSpawnRules,     RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── Skilled Fighters (Tier 3) ──────────────────────────────────────────
        event.register(GotModEntities.NORTH_SOLDIER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NorthSoldierEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.VALE_KNIGHT.get(),   SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ValeKnightEntity::checkSpawnRules,   RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── GOT Stag ───────────────────────────────────────────────────────────
        event.register(GotModEntities.GOT_STAG.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotStagEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── GOT Heron ──────────────────────────────────────────────────────────
        event.register(GotModEntities.GOT_HERON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotHeronEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── GOT Direwolf ───────────────────────────────────────────────────────
        event.register(GotModEntities.GOT_DIREWOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotDirewolfEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── GOT Crow ───────────────────────────────────────────────────────────
        event.register(GotModEntities.GOT_CROW.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotCrowEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── GOT Mammoth ────────────────────────────────────────────────────────
        event.register(GotModEntities.GOT_MAMMOTH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotMammothEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(GotModEntities.GOT_BROWN_BEAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GotBrownBearEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}