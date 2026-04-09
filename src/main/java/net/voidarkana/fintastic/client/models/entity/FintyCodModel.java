package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.CodAnims;
import net.voidarkana.fintastic.client.animation.SharkminnowAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.FintasticCod;

public class FintyCodModel<T extends FintasticCod> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart barbel;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart tailtopfin;
	private final ModelPart analfin_2;
	private final ModelPart analfin;
	private final ModelPart dorsalfin;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;
	private final ModelPart fin_br;
	private final ModelPart fin_bl;

	public FintyCodModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityCutout);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.head = this.body.getChild("head");
		this.barbel = this.head.getChild("barbel");
		this.tail = this.body.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
		this.tailtopfin = this.tail.getChild("tailtopfin");
		this.analfin_2 = this.tail.getChild("analfin_2");
		this.analfin = this.body.getChild("analfin");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.fin_fr = this.body.getChild("fin_fr");
		this.fin_fl = this.body.getChild("fin_fl");
		this.fin_br = this.body.getChild("fin_br");
		this.fin_bl = this.body.getChild("fin_bl");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -5.5F, -3.5F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -2.5F, 4.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 15).addBox(-2.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -2.5F));

		PartDefinition barbel = head.addOrReplaceChild("barbel", CubeListBuilder.create().texOffs(28, 11).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -4.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 7.5F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -3.0F, -0.5F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.5F));

		PartDefinition tailtopfin = tail.addOrReplaceChild("tailtopfin", CubeListBuilder.create().texOffs(10, 28).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition analfin_2 = tail.addOrReplaceChild("analfin_2", CubeListBuilder.create().texOffs(28, 5).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition analfin = body.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(28, 8).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 2.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -5.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -1.5F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.5F, -2.5F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(28, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.5F, -2.5F));

		PartDefinition fin_br = body.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(18, 7).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 2.5F, 0.0F));

		PartDefinition fin_bl = body.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(18, 7).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			limbSwing /= 2;
		if (!entity.isAlive())
			this.applyStatic(SharkminnowAnims.POSE);

		this.animateIdle(entity.idleAnimationState, CodAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f-Math.abs(limbSwing)));
		this.animateIdle(entity.idleAnimationState, CodAnims.FLOP, ageInTicks, 1.0F,entity.getTicksOutsideWater()/3f);

		this.animateWalk(CodAnims.SWIM, limbSwing, limbSwingAmount, 2f, Mth.lerp(entity.getTicksOutsideWater()/3f,3f,0));

		this.swim_rot.xRot = Mth.lerp(entity.getTicksOutsideWater()/3f,headPitch * ((float)Math.PI / 180F),0) ;
		this.swim_rot.zRot = Mth.lerp(entity.getTicksOutsideWater()/3f,netHeadYaw * ((float)Math.PI / 180F)/2,0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}