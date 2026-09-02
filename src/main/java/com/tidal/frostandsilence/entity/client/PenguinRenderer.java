package com.tidal.frostandsilence.entity.client;

import com.tidal.frostandsilence.FrostAndSilence;
import com.tidal.frostandsilence.entity.custom.PenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PenguinRenderer extends MobRenderer<PenguinEntity, PenguinEntityRenderState, PenguinModel> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(FrostAndSilence.MOD_ID, "textures/entity/penguin.png");

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(ModEntityModelLayers.PENGUIN)), 0.4f);
    }

    @Override
    public PenguinEntityRenderState createRenderState() {
        return new PenguinEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(PenguinEntityRenderState state) {
        return TEXTURE;
    }
    @Override
    public void extractRenderState(PenguinEntity entity, PenguinEntityRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        double horizontalMovement = entity.getDeltaMovement().horizontalDistanceSqr();
        state.isWalking = state.walkAnimationSpeed > 1.0E-3F || horizontalMovement > 1.0E-6D;
        entity.idleAnimationState.animateWhen(!state.isWalking, entity.tickCount);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
