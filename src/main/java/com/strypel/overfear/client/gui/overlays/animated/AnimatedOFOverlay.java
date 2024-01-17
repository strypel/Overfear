package com.strypel.overfear.client.gui.overlays.animated;

import com.strypel.overfear.client.gui.overlays.OFOverlay;
import com.strypel.overfear.client.gui.overlays.animated.Animation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public abstract class AnimatedOFOverlay extends OFOverlay {
    List<Animation> animations = new ArrayList<>();

    public AnimatedOFOverlay(Component title) {
        super(title);
    }

    protected void addAnimation(Animation animation){
        animations.add(animation);
    }
    protected void removeAnimation(String title){
        for(Animation anim : animations){
            if(anim.TITLE.getString().equals(title)){
                animations.remove(anim);
            }
        }
    }
    protected void renderAnimation(String title, GuiGraphics graphics,int x,int y,int fps,boolean loop){
        for(Animation anim : animations){
            if(anim.TITLE.getString().equals(title)){
                if(anim.frame < anim.frames.size()){
                    graphics.blit(anim.frames.get((int) Math.floor(anim.frame)),x,y,anim.picX,anim.picY,anim.picSizeX,anim.picSizeY,anim.picSizeXO,anim.picSizeYO);
                    anim.frame += 0.0002314 * fps;
                } else {
                    if(loop){
                        anim.frame = 0;
                    }
                }
            }
        }
    }
}
