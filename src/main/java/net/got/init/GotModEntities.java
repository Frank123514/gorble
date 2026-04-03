package net.got.init;

import net.got.GotMod;
import net.got.entity.npc.smallfolk.northmen.NorthBowmanEntity;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
import net.got.entity.npc.smallfolk.northmen.NorthWarriorEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central entity type registry for the GoT mod.
 *
 * <p><b>Adding a new smallfolk culture</b> (e.g. Reachmen):
 * <ol>
 *   <li>Create entities in {@code net.got.entity.npc.smallfolk.reachmen}.</li>
 *   <li>Register them below in the {@code // ── Reachmen ──} block.</li>
 *   <li>Register attributes + spawn placements in {@link net.got.entity.GotEntityEvents}.</li>
 *   <li>Register renderers in {@link net.got.client.ClientSetup}.</li>
 * </ol>
 */
public class GotModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.ENTITY_TYPE, GotMod.MODID);

    // ── Northmen ───────────────────────────────────────────────────────────────

    /** Civilian northman smallfolk (8 variants: 4 male, 4 female). */
    public static final DeferredHolder<EntityType<?>, EntityType<NorthmanEntity>> NORTHMAN =
            REGISTRY.register("northman", () ->
                    EntityType.Builder.<NorthmanEntity>of(NorthmanEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "northman"))));

    /** Ranged bowman of the North (4 variants: 2 male, 2 female). */
    public static final DeferredHolder<EntityType<?>, EntityType<NorthBowmanEntity>> NORTH_BOWMAN =
            REGISTRY.register("north_bowman", () ->
                    EntityType.Builder.<NorthBowmanEntity>of(NorthBowmanEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "north_bowman"))));

    /** Heavily-armed melee warrior of the North (4 variants: 2 male, 2 female). */
    public static final DeferredHolder<EntityType<?>, EntityType<NorthWarriorEntity>> NORTH_WARRIOR =
            REGISTRY.register("north_warrior", () ->
                    EntityType.Builder.<NorthWarriorEntity>of(NorthWarriorEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "north_warrior"))));

    // ── Reachmen (future) ──────────────────────────────────────────────────────
    // Add REACH_PEASANT, REACH_KNIGHT, etc. here following the same pattern.

    // ── Valemen (future) ───────────────────────────────────────────────────────
    // Add VALE_PEASANT, VALE_KNIGHT, etc. here following the same pattern.
}