package net.got.init;

import net.got.GotMod;
// ── Tier-1 Smallfolk imports ───────────────────────────────────────────────────
import net.got.event.entity.brownbear.BrownBearEntity;
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
// ── Levy imports ──────────────────────────────────────────────────────────────
import net.got.event.entity.npc.levy.stark.StarkLevyEntity;
import net.got.event.entity.npc.levy.tully.TullyLevyEntity;
import net.got.event.entity.npc.levy.lannister.LannisterLevyEntity;
import net.got.event.entity.npc.levy.baratheon.BaratheonLevyEntity;
import net.got.event.entity.npc.levy.greyjoy.GreyjoyLevyEntity;
import net.got.event.entity.npc.levy.martell.MartellLevyEntity;
import net.got.event.entity.npc.levy.tyrell.TyrellLevyEntity;
import net.got.event.entity.npc.levy.arryn.ArrynLevyEntity;
// ── Skilled Fighter imports ───────────────────────────────────────────────────
import net.got.event.entity.npc.fighter.north.NorthSoldierEntity;
import net.got.event.entity.npc.fighter.vale.ValeKnightEntity;
import net.got.event.entity.direwolf.DirewolfEntity;
import net.got.event.entity.crow.CrowEntity;
import net.got.event.entity.mammoth.MammothEntity;
import net.got.event.entity.giant.GiantEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central entity type registry for the GoT mod.
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.ENTITY_TYPE, GotMod.MODID);

    // ── Helper: standard humanoid size ────────────────────────────────────────

    private static <T extends net.got.event.entity.npc.smallfolk.SmallfolkEntity>
    DeferredHolder<EntityType<?>, EntityType<T>> smallfolk(
            String id, EntityType.EntityFactory<T> factory) {
        return REGISTRY.register(id, () ->
                EntityType.Builder.<T>of(factory, MobCategory.CREATURE)
                        .sized(0.6f, 1.8f)
                        .clientTrackingRange(8)
                        .updateInterval(3)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                Identifier.fromNamespaceAndPath(GotMod.MODID, id))));
    }

    // ── Tier-1 Smallfolk (civilians) ──────────────────────────────────────────

    public static final DeferredHolder<EntityType<?>, EntityType<NorthmanEntity>>    NORTHMAN    = smallfolk("northman",    NorthmanEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<RiverlanderEntity>> RIVERLANDER = smallfolk("riverlander", RiverlanderEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<ValemanEntity>>     VALEMAN     = smallfolk("valeman",     ValemanEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<WestermanEntity>>   WESTERMAN   = smallfolk("westerman",   WestermanEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<StormlorderEntity>> STORMLORDER = smallfolk("stormlorder", StormlorderEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<IronbornEntity>>    IRONBORN    = smallfolk("ironborn",    IronbornEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<DornishmanEntity>>  DORNISHMAN  = smallfolk("dornishman",  DornishmanEntity::new);
    public static final DeferredHolder<EntityType<?>, EntityType<ReachmanEntity>>    REACHMAN    = smallfolk("reachman",    ReachmanEntity::new);

    // ── Levy — Tier 2 (armed conscripts, one per major house) ─────────────────

    /** Stark Levy — armed conscript of House Stark (North). */
    public static final DeferredHolder<EntityType<?>, EntityType<StarkLevyEntity>> STARK_LEVY =
            REGISTRY.register("stark_levy", () ->
                    EntityType.Builder.<StarkLevyEntity>of(StarkLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "stark_levy"))));

    /** Tully Levy — armed conscript of House Tully (Riverlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<TullyLevyEntity>> TULLY_LEVY =
            REGISTRY.register("tully_levy", () ->
                    EntityType.Builder.<TullyLevyEntity>of(TullyLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "tully_levy"))));

    /** Lannister Levy — armed conscript of House Lannister (Westerlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<LannisterLevyEntity>> LANNISTER_LEVY =
            REGISTRY.register("lannister_levy", () ->
                    EntityType.Builder.<LannisterLevyEntity>of(LannisterLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "lannister_levy"))));

    /** Baratheon Levy — armed conscript of House Baratheon (Stormlands). */
    public static final DeferredHolder<EntityType<?>, EntityType<BaratheonLevyEntity>> BARATHEON_LEVY =
            REGISTRY.register("baratheon_levy", () ->
                    EntityType.Builder.<BaratheonLevyEntity>of(BaratheonLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "baratheon_levy"))));

    /** Greyjoy Levy — reaver conscript of House Greyjoy (Iron Islands). */
    public static final DeferredHolder<EntityType<?>, EntityType<GreyjoyLevyEntity>> GREYJOY_LEVY =
            REGISTRY.register("greyjoy_levy", () ->
                    EntityType.Builder.<GreyjoyLevyEntity>of(GreyjoyLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "greyjoy_levy"))));

    /** Martell Levy — armed conscript of House Martell (Dorne). */
    public static final DeferredHolder<EntityType<?>, EntityType<MartellLevyEntity>> MARTELL_LEVY =
            REGISTRY.register("martell_levy", () ->
                    EntityType.Builder.<MartellLevyEntity>of(MartellLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "martell_levy"))));

    /** Tyrell Levy — armed conscript of House Tyrell (The Reach). */
    public static final DeferredHolder<EntityType<?>, EntityType<TyrellLevyEntity>> TYRELL_LEVY =
            REGISTRY.register("tyrell_levy", () ->
                    EntityType.Builder.<TyrellLevyEntity>of(TyrellLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "tyrell_levy"))));

    /** Arryn Levy — armed conscript of House Arryn (The Vale). */
    public static final DeferredHolder<EntityType<?>, EntityType<ArrynLevyEntity>> ARRYN_LEVY =
            REGISTRY.register("arryn_levy", () ->
                    EntityType.Builder.<ArrynLevyEntity>of(ArrynLevyEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "arryn_levy"))));

    // ── Skilled Fighters — Tier 3 ─────────────────────────────────────────────

    /** North Soldier — professional infantryman of the North (15% horse chance). */
    public static final DeferredHolder<EntityType<?>, EntityType<NorthSoldierEntity>> NORTH_SOLDIER =
            REGISTRY.register("north_soldier", () ->
                    EntityType.Builder.<NorthSoldierEntity>of(NorthSoldierEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "north_soldier"))));

    /** Vale Knight — elite armoured knight of the Vale (50% horse chance). */
    public static final DeferredHolder<EntityType<?>, EntityType<ValeKnightEntity>> VALE_KNIGHT =
            REGISTRY.register("vale_knight", () ->
                    EntityType.Builder.<ValeKnightEntity>of(ValeKnightEntity::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.8f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "vale_knight"))));


    /** GOT Stag — a wild red deer stag found in the forests of Westeros. */
    public static final DeferredHolder<EntityType<?>, EntityType<StagEntity>> GOT_STAG =
            REGISTRY.register("got_stag", () ->
                    EntityType.Builder.<StagEntity>of(StagEntity::new, MobCategory.CREATURE)
                            .sized(1.4f, 1.6f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_stag"))));

    /** GOT Heron — a wading bird found near rivers and beaches. */
    public static final DeferredHolder<EntityType<?>, EntityType<HeronEntity>> GOT_HERON =
            REGISTRY.register("got_heron", () ->
                    EntityType.Builder.<HeronEntity>of(HeronEntity::new, MobCategory.CREATURE)
                            .sized(0.5f, 1.4f).clientTrackingRange(10).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_heron"))));

    /** GOT Direwolf — the great wolves of the North, symbol of House Stark. */
    public static final DeferredHolder<EntityType<?>, EntityType<DirewolfEntity>> GOT_DIREWOLF =
            REGISTRY.register("got_direwolf", () ->
                    EntityType.Builder.<DirewolfEntity>of(DirewolfEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.2f).clientTrackingRange(12).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_direwolf"))));

    /** GOT Crow — the ravens and crows of Westeros. */
    public static final DeferredHolder<EntityType<?>, EntityType<CrowEntity>> GOT_CROW =
            REGISTRY.register("got_crow", () ->
                    EntityType.Builder.<CrowEntity>of(CrowEntity::new, MobCategory.CREATURE)
                            .sized(0.4f, 0.6f).clientTrackingRange(8).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_crow"))));

    /** GOT Mammoth — the great shaggy mammoths of the lands Beyond the Wall.
     *  Hitbox scaled up significantly so the collision box matches the larger visual. */
    public static final DeferredHolder<EntityType<?>, EntityType<MammothEntity>> GOT_MAMMOTH =
            REGISTRY.register("got_mammoth", () ->
                    EntityType.Builder.<MammothEntity>of(MammothEntity::new, MobCategory.CREATURE)
                            .sized(4.5f, 5.0f).clientTrackingRange(20).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_mammoth"))));

    /** GOT Brown Bear — a large bear found in the forests of Westeros. */
    public static final DeferredHolder<EntityType<?>, EntityType<BrownBearEntity>> GOT_BROWN_BEAR =
            REGISTRY.register("got_brown_bear", () ->
                    EntityType.Builder.<BrownBearEntity>of(BrownBearEntity::new, MobCategory.CREATURE)
                            .sized(1.4f, 1.4f).clientTrackingRange(12).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_brown_bear"))));

    /** GOT Giant — a towering humanoid creature that roams the frozen lands Beyond the Wall. */
    public static final DeferredHolder<EntityType<?>, EntityType<GiantEntity>> GOT_GIANT =
            REGISTRY.register("got_giant", () ->
                    EntityType.Builder.<GiantEntity>of(GiantEntity::new, MobCategory.MONSTER)
                            .sized(2.0f, 5.0f).clientTrackingRange(20).updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(GotMod.MODID, "got_giant"))));
}