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

    private static DeferredHolder<EntityType<?>, EntityType<GotBoat>> boat(String name,
            java.util.function.Supplier<net.minecraft.world.item.Item> dropItem) {
        ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                "textures/entity/boat/" + name + ".png");
        return REGISTRY.register(name + "_boat", () ->
                EntityType.Builder.<GotBoat>of(
                                (type, level) -> new GotBoat(type, level, tex, dropItem), MobCategory.MISC)
                        .sized(1.375f, 0.5625f)
                        .clientTrackingRange(10)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name + "_boat"))));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      NIGHTWOOD_BOAT       = boat("nightwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "nightwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> NIGHTWOOD_CHEST_BOAT = chestBoat("nightwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "nightwood_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PURPLEHEART_BOAT       = boat("purpleheart", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "purpleheart_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PURPLEHEART_CHEST_BOAT = chestBoat("purpleheart", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "purpleheart_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      TIGERWOOD_BOAT       = boat("tigerwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "tigerwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> TIGERWOOD_CHEST_BOAT = chestBoat("tigerwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "tigerwood_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BURL_BOAT       = boat("burl", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "burl_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BURL_CHEST_BOAT = chestBoat("burl", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "burl_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SANDALWOOD_BOAT       = boat("sandalwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sandalwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SANDALWOOD_CHEST_BOAT = chestBoat("sandalwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sandalwood_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SANDBEGGAR_BOAT       = boat("sandbeggar", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sandbeggar_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SANDBEGGAR_CHEST_BOAT = chestBoat("sandbeggar", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sandbeggar_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      APRICOT_BOAT       = boat("apricot", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "apricot_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> APRICOT_CHEST_BOAT = chestBoat("apricot", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "apricot_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLACKTHORN_BOAT       = boat("blackthorn", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blackthorn_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLACKTHORN_CHEST_BOAT = chestBoat("blackthorn", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blackthorn_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CHERRY_BOAT       = boat("cherry", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cherry_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CHERRY_CHEST_BOAT = chestBoat("cherry", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cherry_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CRABAPPLE_BOAT       = boat("crabapple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "crabapple_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CRABAPPLE_CHEST_BOAT = chestBoat("crabapple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "crabapple_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      DATE_PALM_BOAT       = boat("date_palm", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "date_palm_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> DATE_PALM_CHEST_BOAT = chestBoat("date_palm", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "date_palm_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      FIG_BOAT       = boat("fig", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "fig_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> FIG_CHEST_BOAT = chestBoat("fig", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "fig_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      LEMON_BOAT       = boat("lemon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "lemon_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> LEMON_CHEST_BOAT = chestBoat("lemon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "lemon_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      LIME_BOAT       = boat("lime", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "lime_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> LIME_CHEST_BOAT = chestBoat("lime", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "lime_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      OLIVE_BOAT       = boat("olive", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "olive_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> OLIVE_CHEST_BOAT = chestBoat("olive", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "olive_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ORANGE_BOAT       = boat("orange", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "orange_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ORANGE_CHEST_BOAT = chestBoat("orange", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "orange_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PEACH_BOAT       = boat("peach", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "peach_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PEACH_CHEST_BOAT = chestBoat("peach", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "peach_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PEAR_BOAT       = boat("pear", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pear_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PEAR_CHEST_BOAT = chestBoat("pear", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pear_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PERSIMMON_BOAT       = boat("persimmon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "persimmon_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PERSIMMON_CHEST_BOAT = chestBoat("persimmon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "persimmon_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PINK_IVORY_BOAT       = boat("pink_ivory", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pink_ivory_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PINK_IVORY_CHEST_BOAT = chestBoat("pink_ivory", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pink_ivory_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PLUM_BOAT       = boat("plum", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "plum_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PLUM_CHEST_BOAT = chestBoat("plum", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "plum_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      POMEGRANATE_BOAT       = boat("pomegranate", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pomegranate_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> POMEGRANATE_CHEST_BOAT = chestBoat("pomegranate", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pomegranate_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PRUNE_BOAT       = boat("prune", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "prune_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PRUNE_CHEST_BOAT = chestBoat("prune", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "prune_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ALMOND_BOAT       = boat("almond", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "almond_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ALMOND_CHEST_BOAT = chestBoat("almond", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "almond_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      NUTMEG_BOAT       = boat("nutmeg", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "nutmeg_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> NUTMEG_CHEST_BOAT = chestBoat("nutmeg", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "nutmeg_chest_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      HEMLOCK_BOAT       = boat("hemlock", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "hemlock_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> HEMLOCK_CHEST_BOAT = chestBoat("hemlock", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "hemlock_chest_boat")));

}

    private static DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> chestBoat(String name,
            java.util.function.Supplier<net.minecraft.world.item.Item> dropItem) {
        ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(GotMod.MODID,
                "textures/entity/chest_boat/" + name + ".png");
        return REGISTRY.register(name + "_chest_boat", () ->
                EntityType.Builder.<GotChestBoat>of(
                                (type, level) -> new GotChestBoat(type, level, tex, dropItem), MobCategory.MISC)
                        .sized(1.375f, 0.5625f)
                        .clientTrackingRange(10)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, name + "_chest_boat"))));
    }

    // ── Boat entity types ─────────────────────────────────────────────────────

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WEIRWOOD_BOAT            = boat("weirwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "weirwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WEIRWOOD_CHEST_BOAT      = chestBoat("weirwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "weirwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ASPEN_BOAT               = boat("aspen", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "aspen_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ASPEN_CHEST_BOAT         = chestBoat("aspen", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "aspen_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ALDER_BOAT               = boat("alder", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "alder_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ALDER_CHEST_BOAT         = chestBoat("alder", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "alder_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      PINE_BOAT                = boat("pine", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pine_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> PINE_CHEST_BOAT          = chestBoat("pine", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "pine_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      FIR_BOAT                 = boat("fir", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "fir_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> FIR_CHEST_BOAT           = chestBoat("fir", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "fir_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SENTINAL_BOAT            = boat("sentinal", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sentinal_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SENTINAL_CHEST_BOAT      = chestBoat("sentinal", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "sentinal_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      IRONWOOD_BOAT            = boat("ironwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ironwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> IRONWOOD_CHEST_BOAT      = chestBoat("ironwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ironwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BEECH_BOAT               = boat("beech", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "beech_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BEECH_CHEST_BOAT         = chestBoat("beech", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "beech_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      SOLDIER_PINE_BOAT        = boat("soldier_pine", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "soldier_pine_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> SOLDIER_PINE_CHEST_BOAT  = chestBoat("soldier_pine", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "soldier_pine_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ASH_BOAT                 = boat("ash", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ash_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ASH_CHEST_BOAT           = chestBoat("ash", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ash_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      HAWTHORN_BOAT            = boat("hawthorn", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "hawthorn_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> HAWTHORN_CHEST_BOAT      = chestBoat("hawthorn", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "hawthorn_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLACKBARK_BOAT           = boat("blackbark", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blackbark_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLACKBARK_CHEST_BOAT     = chestBoat("blackbark", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blackbark_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLOODWOOD_BOAT           = boat("bloodwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "bloodwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLOODWOOD_CHEST_BOAT     = chestBoat("bloodwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "bloodwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLUE_MAHOE_BOAT          = boat("blue_mahoe", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blue_mahoe_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLUE_MAHOE_CHEST_BOAT    = chestBoat("blue_mahoe", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "blue_mahoe_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      COTTONWOOD_BOAT          = boat("cottonwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cottonwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> COTTONWOOD_CHEST_BOAT    = chestBoat("cottonwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cottonwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      BLACK_COTTONWOOD_BOAT        = boat("black_cottonwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "black_cottonwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> BLACK_COTTONWOOD_CHEST_BOAT  = chestBoat("black_cottonwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "black_cottonwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CINNAMON_BOAT            = boat("cinnamon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cinnamon_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CINNAMON_CHEST_BOAT      = chestBoat("cinnamon", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cinnamon_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CLOVE_BOAT               = boat("clove", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "clove_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CLOVE_CHEST_BOAT         = chestBoat("clove", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "clove_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      EBONY_BOAT               = boat("ebony", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ebony_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> EBONY_CHEST_BOAT         = chestBoat("ebony", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "ebony_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      ELM_BOAT                 = boat("elm", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "elm_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> ELM_CHEST_BOAT           = chestBoat("elm", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "elm_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CEDAR_BOAT               = boat("cedar", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cedar_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CEDAR_CHEST_BOAT         = chestBoat("cedar", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "cedar_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      APPLE_BOAT               = boat("apple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "apple_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> APPLE_CHEST_BOAT         = chestBoat("apple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "apple_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      GOLDENHEART_BOAT         = boat("goldenheart", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "goldenheart_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> GOLDENHEART_CHEST_BOAT   = chestBoat("goldenheart", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "goldenheart_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      LINDEN_BOAT              = boat("linden", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "linden_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> LINDEN_CHEST_BOAT        = chestBoat("linden", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "linden_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MAHOGANY_BOAT            = boat("mahogany", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "mahogany_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MAHOGANY_CHEST_BOAT      = chestBoat("mahogany", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "mahogany_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MAPLE_BOAT               = boat("maple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "maple_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MAPLE_CHEST_BOAT         = chestBoat("maple", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "maple_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      MYRRH_BOAT               = boat("myrrh", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "myrrh_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> MYRRH_CHEST_BOAT         = chestBoat("myrrh", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "myrrh_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      REDWOOD_BOAT             = boat("redwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "redwood_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> REDWOOD_CHEST_BOAT       = chestBoat("redwood", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "redwood_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      CHESTNUT_BOAT            = boat("chestnut", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "chestnut_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> CHESTNUT_CHEST_BOAT      = chestBoat("chestnut", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "chestnut_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WILLOW_BOAT              = boat("willow", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "willow_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WILLOW_CHEST_BOAT        = chestBoat("willow", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "willow_chest_boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<GotBoat>>      WORMTREE_BOAT            = boat("wormtree", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "wormtree_boat")));
    public static final DeferredHolder<EntityType<?>, EntityType<GotChestBoat>> WORMTREE_CHEST_BOAT      = chestBoat("wormtree", () -> net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(GotMod.MODID, "wormtree_chest_boat")));
}