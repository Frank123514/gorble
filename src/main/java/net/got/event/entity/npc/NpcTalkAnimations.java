package net.got.event.entity.npc;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;

public final class NpcTalkAnimations {

    private final SmallfolkEntity entity;
    private int talkTick;

    public NpcTalkAnimations(SmallfolkEntity entity) {
        this.entity = entity;
    }

    public void serverTick() {
        if (!entity.isTalking()) {
            
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

    public float getTalkHeadYaw()   { return entity.getTalkHeadYaw(); }
    public float getTalkHeadPitch() { return entity.getTalkHeadPitch(); }
    public float getTalkGesture()   { return entity.getTalkGesture(); }

    private static float lerpToZero(float val, float speed) {
        return Math.abs(val) < 0.01f ? 0f : val * (1f - speed);
    }
}
