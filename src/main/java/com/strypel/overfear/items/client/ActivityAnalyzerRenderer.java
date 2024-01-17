package com.strypel.overfear.items.client;

import com.strypel.overfear.items.ActivityAnalyzerItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ActivityAnalyzerRenderer extends GeoItemRenderer<ActivityAnalyzerItem> {
    public ActivityAnalyzerRenderer() {
        super(new ActivityAnalyzerModel());
    }
}