package net.francis.got.mixin;

import net.francis.got.client.animation.player.AnimatedPlayerState;
import net.francis.got.client.animation.player.SwingStyle;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AvatarRenderState.class, remap = false)
public abstract class PlayerRenderStateMixin implements AnimatedPlayerState {

    @Unique
    private float got$climbProgress = 0.0F;

    @Unique
    private float got$airborneProgress = 0.0F;

    @Unique
    private float got$sprintProgress = 0.0F;

    @Unique
    private SwingStyle got$swingStyle = SwingStyle.PUNCH;

    @Unique
    private int got$comboIndex = 0;

    @Unique
    private float got$prevSwing = 0.0F;

    @Unique
    private float got$swingStartAge = -1.0E6F;

    @Unique
    private boolean got$miningWithAxe = false;

    @Unique
    private boolean got$ridingHorse = false;

    @Unique
    private float got$horseRunBlend = 0.0F;

    @Unique
    private boolean got$localFirstPerson = false;

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
    public float got$getSprintProgress() {
        return got$sprintProgress;
    }

    @Override
    public void got$setSprintProgress(float value) {
        this.got$sprintProgress = value;
    }

    @Override
    public SwingStyle got$getSwingStyle() {
        return got$swingStyle;
    }

    @Override
    public void got$setSwingStyle(SwingStyle style) {
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

    @Override
    public float got$getSwingStartAge() {
        return got$swingStartAge;
    }

    @Override
    public void got$setSwingStartAge(float value) {
        this.got$swingStartAge = value;
    }

    @Override
    public boolean got$isMiningWithAxe() {
        return got$miningWithAxe;
    }

    @Override
    public void got$setMiningWithAxe(boolean value) {
        this.got$miningWithAxe = value;
    }

    @Override
    public boolean got$isRidingHorse() {
        return got$ridingHorse;
    }

    @Override
    public void got$setRidingHorse(boolean value) {
        this.got$ridingHorse = value;
    }

    @Override
    public float got$getHorseRunBlend() {
        return got$horseRunBlend;
    }

    @Override
    public void got$setHorseRunBlend(float value) {
        this.got$horseRunBlend = value;
    }

    @Override
    public boolean got$isLocalFirstPerson() {
        return got$localFirstPerson;
    }

    @Override
    public void got$setLocalFirstPerson(boolean value) {
        this.got$localFirstPerson = value;
    }
}