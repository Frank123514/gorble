package net.got.entity.npc;

import net.got.entity.npc.smallfolk.SmallfolkEntity;

/**
 * Tracks head-nod and hand-gesture animation state while an NPC is talking.
 *
 * <p>Mirrors LOTR's {@code NPCTalkAnimations}. The actual synced-data
 * accessors ({@code DATA_TALK_*}) live on {@link SmallfolkEntity} so they
 * are registered on the correct class for the network layer.
 *
 * <p>Each tick the server computes sinusoidal oscillation values and writes
 * them to the entity's synced data; clients read them during rendering.
 */
public final class GotNpcTalkAnimations {

    private final SmallfolkEntity entity;
    private int talkTick;

    public GotNpcTalkAnimations(SmallfolkEntity entity) {
        this.entity = entity;
    }

    // ── Server-side tick ──────────────────────────────────────────────────────

    /** Called every server tick from {@link SmallfolkEntity#tick()}. */
    public void serverTick() {
        if (!entity.isTalking()) {
            // Smoothly return all values to zero
            float yaw   = lerpToZero(entity.getTalkHeadYaw(), 0.15f);
            float pitch = lerpToZero(entity.getTalkHeadPitch(), 0.15f);
            float gest  = lerpToZero(entity.getTalkGesture(), 0.15f);
            entity.setTalkData(yaw, pitch, gest);
            talkTick = 0;
            return;
        }
        talkTick++;
        float yaw   = (float) Math.sin(talkTick * 0.12) * 0.3f;
        float pitch = (float) Math.sin(talkTick * 0.09 + 1.0) * 0.15f;
        float gest  = (talkTick % 40 < 20) ? (float) Math.sin(talkTick * 0.18) * 0.5f : 0f;
        entity.setTalkData(yaw, pitch, gest);
    }

    // ── Client-side getters (for the renderer) ────────────────────────────────

    public float getTalkHeadYaw()   { return entity.getTalkHeadYaw(); }
    public float getTalkHeadPitch() { return entity.getTalkHeadPitch(); }
    public float getTalkGesture()   { return entity.getTalkGesture(); }

    private static float lerpToZero(float val, float speed) {
        return Math.abs(val) < 0.01f ? 0f : val * (1f - speed);
    }
}
