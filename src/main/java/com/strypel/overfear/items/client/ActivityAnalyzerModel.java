package com.strypel.overfear.items.client;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.items.ActivityAnalyzerItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ActivityAnalyzerModel extends GeoModel<ActivityAnalyzerItem> {
    @Override
    public ResourceLocation getModelResource(ActivityAnalyzerItem animatable) {
        return new ResourceLocation(Overfear.MODID, "geo/activity_analyzer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ActivityAnalyzerItem animatable) {
        return new ResourceLocation(Overfear.MODID, "textures/item/activity_analyzer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ActivityAnalyzerItem animatable) {
        return new ResourceLocation(Overfear.MODID, "animations/activity_analyzer.animation.json");
    }
}
