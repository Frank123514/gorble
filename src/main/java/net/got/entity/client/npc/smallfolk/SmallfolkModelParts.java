package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Exposes named bone accessors from both male and female Smallfolk models
 * so that renderer layers and item-holding code can reference the same
 * logical attachment points regardless of which concrete model is active.
 */
public interface SmallfolkModelParts {

    ModelPart getArm(HumanoidArm side);

    ModelPart sfHead();

    ModelPart sfBody();

    ModelPart sfRightArm();

    ModelPart sfLeftArm();

    ModelPart sfRightLeg();

    ModelPart sfLeftLeg();

    ModelPart sfRightItemAnchor();

    ModelPart sfLeftItemAnchor();
}