package net.got.event.entity.client.giant;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class GiantRenderState extends LivingEntityRenderState {

    public boolean isAttacking;

    public boolean isRoaring;

    public boolean isEnraged;

    public boolean isMoving;

    public boolean isSprinting;

    public boolean isDeadOrDying;

    public boolean isMounting;

    public boolean isRiding;

    public AnimationDefinition lastAnimation = null;
    
    public float animationStartTick = 0F;

    public AnimationDefinition animationToPlay = GiantAnimations.IDLE;

    public float animationTime = 0F;
}