package net.got.entity.npc.goal;

import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * Melee attack goal tuned for GoT NPCs
 *
 * so the NPC keeps
 * chasing a fleeing target rather than giving up at melee range.
 */
public final class GotMeleeAttackGoal extends MeleeAttackGoal {

    public GotMeleeAttackGoal(SmallfolkEntity entity, double speedMultiplier) {
        super(entity, speedMultiplier, true);
    }
}
