package net.voidarkana.fintastic.client.models.entity;// Made with Blockbench 5.1.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.voidarkana.fintastic.client.animation.CopepodAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Copepod;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CopepodModel<T extends Copepod> extends FintasticModel<T> {
	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart body_only;
	private final ModelPart tail;
	private final ModelPart antenna_r;
	private final ModelPart antenna_l;
	private final ModelPart legs;
	private final ModelPart legs_b;
	private final ModelPart legs_m;
	private final ModelPart legs_f;

	public CopepodModel(ModelPart root) {
        super(0.6f, 1);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.body_only = this.body.getChild("body_only");
		this.tail = this.body.getChild("tail");
		this.antenna_r = this.body.getChild("antenna_r");
		this.antenna_l = this.body.getChild("antenna_l");
		this.legs = this.body.getChild("legs");
		this.legs_b = this.legs.getChild("legs_b");
		this.legs_m = this.legs.getChild("legs_m");
		this.legs_f = this.legs.getChild("legs_f");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -3.5F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body_only = body.addOrReplaceChild("body_only", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -1.5F, 5.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.5F, 7.5F));

		PartDefinition antenna_r = body.addOrReplaceChild("antenna_r", CubeListBuilder.create().texOffs(0, 12).addBox(-12.0F, 0.0F, -1.0F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.5F, -0.5F, -1.5F));

		PartDefinition antenna_l = body.addOrReplaceChild("antenna_l", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(0.0F, 0.5F, -1.0F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(1.5F, -1.0F, -1.5F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.5F));

		PartDefinition legs_b = legs.addOrReplaceChild("legs_b", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition legs1_r1 = legs_b.addOrReplaceChild("legs1_r1", CubeListBuilder.create().texOffs(20, 18).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition legs_m = legs.addOrReplaceChild("legs_m", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legs_2_r1 = legs_m.addOrReplaceChild("legs 2_r1", CubeListBuilder.create().texOffs(20, 20).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition legs_f = legs.addOrReplaceChild("legs_f", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition legs_3_r1 = legs_f.addOrReplaceChild("legs 3_r1", CubeListBuilder.create().texOffs(20, 22).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young){
			this.applyStatic(CopepodAnims.BABY);
			limbSwing /= 2;
		}
		this.animateIdle(entity.idleAnimationState, CopepodAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f-Math.abs(limbSwingAmount)));
		this.animateIdle(entity.idleAnimationState, CopepodAnims.FLOP, ageInTicks, 1.0f, entity.getTicksOutsideWater()/3f);

		this.animateIdle(entity.legsAnimationState, CopepodAnims.IDLE_LEGS, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f));

		this.animateWalk(CopepodAnims.SWIM, limbSwing, limbSwingAmount*5f, 2f, Math.max(0,(1-(entity.getTicksOutsideWater()/3f))));

		this.swim_rot.xRot = Mth.lerp( entity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F)/1.5f, 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}