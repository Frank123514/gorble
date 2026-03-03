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
    public static final BlockSetType WEIRWOOD_SET = registerSet("weirwood");
    public static final WoodType WEIRWOOD = WoodType.register(new WoodType(GotMod.MODID + ":weirwood", BlockSetType.OAK));

    public static final BlockSetType ASPEN_SET = registerSet("aspen");
    public static final WoodType ASPEN = WoodType.register(new WoodType(GotMod.MODID + ":aspen", BlockSetType.OAK));

    public static final BlockSetType ALDER_SET = registerSet("alder");
    public static final WoodType ALDER = WoodType.register(new WoodType(GotMod.MODID + ":alder", BlockSetType.OAK));

    public static final BlockSetType PINE_SET       = registerSet("pine");
    public static final WoodType PINE = WoodType.register(new WoodType(GotMod.MODID + ":pine", BlockSetType.OAK));

    public static final BlockSetType FIR_SET        = registerSet("fir");
    public static final WoodType FIR = WoodType.register(new WoodType(GotMod.MODID + ":fir", BlockSetType.OAK));

    public static final BlockSetType SENTINAL_SET = registerSet("sentinal");
    public static final WoodType SENTINAL = WoodType.register(new WoodType(GotMod.MODID + ":sentinal", BlockSetType.OAK));

    public static final BlockSetType IRONWOOD_SET   = registerSet("ironwood");
    public static final WoodType IRONWOOD = WoodType.register(new WoodType(GotMod.MODID + ":ironwood", BlockSetType.OAK));

    public static final BlockSetType HAWTHORN_SET   = registerSet("hawthorn");
    public static final WoodType HAWTHORN = WoodType.register(new WoodType(GotMod.MODID + ":hawthorn", BlockSetType.OAK));

    public static final BlockSetType BEECH_SET      = registerSet("beech");
    public static final WoodType BEECH = WoodType.register(new WoodType(GotMod.MODID + ":beech", BlockSetType.OAK));

    public static final BlockSetType SOLDIER_PINE_SET = registerSet("soldier_pine");
    public static final WoodType SOLDIER_PINE = WoodType.register(new WoodType(GotMod.MODID + ":soldier_pine", BlockSetType.OAK));

    public static final BlockSetType ASH_SET        = registerSet("ash");
    public static final WoodType ASH = WoodType.register(new WoodType(GotMod.MODID + ":ash", BlockSetType.OAK));

    // ── Batch 1 ───────────────────────────────────────────────────────────
    public static final BlockSetType BLACKBARK_SET      = registerSet("blackbark");
    public static final WoodType BLACKBARK = WoodType.register(new WoodType(GotMod.MODID + ":blackbark", BlockSetType.OAK));

    public static final BlockSetType BLOODWOOD_SET      = registerSet("bloodwood");
    public static final WoodType BLOODWOOD = WoodType.register(new WoodType(GotMod.MODID + ":bloodwood", BlockSetType.OAK));

    public static final BlockSetType BLUE_MAHOE_SET     = registerSet("blue_mahoe");
    public static final WoodType BLUE_MAHOE = WoodType.register(new WoodType(GotMod.MODID + ":blue_mahoe", BlockSetType.OAK));

    public static final BlockSetType BURL_SET           = registerSet("burl");
    public static final WoodType BURL = WoodType.register(new WoodType(GotMod.MODID + ":burl", BlockSetType.OAK));

    public static final BlockSetType COTTONWOOD_SET     = registerSet("cottonwood");
    public static final WoodType COTTONWOOD = WoodType.register(new WoodType(GotMod.MODID + ":cottonwood", BlockSetType.OAK));

    // ── Batch 2 ───────────────────────────────────────────────────────────
    public static final BlockSetType BLACK_COTTONWOOD_SET = registerSet("black_cottonwood");
    public static final WoodType BLACK_COTTONWOOD = WoodType.register(new WoodType(GotMod.MODID + ":black_cottonwood", BlockSetType.OAK));

    public static final BlockSetType CINNAMON_SET       = registerSet("cinnamon");
    public static final WoodType CINNAMON = WoodType.register(new WoodType(GotMod.MODID + ":cinnamon", BlockSetType.OAK));

    public static final BlockSetType CLOVE_SET          = registerSet("clove");
    public static final WoodType CLOVE = WoodType.register(new WoodType(GotMod.MODID + ":clove", BlockSetType.OAK));

    public static final BlockSetType EBONY_SET          = registerSet("ebony");
    public static final WoodType EBONY = WoodType.register(new WoodType(GotMod.MODID + ":ebony", BlockSetType.OAK));

    public static final BlockSetType ELM_SET            = registerSet("elm");
    public static final WoodType ELM = WoodType.register(new WoodType(GotMod.MODID + ":elm", BlockSetType.OAK));

    public static final BlockSetType CEDAR_SET          = registerSet("cedar");
    public static final WoodType CEDAR = WoodType.register(new WoodType(GotMod.MODID + ":cedar", BlockSetType.OAK));


    // ── Batch 3 ───────────────────────────────────────────────────────────
    public static final BlockSetType APPLE_SET          = registerSet("apple");
    public static final WoodType APPLE = WoodType.register(new WoodType(GotMod.MODID + ":apple", BlockSetType.OAK));

    public static final BlockSetType GOLDENHEART_SET    = registerSet("goldenheart");
    public static final WoodType GOLDENHEART = WoodType.register(new WoodType(GotMod.MODID + ":goldenheart", BlockSetType.OAK));

    public static final BlockSetType LINDEN_SET         = registerSet("linden");
    public static final WoodType LINDEN = WoodType.register(new WoodType(GotMod.MODID + ":linden", BlockSetType.OAK));

    public static final BlockSetType MAHOGANY_SET       = registerSet("mahogany");
    public static final WoodType MAHOGANY = WoodType.register(new WoodType(GotMod.MODID + ":mahogany", BlockSetType.OAK));

    public static final BlockSetType MAPLE_SET          = registerSet("maple");
    public static final WoodType MAPLE = WoodType.register(new WoodType(GotMod.MODID + ":maple", BlockSetType.OAK));


    public static final BlockSetType MYRRH_SET          = registerSet("myrrh");
    public static final WoodType MYRRH = WoodType.register(new WoodType(GotMod.MODID + ":myrrh", BlockSetType.OAK));

    // ── Batch 4 ───────────────────────────────────────────────────────────
    public static final BlockSetType REDWOOD_SET        = registerSet("redwood");
    public static final WoodType REDWOOD = WoodType.register(new WoodType(GotMod.MODID + ":redwood", BlockSetType.OAK));

    public static final BlockSetType CHESTNUT_SET       = registerSet("chestnut");
    public static final WoodType CHESTNUT = WoodType.register(new WoodType(GotMod.MODID + ":chestnut", BlockSetType.OAK));

    public static final BlockSetType WILLOW_SET         = registerSet("willow");
    public static final WoodType WILLOW = WoodType.register(new WoodType(GotMod.MODID + ":willow", BlockSetType.OAK));

    public static final BlockSetType WORMTREE_SET       = registerSet("wormtree");
    public static final WoodType WORMTREE = WoodType.register(new WoodType(GotMod.MODID + ":wormtree", BlockSetType.OAK));

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
