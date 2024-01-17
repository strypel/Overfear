package com.strypel.overfear.phase_actions.triggers.core;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import com.strypel.overfear.phase_actions.actions.core.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PhaseTrigger {
    private int id;
    private int phase;
    private Action action;
    private boolean onceOnly;
    public PhaseTrigger(int id,int phase,Action action){
        this.phase = phase;
        this.action = action;
        this.id = id;
        this.onceOnly = this instanceof IOnceTrigger;
    }
    public boolean isTrue(Level level, MinecraftServer server, Player player){
        AtomicBoolean flag = new AtomicBoolean(false);
        player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(playerPhase -> {
            player.getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(playerTriggersUsed ->{
                if(Math.floor(playerPhase.getIntPhase()) == this.phase
                        && (this.onceOnly ? !playerTriggersUsed.getTriggers().containsKey(this.getId()) : true)
                ){
                        if(this.сondition(level,server,player)){
                            flag.set(true);
                        } else {
                            flag.set(false);
                        }
                    if(this instanceof IOnceTrigger){
                        if(this.onceOnly) playerTriggersUsed.addPhase(this, (int) level.getDayTime());
                    }
                } else {
                    flag.set(false);
                }
            });
        });
        return flag.get();
    }

    public int getId() {
        return id;
    }

    public abstract boolean сondition(Level level, MinecraftServer server, Player player);

    public Action getAction() {
        return action;
    }

    public int getPhase() {
        return phase;
    }
}
