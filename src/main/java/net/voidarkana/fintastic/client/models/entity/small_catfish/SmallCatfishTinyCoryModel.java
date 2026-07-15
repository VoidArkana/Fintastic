package net.voidarkana.fintastic.client.models.entity.small_catfish;

import net.voidarkana.fintastic.client.animation.SmallCatfishCorysAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class SmallCatfishTinyCoryModel<T extends SmallCatfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart dorsalfin;
	private final ModelPart tail;
	private final ModelPart whiskers;
	private final ModelPart whiskers_fr;
	private final ModelPart whiskers_fl;
	private final ModelPart pectoralfins;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;

	public SmallCatfishTinyCoryModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityTranslucent);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.tail = this.body.getChild("tail");
		this.whiskers = this.body.getChild("whiskers");
		this.whiskers_fr = this.whiskers.getChild("whiskers_fr");
		this.whiskers_fl = this.whiskers.getChild("whiskers_fl");
		this.pectoralfins = this.body.getChild("pectoralfins");
		this.fin_fr = this.pectoralfins.getChild("fin_fr");
		this.fin_fl = this.pectoralfins.getChild("fin_fl");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.9F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(0.0F, -0.5F, -0.9F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.9F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(5, 5).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, -0.9F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 1.1F));

		PartDefinition whiskers = body.addOrReplaceChild("whiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -1.9F));

		PartDefinition whiskers_fr = whiskers.addOrReplaceChild("whiskers_fr", CubeListBuilder.create().texOffs(9, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition whiskers_fl = whiskers.addOrReplaceChild("whiskers_fl", CubeListBuilder.create().texOffs(9, 1).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition pectoralfins = body.addOrReplaceChild("pectoralfins", CubeListBuilder.create(), PartPose.offset(0.0F, 0.9F, -0.9F));

		PartDefinition fin_fr = pectoralfins.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(8, 5).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition fin_fl = pectoralfins.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(8, 5).mirror().addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			limbSwing /= 2;

		this.animateIdle(entity.idleAnimationState, SmallCatfishCorysAnims.POSE, ageInTicks, 1.0F, Math.max(0, 1-(entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishCorysAnims.POSE_BTM, ageInTicks, 1.0f, Math.max(0, (entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishCorysAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-(entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)-Math.abs(limbSwingAmount)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishCorysAnims.IDLE_GROUND, ageInTicks, 1.0f, Math.max(0, (entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)-Math.abs(limbSwingAmount)));

		this.animateIdle(entity.idleAnimationState, SmallCatfishCorysAnims.FLOP, ageInTicks, 1.0f, entity.getTicksOutsideWater()/3f);

		this.animateWalk(SmallCatfishCorysAnims.SWIM, limbSwing*3, limbSwingAmount*5f, 2f, Math.max(0,3f*(1-(entity.getTicksOutsideWater()/3f)-entity.getTicksOnGround()/3f)));
		this.animateWalk(SmallCatfishCorysAnims.SWIM_BOTTOM, limbSwing*3, limbSwingAmount*5f, 2f, Math.max(0,(3f*(entity.getTicksOnGround()/3f-(entity.getTicksOutsideWater()/3f)))));

		this.swim_control.xRot = Mth.lerp( entity.getTicksOutsideWater()/5f,
				headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}