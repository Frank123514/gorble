package net.got.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Bellows block-entity animations exported from Blockbench 5.1.4.
 * Minecraft 1.21.4 / Mojang mappings.
 */
public class BellowsAnimations {

    public static final AnimationDefinition PUMPING = AnimationDefinition.Builder.withLength(1.5F)
            .addAnimation("top_board", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -22.5F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5F,  KeyframeAnimations.degreeVec(0.0F, 0.0F,    0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("top_board", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F,  KeyframeAnimations.posVec( 0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-3.0F, -1.8F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.5F,  KeyframeAnimations.posVec( 0.0F,  0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
