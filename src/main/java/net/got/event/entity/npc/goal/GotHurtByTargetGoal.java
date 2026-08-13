package net.got.event.entity.npc.goal;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public final class GotHurtByTargetGoal extends HurtByTargetGoal {

    public GotHurtByTargetGoal(SmallfolkEntity entity) {
        super(entity);
        
        setAlertOthers();
    }
}
