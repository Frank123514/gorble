package net.got.event.entity.npc.goal;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

/**
 *
 *
 * <p>Alerts nearby allies of the same class when this NPC is attacked,
 * causing them to join the fight.
 */
public final class GotHurtByTargetGoal extends HurtByTargetGoal {

    public GotHurtByTargetGoal(SmallfolkEntity entity) {
        super(entity);
        // Alert all nearby smallfolk NPCs of the same culture when attacked
        setAlertOthers();
    }
}
