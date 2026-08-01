package net.got.event.entity.npc.goal;

import net.got.event.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * Melee attack goal tuned for GoT NPCs.
 *
 * <p><b>Attack animation fix:</b> the old approach called {@code mob.swing(MAIN_HAND)}
 * and relied on the {@code swinging} flag in the GeckoLib controller. This failed
 * because {@code swinging} is {@code true} for only a single tick — GeckoLib's
 * {@code thenPlay} only fires when the controller's animation <em>changes</em>, so
 * a brief flag that resets each tick never re-triggers the animation.
 *
 * <p>The fix is to use a {@code triggerableAnim} controller registered in
 * {@link SmallfolkEntity#registerControllers} and call
 * {@link SmallfolkEntity#triggerAnim(String, String)} at the exact tick the
 * hit lands. GeckoLib queues one full play-through of the attack clip, regardless
 * of how long {@code swinging} stays set, and the controller returns to STOP when
 * done — no state-change edge-detect problem.
 */
public final class GotMeleeAttackGoal extends MeleeAttackGoal {

    private final SmallfolkEntity smallfolk;

    public GotMeleeAttackGoal(SmallfolkEntity entity, double speedMultiplier) {
        super(entity, speedMultiplier, /* followingTargetEvenIfNotSeen = */ true);
        this.smallfolk = entity;
    }

    /**
     * Called every tick while the NPC is within melee reach of its target.
     * We fire {@code triggerAnim} here — exactly once per attack cooldown — so
     * the GeckoLib triggerable controller plays one full attack clip.
     */
    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        float reach = this.mob.getBbWidth() * 2.0F;
        double reachSq = reach * reach + (target.getBbWidth() * target.getBbWidth());
        double distToTargetSq = this.mob.distanceToSqr(target);
        if (distToTargetSq <= reachSq && this.getTicksUntilNextAttack() <= 0) {
            // Fire the GeckoLib triggerable animation. This queues a single
            // play-through of the "attack" clip in the "smallfolk_attack"
            // controller without relying on a per-tick boolean flag.
            this.smallfolk.triggerAnim("smallfolk_attack", "attack");
        }
        // Vanilla MeleeAttackGoal resets the cooldown and calls doHurtTarget().
        super.checkAndPerformAttack(target);
    }
}