package net.got.entity.client.npc.smallfolk;

import software.bernie.geckolib.cache.object.GeoBone;

/**
 * Provides typed access to the key skeleton bones in a Smallfolk GeckoLib model.
 *
 * <p>Implemented by {@link SmallfolkGeoModel}. Consumed by {@link SmallfolkArmorLayer}
 * to copy bone rotations into the vanilla {@link net.minecraft.client.model.HumanoidModel}
 * before drawing armour geometry.
 *
 * <p>Each method returns the most-recently-baked {@link GeoBone} for the named
 * bone, or {@code null} if the bone was not found in the current model (e.g. a
 * child / alternate geo file that omits it). Callers must null-check.
 */
public interface SmallfolkGeoModelBoneAccessor {

    /** The {@code head} bone. */
    GeoBone getBoneHead();

    /** The {@code body} bone (torso/chest). */
    GeoBone getBoneBody();

    /** The {@code rightArm} bone. */
    GeoBone getBoneRightArm();

    /** The {@code leftArm} bone. */
    GeoBone getBoneLeftArm();

    /** The {@code rightLeg} bone. */
    GeoBone getBoneRightLeg();

    /** The {@code leftLeg} bone. */
    GeoBone getBoneLeftLeg();
}
