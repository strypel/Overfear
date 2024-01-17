package com.strypel.overfear.client.gui.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;


public abstract class OFOverlay implements Renderable {
    protected Component TITLE;
    public OFOverlay(Component title){
        this.TITLE = title;
    }
    @Override
    public void render(GuiGraphics p_281245_, int p_253973_, int p_254325_, float p_254004_) {

    }
    protected void init(){

    }
}
