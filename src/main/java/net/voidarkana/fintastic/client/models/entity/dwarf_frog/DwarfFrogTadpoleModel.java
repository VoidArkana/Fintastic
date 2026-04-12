package net.voidarkana.fintastic.client.models.entity.dwarf_frog;// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.voidarkana.fintastic.client.animation.DwarfFrogTadpoleAnims;
import net.voidarkana.fintastic.client.animation.SalmonAnims;
import net.voidarkana.fintastic.client.animation.SharkminnowAnims;
import net.voidarkana.fintastic.client.models.entity.base.FintasticModel;
import net.voidarkana.fintastic.common.entity.custom.DwarfFrog;

public class DwarfFrogTadpoleModel<T extends DwarfFrog> extends FintasticModel<T> {

	private final ModelPart root;
	private final ModelPart swim_rot;
	private final ModelPart body;
	private final ModelPart tail;

	public DwarfFrogTadpoleModel(ModelPart root) {
		super(1, 0);
		this.root = root.getChild("root");
		this.swim_rot = this.root.getChild("swim_rot");
		this.body = this.swim_rot.getChild("body");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_rot = root.addOrReplaceChild("swim_rot", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition body = swim_rot.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(11, 11).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 2.0F, -0.25F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(11, 6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 2.0F, -0.25F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -1.5F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 2.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateIdle(entity.idleAnimationState, DwarfFrogTadpoleAnims.IDLE, ageInTicks, 1.0F, 1-entity.getTicksOutsideWater()/3f);
		this.animateIdle(entity.idleAnimationState, DwarfFrogTadpoleAnims.FLOP, ageInTicks, 1.0F,entity.getTicksOutsideWater()/3f);

		this.animateWalk(DwarfFrogTadpoleAnims.SWIM, limbSwing, limbSwingAmount, 2f, Mth.lerp(entity.getTicksOutsideWater()/3f,3f,0));

		this.swim_rot.xRot = Mth.lerp(entity.getTicksOutsideWater()/3f,headPitch * ((float)Math.PI / 180F),0) ;
		this.swim_rot.zRot = Mth.lerp(entity.getTicksOutsideWater()/3f,netHeadYaw * ((float)Math.PI / 180F)/2,0);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}