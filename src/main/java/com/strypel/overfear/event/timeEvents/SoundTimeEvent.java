package com.strypel.overfear.event.timeEvents;

import com.strypel.overfear.network.PacketHandler;
import com.strypel.overfear.network.PlaySoundPlayerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class SoundTimeEvent extends TimeEvent{
    private SoundEvent event;
    private float vol;
    private UUID player;

    public SoundTimeEvent(Vec3 pos,SoundEvent event,float vol, int time) {
        super(pos, time);
        this.event = event;
        this.vol = vol;
    }
    public SoundTimeEvent(UUID player, SoundEvent event, float vol, int time) {
        super(null, time);
        this.player = player;
        this.event = event;
        this.vol = vol;
    }

    @Override
    public void tick(Level level, MinecraftServer server) {

    }

    @Override
    protected void onRemove(Level level, MinecraftServer server) {
        super.onRemove(level, server);
        if(level != null && server != null){
            if(this.player != null){
                PacketHandler.sendToPlayer(new PlaySoundPlayerPacket(this.event),server.getPlayerList().getPlayer(this.player));
            } else {
                if(this.pos != null){
                    level.playSound((Player) null,this.pos.x,this.pos.y,this.pos.z,this.event, SoundSource.AMBIENT,this.vol,1);
                }
            }
        }
    }
}
