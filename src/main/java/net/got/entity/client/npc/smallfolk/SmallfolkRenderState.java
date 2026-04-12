package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * Render state for all Smallfolk-hierarchy NPCs (Tiers 1, 2, 3).
 *
 * <p>Fields are populated by {@link SmallfolkRenderer#extractRenderState}
 * and consumed by {@link SmallfolkRenderer#getTextureLocation} and
 * the model-selection logic that mirrors LOTR's small-vs-standard-arms switch.
 */
public class SmallfolkRenderState extends HumanoidRenderState {

    /** {@code true} when the entity is female. */
    public boolean isFemale;

    /** Raw variant index (0 … 2*variantsPerGender-1). */
    public int variant;

    /** Number of texture variants per gender, copied from the entity. */
    public int variantsPerGender;

    /** Whether to use the slim-arms (Alex-style) model. */
    public boolean useSmallArms;

    /** Head-yaw animation from {@code GotNpcTalkAnimations} (radians). */
    public float talkHeadYaw;

    /** Head-pitch animation (radians). */
    public float talkHeadPitch;

    /** Mainhand gesture amount (0–1). */
    public float talkGesture;

    /** True while the NPC is in a talking animation state. */
    public boolean isTalking;
}
