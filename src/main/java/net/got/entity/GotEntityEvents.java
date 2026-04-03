package net.got.entity;

import net.got.entity.npc.smallfolk.northmen.NorthBowmanEntity;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
import net.got.entity.npc.smallfolk.northmen.NorthWarriorEntity;
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
        event.put(GotModEntities.NORTH_BOWMAN.get(),  NorthBowmanEntity.createAttributes().build());
        event.put(GotModEntities.NORTH_WARRIOR.get(), NorthWarriorEntity.createAttributes().build());

        // ── Reachmen (future) ──────────────────────────────────────────────────
        // event.put(GotModEntities.REACH_PEASANT.get(), ReachPeasantEntity.createAttributes().build());
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
        event.register(
                GotModEntities.NORTH_BOWMAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NorthBowmanEntity::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                GotModEntities.NORTH_WARRIOR.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NorthWarriorEntity::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        // ── Reachmen (future) ──────────────────────────────────────────────────
        // event.register(GotModEntities.REACH_PEASANT.get(), ...);
    }
}