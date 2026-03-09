package net.voidarkana.fintastic.client.models.entity.catfish;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

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

public class CatfishModelPiraiba<T extends Catfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart dorsalfin;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart whisker_bl;
	private final ModelPart whisker_br;
	private final ModelPart whiskers_fr;
	private final ModelPart whiskers_fl;
	private final ModelPart fin_fl;
	private final ModelPart fin_fr;
	private final ModelPart fin_bl;
	private final ModelPart fin_br;
	private final ModelPart tail;
	private final ModelPart tailtopfin;
	private final ModelPart tailfin;
	private final ModelPart analfin;
	private final ModelPart tailfin_tip;

	public CatfishModelPiraiba(ModelPart root) {
		super(0.6f, 1, RenderType::entityCutout);
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.head = this.body.getChild("head");
		this.mouth = this.head.getChild("mouth");
		this.whisker_bl = this.mouth.getChild("whisker_bl");
		this.whisker_br = this.mouth.getChild("whisker_br");
		this.whiskers_fr = this.head.getChild("whiskers_fr");
		this.whiskers_fl = this.head.getChild("whiskers_fl");
		this.fin_fl = this.body.getChild("fin_fl");
		this.fin_fr = this.body.getChild("fin_fr");
		this.fin_bl = this.body.getChild("fin_bl");
		this.fin_br = this.body.getChild("fin_br");
		this.tail = this.body.getChild("tail");
		this.tailtopfin = this.tail.getChild("tailtopfin");
		this.tailfin = this.tail.getChild("tailfin");
		this.analfin = this.tailfin.getChild("analfin");
		this.tailfin_tip = this.tailfin.getChild("tailfin_tip");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -5.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(33, 59).addBox(-5.9584F, -7.5F, -10.7501F, 12.0F, 13.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -0.25F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(21, 46).addBox(0.0416F, -11.0F, 0.0F, 0.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -4.75F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0F, -3.0F, -16.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(80, 48).addBox(-6.0F, 2.0F, -7.0F, 12.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0416F, 1.5F, -10.7501F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(48, 49).addBox(-4.0F, -1.0F, -7.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -8.0F));

		PartDefinition whisker_bl = mouth.addOrReplaceChild("whisker_bl", CubeListBuilder.create().texOffs(40, 31).addBox(0.0F, 0.0F, -0.5F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 1.0F, -4.5F));

		PartDefinition whisker_br = mouth.addOrReplaceChild("whisker_br", CubeListBuilder.create().texOffs(40, 31).addBox(0.0F, 0.0F, -0.5F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, -4.5F));

		PartDefinition whiskers_fr = head.addOrReplaceChild("whiskers_fr", CubeListBuilder.create().texOffs(22, 65).addBox(0.0F, -18.0F, -0.5F, 0.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.0F, -14.5F));

		PartDefinition whiskers_fl = head.addOrReplaceChild("whiskers_fl", CubeListBuilder.create().texOffs(22, 65).addBox(0.0F, -18.0F, -0.5F, 0.0F, 18.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -14.5F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0416F, 5.5F, -10.25F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 0.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.9584F, 5.5F, -10.25F));

		PartDefinition fin_bl = body.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, 0.0F, 0.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0416F, 5.5F, 14.25F));

		PartDefinition fin_br = body.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, 0.0F, 0.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.9584F, 5.5F, 14.25F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(47, 0).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0416F, -0.5F, 15.25F));

		PartDefinition tailtopfin = tail.addOrReplaceChild("tailtopfin", CubeListBuilder.create().texOffs(0, 68).addBox(0.0416F, -5.0F, -0.0001F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0416F, -7.0F, 7.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(57, 22).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, 13.0F));

		PartDefinition analfin = tailfin.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0416F, 0.0F, -0.0001F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0416F, 2.5F, 3.0001F));

		PartDefinition tailfin_tip = tailfin.addOrReplaceChild("tailfin_tip", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, -9.5F, -1.0F, 0.0F, 18.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.POSE_PIRAIBA, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));

		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.IDLE_PIRAIBA, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);
		this.animateIdle(pEntity.idleAnimationState, CatfishAnims.FLOP_PIRAIBA, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(CatfishAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));
		this.animateWalk(CatfishAnims.SWIM_PIRAIBA, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));

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