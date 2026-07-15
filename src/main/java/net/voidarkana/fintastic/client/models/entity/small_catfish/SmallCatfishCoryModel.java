package net.voidarkana.fintastic.client.models.entity.small_catfish;

import net.voidarkana.fintastic.client.animation.SmallCatfishCorysAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class SmallCatfishCoryModel<T extends SmallCatfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart tailtopfin;
	private final ModelPart tail;
	private final ModelPart dorsalfin;
	private final ModelPart pectoralfins;
	private final ModelPart fin_fl;
	private final ModelPart fin_fr;
	private final ModelPart pelvicfins;
	private final ModelPart fin_bl;
	private final ModelPart fin_br;
	private final ModelPart whiskers;
	private final ModelPart whiskers_fr;
	private final ModelPart whiskers_fl;

	public SmallCatfishCoryModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityTranslucent);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.tailtopfin = this.body.getChild("tailtopfin");
		this.tail = this.body.getChild("tail");
		this.dorsalfin = this.body.getChild("dorsalfin");
		this.pectoralfins = this.body.getChild("pectoralfins");
		this.fin_fl = this.pectoralfins.getChild("fin_fl");
		this.fin_fr = this.pectoralfins.getChild("fin_fr");
		this.pelvicfins = this.body.getChild("pelvicfins");
		this.fin_bl = this.pelvicfins.getChild("fin_bl");
		this.fin_br = this.pelvicfins.getChild("fin_br");
		this.whiskers = this.body.getChild("whiskers");
		this.whiskers_fr = this.whiskers.getChild("whiskers_fr");
		this.whiskers_fl = this.whiskers.getChild("whiskers_fl");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(-0.3571F, -2.1429F, -0.8929F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1429F, -1.8571F, -2.6071F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition tailtopfin = body.addOrReplaceChild("tailtopfin", CubeListBuilder.create().texOffs(10, 15).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.3571F, -1.8571F, 2.3929F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offset(0.3571F, -0.8571F, 3.3929F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(6, 11).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.3571F, -1.8571F, -0.6071F));

		PartDefinition pectoralfins = body.addOrReplaceChild("pectoralfins", CubeListBuilder.create(), PartPose.offset(0.3571F, 1.0429F, 0.3929F));

		PartDefinition fin_fl = pectoralfins.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(6, 9).mirror().addBox(0.0F, 0.0F, -0.5F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(1.5F, 0.0F, -0.5F));

		PartDefinition fin_fr = pectoralfins.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(6, 9).addBox(-3.0F, 0.0F, -0.5F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.5F, 0.0F, -0.5F));

		PartDefinition pelvicfins = body.addOrReplaceChild("pelvicfins", CubeListBuilder.create(), PartPose.offset(0.3571F, 1.1429F, 2.3929F));

		PartDefinition fin_bl = pelvicfins.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(14, 11).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(0.5F, 0.0F, -0.5F));

		PartDefinition fin_br = pelvicfins.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(14, 11).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(-0.5F, 0.0F, -0.5F));

		PartDefinition whiskers = body.addOrReplaceChild("whiskers", CubeListBuilder.create(), PartPose.offset(0.3571F, 1.1429F, -2.1071F));

		PartDefinition whiskers_fr = whiskers.addOrReplaceChild("whiskers_fr", CubeListBuilder.create().texOffs(10, 11).addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.5F, 0.0F, 0.0F));

		PartDefinition whiskers_fl = whiskers.addOrReplaceChild("whiskers_fl", CubeListBuilder.create().texOffs(10, 11).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(1.5F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;

		this.animateIdle(pEntity.idleAnimationState, SmallCatfishCorysAnims.POSE, pAgeInTicks, 1.0F, Math.max(0, 1-(pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)));
		this.animateIdle(pEntity.idleAnimationState, SmallCatfishCorysAnims.TINY_CORY_POSE_BTM, pAgeInTicks, 1.0f, Math.max(0, (pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)));
		this.animateIdle(pEntity.idleAnimationState, SmallCatfishCorysAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-(pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, SmallCatfishCorysAnims.IDLE_GROUND, pAgeInTicks, 1.0f, Math.max(0, (pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, SmallCatfishCorysAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(SmallCatfishCorysAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,3f*(1-(pEntity.getTicksOutsideWater()/3f)-pEntity.getTicksOnGround()/3f)));
		this.animateWalk(SmallCatfishCorysAnims.SWIM_BOTTOM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,(3f*(pEntity.getTicksOnGround()/3f-(pEntity.getTicksOutsideWater()/3f)))));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f,
				headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}