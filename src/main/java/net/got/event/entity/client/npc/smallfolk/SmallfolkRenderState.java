package net.got.event.entity.client.npc.smallfolk;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class SmallfolkRenderState extends HumanoidRenderState {

    public boolean isFemale;
    public int variant;
    public int variantsPerGender;
    public Identifier texture;
    public boolean isTalking;
    public float talkHeadYaw;
    public float talkHeadPitch;
    public float talkGesture;
}