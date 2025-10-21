package net.voidarkana.fintastic.client.models.entity;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.ArapaimaAnims;
import net.voidarkana.fintastic.client.animation.CoelacanthAnims;
import net.voidarkana.fintastic.client.animation.MoonyAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Coelacanth;

public class CoelacanthModel<T extends Coelacanth> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart backbody;
	private final ModelPart tail;
	private final ModelPart analfin;
	private final ModelPart tailfin;
	private final ModelPart pelvicfinright;
	private final ModelPart adiposefin;
	private final ModelPart pelvicfinleft;
	private final ModelPart head;
	private final ModelPart leftfin;
	private final ModelPart rightfin;
	private final ModelPart dorsalfin;

	public CoelacanthModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.backbody = this.body.getChild("backbody");
		this.tail = this.backbody.getChild("tail");
		this.analfin = this.tail.getChild("analfin");
		this.tailfin = this.tail.getChild("tailfin");
		this.pelvicfinright = this.backbody.getChild("pelvicfinright");
		this.adiposefin = this.backbody.getChild("adiposefin");
		this.pelvicfinleft = this.backbody.getChild("pelvicfinleft");
		this.head = this.body.getChild("head");
		this.leftfin = this.head.getChild("leftfin");
		this.rightfin = this.head.getChild("rightfin");
		this.dorsalfin = this.head.getChild("dorsalfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -13.0F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition backbody = body.addOrReplaceChild("backbody", CubeListBuilder.create().texOffs(31, 27).addBox(-2.5F, -5.0F, -0.5F, 5.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.5F));

		PartDefinition tail = backbody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 2).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.5F));

		PartDefinition analfin = tail.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(50, 51).addBox(0.0F, -0.5F, -1.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.5F, 2.5F, 1.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -6.0F, 0.0F, 0.0F, 12.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition pelvicfinright = backbody.addOrReplaceChild("pelvicfinright", CubeListBuilder.create().texOffs(51, 52).addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.5F, 5.0F, 4.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition adiposefin = backbody.addOrReplaceChild("adiposefin", CubeListBuilder.create().texOffs(2, 52).addBox(0.0F, -5.0F, -1.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, 8.5F));

		PartDefinition pelvicfinleft = backbody.addOrReplaceChild("pelvicfinleft", CubeListBuilder.create().texOffs(51, 45).addBox(0.0F, 0.0F, 2.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, 5.0F, 1.5F, 0.0F, 0.0F, 0.5236F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -15.0F, 7.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 11.0F));

		PartDefinition leftfin = head.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(18, 49).addBox(0.0F, 0.0F, -1.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3.6F, 3.0F, -4.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition rightfin = head.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(18, 49).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, 3.0F, -4.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition dorsalfin = head.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(34, 50).addBox(0.0F, -7.0F, -1.0F, 0.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -6.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Coelacanth pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young){
			this.applyStatic(CoelacanthAnims.BABY);
		}

		this.animateIdle(pEntity.idleAnimationState, CoelacanthAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.flopAnimationState, CoelacanthAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		if (pEntity.isInWaterOrBubble()){
			this.head.xRot = (((headPitch * ((float) Math.PI / 180F))/32));
			this.body.xRot = -(((headPitch * ((float) Math.PI / 180F))/32));
			this.backbody.xRot = (-((headPitch * ((float) Math.PI / 180F))/16));

			this.head.yRot = (pEntity.currentRoll)/2;
			this.body.yRot = (pEntity.currentRoll/4);
			this.backbody.yRot = (-pEntity.currentRoll)/2;

			this.swim_rot.xRot = headPitch * ((float)Math.PI / 180F)/2;
			this.swim_rot.zRot = -netHeadYaw * (((float)Math.PI / 180F)/2);

			this.animateWalk(CoelacanthAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f);
		}
		else {
			this.swim_rot.resetPose();
			this.head.resetPose();
			this.backbody.resetPose();
			this.body.resetPose();
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}