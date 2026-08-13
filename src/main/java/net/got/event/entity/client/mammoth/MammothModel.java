package net.got.event.entity.client.mammoth;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import java.util.HashMap;
import java.util.Map;

public class MammothModel extends EntityModel<MammothRenderState> {

    final ModelPart body;
    final ModelPart head;
    final ModelPart trunk_a;
    final ModelPart trunk_b;
    final ModelPart trunk_c;
    final ModelPart trunk_d;
    final ModelPart tusk_left;
    final ModelPart tusk_left_a;
    final ModelPart tusk_left_b;
    final ModelPart tusk_left_c;
    final ModelPart tusk_left_d;
    final ModelPart tusk_right;
    final ModelPart tusk_right_a;
    final ModelPart tusk_right_b;
    final ModelPart tusk_right_c;
    final ModelPart tusk_right_d;
    final ModelPart tail;
    final ModelPart leg_front_left;
    final ModelPart leg_front_right;
    final ModelPart leg_back_left;
    final ModelPart leg_back_right;
    final ModelPart shoulder;
    private final ModelPart root;
    private final Map<AnimationDefinition, KeyframeAnimation> bakedAnimations = new HashMap<>();

    public MammothModel(ModelPart root) {
        super(root);
        this.root            = root;
        this.body            = root.getChild("body");
        this.head            = root.getChild("head");
        this.trunk_a         = this.head.getChild("trunk_a");
        this.trunk_b         = this.trunk_a.getChild("trunk_b");
        this.trunk_c         = this.trunk_b.getChild("trunk_c");
        this.trunk_d         = this.trunk_c.getChild("trunk_d");
        this.tusk_left       = this.head.getChild("tusk_left");
        this.tusk_left_a     = this.tusk_left.getChild("tusk_left_a");
        this.tusk_left_b     = this.tusk_left_a.getChild("tusk_left_b");
        this.tusk_left_c     = this.tusk_left_b.getChild("tusk_left_c");
        this.tusk_left_d     = this.tusk_left_c.getChild("tusk_left_d");
        this.tusk_right      = this.head.getChild("tusk_right");
        this.tusk_right_a    = this.tusk_right.getChild("tusk_right_a");
        this.tusk_right_b    = this.tusk_right_a.getChild("tusk_right_b");
        this.tusk_right_c    = this.tusk_right_b.getChild("tusk_right_c");
        this.tusk_right_d    = this.tusk_right_c.getChild("tusk_right_d");
        this.tail            = root.getChild("tail");
        this.leg_front_left  = root.getChild("leg_front_left");
        this.leg_front_right = root.getChild("leg_front_right");
        this.leg_back_left   = root.getChild("leg_back_left");
        this.leg_back_right  = root.getChild("leg_back_right");
        this.shoulder        = root.getChild("shoulder");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, -4.0F, -1.5708F, 0.0F, 0.0F));

        body.addOrReplaceChild("body_torso_r1", CubeListBuilder.create().texOffs(0, 35).addBox(-10.0F, -21.1745F, -8.0038F, 20.0F, 14.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 14.0F, 1.5272F, 0.0F, 0.0F));
        body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -23.0F, -7.0F, 20.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 12.0F, 1.4399F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 66).addBox(-7.0F, -6.178F, -5.4187F, 14.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -17.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition trunk_a = head.addOrReplaceChild("trunk_a", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.1701F, -3.6822F, -0.3491F, 0.0F, 0.0F));
        trunk_a.addOrReplaceChild("trunk_a_r1", CubeListBuilder.create().texOffs(102, 61).addBox(-1.5F, 15.0859F, -35.6451F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0798F, -21.7388F, 30.7269F, 0.1309F, 0.0F, 0.0F));

        PartDefinition trunk_b = trunk_a.addOrReplaceChild("trunk_b", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0894F, 5.771F, 2.0169F, -0.5236F, 0.0F, 0.0F));
        trunk_b.addOrReplaceChild("trunk_b_r1", CubeListBuilder.create().texOffs(104, 17).addBox(-1.5F, 14.1305F, -35.6517F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3308F, -37.2759F, 5.8528F, 0.9163F, 0.0F, 0.0F));

        PartDefinition trunk_c = trunk_b.addOrReplaceChild("trunk_c", CubeListBuilder.create(), PartPose.offset(0.3308F, 2.0241F, 5.4528F));
        trunk_c.addOrReplaceChild("trunk_c_r1", CubeListBuilder.create().texOffs(32, 106).addBox(-1.5F, 13.5222F, -35.6384F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -36.3026F, -4.9168F, 1.2217F, 0.0F, 0.0F));

        PartDefinition trunk_d = trunk_c.addOrReplaceChild("trunk_d", CubeListBuilder.create(), PartPose.offset(0.0F, 0.9974F, 5.0832F));
        trunk_d.addOrReplaceChild("trunk_d_r1", CubeListBuilder.create().texOffs(90, 109).addBox(-0.5F, 14.652F, -35.2555F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -30.2F, -22.5F, 1.7017F, 0.0F, 0.0F));

        PartDefinition tusk_left = head.addOrReplaceChild("tusk_left", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 14.6701F, -3.1822F, 0.1745F, -0.2618F, 0.0F));

        PartDefinition tusk_left_a = tusk_left.addOrReplaceChild("tusk_left_a", CubeListBuilder.create(), PartPose.offset(-4.0F, -11.0F, 23.5F));
        tusk_left_a.addOrReplaceChild("tusk_left_a_r1", CubeListBuilder.create().texOffs(102, 73).addBox(0.6671F, -2.1775F, -26.4664F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3F, 10.0F, 3.2F, -0.2618F, 0.1309F, -0.3054F));

        PartDefinition tusk_left_b = tusk_left_a.addOrReplaceChild("tusk_left_b", CubeListBuilder.create(), PartPose.offset(7.0F, 10.0F, -22.0F));
        tusk_left_b.addOrReplaceChild("tusk_left_b_r1", CubeListBuilder.create().texOffs(18, 106).addBox(1.6671F, -2.1775F, -26.4664F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4F, 6.0F, 24.2F, -0.2618F, 0.1309F, -0.3054F));

        PartDefinition tusk_left_c = tusk_left_b.addOrReplaceChild("tusk_left_c", CubeListBuilder.create(), PartPose.offset(-7.0F, -10.0F, 22.0F));
        tusk_left_c.addOrReplaceChild("tusk_left_c_r1", CubeListBuilder.create().texOffs(106, 38).addBox(-2.0825F, -1.9812F, -0.547F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.3F, 13.0F, -24.5F, -0.8727F, 0.0F, 0.0F));

        PartDefinition tusk_left_d = tusk_left_c.addOrReplaceChild("tusk_left_d", CubeListBuilder.create(), PartPose.offset(7.0F, 17.0F, -30.0F));
        tusk_left_d.addOrReplaceChild("tusk_left_d_r1", CubeListBuilder.create().texOffs(74, 109).addBox(-1.0825F, -1.9812F, -0.547F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, 0.1F, -1.6636F, 0.3477F, -0.0317F));

        PartDefinition tusk_right = head.addOrReplaceChild("tusk_right", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 14.6701F, -3.1822F, 0.1745F, 0.2618F, 0.0F));

        PartDefinition tusk_right_a = tusk_right.addOrReplaceChild("tusk_right_a", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        tusk_right_a.addOrReplaceChild("tusk_right_a_r1", CubeListBuilder.create().texOffs(0, 104).addBox(-5.6671F, -2.1775F, -26.4664F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3F, -1.0F, 26.7F, -0.2618F, -0.1309F, 0.3054F));

        PartDefinition tusk_right_b = tusk_right_a.addOrReplaceChild("tusk_right_b", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));
        tusk_right_b.addOrReplaceChild("tusk_left_b_r2", CubeListBuilder.create().texOffs(106, 29).addBox(-5.6671F, -2.1775F, -26.4664F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4F, 5.0F, 25.7F, -0.2618F, -0.1309F, 0.3054F));

        PartDefinition tusk_right_c = tusk_right_b.addOrReplaceChild("tusk_right_c", CubeListBuilder.create(), PartPose.offset(-1.0F, 4.0F, -3.0F));
        tusk_right_c.addOrReplaceChild("tusk_right_c_r1", CubeListBuilder.create().texOffs(108, 0).addBox(-0.9175F, -1.9812F, -0.547F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, -2.0F, 2.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition tusk_right_d = tusk_right_c.addOrReplaceChild("tusk_right_d", CubeListBuilder.create(), PartPose.offset(1.0F, 1.0F, -4.0F));
        tusk_right_d.addOrReplaceChild("tusk_left_d_r2", CubeListBuilder.create().texOffs(82, 109).addBox(-0.9175F, -1.9812F, -0.547F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.6F, -1.6636F, -0.3477F, 0.0317F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 20.5F, 0.5236F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 92).addBox(-1.5F, 4.5572F, 12.9887F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1806F, -17.9252F, 9.5743F, -1.7453F, 0.0F, 0.0F));

        PartDefinition leg_front_left = partdefinition.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(76, 17).addBox(-3.0F, -5.0F, -3.7F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 10.0F, -9.0F));
        leg_front_left.addOrReplaceChild("leg_front_left_c_r1", CubeListBuilder.create().texOffs(74, 99).addBox(-10.0F, 13.9128F, -11.1038F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -4.0F, 7.2F, 0.0F, 0.0F, 0.0F));
        leg_front_left.addOrReplaceChild("leg_front_left_b_r1", CubeListBuilder.create().texOffs(52, 84).addBox(4.0F, 11.9128F, -11.1038F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -9.9F, 9.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg_front_right = partdefinition.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(74, 52).addBox(-4.0F, -5.0F, -3.7F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 10.0F, -9.0F));
        leg_front_right.addOrReplaceChild("leg_front_right_c_r1", CubeListBuilder.create().texOffs(98, 99).addBox(-10.0F, 13.9128F, -11.1038F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -4.0F, 7.2F, 0.0F, 0.0F, 0.0F));
        leg_front_right.addOrReplaceChild("leg_front_right_b_r1", CubeListBuilder.create().texOffs(76, 84).addBox(-10.0F, 11.9128F, -11.1038F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -10.0F, 9.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg_back_left = partdefinition.addOrReplaceChild("leg_back_left", CubeListBuilder.create()
                .texOffs(74, 35).addBox(-4.0F, -4.6715F, -3.7779F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(100, 84).addBox(-2.5F, 11.3285F, -3.4779F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 11.0F, 15.0F));
        leg_back_left.addOrReplaceChild("leg_back_left_b_r1", CubeListBuilder.create().texOffs(26, 92).addBox(4.0F, 14.0F, 13.1F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -11.6715F, -14.5779F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg_back_right = partdefinition.addOrReplaceChild("leg_back_right", CubeListBuilder.create()
                .texOffs(76, 0).addBox(-4.0F, -4.8351F, -3.9092F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(102, 52).addBox(-3.5F, 11.1649F, -3.6092F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 11.0F, 15.0F));
        leg_back_right.addOrReplaceChild("leg_back_right_b_r1", CubeListBuilder.create().texOffs(50, 99).addBox(-10.0F, 14.0F, 13.1F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -11.8351F, -14.7092F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shoulder = partdefinition.addOrReplaceChild("shoulder", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -8.0F));
        shoulder.addOrReplaceChild("shoulder_r1", CubeListBuilder.create().texOffs(52, 66).addBox(-6.0F, 0.0F, -12.0F, 12.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 8.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void applyAnimation(AnimationDefinition definition, float ageInTicks, float weight) {
        body.resetPose();
        head.resetPose();
        trunk_a.resetPose();
        trunk_b.resetPose();
        trunk_c.resetPose();
        trunk_d.resetPose();
        tusk_left.resetPose();
        tusk_left_a.resetPose();
        tusk_left_b.resetPose();
        tusk_left_c.resetPose();
        tusk_left_d.resetPose();
        tusk_right.resetPose();
        tusk_right_a.resetPose();
        tusk_right_b.resetPose();
        tusk_right_c.resetPose();
        tusk_right_d.resetPose();
        tail.resetPose();
        leg_front_left.resetPose();
        leg_front_right.resetPose();
        leg_back_left.resetPose();
        leg_back_right.resetPose();
        shoulder.resetPose();
        bakedAnimations.computeIfAbsent(definition, d -> d.bake(this.root)).apply((long)(ageInTicks * 50F), weight);
    }

    @Override
    public void setupAnim(MammothRenderState state) {
        
    }
}
