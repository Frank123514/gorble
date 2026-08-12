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
 * Stretches the vanilla arm-swing duration for every {@link GotSwingStyle}
 * whose visual swing (either a real authored
 * {@link net.got.client.animation.player.PlayerAnimations} keyframe clip
 * for SWORD/GREATSWORD/AXE, or a hand-tuned
 * {@link net.got.client.animation.player.GotAnimMath#swingVisualDuration}
 * window for TRIDENT/TOOL/GENERIC) runs longer than vanilla's fixed 6-tick
 * default, so each style actually gets enough real time to play out before
 * the next swing re-triggers and cuts it off.
 *
 * <p>Vanilla's {@code LivingEntity#getCurrentSwingDuration()} is a fixed
 * 6 ticks (0.3s) for every held item, regardless of weapon. This duration
 * governs how long {@code attackTime} takes to cycle, and {@code
 * PlayerRendererMixin} treats {@code attackTime} crossing back near 0 and
 * rising again as "a new swing started" — resetting the clip's clock. Any
 * style whose visual swing window is longer than 6 ticks would otherwise
 * get reset back to frame 0 partway through, reading as stuck-at-the-start
 * or a swing that never finishes rather than a full attack arc — this bit
 * PUNCH (6 ticks, matches vanilla, unaffected) but also silently bit
 * TRIDENT (10 ticks), TOOL (8 ticks), and GENERIC (7 ticks) even before
 * SWORD/GREATSWORD/AXE were added here, since all three already run past
 * vanilla's window. This mixin now stretches every style whose window
 * exceeds 6 ticks; only PUNCH is left on vanilla's normal snappy swing
 * since it's the one style actually sized to fit inside it.
 *
 * <p>Cosmetic only — {@code getCurrentSwingDuration} controls the arm-swing
 * animation timer, not attack cooldown/damage timing (that's the separate
 * attack-speed-attribute-driven {@code attackStrengthTicker}), so this
 * doesn't touch combat balance.
 *
 * <p>remap=false: named/Parchment mappings used directly, see other mixins
 * in this package for why.
 */
// TODO(port-1.21.11): The 1.21.11 primer reworks swing timing into a new data-component
// system — `LivingEntity#SWING_DURATION` is replaced by `SwingAnimation#duration`
// ("not one-to-one"), and items now carry `DataComponents#SWING_ANIMATION`
// (SwingAnimationType.NONE/WHACK/STAB + a duration) instead of a single hardcoded
// constant. It's unclear from the primer summary alone whether
// `LivingEntity#getCurrentSwingDuration()` still exists with the same name/signature, or
// whether swing length is now read from `ItemStack#getSwingAnimation()` directly. This
// mixin could not be safely auto-migrated — verify the method still exists post-upgrade;
// if not, the equivalent behavior (extending sword/greatsword swing length) likely needs
// to be implemented via a component override (e.g. `DataComponents.SWING_ANIMATION` on
// the relevant ItemStack) rather than this Inject.
@Mixin(value = LivingEntity.class, remap = false)
public abstract class GotSwingDurationMixin {

    // 20 ticks/sec, rounded up from PlayerAnimations' own real clip lengths
    // (via lengthInSeconds() * 20) so every combo swing finishes inside the
    // window instead of getting cut off/restarted mid-clip by the next
    // rising-edge re-trigger in PlayerRendererMixin:
    //   SWORD_ATTACK   = 1.125s -> 22.5 ticks
    //   SWORD_ATTACK_2 = 1.6s   -> 32.0 ticks  (the actual long pole — the
    //                              old 22-tick value only covered combo 1
    //                              and was cutting combo 2 off ~10 ticks
    //                              early on every other hit)
    // Both combos share one duration here since this mixin can't see which
    // combo index is about to play (that's tracked client-side on the
    // render state), so it must cover the longer of the two.
    private static final int SWORD_SWING_TICKS = 32;
    // Rounded up from GREATSWORD_ATTACK's real length (1.4813s -> 29.626 ticks).
    private static final int GREATSWORD_SWING_TICKS = 30;
    // Rounded up from AXE_ATTACK's real length (1.5267s -> 30.534 ticks).
    // Previously missing entirely, which left axes on vanilla's fixed
    // 6-tick swing window — about 1/5th of AXE_ATTACK's real length — so
    // a new swing retriggered and reset the clip long before it could ever
    // finish playing.
    private static final int AXE_SWING_TICKS = 31;
    // These three are GotAnimMath.swingVisualDuration()'s hand-tuned
    // windows, not real clip lengths — must be kept in sync with
    // TRIDENT_DURATION_TICKS / TOOL_DURATION_TICKS / GENERIC_DURATION_TICKS
    // there. All three were already longer than vanilla's 6-tick default
    // and were silently getting cut off the same way SWORD/AXE were before
    // those were stretched.
    private static final int TRIDENT_SWING_TICKS = 10;
    private static final int TOOL_SWING_TICKS = 8;
    private static final int GENERIC_SWING_TICKS = 7;

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
            case AXE -> cir.setReturnValue(AXE_SWING_TICKS);
            case TRIDENT -> cir.setReturnValue(TRIDENT_SWING_TICKS);
            case TOOL -> cir.setReturnValue(TOOL_SWING_TICKS);
            case GENERIC -> cir.setReturnValue(GENERIC_SWING_TICKS);
            default -> {
                // PUNCH: leave vanilla's 6-tick value untouched — it's the
                // one style actually sized to fit inside it.
            }
        }
    }
}