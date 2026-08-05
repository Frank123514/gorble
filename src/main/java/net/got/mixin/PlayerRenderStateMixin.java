package net.got.mixin;

import net.got.client.animation.player.GotAnimatedPlayerState;
import net.got.client.animation.player.GotSwingStyle;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Merges {@link GotAnimatedPlayerState} onto {@code PlayerRenderState},
 * storing the extra data our custom player animations need that vanilla
 * doesn't already extract (climbing/airborne progress, swing style).
 *
 * <p>Render state instances are reused per-player frame to frame by
 * {@code LivingEntityRenderer}, so these {@code @Unique} fields persist
 * across frames the same way vanilla's own smoothed fields (e.g.
 * {@code swimAmount}) do, which is what lets {@code climbProgress} /
 * {@code airborneProgress} ease in and out instead of snapping.
 *
 * <p>remap=false: named/Parchment mappings used directly, see other mixins
 * in this package for why.
 */
@Mixin(value = PlayerRenderState.class, remap = false)
public abstract class PlayerRenderStateMixin implements GotAnimatedPlayerState {

    @Unique
    private float got$climbProgress = 0.0F;

    @Unique
    private float got$airborneProgress = 0.0F;

    @Unique
    private GotSwingStyle got$swingStyle = GotSwingStyle.PUNCH;

    @Unique
    private int got$comboIndex = 0;

    @Unique
    private float got$prevSwing = 0.0F;

    @Override
    public float got$getClimbProgress() {
        return got$climbProgress;
    }

    @Override
    public void got$setClimbProgress(float value) {
        this.got$climbProgress = value;
    }

    @Override
    public float got$getAirborneProgress() {
        return got$airborneProgress;
    }

    @Override
    public void got$setAirborneProgress(float value) {
        this.got$airborneProgress = value;
    }

    @Override
    public GotSwingStyle got$getSwingStyle() {
        return got$swingStyle;
    }

    @Override
    public void got$setSwingStyle(GotSwingStyle style) {
        this.got$swingStyle = style;
    }

    @Override
    public int got$getComboIndex() {
        return got$comboIndex;
    }

    @Override
    public void got$setComboIndex(int value) {
        this.got$comboIndex = value;
    }

    @Override
    public float got$getPrevSwing() {
        return got$prevSwing;
    }

    @Override
    public void got$setPrevSwing(float value) {
        this.got$prevSwing = value;
    }
}