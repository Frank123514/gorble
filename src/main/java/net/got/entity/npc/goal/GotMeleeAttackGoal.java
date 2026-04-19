package net.got.entity.npc.goal;

import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * Melee attack goal tuned for GoT NPCs.
 *
 * <p><b>Bug fix — attack arm swing:</b> the base {@link MeleeAttackGoal} calls
 * {@code mob.doHurtTarget()} directly but never calls
 * {@code mob.swing(MAIN_HAND)}.  Without the swing call, {@code swingTime}
 * stays at 0, so {@code attackAnim} in {@code LivingEntity} never advances
 * above 0, which means {@code state.attackTime} is always 0 in the render
 * pipeline and the attack-arm animation in
 * {@link net.got.entity.npc.smallfolk.SmallfolkEntity} never fires.
 *
 * <p>Overriding {@link #checkAndPerformAttack} and calling
 * {@code mob.swing(MAIN_HAND)} before delegating to super is the minimal,
 * safe fix — vanilla handles the cooldown reset and the actual hurt logic.
 */
public final class GotMeleeAttackGoal extends MeleeAttackGoal {

    public GotMeleeAttackGoal(SmallfolkEntity entity, double speedMultiplier) {
        super(entity, speedMultiplier, /* followingTargetEvenIfNotSeen = */ true);
    }

    /**
     * Called every tick while the NPC is within melee reach of its target.
     * We fire {@code swing()} here — exactly once per attack cooldown — so the
     * arm-swing animation starts on the same tick the damage lands.
     */
    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        // Calculate melee reach: based on entity width + target bounding box
        float reach = this.mob.getBbWidth() * 2.0F;
        double reachSq = reach * reach + (target.getBbWidth() * target.getBbWidth());
        double distToTargetSq = this.mob.distanceToSqr(target);
        if (distToTargetSq <= reachSq && this.getTicksUntilNextAttack() <= 0) {
            // Trigger the visual arm swing.  This sets mob.swinging = true and
            // mob.swingTime = -1, which drives the attackAnim field every tick
            // via LivingEntity.updateSwingTime(), so the render state picks it up
            // and state.attackTime goes 0 → 1 over one swing period.
            this.mob.swing(InteractionHand.MAIN_HAND);
        }
        // Vanilla MeleeAttackGoal resets the cooldown and calls doHurtTarget().
        super.checkAndPerformAttack(target);
    }
}