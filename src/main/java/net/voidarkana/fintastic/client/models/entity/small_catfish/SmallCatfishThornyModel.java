package net.voidarkana.fintastic.client.models.entity.small_catfish;

import net.voidarkana.fintastic.client.animation.SmallCatfishThornyAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class SmallCatfishThornyModel<T extends SmallCatfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart whiskers;
	private final ModelPart whiskers_fl;
	private final ModelPart whiskers_fr;
	private final ModelPart dorsalfin;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart tailtopfin;
	private final ModelPart analfin;
	private final ModelPart pectoralfins;
	private final ModelPart fin_fl;
	private final ModelPart fin_fr;
	private final ModelPart pelvicfins;
	private final ModelPart fin_bl;
	private final ModelPart fin_br;

	public SmallCatfishThornyModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityTranslucent);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.head = this.body.getChild("head");
		this.whiskers = this.head.getChild("whiskers");
		this.whiskers_fl = this.whiskers.getChild("whiskers_fl");
		this.whiskers_fr = this.whiskers.getChild("whiskers_fr");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.tail = this.body.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
		this.tailtopfin = this.tail.getChild("tailtopfin");
		this.analfin = this.tail.getChild("analfin");
		this.pectoralfins = this.body.getChild("pectoralfins");
		this.fin_fl = this.pectoralfins.getChild("fin_fl");
		this.fin_fr = this.pectoralfins.getChild("fin_fr");
		this.pelvicfins = this.body.getChild("pelvicfins");
		this.fin_bl = this.pelvicfins.getChild("fin_bl");
		this.fin_br = this.pelvicfins.getChild("fin_br");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -2.75F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(23, 23).addBox(3.0F, -1.5F, -1.75F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F))
		.texOffs(23, 23).addBox(-4.0F, -1.5F, -1.75F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.75F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 10).addBox(-2.0F, -1.55F, -3.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.45F, -2.75F));

		PartDefinition whiskers = head.addOrReplaceChild("whiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 1.45F, -2.0F));

		PartDefinition whiskers_fl = whiskers.addOrReplaceChild("whiskers_fl", CubeListBuilder.create().texOffs(12, 25).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(2.0F, 0.0F, -0.5F));

		PartDefinition whiskers_fr = whiskers.addOrReplaceChild("whiskers_fr", CubeListBuilder.create().texOffs(12, 25).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -0.5F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -3.0F, -2.75F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, 0.025F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(-2.5F, 0.0F, 0.025F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F))
		.texOffs(24, 0).addBox(1.5F, 0.0F, 0.025F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.5F, 3.225F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 5.025F));

		PartDefinition tailtopfin = tail.addOrReplaceChild("tailtopfin", CubeListBuilder.create().texOffs(0, 29).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.5F, 2.025F));

		PartDefinition analfin = tail.addOrReplaceChild("analfin", CubeListBuilder.create().texOffs(24, 27).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 1.5F, 0.025F));

		PartDefinition pectoralfins = body.addOrReplaceChild("pectoralfins", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.25F));

		PartDefinition fin_fl = pectoralfins.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(3.0F, -0.1F, -2.5F));

		PartDefinition fin_fr = pectoralfins.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-3.0F, -0.1F, -2.5F));

		PartDefinition pelvicfins = body.addOrReplaceChild("pelvicfins", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 2.25F));

		PartDefinition fin_bl = pelvicfins.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(8, 29).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(2.5F, 0.0F, 0.0F));

		PartDefinition fin_br = pelvicfins.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(8, 29).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			limbSwing /= 2;

		this.animateIdle(entity.idleAnimationState, SmallCatfishThornyAnims.POSE, ageInTicks, 1.0F, Math.max(0, 1-(entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishThornyAnims.POSE_BTM, ageInTicks, 1.0f, Math.max(0, (entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishThornyAnims.IDLE, ageInTicks, 1.0F, Math.max(0, 1-(entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)-Math.abs(limbSwingAmount)));
		this.animateIdle(entity.idleAnimationState, SmallCatfishThornyAnims.IDLE_GROUND, ageInTicks, 1.0f, Math.max(0, (entity.getTicksOnGround()/3f)-(entity.getTicksOutsideWater()/3f)-Math.abs(limbSwingAmount)));

		this.animateIdle(entity.idleAnimationState, SmallCatfishThornyAnims.FLOP, ageInTicks, 1.0f, entity.getTicksOutsideWater()/3f);

		this.animateWalk(SmallCatfishThornyAnims.SWIM, limbSwing*3, limbSwingAmount*5f, 2f, Math.max(0,3f*(1-(entity.getTicksOutsideWater()/3f)-entity.getTicksOnGround()/3f)));
		this.animateWalk(SmallCatfishThornyAnims.SWIM_BOTTOM, limbSwing*3, limbSwingAmount*5f, 2f, Math.max(0,(3f*(entity.getTicksOnGround()/3f-(entity.getTicksOutsideWater()/3f)))));

		this.swim_control.xRot = Mth.lerp( entity.getTicksOutsideWater()/5f,
				headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}