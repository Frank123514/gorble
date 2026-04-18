package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.ItemStack;

/**
 * Render state for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <p>Extends {@link HumanoidRenderState} so this class satisfies the
 * {@code S extends LivingEntityRenderState} bound on {@code MobRenderer} and
 * the {@code T extends HumanoidRenderState} bound on {@code HumanoidModel}.
 *
 * <p>{@code walkAnimationPos} and {@code walkAnimationSpeed} are inherited
 * from {@code LivingEntityRenderState}.  Do NOT redeclare them here — that
 * would shadow the inherited fields and make the walk cycle read stale zeros.
 */
public class SmallfolkRenderState extends HumanoidRenderState {

    // ── Identity / texture selection ──────────────────────────────────────────

    /** {@code true} when the entity is female. */
    public boolean isFemale;

    /** Raw variant index (0 … 2*variantsPerGender−1). */
    public int variant;

    /** Number of texture variants per gender, copied from the entity. */
    public int variantsPerGender;

    // ── Talking animation ─────────────────────────────────────────────────────

    /** Head-yaw animation from {@code GotNpcTalkAnimations} (radians). */
    public float talkHeadYaw;

    /** Head-pitch animation (radians). */
    public float talkHeadPitch;

    /** Mainhand gesture amount (0–1). */
    public float talkGesture;

    /** True while the NPC is in a talking animation state. */
    public boolean isTalking;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    /** True when the entity is rendered as a child (baby). */
    public boolean isChild;

    // ── Combat animation ──────────────────────────────────────────────────────

    /**
     * True when the NPC is actively drawing a bow or crossbow.
     * Set by the renderer from {@code entity.isUsingItem()} + item-type check.
     */
    public boolean isAimingBow;

    /**
     * True when the NPC has a shield raised ({@code entity.isBlocking()}).
     * Drives the left-arm shield-guard pose in the model.
     */
    public boolean isShieldBlocking;

    // ── Riding / mount ────────────────────────────────────────────────────────

    /**
     * True while the NPC is riding a vehicle (horse, etc.).
     *
     * <p>When set, both models lock the legs into the riding straddle pose
     * and return early from {@code setupAnim()} to suppress walk/idle
     * animations — the mount's own animation drives apparent motion instead.
     */
    public boolean isRiding;

    // ── Held items (for SmallfolkHeldItemLayer) ───────────────────────────────

    /**
     * Item currently held in the NPC's main hand (right side).
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack mainHandItem = ItemStack.EMPTY;

    /**
     * Item currently held in the NPC's off hand (left side).
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack offHandItem = ItemStack.EMPTY;

    // ── Armor items (for SmallfolkArmorLayer) ─────────────────────────────────

    /**
     * Equipped helmet/armor in the HEAD slot.
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack headArmorItem = ItemStack.EMPTY;

    /**
     * Equipped chestplate in the CHEST slot.
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack chestArmorItem = ItemStack.EMPTY;

    /**
     * Equipped leggings in the LEGS slot.
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack leggingsArmorItem = ItemStack.EMPTY;

    /**
     * Equipped boots in the FEET slot.
     * Populated by {@link SmallfolkRenderer#extractRenderState}.
     * Never {@code null} — defaults to {@link ItemStack#EMPTY}.
     */
    public ItemStack bootsArmorItem = ItemStack.EMPTY;
}