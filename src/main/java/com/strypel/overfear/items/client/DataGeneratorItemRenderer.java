package com.strypel.overfear.items.client;

import com.strypel.overfear.items.DataGeneratorItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DataGeneratorItemRenderer extends GeoItemRenderer<DataGeneratorItem> {
    public DataGeneratorItemRenderer() {
        super(new DataGeneratorItemModel());
    }
}