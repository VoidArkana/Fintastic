package net.voidarkana.fintastic.client.models.entity.small_catfish;// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.voidarkana.fintastic.client.animation.SmallCatfishBanjoAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.SmallCatfish;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class SmallCatfishBanjoModel<T extends SmallCatfish> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart pelvicfins;
	private final ModelPart fin_bl;
	private final ModelPart fin_br;
	private final ModelPart tailfin;
	private final ModelPart tailtip;
	private final ModelPart pectoralfins;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;
	private final ModelPart whiskers;
	private final ModelPart whisker_r;
	private final ModelPart whisker_l;
	private final ModelPart dorsalfin;

	public SmallCatfishBanjoModel(ModelPart root) {
        super(0.6f, 1, RenderType::entityCutout);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.tail = this.body.getChild("tail");
		this.pelvicfins = this.tail.getChild("pelvicfins");
		this.fin_bl = this.pelvicfins.getChild("fin_bl");
		this.fin_br = this.pelvicfins.getChild("fin_br");
		this.tailfin = this.tail.getChild("tailfin");
		this.tailtip = this.tailfin.getChild("tailtip");
		this.pectoralfins = this.body.getChild("pectoralfins");
		this.fin_fr = this.pectoralfins.getChild("fin_fr");
		this.fin_fl = this.pectoralfins.getChild("fin_fl");
		this.whiskers = this.body.getChild("whiskers");
		this.whisker_r = this.whiskers.getChild("whisker_r");
		this.whisker_l = this.whiskers.getChild("whisker_l");
		this.dorsalfin = this.body.getChild("dorsalfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -2.25F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1F, -0.7071F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.1786F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.1F, -1.2214F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -0.5F, -0.25F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.4F, 0.7786F));

		PartDefinition pelvicfins = tail.addOrReplaceChild("pelvicfins", CubeListBuilder.create(), PartPose.offset(0.0F, 0.45F, 0.75F));

		PartDefinition fin_bl = pelvicfins.addOrReplaceChild("fin_bl", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition fin_br = pelvicfins.addOrReplaceChild("fin_br", CubeListBuilder.create().texOffs(16, 0).mirror().addBox(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 3.75F));

		PartDefinition tailtip = tailfin.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(10, 7).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition pectoralfins = body.addOrReplaceChild("pectoralfins", CubeListBuilder.create(), PartPose.offset(0.0F, 0.05F, -1.2214F));

		PartDefinition fin_fr = pectoralfins.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-5.75F, 0.0F, -0.25F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

		PartDefinition fin_fl = pectoralfins.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(0, 5).addBox(-0.25F, 0.0F, -0.25F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(2.5F, 0.0F, 0.0F));

		PartDefinition whiskers = body.addOrReplaceChild("whiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 0.05F, -3.5714F));

		PartDefinition whisker_r = whiskers.addOrReplaceChild("whisker_r", CubeListBuilder.create().texOffs(12, 15).mirror().addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition whisker_l = whiskers.addOrReplaceChild("whisker_l", CubeListBuilder.create().texOffs(12, 15).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create().texOffs(16, 2).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -0.9F, 0.5286F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;

		this.animateIdle(pEntity.idleAnimationState, SmallCatfishBanjoAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-(pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, SmallCatfishBanjoAnims.IDLE_GROUND, pAgeInTicks, 1.0f, Math.max(0, (pEntity.getTicksOnGround()/3f)-(pEntity.getTicksOutsideWater()/3f)-Math.abs(pLimbSwingAmount)));

		this.animateIdle(pEntity.idleAnimationState, SmallCatfishBanjoAnims.BEACHED, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(SmallCatfishBanjoAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,3f*(1-(pEntity.getTicksOutsideWater()/3f)-pEntity.getTicksOnGround()/3f)));
		this.animateWalk(SmallCatfishBanjoAnims.SWIM_BOTTOM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, Math.max(0,(3f*(pEntity.getTicksOnGround()/3f-(pEntity.getTicksOutsideWater()/3f)))));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f,
				headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}