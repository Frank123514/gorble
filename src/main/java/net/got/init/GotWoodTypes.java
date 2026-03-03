package net.got.init;

import net.got.GotMod;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.sounds.SoundEvents;

/**
 * Registers a {@link WoodType} and {@link BlockSetType} for every GOT wood species.
 * WoodType carries the sound events used by fence gates, signs, hanging signs, etc.
 * BlockSetType carries sounds for buttons, pressure plates, doors, and trapdoors.
 *
 * Call {@link #init()} once during mod construction so the static fields are populated
 * before any block instances are created.
 */
public final class GotWoodTypes {

    private GotWoodTypes() {}

    // ── Original trees ────────────────────────────────────────────────────
    public static final BlockSetType WEIRWOOD_SET    = registerSet("weirwood");
    public static final WoodType     WEIRWOOD        = register("weirwood",      WEIRWOOD_SET);

    public static final BlockSetType ASPEN_SET       = registerSet("aspen");
    public static final WoodType     ASPEN           = register("aspen",         ASPEN_SET);

    public static final BlockSetType ALDER_SET       = registerSet("alder");
    public static final WoodType     ALDER           = register("alder",         ALDER_SET);

    public static final BlockSetType PINE_SET        = registerSet("pine");
    public static final WoodType     PINE            = register("pine",          PINE_SET);

    public static final BlockSetType FIR_SET         = registerSet("fir");
    public static final WoodType     FIR             = register("fir",           FIR_SET);

    public static final BlockSetType SENTINAL_SET    = registerSet("sentinal");
    public static final WoodType     SENTINAL        = register("sentinal",      SENTINAL_SET);

    public static final BlockSetType IRONWOOD_SET    = registerSet("ironwood");
    public static final WoodType     IRONWOOD        = register("ironwood",      IRONWOOD_SET);

    public static final BlockSetType HAWTHORN_SET    = registerSet("hawthorn");
    public static final WoodType     HAWTHORN        = register("hawthorn",      HAWTHORN_SET);

    public static final BlockSetType BEECH_SET       = registerSet("beech");
    public static final WoodType     BEECH           = register("beech",         BEECH_SET);

    public static final BlockSetType SOLDIER_PINE_SET = registerSet("soldier_pine");
    public static final WoodType     SOLDIER_PINE    = register("soldier_pine",  SOLDIER_PINE_SET);

    public static final BlockSetType ASH_SET         = registerSet("ash");
    public static final WoodType     ASH             = register("ash",           ASH_SET);

    // ── Batch 1 ───────────────────────────────────────────────────────────
    public static final BlockSetType BLACKBARK_SET   = registerSet("blackbark");
    public static final WoodType     BLACKBARK       = register("blackbark",     BLACKBARK_SET);

    public static final BlockSetType BLOODWOOD_SET   = registerSet("bloodwood");
    public static final WoodType     BLOODWOOD       = register("bloodwood",     BLOODWOOD_SET);

    public static final BlockSetType BLUE_MAHOE_SET  = registerSet("blue_mahoe");
    public static final WoodType     BLUE_MAHOE      = register("blue_mahoe",    BLUE_MAHOE_SET);

    public static final BlockSetType COTTONWOOD_SET  = registerSet("cottonwood");
    public static final WoodType     COTTONWOOD      = register("cottonwood",    COTTONWOOD_SET);

    // ── Batch 2 ───────────────────────────────────────────────────────────
    public static final BlockSetType BLACK_COTTONWOOD_SET = registerSet("black_cottonwood");
    public static final WoodType     BLACK_COTTONWOOD = register("black_cottonwood", BLACK_COTTONWOOD_SET);

    public static final BlockSetType CINNAMON_SET    = registerSet("cinnamon");
    public static final WoodType     CINNAMON        = register("cinnamon",      CINNAMON_SET);

    public static final BlockSetType CLOVE_SET       = registerSet("clove");
    public static final WoodType     CLOVE           = register("clove",         CLOVE_SET);

    public static final BlockSetType EBONY_SET       = registerSet("ebony");
    public static final WoodType     EBONY           = register("ebony",         EBONY_SET);

    public static final BlockSetType ELM_SET         = registerSet("elm");
    public static final WoodType     ELM             = register("elm",           ELM_SET);

    public static final BlockSetType CEDAR_SET       = registerSet("cedar");
    public static final WoodType     CEDAR           = register("cedar",         CEDAR_SET);

    // ── Batch 3 ───────────────────────────────────────────────────────────
    public static final BlockSetType APPLE_SET       = registerSet("apple");
    public static final WoodType     APPLE           = register("apple",         APPLE_SET);

    public static final BlockSetType GOLDENHEART_SET = registerSet("goldenheart");
    public static final WoodType     GOLDENHEART     = register("goldenheart",   GOLDENHEART_SET);

    public static final BlockSetType LINDEN_SET      = registerSet("linden");
    public static final WoodType     LINDEN          = register("linden",        LINDEN_SET);

    public static final BlockSetType MAHOGANY_SET    = registerSet("mahogany");
    public static final WoodType     MAHOGANY        = register("mahogany",      MAHOGANY_SET);

    public static final BlockSetType MAPLE_SET       = registerSet("maple");
    public static final WoodType     MAPLE           = register("maple",         MAPLE_SET);

    public static final BlockSetType MYRRH_SET       = registerSet("myrrh");
    public static final WoodType     MYRRH           = register("myrrh",         MYRRH_SET);

    // ── Batch 4 ───────────────────────────────────────────────────────────
    public static final BlockSetType REDWOOD_SET     = registerSet("redwood");
    public static final WoodType     REDWOOD         = register("redwood",       REDWOOD_SET);

    public static final BlockSetType CHESTNUT_SET    = registerSet("chestnut");
    public static final WoodType     CHESTNUT        = register("chestnut",      CHESTNUT_SET);

    public static final BlockSetType WILLOW_SET      = registerSet("willow");
    public static final WoodType     WILLOW          = register("willow",        WILLOW_SET);

    public static final BlockSetType WORMTREE_SET    = registerSet("wormtree");
    public static final WoodType     WORMTREE        = register("wormtree",      WORMTREE_SET);

    // ── Helpers ───────────────────────────────────────────────────────────
    private static BlockSetType registerSet(String name) {
        return BlockSetType.register(new BlockSetType(
                "got:" + name,
                true, true, true,
                BlockSetType.PressurePlateSensitivity.MOBS,
                SoundType.WOOD,
                SoundEvents.WOODEN_DOOR_CLOSE,  SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON
        ));
    }

    private static WoodType register(String name, BlockSetType blockSetType) {
        return WoodType.register(new WoodType(
                "got:" + name,
                blockSetType,
                SoundType.WOOD,
                SoundType.HANGING_SIGN,
                SoundEvents.FENCE_GATE_CLOSE,
                SoundEvents.FENCE_GATE_OPEN
        ));
    }

    /** Call once before block registration to load all statics. */
    public static void init() { /* static initialiser does the work */ }
}