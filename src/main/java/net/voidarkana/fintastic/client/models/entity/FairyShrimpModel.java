package net.voidarkana.fintastic.client.models.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.FairyShrimpAnims;
import net.voidarkana.fintastic.client.animation.FeatherbackAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.FairyShrimp;

public class FairyShrimpModel<T extends FairyShrimp> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart tail_tip;
	private final ModelPart legleft6;
	private final ModelPart legleft7;
	private final ModelPart legleft8;
	private final ModelPart rightleg6;
	private final ModelPart rightleg7;
	private final ModelPart rightleg8;
	private final ModelPart leftlegs;
	private final ModelPart legleft2;
	private final ModelPart legleft3;
	private final ModelPart legleft4;
	private final ModelPart legleft5;
	private final ModelPart rightlegs;
	private final ModelPart rightleg2;
	private final ModelPart rightleg3;
	private final ModelPart rightleg4;
	private final ModelPart rightleg5;
	private final ModelPart head;
	private final ModelPart antenna;
	private final ModelPart legleft1;
	private final ModelPart rightleg1;

	public FairyShrimpModel(ModelPart root) {
        super(0.6F, 1, RenderType::entityCutoutNoCull);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.tail = this.body.getChild("tail");
		this.tail_tip = this.tail.getChild("tail_tip");
		this.legleft6 = this.tail.getChild("legleft6");
		this.legleft7 = this.tail.getChild("legleft7");
		this.legleft8 = this.tail.getChild("legleft8");
		this.rightleg6 = this.tail.getChild("rightleg6");
		this.rightleg7 = this.tail.getChild("rightleg7");
		this.rightleg8 = this.tail.getChild("rightleg8");
		this.leftlegs = this.body.getChild("leftlegs");
		this.legleft2 = this.leftlegs.getChild("legleft2");
		this.legleft3 = this.leftlegs.getChild("legleft3");
		this.legleft4 = this.leftlegs.getChild("legleft4");
		this.legleft5 = this.leftlegs.getChild("legleft5");
		this.rightlegs = this.body.getChild("rightlegs");
		this.rightleg2 = this.rightlegs.getChild("rightleg2");
		this.rightleg3 = this.rightlegs.getChild("rightleg3");
		this.rightleg4 = this.rightlegs.getChild("rightleg4");
		this.rightleg5 = this.rightlegs.getChild("rightleg5");
		this.head = this.body.getChild("head");
		this.antenna = this.head.getChild("antenna");
		this.legleft1 = this.head.getChild("legleft1");
		this.rightleg1 = this.head.getChild("rightleg1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -3.5F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 10).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 4.0F));

		PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(5, 2).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition legleft6 = tail.addOrReplaceChild("legleft6", CubeListBuilder.create().texOffs(23, 28).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.5F));

		PartDefinition legleft7 = tail.addOrReplaceChild("legleft7", CubeListBuilder.create().texOffs(23, 27).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 1.5F));

		PartDefinition legleft8 = tail.addOrReplaceChild("legleft8", CubeListBuilder.create().texOffs(23, 26).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 2.5F));

		PartDefinition rightleg6 = tail.addOrReplaceChild("rightleg6", CubeListBuilder.create().texOffs(23, 28).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 0.5F));

		PartDefinition rightleg7 = tail.addOrReplaceChild("rightleg7", CubeListBuilder.create().texOffs(23, 27).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 1.5F));

		PartDefinition rightleg8 = tail.addOrReplaceChild("rightleg8", CubeListBuilder.create().texOffs(23, 26).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 2.5F));

		PartDefinition leftlegs = body.addOrReplaceChild("leftlegs", CubeListBuilder.create(), PartPose.offset(-1.5F, -0.5F, 2.0F));

		PartDefinition legleft2 = leftlegs.addOrReplaceChild("legleft2", CubeListBuilder.create().texOffs(21, 20).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.5F));

		PartDefinition legleft3 = leftlegs.addOrReplaceChild("legleft3", CubeListBuilder.create().texOffs(21, 19).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -0.5F));

		PartDefinition legleft4 = leftlegs.addOrReplaceChild("legleft4", CubeListBuilder.create().texOffs(21, 18).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition legleft5 = leftlegs.addOrReplaceChild("legleft5", CubeListBuilder.create().texOffs(23, 29).mirror().addBox(-6.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition rightlegs = body.addOrReplaceChild("rightlegs", CubeListBuilder.create(), PartPose.offset(1.5F, -0.5F, 2.0F));

		PartDefinition rightleg2 = rightlegs.addOrReplaceChild("rightleg2", CubeListBuilder.create().texOffs(21, 20).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.5F));

		PartDefinition rightleg3 = rightlegs.addOrReplaceChild("rightleg3", CubeListBuilder.create().texOffs(21, 19).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.5F));

		PartDefinition rightleg4 = rightlegs.addOrReplaceChild("rightleg4", CubeListBuilder.create().texOffs(21, 18).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition rightleg5 = rightlegs.addOrReplaceChild("rightleg5", CubeListBuilder.create().texOffs(23, 29).addBox(0.0F, 0.0F, -0.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, -0.6667F, -3.95F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 13).mirror().addBox(1.5F, -1.1667F, -4.15F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(28, 13).addBox(-4.5F, -1.1667F, -4.15F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1667F, -0.05F));

		PartDefinition antenna = head.addOrReplaceChild("antenna", CubeListBuilder.create().texOffs(0, 13).addBox(-4.5F, 0.0F, -5.0F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.8333F, -3.95F));

		PartDefinition legleft1 = head.addOrReplaceChild("legleft1", CubeListBuilder.create().texOffs(21, 21).mirror().addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -0.6667F, -0.45F));

		PartDefinition rightleg1 = head.addOrReplaceChild("rightleg1", CubeListBuilder.create().texOffs(22, 21).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -0.6667F, -0.45F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;
		this.animateIdle(pEntity.idleAnimationState, FairyShrimpAnims.LEGS, pAgeInTicks, 1.5F, 1);
		this.animateIdle(pEntity.idleAnimationState, FairyShrimpAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, FairyShrimpAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateIdle(pEntity.circleAnimationState, FairyShrimpAnims.CIRCLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));

		this.animateWalk(FairyShrimpAnims.SWIM, pLimbSwing, pLimbSwingAmount*5f, 2f, (1-(pEntity.getTicksOutsideWater()/3f)));
//		this.animateWalk(FairyShrimpAnims.LEGS, pLimbSwing, pLimbSwingAmount*5f, 2f, (1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F)/1.5f, 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}