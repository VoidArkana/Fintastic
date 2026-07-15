package net.voidarkana.fintastic.client.models.entity.catfish;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.CatfishAnims;
import net.voidarkana.fintastic.common.entity.custom.Catfish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;

public class CatfishModelFlat<T extends Catfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart dorsalfin;
	private final ModelPart fin_bl;
	private final ModelPart fin_br;
	private final ModelPart head;
	private final ModelPart whiskers_fl;
	private final ModelPart whiskers_fr;
	private final ModelPart fin_fl;
	private final ModelPart fin_fr;
	private final ModelPart tail;
	private final ModelPart tailtopfin;
	private final ModelPart analfin;
	private final ModelPart tailfin;

	public CatfishModelFlat(ModelPart root) {
		super(0.6f, 1, RenderType::entityCutout);
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.fin_bl = this.body.getChild("fin_bl");
		this.fin_br = this.body.getChild("fin_br");
		this.head = this.body.getChild("head");
		this.whiskers_fl = this.head.getChild("whiskers_fl");
		this.whiskers_fr = this.head.getChild("whiskers_fr");
		this.fin_fl = this.body.getChild("fin_fl");
		this.fin_fr = this.body.getChild("fin_fr");
		this.tail = this.body.getChild("tail");
		this.tailtopfin = this.tail.getChild("tailtopfin");
		this.analfin = this.tail.getChild("analfin");
		this.tailfin = this.tail.getChild("tailfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -5.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -2.5F, -1.5F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -0.5F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -0.5F));

		PartDefinition fin_bl = body.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(52, 51).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.5F, 4.0F));

		PartDefinition fin_br = body.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(52, 51).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.5F, 4.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 39).addBox(-3.0F, -0.5F, -7.0F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.5F));

		PartDefinition whiskers_fl = head.addOrReplaceChild("whiskers_fl", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.5F, -6.5F));

		PartDefinition whiskers_fr = head.addOrReplaceChild("whiskers_fr", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.5F, -6.5F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(34, 34).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.5F, -1.0F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(34, 34).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.5F, -1.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 49).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 9.5F));

		PartDefinition tailtopfin = tail.addOrReplaceChild("tailtopfin", CubeListBuilder.create().texOffs(32, 51).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition analfin = tail.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(18, 49).addBox(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.POSE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.FLOP_FLAT, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(CatfishAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}