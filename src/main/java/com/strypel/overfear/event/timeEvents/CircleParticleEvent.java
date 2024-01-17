package com.strypel.overfear.event.timeEvents;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;


public class CircleParticleEvent extends TimeEvent {
    private ParticleOptions type;
    private double radius;

    public CircleParticleEvent(Vec3 pos,double radius, ParticleOptions type,int time) {
        super(pos,time);
        this.type = type;
        this.radius = radius;
    }

    @Override
    public void onTick(TickEvent.LevelTickEvent event) {

                if(this.time > 0){
                    this.time--;
                    this.tick(event.level,event.level.getServer());
                } else {
                    this.onRemove(event.level,event.level.getServer());
                    MinecraftForge.EVENT_BUS.unregister(this);
                    TimesEvents.unRegisterEvent(this);
                }

    }

    @Override
    public void tick(Level level, MinecraftServer server) {
        for (int i = 0; i < 360; i++) {
            double a = i;
            double x = radius * Math.cos(a);
            double z = radius * Math.sin(a);
            level.addParticle(type,this.pos.x + x,this.pos.y,this.pos.z + z,0,0,0);
        }
    }
}
