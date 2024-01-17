package com.strypel.overfear.event.timeEvents;

import com.strypel.overfear.Overfear;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Overfear.MODID)
public abstract class TimeEvent {
    protected final int allTime;
    protected int lastTick = 0;
    protected int time;
    protected Vec3 pos;
    protected TimeEvent(Vec3 pos,int time){
        this.pos = pos;
        this.time = time;
        this.allTime = time;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.LevelTickEvent event){
        if(event.level.getServer() != null){
            if(event.level.getServer().getTickCount() != this.lastTick){
                if(this.time > 0){
                    this.time--;

                    this.tick(event.level,event.level.getServer());

                } else {
                    this.onRemove(event.level,event.level.getServer());
                    MinecraftForge.EVENT_BUS.unregister(this);
                    TimesEvents.unRegisterEvent(this);
                }
                this.lastTick = event.level.getServer().getTickCount();
            }
        }
    }

    public abstract void tick(Level level, MinecraftServer server);
    protected void onRemove(Level level, MinecraftServer server){

    }

}
