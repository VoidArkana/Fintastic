package net.voidarkana.fintastic.client.models.entity.moonies;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.MoonyAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Moony;

public class MoonyTallModel<T extends Moony> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart fins;
	private final ModelPart dorsalfin;
	private final ModelPart analfin;
	private final ModelPart tailfin;
	private final ModelPart PectoralFinL;
	private final ModelPart PectoralFinR;

	public MoonyTallModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.fins = this.body.getChild("fins");
		this.dorsalfin = this.fins.getChild("dorsalfin");
		this.analfin = this.fins.getChild("analfin");
		this.tailfin = this.fins.getChild("tailfin");
		this.PectoralFinL = this.fins.getChild("PectoralFinL");
		this.PectoralFinR = this.fins.getChild("PectoralFinR");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -0.75F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -1.85F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.1F, -0.7854F, 0.0F, 0.0F));

		PartDefinition fins = body.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 2.6F));

		PartDefinition dorsalfin = fins.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(13, 12).addBox(0.0F, -3.0F, -1.55F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.5F, 1.05F));

		PartDefinition analfin = fins.addOrReplaceChild("analfin", CubeListBuilder.create(), PartPose.offset(0.0F, 2.2F, 1.3F));

		PartDefinition cube_r2 = analfin.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -2.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.8F, 0.2F, -1.5708F, 0.0F, 0.0F));

		PartDefinition tailfin = fins.addOrReplaceChild("tailfin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 3.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r3 = tailfin.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 0).addBox(0.0F, -2.65F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.0249F, 0.3107F, -1.5708F, 0.0F, 0.0F));

		PartDefinition PectoralFinL = fins.addOrReplaceChild("PectoralFinL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.5F, -1.15F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r4 = PectoralFinL.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 6).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.05F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition PectoralFinR = fins.addOrReplaceChild("PectoralFinR", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.5F, -1.15F, 0.0F, -1.1781F, 0.0F));

		PartDefinition cube_r5 = PectoralFinR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(18, 6).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Moony pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.isInWater()){
			this.animateWalk(MoonyAnims.SWIM, pLimbSwing, pLimbSwingAmount, 2f, 3f);
		}else {
			this.applyStatic(MoonyAnims.MOONYTALL_FLOP);
		}

		this.animateIdle(pEntity.idleAnimationState, MoonyAnims.IDLE, pAgeInTicks, 1.0F, 1-Math.abs(pLimbSwingAmount));

		this.animate(pEntity.flopAnimationState, MoonyAnims.FLOP, pAgeInTicks, 1.0F);

		this.swim_rot.xRot = pHeadPitch * ((float)Math.PI / 180F);
		this.swim_rot.zRot = pNetHeadYaw * (((float)Math.PI / 180F)/2);
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

	@Override
	public ModelPart root() {
		return root;
	}
}