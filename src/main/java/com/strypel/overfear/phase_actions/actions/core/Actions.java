package com.strypel.overfear.phase_actions.actions.core;

import com.strypel.overfear.capabilities.phase.PlayerPhaseProvider;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import com.strypel.overfear.phase_actions.triggers.core.IOnceTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTrigger;
import com.strypel.overfear.phase_actions.triggers.core.PhaseTriggers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class Actions {
    public static void doActionsFromPlayer(ServerPlayer player){
        Level level = player.level();
        MinecraftServer server = player.getServer();
        for (int i = 0; i < PhaseTriggers.getTriggers().size(); i++) {
            PhaseTrigger trigger = (PhaseTrigger) PhaseTriggers.getTriggers().get(i);
            if(trigger.isTrue(level,server,player)){
                trigger.getAction().action(level,server,player);
                if(trigger.getAction() instanceof IAffectingPhaseAction){
                    player.getCapability(PlayerPhaseProvider.PLAYER_PHASE).ifPresent(phase -> {
                        phase.copyFrom(((IAffectingPhaseAction) trigger.getAction()).newPhase(phase));
                    });
                }
            }
            if(trigger instanceof IOnceTrigger){
                player.getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(playerTriggersUsed -> {
                    if(playerTriggersUsed.getTriggers().containsKey(trigger.getId()) && ((IOnceTrigger) trigger).isTimeReactivationType()){
                        if(Math.abs(level.getDayTime() - playerTriggersUsed.getTriggers().get(trigger.getId())) >  ((IOnceTrigger) trigger).reactivationTime()){
                            playerTriggersUsed.subPhase(trigger);
                        }
                    }
                });
            }
        }
    }
}
