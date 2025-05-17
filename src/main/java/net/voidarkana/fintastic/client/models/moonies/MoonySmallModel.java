package net.voidarkana.fintastic.client.models.moonies;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.animation.MoonyAnims;
import net.voidarkana.fintastic.client.models.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Moony;

public class MoonySmallModel<T extends Moony> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart fins;
	private final ModelPart dorsalfin;
	private final ModelPart analfin;
	private final ModelPart tailfin;
	private final ModelPart PectoralFinL;
	private final ModelPart PectoralFinR;

	public MoonySmallModel(ModelPart root) {
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

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.45F, -0.1F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -2.65F, -1.35F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -1.9142F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 1.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition fins = body.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1F, 1.4358F));

		PartDefinition dorsalfin = fins.addOrReplaceChild("dorsalfin", CubeListBuilder.create(), PartPose.offset(0.0F, -1.7F, 1.0142F));

		PartDefinition cube_r2 = dorsalfin.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 8).addBox(0.0F, -2.0F, -0.5142F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.6F, -0.7F, -0.7854F, 0.0F, 0.0F));

		PartDefinition analfin = fins.addOrReplaceChild("analfin", CubeListBuilder.create(), PartPose.offset(0.0F, 1.85F, 0.9642F));

		PartDefinition cube_r3 = analfin.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 0).addBox(0.0F, -1.9293F, 0.0565F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.15F, -0.35F, -0.7854F, 0.0F, 0.0F));

		PartDefinition tailfin = fins.addOrReplaceChild("tailfin", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0305F, 2.7991F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r4 = tailfin.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -2.7F, -0.0142F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.0009F, 0.2849F, -1.5708F, 0.0F, 0.0F));

		PartDefinition PectoralFinL = fins.addOrReplaceChild("PectoralFinL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.1F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r5 = PectoralFinL.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 5).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition PectoralFinR = fins.addOrReplaceChild("PectoralFinR", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.1F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r6 = PectoralFinR.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(12, 5).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Moony pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (pEntity.isInWater()){
			this.animateWalk(MoonyAnims.SWIM, pLimbSwing, pLimbSwingAmount, 2f, 3f);
		}else {
			this.applyStatic(MoonyAnims.MOONYSMALL_FLOP);
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