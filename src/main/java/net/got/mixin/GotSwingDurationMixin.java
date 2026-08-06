package net.got.mixin;

import net.got.client.animation.player.GotSwingStyle;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Stretches the vanilla arm-swing duration for sword/greatsword swings so
 * the elaborate keyframe-ported attack animations in {@code GotAnimMath}
 * (the {@code sword1}/{@code sword2}/{@code greatsword} keyframe tables,
 * authored as ~1.05-1.48s clips — see {@code SWORD1_LENGTH},
 * {@code SWORD2_LENGTH}, {@code GSWORD_LENGTH}) actually get enough real
 * time to play out.
 *
 * <p>Vanilla's {@code LivingEntity#getCurrentSwingDuration()} is a fixed
 * 6 ticks (0.3s) for every held item, regardless of weapon. {@code
 * GotPlayerAnimator} drives its keyframe playback off {@code
 * state.attackTime}, which is just swing-progress-through-that-6-tick
 * window — so the whole ~1-1.5s authored slash was being compressed into
 * 0.3 real seconds, which is what made it read as an instant flourish
 * instead of a swing. This mixin gives sword-classified holds enough
 * ticks to actually show the full arc; every other held item (fists,
 * axes, tools, tridents, etc.) keeps vanilla's normal snappy 6-tick swing.
 *
 * <p>Cosmetic only — {@code getCurrentSwingDuration} controls the arm-swing
 * animation timer, not attack cooldown/damage timing (that's the separate
 * attack-speed-attribute-driven {@code attackStrengthTicker}), so this
 * doesn't touch combat balance.
 *
 * <p>remap=false: named/Parchment mappings used directly, see other mixins
 * in this package for why.
 */
@Mixin(value = LivingEntity.class, remap = false)
public abstract class GotSwingDurationMixin {

    // 20 ticks/sec, rounded up from GotAnimMath's SWORD1_LENGTH/SWORD2_LENGTH
    // (1.125s / 1.05s) so both combo swings finish inside the window.
    private static final int SWORD_SWING_TICKS = 22;
    // Rounded up from GotAnimMath's GSWORD_LENGTH (1.4813s).
    private static final int GREATSWORD_SWING_TICKS = 30;

    @Inject(method = "getCurrentSwingDuration", at = @At("RETURN"), cancellable = true, remap = false)
    private void got_stretchSwordSwing(CallbackInfoReturnable<Integer> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player)) {
            return;
        }
        ItemStack mainHand = player.getMainHandItem();
        GotSwingStyle style = GotSwingStyle.fromItem(mainHand);
        switch (style) {
            case SWORD -> cir.setReturnValue(SWORD_SWING_TICKS);
            case GREATSWORD -> cir.setReturnValue(GREATSWORD_SWING_TICKS);
            default -> {
                // Leave vanilla's value (fists, axes, tools, etc.) untouched.
            }
        }
    }
}