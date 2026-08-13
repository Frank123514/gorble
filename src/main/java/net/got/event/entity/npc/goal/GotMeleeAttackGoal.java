package net.got.event.entity.npc.goal;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public final class GotMeleeAttackGoal extends MeleeAttackGoal {

    private final SmallfolkEntity smallfolk;

    public GotMeleeAttackGoal(SmallfolkEntity entity, double speedMultiplier) {
        super(entity, speedMultiplier,  true);
        this.smallfolk = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        float reach = this.mob.getBbWidth() * 2.0F;
        double reachSq = reach * reach + (target.getBbWidth() * target.getBbWidth());
        double distToTargetSq = this.mob.distanceToSqr(target);
        if (distToTargetSq <= reachSq && this.getTicksUntilNextAttack() <= 0) {
            
            this.smallfolk.triggerAnim("smallfolk_attack", "attack");
        }
        
        super.checkAndPerformAttack(target);
    }
}