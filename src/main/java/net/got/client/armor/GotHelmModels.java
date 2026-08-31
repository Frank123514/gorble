package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

/**
 * LayerDefinitions for the nine custom headwear types from the AWOIAF
 * Armament#Headwear page (skull cap, conical cap, kettle helm, padded coif,
 * mail coif, halfhelm, bascinet, greathelm flat-topped, greathelm rounded).
 *
 * Each model only needs "head"/"hat" geometry that actually renders -- the
 * body/arm/leg parts are required by HumanoidModel's constructor (it looks
 * children up by name) but are zero-size and never visible, since these are
 * helmet-slot-only items. See {@link GotHelmModel} and {@link GotHelmArmorItem}
 * for how these get baked and hooked up to the item at render time.
 */
public final class GotHelmModels {

    private GotHelmModels() {}

    private static ModelLayerLocation location(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "helm_" + name), "main");
    }

    public static final ModelLayerLocation SKULL_CAP          = location("skull_cap");
    public static final ModelLayerLocation CONICAL_CAP        = location("conical_cap");
    public static final ModelLayerLocation KETTLE_HELM        = location("kettle_helm");
    public static final ModelLayerLocation PADDED_COIF        = location("padded_coif");
    public static final ModelLayerLocation MAIL_COIF          = location("mail_coif");
    public static final ModelLayerLocation HALFHELM           = location("halfhelm");
    public static final ModelLayerLocation BASCINET           = location("bascinet");
    public static final ModelLayerLocation GREATHELM_FLAT     = location("greathelm_flat");
    public static final ModelLayerLocation GREATHELM_ROUNDED  = location("greathelm_rounded");

    /** Builds the mandatory-but-invisible body/arm/leg parts every HumanoidModel needs. */
    private static PartDefinition dummyLimbs(PartDefinition root) {
        CubeListBuilder empty = CubeListBuilder.create();
        root.addOrReplaceChild("hat", empty, PartPose.ZERO);
        root.addOrReplaceChild("body", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_leg", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_leg", empty, PartPose.ZERO);
        return root;
    }

    public static LayerDefinition createSkullCapLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0.5F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createConicalCapLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0.5F))
                        .texOffs(32, 0).addBox(-3F, -11F, -3F, 6F, 3F, 6F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-1.5F, -13F, -1.5F, 3F, 2F, 3F, new CubeDeformation(0.0F))
                        .texOffs(12, 16).addBox(-0.5F, -14F, -0.5F, 1F, 1F, 1F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createKettleHelmLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 7F, 8F, new CubeDeformation(0.5F))
                        .texOffs(32, 0).addBox(-3F, -10F, -3F, 6F, 2F, 6F, new CubeDeformation(0.0F))
                        .texOffs(0, 15).addBox(-7F, -1F, -5F, 14F, 1F, 10F, new CubeDeformation(0.0F))
                        .texOffs(0, 26).addBox(-5F, -1F, -7F, 10F, 1F, 14F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createPaddedCoifLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.3F, -8.3F, -4.3F, 8.6F, 8.6F, 8.6F, new CubeDeformation(0.0F))
                        .texOffs(36, 0).addBox(-1.5F, 0F, -4.5F, 3F, 1F, 2F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createMailCoifLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.2F, -8.2F, -4.2F, 8.4F, 8.4F, 8.4F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-5F, 0F, -4.5F, 10F, 4F, 9F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createHalfhelmLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0.5F))
                        .texOffs(32, 0).addBox(-0.5F, -4F, -4.6F, 1F, 4.5F, 1F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createBascinetLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0.5F))
                        .texOffs(32, 0).addBox(-2.75F, -11F, -2.75F, 5.5F, 3F, 5.5F, new CubeDeformation(0.0F))
                        .texOffs(56, 0).addBox(-1.25F, -13F, -1.25F, 2.5F, 2F, 2.5F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-5F, -2F, -3F, 1F, 4F, 6F, new CubeDeformation(0.0F))
                        .texOffs(14, 16).addBox(4F, -2F, -3F, 1F, 4F, 6F, new CubeDeformation(0.0F))
                        .texOffs(28, 16).addBox(-4F, -2F, 4F, 8F, 3F, 1.5F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createGreathelmFlatLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5F, -9F, -5F, 10F, 11F, 10F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(-4F, -3F, -5.1F, 8F, 1F, 10.2F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createGreathelmRoundedLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5F, -8F, -5F, 10F, 10F, 10F, new CubeDeformation(0.0F))
                        .texOffs(0, 20).addBox(-4F, -10F, -4F, 8F, 2F, 8F, new CubeDeformation(0.0F))
                        .texOffs(32, 20).addBox(-2F, -12F, -2F, 4F, 2F, 4F, new CubeDeformation(0.0F))
                        .texOffs(0, 30).addBox(-0.5F, -12F, -5.1F, 1F, 3F, 10.2F, new CubeDeformation(0.0F))
                        .texOffs(22, 30).addBox(-4F, -2F, -5.1F, 8F, 1F, 10.2F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        dummyLimbs(root);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
