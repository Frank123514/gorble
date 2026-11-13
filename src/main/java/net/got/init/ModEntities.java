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