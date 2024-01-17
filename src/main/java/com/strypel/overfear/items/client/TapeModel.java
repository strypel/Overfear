package com.strypel.overfear.items.client;

import com.strypel.overfear.Overfear;
import com.strypel.overfear.items.ActivityAnalyzerItem;
import com.strypel.overfear.items.TapeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TapeModel extends GeoModel<TapeItem> {
    @Override
    public ResourceLocation getModelResource(TapeItem animatable) {
        return new ResourceLocation(Overfear.MODID, "geo/tape.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TapeItem animatable) {
        return new ResourceLocation(Overfear.MODID, "textures/item/tape.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TapeItem animatable) {
        return new ResourceLocation(Overfear.MODID,"animations/tape.animation.json");
    }


}
