package net.voidarkana.fintastic.client.models.entity.gourami;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.GouramiAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Gourami;
import net.voidarkana.fintastic.common.entity.custom.base.BreedableWaterAnimal;

public class GouramiSmallModel<T extends Gourami> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart mouth;
	private final ModelPart dorsalfin;
	private final ModelPart analfin;
	private final ModelPart pelvicfinleft;
	private final ModelPart pelvicfinright;
	private final ModelPart leftfin;
	private final ModelPart rightfin;
	private final ModelPart tailfin;

	public GouramiSmallModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.mouth = this.body.getChild("mouth");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.analfin = this.body.getChild("analfin");
		this.pelvicfinleft = this.body.getChild("pelvicfinleft");
		this.pelvicfinright = this.body.getChild("pelvicfinright");
		this.leftfin = this.body.getChild("leftfin");
		this.rightfin = this.body.getChild("rightfin");
		this.tailfin = this.body.getChild("tailfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, -5.0F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -2.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -2.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, -5.5F, -0.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, 2.5F));

		PartDefinition analfin = body.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, 0.5F, -1.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, 2.5F));

		PartDefinition pelvicfinleft = body.addOrReplaceChild("pelvicfinleft", CubeListBuilder.create().texOffs(14, 21).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.0F, 1.5F, 0.0F, 0.0F, 0.0F, -0.7418F));

		PartDefinition pelvicfinright = body.addOrReplaceChild("pelvicfinright", CubeListBuilder.create().texOffs(14, 21).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.7418F));

		PartDefinition leftfin = body.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(26, 10).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition rightfin = body.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(26, 10).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition tailfin = body.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(14, 10).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.5F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Gourami pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateIdle(pEntity.idleAnimationState, GouramiAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.flopAnimationState, GouramiAnims.FLOP, pAgeInTicks, 1.0F,pEntity.getTicksOutsideWater()/3f);

		if (pEntity.isInWaterOrBubble()){
			this.swim_rot.xRot = pHeadPitch * ((float)Math.PI / 180F);
			this.swim_rot.zRot = pNetHeadYaw * (((float)Math.PI / 180F)/2);

			this.animateWalk(GouramiAnims.SWIM, pLimbSwing, pLimbSwingAmount, 2f, 3f);
		}
		else {
			this.swim_rot.resetPose();
			this.applyStatic(GouramiAnims.SMALL_FLOP_OFFSET);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();

		if (this.young){
			poseStack.scale(0.5f, 0.5f, 0.5f);
			poseStack.translate(0, 1.6, 0);
		}else {
			poseStack.translate(0, 0.2, 0);
		}

		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();	}

	@Override
	public ModelPart root() {
		return root;
	}
}