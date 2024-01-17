package com.strypel.overfear.entity.client;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SpectatorModel extends GeoModel<SpectatorEntity> {
    @Override
    public ResourceLocation getModelResource(SpectatorEntity animatable) {
        return new ResourceLocation(Overfear.MODID,"geo/spectator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpectatorEntity animatable) {
        return new ResourceLocation(Overfear.MODID,"textures/entity/spectator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpectatorEntity animatable) {
        return new ResourceLocation(Overfear.MODID,"animations/spectator.animation.json");
    }

    @Override
    public void setCustomAnimations(SpectatorEntity animatable, long instanceId, AnimationState<SpectatorEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
