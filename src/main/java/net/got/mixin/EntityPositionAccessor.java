package net.got.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes {@code Entity}'s private {@code position} field so
 * {@link LevelRendererMixin} can temporarily nudge the local player's own
 * render position forward of the camera for its one self-render call each
 * frame, then restore it immediately after — see that class's doc for why.
 *
 * <p>Only {@code position} needs an accessor; the interpolation fields
 * ({@code xo}/{@code yo}/{@code zo}/{@code xOld}/{@code yOld}/{@code zOld})
 * are already public on {@code Entity} in Mojang mappings and get nudged
 * directly.
 *
 * <p><b>Verification note:</b> like the rest of this project's client
 * mixins, this targets named/Parchment mappings directly ({@code
 * remap = false}) — confirm the {@code position} field name against a
 * decompile of {@code Entity} if Mixin fails to resolve the accessor at
 * launch.
 */
@Mixin(value = Entity.class, remap = false)
public interface EntityPositionAccessor {

    @Accessor("position")
    Vec3 got$getRawPosition();

    @Accessor("position")
    void got$setRawPosition(Vec3 position);
}