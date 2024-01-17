package com.strypel.overfear.entity.client;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.entity.custom.SpectatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpectatorRender extends GeoEntityRenderer<SpectatorEntity> {
    public SpectatorRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SpectatorModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SpectatorEntity animatable) {
        return new ResourceLocation(Overfear.MODID,"textures/entity/spectator.png");
    }
}
