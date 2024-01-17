package com.strypel.overfear.items.client;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.items.DataGeneratorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DataGeneratorItemModel extends GeoModel<DataGeneratorItem> {
    @Override
    public ResourceLocation getModelResource(DataGeneratorItem animatable) {
        return new ResourceLocation(Overfear.MODID, "geo/block/data_generator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DataGeneratorItem animatable) {
        return new ResourceLocation(Overfear.MODID, "textures/block/data_generator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DataGeneratorItem animatable) {
        return new ResourceLocation(Overfear.MODID, "animations/data_generator.animation.json");
    }
}