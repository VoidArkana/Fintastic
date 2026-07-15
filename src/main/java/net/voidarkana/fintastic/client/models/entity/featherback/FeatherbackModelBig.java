package net.voidarkana.fintastic.client.models.entity.featherback;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.FeatherbackAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Featherback;

public class FeatherbackModelBig<T extends Featherback> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart dorsalfin;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart head;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;

	public FeatherbackModelBig(ModelPart root) {
        super(0.6F, 1, RenderType::entityCutout);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.tail = this.body.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
		this.head = this.body.getChild("head");
		this.fin_fr = this.body.getChild("fin_fr");
		this.fin_fl = this.body.getChild("fin_fl");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 3.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -10.0F, -3.0F, 3.0F, 13.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(0.0F, -5.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 4.0F, -6.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -5.0F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 3.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 19).addBox(-1.0F, -2.5F, 0.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 24).addBox(0.0F, -2.5F, 2.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 9.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(28, 24).addBox(0.0F, -3.5F, 2.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(30, 4).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 0).addBox(-1.5F, -2.0F, -6.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -3.0F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(16, 21).addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 3.0F, -0.5F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(16, 21).addBox(0.0F, 0.0F, -1.5F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 3.0F, -0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			limbSwing /= 2;
		this.animateIdle(entity.idleAnimationState, FeatherbackAnims.POSE, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f));
		this.animateIdle(entity.idleAnimationState, FeatherbackAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f-Math.abs(limbSwingAmount)));
		this.animateIdle(entity.idleAnimationState, FeatherbackAnims.FLOP, ageInTicks, 1.0f, entity.getTicksOutsideWater()/3f);

		this.animateWalk(FeatherbackAnims.SWIM, limbSwing*3, limbSwingAmount*5f, 2f, 3f*(1-(entity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( entity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F)/2, 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}