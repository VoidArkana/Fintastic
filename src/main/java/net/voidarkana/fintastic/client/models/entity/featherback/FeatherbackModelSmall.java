package net.voidarkana.fintastic.client.models.entity.featherback;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.FeatherbackAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.Featherback;

public class FeatherbackModelSmall<T extends Featherback> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart fin_fr;
	private final ModelPart fin_fl;
	private final ModelPart tail;

	public FeatherbackModelSmall(ModelPart root) {
        super(0.6f, 1, RenderType::entityCutout);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body = this.swim_control.getChild("body");
		this.head = this.body.getChild("head");
		this.fin_fr = this.body.getChild("fin_fr");
		this.fin_fl = this.body.getChild("fin_fl");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.5F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -4.5F, -2.25F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(0.0F, -2.5F, 0.75F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.5F, -3.75F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 0).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.5F, -1.0F, -5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -2.25F));

		PartDefinition fin_fr = body.addOrReplaceChild("fin_fr", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 2.5F, -0.75F));

		PartDefinition fin_fl = body.addOrReplaceChild("fin_fl", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.5F, -0.75F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -3.0F, 2.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(14, 15).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 5.75F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (this.young)
			pLimbSwing /= 2;
		this.animateIdle(pEntity.idleAnimationState, FeatherbackAnims.POSE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f));
		this.animateIdle(pEntity.idleAnimationState, FeatherbackAnims.IDLE, pAgeInTicks, 1.0F, Math.max(0, 1-pEntity.getTicksOutsideWater()/3f-Math.abs(pLimbSwingAmount)));
		this.animateIdle(pEntity.idleAnimationState, FeatherbackAnims.FLOP, pAgeInTicks, 1.0f, pEntity.getTicksOutsideWater()/3f);

		this.animateWalk(FeatherbackAnims.SWIM, pLimbSwing*3, pLimbSwingAmount*5f, 2f, 3f*(1-(pEntity.getTicksOutsideWater()/3f)));

		this.swim_control.xRot = Mth.lerp( pEntity.getTicksOutsideWater()/5f, headPitch * ((float)Math.PI / 180F), 0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}