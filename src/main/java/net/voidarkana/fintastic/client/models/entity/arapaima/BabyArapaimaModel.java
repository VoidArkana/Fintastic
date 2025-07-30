package net.voidarkana.fintastic.client.models.entity.arapaima;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.ArapaimaAnims;
import net.voidarkana.fintastic.client.animation.BabyArapaimaAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.ArapaimaEntity;

public class BabyArapaimaModel<T extends ArapaimaEntity> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart dorsalfin;
	private final ModelPart analfin;
	private final ModelPart leftfin;
	private final ModelPart rightfin;
	private final ModelPart tailfin;
	private final ModelPart head;

	public BabyArapaimaModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.torso = this.body.getChild("torso");
		this.dorsalfin = this.torso.getChild("dorsalfin");
		this.analfin = this.torso.getChild("analfin");
		this.leftfin = this.torso.getChild("leftfin");
		this.rightfin = this.torso.getChild("rightfin");
		this.tailfin = this.torso.getChild("tailfin");
		this.head = this.torso.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -5.5F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -13.0F, 3.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.5F));

		PartDefinition dorsalfin = torso.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(3, 14).addBox(0.5F, -2.0F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, -1.5F, -2.5F));

		PartDefinition analfin = torso.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(14, 14).addBox(0.0F, 0.0F, -3.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.5F, -2.0F));

		PartDefinition leftfin = torso.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(12, 23).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.5F, 1.5F, -11.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rightfin = torso.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(12, 23).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, 1.5F, -11.5F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tailfin = torso.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(17, 23).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(2, 24).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -13.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(ArapaimaEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.isInWaterOrBubble()){
			this.swim_rot.xRot = headPitch * ((float)Math.PI / 180F);
			this.swim_rot.zRot = netHeadYaw * (((float)Math.PI / 180F)/2);

			this.animateIdle(pEntity.idleAnimationState, BabyArapaimaAnims.IDLE, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));

			this.animateWalk(BabyArapaimaAnims.SWIM, pLimbSwing/2, pLimbSwingAmount/2, 2f, 3f);
		}
		else {
			this.swim_rot.resetPose();

			this.animateIdle(pEntity.idleAnimationState, BabyArapaimaAnims.CRAWL_IDLE, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));
			this.animateWalk(BabyArapaimaAnims.CRAWL_WALK, pLimbSwing/2, pLimbSwingAmount/2, 2f, 3f);
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