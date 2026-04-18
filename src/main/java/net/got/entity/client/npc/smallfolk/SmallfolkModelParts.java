package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.geom.ModelPart;

/**
 * Exposes the eight key skeleton anchors from whichever Smallfolk model variant
 * (male or female) is active.
 *
 * <p>Implemented by both {@link GotSmallfolkModel} and
 * {@link GotSmallfolkFemaleModel}.  Consumed by {@link SmallfolkArmorLayer}
 * (to copy poses into the vanilla HumanoidModel before calling
 * EquipmentRenderer) and by {@link SmallfolkHeldItemLayer} (to position
 * held items at the palm anchors).
 */
public interface SmallfolkModelParts {

    /** The head part (child of body). */
    ModelPart sfHead();

    /** The torso / chest part. */
    ModelPart sfBody();

    /** The right arm — mainhand / sword side. */
    ModelPart sfRightArm();

    /** The left arm — offhand / shield side. */
    ModelPart sfLeftArm();

    /** Right leg. */
    ModelPart sfRightLeg();

    /** Left leg. */
    ModelPart sfLeftLeg();

    /**
     * The {@code rightItem} anchor — a zero-volume child of {@code rightArm}
     * positioned at the palm pivot.  Used to attach held items.
     */
    ModelPart sfRightItemAnchor();

    /**
     * The {@code leftItem} anchor — a zero-volume child of {@code leftArm}
     * positioned at the palm pivot.  Used to attach off-hand / shield items.
     */
    ModelPart sfLeftItemAnchor();
}