package net.voidarkana.fintastic.client.models.entity.guppy;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.GuppyAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Guppy;

public class GuppyModel<T extends Guppy> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart dorsalfin;

	public GuppyModel(ModelPart root) {
        super(1, 1);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.fin_fr = this.body.getChild("fin_fr");
		this.fin_fl = this.body.getChild("fin_fl");
		this.tail = this.body.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
		this.dorsalfin = this.body.getChild("dorsalfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -2.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(8, -2).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.5F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(7, 4).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 2.0F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition dorsalfin = body.addOrReplaceChild("dorsalfin", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 1.0F));

		PartDefinition fin_r1 = dorsalfin.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(8, -2).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

        this.dorsalfin.visible = pEntity.getHasDorsalFin();

		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.POSE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));
		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(GuppyAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}