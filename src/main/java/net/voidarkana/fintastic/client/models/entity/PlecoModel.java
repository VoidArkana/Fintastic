package net.voidarkana.fintastic.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.voidarkana.fintastic.client.animation.PlecoAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Pleco;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class PlecoModel<T extends Pleco> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart body_no_head;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart dorsalfin;
	private final ModelPart rightfin2;
	private final ModelPart leftfin2;
	private final ModelPart head;
	private final ModelPart head_only;
	private final ModelPart rightfin1;
	private final ModelPart leftfin1;

	public PlecoModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityCutout);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.body_no_head = this.body.getChild("body_no_head");
		this.tail = this.body_no_head.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
		this.dorsalfin = this.body_no_head.getChild("dorsalfin");
		this.rightfin2 = this.body_no_head.getChild("rightfin2");
		this.leftfin2 = this.body_no_head.getChild("leftfin2");
		this.head = this.body.getChild("head");
		this.head_only = this.head.getChild("head_only");
		this.rightfin1 = this.head.getChild("rightfin1");
		this.leftfin1 = this.head.getChild("leftfin1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -2.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_no_head = body.addOrReplaceChild("body_no_head", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, -1.0F));

		PartDefinition tail = body_no_head.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(30, 23).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(28, 6).addBox(0.0F, -5.0F, -0.5F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.5F));

		PartDefinition dorsalfin = body_no_head.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(0, -2).addBox(0.0F, -9.0F, -0.5F, 0.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.5F));

		PartDefinition rightfin2 = body_no_head.addOrReplaceChild("rightfin2", CubeListBuilder.create(), PartPose.offset(-3.0F, 1.0F, 7.0F));

		PartDefinition rightfin2_r1 = rightfin2.addOrReplaceChild("rightfin2_r1", CubeListBuilder.create().texOffs(10, 33).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leftfin2 = body_no_head.addOrReplaceChild("leftfin2", CubeListBuilder.create(), PartPose.offset(3.0F, 1.0F, 7.0F));

		PartDefinition leftfin2_r1 = leftfin2.addOrReplaceChild("leftfin2_r1", CubeListBuilder.create().texOffs(10, 33).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.5F, 2.1F, -1.0F));

		PartDefinition head_only = head.addOrReplaceChild("head_only", CubeListBuilder.create().texOffs(24, 0).addBox(-3.5F, -2.5F, -5.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -1.0F));

		PartDefinition rightfin1 = head.addOrReplaceChild("rightfin1", CubeListBuilder.create(), PartPose.offset(-3.5F, -0.1F, -0.5F));

		PartDefinition rightfin1_r1 = rightfin1.addOrReplaceChild("rightfin1_r1", CubeListBuilder.create().texOffs(30, 30).addBox(0.0F, -1.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition leftfin1 = head.addOrReplaceChild("leftfin1", CubeListBuilder.create(), PartPose.offset(3.5F, -0.1F, -0.5F));

		PartDefinition leftfin1_r1 = leftfin1.addOrReplaceChild("leftfin1_r1", CubeListBuilder.create().texOffs(30, 30).addBox(0.0F, -1.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -0.5F, 0.0F, 0.0F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;

		this.animateIdle(pEntity.idleAnimationState, PlecoAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-(pEntity.getTicksOnGround()/3f)-(pEntity.getTicksAttached()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, PlecoAnims.IDLE_GROUND, pAgeInTicks, 1.0f, Math.max(0, (pEntity.getTicksAttached()/3f)-(pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, PlecoAnims.BEACHED, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(PlecoAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,3f*(1-(pEntity.getTicksOutsideWater()/3f)-pEntity.getTicksOnGround()/3f)));
		this.animateWalk(PlecoAnims.SWIM_BOTTOM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,(3f*(pEntity.getTicksOnGround()/3f-(pEntity.getTicksOutsideWater()/3f)))));

		this.swim_rot.xRot =
				Mth.lerp(pEntity.getTicksAttached()/3f,
				Mth.lerp( pEntity.getTicksOutsideWater()/5f,
				headPitch * ((float)Math.PI / 180F), 0),
						(float) Math.toRadians(-90));

		this.swim_rot.z = Mth.lerp(pEntity.getTicksAttached()/3f, 0, -3.75f);

	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
//		pPoseStack.pushPose();


		super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
//		pPoseStack.popPose();
	}
}