package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

/**
 * Renders equipped armour on Smallfolk NPCs.
 *
 * <h3>Why a custom layer?</h3>
 * The vanilla {@code HumanoidArmorLayer} requires the entity model to extend
 * {@link HumanoidModel}. Our models are plain {@code EntityModel} subclasses
 * with a non-standard part hierarchy, so we cannot use that layer directly.
 *
 * <h3>Texture path convention (MC 1.21.4)</h3>
 * {@code textures/entity/equipment/<layer>/<material>.png}
 * where {@code <layer>} is {@code humanoid_leggings} for LEGS, {@code humanoid} otherwise,
 * and {@code <material>} is derived by stripping the ArmorType suffix from the item's
 * registry name (e.g. {@code iron_chestplate} → {@code iron},
 * {@code got:copper_helmet} → {@code copper}).
 * This matches the 1.21.4 equipment asset naming convention for all standard armor.
 */
public class SmallfolkArmorLayer
        extends RenderLayer<SmallfolkRenderState, EntityModel<SmallfolkRenderState>> {

    private static final String[] ARMOR_SUFFIXES =
            { "_helmet", "_chestplate", "_leggings", "_boots", "_cap", "_tunic", "_pants", "_boots" };

    /** Inner humanoid model — used for LEGS slot (leggings geometry). */
    private final HumanoidModel<SmallfolkRenderState> inner;
    /** Outer humanoid model — used for HEAD, CHEST, FEET slots. */
    private final HumanoidModel<SmallfolkRenderState> outer;

    public SmallfolkArmorLayer(
            RenderLayerParent<SmallfolkRenderState, EntityModel<SmallfolkRenderState>> parent,
            EntityRendererProvider.Context ctx) {
        super(parent);
        this.inner = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
        this.outer = new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       SmallfolkRenderState state, float yRot, float xRot) {

        EntityModel<SmallfolkRenderState> raw = this.getParentModel();
        if (!(raw instanceof SmallfolkModelParts src)) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;

            ItemStack stack = switch (slot) {
                case HEAD  -> state.headArmorItem;
                case CHEST -> state.chestArmorItem;
                case LEGS  -> state.leggingsArmorItem;
                case FEET  -> state.bootsArmorItem;
                default    -> ItemStack.EMPTY;
            };
            if (stack.isEmpty()) continue;
            if (!(stack.getItem() instanceof ArmorItem)) continue;

            HumanoidModel<SmallfolkRenderState> armorModel =
                    (slot == EquipmentSlot.LEGS) ? inner : outer;

            copyPose(src, armorModel);
            setSlotVisibility(armorModel, slot);

            // Derive the equipment asset name from the item's registry path by
            // stripping the armor-type suffix (e.g. "iron_chestplate" -> "iron").
            // This works for all vanilla and modded armor that follows the standard
            // naming convention, matching the 1.21.4 equipment asset path layout.
            ResourceLocation itemKey = BuiltInRegistries.ITEM
                    .getKey(stack.getItem());
            String materialName = stripArmorSuffix(itemKey.getPath());
            String layer = (slot == EquipmentSlot.LEGS) ? "humanoid_leggings" : "humanoid";
            ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(
                    itemKey.getNamespace(),
                    "textures/entity/equipment/" + layer + "/" + materialName + ".png"
            );

            poseStack.pushPose();
            armorModel.renderToBuffer(
                    poseStack,
                    buffer.getBuffer(RenderType.armorCutoutNoCull(textureLoc)),
                    packedLight,
                    OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Strips a known armor-piece suffix from an item registry path. */
    private static String stripArmorSuffix(String path) {
        for (String suffix : ARMOR_SUFFIXES) {
            if (path.endsWith(suffix)) {
                return path.substring(0, path.length() - suffix.length());
            }
        }
        return path; // fallback: use path as-is
    }

    private static void copyPose(SmallfolkModelParts src,
                                 HumanoidModel<SmallfolkRenderState> dst) {
        copyPart(src.sfHead(),     dst.head);
        copyPart(src.sfBody(),     dst.body);
        copyPart(src.sfRightArm(), dst.rightArm);
        copyPart(src.sfLeftArm(),  dst.leftArm);
        copyPart(src.sfRightLeg(), dst.rightLeg);
        copyPart(src.sfLeftLeg(),  dst.leftLeg);
    }

    private static void copyPart(ModelPart from, ModelPart to) {
        // Copy rotations only.  The NPC model uses a non-standard part hierarchy
        // (root → waist → body, legs offset by -12 from root, etc.), so its x/y/z
        // positions are incompatible with the vanilla HumanoidModel's coordinate
        // system.  Overwriting the HumanoidModel's own part positions was the root
        // cause of leggings rendering at the head, the chestplate torso disappearing,
        // and boots not appearing at all.  The HumanoidModel already knows where each
        // part belongs; we only need to sync the animation rotations.
        to.xRot = from.xRot;
        to.yRot = from.yRot;
        to.zRot = from.zRot;
    }

    private static void setSlotVisibility(HumanoidModel<?> model, EquipmentSlot slot) {
        model.head.visible     = false;
        model.hat.visible      = false;
        model.body.visible     = false;
        model.rightArm.visible = false;
        model.leftArm.visible  = false;
        model.rightLeg.visible = false;
        model.leftLeg.visible  = false;

        switch (slot) {
            case HEAD  -> { model.head.visible = true;     model.hat.visible      = true; }
            case CHEST -> { model.body.visible = true;     model.rightArm.visible = true;
                model.leftArm.visible  = true; }
            // body must also be visible for LEGS so the leggings texture renders its
            // hip/waistband section on the torso area — matching vanilla 1.21.4 behaviour.
            case LEGS  -> { model.body.visible     = true;
                            model.rightLeg.visible = true; model.leftLeg.visible  = true; }
            case FEET  -> { model.rightLeg.visible = true; model.leftLeg.visible  = true; }
            default    -> {}
        }
    }
}