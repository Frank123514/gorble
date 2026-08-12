package net.got.mixin;

import net.got.client.animation.player.GotFirstPersonRenderState;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes the local player's own entity actually get rendered while the
 * camera is in first person, which vanilla otherwise skips entirely (the
 * player only ever sees the separate first-person hand/arm model, never
 * their own {@code PlayerRenderer} body) — the same behavior mods like
 * "First Person Model" / "Not Enough Animations" provide.
 *
 * <p>Vanilla's entity-culling pass now lives inline inside
 * {@code LevelRenderer.extractVisibleEntities(Camera, Frustum, DeltaTracker,
 * LevelRenderState)} — confirmed by decompiling this project's own
 * {@code LevelRenderer.class} (1.21.11 / NeoForge 21.11.45), which no
 * longer has a standalone {@code collectVisibleEntities} method at all
 * (that logic, and the {@link Camera#isDetached()} check gating whether the
 * camera's own entity is included, got folded directly into
 * {@code extractVisibleEntities}'s loop body). This redirects that same
 * {@code isDetached()} call site so it also reports {@code true} whenever
 * the camera is first-person, letting the local player fall through the
 * same render path every other player already takes.
 * {@code PlayerModelMixin} then hides the head/hat cubes for that one
 * render via {@code GotAnimatedPlayerState#got$isLocalFirstPerson}, which
 * is itself gated on {@link GotFirstPersonRenderState} being set true for
 * exactly this call — see that class's doc for why the gate is needed
 * (short version: {@code PlayerRenderer.extractRenderState} also runs for
 * {@code mc.player} outside the world, e.g. the inventory screen's player
 * preview, and camera type alone can't tell those apart from this render).
 *
 * <p><b>Render-position offset:</b> the first-person camera sits at the
 * player entity's own eye position, so rendering the body at its literal
 * real position puts the camera almost exactly inside the model's own
 * head/chest geometry — near-plane clipping and z-fighting, not the clean
 * look-down-and-see-your-own-body view "First Person Model" produces (see
 * that mod's own {@code LogicHandler#updatePositionOffset} /
 * {@code WorldRendererMixin}). {@code got_applyRenderOffset}/{@code
 * got_restoreRenderOffset} below reproduce that trick, but now hook
 * {@code LevelRenderer.extractEntity(Entity, float)} instead of a
 * per-entity {@code renderEntity} draw call — that method no longer exists
 * in 1.21.11. Rendering was split into an extract phase (builds an
 * immutable {@code EntityRenderState} with position already baked in) and
 * a separate submit phase that only reads {@code x}/{@code y}/{@code z}
 * off that state, so this is the last point the entity's live position can
 * still be nudged before it's captured. For the one frame the local player
 * is extracted, its raw position (via {@link EntityPositionAccessor}) and
 * interpolation fields ({@code xo}/{@code yo}/{@code zo}/{@code xOld}/
 * {@code yOld}/{@code zOld}) are nudged a short distance out along the
 * body's own facing direction before extraction, then restored
 * immediately after.
 */
@Mixin(value = LevelRenderer.class, remap = false)
public abstract class LevelRendererMixin {

    /** How far (in blocks) to push the render position out along facing while standing/walking. */
    @Unique
    private static final float GOT_STANDING_RENDER_OFFSET = 0.35F;

    /** Slightly larger while sneaking, matching the lower/more forward-leaning crouch pose. */
    @Unique
    private static final float GOT_SNEAK_RENDER_OFFSET = 0.37F;

    /** Set by {@code got_applyRenderOffset}, read by {@code got_restoreRenderOffset} for the same call. */
    @Unique
    private double got$offsetX, got$offsetY, got$offsetZ;

    /** Whether this particular {@code renderEntity} call actually nudged the position and needs restoring. */
    @Unique
    private boolean got$offsetApplied;

    @Redirect(
            method = "extractVisibleEntities(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/renderer/state/LevelRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;isDetached()Z")
    )
    private boolean got_forceRenderLocalPlayerBody(Camera camera) {
        if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            return true;
        }
        return camera.isDetached();
    }

    /**
     * {@code extractEntity} is called once per visible entity from
     * {@code extractVisibleEntities} to build that entity's
     * {@code EntityRenderState} — including, now, the local player in
     * first person. Position is baked into the returned state here and
     * never revisited, so this is the last point the nudge can happen.
     * Only the local-first-person case is touched; every other entity
     * (other players, mobs, etc.) passes straight through untouched.
     */
    @Inject(method = "extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("HEAD"))
    private void got_applyRenderOffset(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        got$offsetApplied = false;

        Minecraft mc = Minecraft.getInstance();
        boolean isLocalFirstPersonBody = entity == mc.player
                && mc.options.getCameraType() == CameraType.FIRST_PERSON;
        // Set for the whole duration of this call (see restore below), not
        // just the branch that actually nudges position, so
        // PlayerRendererMixin/PlayerModelMixin see it correctly even for
        // the sleeping early-return right below.
        GotFirstPersonRenderState.setRenderingLocalBody(isLocalFirstPersonBody);

        if (!(entity instanceof LivingEntity living) || !isLocalFirstPersonBody || mc.player.isSleeping()) {
            return;
        }

        float realYaw = Mth.rotLerp(partialTick, living.yBodyRotO, living.yBodyRot);
        float renderOffset = (living.isCrouching() || living.getPose() == Pose.CROUCHING)
                ? GOT_SNEAK_RENDER_OFFSET
                : GOT_STANDING_RENDER_OFFSET;
        double x = renderOffset * Math.sin(Math.toRadians(realYaw));
        double z = -renderOffset * Math.cos(Math.toRadians(realYaw));

        got$offsetX = x;
        got$offsetY = 0.0D;
        got$offsetZ = z;
        got$offsetApplied = true;

        EntityPositionAccessor access = (EntityPositionAccessor) entity;
        access.got$setRawPosition(access.got$getRawPosition().add(got$offsetX, got$offsetY, got$offsetZ));
        entity.xo += got$offsetX;
        entity.yo += got$offsetY;
        entity.zo += got$offsetZ;
        entity.xOld += got$offsetX;
        entity.yOld += got$offsetY;
        entity.zOld += got$offsetZ;
    }

    @Inject(method = "extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            at = @At("RETURN"))
    private void got_restoreRenderOffset(Entity entity, float partialTick, CallbackInfoReturnable<EntityRenderState> cir) {
        GotFirstPersonRenderState.setRenderingLocalBody(false);

        if (!got$offsetApplied) {
            return;
        }
        got$offsetApplied = false;

        EntityPositionAccessor access = (EntityPositionAccessor) entity;
        access.got$setRawPosition(access.got$getRawPosition().subtract(got$offsetX, got$offsetY, got$offsetZ));
        entity.xo -= got$offsetX;
        entity.yo -= got$offsetY;
        entity.zo -= got$offsetZ;
        entity.xOld -= got$offsetX;
        entity.yOld -= got$offsetY;
        entity.zOld -= got$offsetZ;
    }
}