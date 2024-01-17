package com.strypel.overfear.items.client;

import com.strypel.overfear.items.ActivityAnalyzerItem;
import com.strypel.overfear.items.TapeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TapeRenderer extends GeoItemRenderer<TapeItem> {
    public TapeRenderer() {
        super(new TapeModel());
    }
}