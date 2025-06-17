package net.voidarkana.fintastic.client.models.entity.arapaima;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.ArapaimaAnims;
import net.voidarkana.fintastic.client.animation.MinnowAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.ArapaimaEntity;

public class ArapaimaModel<T extends ArapaimaEntity> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart rightfin;
	private final ModelPart leftfin;
	private final ModelPart leftpelvicfin;
	private final ModelPart rightpelvicfin;
	private final ModelPart torsoend;
	private final ModelPart tailfin;
	private final ModelPart analfin;
	private final ModelPart dorsalfin;

	public ArapaimaModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.mouth = this.head.getChild("mouth");
		this.rightfin = this.torso.getChild("rightfin");
		this.leftfin = this.torso.getChild("leftfin");
		this.leftpelvicfin = this.torso.getChild("leftpelvicfin");
		this.rightpelvicfin = this.torso.getChild("rightpelvicfin");
		this.torsoend = this.body.getChild("torsoend");
		this.tailfin = this.torsoend.getChild("tailfin");
		this.analfin = this.torsoend.getChild("analfin");
		this.dorsalfin = this.torsoend.getChild("dorsalfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(-0.5F, -9.0F, -25.0F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -6.0F, -31.0F, 9.0F, 12.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 28.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 83).addBox(-3.5F, -2.75F, -9.0F, 7.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.75F, -30.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(42, 95).addBox(-3.0F, -4.0F, -0.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, -9.5F));

		PartDefinition rightfin = torso.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4.5F, 6.0F, -25.5F, 0.0F, 0.0F, 1.1345F));

		PartDefinition leftfin = torso.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, -25.5F, 0.0F, 0.0F, -1.1345F));

		PartDefinition leftpelvicfin = torso.addOrReplaceChild("leftpelvicfin", CubeListBuilder.create().texOffs(42, 84).addBox(0.0F, -0.5F, -1.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.8F, 6.1F, -3.5F, 0.0F, 0.0F, -1.1345F));

		PartDefinition rightpelvicfin = torso.addOrReplaceChild("rightpelvicfin", CubeListBuilder.create().texOffs(42, 84).addBox(0.0F, -0.5F, -1.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4.7F, 6.1F, -3.5F, 0.0F, 0.0F, 1.1345F));

		PartDefinition torsoend = body.addOrReplaceChild("torsoend", CubeListBuilder.create().texOffs(0, 43).addBox(-3.5F, -6.0F, 0.0F, 7.0F, 12.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 28.0F));

		PartDefinition tailfin = torsoend.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 85).addBox(-0.5F, -4.0F, 0.0F, 0.0F, 8.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.5F, 0.0F, 30.0F));

		PartDefinition analfin = torsoend.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(46, 14).addBox(0.0F, -3.0F, -15.5F, 0.0F, 8.0F, 31.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 6.0F, 17.5F));

		PartDefinition dorsalfin = torsoend.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(46, 29).addBox(0.0F, -4.5F, -16.5F, 0.0F, 7.0F, 33.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -6.5F, 16.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(ArapaimaEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.isInWaterOrBubble()){
			this.head.xRot = (((headPitch * ((float) Math.PI / 180F))/8));
			this.body.xRot = (((headPitch * ((float) Math.PI / 180F))/8));
			this.torsoend.xRot = (-((headPitch * ((float) Math.PI / 180F))/4));

			this.head.yRot = (pEntity.currentRoll);
			this.body.yRot = (pEntity.currentRoll/2);
			this.torsoend.yRot = (-pEntity.currentRoll);

			this.swim_rot.xRot = headPitch * ((float)Math.PI / 180F)/2;
			this.swim_rot.zRot = netHeadYaw * (((float)Math.PI / 180F)/2);

			this.animateIdle(pEntity.idleAnimationState, ArapaimaAnims.IDLE_SWIM, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));

			this.animateWalk(ArapaimaAnims.SWIM, pLimbSwing, pLimbSwingAmount*5f, 2f, 3f);
		}
		else {
			this.swim_rot.resetPose();
			this.head.resetPose();
			this.torsoend.resetPose();
			this.body.resetPose();

			this.animateIdle(pEntity.idleAnimationState, ArapaimaAnims.GROUND_IDLE, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));
			this.animateWalk(ArapaimaAnims.CRAWL, pLimbSwing, pLimbSwingAmount*5f, 2f, 3f);
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