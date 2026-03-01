package net.got.init;

import net.got.GotMod;
import net.got.entity.NorthBowmanEntity;
import net.got.entity.NorthmanEntity;
import net.got.entity.NorthWarriorEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.ENTITY_TYPE, GotMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<NorthBowmanEntity>> NORTH_BOWMAN =
            REGISTRY.register("north_bowman", () ->
                    EntityType.Builder.<NorthBowmanEntity>of(NorthBowmanEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.99f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "north_bowman"))));

    public static final DeferredHolder<EntityType<?>, EntityType<NorthWarriorEntity>> NORTH_WARRIOR =
            REGISTRY.register("north_warrior", () ->
                    EntityType.Builder.<NorthWarriorEntity>of(NorthWarriorEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.99f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "north_warrior"))));

    public static final DeferredHolder<EntityType<?>, EntityType<NorthmanEntity>> NORTHMAN =
            REGISTRY.register("northman", () ->
                    EntityType.Builder.<NorthmanEntity>of(NorthmanEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.99f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "northman"))));
}