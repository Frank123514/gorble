package net.got.entity.npc.goal;

import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import java.util.function.Predicate;

/**
 * Nearest-attackable-target goal for GoT NPCs.
 * Mirrors LOTR's {@code NPCNearestAttackableTargetGoal}.
 *
 * @param <T> the target entity type
 */
public final class GotNearestTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public GotNearestTargetGoal(SmallfolkEntity entity, Class<T> targetClass, boolean mustSee) {
        super(entity, targetClass, mustSee);
    }

    public GotNearestTargetGoal(SmallfolkEntity entity, Class<T> targetClass, boolean mustSee, Predicate<LivingEntity> predicate) {
        super(entity, targetClass, 10, mustSee, false, (target, level) -> predicate.test(target));
    }
}