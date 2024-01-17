package com.strypel.overfear.block.client;

import com.strypel.overfear.block.client.models.DataGeneratorModel;
import com.strypel.overfear.block.entity.DataGeneratorEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DataGeneratorBlockEntityRenderer extends GeoBlockRenderer<DataGeneratorEntity> {
    public DataGeneratorBlockEntityRenderer() {
        super(new DataGeneratorModel());
    }

}