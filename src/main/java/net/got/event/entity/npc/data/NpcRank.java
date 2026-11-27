package net.got.event.entity.npc.data;

import net.got.init.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Rank / station of a smallfolk NPC. There are four tiers:
 * <ul>
 *   <li>{@link Tier#CIVILIAN}: plain smallfolk (peasant). Unarmed, flees from danger.</li>
 *   <li>{@link Tier#LEVY}: a smallfolk who has been conscripted. Fights, but is lightly equipped.</li>
 *   <li>{@link Tier#MAN_AT_ARMS}: a professional fighter in basic armor.</li>
 *   <li>{@link Tier#SKILLED}: the top fighter tier. What it IS depends on the region: a knight in most of
 *       the south, heavy cavalry in the North, a reaver on the Iron Islands, and so on. Each regional
 *       flavour is its own constant below; a region picks one by overriding
 *       {@code SmallfolkEntity#skilledRank()}.</li>
 * </ul>
 * What each rank WEARS lives in {@link NpcLoadout}, not here.
 */
public enum NpcRank {

    //        id               title               tier              prefix  hp    dmg  armor speed femaleChance horseChance
    PEASANT   ("peasant",      "Smallfolk",        Tier.CIVILIAN,    false,  20.0, 2.0, 0.0, 0.15, -1.0F,       0.0F),
    LEVY      ("levy",         "Levy",             Tier.LEVY,        false,  24.0, 3.0, 0.0, 0.15, -1.0F,       0.0F),
    MAN_AT_ARMS("man_at_arms", "Man-at-Arms",      Tier.MAN_AT_ARMS, false,  30.0, 5.0, 0.0, 0.15,  0.05F,      0.0F),

    // ---- skilled tier: regional flavours (add more as regions get theirs) ----
    KNIGHT       ("knight",        "Ser",               Tier.SKILLED, true,  40.0, 7.0, 0.0, 0.15,  0.02F, 0.25F),
    HEAVY_CAVALRY("heavy_cavalry", "Heavy Cavalryman",  Tier.SKILLED, false, 40.0, 7.0, 0.0, 0.15,  0.02F, 0.40F),
    REAVER       ("reaver",        "Reaver",            Tier.SKILLED, false, 36.0, 7.0, 0.0, 0.16,  0.05F, 0.0F),
    // ---- Free Folk skilled tiers ----
    /** A hardened wilderness fighter of the Free Folk — the standard skilled rank for most tribes. */
    RAIDER       ("raider",        "Raider",            Tier.SKILLED, false, 36.0, 7.0, 0.0, 0.17, -1.0F, 0.0F),
    /** A disciplined Thenn warrior in bronze armour — the elite of the most organised wildling clan. */
    THENN_WARRIOR("thenn_warrior", "Thenn Warrior",     Tier.SKILLED, false, 42.0, 8.0, 2.0, 0.15, -1.0F, 0.0F),
    // ---- Crownlands skilled tier ----
    /** A Gold Cloak — a City Watch soldier of King's Landing. Well-equipped but not as heavily armoured as a knight. */
    GOLD_CLOAK   ("gold_cloak",    "Gold Cloak",        Tier.SKILLED, false, 38.0, 7.0, 0.0, 0.15,  0.10F, 0.0F);

    public enum Tier { CIVILIAN, LEVY, MAN_AT_ARMS, SKILLED }

    public static final String NBT_KEY = "Rank";

    public final String id;
    public final String title;
    public final Tier tier;
    private final boolean prefixTitle;
    public final double maxHealth;
    public final double attackDamage;
    /** Base armor attribute. Left at 0: worn gear supplies the armor. */
    public final double armor;
    public final double speed;
    /** 0..1 chance of being female, or negative to let the region's gender provider decide. */
    public final float femaleChance;
    /** Chance of spawning mounted on a saddled horse. */
    public final float horseChance;
    public final boolean civilian;

    NpcRank(String id, String title, Tier tier, boolean prefixTitle, double maxHealth, double attackDamage,
            double armor, double speed, float femaleChance, float horseChance) {
        this.id = id;
        this.title = title;
        this.tier = tier;
        this.prefixTitle = prefixTitle;
        this.maxHealth = maxHealth;
        this.attackDamage = attackDamage;
        this.armor = armor;
        this.speed = speed;
        this.femaleChance = femaleChance;
        this.horseChance = horseChance;
        this.civilian = tier == Tier.CIVILIAN;
    }

    /** True for titles written before the name ("Ser Name") rather than after it ("Name the Reaver"). */
    public boolean isPrefixTitle() { return prefixTitle; }

    public boolean isSkilled() { return tier == Tier.SKILLED; }

    /** Main-hand weapon pool (one is picked at random). Placeholder choices; adjust freely. */
    public Item[] weapons() {
        return switch (this) {
            case PEASANT      -> new Item[0];
            case LEVY         -> new Item[] { Items.STONE_AXE };
            case MAN_AT_ARMS  -> new Item[] { ModItems.IRON_SHORTSWORD_CROSSGUARD_POMMEL.get(),
                                              ModItems.IRON_LONGSWORD_CROSSGUARD_POMMEL.get() };
            case KNIGHT, HEAVY_CAVALRY -> new Item[] { ModItems.STEEL_LONGSWORD_CROSSGUARD_POMMEL.get(),
                                                       ModItems.STEEL_BASTARD_SWORD_CROSSGUARD_POMMEL.get() };
            case REAVER       -> new Item[] { Items.IRON_AXE, ModItems.STEEL_SHORTSWORD_CROSSGUARD_POMMEL.get() };
            // Free Folk: bone, obsidian, stone weapons — raw and brutal
            case RAIDER       -> new Item[] { Items.STONE_AXE, Items.STONE_SWORD };
            // Thenns wield bronze — use iron-tier items as stand-in until bronze is a separate item
            case THENN_WARRIOR-> new Item[] { Items.IRON_AXE, ModItems.IRON_SHORTSWORD_CROSSGUARD_POMMEL.get() };
            // Gold Cloaks: trained city guards — shortswords and spears
            case GOLD_CLOAK   -> new Item[] { ModItems.IRON_SHORTSWORD_CROSSGUARD_POMMEL.get(),
                                              ModItems.IRON_LONGSWORD_CROSSGUARD_POMMEL.get() };
        };
    }

    // ── lookup / persistence ──────────────────────────────────────────────────

    /** Also understands the ids of the old five-rank system so saved NPCs keep a sensible rank. */
    public static NpcRank fromString(String s) {
        if (s == null) return PEASANT;
        for (NpcRank r : values()) if (r.id.equals(s)) return r;
        return switch (s) {
            case "man-at-arms"      -> MAN_AT_ARMS;
            case "cavalryman"       -> HEAVY_CAVALRY;
            default                 -> PEASANT;
        };
    }

    public static boolean isValid(String s) {
        if (s == null || s.isEmpty()) return false;
        for (NpcRank r : values()) if (r.id.equals(s)) return true;
        return s.equals("man-at-arms") || s.equals("cavalryman");
    }

    // ── spawning ──────────────────────────────────────────────────────────────

    /** Rank mix for a new NPC. {@code skilled} is the region's skilled-tier flavour. */
    public static NpcRank roll(RandomSource rand, NpcRank skilled) {
        if (skilled == null || !skilled.isSkilled()) skilled = KNIGHT;
        float r = rand.nextFloat();
        if (r < 0.78F) return PEASANT;
        if (r < 0.90F) return LEVY;
        if (r < 0.97F) return MAN_AT_ARMS;
        return skilled;
    }

    public static NpcRank roll(RandomSource rand) { return roll(rand, KNIGHT); }
}
