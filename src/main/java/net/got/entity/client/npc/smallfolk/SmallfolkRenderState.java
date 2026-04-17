package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * Render state for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <p>Fields are populated by {@link SmallfolkRenderer#extractRenderState}
 * and consumed by {@link SmallfolkRenderer#getTextureLocation} and the
 * male/female model-selection logic.
 *
 * <p>Extends {@link HumanoidRenderState} so that this class satisfies the
 * {@code S extends LivingEntityRenderState} bound on {@code MobRenderer} and
 * the {@code T extends HumanoidRenderState} bound on {@code HumanoidModel}.
 * {@code walkAnimationPos} and {@code walkAnimationSpeed} are inherited from
 * {@code LivingEntityRenderState} and must not be redeclared here.
 *
 * <p>Note: slim-arm / small-arms fields have been removed. The renderer now
 * selects a completely separate model ({@link GotSmallfolkFemaleModel}) for
 * female NPCs — no vanilla slim-arm plumbing is needed.
 */
public class SmallfolkRenderState extends HumanoidRenderState {

    /** {@code true} when the entity is female. */
    public boolean isFemale;

    /** Raw variant index (0 … 2*variantsPerGender-1). */
    public int variant;

    /** Number of texture variants per gender, copied from the entity. */
    public int variantsPerGender;

    /** Head-yaw animation from {@code GotNpcTalkAnimations} (radians). */
    public float talkHeadYaw;

    /** Head-pitch animation (radians). */
    public float talkHeadPitch;

    /** Mainhand gesture amount (0–1). */
    public float talkGesture;

    /** True while the NPC is in a talking animation state. */
    public boolean isTalking;

    /** True when the entity is rendered as a child. */
    public boolean isChild;

    // walkAnimationPos and walkAnimationSpeed are inherited from LivingEntityRenderState.
    // Do NOT redeclare them here — that would shadow the inherited fields and cause
    // the vanilla walk-cycle code to read stale zero values.
}