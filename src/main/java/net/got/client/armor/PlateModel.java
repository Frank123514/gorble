package net.got.client.armor;

import java.util.EnumMap;
import java.util.Map;

import net.got.plate.PlatePiece;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

// Wrapped in GotBodyArmorModel(root, true) at render time. One layer definition per plate piece.
// Converted from gorget / breastplate / spaulders / vambraces / cuisses .bbmodel (32x32 texture sheets,
// textures/entity/equipment/humanoid/<piece>.png). Every piece is two stacked boxes on a vanilla player part:
// the inner layer at the piece's deform and the outer layer 0.25 larger, exactly as set up in Blockbench.
// mc_y = 24 - bb_yMax and x/z are relative to each group's pivot, not x-flipped (same as BrigandineModel).
// Texture UV origins (from the bbmodels):
//   body / breastplate / gorget : (0,0) and (0,16)
//   right arm / right leg       : (0,0) and (0,16), mirrored
//   left  arm / left  leg       : (16,0) and (16,16), mirrored
public final class PlateModel {

    /** Gap between the inner and outer texture layer (the bbmodels use inflate 0 and 0.25). */
    private static final float OUTER_STEP = 0.25F;

    public static final Map<PlatePiece, ModelLayerLocation> LAYERS = new EnumMap<>(PlatePiece.class);

    static {
        for (PlatePiece piece : PlatePiece.values()) {
            LAYERS.put(piece, new ModelLayerLocation(
                    Identifier.fromNamespaceAndPath("got", "plate_" + piece.id), "main"));
        }
    }

    public static LayerDefinition createLayer(PlatePiece piece) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        // "hat" must hang off "head" (HumanoidModel does head.getChild("hat")), not off root.
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        // Every humanoid part must exist; only the ones this piece covers get cubes.
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        CubeDeformation inner = new CubeDeformation(piece.deform);
        CubeDeformation outer = new CubeDeformation(piece.deform + OUTER_STEP);

        switch (piece) {
            case BREASTPLATE, GORGET -> body.addOrReplaceChild("plate",
                    CubeListBuilder.create()
                            .texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, inner)
                            .texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, outer),
                    PartPose.ZERO);
            case SPAULDERS, VAMBRACES -> {
                rightArm.addOrReplaceChild("right_plate",
                        CubeListBuilder.create().mirror(true)
                                .texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, inner)
                                .texOffs(0, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, outer),
                        PartPose.ZERO);
                leftArm.addOrReplaceChild("left_plate",
                        CubeListBuilder.create().mirror(true)
                                .texOffs(16, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, inner)
                                .texOffs(16, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, outer),
                        PartPose.ZERO);
            }
            case CUISSES -> {
                rightLeg.addOrReplaceChild("right_plate",
                        CubeListBuilder.create().mirror(true)
                                .texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, inner)
                                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, outer),
                        PartPose.ZERO);
                leftLeg.addOrReplaceChild("left_plate",
                        CubeListBuilder.create().mirror(true)
                                .texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, inner)
                                .texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, outer),
                        PartPose.ZERO);
            }
        }

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    private PlateModel() {}
}
