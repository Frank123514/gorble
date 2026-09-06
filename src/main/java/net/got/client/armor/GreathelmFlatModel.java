package net.got.client.armor;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

// Same shape as the Blockbench-exported helm classes (bone-only EntityModel).
// No .bbmodel export exists for this helm, so geometry is carried over
// unchanged from the old GotHelmModels entry (already head-space, no
// re-derivation needed -- PartPose.ZERO instead of the raw bone pivot offset).

public class GreathelmFlatModel extends EntityModel<EntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("got", "greathelm_flat"), "main");
	private final ModelPart bone;

	public GreathelmFlatModel(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 21).addBox(-4.0F, -6.0F, -5.1F, 8.0F, 1.0F, 10.2F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityRenderState renderState) {

	}

}
