package net.got.client.armor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

/**
 * Hand-authored ModelPart geometry for every custom-shape helm, one
 * {@link ModelLayerLocation} per helm. This is the Ofaw-style approach:
 * cube geometry baked directly in Java and rendered through the standard
 * vanilla armor pipeline (HumanoidArmorLayer -> EquipmentLayerRenderer),
 * instead of resolving the helm's Blockbench item model at render time.
 * <p>
 * The coordinates below were reverse-converted from this mod's existing
 * models/item/*.json files (each element's "from"/"to" maps to ModelPart
 * space via x-8, y-16, z-8; UV offsets were recovered from each element's
 * face UVs against Mojang's standard box unwrap layout), so the worn
 * shape matches the item's in-hand/GUI Blockbench model exactly. If you
 * ever redesign a helm's shape, update it in Blockbench and re-run the
 * same conversion rather than hand-editing both independently.
 */
public final class GotHelmModels {

    private GotHelmModels() {}

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", name), "main");
    }

    public static final ModelLayerLocation SKULL_CAP         = layer("skull_cap");
    public static final ModelLayerLocation CONICAL_CAP       = layer("conical_cap");
    public static final ModelLayerLocation PADDED_COIF       = layer("padded_coif");
    public static final ModelLayerLocation GREATHELM_FLAT    = layer("greathelm_flat");
    public static final ModelLayerLocation KETTLE_HELM       = layer("kettle_helm");
    public static final ModelLayerLocation MAIL_COIF         = layer("mail_coif");
    public static final ModelLayerLocation BASCINET          = layer("bascinet");
    public static final ModelLayerLocation GREATHELM_ROUNDED = layer("greathelm_rounded");
    public static final ModelLayerLocation HALFHELM          = layer("halfhelm");

    /**
     * Builds a full HumanoidModel-compatible LayerDefinition: the given
     * geometry becomes the head's cubes, and hat/body/arms/legs are added
     * as empty placeholders since HumanoidModel's constructor requires
     * all seven children to exist even though only the head is visible.
     */
    private static LayerDefinition createHelmLayer(int texWidth, int texHeight, Consumer<CubeListBuilder> headGeometry) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        CubeListBuilder cubes = CubeListBuilder.create();
        headGeometry.accept(cubes);
        root.addOrReplaceChild("head", cubes, PartPose.ZERO);

        CubeListBuilder empty = CubeListBuilder.create();
        root.addOrReplaceChild("hat", empty, PartPose.ZERO);
        root.addOrReplaceChild("body", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_arm", empty, PartPose.ZERO);
        root.addOrReplaceChild("right_leg", empty, PartPose.ZERO);
        root.addOrReplaceChild("left_leg", empty, PartPose.ZERO);

        return LayerDefinition.create(mesh, texWidth, texHeight);
    }

    /**
     * Skull cap, conical cap, kettle helm and bascinet updated from new
     * Blockbench "modded entity" project files (2026-09-01). Those files
     * export cubes relative to an internal "bone" group pivot that's just
     * Blockbench's boilerplate default (identical across every file, not
     * something the author positioned deliberately), so raw coordinates
     * from the exported Java land nowhere near the head. These were
     * reverse-derived straight from each .bbmodel's element data using
     * the verified from/to -> ModelPart transform (jx=-(x-originX),
     * jy=-(y-originY), jz=(z-originZ)), then re-centered on the X/Z axes
     * and vertically anchored so the geometry's top sits just above
     * vanilla's head box (y=-8.5), same convention the other five helms
     * already use. Note: as uploaded, skull_cap/kettle_helm share one
     * base shape and bascinet/halfhelm share another (bascinet == halfhelm
     * exactly) -- that's the source files, not a conversion error.
     */
    public static LayerDefinition createSkullCapLayer() {
        return createHelmLayer(32, 32, cubes -> {
            cubes.texOffs(16, 0).addBox(-4.0F, -6.5F, -4.0F, 8.0F, 3.0F, 8.0F);
            cubes.texOffs(12, 11).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 1.0F, 6.0F);
            cubes.texOffs(8, 18).addBox(-2.0F, -8.5F, -2.0F, 4.0F, 1.0F, 4.0F);
        });
    }

    public static LayerDefinition createConicalCapLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F);
            cubes.texOffs(32, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 6.0F);
            cubes.texOffs(0, 16).addBox(-1.5F, 3.0F, -1.5F, 3.0F, 2.0F, 3.0F);
            cubes.texOffs(12, 16).addBox(-0.5F, 5.0F, -0.5F, 1.0F, 1.0F, 1.0F);
        });
    }

    public static LayerDefinition createPaddedCoifLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(0, 0).addBox(-4.3F, -8.3F, -4.3F, 8.6F, 8.6F, 8.6F);
            cubes.texOffs(36, 0).addBox(-1.5F, -9.0F, -4.5F, 3.0F, 1.0F, 2.0F);
        });
    }

    public static LayerDefinition createGreathelmFlatLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 11.0F, 10.0F);
            cubes.texOffs(0, 21).addBox(-4.0F, -6.0F, -5.1F, 8.0F, 1.0F, 10.2F);
        });
    }

    public static LayerDefinition createKettleHelmLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(16, 14).addBox(-4.0F, -6.5F, -4.0F, 8.0F, 3.0F, 8.0F);
            cubes.texOffs(12, 25).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 1.0F, 6.0F);
            cubes.texOffs(32, 25).addBox(-2.0F, -8.5F, -2.0F, 4.0F, 1.0F, 4.0F);
            cubes.texOffs(28, 0).addBox(-7.0F, -3.5F, -7.0F, 14.0F, 0.0F, 14.0F);
        });
    }

    public static LayerDefinition createMailCoifLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(0, 0).addBox(-4.2F, -8.2F, -4.2F, 8.4F, 8.4F, 8.4F);
            cubes.texOffs(0, 16).addBox(-5.0F, -12.0F, -4.5F, 10.0F, 4.0F, 9.0F);
        });
    }

    public static LayerDefinition createBascinetLayer() {
        return createHelmLayer(32, 32, cubes -> {
            cubes.texOffs(16, 0).addBox(-4.0F, -6.5F, -4.0F, 8.0F, 8.0F, 8.0F);
            cubes.texOffs(12, 16).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 1.0F, 6.0F);
            cubes.texOffs(8, 23).addBox(-2.0F, -8.5F, -2.0F, 4.0F, 1.0F, 4.0F);
        });
    }

    public static LayerDefinition createGreathelmRoundedLayer() {
        return createHelmLayer(64, 64, cubes -> {
            cubes.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F);
            cubes.texOffs(0, 20).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 2.0F, 8.0F);
            cubes.texOffs(32, 20).addBox(-2.0F, 2.0F, -2.0F, 4.0F, 2.0F, 4.0F);
            cubes.texOffs(0, 30).addBox(-0.5F, 1.0F, -5.1F, 1.0F, 3.0F, 10.2F);
            cubes.texOffs(22, 30).addBox(-4.0F, -7.0F, -5.1F, 8.0F, 1.0F, 10.2F);
        });
    }

    public static LayerDefinition createHalfhelmLayer() {
        return createHelmLayer(32, 32, cubes -> {
            cubes.texOffs(16, 0).addBox(-4.0F, -6.5F, -4.0F, 8.0F, 8.0F, 8.0F);
            cubes.texOffs(12, 16).addBox(-3.0F, -7.5F, -3.0F, 6.0F, 1.0F, 6.0F);
            cubes.texOffs(8, 23).addBox(-2.0F, -8.5F, -2.0F, 4.0F, 1.0F, 4.0F);
        });
    }
}
