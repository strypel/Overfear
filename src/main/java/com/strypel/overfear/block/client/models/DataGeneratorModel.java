package com.strypel.overfear.block.client.models;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.block.entity.DataGeneratorEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class DataGeneratorModel extends DefaultedBlockGeoModel<DataGeneratorEntity> {
	public DataGeneratorModel() {
		super(new ResourceLocation(Overfear.MODID, "data_generator"));
	}

	@Override
	public ResourceLocation getTextureResource(DataGeneratorEntity animatable) {
		return new ResourceLocation(Overfear.MODID, "textures/block/data_generator.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DataGeneratorEntity animatable) {
		return new ResourceLocation(Overfear.MODID, "animations/data_generator.animation.json");
	}

	@Override
	public RenderType getRenderType(DataGeneratorEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}