package net.voidarkana.fintastic.client.models.minnows;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.MinnowAnims;
import net.voidarkana.fintastic.client.models.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.MinnowEntity;

public class MinnowSlimModel<T extends MinnowEntity> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart dorsalfin;
	private final ModelPart analfin;
	private final ModelPart leftfin;
	private final ModelPart rightfin;
	private final ModelPart tailfin;

	public MinnowSlimModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.analfin = this.body.getChild("analfin");
		this.leftfin = this.body.getChild("leftfin");
		this.rightfin = this.body.getChild("rightfin");
		this.tailfin = this.body.getChild("tailfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -5.25F, -2.75F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.25F, -1.25F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 6).addBox(-1.0F, -1.25F, -2.25F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -4.0F, -2.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.25F, 2.25F));

		PartDefinition analfin = body.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.75F, 3.25F));

		PartDefinition leftfin = body.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(10, 17).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.0F, 1.75F, 0.25F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rightfin = body.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(10, 17).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.75F, 0.25F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tailfin = body.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(10, 7).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.75F, 4.75F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}


	@Override
	public void setupAnim(MinnowEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.isInWater()){
			this.animateWalk(MinnowAnims.SWIM, pLimbSwing, pLimbSwingAmount, 2f, 3f);
		}else {
			this.applyStatic(MinnowAnims.SLIM_FLOP);
		}

		this.animateIdle(pEntity.idleAnimationState, MinnowAnims.IDLE, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));

		this.animate(pEntity.flopAnimationState, MinnowAnims.FLOP, pAgeInTicks, 1.0F);

		this.swim_rot.xRot = pHeadPitch * ((float)Math.PI / 180F);
		this.swim_rot.zRot = pNetHeadYaw * (((float)Math.PI / 180F)/2);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();

		if (this.young){
			poseStack.scale(0.6f, 0.6f, 0.6f);
			poseStack.translate(0, 1, 0);
		}

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}
}