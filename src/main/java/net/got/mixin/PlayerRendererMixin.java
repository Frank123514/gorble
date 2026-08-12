package net.got.mixin;

import net.got.client.animation.player.GotAnimMath;
import net.got.client.animation.player.GotAnimatedPlayerState;
import net.got.client.animation.player.GotFirstPersonRenderState;
import net.got.client.animation.player.GotSwingStyle;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Populates the {@link GotAnimatedPlayerState} fields mixed onto
 * {@code AvatarRenderState} (see {@link PlayerRenderStateMixin}) each time
 * the renderer pulls a fresh snapshot from the live {@code AbstractClientPlayer}.
 *
 * <p>This is the only place in the whole custom-animation system that
 * touches the actual entity — everything downstream ({@code PlayerModelMixin}
 * / {@code GotPlayerAnimator}) only ever reads the render state, which
 * keeps us aligned with how the rest of vanilla's render-state pipeline is
 * meant to be used.
 *
 * <p><b>1.21.11 signature change:</b> {@code AvatarRenderer} is now generic
 * over {@code T extends Avatar} (a new player-avatar abstraction), so
 * {@code extractRenderState}'s erased/bytecode parameter type is
 * {@code net.minecraft.world.entity.Avatar}, not {@code AbstractClientPlayer}
 * — confirmed directly from Mixin's own "Invalid descriptor" error at launch,
 * which reports the exact expected descriptor. The only concrete renderer
 * registered for actual client players still only ever receives real
 * {@code AbstractClientPlayer} instances here, so the parameter is declared
 * as {@code Avatar} to satisfy the descriptor match and immediately cast
 * back to {@code AbstractClientPlayer} for the rest of the method, which is
 * otherwise unchanged from 1.21.4. Still {@code void}/plain
 * {@link CallbackInfo} — {@code extractRenderState} mutates the reused
 * state in place rather than returning it.
 */
@Mixin(value = AvatarRenderer.class, remap = false)
public abstract class PlayerRendererMixin {

    @Inject(method = "extractRenderState", at = @At("RETURN"), remap = false)
    private void got_extractCustomAnimState(
            Avatar entity, AvatarRenderState state, float partialTick,
            CallbackInfo ci) {

        AbstractClientPlayer player = (AbstractClientPlayer) entity;
        GotAnimatedPlayerState anim = (GotAnimatedPlayerState) state;

        float climbTarget = player.onClimbable() ? 1.0F : 0.0F;
        anim.got$setClimbProgress(GotAnimMath.approach(anim.got$getClimbProgress(), climbTarget, 0.35F));

        float airborneTarget = player.onGround() ? 0.0F : 1.0F;
        anim.got$setAirborneProgress(GotAnimMath.approach(anim.got$getAirborneProgress(), airborneTarget, 0.25F));

        float sprintTarget = player.isSprinting() ? 1.0F : 0.0F;
        anim.got$setSprintProgress(GotAnimMath.approach(anim.got$getSprintProgress(), sprintTarget, 0.35F));

        anim.got$setSwingStyle(GotSwingStyle.fromItem(player.getMainHandItem()));

        // Only knowable for the local client's own player — Minecraft only
        // exposes block-breaking progress for whoever's actually holding
        // down attack on this client, not for remote players rendered from
        // sync packets. Remote players (and the local player when not
        // mining) fall back to the normal attack-swing behavior below.
        Minecraft mc = Minecraft.getInstance();
        MultiPlayerGameMode gameMode = mc.gameMode;
        boolean miningWithAxe = mc.player == player
                && gameMode != null
                && gameMode.isDestroying()
                && GotSwingStyle.fromItem(player.getMainHandItem()) == GotSwingStyle.AXE;
        anim.got$setMiningWithAxe(miningWithAxe);

        // Local player's own model, camera currently first-person, AND
        // this extractRenderState call is happening inside
        // LevelRendererMixin's forced world-render (not the inventory
        // screen's player preview or any other AvatarRenderer use that
        // also targets mc.player while the camera option still reads
        // FIRST_PERSON — see GotFirstPersonRenderState's doc). The one
        // case LevelRendererMixin makes render at all instead of being
        // skipped like vanilla. PlayerModelMixin reads this to hide the
        // head/hat cubes so we're not looking at the inside of our own
        // skull, without also hiding the head on menu previews where the
        // camera option is irrelevant.
        anim.got$setLocalFirstPerson(
                mc.player == player
                        && mc.options.getCameraType() == CameraType.FIRST_PERSON
                        && GotFirstPersonRenderState.isRenderingLocalBody());

        // Horse riding pose: HORSE_IDLE/HORSE_RUNNING crossfade off the
        // ridden horse's own ground speed (the player has no walk speed
        // of their own while seated). Other vehicles (boats, minecarts,
        // pigs, etc.) leave got$ridingHorse false and fall through to
        // GotPlayerAnimator's plain skip-if-passenger behavior, since
        // there's no authored clip for them.
        Entity vehicle = player.getVehicle();
        boolean ridingHorse = vehicle instanceof AbstractHorse;
        anim.got$setRidingHorse(ridingHorse);
        float horseRunTarget = ridingHorse && vehicle.getDeltaMovement().horizontalDistanceSqr() > 0.0025
                ? 1.0F
                : 0.0F;
        anim.got$setHorseRunBlend(GotAnimMath.approach(anim.got$getHorseRunBlend(), horseRunTarget, 0.2F));

        // Toggle the 2-swing sword combo on the rising edge of a new swing
        // (previous frame's progress near 0, this frame's above it) rather
        // than on any particular attackTime value, since that's the one
        // moment guaranteed to happen exactly once per swing regardless of
        // how fast the swing animation itself plays.
        //
        // Suppressed entirely while mining with an axe: vanilla still
        // cycles attackTime per mining swing, and without this guard that
        // rising edge would capture a fresh got$swingStartAge mid-mine —
        // which, the instant mining stopped, would hand the normal
        // single-swing path a still-fresh timer and play a leftover
        // combat-swing tail instead of cutting off immediately.
        float swingNow = Mth.clamp(state.attackTime, 0.0F, 1.0F);
        if (!miningWithAxe && anim.got$getPrevSwing() < 0.02F && swingNow >= 0.02F) {
            anim.got$setComboIndex((anim.got$getComboIndex() + 1) % 2);
            // Marks t=0 for our own fixed-duration visual swing (see
            // GotAnimatedPlayerState#got$getSwingStartAge) — captured here,
            // not derived from attackTime, since attackTime's real duration
            // depends on the weapon's attack speed and is often far too
            // short to actually see the swing play out.
            anim.got$setSwingStartAge(state.ageInTicks);
        }
        anim.got$setPrevSwing(swingNow);

        if (miningWithAxe) {
            // Keep the real swing timer pinned deep in the past for the
            // entire duration of mining, so the frame mining ends there's
            // no lingering fresh got$swingStartAge for the normal
            // single-swing path to pick up — the mining loop cuts off
            // immediately instead of finishing/blending into a trailing
            // combat swing.
            anim.got$setSwingStartAge(state.ageInTicks - 1.0E6F);
        }
    }
}