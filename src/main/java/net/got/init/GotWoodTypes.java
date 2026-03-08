package net.got.init;

import net.got.GotMod;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class GotWoodTypes {

    private GotWoodTypes() {}

    // ── Wood Types ─────────────────────────────────────────────────────────
    // All use BlockSetType.OAK for door/trapdoor sounds — matching vanilla
    // wood-type registration pattern (see ModWoodTypes reference).

    public static final WoodType WEIRWOOD         = WoodType.register(new WoodType(GotMod.MODID + ":weirwood",          BlockSetType.OAK));
    public static final WoodType ASPEN            = WoodType.register(new WoodType(GotMod.MODID + ":aspen",             BlockSetType.OAK));
    public static final WoodType ALDER            = WoodType.register(new WoodType(GotMod.MODID + ":alder",             BlockSetType.OAK));
    public static final WoodType PINE             = WoodType.register(new WoodType(GotMod.MODID + ":pine",              BlockSetType.OAK));
    public static final WoodType FIR              = WoodType.register(new WoodType(GotMod.MODID + ":fir",               BlockSetType.OAK));
    public static final WoodType SENTINAL         = WoodType.register(new WoodType(GotMod.MODID + ":sentinal",          BlockSetType.OAK));
    public static final WoodType IRONWOOD         = WoodType.register(new WoodType(GotMod.MODID + ":ironwood",          BlockSetType.OAK));
    public static final WoodType HAWTHORN         = WoodType.register(new WoodType(GotMod.MODID + ":hawthorn",          BlockSetType.OAK));
    public static final WoodType BEECH            = WoodType.register(new WoodType(GotMod.MODID + ":beech",             BlockSetType.OAK));
    public static final WoodType SOLDIER_PINE     = WoodType.register(new WoodType(GotMod.MODID + ":soldier_pine",      BlockSetType.OAK));
    public static final WoodType ASH              = WoodType.register(new WoodType(GotMod.MODID + ":ash",               BlockSetType.OAK));
    public static final WoodType BLACKBARK        = WoodType.register(new WoodType(GotMod.MODID + ":blackbark",         BlockSetType.OAK));
    public static final WoodType BLOODWOOD        = WoodType.register(new WoodType(GotMod.MODID + ":bloodwood",         BlockSetType.OAK));
    public static final WoodType BLUE_MAHOE       = WoodType.register(new WoodType(GotMod.MODID + ":blue_mahoe",        BlockSetType.OAK));
    public static final WoodType COTTONWOOD       = WoodType.register(new WoodType(GotMod.MODID + ":cottonwood",        BlockSetType.OAK));
    public static final WoodType BLACK_COTTONWOOD = WoodType.register(new WoodType(GotMod.MODID + ":black_cottonwood",  BlockSetType.OAK));
    public static final WoodType CINNAMON         = WoodType.register(new WoodType(GotMod.MODID + ":cinnamon",          BlockSetType.OAK));
    public static final WoodType CLOVE            = WoodType.register(new WoodType(GotMod.MODID + ":clove",             BlockSetType.OAK));
    public static final WoodType EBONY            = WoodType.register(new WoodType(GotMod.MODID + ":ebony",             BlockSetType.OAK));
    public static final WoodType ELM              = WoodType.register(new WoodType(GotMod.MODID + ":elm",               BlockSetType.OAK));
    public static final WoodType CEDAR            = WoodType.register(new WoodType(GotMod.MODID + ":cedar",             BlockSetType.OAK));
    public static final WoodType APPLE            = WoodType.register(new WoodType(GotMod.MODID + ":apple",             BlockSetType.OAK));
    public static final WoodType GOLDENHEART      = WoodType.register(new WoodType(GotMod.MODID + ":goldenheart",       BlockSetType.OAK));
    public static final WoodType LINDEN           = WoodType.register(new WoodType(GotMod.MODID + ":linden",            BlockSetType.OAK));
    public static final WoodType MAHOGANY         = WoodType.register(new WoodType(GotMod.MODID + ":mahogany",          BlockSetType.OAK));
    public static final WoodType MAPLE            = WoodType.register(new WoodType(GotMod.MODID + ":maple",             BlockSetType.OAK));
    public static final WoodType MYRRH            = WoodType.register(new WoodType(GotMod.MODID + ":myrrh",             BlockSetType.OAK));
    public static final WoodType REDWOOD          = WoodType.register(new WoodType(GotMod.MODID + ":redwood",           BlockSetType.OAK));
    public static final WoodType CHESTNUT         = WoodType.register(new WoodType(GotMod.MODID + ":chestnut",          BlockSetType.OAK));
    public static final WoodType WILLOW           = WoodType.register(new WoodType(GotMod.MODID + ":willow",            BlockSetType.OAK));
    public static final WoodType WORMTREE         = WoodType.register(new WoodType(GotMod.MODID + ":wormtree",          BlockSetType.OAK));

    /** Call once before block registration to load all statics. */
    public static void init() {}
}