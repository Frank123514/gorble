package net.got.entity;

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
        event.put(GotModEntities.NORTH_BOWMAN.get(),  NorthBowmanEntity.createAttributes().build());
        event.put(GotModEntities.NORTH_WARRIOR.get(), NorthWarriorEntity.createAttributes().build());
        event.put(GotModEntities.NORTHMAN.get(),      NorthmanEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
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
        event.register(
                GotModEntities.NORTHMAN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NorthmanEntity::checkSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }
}