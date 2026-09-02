package com.tidal.frostandsilence.entity.client;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import com.tidal.frostandsilence.entity.animation.PenguinEntityAnimation;

public class PenguinModel extends EntityModel<PenguinEntityRenderState> {
    private final ModelPart body;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart upperBody;
    private final KeyframeAnimation idle;

    public PenguinModel(ModelPart root) {
        super(root);
        ModelPart penguin = root.getChild("penguin");
        this.body = penguin.getChild("body");
        ModelPart legs = this.body.getChild("legs");
        this.leftFoot = legs.getChild("left");
        this.rightFoot = legs.getChild("right");
        this.upperBody = this.body.getChild("upperbody");
        ModelPart wings = this.upperBody.getChild("wings");
        this.leftWing = wings.getChild("left2");
        this.rightWing = wings.getChild("right2");
        this.idle = PenguinEntityAnimation.PenguinIdle.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition penguin = partDefinition.addOrReplaceChild("penguin", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

        PartDefinition body = penguin.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left = legs.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 28).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition right = legs.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 28).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition upperbody = body.addOrReplaceChild("upperbody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wings = upperbody.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left2 = wings.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -1.0F, -3.0F, 1.0F, 8.0F, 6.0F), PartPose.offset(4.0F, -9.0F, 0.0F));

        PartDefinition right2 = wings.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -1.0F, -3.0F, 1.0F, 8.0F, 6.0F), PartPose.offset(-4.0F, -9.0F, 0.0F));

        PartDefinition torso = upperbody.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -3.0F, 8.0F, 9.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -4.0F, -3.5F, 8.0F, 6.0F, 7.0F), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.5F));

        PartDefinition upper = beak.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(30, 14).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(0.0F, -12.0F, -4.0F));

        PartDefinition lower = beak.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(30, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(0.0F, -11.0F, -4.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(PenguinEntityRenderState state) {
        super.setupAnim(state);

        if (state.isWalking) {
            this.setupWalkingAnimation(state);
        } else {
            this.idle.apply(state.idleAnimationState, state.ageInTicks);
        }
    }

    private void setupWalkingAnimation(PenguinEntityRenderState state) {
        float walkAmount = Mth.clamp(state.walkAnimationSpeed * 4.0F, 0.35F, 1.0F);
        float phase = state.walkAnimationPos * 0.9F;
        float leftStep = Mth.cos(phase) * walkAmount;
        float rightStep = Mth.cos(phase + Mth.PI) * walkAmount;
        float rock = Mth.sin(phase) * walkAmount;


        this.leftFoot.y -= Math.max(0.0F, leftStep) * 0.5F;
        this.rightFoot.y -= Math.max(0.0F, rightStep) * 0.5F;

        this.body.zRot += rock * 0.08F;
        this.upperBody.yRot += rock * 0.12F;
        this.leftWing.zRot -= 0.25F * walkAmount + rock * 0.12F;
        this.rightWing.zRot += 0.25F * walkAmount - rock * 0.12F;
    }
}
