package net.voidarkana.fintastic.client.models.entity.dwarf_frog;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.DwarfFrogAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.DwarfFrog;

public class DwarfFrogModel<T extends DwarfFrog> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart frog;
	private final ModelPart body;
	private final ModelPart arm_left;
	private final ModelPart arm_right;
	private final ModelPart legs;
	private final ModelPart leg_left;
	private final ModelPart foot_left;
	private final ModelPart leg_right;
	private final ModelPart foot_right;

	public DwarfFrogModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.frog = this.swim_rot.getChild("frog");
		this.body = this.frog.getChild("body");
		this.arm_left = this.body.getChild("arm_left");
		this.arm_right = this.body.getChild("arm_right");
		this.legs = this.frog.getChild("legs");
		this.leg_left = this.legs.getChild("leg_left");
		this.foot_left = this.leg_left.getChild("foot_left");
		this.leg_right = this.legs.getChild("leg_right");
		this.foot_right = this.leg_right.getChild("foot_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.25F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -2.5F));

		PartDefinition frog = swim_rot.addOrReplaceChild("frog", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = frog.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

		PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.5F, 0.5F, -1.0F));

		PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.5F, 0.5F, -1.0F));

		PartDefinition legs = frog.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 3.5F));

		PartDefinition leg_left = legs.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(9, 12).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, 0.0F, 1.5F));

		PartDefinition leg_right = legs.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-1.5F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

		PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(9, 12).mirror().addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.5F, 0.0F, 1.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateIdle(entity.idleAnimationState, DwarfFrogAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOutsideWater()/3f-Math.abs(limbSwing)));
		this.animateIdle(entity.idleAnimationState, DwarfFrogAnims.BEACHED, ageInTicks, 1.0F, Math.max(0, entity.getTicksOutsideWater()/3f-Math.abs(limbSwing)));
		this.animateIdle(entity.idleAnimationState, DwarfFrogAnims.FALLING, ageInTicks, 1.0F, Math.max(0, 1-entity.getTicksOnGround()/3f-entity.getTicksOutsideWater()/3f-Math.abs(limbSwing)));

		this.animateIdle(entity.idleAnimationState, DwarfFrogAnims.POSE_WATER, ageInTicks, 1.0F, 1-entity.getTicksOutsideWater()/3f);
		this.animateIdle(entity.idleAnimationState, DwarfFrogAnims.POSE_LAND, ageInTicks, 1.0F, entity.getTicksOutsideWater()/3f);

		this.animateWalk(DwarfFrogAnims.SWIM, limbSwing*2, limbSwingAmount, 2f, Mth.lerp(entity.getTicksOutsideWater()/3f,3f,0));
		this.animateWalk(DwarfFrogAnims.CRAWL, limbSwing*2, limbSwingAmount, 2f, Mth.lerp(entity.getTicksOutsideWater()/3f,0,3f));

		this.swim_rot.xRot = Mth.lerp(entity.getTicksOutsideWater()/3f,headPitch * ((float)Math.PI / 180F),0) ;
		this.swim_rot.zRot = Mth.lerp(entity.getTicksOutsideWater()/3f,netHeadYaw * ((float)Math.PI / 180F)/2,0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}