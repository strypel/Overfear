package com.strypel.overfear.phase_actions.triggers.core;

import com.google.common.collect.ImmutableList;
import com.strypel.overfear.capabilities.triggersused.PlayerTriggersUsedProvider;
import com.strypel.overfear.phase_actions.actions.*;
import com.strypel.overfear.phase_actions.triggers.*;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PhaseTriggers {
    private static List<PhaseTrigger> triggers = new ArrayList<>();

    public static void register(){
        //A place where you can register your triggers
        //Note: Trigger IDs should not be the sam
        //Note 2: OnceOnly - means that when trigger.isTrue = true it stops working until the phase is switched to any other
        registerTrigger(new ChanceTrigger(1,0,new AddPhaseAction(),0.00003388888F));
        registerTrigger(new ChanceTrigger(2,1,new RandomLiteSoundAction(),0.00001388888F));
        registerTrigger(new RandomItemTrigger(3,1,new RandomItemAction()));
        registerTrigger(new RandomItemTrigger(4,2,new RandomItemAction()));
        registerTrigger(new ChanceTrigger(5,2,new RandomLiteSoundAction(),0.00001388888F));
        registerTrigger(new RandomOpenDoorTrigger(6,2,new AddPhaseAction()));
        registerTrigger(new SpawnItemInChestTrigger(7,3,new AddPhaseAction()));
        registerTrigger(new ChanceTrigger(8,3,new SpawnBlockNearbyAction(),0.000009088888F));
        registerTrigger(new SpawnSpectatorTrigger(9,3,new SpawnSpectatorAction()));
        registerTrigger(new ChanceTrigger(10,3,new RandomLiteSoundAction(),0.00001088888F));
        registerTrigger(new RandomItemTrigger(11,3,new RandomItemAction()));
        registerTrigger(new RandomOpenDoorTrigger(12,3,new AddPhaseAction()));
        registerTrigger(new SpawnItemInChestTrigger(13,3,new AddPhaseAction()));
        registerTrigger(new SpawnSpectatorTrigger(14,4,new SpawnSpectatorAction()));
        registerTrigger(new ChanceTrigger(15,4,new SpawnBlockNearbyAction(),0.00001088888F));
        registerTrigger(new ChanceTrigger(16,4,new RandomLiteSoundAction(),0.00002388888F));
        registerTrigger(new RandomItemTrigger(17,4,new RandomItemAction()));
        registerTrigger(new RandomOpenDoorTrigger(18,4,new AddPhaseAction()));
        registerTrigger(new SpawnItemInChestTrigger(19,4,new AddPhaseAction()));
    }

    public static PhaseTrigger registerTrigger(PhaseTrigger trigger){
        triggers.add(trigger);
        return trigger;
    }

    public static ImmutableList getTriggers(){
        return ImmutableList.copyOf(triggers);
    }


    public static void resetTriggersForPlayer(double phase, Player player) {
        player.getCapability(PlayerTriggersUsedProvider.PLAYER_TRIGGER_USED).ifPresent(playerTriggersUsed -> {
            for (PhaseTrigger trigger : triggers) {
                if (trigger.getPhase() == phase){
                    if(trigger instanceof IOnceTrigger){
                        playerTriggersUsed.subPhase(trigger);
                    } else {
                        playerTriggersUsed.subPhase(trigger);
                    }
                }
            }
        });
    }
}
