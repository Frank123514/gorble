package net.got.entity;

import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
// ── Levy ──────────────────────────────────────────────────────────────────────
import net.got.entity.npc.levy.stark.StarkLevyEntity;
import net.got.entity.npc.levy.tully.TullyLevyEntity;
import net.got.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.entity.npc.levy.martell.MartellLevyEntity;
import net.got.entity.npc.levy.tyrell.TyrellLevyEntity;
// ── Skilled Fighters ──────────────────────────────────────────────────────────
import net.got.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.entity.npc.fighter.vale.ValeKnightEntity;
import net.got.init.GotModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

/**
 * MOD-bus event subscriber for entity attributes and spawn placements.
 *
 * <p>When adding a new culture (e.g. Reachmen), append entries for each
 * new entity type to both event handlers below.
 */
@EventBusSubscriber(modid = "got")
public class GotEntityEvents {

    // ── Attributes ─────────────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        // ── Northmen ───────────────────────────────────────────────────────────
        event.put(GotModEntities.NORTHMAN.get(),      NorthmanEntity.createAttributes().build());

        // ── Levies (Tier 2) ────────────────────────────────────────────────────
        event.put(GotModEntities.STARK_LEVY.get(),     StarkLevyEntity.createAttributes().build());
        event.put(GotModEntities.TULLY_LEVY.get(),     TullyLevyEntity.createAttributes().build());
        event.put(GotModEntities.LANNISTER_LEVY.get(), LannisterLevyEntity.createAttributes().build());
        event.put(GotModEntities.BARATHEON_LEVY.get(), BaratheonLevyEntity.createAttributes().build());
        event.put(GotModEntities.GREYJOY_LEVY.get(),   GreyjoyLevyEntity.createAttributes().build());
        event.put(GotModEntities.MARTELL_LEVY.get(),   MartellLevyEntity.createAttributes().build());
        event.put(GotModEntities.TYRELL_LEVY.get(),    TyrellLevyEntity.createAttributes().build());

        // ── Skilled Fighters (Tier 3) ──────────────────────────────────────────
        event.put(GotModEntities.NORTH_SOLDIER.get(), NorthSoldierEntity.createAttributes().build());
        event.put(GotModEntities.VALE_KNIGHT.get(),   ValeKnightEntity.createAttributes().build());
    }

    // ── Spawn placements ───────────────────────────────────────────────────────

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        // ── Northmen ───────────────────────────────────────────────────────────
        event.register(
                GotModEntities.NORTHMAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NorthmanEntity::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );


        // ── Levies (Tier 2) ────────────────────────────────────────────────────
        event.register(GotModEntities.STARK_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                StarkLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.TULLY_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TullyLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.LANNISTER_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LannisterLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.BARATHEON_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BaratheonLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.GREYJOY_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GreyjoyLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.MARTELL_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MartellLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.TYRELL_LEVY.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TyrellLevyEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);

        // ── Skilled Fighters (Tier 3) ──────────────────────────────────────────
        event.register(GotModEntities.NORTH_SOLDIER.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NorthSoldierEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GotModEntities.VALE_KNIGHT.get(),
                SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ValeKnightEntity::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}