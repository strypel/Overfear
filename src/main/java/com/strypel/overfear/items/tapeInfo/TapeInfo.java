package com.strypel.overfear.items.tapeInfo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;


import java.util.List;

public class TapeInfo {
    private String id;
    private ResourceLocation picture;
    private List<Component> text;
    private SoundEvent sound;
    public TapeInfo(String infoID, ResourceLocation picture, List<Component> text, SoundEvent sound){
        this.id = infoID;
        this.picture = picture;
        this.text = text;
        this.sound = sound;
    }

    public String getId() {
        return this.id;
    }
    public ResourceLocation getPicture() {
        return this.picture;
    }

    public List<Component> getText() {
        if(this.text != null){
            return this.text;
        } else {
            return List.of(Component.nullToEmpty("No text data found"));
        }
    }

    public SoundEvent getSound() {
        return this.sound;
    }
}
