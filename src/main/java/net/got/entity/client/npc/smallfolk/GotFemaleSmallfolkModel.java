package net.got.entity.client.npc.smallfolk;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;

/**
 * Slim-armed (Alex/female) model for female Smallfolk NPCs.
 *
 * <p>Identical to the standard smallfolk body except the arms are <b>3 px wide</b>
 * instead of 4 px, matching Minecraft's built-in "slim" (Alex) player model.
 * The UV offsets match the standard 64x64 Alex skin layout so female textures
 * painted to that spec render without seams.
 *
 * <p>Arm geometry vs. Steve/standard:
 * <pre>
 *  Part       | Steve (standard)                       | Alex (slim/this model)
 *  -----------+----------------------------------------+----------------------------
 *  right_arm  | texOffs(40,16) box(-4,-2,-2, 4,12,4)  | texOffs(40,16) box(-3,-2,-2, 3,12,4)
 *             | pivot(-5, 2.0, 0)                      | pivot(-5, 2.5, 0)
 *  left_arm   | texOffs(32,48) box(0,-2,-2, 4,12,4)   | texOffs(32,48) box(0,-2,-2, 3,12,4)
 *             | pivot(5, 2.0, 0)                       | pivot(5, 2.5, 0)
 * </pre>
 */
public class GotFemaleSmallfolkModel extends HumanoidModel<SmallfolkRenderState> {

    /**
     * Model layer location used to register and bake this model.
     * Registered in {@code ClientSetup.registerLayerDefinitions} and
     * baked in {@link SmallfolkRenderer}.
     */
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("got", "female_smallfolk"), "main");

    public GotFemaleSmallfolkModel(ModelPart root) {
        super(root);
    }

    /**
     * Builds the slim-arm {@link LayerDefinition} for baking.
     *
     * <p>Starts from {@link HumanoidModel#createMesh} (standard Steve body on
     * a 64x64 UV sheet) then replaces both arm parts with 3 px-wide Alex variants.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition root = mesh.getRoot();

        // ── Right arm — slim (3 px wide) ──────────────────────────────────────
        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-3.0f, -2.0f, -2.0f, 3, 12, 4, CubeDeformation.NONE),
                PartPose.offset(-5.0f, 2.5f, 0.0f));

        // ── Left arm — slim (3 px wide) ───────────────────────────────────────
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(32, 48)
                        .addBox(0.0f, -2.0f, -2.0f, 3, 12, 4, CubeDeformation.NONE),
                PartPose.offset(5.0f, 2.5f, 0.0f));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
