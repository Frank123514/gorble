package net.got.event.entity.npc.goal;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import java.util.function.Predicate;

public final class NearestTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public NearestTargetGoal(SmallfolkEntity entity, Class<T> targetClass, boolean mustSee) {
        super(entity, targetClass, mustSee);
    }

    public NearestTargetGoal(SmallfolkEntity entity, Class<T> targetClass, boolean mustSee, Predicate<LivingEntity> predicate) {
        super(entity, targetClass, 10, mustSee, false, (target, level) -> predicate.test(target));
    }
}