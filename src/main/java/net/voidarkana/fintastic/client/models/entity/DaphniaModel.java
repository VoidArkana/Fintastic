package net.voidarkana.fintastic.client.models.entity;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.DaphniaAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Daphnia;

public class DaphniaModel<T extends Daphnia> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart daphnia;
	private final ModelPart body;
	private final ModelPart insides;
	private final ModelPart head;
	private final ModelPart antennaleft;
	private final ModelPart antennaright;

	public DaphniaModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityTranslucent);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.daphnia = this.swim_control.getChild("daphnia");
		this.body = this.daphnia.getChild("body");
		this.insides = this.body.getChild("insides");
		this.head = this.daphnia.getChild("head");
		this.antennaleft = this.head.getChild("antennaleft");
		this.antennaright = this.head.getChild("antennaright");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 1.75F));

		PartDefinition daphnia = swim_control.addOrReplaceChild("daphnia", CubeListBuilder.create(), PartPose.offset(0.0F, 1.8F, -1.65F));

		PartDefinition body = daphnia.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -5.5F, 5.0F, 8.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -2.0F, 2.0F));

		PartDefinition insides = body.addOrReplaceChild("insides", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -6.0F, 0.24F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(0.5F, 6.5F, -4.5F));

		PartDefinition head = daphnia.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, -3.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(-1.5F, -3.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -1.8F, 1.4F));

		PartDefinition antennaleft = head.addOrReplaceChild("antennaleft", CubeListBuilder.create().texOffs(24, 11).addBox(0.0F, -8.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -3.5F, -0.5F));

		PartDefinition antennaright = head.addOrReplaceChild("antennaright", CubeListBuilder.create().texOffs(24, 11).addBox(0.0F, -8.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.5F, -0.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (this.young){
			this.applyStatic(DaphniaAnims.BABY);
			pLimbSwing /= 2;
		}

		this.animateIdle(pEntity.idleAnimationState, DaphniaAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.jumpAnimationState, DaphniaAnims.JUMP, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, DaphniaAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(DaphniaAnims.SWIM, pLimbSwing, pLimbSwingAmount*5f, 2f, (1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F)/1.5f, 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}