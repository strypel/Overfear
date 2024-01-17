package com.strypel.overfear.client.gui.overlays.animated;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class Animation {
    public double frame;
    public final net.minecraft.network.chat.Component TITLE;
    public final List<ResourceLocation> frames;
    public int picX;
    public int picY;
    public int picSizeX;
    public int picSizeY;
    public int picSizeXO;
    public int picSizeYO;
    public Animation(Component title, List<ResourceLocation> frames,int picX,int picY,int picSizeX,int picSizeY,int picSizeXO,int picSizeYO){
        this.TITLE = title;
        this.frames = frames;
        this.picX = picX;
        this.picY = picY;
        this.picSizeX = picSizeX;
        this.picSizeY = picSizeY;
        this.picSizeXO = picSizeXO;
        this.picSizeYO = picSizeYO;
    }
    public Animation(Component title, List<ResourceLocation> frames,int picX,int picY,int picSizeX,int picSizeY){
        this(title,frames,picX,picY,picSizeX,picSizeY,picSizeX,picSizeY);
    }
}
