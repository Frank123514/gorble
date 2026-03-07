package net.got.init;

import net.got.GotMod;
import net.got.entity.GotBoat;
import net.got.entity.GotChestBoat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GotModBoatEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY =
            DeferredRegister.create(Registries.ENTITY_TYPE, GotMod.MODID);

    // ── Helper ────────────────────────────────────────────────────────────────

    private static DeferredHolder<EntityType<?>, EntityType<GotBoat>> boat(String name) {
        ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                "textures/entity/boat/" + name + ".png");
        return REGISTRY.register(name + "_boat", () ->
                EntityType.Builder.<GotBoat>of(
                                (type, level) -> new GotBoat(type, level, tex), MobCategory.MISC)
                        .sized(1.375f, 0.5625f)
                        .clientTrackingRange(10)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name + "_boat"))));
    }

    private static DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> chestBoat(String name) {
        ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                "textures/entity/boat/chest/" + name + ".png");
        return REGISTRY.register(name + "_chest_boat", () ->
                EntityType.Builder.<GotChestBoat>of(
                                (type, level) -> new GotChestBoat(type, level, tex), MobCategory.MISC)
                        .sized(1.375f, 0.5625f)
                        .clientTrackingRange(10)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name + "_chest_boat"))));
    }

    // ── Boat entity types ─────────────────────────────────────────────────────

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WEIRWOOD_BOAT            = boat("weirwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WEIRWOOD_CHEST_BOAT      = chestBoat("weirwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ASPEN_BOAT               = boat("aspen");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ASPEN_CHEST_BOAT         = chestBoat("aspen");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ALDER_BOAT               = boat("alder");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ALDER_CHEST_BOAT         = chestBoat("alder");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PINE_BOAT                = boat("pine");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PINE_CHEST_BOAT          = chestBoat("pine");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      FIR_BOAT                 = boat("fir");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> FIR_CHEST_BOAT           = chestBoat("fir");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SENTINAL_BOAT            = boat("sentinal");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SENTINAL_CHEST_BOAT      = chestBoat("sentinal");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      IRONWOOD_BOAT            = boat("ironwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> IRONWOOD_CHEST_BOAT      = chestBoat("ironwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BEECH_BOAT               = boat("beech");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BEECH_CHEST_BOAT         = chestBoat("beech");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SOLDIER_PINE_BOAT        = boat("soldier_pine");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SOLDIER_PINE_CHEST_BOAT  = chestBoat("soldier_pine");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ASH_BOAT                 = boat("ash");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ASH_CHEST_BOAT           = chestBoat("ash");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      HAWTHORN_BOAT            = boat("hawthorn");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> HAWTHORN_CHEST_BOAT      = chestBoat("hawthorn");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLACKBARK_BOAT           = boat("blackbark");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLACKBARK_CHEST_BOAT     = chestBoat("blackbark");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLOODWOOD_BOAT           = boat("bloodwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLOODWOOD_CHEST_BOAT     = chestBoat("bloodwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLUE_MAHOE_BOAT          = boat("blue_mahoe");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLUE_MAHOE_CHEST_BOAT    = chestBoat("blue_mahoe");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      COTTONWOOD_BOAT          = boat("cottonwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> COTTONWOOD_CHEST_BOAT    = chestBoat("cottonwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLACK_COTTONWOOD_BOAT        = boat("black_cottonwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLACK_COTTONWOOD_CHEST_BOAT  = chestBoat("black_cottonwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CINNAMON_BOAT            = boat("cinnamon");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CINNAMON_CHEST_BOAT      = chestBoat("cinnamon");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CLOVE_BOAT               = boat("clove");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CLOVE_CHEST_BOAT         = chestBoat("clove");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      EBONY_BOAT               = boat("ebony");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> EBONY_CHEST_BOAT         = chestBoat("ebony");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ELM_BOAT                 = boat("elm");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ELM_CHEST_BOAT           = chestBoat("elm");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CEDAR_BOAT               = boat("cedar");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CEDAR_CHEST_BOAT         = chestBoat("cedar");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      APPLE_BOAT               = boat("apple");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> APPLE_CHEST_BOAT         = chestBoat("apple");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      GOLDENHEART_BOAT         = boat("goldenheart");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> GOLDENHEART_CHEST_BOAT   = chestBoat("goldenheart");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      LINDEN_BOAT              = boat("linden");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> LINDEN_CHEST_BOAT        = chestBoat("linden");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MAHOGANY_BOAT            = boat("mahogany");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MAHOGANY_CHEST_BOAT      = chestBoat("mahogany");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MAPLE_BOAT               = boat("maple");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MAPLE_CHEST_BOAT         = chestBoat("maple");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MYRRH_BOAT               = boat("myrrh");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MYRRH_CHEST_BOAT         = chestBoat("myrrh");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      REDWOOD_BOAT             = boat("redwood");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> REDWOOD_CHEST_BOAT       = chestBoat("redwood");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CHESTNUT_BOAT            = boat("chestnut");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CHESTNUT_CHEST_BOAT      = chestBoat("chestnut");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WILLOW_BOAT              = boat("willow");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WILLOW_CHEST_BOAT        = chestBoat("willow");

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WORMTREE_BOAT            = boat("wormtree");
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WORMTREE_CHEST_BOAT      = chestBoat("wormtree");
}