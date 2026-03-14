package net.voidarkana.fintastic.client.models.entity.guppy;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.GuppyAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.GuppyEntity;

public class BabyGuppyModel<T extends GuppyEntity> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart tailfin;

	public BabyGuppyModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.tail = this.body.getChild("tail");
		this.tailfin = this.tail.getChild("tailfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -0.5F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.5F));

		PartDefinition tailfin = tail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(2, 1).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;
		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.POSE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));
		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, GuppyAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(GuppyAnims.BABY_SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}