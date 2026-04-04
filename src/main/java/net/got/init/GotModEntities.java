package net.got.init;

import net.got.GotMod;
import net.got.entity.npc.smallfolk.northmen.NorthmanEntity;
// ── Levy imports ──────────────────────────────────────────────────────────────
import net.got.entity.npc.levy.stark.StarkLevyEntity;
import net.got.entity.npc.levy.tully.TullyLevyEntity;
import net.got.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.entity.npc.levy.martell.MartellLevyEntity;
import net.got.entity.npc.levy.tyrell.TyrellLevyEntity;
// ── Skilled Fighter imports ───────────────────────────────────────────────────
import net.got.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.entity.npc.fighter.vale.ValeKnightEntity;
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

    // ── Levy — Tier 2 (armed conscripts, one per major house) ─────────────────

    /** Stark Levy — armed conscript of House Stark (North). */
    public static final DeferredHolder<EntityType<?>, EntityType<StarkLevyEntity>> STARK_LEVY =
            REGISTRY.register("stark_levy", () ->
                    EntityType.Builder.<StarkLevyEntity>of(StarkLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "stark_levy"))));

    /** Tully Levy — armed conscript of House Tully (Riverlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<TullyLevyEntity>> TULLY_LEVY =
            REGISTRY.register("tully_levy", () ->
                    EntityType.Builder.<TullyLevyEntity>of(TullyLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "tully_levy"))));

    /** Lannister Levy — armed conscript of House Lannister (Westerlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<LannisterLevyEntity>> LANNISTER_LEVY =
            REGISTRY.register("lannister_levy", () ->
                    EntityType.Builder.<LannisterLevyEntity>of(LannisterLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "lannister_levy"))));

    /** Baratheon Levy — armed conscript of House Baratheon (Stormlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<BaratheonLevyEntity>> BARATHEON_LEVY =
            REGISTRY.register("baratheon_levy", () ->
                    EntityType.Builder.<BaratheonLevyEntity>of(BaratheonLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "baratheon_levy"))));

    /** Greyjoy Levy — reaver conscript of House Greyjoy (Iron Islands). */
    public static final DeferredHolder<EntityType<?>, EntityType<GreyjoyLevyEntity>> GREYJOY_LEVY =
            REGISTRY.register("greyjoy_levy", () ->
                    EntityType.Builder.<GreyjoyLevyEntity>of(GreyjoyLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "greyjoy_levy"))));

    /** Martell Levy — armed conscript of House Martell (Dorne). */
    public static final DeferredHolder<EntityType<?>, EntityType<MartellLevyEntity>> MARTELL_LEVY =
            REGISTRY.register("martell_levy", () ->
                    EntityType.Builder.<MartellLevyEntity>of(MartellLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "martell_levy"))));

    /** Tyrell Levy — armed conscript of House Tyrell (The Reach). */
    public static final DeferredHolder<EntityType<?>, EntityType<TyrellLevyEntity>> TYRELL_LEVY =
            REGISTRY.register("tyrell_levy", () ->
                    EntityType.Builder.<TyrellLevyEntity>of(TyrellLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "tyrell_levy"))));

    // ── Skilled Fighters — Tier 3 (professional soldiers, may spawn mounted) ──

    /** North Soldier — professional infantryman of the North (15% horse chance). */
    public static final DeferredHolder<EntityType<?>, EntityType<NorthSoldierEntity>> NORTH_SOLDIER =
            REGISTRY.register("north_soldier", () ->
                    EntityType.Builder.<NorthSoldierEntity>of(NorthSoldierEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "north_soldier"))));

    /** Vale Knight — elite armoured knight of the Vale (50% horse chance). */
    public static final DeferredHolder<EntityType<?>, EntityType<ValeKnightEntity>> VALE_KNIGHT =
            REGISTRY.register("vale_knight", () ->
                    EntityType.Builder.<ValeKnightEntity>of(ValeKnightEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "vale_knight"))));
}