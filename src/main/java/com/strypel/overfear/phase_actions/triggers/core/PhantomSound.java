package com.strypel.overfear.phase_actions.triggers.core;

import net.minecraft.sounds.SoundEvent;

import java.util.Random;

public class PhantomSound {
    private SoundEvent sound;
    private double probability;
    private float volume;
    public PhantomSound(SoundEvent soundEvent,double probability){
        this(soundEvent,probability,1.0F);
    }
    public PhantomSound(SoundEvent soundEvent,double probability,float volume){
        this.probability = probability;
        this.sound = soundEvent;
        this.volume = volume;
    }

    public SoundEvent getSound() {
        return sound;
    }
    public SoundEvent getSoundRandom(){
        Random random = new Random();
        return (random.nextDouble() < probability) ? this.sound : null;
    }

    public double getVolume() {
        return volume;
    }
}
