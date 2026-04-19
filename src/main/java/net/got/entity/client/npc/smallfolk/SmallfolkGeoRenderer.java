package net.got.entity.client.npc.smallfolk;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.got.entity.npc.smallfolk.SmallfolkEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

/**
 * GeckoLib renderer for all Smallfolk NPC tiers.
 *
 * Bone names match exactly what is defined in the geo.json files:
 *
 *   Held items : rightItem, leftItem
 *   Armor      : armor_head, armor_body, armor_right_arm, armor_left_arm,
 *                armor_right_leg, armor_left_leg, armor_right_boot, armor_left_boot
 */
public final class SmallfolkGeoRenderer<T extends SmallfolkEntity> extends GeoEntityRenderer<T> {

    // ── Held-item bone names ───────────────────────────────────────────────────
    private static final String RIGHT_ITEM      = "rightItem";
    private static final String LEFT_ITEM       = "leftItem";

    // ── Armor bone names ───────────────────────────────────────────────────────
    private static final String HELMET          = "armor_head";
    private static final String CHESTPLATE      = "armor_body";
    private static final String RIGHT_SLEEVE    = "armor_right_arm";
    private static final String LEFT_SLEEVE     = "armor_left_arm";
    private static final String RIGHT_ARMOR_LEG = "armor_right_leg";
    private static final String LEFT_ARMOR_LEG  = "armor_left_leg";
    private static final String RIGHT_BOOT      = "armor_right_boot";
    private static final String LEFT_BOOT       = "armor_left_boot";

    private static final float RIDING_Y_OFFSET  = 0.3f;
    private static final float NPC_SCALE        = 0.9375f;

    // ── Cached equipment stacks — set in preRender, read by both layers ────────
    protected ItemStack helmetItem;
    protected ItemStack chestplateItem;
    protected ItemStack leggingsItem;
    protected ItemStack bootsItem;
    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;

    public SmallfolkGeoRenderer(EntityRendererProvider.Context context,
                                ResourceLocation[] maleTextures,
                                ResourceLocation[] femaleTextures) {
        super(context, new SmallfolkGeoModel<>(maleTextures, femaleTextures));
        this.shadowRadius = 0.5f;

        final EquipmentLayerRenderer equipmentRenderer = context.getEquipmentRenderer();

        // ── Armor layer ────────────────────────────────────────────────────────
        addRenderLayer(new ItemArmorGeoLayer<T>(this, equipmentRenderer) {

            @Nullable
            @Override
            protected ItemStack getArmorItemForBone(GeoBone bone, T animatable) {
                return switch (bone.getName()) {
                    case HELMET                              -> SmallfolkGeoRenderer.this.helmetItem;
                    case CHESTPLATE, RIGHT_SLEEVE,
                         LEFT_SLEEVE                        -> SmallfolkGeoRenderer.this.chestplateItem;
                    case RIGHT_ARMOR_LEG, LEFT_ARMOR_LEG    -> SmallfolkGeoRenderer.this.leggingsItem;
                    case RIGHT_BOOT, LEFT_BOOT               -> SmallfolkGeoRenderer.this.bootsItem;
                    default                                 -> null;
                };
            }

            @NotNull
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, T animatable) {
                return switch (bone.getName()) {
                    case HELMET                              -> EquipmentSlot.HEAD;
                    case CHESTPLATE, RIGHT_SLEEVE,
                         LEFT_SLEEVE                        -> EquipmentSlot.CHEST;
                    case RIGHT_ARMOR_LEG, LEFT_ARMOR_LEG    -> EquipmentSlot.LEGS;
                    case RIGHT_BOOT, LEFT_BOOT               -> EquipmentSlot.FEET;
                    default                                 -> super.getEquipmentSlotForBone(bone, stack, animatable);
                };
            }

            @NotNull
            @Override
            protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot,
                                                    ItemStack stack, T animatable,
                                                    HumanoidModel<?> baseModel) {
                return switch (bone.getName()) {
                    case HELMET                              -> baseModel.head;
                    case CHESTPLATE                          -> baseModel.body;
                    case RIGHT_SLEEVE                        -> baseModel.rightArm;
                    case LEFT_SLEEVE                         -> baseModel.leftArm;
                    case RIGHT_ARMOR_LEG, RIGHT_BOOT         -> baseModel.rightLeg;
                    case LEFT_ARMOR_LEG,  LEFT_BOOT          -> baseModel.leftLeg;
                    default                                 -> super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
                };
            }
        });

        // ── Held-item layer ────────────────────────────────────────────────────
        addRenderLayer(new BlockAndItemGeoLayer<T>(this) {

            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, T animatable) {
                return switch (bone.getName()) {
                    case RIGHT_ITEM -> SmallfolkGeoRenderer.this.mainHandItem;
                    case LEFT_ITEM  -> SmallfolkGeoRenderer.this.offhandItem;
                    default         -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
                return switch (bone.getName()) {
                    case RIGHT_ITEM -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    case LEFT_ITEM  -> ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
                    default         -> ItemDisplayContext.NONE;
                };
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                              T animatable, MultiBufferSource bufferSource,
                                              float partialTick, int packedLight, int packedOverlay) {
                if (stack == SmallfolkGeoRenderer.this.mainHandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-70f));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0f));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0f));
                    poseStack.translate(0, 0.25, -0.05);

                } else if (stack == SmallfolkGeoRenderer.this.offhandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0f));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.2, 1.3);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180f));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable,
                        bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model,
                          MultiBufferSource bufferSource, VertexConsumer buffer,
                          boolean isReRender, float partialTick, int packedLight,
                          int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender,
                partialTick, packedLight, packedOverlay, colour);

        // Populate equipment stacks so both layers above can read them
        this.helmetItem     = animatable.getItemBySlot(EquipmentSlot.HEAD);
        this.chestplateItem = animatable.getItemBySlot(EquipmentSlot.CHEST);
        this.leggingsItem   = animatable.getItemBySlot(EquipmentSlot.LEGS);
        this.bootsItem      = animatable.getItemBySlot(EquipmentSlot.FEET);
        this.mainHandItem   = animatable.getItemBySlot(EquipmentSlot.MAINHAND);
        this.offhandItem    = animatable.getItemBySlot(EquipmentSlot.OFFHAND);

        // Translate down when riding so the NPC sits in the saddle
        if (animatable.isPassenger()) {
            poseStack.translate(0.0, -RIDING_Y_OFFSET, 0.0);
        }

        poseStack.scale(NPC_SCALE, NPC_SCALE, NPC_SCALE);
    }
}