package net.got.client.model;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

import java.util.HashMap;
import java.util.Map;

public class BellowsModel extends Model<Unit> {

    private final ModelPart root;
    private final ModelPart topBoard;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();

    public BellowsModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.root     = root.getChild("root");
        this.topBoard = this.root.getChild("top_board");
    }

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        topBoard.resetPose();
        bakedAnimations.computeIfAbsent(definition, d -> d.bake(this.root)).apply((long) (ageInTicks * 50F), weight);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root",
                CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(40, 30).addBox(-4.1F, 1.4F, -3.5F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition top_board = root.addOrReplaceChild("top_board",
                CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

        top_board.addOrReplaceChild("top_board_r1",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-8.1485F, -0.2371F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9F, 6.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition handle_top = top_board.addOrReplaceChild("handle_top",
                CubeListBuilder.create(), PartPose.offset(-7.6F, 2.6F, 0.0F));

        handle_top.addOrReplaceChild("handle_top_r1",
                CubeListBuilder.create()
                        .texOffs(38, 47).addBox(-1.8858F, -0.735F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition body_top = top_board.addOrReplaceChild("body_top",
                CubeListBuilder.create(), PartPose.offset(-0.5F, 7.5F, 0.5F));

        body_top.addOrReplaceChild("body_c_r1",
                CubeListBuilder.create()
                        .texOffs(0, 31).addBox(-7.0F, -0.5F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.5F, -0.5F, -0.5F, 0.0F, 0.0F, 0.3927F));

        body_top.addOrReplaceChild("body_b_2_r1",
                CubeListBuilder.create()
                        .texOffs(0, 40).addBox(-4.7284F, -0.5481F, -4.0F, 11.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition bottom_board = root.addOrReplaceChild("bottom_board",
                CubeListBuilder.create()
                        .texOffs(0, 11).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        bottom_board.addOrReplaceChild("handle_bottom",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-18.0F, 12.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.0F, -12.0F, 0.0F));

        bottom_board.addOrReplaceChild("body_bottom",
                CubeListBuilder.create()
                        .texOffs(40, 22).addBox(-4.5F, -0.8F, -4.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 22).addBox(-5.5F, 0.2F, -4.5F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, -1.2F, 0.5F));

        root.addOrReplaceChild("nozzle",
                CubeListBuilder.create()
                        .texOffs(38, 40).addBox(-8.0F, 3.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(48, 0).addBox(-6.0F, 3.6F, -1.0F, 2.0F, 1.5F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(14.0F, -6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}